package bean;

public class Composant {
	private int id;
	private String nom;
	
	public Composant() {
		// TODO Auto-generated constructor stub
	}
	
	public Composant(int aid, String anom) {
		nom=anom;
		id=aid;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+" "+nom;
	}
	
	public int getId() {
		return id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
}
