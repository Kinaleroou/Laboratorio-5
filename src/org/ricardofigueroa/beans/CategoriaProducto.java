package org.ricardofigueroa.beans;

/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
public class CategoriaProducto {
    private String NombreCategoria;
    private String DescripcionCategoria;
    private int CategoriaProductosId;

    public CategoriaProducto() {
    }

    public CategoriaProducto(String NombreCategoria, String DescripcionCategoria, int CategoriaProductosId) {
        this.NombreCategoria = NombreCategoria;
        this.DescripcionCategoria = DescripcionCategoria;
        this.CategoriaProductosId = CategoriaProductosId;
    }

    public String getNombreCategoria() {
        return NombreCategoria;
    }

    public void setNombreCategoria(String NombreCategoria) {
        this.NombreCategoria = NombreCategoria;
    }

    public String getDescripcionCategoria() {
        return DescripcionCategoria;
    }

    public void setDescripcionCategoria(String DescripcionCategoria) {
        this.DescripcionCategoria = DescripcionCategoria;
    }

    public int getCategoriaProductosId() {
        return CategoriaProductosId;
    }

    public void setCategoriaProductosId(int CategoriaProductosId) {
        this.CategoriaProductosId = CategoriaProductosId;
    }

    

    @Override
    public String toString() {
        return getNombreCategoria() + " | " + getDescripcionCategoria() ;
    }
    
    
    
    
    
}
