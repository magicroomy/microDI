package de.microdi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.microdi.annotations.Component;
import de.microdi.annotations.Component.TYPE;
import de.microdi.annotations.Inject;
import de.microdi.config.Instanceconfig;
import de.microdi.config.MicroDIConfig;
import de.microdi.config.ComponentDefinition;

public class DIRepository
{
   Map<String, Class<?>> componentByName = new HashMap<String, Class<?>>() ;
   Map<Class<?>, Object> singletonByClass = new HashMap<Class<?>, Object>() ;
   Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<Class<?>, Class<?>>() ;
   private Logger logger = Logger.getLogger(DIRepository.class.getName()) ;

   private static DIRepository repo ;
   
   public static DIRepository instance() 
   {
      return repo ;
   }
   
   public static DIRepository init()
   {
      repo = new DIRepository() ;
      return repo ;
   }
   
   public static DIRepository init(URL configURL)
   {
      repo = new DIRepository(configURL) ;
      return repo ;
   }

   public static DIRepository init(InputStream is)
   {
      repo = new DIRepository(is) ;
      return repo ;
   }
   
   public static DIRepository init(MicroDIConfig config)
   {
      repo = new DIRepository(config) ;
      return repo ;
   }
   
   private DIRepository()
   {
   }

   private DIRepository(URL configURL)
   {
      try
      {
         InputStream is = configURL.openStream() ;
         MicroDIConfig conf = readConfigFromStream( is ) ;
         processConfig(conf) ;
      } 
      catch (IOException e)
      {
         e.printStackTrace();
         System.exit(2) ;
      }
   }
   
   private DIRepository(InputStream is)
   {
      MicroDIConfig config = readConfigFromStream(is) ;
      processConfig(config) ;
   }
   
   private DIRepository(MicroDIConfig config)
   {
      processConfig(config) ;
   }

   private MicroDIConfig readConfigFromStream(InputStream is)
   {
      MicroDIConfig config = null ;
      try
      {
         JAXBContext ctx = JAXBContext.newInstance(MicroDIConfig.class) ;
         Unmarshaller unmarshaller = ctx.createUnmarshaller() ;
         
         config = (MicroDIConfig)unmarshaller.unmarshal(is) ;
         
      } catch (Exception e)
      {
         e.printStackTrace();
         System.exit(2); 
      }
      return config ;
   }
   
   private void processConfig(MicroDIConfig config)
   {
      if ( config.getInstances() != null )
      {
         for (Instanceconfig currInstance : config.getInstances())
         {
            Class<?> instanceClass = null ;
            try
            {
               instanceClass = Class.forName(currInstance.getClassname());
            } catch (ClassNotFoundException e)
            {
               System.err.println("Class " + currInstance.getClassname() + " not in Classpath");
               System.exit(1);
            }
            
            logger.fine( "Stored " + currInstance.getName() + " as " + currInstance.getClassname() );
            componentByName.put( currInstance.getName(), instanceClass ) ;
         }
      }
      if ( config.getComponentDefinitions() != null )
      {
         for ( ComponentDefinition currComponent : config.getComponentDefinitions())
         {
            try
            {
               scanClass(currComponent.getClassname()) ;
            } 
            catch (Exception e)
            {
               System.err.println("Trouble in scanning " + currComponent.getClassname());
               e.printStackTrace();
               System.exit(2) ;
            }
         }
      }
   }
   
   public void scanClasspath()
   {
      try
      {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         Enumeration<URL> urls = cl.getResources("META-INF/microDI.xml");
         
         while ( urls.hasMoreElements() )
         {
            URL currURL = urls.nextElement() ;
            String urlText = currURL.toString();
            
            logger.fine("Check if " + urlText + " has a component configuration") ;
            
            InputStream is = currURL.openStream();
            
            // we try to read a few bytes => if possible => must have content => parse it
            byte[] testbuffer = new byte[10] ;
            int read = is.read(testbuffer) ;
            if ( read > 0 )
            {
               // config present
               // reopen the stream. maybe we could have used mark/rest, but it seams not all streams support this.
               is.close() ;
               is = currURL.openStream();
               
               MicroDIConfig config = readConfigFromStream(is) ;
               
               processConfig(config) ;
            }
            else 
            {
               logger.fine("Automatically scan " + currURL + " for annotated classes") ;
               
               System.err.println("Scan " + currURL + " for annotated classes");
               
               if ( urlText.startsWith("jar:file") )
               {
                  //jar:file:/empic-client/microDI.jar!/META-INF/microDI.xml
                  String jarPath = urlText.substring("jar:file:/".length(), currURL.toString().length() - "!/META-INF/microDI.xml".length()) ;
                  analyseJar(new JarInputStream(new FileInputStream(jarPath))) ;
               }
               else if ( urlText.startsWith("jar:http") )
               {
                  //jar:http://10.81.1.2:8080/empic-client/microDI.jar!/META-INF/microDI.xml
                  String jarPath = urlText.substring("jar:".length(), currURL.toString().length() - "!/META-INF/microDI.xml".length()) ;
                  URL conn = new URL(jarPath) ;
                  analyseJar( new JarInputStream( conn.openStream())) ;
               }
               else if ( urlText.startsWith("file") )
               {
                  String folder = urlText.substring("file:/".length(), currURL.toString().length() - "/META-INF/microDI.xml".length()) ;
                  analyseClassFolder(new File(folder),folder) ;
               }
               
            }
            
            
         }
      } catch (IOException e)
      {
         e.printStackTrace();
         System.exit(2) ;
      }
   }

