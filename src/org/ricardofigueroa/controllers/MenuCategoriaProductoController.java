package org.ricardofigueroa.controllers;

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
import org.ricardofigueroa.beans.CategoriaProducto;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuCategoriaProductoController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<CategoriaProducto> listaCatProductos;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtcategoriaProductosId;
    @FXML
    private TextField txtnombreCategoria;
    @FXML
    private TextField txtdescripcionCategoria;
    @FXML
    private TableView tblCatProductos;
    @FXML
    private TableColumn colcategoriaProductosId;
    @FXML
    private TableColumn colnombreCategoria;
    @FXML
    private TableColumn coldescripcionCategoria;
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

    public void seleccionarDatos() {
        CategoriaProducto categoriaSeleccionada = (CategoriaProducto) tblCatProductos.getSelectionModel().getSelectedItem();
        txtcategoriaProductosId.setText(String.valueOf(categoriaSeleccionada.getCategoriaProductosId()));
        txtnombreCategoria.setText(categoriaSeleccionada.getNombreCategoria());
        txtdescripcionCategoria.setText(categoriaSeleccionada.getDescripcionCategoria());
    }

    public void cargarDatos() {
        tblCatProductos.setItems(getCatProductos());
        colcategoriaProductosId.setCellValueFactory(new PropertyValueFactory<>("categoriaProductosId"));
        colnombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        coldescripcionCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcionCategoria"));
    }

    public ObservableList<CategoriaProducto> getCatProductos() {
        ArrayList<CategoriaProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCategoriaProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CategoriaProducto(
                        resultado.getString("nombreCategoria"),
                        resultado.getString("descripcionCategoria"),
                        resultado.getInt("categoriaProductosId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCatProductos = FXCollections.observableArrayList(lista);
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
                tipoOperaciones = operaciones.AGREGAR;
                break;
            case AGREGAR:
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
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setCategoriaProductosId(Integer.parseInt(txtcategoriaProductosId.getText()));
        categoria.setNombreCategoria(txtnombreCategoria.getText());
        categoria.setDescripcionCategoria(txtdescripcionCategoria.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCategoriaProductos(?, ?, ?)}");
            procedimiento.setInt(1, categoria.getCategoriaProductosId());
            procedimiento.setString(2, categoria.getNombreCategoria());
            procedimiento.setString(3, categoria.getDescripcionCategoria());
            procedimiento.execute();
            listaCatProductos.add(categoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoOperaciones) {
            case AGREGAR:
                desactivarCampos();
                limpiarCampos();
                tipoOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCatProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMAR eliminar Registro", "ELIMINAR CATEGORIA PRODUCTOS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCategoriaProductos(?)}");
                            procedimiento.setInt(1, ((CategoriaProducto) tblCatProductos.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
                            procedimiento.execute();
                            listaCatProductos.remove(tblCatProductos.getSelectionModel().getSelectedItem());
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
                if (tblCatProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("ACTUALIZAR");
                    btnReportesP.setText("CANCELAR");
                    btnAgregarP.setDisable(true);
                    btnEliminarP.setDisable(true);
                    imgReportesP.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    activarCampos();
                    txtcategoriaProductosId.setEditable(false);
                    tipoOperaciones = operaciones.EDITAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                }
                break;
            case EDITAR:
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
        switch (tipoOperaciones) {
            case AGREGAR:
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
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCategoriaProductos(?, ?, ?)}");
            CategoriaProducto categoria = (CategoriaProducto) tblCatProductos.getSelectionModel().getSelectedItem();
            categoria.setNombreCategoria(txtnombreCategoria.getText());
            categoria.setDescripcionCategoria(txtdescripcionCategoria.getText());
            procedimiento.setInt(1, categoria.getCategoriaProductosId());
            procedimiento.setString(2, categoria.getNombreCategoria());
            procedimiento.setString(3, categoria.getDescripcionCategoria());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarCampos() {
        txtcategoriaProductosId.setEditable(false);
        txtnombreCategoria.setEditable(false);
        txtdescripcionCategoria.setEditable(false);
    }

    public void activarCampos() {
        txtcategoriaProductosId.setEditable(true);
        txtnombreCategoria.setEditable(true);
        txtdescripcionCategoria.setEditable(true);
    }

    public void limpiarCampos() {
        txtcategoriaProductosId.clear();
        txtnombreCategoria.clear();
        txtdescripcionCategoria.clear();
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
