package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dao.AdminDAO;
import dao.SalleDAO;
import dao.ServeurDAO;
import datainterface.DataInterface;
import domaine.Admin;
import domaine.Salle;
import domaine.Serveur;

@SuppressWarnings({ "serial" })
public class ServiceRMI extends UnicastRemoteObject implements DataInterface
{

	public ServiceRMI() throws RemoteException {
		super();	
		}

	@Override
	public void ajouter(Serveur serveur) throws RemoteException {
		ServeurDAO dao = new ServeurDAO();
		dao.ajouter(serveur);
		
	}

	@Override
	public ArrayList<Serveur> afficherServeurParSalle(String numSalle) throws RemoteException {
		ServeurDAO dao = new ServeurDAO();
		ArrayList<Serveur> liste = dao.afficherParSalle(numSalle);
		return liste;
	}

	@Override
	public void ajouter(Admin admin) throws RemoteException {
		AdminDAO dao = new AdminDAO();
		dao.ajouter(admin);
		
	}

	@Override
	public void ajouter(Salle salle) throws RemoteException {
		SalleDAO dao = new SalleDAO();
		dao.ajouter(salle);
		
	}

	@Override
	public ArrayList<Salle> afficherSalle() throws RemoteException {
		SalleDAO dao = new SalleDAO();
		ArrayList<Salle> liste = dao.afficherTout();
		return liste;
	}

	@Override
	public ArrayList<Admin> afficherAdmin() throws RemoteException {
		AdminDAO dao = new AdminDAO();
		ArrayList<Admin> liste = dao.afficherTout();
		return liste;
	}

	@Override
	public ArrayList<Serveur> afficherServeur() throws RemoteException {
		ServeurDAO dao = new ServeurDAO();
		ArrayList<Serveur> liste = dao.afficherTout();
		return liste;
	}

	@Override
	public ArrayList<Serveur> afficherServeurParAdmin(int id) throws RemoteException {
		ServeurDAO dao = new ServeurDAO();
		ArrayList<Serveur> liste = dao.afficherParAdmin(id);
		return liste;
	}

}
