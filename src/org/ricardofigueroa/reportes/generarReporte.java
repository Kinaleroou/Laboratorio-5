/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ricardofigueroa.reportes;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.ricardofigueroa.db.Conexion;

/**
 *
 * @author informatica
 */
public class generarReporte {

    public static void mostrarReportes(String nombreReporte, String titulo, Map parametro) {
        InputStream reporte = generarReporte.class.getResourceAsStream(nombreReporte);
        try {
            JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametro, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*
interface Map
    Hashmap  es uno de los objetos que implementa un conjunto de key-value
    tiene un constructor sin parametros new Hashmap () y su finalidad suele  
    referirse para agrupar informacion en un unico objeto.


Tiene cierta similitud con la coleccion de objetos ArrayList, el hashing (Abierto-Cerrado)
en la que se almacena el registro de una diraccion que es generada por una funcion se aplica 
a la llave del registro.

Hash hace referencia a una tecnica de organizacion de archivos.
todo se guarda en memoria fisica

En java hashmap posee un espacio en memoria cuando se guarda un objeto en dicho espacio se
determina su direccion, aplicando una funcion a la llave que le indiquemos.
Cada objeto se identifica mediante algun identificador apropiado.
 */
