package dao;

import java.util.ArrayList;

import domaine.Serveur;

public class ServeurDAO extends Database 
{

	public void ajouter(Serveur serveur) {
		try {
			this.statement = this.getConnexion().prepareStatement("INSERT INTO serveur VALUES(?,?,?,?)");
			this.statement.setString(1, serveur.getNum());
			this.statement.setString(2, serveur.getNom());
			this.statement.setString(3, serveur.getNumSalle());
			this.statement.setInt(4, serveur.getIdAdmin());
			this.statement.executeUpdate();
			System.out.println("Ajout réussis");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Serveur> afficherTout() {
		ArrayList<Serveur> liste = new ArrayList<Serveur>();
		Serveur serveur;
		try {
			this.statement = this.getConnexion().prepareStatement("SELECT * FROM serveur");
			this.result = this.statement.executeQuery();
			while (this.result.next()) {
				serveur = new Serveur();
				serveur.setNum(this.result.getString("num"));
				serveur.setNom(this.result.getString("nom"));
				serveur.setNumSalle(this.result.getString("num_salle"));
				serveur.setIdAdmin(this.result.getInt("id_admin"));
				liste.add(serveur);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return liste;
	}

	public ArrayList<Serveur> afficherParSalle(String numSalle) 
	{
		ArrayList<Serveur> liste = new ArrayList<Serveur>();
		Serveur serveur;
		try 
		{
			this.statement = this.getConnexion().prepareStatement("SELECT * FROM serveur WHERE num_salle = ?");
			this.statement.setString(1, numSalle);
			this.result = this.statement.executeQuery();
			
			while (this.result.next())
			{
				serveur = new Serveur();
				serveur.setNum(this.result.getString("num"));
				serveur.setNom(this.result.getString("nom"));
				serveur.setNumSalle(this.result.getString("num_salle"));
				serveur.setIdAdmin(this.result.getInt("id_admin"));
				liste.add(serveur);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return liste;
	}
	
	public ArrayList<Serveur> afficherParAdmin(int id)
	{
		ArrayList<Serveur> liste = new ArrayList<Serveur>();
		Serveur serveur;
		try 
		{
			this.statement = this.getConnexion().prepareStatement("SELECT * FROM serveur WHERE id_admin = ?");
			this.statement.setInt(1, id);
			this.result = this.statement.executeQuery();
			
			while (this.result.next())
			{
				serveur = new Serveur();
				serveur.setNum(this.result.getString("num"));
				serveur.setNom(this.result.getString("nom"));
				serveur.setNumSalle(this.result.getString("num_salle"));
				serveur.setIdAdmin(this.result.getInt("id_admin"));
				liste.add(serveur);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return liste;
	}
}
