
package models;

import models.enums.Categoria;

public abstract class Producto implements Comparable <Producto> {
    protected int id;
    protected String nombre;
    protected double precio;
    protected Categoria categoria;
    protected int stock;
    
    //constructor:
     
    public Producto (int id, String nombre, double precio, Categoria categoria, int stock ){
        this.id= id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    //Compara el id con otro 
    @Override
    public int compareTo(Producto otro){
        return Integer.compare(this.getId(),otro.getId());
    }
    
    /*especificadores de formato.
    %d (Digit), %s (String), %.2f (Float/Double)
    */
    @Override
    public String toString() {
        return String.format("%d | %s | %.2f | %s | %d", id, nombre, precio, categoria, stock);
    }
    
    public abstract String getTXT();
    public abstract String getCSV();
    public abstract String getJSON();
    
    
}
