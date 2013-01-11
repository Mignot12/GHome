package serveur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class IPChangeNotifier{
	  Timer t;

	  public IPChangeNotifier() {
	    t = new Timer();
	    t.schedule(new CheckIpPublic(), 0, 1*1000);
	    }

	  class CheckIpPublic extends TimerTask {
		  private String ipActuelle = "";
		  
		 public CheckIpPublic() {
			super();

	        FileReader fr;
	        File file = new File("conf_serveur.txt");
	        String ipPrecedente ="";
	        String str ="";
	    	//Crï¿½ation de l'objet de lecture
	        try {
				fr = new FileReader(file);
		        str = "";
		        int i = 0;
		        //Lecture des donnï¿½es
		        while((i = fr.read()) != -1){
		        	if(i=='\n'){
		        		str ="";
		        	}
		        	else if(i!=' '){
		        		str += (char)i;
		        	}
		        	else{
		        		System.out.println("kokok");
				        if(str.equals("ip_publique"))
				        {
			        		System.out.println("eger");
				        	while((i = fr.read()) != '\n'){
				        		ipPrecedente+= (char)i;
				        	}
			        		System.out.println(ipPrecedente);
				        	if(ipActuelle.equals(ipPrecedente)){
				        		System.out.println("idem");
				        	}
				        	else{
				        		System.out.println("pas idem");
				        	}
				        }
		        	}
		        }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	    public void run() {
			
			File file = new File("log.txt");		
		    FileWriter fw;
				
//		    try {
//		      //Création de l'objet
//		      fw = new FileWriter(file, true);
//		      //On écrit la chaîne
//		      fw.append(text);
//		      //On ferme le flux
//		      fw.close();
//		      fenetre.majlog();
//
//		    } catch (FileNotFoundException e) {
//		      e.printStackTrace();
//		    } catch (IOException e) {
//		      e.printStackTrace();
//		    }
	    }
	  }
}

