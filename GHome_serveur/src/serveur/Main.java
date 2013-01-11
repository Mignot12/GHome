package serveur;

import java.util.Timer;

import serveur.Log.logType;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Fenetre fenetre = new Fenetre();
        fenetre.setVisible(true);
		Log log = new Log(fenetre);
		log.ecrire("Demarrage de l'application", logType.EVENT);
	}
}
