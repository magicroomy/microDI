package de.microdi;

import org.junit.Test;

import de.microdi.testclasses.Main;

public class DIRepositoryTest
{

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

}
