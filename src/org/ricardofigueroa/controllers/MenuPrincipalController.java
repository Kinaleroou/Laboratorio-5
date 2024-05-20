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
import org.ricardofigueroa.system.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;

    @FXML
    MenuItem btnClientes;
    @FXML
    MenuItem btnProveedores;
    @FXML
    MenuItem btnProductos;
    @FXML
    MenuItem btnInfoProgramador;
    @FXML
    MenuItem btnEmpleados;
    @FXML
    MenuItem btnCompras;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnClientes) {
            escenarioPrincipal.menuClientesView();
        } else if (event.getSource() == btnInfoProgramador) {
            escenarioPrincipal.InfoProgramadorView();
        } else if (event.getSource() == btnProveedores) {
            escenarioPrincipal.menuProveedoresView();
        } else if (event.getSource() == btnProductos) {
            escenarioPrincipal.menuProductoView();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.menuEmpleadoView();
        } else if (event.getSource() == btnCompras) {
            escenarioPrincipal.menuCompraView();
        }
    }

}
