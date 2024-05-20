package org.ricardofigueroa.beans;

import java.time.LocalDate;

/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
public class Compra {
      private int compraId;
    private LocalDate fechaCompra;
    private String descripcion;
    private double totalCompra;

    public Compra() {
    }

    public Compra(int compraId, LocalDate fechaCompra, String descripcion, double totalCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.descripcion = descripcion;
        this.totalCompra = totalCompra;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }
    
    
    
}
