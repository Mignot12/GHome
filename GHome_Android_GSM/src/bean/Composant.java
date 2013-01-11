package bean;

public class Composant {
	private String nom;
	private int id;
	
	public Composant() {
		// TODO Auto-generated constructor stub
	}
	
	public Composant(int aid, String anom) {
		nom=anom;
		id=aid;
	}
	
	public String toString(){
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
