package models;

import comparators.IdComparator;
import interfaces.Crud;
import interfaces.Exportable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

import java.util.stream.Collectors;

public class Inventario implements Crud, Iterable<Producto>, Exportable{
    private List<Producto> productos = new ArrayList<>();
    
    public List<Producto> getListaProductos(){
        return productos;
    }
    
    //_____________CRUD: 
    @Override
    public void crear(Producto producto){
        ordenarPorId();
        productos.add(producto);
    }
    
    @Override
    public Producto leer(int id){
        ordenarPorId();
        return productos.get(id);
    }
    
    @Override
    public void actualizar(int id, Producto producto){
        productos.set(id, producto);
        ordenarPorId();
    }
    
    @Override
    public void eliminar(int id){
        productos.remove(id);
        ordenarPorId();
    }
    
    /* 
     * @brief Método para filtrar los productos en el inventario por nombre.
     * @param nombre - El nombre o parte del nombre del producto a filtrar.
     * @return List<Producto> - Una lista de productos que coinciden con el filtro de nombre.
    */
    public List<Producto> filtrar(String nombre){
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList()); 
    }
    
    /* 
     * @brief Método para ordenar los productos en el inventario utilizando un comparador personalizado.
     * @param comparator - El comparador que define el criterio de ordenamiento para los productos.
     * Este método ordena la lista de productos en el inventario según el criterio definido por el comparador proporcionado.
    */
    public void ordenar(Comparator<Producto> comparator){
        Collections.sort(productos, comparator);
    }
    
    public void reiniciar(){
        Collections.sort(productos, new IdComparator());
    }
    
    @Override
    public Iterator<Producto> iterator(){
        return productos.iterator();
    }
    
    @Override
    public void exportarTXT(String path){
        StringBuilder sb = new StringBuilder();
        for (Producto producto : productos){
            sb.append(producto.getTXT());
            sb.append("\n\n");
        }
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File(path))){
            writer.println(sb.toString());
        }catch (java.io.IOException e){
            e.printStackTrace();
        }        
    }
    //cual es el siguiente id que falta en la lista 
    public int getNextID(){
        this.ordenarPorId();
        for(int i = 0; i < productos.size(); i++){
            if (productos.get(i).getId() != i+1 ){
                return i+1;
            }
        }
        return productos.size() + 1;
    }
    
    private void ordenarPorId(){
        Collections.sort(productos, new IdComparator());
    }
    
}
