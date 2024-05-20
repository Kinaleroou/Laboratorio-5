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
import org.ricardofigueroa.beans.ProveedorEmail;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuEmailProveedorController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<ProveedorEmail> listaEmailProveedores;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtEmailProveedorid;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtProveedorId;
    @FXML
    private TableView tblEmailProveedores;
    @FXML
    private TableColumn colEmailProveedorid;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colDescripcion;
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
        desactivarControles();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    

    public void cargarDatos() {
        tblEmailProveedores.setItems(getEmailProveedores());
        colEmailProveedorid.setCellValueFactory(new PropertyValueFactory<>("emailProveedorid"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colProveedorId.setCellValueFactory(new PropertyValueFactory<>("proveedorId"));
    }

    public void seleccionarDatos() {
        ProveedorEmail emailProveedor = (ProveedorEmail) tblEmailProveedores.getSelectionModel().getSelectedItem();
        txtEmailProveedorid.setText(String.valueOf(emailProveedor.getEmailProveedorid()));
        txtEmail.setText(emailProveedor.getEmail());
        txtDescripcion.setText(emailProveedor.getDescripcion());
        txtProveedorId.setText(String.valueOf(emailProveedor.getProveedorId()));

    }

    public ObservableList<ProveedorEmail> getEmailProveedores() {
        ArrayList<ProveedorEmail> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmailProveedor() }");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new ProveedorEmail(
                        resultado.getInt("EmailProveedorid"),
                        resultado.getString("email"),
                        resultado.getString("descripcion"),
                        resultado.getInt("proveedorId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmailProveedores = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarControles();
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
                desactivarControles();
                limpiarControles();
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
        ProveedorEmail emailProveedor = new ProveedorEmail();
        emailProveedor.setEmailProveedorid(Integer.parseInt(txtEmailProveedorid.getText()));
        emailProveedor.setEmail(txtEmail.getText());
        emailProveedor.setDescripcion(txtDescripcion.getText());
        emailProveedor.setProveedorId(Integer.parseInt(txtProveedorId.getText()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmailProveedor (?, ?, ?, ?)}");
            procedimiento.setInt(1, emailProveedor.getEmailProveedorid());
            procedimiento.setString(2, emailProveedor.getEmail());
            procedimiento.setString(3, emailProveedor.getDescripcion());
            procedimiento.setInt(4, emailProveedor.getProveedorId());
            procedimiento.execute();
            listaEmailProveedores.add(emailProveedor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                tipoOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblEmailProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMAR eliminar Registro",
                            "ELIMINAR CLIENTES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmailProveedor (?) }");
                            procedimiento.setInt(1, ((ProveedorEmail) tblEmailProveedores.getSelectionModel().getSelectedItem()).getEmailProveedorid());
                            procedimiento.execute();
                            listaEmailProveedores.remove(tblEmailProveedores.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
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
                if (tblEmailProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("ACTUALIZAR");
                    btnReportesP.setText("CANCELAR");
                    btnAgregarP.setDisable(true);
                    btnEliminarP.setDisable(true);
                    imgReportesP.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    activarControles();
                    txtEmailProveedorid.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarP.setText("EDITAR");
                btnReportesP.setText("REPORTES");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgReportesP.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmailProveedor(?, ?, ?, ?) }");
            ProveedorEmail registro = (ProveedorEmail) tblEmailProveedores.getSelectionModel().getSelectedItem();
            registro.setEmail(txtEmail.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setProveedorId(Integer.parseInt(txtProveedorId.getText()));
            procedimiento.setInt(1, registro.getEmailProveedorid());
            procedimiento.setString(2, registro.getEmail());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getProveedorId());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void reporte() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarP.setText("EDITAR");
                btnReportesP.setText("REPORTE");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                btnEditarP.setDisable(false);
                imgEditarP.setImage(new Image("/org/ricardofigueroa/images/editarClientes.png"));
                imgReportesP.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void seleccionarElemento() {
        if (tblEmailProveedores.getSelectionModel().getSelectedItem() != null) {
            txtEmailProveedorid.setText(String.valueOf(((ProveedorEmail) tblEmailProveedores.getSelectionModel().getSelectedItem()).getEmailProveedorid()));
            txtEmail.setText(((ProveedorEmail) tblEmailProveedores.getSelectionModel().getSelectedItem()).getEmail());
            txtDescripcion.setText(((ProveedorEmail) tblEmailProveedores.getSelectionModel().getSelectedItem()).getDescripcion());
            txtProveedorId.setText(String.valueOf(((ProveedorEmail) tblEmailProveedores.getSelectionModel().getSelectedItem()).getProveedorId()));
        }
    }

    public void desactivarControles() {
        txtEmailProveedorid.setEditable(false);
        txtEmail.setEditable(false);
        txtDescripcion.setEditable(false);
        txtProveedorId.setEditable(false);
    }

    public void activarControles() {
        txtEmailProveedorid.setEditable(true);
        txtEmail.setEditable(true);
        txtDescripcion.setEditable(true);
        txtProveedorId.setEditable(true);
    }

    public void limpiarControles() {
        txtEmailProveedorid.setText("");
        txtEmail.setText("");
        txtDescripcion.setText("");
        txtProveedorId.setText("");
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuProveedoresView();
        }
    }
    
    
}
