import de.microdi.DIRepository;

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
      DIRepository repo = DIRepository.init() ;
      repo.scanClasspath();
      
      System.out.println("NEW--------------------------------------");
      
   }
}