   private void analyseClassFolder( File folder, String baseDir )
   {
      File[] files = folder.listFiles() ;
      for ( File currFile : files)
      {
         String absolutName = currFile.getAbsolutePath() ;
         if ( currFile.isDirectory())
         {
            analyseClassFolder(currFile, baseDir); 
         }
         else if ( absolutName.endsWith(".class") )
         {
            String fqcName = absolutName.substring(baseDir.length()+1 , absolutName.length() - ".class".length()).replace(File.separatorChar, '.') ;
            scanClass(fqcName) ;
         }
      }
   }
   
   private void analyseJar(JarInputStream jarPath)
   {
      try
      {
         JarEntry currEntry = null ;
         while ( (currEntry = jarPath.getNextJarEntry()) != null )
         {

            if ( currEntry.getName().endsWith(".class") )
            {
               String classname = currEntry.getName().replace('/', '.') ;
               
               classname = classname.substring(0, classname.length()-".class".length()) ;
               
               scanClass(classname) ;
            }
         }
      } catch (IOException e)
      {
         e.printStackTrace();
         System.exit(2) ;
      }
   }
   private void scanClass(String classname)
   {
      try
      {
         Class<?> testClass;
         try
         {
            testClass = Class.forName(classname);
         } catch (Throwable e)
         {
            System.err.println("Trouble scanning with class " + classname );
            System.exit(1);

            return ;
         }
         Component compAnno = (Component)testClass.getAnnotation(Component.class) ;
         if ( compAnno != null )

         {
            logger.fine("Found annotated class " + testClass.getName() + " with name " + compAnno.value()) ;
            if ( compAnno.value().length() >0 )
            {
               componentByName.put(compAnno.value(), testClass) ;
            }
            else
            {
               componentByName.put(testClass.getName() , testClass) ;
            }

            // store the Interfaces
            Class<?>[] implementsInterfaces = testClass.getInterfaces() ;
            for ( Class<?> currInterface : implementsInterfaces)
            {
               if ( interfaceToImplementation.get(currInterface) != null )
               {
                  logger.fine("More than one class implements interface " + currInterface.getName() +" => Config needed");
               }
               else
               {
                  logger.fine("Accepted " + currInterface.getName() + " => " + testClass.getName());
                  interfaceToImplementation.put(currInterface, testClass) ;
               }
            }
         }
      } catch (Exception e)
      {
         e.printStackTrace();
         System.exit(2) ;
      } 
   }

   public Object getInstance(String instanceName, Class<?> fallBack)
   {
      Class<?> classToLoad = componentByName.get(instanceName); 
      if ( classToLoad == null )
      {
         return getInstance(fallBack) ;
      }
      return getInstance(classToLoad) ;
   }
   
   @SuppressWarnings("unchecked")
   public <T> T getInstance(Class<T> classToInstantiate)
   {
      logger.finest("Get Instance of "+ classToInstantiate.getName());
      Object result = null ;
      try
      {
         // 1. Build the Instance
         
         if ( interfaceToImplementation.get(classToInstantiate) != null )
         {
            classToInstantiate = (Class<T>) interfaceToImplementation.get(classToInstantiate) ;
         }
         
         Component compAnno = (Component)classToInstantiate.getAnnotation(Component.class) ;
         if ( compAnno == null )
         {
            logger.info("Class " + classToInstantiate.getName()+ " is not annotated with @Component and will be generated as new") ;
            // we simple assume that a "new" is required here.
            result = classToInstantiate.newInstance();
         }
         
         if ( compAnno.type() == TYPE.Singleton)
         {
            result = singletonByClass.get(classToInstantiate) ;
            
            if ( result == null )
            {
               result = classToInstantiate.newInstance();
               singletonByClass.put(classToInstantiate, result) ;
            }
            else
            {
               // everything is already done => just return it
               return (T) result ;
            }
         }
         else
         {
            result = classToInstantiate.newInstance();
         }

         // 2. Fill the fields of the instance.
         
         // now check the Injections in this class.
         Field[] fieldOfClass = classToInstantiate.getDeclaredFields() ;
         for ( Field currField : fieldOfClass)
         {
            Inject inject = currField.getAnnotation(Inject.class ) ;
            if ( inject != null )
            {
               currField.setAccessible(true) ; // could be private
               
               if ( inject.name().length()>0)
               {
                  currField.set(result, getInstance(inject.name(),currField.getType())) ;
               }
               else
               {
                  currField.set(result, getInstance(currField.getType())) ;   
               }
            }
         }
         
         // 3. do the post processing
         Method[] methods = classToInstantiate.getMethods() ;
         for ( Method currMethod : methods)
         {
            if ( currMethod.isAnnotationPresent(PostConstruct.class))
            {
               currMethod.invoke(result, (Object[]) null) ;
               // stop here or call other methods as well?
               // we currently decided to call all of them
            }
         }
         
      } catch (Exception e)
      {
         e.printStackTrace();
         System.exit(0) ;
      } 
      return (T) result ;
   }
   
}
