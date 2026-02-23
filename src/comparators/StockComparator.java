
package comparators;

import java.util.Comparator;
import models.Producto;
/* 
 * Clase que compara dos productos por su stock. Se utiliza para ordenar los productos de menor a mayor stock. 
*/
public class StockComparator implements Comparator<Producto>  {
    @Override
    public int compare(Producto p1, Producto p2){
        return Integer.compare(p1.getStock(), p2.getStock());
    }
}
