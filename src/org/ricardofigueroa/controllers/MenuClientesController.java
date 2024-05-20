package org.ricardofigueroa.controllers;

/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 Ultmia Fecha de
 * edicion : 02/05/2024
 *
 */
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.ricardofigueroa.beans.Clientes;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<Clientes> listaClientes;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtEmailC;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TextField txtCodigoClienteC;
    @FXML
    private TextField txtNitC;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNitC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private TableColumn colCorreoC;
    @FXML
    private Button btnAgregarC;
    @FXML
    private Button btnEliminarC;
    @FXML
    private Button btnEditarC;
    @FXML
    private Button btnReportesC;
    @FXML
    private ImageView imgAgregarC;
    @FXML
    private ImageView imgEliminarC;
    @FXML
    private ImageView imgEditarC;
    @FXML
    private ImageView imgReportesC;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        deactivarControllers();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNitC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));

    }

    public void seleccionarDatos() {
        txtCodigoClienteC.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNitC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNITCliente()));
        txtNombreC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApellidoC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
        txtDireccionC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtEmailC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList();
        try {
            PreparedStatement prodecimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes() }");
            ResultSet resultado = prodecimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarControllers();
                btnAgregarC.setText("GUARDAR");
                btnReportesC.setText("CANCELAR");
                btnEditarC.setDisable(true);
                btnEliminarC.setDisable(true);
                imgAgregarC.setImage(new Image("/org/ricardofigueroa/images/guardarImagen.png"));
                imgReportesC.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                deactivarControllers();
                limpiarControllers();
                btnAgregarC.setText("AGREGAR");
                btnReportesC.setText("REPORTES");
                btnEditarC.setDisable(false);
                btnReportesC.setDisable(false);
                imgAgregarC.setImage(new Image("/org/ricardofigueroa/images/agregarUsuario.png"));
                imgReportesC.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoClienteC.getText()));
        registro.setNITCliente(txtNitC.getText());
        registro.setNombreCliente((txtNombreC.getText()));
        registro.setApellidoCliente(txtApellidoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setCorreoCliente(txtEmailC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes (?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                deactivarControllers();
                limpiarControllers();
                tipoOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMAR elminar Registro",
                            "ELIMINAR CLIENTES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement prodecimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes (?) }");
                            prodecimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            prodecimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
                }
        }
    }

    public void editar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditarC.setText("ACTUALIZAR");
                    btnReportesC.setText("CANCELAR");
                    btnAgregarC.setDisable(true);
                    btnEliminarC.setDisable(true);
                    imgReportesC.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    activarControllers();
                    txtCodigoClienteC.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "¡Debe de seleccionar un registro!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarC.setText("EDITAR");
                btnReportesC.setText("REPORTE");
                btnAgregarC.setDisable(false);
                btnEliminarC.setDisable(false);
                imgReportesC.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                deactivarControllers();
                limpiarControllers();
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void reporte() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                deactivarControllers();
                limpiarControllers();
                btnEditarC.setText("EDITAR");
                btnReportesC.setText("REPORTE");
                btnAgregarC.setDisable(false);
                btnEliminarC.setDisable(false);
                btnEditarC.setDisable(false);
                imgEditarC.setImage(new Image("/org/ricardofigueroa/images/editarClientes.png"));
                imgReportesC.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {   
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarClientes (?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNITCliente(txtNitC.getText());
            registro.setNombreCliente(txtNombreC.getText());
            registro.setApellidoCliente(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setCorreoCliente(txtEmailC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivarControllers() {
        txtDireccionC.setEditable(false);
        txtEmailC.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtCodigoClienteC.setEditable(false);
        txtNitC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
    }

    public void activarControllers() {
        txtDireccionC.setEditable(true);
        txtEmailC.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtCodigoClienteC.setEditable(true);
        txtNitC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
    }

    public void limpiarControllers() {
        txtDireccionC.clear();
        txtEmailC.clear();
        txtTelefonoC.clear();
        txtCodigoClienteC.clear();
        txtNitC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
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
}
