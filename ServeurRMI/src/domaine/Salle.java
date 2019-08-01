package domaine;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Salle implements Serializable
{
	
	private String num;
	private String nom;
	
	public Salle() {};
	
	public Salle(String num, String nom)
	{
		setNum(num);
		setNom(nom);
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
}
