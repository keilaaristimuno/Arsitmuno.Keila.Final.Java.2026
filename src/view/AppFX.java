package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import models.*;
import models.producto.Baldosa;
import services.GestionProducto;

public class AppFX extends Application {

    private TableView<Producto> tableView = ProductoViewHelper.crearTabla();

    private Inventario crearInventarioConDatos() {
        Inventario inventario = new Inventario();

        inventario.crear(new Baldosa(inventario.getNextID(), "rgsedfaswef", 111, 23, "111", 111));
        inventario.crear(new Baldosa(inventario.getNextID(), "wadebtabwer", 666, 32, "666", 666));
        inventario.crear(new Baldosa(inventario.getNextID(), "wdrbaerb", 444, 1, "444", 444));
        inventario.crear(new Baldosa(inventario.getNextID(), "wdasdvw", 333, 61, "333", 333));
        inventario.crear(new Baldosa(inventario.getNextID(), "aasdas", 222, 24, "222", 222));
        inventario.crear(new Baldosa(inventario.getNextID(), "sdsda", 555, 54, "555", 555));

        return inventario;
    }

    //Crea un controlador de productos
    private ProductoController crearController(Inventario inventario) {
        GestionProducto gestion = new GestionProducto(inventario);
        return new ProductoController(gestion);
    }
/*
    private HBox crearBarraPersistencia(ProductoController controller){
        Button btnExportarJSON = new Button("Exportar JSON");
        Button btnExportarCSV = new Button("Exportar CSV");
        Button btnExportarDAT = new Button("Exportar DAT");
        
        btnExportarJSON.setOnAction( e -> 
        .)
        
    }
    
    */

    //metodo para crear una barra de botones
    private HBox crearBarraBotones(
            ProductoController controller,
            Inventario inventario,
            TableView<Producto> tableView
    ) {
        Button btnAgregar = new Button("Agregar");
        Button btnOrdenar = new Button("Ordenar");
        Button btnFiltrar = new Button("Filtrar");
        Button btnEliminar = new Button("Eliminar");
        Button btnReiniciar = new Button("Reiniciar");
        
        configurarEventos(controller, inventario, tableView,
                btnAgregar, btnOrdenar, btnFiltrar, btnEliminar, btnReiniciar);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return new HBox(10,
                btnAgregar,
                btnOrdenar,
                btnFiltrar,
                btnEliminar,
                spacer,
                btnReiniciar
        );
    }

//metodo para configurar los eventos de los botones 
    private void configurarEventos(
            ProductoController controller,
            Inventario inventario,
            TableView<Producto> tableView,
            Button btnAgregar,
            Button btnOrdenar,
            Button btnFiltrar,
            Button btnEliminar,
            Button btnReiniciar
    ) {
        btnAgregar.setOnAction(e -> {
            Producto p = ProductoDialogs.dialogoCrearProducto(inventario);
            if (p != null) {
                controller.agregar(p);
                ProductoViewHelper.refreshTableView(tableView, inventario);
            }
        });

        btnOrdenar.setOnAction(e -> {
            String criterio = ProductoDialogs.dialogoSeleccionarCriterio();
            if (criterio != null) {
                controller.ordenar(criterio);
                ProductoViewHelper.refreshTableView(tableView, inventario);
            }
        });

        btnFiltrar.setOnAction(e -> {
            String filtro = ProductoDialogs.dialogoIngresarFiltro();
            if (filtro != null) {
                var lista = controller.filtrar(filtro);
                ProductoViewHelper.refreshTableViewWithFiltered(tableView, lista);
            }
        });
        btnEliminar.setOnAction(e -> {
            int id = ProductoDialogs.dialogoIngresarId();
            if (id != -1) {
                controller.eliminar(id - 1);
                ProductoViewHelper.refreshTableView(tableView, inventario);
            }
        });

        btnReiniciar.setOnAction(e -> {
            controller.reiniciar();
            ProductoViewHelper.refreshTableView(tableView, inventario);
        });

    }

    /* 
     * @brief Método para crear el layout principal de la aplicación.
     * @param top La barra superior con los botones.
     * @param tableView La tabla que muestra los productos.
     * @return VBox El contenedor principal de la aplicación.
     */
    private VBox crearLayout(HBox top, TableView<Producto> tableView) {
        VBox root = new VBox(top, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        return root;
    }

    /* 
     * @brief Método para configurar el stage principal de la aplicación.
     * @param stage El stage principal de la aplicación.
     * @param root El contenedor principal de la aplicación.
     */
    private void configurarStage(Stage stage, VBox root) {
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Gestión de Productos");
        stage.show();
    }

    @Override
    public void start(Stage stage) {

        Inventario inventario = crearInventarioConDatos();
        ProductoController controller = crearController(inventario);

        TableView<Producto> tableView = ProductoViewHelper.crearTabla();
        ProductoViewHelper.cargarTableView(tableView, inventario);

        HBox top = crearBarraBotones(controller, inventario, tableView);
        VBox root = crearLayout(top, tableView);

        configurarStage(stage, root);
    }
}
