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
import org.ricardofigueroa.beans.DetalleCompra;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuDetalleCompraController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listaDetalleCompras;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField txtDetalleCompraId;
    @FXML
    private TextField txtCantidadCompra;
    @FXML
    private TextField txtProductoId;
    @FXML
    private TextField txtCompraId;
    @FXML
    private TableView<DetalleCompra> tblDetalleCompra;
    @FXML
    private TableColumn<DetalleCompra, Integer> colDetalleCompraId;
    @FXML
    private TableColumn<DetalleCompra, Integer> colCantidadCompra;
    @FXML
    private TableColumn<DetalleCompra, Integer> colProductoId;
    @FXML
    private TableColumn<DetalleCompra, Integer> colCompraId;

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
        tblDetalleCompra.setItems(getDetalleCompras());
        colDetalleCompraId.setCellValueFactory(new PropertyValueFactory<>("detalleCompraId"));
        colCantidadCompra.setCellValueFactory(new PropertyValueFactory<>("cantidadCompra"));
        colProductoId.setCellValueFactory(new PropertyValueFactory<>("productoId"));
        colCompraId.setCellValueFactory(new PropertyValueFactory<>("compraId"));
    }

    public ObservableList<DetalleCompra> getDetalleCompras() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(
                        resultado.getInt("detalleCompraId"),
                        resultado.getInt("cantidadCompra"),
                        resultado.getInt("productoId"),
                        resultado.getInt("compraId")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDetalleCompras = FXCollections.observableArrayList(lista);
    }

    public void seleccionarDatos() {
        DetalleCompra detalleCompraSeleccionado = tblDetalleCompra.getSelectionModel().getSelectedItem();
        if (detalleCompraSeleccionado != null) {
            txtDetalleCompraId.setText(String.valueOf(detalleCompraSeleccionado.getDetalleCompraId()));
            txtCantidadCompra.setText(String.valueOf(detalleCompraSeleccionado.getCantidadCompra()));
            txtProductoId.setText(String.valueOf(detalleCompraSeleccionado.getProductoId()));
            txtCompraId.setText(String.valueOf(detalleCompraSeleccionado.getCompraId()));
        }
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
        DetalleCompra detalleCompra = new DetalleCompra(
                Integer.parseInt(txtDetalleCompraId.getText()),
                Integer.parseInt(txtCantidadCompra.getText()),
                Integer.parseInt(txtProductoId.getText()),
                Integer.parseInt(txtCompraId.getText())
        );
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleCompra(?, ?, ?, ?)}");
            procedimiento.setInt(1, detalleCompra.getDetalleCompraId());
            procedimiento.setInt(2, detalleCompra.getCantidadCompra());
            procedimiento.setInt(3, detalleCompra.getProductoId());
            procedimiento.setInt(4, detalleCompra.getCompraId());
            procedimiento.execute();
            listaDetalleCompras.add(detalleCompra);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoOperaciones) {
            case NINGUNO:
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el detalle de compra seleccionado?", "Eliminar Detalle de Compra", JOptionPane.YES_NO_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, tblDetalleCompra.getSelectionModel().getSelectedItem().getDetalleCompraId());
                            procedimiento.execute();
                            listaDetalleCompras.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
                            limpiarCampos();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un detalle de compra para eliminar.");
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    activarControles();
                    btnAgregarC.setDisable(true);
                    btnReportesC.setText("Cancelar");
                    btnEditarC.setText("Actualizar");
                    btnEliminarC.setDisable(true);
                    imgReportesC.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    tipoOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un detalle de compra para editar.");
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
        DetalleCompra detalleCompra = tblDetalleCompra.getSelectionModel().getSelectedItem();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDetalleCompra(?, ?, ?, ?)}");
            procedimiento.setInt(1, detalleCompra.getDetalleCompraId());
            procedimiento.setInt(2, detalleCompra.getCantidadCompra());
            procedimiento.setInt(3, detalleCompra.getProductoId());
            procedimiento.setInt(4, detalleCompra.getCompraId());
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
        txtDetalleCompraId.setEditable(false);
        txtCantidadCompra.setEditable(false);
        txtProductoId.setEditable(false);
        txtCompraId.setEditable(false);
    }

    public void activarControles() {
        txtDetalleCompraId.setEditable(true);
        txtCantidadCompra.setEditable(true);
        txtProductoId.setEditable(true);
        txtCompraId.setEditable(true);
    }

    public void limpiarCampos() {
        txtDetalleCompraId.clear();
        txtCantidadCompra.clear();
        txtProductoId.clear();
        txtCompraId.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuCompraView();
        }
    }
}
