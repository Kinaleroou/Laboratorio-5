package org.ricardofigueroa.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.ricardofigueroa.beans.ProveedorTelefono;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuTelefonoProveedorController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<ProveedorTelefono> listaTelefonoProveedores;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtTelefonoProveedorId;
    @FXML
    private TextField txtNumeroP;
    @FXML
    private TextField txtNumeroS;
    @FXML
    private TextField txtObservaciones;
    @FXML
    private TextField txtProveedorId;
    @FXML
    private TableView tblTelefonoProveedor;
    @FXML
    private TableColumn colTelefonoProveedorId;
    @FXML
    private TableColumn colNumeroP;
    @FXML
    private TableColumn colNumeroS;
    @FXML
    private TableColumn colObservaciones;
    @FXML
    private TableColumn colProveedorId;
    @FXML
    private Button btnAgregarP;
    @FXML
    private Button btnEliminarP;
    @FXML
    private Button btnEditarP;
    @FXML
    private Button btnReportesP;
    @FXML
    private ImageView imgAgregarP;
    @FXML
    private ImageView imgEditarP;
    @FXML
    private ImageView imgReportesP;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        desactivarCampos();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tblTelefonoProveedor.setItems(getTelefonoProveedores());
        colTelefonoProveedorId.setCellValueFactory(new PropertyValueFactory<>("telefonoProveedorId"));
        colNumeroP.setCellValueFactory(new PropertyValueFactory<>("numeroP"));
        colNumeroS.setCellValueFactory(new PropertyValueFactory<>("numeroS"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colProveedorId.setCellValueFactory(new PropertyValueFactory<>("proveedorId"));
    }

    public void seleccionarDatos() {
        ProveedorTelefono telefonoProveedor = (ProveedorTelefono) tblTelefonoProveedor.getSelectionModel().getSelectedItem();
        txtTelefonoProveedorId.setText(String.valueOf(telefonoProveedor.getTelefonoProveedorId()));
        txtNumeroP.setText(telefonoProveedor.getNumeroP());
        txtNumeroS.setText(telefonoProveedor.getNumeroS());
        txtObservaciones.setText(telefonoProveedor.getObservaciones());
        txtProveedorId.setText(String.valueOf(telefonoProveedor.getProveedorId()));
    }

    public ObservableList<ProveedorTelefono> getTelefonoProveedores() {
        ArrayList<ProveedorTelefono> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTelefonoProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new ProveedorTelefono(
                        resultado.getInt("TelefonoProveedorId"),
                        resultado.getString("numeroP"),
                        resultado.getString("numeroS"),
                        resultado.getString("Observaciones"),
                        resultado.getInt("proveedorId")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarCampos();
                btnAgregarP.setText("GUARDAR");
                btnReportesP.setText("CANCELAR");
                btnEditarP.setDisable(true);
                btnEliminarP.setDisable(true);
                imgAgregarP.setImage(new Image("/org/ricardofigueroa/images/guardarImagen.png"));
                imgReportesP.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarCampos();
                limpiarCampos();
                btnAgregarP.setText("AGREGAR");
                btnReportesP.setText("REPORTES");
                btnEditarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgAgregarP.setImage(new Image("/org/ricardofigueroa/images/agregarUsuario.png"));
                imgReportesP.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        ProveedorTelefono telefonoProveedor = new ProveedorTelefono();
        telefonoProveedor.setTelefonoProveedorId(Integer.parseInt(txtTelefonoProveedorId.getText()));
        telefonoProveedor.setNumeroP(txtNumeroP.getText());
        telefonoProveedor.setNumeroS(txtNumeroS.getText());
        telefonoProveedor.setObservaciones(txtObservaciones.getText());
        telefonoProveedor.setProveedorId(Integer.parseInt(txtProveedorId.getText()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTelefonoProveedor(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, telefonoProveedor.getTelefonoProveedorId());
            procedimiento.setString(2, telefonoProveedor.getNumeroP());
            procedimiento.setString(3, telefonoProveedor.getNumeroS());
            procedimiento.setString(4, telefonoProveedor.getObservaciones());
            procedimiento.setInt(5, telefonoProveedor.getProveedorId());
            procedimiento.execute();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        if (tipoOperaciones == operaciones.ACTUALIZAR) {
            desactivarCampos();
            limpiarCampos();
            tipoOperaciones = operaciones.NINGUNO;
        } else {
            if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                int respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMAR eliminar Registro",
                        "ELIMINAR PROVEEDORES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefonoProveedor(?)}");
                        procedimiento.setInt(1, ((ProveedorTelefono) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getTelefonoProveedorId());
                        procedimiento.execute();
                        listaTelefonoProveedores.remove(tblTelefonoProveedor.getSelectionModel().getSelectedIndex());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
            }
        }
    }

    public void editar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("ACTUALIZAR");
                    btnReportesP.setText("CANCELAR");
                    btnAgregarP.setDisable(true);
                    btnEliminarP.setDisable(true);
                    imgReportesP.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    activarCampos();
                    txtTelefonoProveedorId.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "¡Debe seleccionar un registro!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarP.setText("EDITAR");
                btnReportesP.setText("REPORTE");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgReportesP.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                desactivarCampos();
                limpiarCampos();
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {
        if (tipoOperaciones == operaciones.ACTUALIZAR) {
            desactivarCampos();
            limpiarCampos();
            btnEditarP.setText("EDITAR");
            btnReportesP.setText("REPORTE");
            btnAgregarP.setDisable(false);
            btnEliminarP.setDisable(false);
            btnEditarP.setDisable(false);
            imgEditarP.setImage(new Image("/org/ricardofigueroa/images/editarClientes.png"));
            imgReportesP.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
            tipoOperaciones = operaciones.NINGUNO;
            cargarDatos();
        }
    }

    public void actualizar() {
        try {
            ProveedorTelefono telefonoProveedor = (ProveedorTelefono) tblTelefonoProveedor.getSelectionModel().getSelectedItem();
            telefonoProveedor.setNumeroP(txtNumeroP.getText());
            telefonoProveedor.setNumeroS(txtNumeroS.getText());
            telefonoProveedor.setObservaciones(txtObservaciones.getText());
            telefonoProveedor.setProveedorId(Integer.parseInt(txtProveedorId.getText()));

            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTelefonoProveedor(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, telefonoProveedor.getTelefonoProveedorId());
            procedimiento.setString(2, telefonoProveedor.getNumeroP());
            procedimiento.setString(3, telefonoProveedor.getNumeroS());
            procedimiento.setString(4, telefonoProveedor.getObservaciones());
            procedimiento.setInt(5, telefonoProveedor.getProveedorId());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desactivarCampos() {
        txtNumeroP.setEditable(false);
        txtNumeroS.setEditable(false);
        txtObservaciones.setEditable(false);
        txtProveedorId.setEditable(false);
    }

    public void activarCampos() {
        txtNumeroP.setEditable(true);
        txtNumeroS.setEditable(true);
        txtObservaciones.setEditable(true);
        txtProveedorId.setEditable(true);
    }

    public void limpiarCampos() {
        txtNumeroP.clear();
        txtNumeroS.clear();
        txtObservaciones.clear();
        txtProveedorId.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuProveedoresView();
        }
    }
}
