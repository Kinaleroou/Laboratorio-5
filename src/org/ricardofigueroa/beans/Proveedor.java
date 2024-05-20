package org.ricardofigueroa.beans;

/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
public class Proveedor {
       private int codigoProveedor;
    private String NITProveedor;
    private String nombreProveedor;
    private String apellidoProveedor;
    private String direccionProveedor;
    private String razonSocialProveedor;
    private String contactoProveedor;
    private String paginaWebProveedor;

    public Proveedor() {
    }

    public Proveedor(int codigoProveedor, String NITProveedor, String nombreProveedor, String apellidoProveedor, String direccionProveedor, String razonSocialProveedor, String contactoProveedor, String paginaWebProveedor) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.nombreProveedor = nombreProveedor;
        this.apellidoProveedor = apellidoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.razonSocialProveedor = razonSocialProveedor;
        this.contactoProveedor = contactoProveedor;
        this.paginaWebProveedor = paginaWebProveedor;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getApellidoProveedor() {
        return apellidoProveedor;
    }

    public void setApellidoProveedor(String apellidoProveedor) {
        this.apellidoProveedor = apellidoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    public String getContactoProveedor() {
        return contactoProveedor;
    }

    public void setContactoProveedor(String contactoProveedor) {
        this.contactoProveedor = contactoProveedor;
    }

    public String getPaginaWebProveedor() {
        return paginaWebProveedor;
    }

    public void setPaginaWebProveedor(String paginaWebProveedor) {
        this.paginaWebProveedor = paginaWebProveedor;
    }

    @Override
    public String toString() {
        return getCodigoProveedor() + " | " + getNombreProveedor();
    }

    
}
