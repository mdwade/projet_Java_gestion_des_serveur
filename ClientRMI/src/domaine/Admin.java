package domaine;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Admin implements Serializable
{
	
	private int id;
	private String pseudo;
	private String nom;
	private String prenom;
	
	public Admin() {};
	
	public Admin(String pseudo, String nom, String prenom)
	{
		setPseudo(pseudo);
		setNom(nom);
		setPrenom(prenom);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	

}
