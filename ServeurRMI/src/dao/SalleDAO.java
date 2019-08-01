package dao;

import java.util.ArrayList;

import domaine.Salle;

public class SalleDAO extends Database
{

	public void ajouter(Salle salle) {
		try {
			this.statement = this.getConnexion().prepareStatement("INSERT INTO salle VALUES(?,?)");
			this.statement.setString(1, salle.getNum());
			this.statement.setString(2, salle.getNom());
			this.statement.executeUpdate();
			System.out.println("Ajout réussis");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Salle> afficherTout() {
		ArrayList<Salle> liste = new ArrayList<Salle>();
		Salle salle;
		try {
			this.statement = this.getConnexion().prepareStatement("SELECT * FROM salle");
			this.result = this.statement.executeQuery();
			while (this.result.next()) {
				salle = new Salle();
				salle.setNum(this.result.getString("num"));
				salle.setNom(this.result.getString("nom"));
				liste.add(salle);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return liste;
	}
}
