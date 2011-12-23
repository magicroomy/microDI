package de.microdi;

import static org.junit.Assert.fail;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DIRepositoryTest
{

   @Before
   public void setUp() throws Exception
   {
      
      /**
       * 
   @Test
   public void test()
   {
//      ConsoleHandler consoleHandler = new ConsoleHandler() ;
//      Logger.getLogger("de.microdi.DIRepository").addHandler(consoleHandler);
//      Logger.getLogger("de.microdi.DIRepository").setLevel(Level.FINEST);
      
      
      DIRepository repo = DIRepository.init(this.getClass().getResourceAsStream("/microDIconfig.xml")) ;
      repo.scanClasspath();
      repo.getInstance(Main.class) ;
      
      System.err.println("NEW--------------------------------------");
      
      repo.getInstance(Main.class) ;
      
   }
       */
      
   }

   @Test
   public void testInit()
   {
      DIRepository repo = DIRepository.instance() ;
      Assert.assertNull("Repository already present without init", repo) ;
      repo = DIRepository.init() ;
      Assert.assertNotNull("Repository not returned by init", repo) ;
   }

   @Test
   public void testInitURL()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testInitInputStream()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testInitMicroDIConfig()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testScanClasspath()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testGetInstanceStringClassOfQ()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testGetInstanceClassOfT()
   {
      fail("Not yet implemented");
   }

}
