package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.*;
import models.producto.*;
import models.enums.Categoria;

public class ProductoDialogs {
    
     public static Producto editarProducto(Producto p) {
        Dialog<Producto> dialog = crearDialog();

        ButtonType okType = dialog.getDialogPane().getButtonTypes().stream()
                .filter(bt -> bt.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                .findFirst().orElse(null);

        ChoiceBox<String> tipo = crearChoiceTipo();
        TextField nombre = new TextField(p.getNombre());
        TextField precio = new TextField(String.valueOf(p.getPrecio()));
        TextField stock = new TextField(String.valueOf(p.getStock()));
        TextField extra1 = new TextField(p.getCategoria() == Categoria.PILETAS ? String.valueOf(((Pileta) p).getCapacidad()) :
                p.getCategoria() == Categoria.PINTURAS ? ((Pintura) p).getColor() :
                        ((Baldosa) p).getMaterial());

        TextField extra2 = new TextField(p.getCategoria() == Categoria.PILETAS ? String.valueOf(((Pileta) p).getForma()) :
                p.getCategoria() == Categoria.PINTURAS ? String.valueOf(((Pintura) p).getLitros()) :
                        String.valueOf(((Baldosa) p).getMedida()));

        Label l1 = new Label(p.getCategoria() == Categoria.PILETAS ? "Capacidad" :
                p.getCategoria() == Categoria.PINTURAS ? "Color" : "Material");

        Label l2 = new Label(p.getCategoria() == Categoria.PILETAS ? "Forma" :
                p.getCategoria() == Categoria.PINTURAS ? "Litros" : "Medida");

        tipo.setValue(p.getClass().getSimpleName());

        configurarCambioTipo(tipo, l1, l2);

        GridPane grid = crearGrid(tipo, nombre, precio, stock, l1, extra1, l2, extra2);
        dialog.getDialogPane().setContent(grid);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okType);

        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                p.setNombre(nombre.getText());
                p.setPrecio(Double.parseDouble(precio.getText()));
                p.setStock(Integer.parseInt(stock.getText()));

                if (p.getCategoria() == Categoria.PILETAS) {
                    Pileta pl = (Pileta) p;
                    pl.setCapacidad(Integer.parseInt(extra1.getText()));
                    pl.setForma(extra2.getText());
                } else if (p.getCategoria() == Categoria.PINTURAS) {
                    Pintura pt = (Pintura) p;
                    pt.setColor(extra1.getText());
                    pt.setLitros(Integer.parseInt(extra2.getText()));
                } else if (p.getCategoria() == Categoria.BALDOSAS) {
                    Baldosa bd = (Baldosa) p;
                    bd.setMaterial(extra1.getText());
                    bd.setMedida(Double.parseDouble(extra2.getText()));
                }
            } catch (Exception e) {
                mostrarError("Nombre no valido");
                event.consume();
                return;
            }
        });

        dialog.setResultConverter(btn -> btn == okType ? p : null);
        return dialog.showAndWait().orElse(null);
    }
    
    //muestra un dialogo para crear un nuevo producto, con campos dinámicos según el tipo seleccionado.
    /*@param inv El inventario para asignar un ID único al nuevo producto.
     * @return El producto creado o null si se cancela el diálogo.     */

    public static Producto dialogoCrearProducto(Inventario inv) {
        Dialog<Producto> dialog = crearDialog();
        ButtonType crearBtnType = dialog.getDialogPane().getButtonTypes().stream()
                .filter(bt -> bt.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                .findFirst().orElse(null);
        
        ChoiceBox<String> tipo = crearChoiceTipo();
        TextField nombre = new TextField();
        TextField precio = new TextField();
        TextField stock = new TextField();
        TextField extra1 = new TextField();
        TextField extra2 = new TextField();
        Label l1 = new Label("Material");
        Label l2 = new Label("Medida");
        final Producto[] productoTemp = new Producto[1];
        
        configurarCambioTipo(tipo, l1, l2);
        
        GridPane grid = crearGrid(tipo, nombre, precio, stock, l1, extra1, l2, extra2);
        dialog.getDialogPane().setContent(grid);
        
        // Obtener botón OK
        Button okButton = (Button) dialog.getDialogPane().lookupButton(crearBtnType);
        //  Evitar que cierre si hay error
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            Producto p = construirProducto(inv, tipo, nombre, precio, stock, extra1, extra2);
            if (p == null) {
                event.consume(); //  No se cierra
            }else {
                productoTemp[0] = p; // Guardamos el producto creado
            }
        });
        
        dialog.setResultConverter(btn -> {
            if ( btn == crearBtnType){
                return productoTemp [0];
            }
            return null;
        });
        return dialog.showAndWait().orElse(null);
    }
    
    //Crea y configura el diálogo base para la creación de un producto, con botones de "Crear" y "Cancelar".
    //@return El diálogo configurado listo para ser personalizado con campos específicos.
    private static Dialog<Producto> crearDialog() {
        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle("Crear Producto");
        
        ButtonType crearBtn = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(crearBtn, ButtonType.CANCEL);

        return dialog;
    }
    
    // Crea un ChoiceBox con las opciones de tipo de producto.
    private static ChoiceBox<String> crearChoiceTipo() {
        ChoiceBox<String> tipo = new ChoiceBox<>(
                FXCollections.observableArrayList("Pileta", "Pintura", "Baldosa")
        );
        tipo.setValue("Baldosa");
        return tipo;
    }
    
    //Configura el cambio de tipo de producto en el diálogo.
    /*@param tipo El ChoiceBox con las opciones de tipo de producto.*/
    private static void configurarCambioTipo(ChoiceBox<String> tipo, Label l1, Label l2) {
        tipo.setOnAction(e -> {
            switch (tipo.getValue()) {
                case "Pileta" -> {
                    l1.setText("Capacidad");
                    l2.setText("Forma");
                }
                case "Pintura" -> {
                    l1.setText("Color");
                    l2.setText("Litros");
                }
                case "Baldosa" -> {
                    l1.setText("Material");
                    l2.setText("Medida");
                }
            }
        });
    }

    /* 
     * @brief Crea un GridPane con los campos necesarios para ingresar los datos de un producto, incluyendo campos dinámicos según el tipo seleccionado.
     * @return El GridPane configurado con los campos necesarios para ingresar los datos de un producto.
    */
    private static GridPane crearGrid(
            ChoiceBox<String> tipo,
            TextField nombre,
            TextField precio,
            TextField stock,
            Label l1,
            TextField extra1,
            Label l2,
            TextField extra2
    ) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, new Label("Tipo"), tipo);
        grid.addRow(1, new Label("Nombre"), nombre);
        grid.addRow(2, new Label("Precio"), precio);
        grid.addRow(3, new Label("Stock"), stock);
        grid.addRow(4, l1, extra1);
        grid.addRow(5, l2, extra2);

        return grid;
    }

    /* 
     * @brief Construye un producto a partir de los datos ingresados en el diálogo, asignando un ID único del inventario.
     * @return El producto construido a partir de los datos ingresados en el diálogo.
    */
    private static Producto construirProducto(
            Inventario inv,
            ChoiceBox<String> tipo,
            TextField nombre,
            TextField precio,
            TextField stock,
            TextField extra1,
            TextField extra2
    ) {

        try {
            String n = nombre.getText();
            double p = Double.parseDouble(precio.getText());
            int s = Integer.parseInt(stock.getText());
            int i = inv.getNextID();

            return switch (tipo.getValue()) {
                case "Pileta" ->
                        new Pileta(i, n, p, s,
                                Integer.parseInt(extra1.getText()),
                                extra2.getText());

                case "Pintura" ->
                        new Pintura(i, n, p, s,
                                extra1.getText(),
                                Integer.parseInt(extra2.getText()));

                case "Baldosa" ->
                        new Baldosa(i, n, p, s,
                                extra1.getText(),
                                Double.parseDouble(extra2.getText()));

                default -> null;

                };
            }  catch (NumberFormatException e){
                mostrarError ( "Ingresa un valor numerico valido");
                return null;                    
        
        }
    }
    
    private static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static String dialogoSeleccionarAscDes(){
        ChoiceDialog<String> dialog =
                new ChoiceDialog<>("Ascendente", "Ascendente", "Descendente");
        dialog.setTitle("Orden");
        dialog.setHeaderText("Seleccione el orden: ");
        return dialog.showAndWait().orElse(null);
    }    

    /* 
     * @brief Muestra un diálogo para seleccionar el criterio de ordenamiento.
     * @return El criterio seleccionado o null si no se selecciona ninguno.
    */
    public static String dialogoSeleccionarCriterio() {
        ChoiceDialog<String> dialog =
                new ChoiceDialog<>("Precio", "Precio", "Nombre", "Stock");
        dialog.setTitle("Ordenar");
        dialog.setHeaderText("Ordenar por: ");
        return dialog.showAndWait().orElse(null);
    }
    
    
    /* 
     * @brief Muestra un diálogo para ingresar un filtro de texto.
     * @return El texto ingresado o null si no se ingresa nada.
    */
    public static String dialogoIngresarFiltro() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filtracion");
        dialog.setHeaderText("Filtro por nombre");
        dialog.setContentText("Ingrese el nombre: ");
        return dialog.showAndWait().orElse(null);
    }
}
