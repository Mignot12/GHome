package bean;

public class Interrupteur extends Composant{
	private Position position;
	public enum Position {
		HG, HD, BG, BD // haut-gauche, haut-droit...
	};
	public Interrupteur() {
		
	}
	
	public Interrupteur(int aid, String anom) {
		super(aid, anom);
		position = Position.HG;
	}
}
