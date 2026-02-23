package view;

import java.util.List;
import services.GestionProducto;
import models.Producto;

public class ProductoController {
    private GestionProducto gestionProducto;

    public ProductoController(GestionProducto gestionProducto) {
        this.gestionProducto = gestionProducto;
    }
    
    //Agrega un producto al inventario
    public void agregar(Producto producto){
        gestionProducto.crear(producto);
    }
    
    public void eliminar (int id){
        gestionProducto.eliminar(id);
    }
    
    //Ordena los productos del inventario por un criterio dado.
    public void ordenar (String criterio){
        switch (criterio){
            case "Precio":
                gestionProducto.ordenarPorPrecio();
                break;
            case "Stock":
                gestionProducto.ordenarPorStock();
                break;
            case "Nombre":
                gestionProducto.ordenarPorNombre();
                break;
            default:
                throw new IllegalArgumentException("Criterio de ordenación no válido");
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
}
