package models.producto;

import models.enums.Categoria;
import interfaces.Exportable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;
import models.Producto;

public class Pileta extends Producto implements Exportable, Serializable{
    private int capacidad;
    private String forma;
    
     /* 
     * @brief Constructor que recibe un Map con los datos de la pileta.
     * @param data Map con las claves: "id", "nombre", "precio", "stock", "capacidad", "forma".
     * @throws NumberFormatException si los valores numéricos no pueden ser parseados.
     * @throws NullPointerException si alguna clave requerida no está presente en el Map.
    */
    public Pileta(Map<String, String> data) {
        this(
            Integer.parseInt(data.get("id")),
            data.get("nombre"),
            Double.parseDouble(data.get("precio")),
            Integer.parseInt(data.get("stock")),
            Integer.parseInt(data.get("capacidad")),
            data.get("forma")
        );
    }
    
    //el encadenamiento de constructores por this(),  no reciben todos los parametros yo los inventos 
    /*
        como el usario no te dio stock, supongo que hay 1, lo mismo con la capacidad y la forma
    esto sirve parea que si alguien crea una pileta lo pueda hacer con pocos datos, los basicos
    */
    public Pileta(int id, String nombre, double precio){
        this(id, nombre, precio, 1, 1000, "Rectangular");
    }
    
    public Pileta(int id, String nombre, double precio, int capacidad, String forma) {
        this(id, nombre, precio, 1, capacidad, forma);
    }

    public Pileta(int id, String nombre, double precio, int stock, int capacidad, String forma) {
        super(id, nombre, precio, Categoria.PILETAS, stock);
        this.capacidad = capacidad;
        this.forma = forma;
    }
    
    public int getCapacidad(){
        return capacidad;
    }
    
    public String getForma(){
        return forma;
    }
    
    @Override
    public String getTXT() {
        return "ID: " + getId() + "\n" +
               "Nombre: " + getNombre() + "\n" +
               "Precio: " + getPrecio() + "\n" +
               "Categoría: " + getCategoria() + "\n" +
               "Stock: " + getStock() + "\n" +
               "Capacidad: " + capacidad + "\n" +
               "Forma: " + forma;
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
               capacidad      + "," +
               forma;
    }

    @Override
    public String getJSON() {
        return "{\n" +
               "  \"id\": "          + getId()        + ",\n"   +
               "  \"nombre\": \""    + getNombre()    + "\",\n" +
               "  \"precio\": "      + getPrecio()    + ",\n"   +
               "  \"categoria\": \"" + getCategoria() + "\",\n" +
               "  \"stock\": "       + getStock()     + ",\n"   +
               "  \"capacidad\": "   + capacidad      + ",\n"   +
               "  \"forma\": \""     + forma          + "\"\n"  +
               "}";
    }
    
}
