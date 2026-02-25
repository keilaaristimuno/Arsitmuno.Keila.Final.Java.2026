package interfaces;

import java.util.List;

/*
Como es una interface, no dice cómo se guardan los archivos, sino qué
acciones son obligatorias para cualquier clase que la implemente.
*/
public interface Persistencia <T> {

    /* 
     * @brief Guarda una lista de productos en un medio de almacenamiento persistente.
     * @param lista La lista de productos a guardar.
    */
    void guardar(List<T>lista);
    /* 
     * @brief Carga una lista de productos desde un medio de almacenamiento persistente.
     * @return La lista de productos cargada.
    */
    List<T>cargar();  
}
