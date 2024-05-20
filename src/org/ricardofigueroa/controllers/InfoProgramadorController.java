package org.ricardofigueroa.controllers;
/**
 * Nombre: Ricardo Figueroa 
 * Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.ricardofigueroa.system.Main;

public class InfoProgramadorController implements Initializable{

    private Main escenarioPrincipal;

    @FXML
    private Button btnRegresar;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
