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

import com.jfoenix.controls.JFXTextField;

import animatefx.animation.Pulse;
import datainterface.DataInterface;
import domaine.Admin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PanAdminController implements Initializable {
	
	@FXML
    private JFXTextField text_prenom;

    @FXML
    private Label label_hours;

    @FXML
    private JFXTextField text_nom;

    @FXML
    private TableView<Admin> listeAdmin;

    @FXML
    private Pane panPrincipal;
    private Pane pan1;
    private Pane pan2;
    private Pane pan3;

    @FXML
    private JFXTextField text_pseudo;

    @FXML
    private ImageView admin_home;

    @FXML
    private TableColumn<Admin, String> pseudo;

    @FXML
    private ImageView salle_home;

    @FXML
    private ImageView server_home;

    @FXML
    private TableColumn<Admin, String> nom;

    @FXML
    private TableColumn<Admin, String> prenom;
    private DataInterface adminService;
    private ObservableList<Admin> listAdministrateur;

    @FXML
    private Label label_date;

    @FXML
    void enregistrer(ActionEvent event) {
    	Admin administrateur;
        String pseudoAdmin = text_pseudo.getText();
        String lastNameAdmin = text_nom.getText();
        String firstNameAdmin = text_prenom.getText();

        administrateur = new Admin(pseudoAdmin, lastNameAdmin, firstNameAdmin);

        try {
            adminService.ajouter(administrateur);
            text_pseudo.setText("");
            text_nom.setText("");
            text_prenom.setText("");
        } catch (RemoteException ex) {
            showAlert("Erreur lors de l'ajout", "Reesayer ou joindre l'administrateur réseau");

        }

        afficher(event);
    }
    
    private void afficher(ActionEvent event) {
        displayAllAdmin();
    }
    
    private void animeImage(ImageView image){
        Pulse pulse = new Pulse(image);
        pulse.setSpeed(2.5);
        pulse.setCycleCount(3);
        pulse.setDelay(Duration.seconds(0.5));
        pulse.play();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SimpleDateFormat formater = null;
        Date aujourdhui = new Date();
        formater = new SimpleDateFormat("dd-MM-yyyy");
        label_date.setText(formater.format(aujourdhui));
        currentDate();
        try {
            adminService = (DataInterface) Naming.lookup("rmi://localhost:1024/serviceRMI");
        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
            showAlert("Erreur de communication", "Impossible de joindre le serveur, reessayer ou contacter l'administrateur");
        }
        displayAllAdmin();
		
	}
	
	private void displayAllAdmin() {
        try {
            ArrayList<Admin> admins = adminService.afficherAdmin();
            listAdministrateur = FXCollections.observableArrayList(admins);

        } catch (RemoteException ex) {
            showAlert("Erreur de récuperation", "Impossible de récupérer la liste des administrateur");
        }

        pseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        listeAdmin.setItems(listAdministrateur);
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
	
	private void showAlert(String title,String message)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
