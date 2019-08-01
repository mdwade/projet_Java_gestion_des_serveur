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
import domaine.Salle;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PanSalleController implements Initializable {

	private DataInterface salleService;
	@FXML
    private JFXTextField txt_num;

    @FXML
    private TableView<Salle> listSalle;

    @FXML
    private ImageView admin_home;

    @FXML
    private TableColumn<Salle, String> nom;

    @FXML
    private Label label_hours;

    @FXML
    private ImageView salle_home;

    @FXML
    private TableColumn<Salle, String> num;

    @FXML
    private ImageView server_home;

    @FXML
    private Pane panPrincipal;
    private Pane pan1;
    private Pane pan2;
    private Pane pan3;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private Label label_date;
    private ObservableList<Salle> listesalle;
    
    private void showAlert(String title,String message)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
    
    private void animeImage(ImageView image) {
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
            salleService = (DataInterface) Naming.lookup("rmi://localhost:1024/serviceRMI");
        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
        	showAlert("Erreur de communication", "Impossible de joindre le serveur, reessayer ou contacter l'administrateur");
        }

        displayAllSalle();
		
	}
	
	@FXML
    void enregistrer(MouseEvent event) {
        Salle salle;
        String numSalle = txt_num.getText();
        String nomSalle = txt_name.getText();

        salle = new Salle(numSalle, nomSalle);

        try {
            salleService.ajouter(salle);
            txt_num.setText("");
            txt_name.setText("");
        } catch (RemoteException ex) {
        	showAlert("Erreur lors de l'ajout", "Reesayer ou joindre l'administrateur réseau");

        }

        displayAllSalle();
    }
	
	private void displayAllSalle() {
        try {
            ArrayList<Salle> salles = salleService.afficherSalle();
            listesalle = FXCollections.observableArrayList(salles);

        } catch (RemoteException ex) {
        	showAlert("Erreur de récuperation", "Impossible de récupérer la liste des salles");
        }

        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        listSalle.setItems(listesalle);
    }
	
	private void removeAllPane() {
        panPrincipal.getChildren().remove(pan1);
        panPrincipal.getChildren().remove(pan2);
        panPrincipal.getChildren().remove(pan3);
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


}
