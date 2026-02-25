package models.producto;

import models.enums.Categoria;
import interfaces.Exportable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;
import models.Producto;

public class Pintura extends Producto implements Exportable, Serializable  {
    private String color;
    private int litros;
    
    /* 
     * @brief Constructor que recibe un Map con los datos de la pintura.
     * @param data Map con las claves: "id", "nombre", "precio", "stock", "color", "litros".
     * @throws NumberFormatException si los valores numéricos no pueden ser parseados.
     * @throws NullPointerException si alguna clave requerida no está presente en el Map.
    */
    public Pintura(Map<String, String> data) {
        this(
            Integer.parseInt(data.get("id")),
            data.get("nombre"),
            Double.parseDouble(data.get("precio")),
            Integer.parseInt(data.get("stock")),
            data.get("color"),
            Integer.parseInt(data.get("litros"))
        );
    }
    
    public Pintura(int id, String nombre, double precio){
        this(id, nombre, precio, 1, "Blanco", 1);
    }
    
    public Pintura(int id, String nombre, double precio, String color, int litros) {
        this(id, nombre, precio, 1, color, litros);
    }

    public Pintura(int id, String nombre, double precio, int stock, String color, int litros) {
        super(id, nombre, precio, Categoria.PINTURAS, stock);
        this.color = color;
        this.litros = litros;
    }

    public String getColor() {
        return color;
    }

    public int getLitros() {
        return litros;
    }
    
    @Override
    public String getTXT() {
        return "ID: " + getId() + "\n" +
               "Nombre: " + getNombre() + "\n" +
               "Precio: " + getPrecio() + "\n" +
               "Categoría: " + getCategoria() + "\n" +
               "Stock: " + getStock() + "\n" +
               "Color: " + color + "\n" +
               "Litros: " + litros;
    }

    @Override
    public void exportarTXT(String path) {
        try (PrintWriter writer = new PrintWriter(new File(path))) {
            writer.println(getTXT());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCSV() {
        return getId()        + "," +
               getNombre()    + "," +
               getPrecio()    + "," +
               getCategoria() + "," +
               getStock()     + "," +
               color          + "," +
               litros;
    }

    @Override
    public String getJSON() {
        return "{" +
               "\"id\": "          + getId()        + ","   +
               "\"nombre\": \""    + getNombre()    + "\"," +
               "\"precio\": "      + getPrecio()    + ","   +
               "\"categoria\": \"" + getCategoria() + "\"," +
               "\"stock\": "       + getStock()     + ","   +
               "\"color\": \""     + color          + "\"," +
               "\"litros\": "      + litros         +
               "}";
    }   
}
