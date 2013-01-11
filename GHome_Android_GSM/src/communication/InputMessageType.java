package communication;

public abstract class InputMessageType {
	public static final String LISTE_ENTREES = "liste_entrees";
	public static final String LISTE_SORTIES = "liste_sorties";
	public static final String INTERRUPTEUR = "INTERRUPTEUR";
	
	public static String Interrupteur(int id, String nom){
		return INTERRUPTEUR +" "+id+" "+nom;
	}
}
