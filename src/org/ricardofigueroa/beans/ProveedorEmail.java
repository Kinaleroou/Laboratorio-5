package org.ricardofigueroa.beans;

/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
public class ProveedorEmail {
    private int EmailProveedorid;
    private String email;
    private String descripcion;
    private int proveedorId;

    public ProveedorEmail() {
    }

    public ProveedorEmail(int EmailProveedorid, String email, String descripcion, int proveedorId) {
        this.EmailProveedorid = EmailProveedorid;
        this.email = email;
        this.descripcion = descripcion;
        this.proveedorId = proveedorId;
    }

    public int getEmailProveedorid() {
        return EmailProveedorid;
    }

    public void setEmailProveedorid(int EmailProveedorid) {
        this.EmailProveedorid = EmailProveedorid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    

    
}
