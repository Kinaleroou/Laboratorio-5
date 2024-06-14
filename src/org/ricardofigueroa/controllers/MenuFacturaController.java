package org.ricardofigueroa.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.ricardofigueroa.reportes.generarReporte;
import org.ricardofigueroa.system.Main;

/**
 *
 * @author rocio
 */
public class MenuFacturaController implements Initializable {

    private Main escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    @FXML
    private Button btnReportesF;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

 /*   @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }*/

    public void reporte() {
        switch (tipoOperaciones) {
            case NINGUNO:
                imprimirReporte();
                break;
        }
    }

    public void imprimirReporte() {
        Map parametro = new HashMap();
        parametro.put("facturaId", null);
        generarReporte.mostrarReportes("reporteFactura.jasper", "Reporte de los clientes", parametro);
    }

}
