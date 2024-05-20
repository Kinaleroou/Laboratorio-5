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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.ricardofigueroa.beans.Compra;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuCompraController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<Compra> listaCompras;

    @FXML
    private Button btnRegresar;
    
    @FXML
    private Button btnDetalle;

    @FXML
    private TextField txtIdCompra;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTotalC;
    @FXML
    private DatePicker dpFechaCompra;

    @FXML
    private TableView<Compra> tblCompra;

    @FXML
    private TableColumn colIdCompra;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colTotalC;
    @FXML
    private TableColumn colFechaCompra;

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
        desactivarControles();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tblCompra.setItems(getCompras());
        colIdCompra.setCellValueFactory(new PropertyValueFactory<>("idCompra"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colTotalC.setCellValueFactory(new PropertyValueFactory<>("total"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
    }

    public void seleccionarDatos() {
        // Lógica de selección de datos de compra
        txtIdCompra.setText(String.valueOf(tblCompra.getSelectionModel().getSelectedItem().getCompraId()));
        txtDescripcion.setText(tblCompra.getSelectionModel().getSelectedItem().getDescripcion());
        txtTotalC.setText(String.valueOf(tblCompra.getSelectionModel().getSelectedItem().getTotalCompra()));
        dpFechaCompra.setValue(tblCompra.getSelectionModel().getSelectedItem().getFechaCompra());
    }

    public ObservableList<Compra> getCompras() {
        ArrayList<Compra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compra(
                        resultado.getInt("compraId"),
                        resultado.getDate("fechaCompra").toLocalDate(),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalCompra")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                activarControles();
                limpiarCampos();
                btnAgregarC.setText("Guardar");
                btnReportesC.setText("Cancelar");
                imgAgregarC.setImage(new Image("/org/ricardofigueroa/images/guardarImagen.png"));
                imgReportesC.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                btnEditarC.setDisable(true);
                btnEliminarC.setDisable(true);
                tipoOperaciones = operaciones.AGREGAR;
                break;
            case AGREGAR:
                guardar();
                desactivarControles();
                btnAgregarC.setText("Agregar");
                btnReportesC.setText("Reportes");
                btnEditarC.setDisable(false);
                btnEliminarC.setDisable(false);
                imgAgregarC.setImage(new Image("/org/ricardofigueroa/images/agregarUsuario.png"));
                imgReportesC.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        Compra compra = new Compra(
                Integer.parseInt(txtIdCompra.getText()),
                dpFechaCompra.getValue(),
                txtDescripcion.getText(), // Aquí se agrega la descripción
                Double.parseDouble(txtTotalC.getText())
        );
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompras(?, ?, ?, ?)}");
            procedimiento.setInt(1, compra.getCompraId());
            procedimiento.setDate(2, java.sql.Date.valueOf(compra.getFechaCompra()));
            procedimiento.setString(3, compra.getDescripcion()); // Aquí se agrega la descripción
            procedimiento.setDouble(4, compra.getTotalCompra());
            procedimiento.execute();
            listaCompras.add(compra);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                if (tblCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la compra seleccionada?", "Eliminar Compra", JOptionPane.YES_NO_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?)}");
                            procedimiento.setInt(1, tblCompra.getSelectionModel().getSelectedItem().getCompraId());
                            procedimiento.execute();
                            listaCompras.remove(tblCompra.getSelectionModel().getSelectedItem());
                            limpiarCampos();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una compra para eliminar.");
                }
                break;
            default:
                desactivarControles();
                limpiarCampos();
                tipoOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void editar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                if (tblCompra.getSelectionModel().getSelectedItem() != null) {
                    activarControles();
                    btnAgregarC.setDisable(true);
                    btnReportesC.setText("Cancelar");
                    btnEditarC.setText("Actualizar");
                    btnEliminarC.setDisable(true);
                    imgReportesC.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una compra para editar.");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarCampos();
                btnAgregarC.setDisable(false);
                btnReportesC.setText("Reportes");
                btnEditarC.setText("Editar");
                imgReportesC.setImage(new Image("/org/ricardofigueroa/images/reportecliente.png"));
                btnEliminarC.setDisable(false);
                tipoOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        Compra compra = tblCompra.getSelectionModel().getSelectedItem();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompras(?, ?, ?, ?)}");
            procedimiento.setInt(1, compra.getCompraId());
            procedimiento.setDate(2, java.sql.Date.valueOf(compra.getFechaCompra()));
            procedimiento.setString(3, compra.getDescripcion());
            procedimiento.setDouble(4, compra.getTotalCompra());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarCampos();
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

    public void desactivarControles() {
        txtIdCompra.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalC.setEditable(false);
        dpFechaCompra.setDisable(true);
    }

    public void activarControles() {
        txtIdCompra.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalC.setEditable(true);
        dpFechaCompra.setDisable(false);
    }

    public void limpiarCampos() {
        txtIdCompra.clear();
        txtDescripcion.clear();
        txtTotalC.clear();
        dpFechaCompra.setValue(null);
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }else if (event.getSource() == btnDetalle) {
            escenarioPrincipal.MenuDetalleCompraView();
        }
    }
}
