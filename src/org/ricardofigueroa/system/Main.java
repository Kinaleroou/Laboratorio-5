package org.ricardofigueroa.system;

/**
 * Nombre: Ricardo Figueroa
 * Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.ricardofigueroa.controllers.InfoProgramadorController;
import org.ricardofigueroa.controllers.MenuCargoController;
import org.ricardofigueroa.controllers.MenuClientesController;
import org.ricardofigueroa.controllers.MenuCompraController;
import org.ricardofigueroa.controllers.MenuEmpleadoController;
import org.ricardofigueroa.controllers.MenuPrincipalController;
import org.ricardofigueroa.controllers.MenuProductoController;
import org.ricardofigueroa.controllers.MenuProveedoresController;
import org.ricardofigueroa.controllers.MenuCategoriaProductoController;
import org.ricardofigueroa.controllers.MenuDetalleCompraController;
import org.ricardofigueroa.controllers.MenuEmailProveedorController;
import org.ricardofigueroa.controllers.MenuTelefonoProveedorController;

/**
 *
 * @author rocio
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/ricardofigueroa/views/";

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("KwikiMark");
        menuPrincipalView();

        //Parent root = FXMLLoader.load(getClass().getResource("/org/ricardofigueroa/views/MenuPrincipalView.fxml"));
        //Scene esena = new Scene(root);
        //escenarioPrincipal.setScene(esena);
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception {
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 617, 580);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuCompraView() {
        try {
            MenuCompraController menuPrincipal = (MenuCompraController) cambiarEscena("MenuCompraView.fxml", 910, 580);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuPrincipal = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 920, 581);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuEmpleadoView() {
        try {
            MenuEmpleadoController menuPrincipal = (MenuEmpleadoController) cambiarEscena("MenuEmpleadosView.fxml", 1025, 573);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuProveedoresView() {
        try {
            MenuProveedoresController menuPrincipal = (MenuProveedoresController) cambiarEscena("MenuProveedoresView.fxml", 1025, 573);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuTelefonoProveedoresView() {
        try {
            MenuTelefonoProveedorController menuPrincipal = (MenuTelefonoProveedorController) cambiarEscena("MenuTelefonoProveedoresView.fxml", 1025, 573);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuEmailProveedoresView() {
        try {
            MenuEmailProveedorController menuPrincipal = (MenuEmailProveedorController) cambiarEscena("MenuEmailProveedoresView.fxml", 1025, 573);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuCargoView() {
        try {
            MenuCargoController menuPrincipal = (MenuCargoController) cambiarEscena("MenuCargoView.fxml", 1025, 573);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuProductoView() {
        try {
            MenuProductoController menuPrincipal = (MenuProductoController) cambiarEscena("MenuProductoView.fxml", 1025, 572);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menCategoriaProductoView() {
        try {
            MenuCategoriaProductoController menuPrincipal = (MenuCategoriaProductoController) cambiarEscena("MenuCategoriaProductoView.fxml", 1025, 572);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InfoProgramadorView() {
        try {
            InfoProgramadorController menuPrincipal = (InfoProgramadorController) cambiarEscena("InfoProgramadorView.fxml", 922, 581);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MenuDetalleCompraView() {
        try {
            MenuDetalleCompraController menuPrincipal = (MenuDetalleCompraController) cambiarEscena("MenuDetalleCompraView.fxml", 922, 581);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
