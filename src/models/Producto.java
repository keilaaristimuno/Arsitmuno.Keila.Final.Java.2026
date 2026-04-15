
package models;

import models.enums.Categoria;

public abstract class Producto implements Comparable <Producto> {
    protected int id;
    protected String nombre;
    protected double precio;
    protected Categoria categoria;
    protected int stock;
         
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Precio: ").append(precio).append("\n");
        sb.append("Categoría: ").append(categoria).append("\n");
        sb.append("Stock: ").append(stock).append("\n");
        return sb.toString();
    }
    
    public abstract String getTXT();
    public abstract String getCSV();
    public abstract String getJSON();

}
