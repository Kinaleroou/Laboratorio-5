package org.ricardofigueroa.beans;


/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 
 */
public class Producto {
    
    private  int productoId;
    private  String nombreProducto;
    private  String descripcionProducto;
    private  Double precioVentaMayor;
    private  Double precioCompra;
    private  String imagenProducto;
    private  int cantidadStock;
    private  int distribuidorId;
    private  int categoriaProductosId;

    public Producto() {
    }

    public Producto(int productoId, String nombreProducto, String descripcionProducto, int cantidadStock, Double precioVentaMayor, Double precioCompra, int distribuidorId, String imagenProducto, int categoriaProductosId) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadStock = cantidadStock;
        this.precioVentaMayor = precioVentaMayor;
        this.precioCompra = precioCompra;
        this.distribuidorId = distribuidorId;
        this.imagenProducto = imagenProducto;
        this.categoriaProductosId = categoriaProductosId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Double getPrecioVentaMayor() {
        return precioVentaMayor;
    }

    public void setPrecioVentaMayor(Double precioVentaMayor) {
        this.precioVentaMayor = precioVentaMayor;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public int getCategoriaProductosId() {
        return categoriaProductosId;
    }

    public void setCategoriaProductosId(int categoriaProductosId) {
        this.categoriaProductosId = categoriaProductosId;
    }
    
    
    
}
