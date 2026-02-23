package view;

import java.util.List;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;

import models.*;
import models.enums.Categoria;
import models.producto.*;

public class ProductoViewHelper {

    /* 
     * @brief Crea una tabla con columnas para mostrar los productos.
     * @return La tabla creada con las columnas configuradas.
    */
    public static TableView<Producto> crearTabla(){
        TableView<Producto> tabla = new TableView<>();
        
        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoria");
        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tabla.getColumns().addAll(colId, colNombre, colPrecio, colCategoria, colStock);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return tabla;
    }

    /* 
     * @brief Refresca la tabla con una lista de productos filtrados.
     * @param tableView La tabla a refrescar.
     * @param productosFiltrados La lista de productos filtrados.
    */
    public static void refreshTableViewWithFiltered(TableView<Producto> tableView, List<Producto> productosFiltrados) {
        tableView.setItems(FXCollections.observableArrayList(productosFiltrados));
    }

    /* 
     * @brief Carga los productos en la tabla desde el inventario.
     * @param tableView La tabla donde se cargarán los productos.
     * @param inv El inventario del cual se obtendrán los productos.
    */
    public static void cargarTableView(TableView<Producto> tableView, Inventario inv) {
        refreshTableView(tableView, inv);

        tableView.setRowFactory(tv -> {
            TableRow<Producto> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1 && !row.isEmpty()) {
                    Producto p = row.getItem();
                    mostrarTarjeta(p);
                }
            });
            return row;
        });
    }

    /* 
     * @brief Refresca la tabla con los productos del inventario.
     * @param tableView La tabla a refrescar.
     * @param inv El inventario del cual se obtendrán los productos.
    */
    public static void refreshTableView(TableView<Producto> tableView, Inventario inv) {
        tableView.setItems(FXCollections.observableArrayList(inv.getListaProductos()));
    }

    /* 
     * @brief Muestra una tarjeta con los detalles del producto seleccionado.
     * @param p El producto cuyos detalles se mostrarán.
    */
    private static void mostrarTarjeta(Producto p) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles");
        alert.setHeaderText(p.getNombre());

        String contenido = """
                ID: %d
                Precio: $%.2f
                Categoria: %s
                Stock: %d
                """.formatted(p.getId(), p.getPrecio(), p.getCategoria(), p.getStock());
        
        if(p.getCategoria() == Categoria.PILETAS){
            Pileta pileta = (Pileta) p;
            contenido += """
                    
                    Capacidad: %d cm
                    Material: %s
                    """.formatted(pileta.getCapacidad(), pileta.getForma());
        } else if(p.getCategoria() == Categoria.PINTURAS){
            Pintura pintura = (Pintura) p;
            contenido += """
                    
                    Color: %s
                    Litros: %d L
                    """.formatted(pintura.getColor(), pintura.getLitros());
        } else if(p.getCategoria() == Categoria.BALDOSAS){
            Baldosa baldosa = (Baldosa) p;
            contenido += """
                    
                    Material: %s
                    Medida: %.2f m²
                    """.formatted(baldosa.getMaterial(), baldosa.getMedida());

        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
  
}
