package view;

import interfaces.Persistencia;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import models.Inventario;
import services.GestionProducto;
import models.Producto;
import services.*;

public class ProductoController {
    private GestionProducto gestionProducto;

    public ProductoController(GestionProducto gestionProducto) {
        this.gestionProducto = gestionProducto;
    }
    
    public Inventario getInventario() {
        return gestionProducto.getInventario();
    }

    public void importar(File archivo) {
        String extension = archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1);

        Persistencia<? extends Producto> service = (extension.equals("csv")) ? new CSVService(archivo.getPath()) :
                                                (extension.equals("json")) ? new JSONService(archivo.getPath()) :
                                                (extension.equals("dat")) ? new DATService(archivo.getPath()) :
                                                null;

        if (service != null) {
            List<? extends Producto> productos = service.cargar(); // wildcard 
            for (Producto producto : productos) {
                gestionProducto.crear(producto);
            }
        } else {
            throw new IllegalArgumentException("Archivo no soportado");
        }
    }

    public void exportar(String extension) {
        Persistencia<Producto> service = (extension.equals("csv")) ? new CSVService("inventario.csv") :
                                        (extension.equals("json")) ? new JSONService("inventario.json") :
                                        (extension.equals("dat")) ? new DATService("inventario.dat") :
                                        null;

        if (service != null) {
            service.guardar(gestionProducto.getInventario().getListaProductos());
        } else {
            throw new IllegalArgumentException("Formato de exportación no soportado");
        }
    }

    //Agrega un producto al inventario
    public void agregar(Producto producto){
        gestionProducto.crear(producto);
    }
    
    public void actualizar(int id, Producto producto){
        gestionProducto.actualizar(id, producto);
    }
    
    public void eliminar (int id){
        gestionProducto.eliminar(id);
    }
    
    //Ordena los productos del inventario por un criterio dado.
    public void ordenar (String criterio){
        switch (criterio){
            case "Precio" -> gestionProducto.ordenarPorPrecio();
            case "Stock" -> gestionProducto.ordenarPorStock();
            case "Nombre" -> gestionProducto.ordenarPorNombre();
            default -> throw new IllegalArgumentException("Criterio de ordenación no válido");
        }
    }
    
    //Reinicia el filtro y ordenamiento del inventario
    public void reiniciar(){
        gestionProducto.reiniciar();
    }
    
    //Filtra productos del inventario por texto
    //@param texto El texto por el que se filtrarán los productos.
    public List<Producto> filtrar (String texto){
        return gestionProducto.filtrar (texto);
    }

    public void exportarTXT() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventario de Productos\n");
        sb.append("=======================\n");
        for (Producto p : gestionProducto.getInventario().getListaProductos()) {
            sb.append(p.toString()).append("-----------------------\n");
        }
        File archivo = new File("inventario.txt");
        try (PrintWriter writer = new PrintWriter(archivo)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}