package services;

import comparators.NombreComparator;
import comparators.PrecioComparator;
import comparators.StockComparator;
import exceptions.ProductoNoEncontradoException;
import exceptions.StockInvalidoException;
import java.util.List;
import models.Inventario;
import models.Producto;
import java.util.Comparator;

public class GestionProducto {
    private Inventario inventario;

    public GestionProducto(Inventario inventario) {
        this.inventario = inventario;
    }
    //Crea un producto en el inventario. 
    /*@param producto El producto a crear.
     * @throws StockInvalidoException si el stock del producto es negativo.*/
    public void crear (Producto producto){
        if(producto.getStock() < 0 ) throw new StockInvalidoException();
        inventario.crear(producto);
    }
    
     /* 
     * @brief Lee un producto del inventario.
     * @param id El ID del producto a leer.
     * @return El producto leído.
     * @throws ProductoNoEncontradoException si no se encuentra el producto con el ID dado.
    */
    public Producto leer(int id){
        Producto producto = inventario.leer(id);
        if(producto == null) throw new ProductoNoEncontradoException();
        return producto;
    }
    
     /* 
     * @brief Actualiza un producto en el inventario.
     * @param id El ID del producto a actualizar.
     * @param producto El nuevo producto con los datos actualizados.
     * @throws ProductoNoEncontradoException si no se encuentra el producto con el ID dado.
     * @throws StockInvalidoException si el stock del nuevo producto es negativo.
    */
    public void actualizar(int id, Producto producto){
        if(inventario.leer(id) == null) throw new ProductoNoEncontradoException();
        if(producto.getStock() < 0) throw new StockInvalidoException();
        inventario.actualizar(id, producto);
    }
    
    public void eliminar (int id){
        if(inventario.leer(id) == null) throw new ProductoNoEncontradoException();
        inventario.eliminar(id);
    }
    
    public List<Producto> filtrar(String texto){
        return inventario.filtrar(texto);
    }
    
    public void reiniciar(){
        inventario.reiniciar();
    }
    
    //ordena los productos del inventario por precio
    public void ordenarPorPrecio(boolean asc){
        Comparator <Producto> c = Comparator.comparingDouble(Producto :: getPrecio);
       inventario.ordenar(asc ? c : c.reversed ());
    }
    
    public void ordenarPorStock(boolean asc){
        Comparator<Producto> c = Comparator.comparingInt(Producto::getStock);
        inventario.ordenar(asc ? c : c.reversed());
    }
    
    public void ordenarPorNombre(boolean asc){
        Comparator<Producto> c = Comparator.comparing(Producto::getNombre);
        inventario.ordenar(asc ? c : c.reversed());
    }

    public Inventario getInventario() {
        return inventario;
    }  
}
