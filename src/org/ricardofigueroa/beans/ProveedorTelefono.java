package org.ricardofigueroa.beans;


/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
public class ProveedorTelefono {
    private int TelefonoProveedorId;
    private String numeroP;
    private String numeroS;
    private String Observaciones;
    private int proveedorId;

    public ProveedorTelefono() {
    }

    public ProveedorTelefono(int TelefonoProveedorId, String numeroP, String numeroS, String Observaciones, int proveedorId) {
        this.TelefonoProveedorId = TelefonoProveedorId;
        this.numeroP = numeroP;
        this.numeroS = numeroS;
        this.Observaciones = Observaciones;
        this.proveedorId = proveedorId;
    }

    public int getTelefonoProveedorId() {
        return TelefonoProveedorId;
    }

    public void setTelefonoProveedorId(int TelefonoProveedorId) {
        this.TelefonoProveedorId = TelefonoProveedorId;
    }

    public String getNumeroP() {
        return numeroP;
    }

    public void setNumeroP(String numeroP) {
        this.numeroP = numeroP;
    }

    public String getNumeroS() {
        return numeroS;
    }

    public void setNumeroS(String numeroS) {
        this.numeroS = numeroS;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }


    
}
