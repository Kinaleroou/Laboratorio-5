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
import org.ricardofigueroa.beans.Empleado;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuEmpleadoController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleado> listaEmpleados;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnCargo;
    @FXML
    private TextField txtEmpleadoId;
    @FXML
    private TextField txtNombreE;
    @FXML
    private TextField txtApellidoE;
    @FXML
    private TextField txtSueldoE;
    @FXML
    private TextField txtCargoId;
    @FXML
    private TextField txtDireccionE;
    @FXML
    private TextField txtTurnoE;
    @FXML
    private TextField txtEncargadoId;
    @FXML
    private TextField txthoraEntrada;
    @FXML
    private TextField txthoraSalida;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TableColumn colEmpleadoId;
    @FXML
    private TableColumn colNombreE;
    @FXML
    private TableColumn colApellidoE;
    @FXML
    private TableColumn colSueldoE;
    @FXML
    private TableColumn colCargoE;
    @FXML
    private TableColumn colDireccionE;
    @FXML
    private TableColumn colTurnoE;
    @FXML
    private TableColumn colEncargadoE;
    @FXML
    private TableColumn colHoraEntrada;
    @FXML
    private TableColumn colHoraSalida;
    @FXML
    private Button btnAgregarE;
    @FXML
    private Button btnEliminarE;
    @FXML
    private Button btnEditarE;
    @FXML
    private Button btnReportesE;
    @FXML
    private ImageView imgAgregarE;
    @FXML
    private ImageView imgEditarE;
    @FXML
    private ImageView imgReportesE;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        deactivarControllers();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<>("empleadoId"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<>("apellidoEmpleado"));
        colSueldoE.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        colDireccionE.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTurnoE.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colCargoE.setCellValueFactory(new PropertyValueFactory<>("cargoId"));
        colEncargadoE.setCellValueFactory(new PropertyValueFactory<>("encargadoId"));
    }

    public void seleccionarDatos() {
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
            Empleado empleadoSeleccionado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
            txtEmpleadoId.setText(String.valueOf(empleadoSeleccionado.getEmpleadoId()));
            txtNombreE.setText(empleadoSeleccionado.getNombreEmpleado());
            txtApellidoE.setText(empleadoSeleccionado.getApellidoEmpleado());
            txtSueldoE.setText(String.valueOf(empleadoSeleccionado.getSueldo()));
            txtDireccionE.setText(empleadoSeleccionado.getDireccion());
            txtTurnoE.setText(empleadoSeleccionado.getTurno());
            txtCargoId.setText(String.valueOf(empleadoSeleccionado.getCargoId()));
            txtEncargadoId.setText(String.valueOf(empleadoSeleccionado.getEncargadoId()));
        }
    }
    
    public ObservableList<Empleado> getEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleado(
                        resultado.getInt("empleadoId"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("apellidoEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("cargoId"),
                        resultado.getInt("encargadoId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarControllers();
                btnAgregarE.setText("GUARDAR");
                btnReportesE.setText("CANCELAR");
                btnEditarE.setDisable(true);
                btnEliminarE.setDisable(true);
                imgAgregarE.setImage(new Image("/org/ricardofigueroa/images/guardarImagen.png"));
                imgReportesE.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                deactivarControllers();
                limpiarControllers();
                btnAgregarE.setText("AGREGAR");
                btnReportesE.setText("REPORTES");
                btnEditarE.setDisable(false);
                btnReportesE.setDisable(false);
                imgAgregarE.setImage(new Image("/org/ricardofigueroa/images/agregarUsuario.png"));
                imgReportesE.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Empleado empleado = new Empleado();
        empleado.setEmpleadoId(Integer.parseInt(txtEmpleadoId.getText()));
        empleado.setNombreEmpleado(txtNombreE.getText());
        empleado.setApellidoEmpleado(txtApellidoE.getText());
        empleado.setSueldo(Double.parseDouble(txtSueldoE.getText()));
        empleado.setDireccion(txtDireccionE.getText());
        empleado.setTurno(txtTurnoE.getText());
        empleado.setCargoId(Integer.parseInt(txtCargoId.getText()));
        empleado.setEncargadoId(Integer.parseInt(txtEncargadoId.getText()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado (?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, empleado.getEmpleadoId());
            procedimiento.setString(2, empleado.getNombreEmpleado());
            procedimiento.setString(3, empleado.getApellidoEmpleado());
            procedimiento.setDouble(4, empleado.getSueldo());
            procedimiento.setString(5, empleado.getDireccion());
            procedimiento.setString(6, empleado.getTurno());
            procedimiento.setInt(7, empleado.getCargoId());
            procedimiento.setInt(8, empleado.getEncargadoId());
            procedimiento.execute();
            listaEmpleados.add(empleado);
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMAR elminar Registro",
                            "ELIMINAR CLIENTES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement prodecimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProductos (?) }");
                            prodecimiento.setInt(1, ((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
                            prodecimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditarE.setText("ACTUALIZAR");
                    btnReportesE.setText("CANCELAR");
                    btnAgregarE.setDisable(true);
                    btnEliminarE.setDisable(true);
                    imgReportesE.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    activarControllers();
                    txtEmpleadoId.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "¡Debe de seleccionar un registro!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarE.setText("EDITAR");
                btnReportesE.setText("REPORTE");
                btnAgregarE.setDisable(false);
                btnEliminarE.setDisable(false);
                imgReportesE.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
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
                btnEditarE.setText("EDITAR");
                btnReportesE.setText("REPORTE");
                btnAgregarE.setDisable(false);
                btnEliminarE.setDisable(false);
                btnEditarE.setDisable(false);
                imgEditarE.setImage(new Image("/org/ricardofigueroa/images/editarClientes.png"));
                imgReportesE.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleado (?, ?, ?, ?, ?, ?, ?, ?)}");
            Empleado empleado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
            empleado.setEmpleadoId(Integer.parseInt(txtEmpleadoId.getText()));
            empleado.setNombreEmpleado(txtNombreE.getText());
            empleado.setApellidoEmpleado(txtApellidoE.getText());
            empleado.setSueldo(Double.parseDouble(txtSueldoE.getText()));
            empleado.setDireccion(txtDireccionE.getText());
            empleado.setTurno(txtTurnoE.getText());
            empleado.setCargoId(Integer.parseInt(txtCargoId.getText()));
            empleado.setEncargadoId(Integer.parseInt(txtEncargadoId.getText()));

            procedimiento.setInt(1, empleado.getEmpleadoId());
            procedimiento.setString(2, empleado.getNombreEmpleado());
            procedimiento.setString(3, empleado.getApellidoEmpleado());
            procedimiento.setDouble(4, empleado.getSueldo());
            procedimiento.setString(5, empleado.getDireccion());
            procedimiento.setString(6, empleado.getTurno());
            procedimiento.setInt(7, empleado.getCargoId());
            procedimiento.setInt(8, empleado.getEncargadoId());
            procedimiento.execute();
            limpiarControllers();
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivarControllers() {
        txtEmpleadoId.setDisable(true);
        txtNombreE.setDisable(true);
        txtApellidoE.setDisable(true);
        txtSueldoE.setDisable(true);
        txtDireccionE.setDisable(true);
        txtTurnoE.setDisable(true);
        txtCargoId.setDisable(true);
        txtEncargadoId.setDisable(true);
        tblEmpleados.setDisable(false);
    }

    public void activarControllers() {
        txtEmpleadoId.setDisable(false);
        txtNombreE.setDisable(false);
        txtApellidoE.setDisable(false);
        txtSueldoE.setDisable(false);
        txtDireccionE.setDisable(false);
        txtTurnoE.setDisable(false);
        txtCargoId.setDisable(false);
        txtEncargadoId.setDisable(false);
        tblEmpleados.setDisable(true);
    }

   public void limpiarControllers() {
        txtEmpleadoId.clear();
        txtNombreE.clear();
        txtApellidoE.clear();
        txtSueldoE.clear();
        txtDireccionE.clear();
        txtTurnoE.clear();
        txtCargoId.clear();
        txtEncargadoId.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }else if (event.getSource() == btnCargo) {
            escenarioPrincipal.menuCargoView();
        }
        
    }
}
