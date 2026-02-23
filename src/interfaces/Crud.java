
package interfaces;

import  models.Producto;

public interface Crud {
    void crear(Producto item);
    Producto leer(int id);
    void actualizar(int id, Producto item);
    void eliminar(int id);
}
