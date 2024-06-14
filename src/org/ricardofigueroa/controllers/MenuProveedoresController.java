package org.ricardofigueroa.controllers;

/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 Ultmia Fecha de
 * edicion : 19/05/2024
 *
 */
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.ricardofigueroa.beans.Proveedor;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.reportes.generarReporte;
import org.ricardofigueroa.system.Main;

public class MenuProveedoresController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<Proveedor> listaProveedores;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnTelefono;
    @FXML
    private Button btnEmail;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TextField txtNitP;
    @FXML
    private TextField txtContactoP;
    @FXML
    private TextField txtNombreP;
    @FXML
    private TextField txtApellidoP;
    @FXML
    private TextField txtDireccionP;
    @FXML
    private TextField txtRazonSocialP;
    @FXML
    private TextField txtPaginaWebP;
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colNitP;
    @FXML
    private TableColumn colNombreP;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colDireccionP;
    @FXML
    private TableColumn colWebP;
    @FXML
    private TableColumn colRazonSocialP;
    @FXML
    private TableColumn colContactoP;
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
    private ImageView imgEliminarP;
    @FXML
    private ImageView imgEditarP;
    @FXML
    private ImageView imgReportesP;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        deactivarControllers();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
        colNitP.setCellValueFactory(new PropertyValueFactory<>("NITProveedor"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<>("direccionProveedor"));
        colWebP.setCellValueFactory(new PropertyValueFactory<>("paginaWeb"));
        colRazonSocialP.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        colContactoP.setCellValueFactory(new PropertyValueFactory<>("contactoPrincipal"));
    }

    public void seleccionarDatos() {
        txtCodigoP.setText(String.valueOf(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitP.setText((((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor()));
        txtNombreP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor());
        txtApellidoP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor());
        txtDireccionP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocialP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocialProveedor());
        txtPaginaWebP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWebProveedor());
        txtContactoP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getContactoProveedor());
    }

    public ObservableList<Proveedor> getProveedores() {
        ArrayList<Proveedor> lista = new ArrayList<>();
        try {
            PreparedStatement prodecimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores() }");
            ResultSet resultado = prodecimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedor(
                        resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarControllers();
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
                deactivarControllers();
                limpiarControllers();
                btnAgregarP.setText("AGREGAR");
                btnReportesP.setText("REPORTES");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                imgAgregarP.setImage(new Image("/org/ricardofigueroa/images/agregarUsuario.png"));
                imgReportesP.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        proveedor.setNITProveedor(txtNitP.getText());
        proveedor.setNombreProveedor(txtNombreP.getText());
        proveedor.setApellidoProveedor(txtApellidoP.getText());
        proveedor.setDireccionProveedor(txtDireccionP.getText());
        proveedor.setRazonSocialProveedor(txtRazonSocialP.getText());
        proveedor.setContactoProveedor(txtContactoP.getText());
        proveedor.setPaginaWebProveedor(txtPaginaWebP.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores (?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, proveedor.getCodigoProveedor());
            procedimiento.setString(2, proveedor.getNITProveedor());
            procedimiento.setString(3, proveedor.getNombreProveedor());
            procedimiento.setString(4, proveedor.getApellidoProveedor());
            procedimiento.setString(5, proveedor.getDireccionProveedor());
            procedimiento.setString(6, proveedor.getRazonSocialProveedor());
            procedimiento.setString(7, proveedor.getContactoProveedor());
            procedimiento.setString(8, proveedor.getPaginaWebProveedor());
            procedimiento.execute();
            listaProveedores.add(proveedor); // Suponiendo que listaProveedores es la lista observable de proveedores en tu controlador
        } catch (SQLException e) {
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
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMAR elminar Registro",
                            "ELIMINAR CLIENTES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement prodecimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedores (?) }");
                            prodecimiento.setInt(1, ((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            prodecimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
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
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("ACTUALIZAR");
                    btnReportesP.setText("CANCELAR");
                    btnAgregarP.setDisable(true);
                    btnEliminarP.setDisable(true);
                    imgReportesP.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    activarControllers();
                    txtCodigoP.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "¡Debe de seleccionar un registro!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarP.setText("EDITAR");
                btnReportesP.setText("REPORTE");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgReportesP.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                deactivarControllers();
                limpiarControllers();
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {
        switch (tipoOperaciones) {
            case NINGUNO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                deactivarControllers();
                limpiarControllers();
                btnEditarP.setText("EDITAR");
                btnReportesP.setText("REPORTE");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                btnEditarP.setDisable(false);
                //imgEditarC.setImage(new Image("/org/ricardofigueroa/images/editarClientes.png"));
                //imgReportesC.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void imprimirReporte() {
        Map parametro = new HashMap();
        parametro.put("codigoCliente", null);
        generarReporte.mostrarReportes("reporteCliente.jasper", "Reporte de los clientes", parametro);
    }

    public void actualizar() {
        try {
            Proveedor proveedor = (Proveedor) tblProveedores.getSelectionModel().getSelectedItem();
            proveedor.setNITProveedor(txtNitP.getText());
            proveedor.setNombreProveedor(txtNombreP.getText());
            proveedor.setApellidoProveedor(txtApellidoP.getText());
            proveedor.setDireccionProveedor(txtDireccionP.getText());
            proveedor.setRazonSocialProveedor(txtRazonSocialP.getText());
            proveedor.setContactoProveedor(txtContactoP.getText());
            proveedor.setPaginaWebProveedor(txtPaginaWebP.getText());

            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProveedores (?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, proveedor.getCodigoProveedor());
            procedimiento.setString(2, proveedor.getNITProveedor());
            procedimiento.setString(3, proveedor.getNombreProveedor());
            procedimiento.setString(4, proveedor.getApellidoProveedor());
            procedimiento.setString(5, proveedor.getDireccionProveedor());
            procedimiento.setString(6, proveedor.getRazonSocialProveedor());
            procedimiento.setString(7, proveedor.getContactoProveedor());
            procedimiento.setString(8, proveedor.getPaginaWebProveedor());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deactivarControllers() {
        txtDireccionP.setEditable(false);
        txtRazonSocialP.setEditable(false);
        txtContactoP.setEditable(false);
        txtCodigoP.setEditable(false);
        txtNitP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtPaginaWebP.setEditable(false);
    }

    public void activarControllers() {
        txtDireccionP.setEditable(true);
        txtRazonSocialP.setEditable(true);
        txtContactoP.setEditable(true);
        txtCodigoP.setEditable(true);
        txtNitP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtPaginaWebP.setEditable(true);
    }

    public void limpiarControllers() {
        txtDireccionP.clear();
        txtRazonSocialP.clear();
        txtContactoP.clear();
        txtCodigoP.clear();
        txtNitP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtPaginaWebP.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == btnTelefono) {
            escenarioPrincipal.menuTelefonoProveedoresView();
        } else if (event.getSource() == btnEmail) {
            escenarioPrincipal.menuEmailProveedoresView();
        }
    }
}
