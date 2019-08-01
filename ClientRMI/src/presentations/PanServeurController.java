package presentations;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.Pulse;
import datainterface.DataInterface;
import domaine.Admin;
import domaine.Salle;
import domaine.Serveur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PanServeurController implements Initializable {

    @FXML
    private JFXTextField txt_nom;

    @FXML
    private JFXComboBox<String> combo_numAdmin;

    @FXML
    private JFXComboBox<String> combo_filter;

    @FXML
    private TableView<Serveur> serverList;

    @FXML
    private Label label_hours;

    @FXML
    private TableColumn<Serveur, String> num;

    @FXML
    private Pane panPrincipal;
    private Pane pan1;
    private Pane pan2;
    private Pane pan3;

    @FXML
    private ImageView admin_home;

    @FXML
    private TableColumn<Serveur, Integer> idAdmin;

    @FXML
    private TableColumn<Serveur, String> numSalle;

    @FXML
    private JFXTextField txt_num;

    @FXML
    private TableColumn<Serveur, String> nom;

    @FXML
    private ImageView salle_home;

    @FXML
    private ImageView server_home;

    @FXML
    private TextField txt_search;
    private DataInterface rmiService;
    private ObservableList<Serveur> listeserveur;

    @FXML
    private JFXComboBox<String> combo_numSalle;

    @FXML
    private Label label_date;
    
    private void showAlert(String title,String message)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

    @FXML
    void rechargerLaListe(KeyEvent event) {
    	if (txt_search.getText().equals("")) {
            displayAllServer();
        }

    }

    @FXML
    void searchAdminById(MouseEvent event) {
    	String filter = combo_filter.getSelectionModel().getSelectedItem();
        
        if (filter.equals("salle")) {
            String idSalle = txt_search.getText();
            try {
                ArrayList<Serveur> serveurs = rmiService.afficherServeurParSalle(idSalle);
                listeserveur = FXCollections.observableArrayList(serveurs);

            } catch (RemoteException ex) {
            	showAlert("Erreur de récuperation", "Impossible de récupérer la liste des serveurs");
            }

        } else if (filter.equals("admin")) {
            int id = Integer.parseInt(txt_search.getText());
            try {
                ArrayList<Serveur> serveurs = rmiService.afficherServeurParAdmin(id);
                listeserveur = FXCollections.observableArrayList(serveurs);

            } catch (RemoteException ex) {
            	showAlert("Erreur de récuperation", "Impossible de récupérer la liste des serveurs");
            }
        }

        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        numSalle.setCellValueFactory(new PropertyValueFactory<>("numSalle"));
        idAdmin.setCellValueFactory(new PropertyValueFactory<>("idAdmin"));
        serverList.setItems(listeserveur);

    }

    @FXML
    void createServer(MouseEvent event) {
    	Serveur serveur;
        String idServeur = txt_num.getText();
        String nameSer = txt_nom.getText();
        String numsalle = combo_numSalle.getSelectionModel().getSelectedItem();
        int idadmin = Integer.parseInt(combo_numAdmin.getSelectionModel().getSelectedItem());

        serveur = new Serveur(idServeur, nameSer, numsalle, idadmin);

        try {
            rmiService.ajouter(serveur);
            txt_num.setText("");
            txt_nom.setText("");
            combo_numSalle.setValue(null);
            combo_numAdmin.setValue(null);
        } catch (RemoteException ex) {
        	showAlert("Erreur lors de l'ajout", "Reesayer ou joindre l'administrateur réseau");

        }

        displayAllServer();

    }
    
    
    private void animeImage(ImageView image){
        Pulse pulse = new Pulse(image);
        pulse.setSpeed(2.5);
        pulse.setCycleCount(3);
        pulse.setDelay(Duration.seconds(0.5));
        pulse.play();
    }


    @FXML
    void salleHome(MouseEvent event) {
    	try {
            removeAllPane();
            final URL fxmlURL = getClass().getResource("panSalle.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            pan2 = fxmlLoader.load();
            panPrincipal.getChildren().add(pan2);
        } catch (IOException ex) {
        	showAlert("Erreur de récupération", "Erreur d'accès à l'interface, réessayer ou contacter l'administrateur");
        }

    }

    @FXML
    void salleAnime(MouseEvent event) {
    	animeImage(salle_home);

    }

    @FXML
    void serverHome(MouseEvent event) {
    	try {
            removeAllPane();
            final URL fxmlURL = getClass().getResource("panServeur.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            pan3 = fxmlLoader.load();
            panPrincipal.getChildren().add(pan3);
        } catch (IOException ex) {
        	showAlert("Erreur de récupération", "Erreur d'accès à l'interface, réessayer ou contacter l'administrateur");
        }

    }

    @FXML
    void serverAnime(MouseEvent event) {
    	animeImage(server_home);

    }

    @FXML
    void adminHome(MouseEvent event) {
    	try {
            removeAllPane();
            final URL fxmlURL = getClass().getResource("panAdmin.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            pan1 = fxmlLoader.load();
            panPrincipal.getChildren().add(pan1);
        } catch (IOException ex) {
        	showAlert("Erreur de récupération", "Erreur d'accès à l'interface, réessayer ou contacter l'administrateur");
        }

    }

    @FXML
    void animeAdmin(MouseEvent event) {
    	animeImage(admin_home);

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SimpleDateFormat formater = null;
        Date aujourdhui = new Date();
        formater = new SimpleDateFormat("dd-MM-yyyy");
        label_date.setText(formater.format(aujourdhui));
        currentDate();
        try {
            rmiService = (DataInterface) Naming.lookup("rmi://localhost:1024/serviceRMI");
        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
        	showAlert("Erreur de communication", "Impossible de joindre le serveur, reessayer ou contacter l'administrateur");
        }

        displayAllServer();

        try {
            ArrayList<Admin> admins = rmiService.afficherAdmin();
            admins.forEach((ad) -> {
                combo_numAdmin.getItems().add("" + ad.getId());
            });

            ArrayList<Salle> salles = rmiService.afficherSalle();
            salles.forEach((s) -> {
                combo_numSalle.getItems().add("" + s.getNum());
            });

        } catch (RemoteException ex) {
        	showAlert("Erreur de récuperation", "Impossible de récupérer les listes");
        }

        combo_filter.getItems().add("admin");
        combo_filter.getItems().add("salle");
		
	}
	
	private void displayAllServer() {
        try {
            ArrayList<Serveur> serveurs = rmiService.afficherServeur();
            listeserveur = FXCollections.observableArrayList(serveurs);

        } catch (RemoteException ex) {
        	showAlert("Erreur de récuperation", "Impossible de récupérer la liste des serveurs");
        }

        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        numSalle.setCellValueFactory(new PropertyValueFactory<>("numSalle"));
        idAdmin.setCellValueFactory(new PropertyValueFactory<>("idAdmin"));
        serverList.setItems(listeserveur);
    }
	
	private void currentDate() {

        Thread clock = new Thread() {

            @Override
            public void run() {
                for (;;) {
                    Calendar cal = new GregorianCalendar();
                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);

                    Platform.runLater(() -> {
                        label_hours.setText(hour + ":" + minute + ":" + second);
                    });
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        clock.start();
    }
	
	private void removeAllPane() {
        panPrincipal.getChildren().remove(pan1);
        panPrincipal.getChildren().remove(pan2);
        panPrincipal.getChildren().remove(pan3);
    }

}
