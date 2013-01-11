package serveur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {
	Fenetre fenetre;

	public enum logType{
		WARNING, ERROR, EVENT, MESSAGE
	}
	
	public Log(Fenetre afenetre){
		fenetre = afenetre;
		fenetre.setLog(this);
	}
	
	public void ecrire(String message, logType type){
		String text="";
		switch(type){
		case WARNING:
			text = ": [WARNING] ";
			break;
		case ERROR:
			text = ": [ERROR] ";
			break;
		case EVENT:
			text = ": [EVENT] ";
			break;
		case MESSAGE:
			text = ": [MESSAGE] ";
			break;
		}	
		text+=message;
		
		//ajout de la date
		Date now = new Date();
		text = now.toString()+text+"\n";
		
		//System.out.println(text);
		
		File file = new File("log.txt");		
	    FileWriter fw;
			
	    try {
	      //Création de l'objet
	      fw = new FileWriter(file, true);
	      //On écrit la chaîne
	      fw.append(text);
	      //On ferme le flux
	      fw.close();
	      fenetre.majlog();

	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	}
}
