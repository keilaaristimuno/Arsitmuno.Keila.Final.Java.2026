package models.producto;

import models.enums.Categoria;
import interfaces.Exportable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;
import models.Producto;

public class Baldosa extends Producto implements Exportable, Serializable {
    private String material;
    private double medida;

    /* 
     * @brief Constructor que recibe un Map con los datos de la baldosa.
     * @param data Map con los datos de la baldosa, con las siguientes claves:
     * "id", "nombre", "precio", "stock", "material", "medida".
     * @throws NumberFormatException si los valores de "id", "precio", "stock" o "medida" no son números válidos.
     * @throws NullPointerException si alguna de las claves no está presente en el Map.
    */
    public Baldosa(Map<String, String> data) {
        this(
            Integer.parseInt(data.get("id")),
            data.get("nombre"),
            Double.parseDouble(data.get("precio")),
            Integer.parseInt(data.get("stock")),
            data.get("material"),
            Double.parseDouble(data.get("medida"))
        );
    }
    
    public Baldosa(int id, String nombre, double precio){
        this(id, nombre, precio, 1, "Ceramica", 1.0);
    }
    
    public Baldosa(int id, String nombre, double precio, String material, double medida) {
        this(id, nombre, precio, 1, material, medida);
    }
    
    public Baldosa(int id, String nombre, double precio, int stock, String material, double medida) {
        super(id, nombre, precio, Categoria.BALDOSAS, stock);
        this.material = material;
        this.medida = medida;
    }
    
    public String getMaterial() {
        return material;
    }

    public double getMedida() {
        return medida;
    }
    
    public void  setMaterial(String material){
        this.material = material;
    }
    
    public void setMedida (double medida){
        this.medida = medida;
    }
    
    @Override
    public String getTXT() {
        return "ID: " + getId() + "\n" +
               "Nombre: " + getNombre() + "\n" +
               "Precio: " + getPrecio() + "\n" +
               "Categoría: " + getCategoria() + "\n" +
               "Stock: " + getStock() + "\n" +
               "Material: " + material + "\n" +
               "Medida: " + medida;
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
               getCategoria() + "," +
               getNombre()    + "," +
               getPrecio()    + "," +
               getStock()     + "," +
               material       + "," +
               medida;
    }

    @Override
    public String getJSON() {
        return "{" +
               "\"id\": "          + getId()        + ","   +
               "\"nombre\": \""    + getNombre()    + "\"," +
               "\"precio\": "      + getPrecio()    + ","   +
               "\"categoria\": \"" + getCategoria() + "\"," +
               "\"stock\": "       + getStock()     + ","   +
               "\"material\": \""  + material       + "\"," +
               "\"medida\": "      + medida         +
               "}";
    }
}
