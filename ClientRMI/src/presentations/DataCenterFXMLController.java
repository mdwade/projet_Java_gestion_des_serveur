package presentations;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import javafx.util.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import animatefx.animation.Pulse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DataCenterFXMLController implements Initializable
{

    @FXML
    private Pane panPrincipal;
    private Pane pan1;
    private Pane pan2;
    private Pane pan3;
    @FXML
    private ImageView admin_home;

    @FXML
    private ImageView server_home;

    @FXML
    private ImageView salle_home;

    @FXML
    private Label label_hours;

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
    void adminSeeting(MouseEvent event) {
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
    void roomSeeting(MouseEvent event) {
    	
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
    void serverAnime(MouseEvent event) {
    	animeImage(server_home);
    }

    @FXML
    void serverSetting(MouseEvent event) {
    	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SimpleDateFormat formater = null;
        Date aujourdhui = new Date();
        formater = new SimpleDateFormat("dd-MM-yyyy");
        label_date.setText(formater.format(aujourdhui));
        currentDate();
		
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
	
	private void animeImage(ImageView image){
        Pulse pulse = new Pulse(image);
        pulse.setSpeed(2.5);
        pulse.setCycleCount(2);
        pulse.setDelay(Duration.seconds(0.5));
        pulse.play();
    }

}
