package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Conteneur { 
	
   private static Conteneur instance = null;
   private static ArrayList<Interrupteur> interrupteurs = new ArrayList<Interrupteur>() ;

   protected Conteneur() {
   }
   public static Conteneur getInstance() {
      if(instance == null) {
         instance = new Conteneur();
      }
      return instance;
   }
   
   public void add(Interrupteur i){
	   interrupteurs.add(i);
   }
   
   public static ArrayList<Interrupteur> getInterrupteurs() {
	return interrupteurs;
   }
}
