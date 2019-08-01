package dao;

import java.util.ArrayList;

import domaine.Admin;

public class AdminDAO extends Database 
{

	public void ajouter(Admin admin) {
		try {
			this.statement = this.getConnexion().prepareStatement("INSERT INTO admin(pseudo,nom,prenom) VALUES(?,?,?)");
			this.statement.setString(1, admin.getPseudo());
			this.statement.setString(2, admin.getNom());
			this.statement.setString(3, admin.getPrenom());
			
			this.statement.executeUpdate();
			
			System.out.println("Ajout réussis");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Admin> afficherTout() {
		ArrayList<Admin> liste = new ArrayList<Admin>();
		Admin admin;
		
		try {
			this.statement = this.getConnexion().prepareStatement("SELECT * FROM admin");
			this.result = this.statement.executeQuery();
			while (this.result.next()) {
				admin = new Admin();
				admin.setId(this.result.getInt("id"));
				admin.setPseudo(this.result.getString("pseudo"));
				admin.setNom(this.result.getString("nom"));
				admin.setPrenom(this.result.getString("prenom"));
				liste.add(admin);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return liste;
	}
}
