package de.microdi.testclasses;

import javax.annotation.PostConstruct;

import de.microdi.annotations.Component;
import de.microdi.annotations.Component.TYPE;
import de.microdi.annotations.Inject;

@Component(value = "ole222ole", type=TYPE.Singleton)
public class Main implements AnInterface
{

   @Inject(name="oleole")
   Subclass sub1 ;
   
   @PostConstruct
   public void post()
   {
      System.out.println("Postconstruct " + this.getClass().getName());
   }

   
}
