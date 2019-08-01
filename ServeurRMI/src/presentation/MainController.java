package presentation;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import service.ServiceRMI;

public class MainController implements Initializable {

    @FXML
    private Button start_button;

    @FXML
    private Button off_button;

    @FXML
    private TextArea text_log;
    private ServiceRMI serviceRMI;
    
    public MainController() throws RemoteException {
    	serviceRMI = new ServiceRMI();
		LocateRegistry.createRegistry(1024);
	}

    @FXML
    void powerOff(MouseEvent event) {
    	try {
			Naming.unbind("rmi://localhost:1024/serviceRMI");
			text_log.appendText("\nLe serveur est eteint");
			off_button.setVisible(false);
			start_button.setVisible(true);
		} catch (Exception e) {
			text_log.appendText(e.getMessage());
		}
    }

    @FXML
    void start(MouseEvent event) {
    	try {
    		Naming.rebind("rmi://localhost:1024/serviceRMI",serviceRMI);
    		text_log.appendText("\nLe serveur a demarré");
			start_button.setVisible(false);
			off_button.setVisible(true);
		} catch (Exception e) {
			text_log.appendText("\n"+e.getMessage());
		}

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    		text_log.appendText("\nCliquez sur Demarrer pour demarrer le Serveur");
    		
	}

}

