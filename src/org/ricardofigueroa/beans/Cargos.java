package org.ricardofigueroa.beans;


/**
 * Nombre: Ricardo Figueroa Fecha de creacion: 11/04/2024 
 * Ultmia Fecha de edicion : 26/04/2024
 *
 */
public class Cargos {
    
    private int cargoId;
    private String nombreCargo, descripcionCargo;

    public Cargos() {
    }

    public Cargos(int cargoId, String nombreCargo, String descripcionCargo) {
        this.cargoId = cargoId;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
    
    
    
}
