package datainterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import domaine.Admin;
import domaine.Salle;
import domaine.Serveur;

public interface DataInterface extends Remote 
{
	public void ajouter(Salle salle) throws RemoteException;
	public ArrayList<Salle> afficherSalle() throws RemoteException;
	public void ajouter(Admin admin) throws RemoteException;
	public ArrayList<Admin> afficherAdmin() throws RemoteException;
	public void ajouter(Serveur serveur) throws RemoteException;
	public ArrayList<Serveur> afficherServeur() throws RemoteException;
	public ArrayList<Serveur> afficherServeurParSalle(String numSalle) throws RemoteException;
	public ArrayList<Serveur> afficherServeurParAdmin(int id) throws RemoteException;

}
