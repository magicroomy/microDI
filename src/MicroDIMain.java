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
//      Logger mylogger = Logger.getLogger("") ;
//      for ( Handler handler : mylogger.getHandlers())
//      {
//         handler.setLevel(Level.FINEST) ;
//         handler.setFormatter(new PatternFormatter("%ts %me", "dd.MM.yyyy HH:mm:ss")) ;
//
//      }
//      
//      Logger.getLogger("demo").log(Level.SEVERE, "HOLLA", new Exception("UFF")) ;
//      
//      Logger.getLogger(DIRepository.class.getName()).setLevel(Level.FINEST);
      
      DIRepository repo = DIRepository.init() ;
      repo.scanClasspath();
      repo.getInstance(Main.class) ;
      
      System.out.println("NEW--------------------------------------");
      
      repo.getInstance(Main.class).post() ;
   }
}
