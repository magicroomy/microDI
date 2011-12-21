package de.microdi.testclasses;

import javax.annotation.PostConstruct;

import de.microdi.annotations.Component;
import de.microdi.annotations.Inject;

@Component
public class Subclass
{
   @Inject 
   SubSubclass subsubclass;

   @Inject 
   SubSubclass subsubclass1;

   @Inject 
   SubSubclass subsubclass2;

   
   @PostConstruct
   public void post()
   {
      System.out.println("Postconstruct " + this.getClass().getName());
   }

   
}
