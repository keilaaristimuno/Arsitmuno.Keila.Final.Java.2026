package view;

import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;

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
        
        TableColumn<Producto, Integer> tableRowId = new TableColumn<>("ID");
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoria");
        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        
        tableRowId.setCellValueFactory(cd -> new SimpleIntegerProperty(tabla.getItems().indexOf(cd.getValue()) +1).asObject());
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tabla.getColumns().addAll(tableRowId, colNombre, colPrecio, colCategoria, colStock);
        //las columnas ocupan el ancho disponible
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
    public static void cargarTableView(TableView<Producto> tableView, Inventario inv, ProductoController controller) {
        refreshTableView(tableView, inv);

        tableView.setRowFactory(tv -> {
            TableRow<Producto> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1 && !row.isEmpty()) {
                    Producto p = row.getItem();
                    mostrarTarjeta(p, tableView, controller);
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
        tableView.getItems().setAll(inv.getListaProductos());
    }

    /* 
     * @brief Muestra una tarjeta con los detalles del producto seleccionado.
     * @param p El producto cuyos detalles se mostrarán.
    */
    private static void mostrarTarjeta(Producto p, TableView<Producto> tableView, ProductoController controller) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles");
        alert.setHeaderText(p.getNombre());
        
        ButtonType editarBtn = new ButtonType("✏ Editar", ButtonBar.ButtonData.OK_DONE);
        ButtonType eliminarBtn = new ButtonType("🗑 Eliminar", ButtonBar.ButtonData.OTHER);
        alert.getButtonTypes().setAll(editarBtn, eliminarBtn, ButtonType.CLOSE);

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

    }
        alert.setContentText(contenido);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == editarBtn) {
                Producto editado = ProductoDialogs.editarProducto(p);
                
                if(editado != null){
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Confirmar Edición");
                    confirm.setHeaderText("¿Desea guardar los cambios?");
                    confirm.setContentText("El producto: " + p.getNombre() + " va a ser editado.  \n ¿Quiere continuar?");
                    confirm.setResizable(true);
                    confirm.getDialogPane().setPrefWidth(500);
                    if(confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK){
                        controller.actualizar(p.getId(), editado);
                        tableView.refresh();
                    }
                }
            } else if (btn == eliminarBtn) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmar Eliminación");
                confirm.setHeaderText("¿Desea eliminar este producto?");
                confirm.setContentText("El producto: " + p.getNombre()+ " va a ser eliminado.  \n ¿Esta seguro?");
                confirm.setResizable(true);
                confirm.getDialogPane().setPrefWidth(500);
                if(confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK){
                    controller.eliminar(p.getId());
                    tableView.getItems().remove(p);
                    tableView.refresh();
                }
            }
        });
    }
}
