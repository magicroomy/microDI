package de.microdi.testclasses;

import javax.annotation.PostConstruct;

import de.microdi.annotations.Component;
import de.microdi.annotations.Component.TYPE;
import de.microdi.annotations.Inject;

@Component(type= TYPE.New)
public class SubSubclass
{
   
   @Inject 
   Main main;
   
   @PostConstruct
   public void post()
   {
      System.out.println("Postconstruct " + this.getClass().getName());
   }
   
}
