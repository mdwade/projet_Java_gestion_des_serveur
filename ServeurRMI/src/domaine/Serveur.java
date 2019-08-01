package domaine;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Serveur implements Serializable
{

	private String num;
	private String nom;
	private String numSalle;
	private int idAdmin;
	
	public Serveur() {};
	
	public Serveur(String num, String nom, String numSalle, int idAdmin) {
		setNum(num);
		setNom(nom);
		setNumSalle(numSalle);
		setIdAdmin(idAdmin);
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNumSalle() {
		return numSalle;
	}

	public void setNumSalle(String numSalle) {
		this.numSalle = numSalle;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
}
