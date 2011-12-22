import de.microdi.DIRepository;
import de.microdi.testclasses.Main;

public class MicroDIMain
{

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      new MicroDIMain() ;
   }

   public MicroDIMain()
   {
      
      DIRepository repo = DIRepository.init(this.getClass().getResourceAsStream("/microDIconfig.xml")) ;
      //repo.scanClasspath();
      repo.getInstance(Main.class) ;
      
      System.out.println("NEW--------------------------------------");
      
      repo.getInstance(Main.class).post() ;
   }
}
