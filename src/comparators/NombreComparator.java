
package comparators;

import java.util.Comparator;
import models.Producto;

/* 
 Ordenar productos por nombre.
*/
public class NombreComparator implements Comparator<Producto> {
    @Override

    public int compare(Producto p1, Producto p2) {
        return p1.getNombre().compareToIgnoreCase(p2.getNombre());
    }
}
