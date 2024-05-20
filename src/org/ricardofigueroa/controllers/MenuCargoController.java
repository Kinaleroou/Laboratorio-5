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
import org.ricardofigueroa.beans.Cargos;
import org.ricardofigueroa.db.Conexion;
import org.ricardofigueroa.system.Main;

public class MenuCargoController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperaciones = operaciones.NINGUNO;
    private ObservableList<Cargos> listaCargos;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCargoId;
    @FXML
    private TextField txtNombreCargo;
    @FXML
    private TextField txtDescripcionCargo;
    @FXML
    private TableView tblCargos;
    @FXML
    private TableColumn colCargoId;
    @FXML
    private TableColumn colNombreCargo;
    @FXML
    private TableColumn colDescripcionCargo;
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
        tblCargos.setItems(getCargos());
        colCargoId.setCellValueFactory(new PropertyValueFactory<>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<>("descripcionCargo"));
    }

    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Cargos(
                        resultado.getInt("cargoId"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
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
        Cargos cargo = new Cargos();
        cargo.setCargoId(Integer.parseInt(txtCargoId.getText()));
        cargo.setNombreCargo(txtNombreCargo.getText());
        cargo.setDescripcionCargo(txtDescripcionCargo.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargo(?, ?, ?)}");
            procedimiento.setInt(1, cargo.getCargoId());
            procedimiento.setString(2, cargo.getNombreCargo());
            procedimiento.setString(3, cargo.getDescripcionCargo());
            procedimiento.execute();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        if (tipoOperaciones == operaciones.AGREGAR) {
            desactivarCampos();
            limpiarCampos();
            tipoOperaciones = operaciones.NINGUNO;
        } else {
            if (tblCargos.getSelectionModel().getSelectedItem() != null) {
                int respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMAR eliminar Registro", "ELIMINAR CARGOS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargo(?)}");
                        procedimiento.setInt(1, ((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getCargoId());
                        procedimiento.execute();
                        listaCargos.remove(tblCargos.getSelectionModel().getSelectedIndex());
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
                if (tblCargos.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("ACTUALIZAR");
                    btnReportesP.setText("CANCELAR");
                    btnAgregarP.setDisable(true);
                    btnEliminarP.setDisable(true);
                    imgReportesP.setImage(new Image("/org/ricardofigueroa/images/cancelar.png"));
                    activarCampos();
                    txtCargoId.setEditable(false);
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
        if (tipoOperaciones == operaciones.AGREGAR) {
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
            Cargos cargo = (Cargos) tblCargos.getSelectionModel().getSelectedItem();
            cargo.setNombreCargo(txtNombreCargo.getText());
            cargo.setDescripcionCargo(txtDescripcionCargo.getText());

            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCargo(?, ?, ?)}");
            procedimiento.setInt(1, cargo.getCargoId());
            procedimiento.setString(2, cargo.getNombreCargo());
            procedimiento.setString(3, cargo.getDescripcionCargo());
            procedimiento.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desactivarCampos() {
        txtCargoId.setEditable(false);
        txtNombreCargo.setEditable(false);
        txtDescripcionCargo.setEditable(false);
    }

    public void activarCampos() {
        txtCargoId.setEditable(true);
        txtNombreCargo.setEditable(true);
        txtDescripcionCargo.setEditable(true);
    }

    public void limpiarCampos() {
        txtCargoId.clear();
        txtNombreCargo.clear();
        txtDescripcionCargo.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuEmpleadoView();
        }
    }

    public void seleccionarDatos() {
        Cargos cargoSeleccionado = (Cargos) tblCargos.getSelectionModel().getSelectedItem();
        txtCargoId.setText(String.valueOf(cargoSeleccionado.getCargoId()));
        txtNombreCargo.setText(cargoSeleccionado.getNombreCargo());
        txtDescripcionCargo.setText(cargoSeleccionado.getDescripcionCargo());

    }

}
