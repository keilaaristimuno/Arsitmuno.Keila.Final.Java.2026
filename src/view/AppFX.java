package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import models.*;
import models.producto.Baldosa;
import services.GestionProducto;

public class AppFX extends Application {

    private ProductoController controller;
    private TableView<Producto> tableView = ProductoViewHelper.crearTabla();

    private Inventario crearInventarioConDatos() {
        Inventario inventario = new Inventario();

        inventario.crear(new Baldosa(inventario.getNextID(), "Borde ballena", 7500, 23, "Atermico", 40));
        inventario.crear(new Baldosa(inventario.getNextID(), "Esquinero", 9500, 32, "Atermico", 60));
        inventario.crear(new Baldosa(inventario.getNextID(), "Solarium", 6250, 1, "Atermico", 40));
        inventario.crear(new Baldosa(inventario.getNextID(), "Rejilla", 2650, 61, "Atermico", 35));
        inventario.crear(new Baldosa(inventario.getNextID(), "Deck", 10250, 24, "Cemento", 15));
        inventario.crear(new Baldosa(inventario.getNextID(), "Borde L", 8750, 54, "Atermico", 35));

        return inventario;
    }


    private String exportarDialog(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("CSV","CSV", "JSON", "TXT");
        dialog.setTitle("Exportar");
        dialog.setHeaderText("Seleccionar formato");
        dialog.showAndWait();
        return dialog.getSelectedItem();
    }

    private File importarDialog(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar Archivo");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Archivos JSON", "*.json")
        );

        File archivo = fileChooser.showOpenDialog(stage);

        if (archivo != null){
            String nombre = archivo.getName().toLowerCase();

            if (nombre.endsWith(".csv") || nombre.endsWith(".json")){
                return archivo;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de formato");
                alert.setHeaderText("Archivo no soportado");
                alert.setContentText("Por favor, selecciona un archivo CSV, JSON o DAT.");
                alert.showAndWait();
                return null;
            }
        }

        return null;
    }
    
   private HBox crearBarraExportar(Stage stage){
        Button btnExportar = new Button("Exportar ");
        Button btnImportar = new Button("Importar");
        
        btnExportar.setOnAction( e -> {
            String formato = exportarDialog();
            if (formato != null) {
                if(formato == "TXT"){
                    controller.exportarTXT();
                } else {
                    controller.exportar(formato.toLowerCase());
                }
            }
        });

        btnImportar.setOnAction( e -> {
            File file = importarDialog(stage);
            if (file != null) {
                controller.importar(file);
                
            } else {
                System.out.println("No se seleccionó ningún archivo");
            }
        });
   
        HBox bottom = new HBox(10, btnExportar, btnImportar );
        bottom.setStyle("-fx-padding: 10;");
        bottom.setAlignment(javafx.geometry.Pos.CENTER);
        return bottom;
   }
    
    //metodo para crear una barra de botones
    private HBox crearBarraBotones(
            TableView<Producto> tableView
    ) {
        Button btnAgregar = new Button("Agregar");
        Button btnOrdenar = new Button("Ordenar");
        Button btnFiltrar = new Button("Filtrar");
        Button btnReiniciar = new Button("Reiniciar");
        
        configurarEventos(tableView, btnAgregar, btnOrdenar, btnFiltrar, btnReiniciar);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return new HBox(10,
                btnAgregar,
                btnOrdenar,
                btnFiltrar,
                spacer,
                btnReiniciar
        );
    }

//metodo para configurar los eventos de los botones 
    private void configurarEventos(
            TableView<Producto> tableView,
            Button btnAgregar,
            Button btnOrdenar,
            Button btnFiltrar,
            Button btnReiniciar
    ) {
        btnAgregar.setOnAction(e -> {
            Producto p = ProductoDialogs.dialogoCrearProducto(controller.getInventario());
            if (p != null) {
                controller.agregar(p);
                ProductoViewHelper.refreshTableView(tableView, controller.getInventario());
            }
        });

        btnOrdenar.setOnAction(e -> {
            String criterio = ProductoDialogs.dialogoSeleccionarCriterio();
            if (criterio != null) {
                controller.ordenar(criterio);
                ProductoViewHelper.refreshTableView(tableView, controller.getInventario());
            }
        });

        btnFiltrar.setOnAction(e -> {
            String filtro = ProductoDialogs.dialogoIngresarFiltro();
            if (filtro != null) {
                var lista = controller.filtrar(filtro);
                ProductoViewHelper.refreshTableViewWithFiltered(tableView, lista);
            }
        });

        btnReiniciar.setOnAction(e -> {
            controller.reiniciar();
            ProductoViewHelper.refreshTableView(tableView, controller.getInventario());
        });

    }

    /* 
     * @brief Método para crear el layout principal de la aplicación.
     * @param top La barra superior con los botones.
     * @param tableView La tabla que muestra los productos.
     * @return VBox El contenedor principal de la aplicación.
     */
    private VBox crearLayout(HBox top, TableView<Producto> tableView, Stage stage) {
        HBox bottom = crearBarraExportar(stage);
        VBox root = new VBox(top, tableView, bottom);
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
        GestionProducto gestionProducto = new GestionProducto(inventario);
        controller = new ProductoController(gestionProducto);

        TableView<Producto> tableView = ProductoViewHelper.crearTabla();
        ProductoViewHelper.cargarTableView(tableView, inventario, controller);

        HBox top = crearBarraBotones(tableView);
        VBox root = crearLayout(top, tableView, stage);

        configurarStage(stage, root);
    }
}