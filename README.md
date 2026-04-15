# Gestión de Inventario para Placas Atérmicas y Piletas

## 👤 Sobre mí
Soy **Keila Aristimuño**, estudiante de la **Tecnicatura Universitaria en Programación** en la **UTN Avellaneda**.
Este proyecto final para la materia Programación II consiste en un sistema de gestión de stock profesional para placas atermicas y mantenimiento de piletas.
---
## 📝 Resumen y Funcionalidad
### Descripción
El sistema es una solución integral diseñada para el control administrativo de productos como **baldosas atérmicas, piletas y pinturas**. 
Permite un manejo eficiente del inventario, facilitando la organización y el seguimiento de existencias.

## Requisitos
* JDK 21
* Las librerías necesarias están incluidas en la carpeta `/lib` del proyecto.

## Cómo ejecutar
1. Clonar el repositorio.
2. Abrir el proyecto con NetBeans.
3. Si aparecen errores de librerías, ir a Propiedades del Proyecto -> Libraries y re-vincular los archivos JAR desde la carpeta `/lib`.

### Operaciones (CRUD)
A través de una interfaz gráfica intuitiva, el usuario puede realizar las siguientes acciones:
**Agregar:** Registrar nuevos productos con sus atributos específicos (ID, nombre, precio, stock, etc.).
**Listar:** Visualizar el inventario completo en tiempo real mediante una tabla interactiva (`TableView`).
**Actualizar:** Modificar información de productos existentes.
**Eliminar:** Dar de baja productos del sistema por ID.

### Persistencia de Datos
Para garantizar la integridad y portabilidad de la información, la aplicación implementa tres métodos de persistencia exigidos por la cátedra:
1. **JSON:** Exportación manual mediante parseo de Strings y manejo de colecciones `Map`.
2. **CSV:** Formato legible por hojas de cálculo con encabezados descriptivos.
3. **Serialización Binaria (.dat):** Almacenamiento de objetos Java mediante `ObjectOutputStream`.

---
## 📊 Diagrama de Clases UML
![UML.png](src/Img/UML.png)

---
## 🖼️ Capturas de Pantalla
### Interfaz de JavaFX
Tabla principal
![Gestión de Productos - UI](src/Img/GestionTablaPrincipal.png)
Se agregan los productos
![Gestión de Productos - UI](src/Img/AgregarProductoCategoria.png)
Los detalles de cada producto, funcion eliminar, funcion editar
![Gestión de Productos - UI](src/Img/Detalles.png)
Metodo filtrar por nombre
![Gestión de Productos - UI](src/Img/Filtrar.png)
Metodo Ordenar por Nombre, Precio o Stock
![Gestión de Productos - UI](src/Img/Ordenar.png)
Metodo eliminar producto al tocarlo
![Gestión de Productos - UI](src/Img/EliminarProducto.png)
Metodo editar producto
![Gestión de Productos - UI](src/Img/EditarProducto.png)

---
> **Proyecto Final - Programación II**
> **Año:** 2026
