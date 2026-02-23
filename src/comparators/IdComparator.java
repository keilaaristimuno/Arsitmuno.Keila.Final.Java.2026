package comparators;

//Ordena productos por ID
import models.Producto;
import java.util.Comparator;

public class IdComparator implements Comparator<Producto>{
    @Override
    public int compare(Producto p1, Producto p2 ){
        return Integer.compare(p1.getId(), p2.getId());
    }
}
