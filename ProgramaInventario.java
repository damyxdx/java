import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

class Producto {
    private int id;
    private String nombre;
    private int cantidad;
    private double precio;
    private String categoria;

    public Producto(int id, String nombre, int cantidad, double precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Cantidad: " + cantidad + ", Precio: $" + precio + ", Categoría: " + categoria;
    }
}

class Inventario {
    private List<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(int id) {
        productos.removeIf(p -> p.getId() == id);
    }

    public Optional<Producto> buscarPorId(int id) {
        return productos.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public List<Producto> buscarPorPrecio(double precio) {
        return productos.stream()
                .filter(p -> p.getPrecio() == precio)
                .collect(Collectors.toList());
    }

    public void actualizarCantidad(int id, int nuevaCantidad) {
        buscarPorId(id).ifPresent(p -> p.setCantidad(nuevaCantidad));
    }

    public void resumenInventario() {
        productos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoria))
                .forEach((categoria, listaProductos) -> {
                    System.out.println("Categoría: " + categoria);
                    listaProductos.forEach(p -> System.out.println(" - " + p));
                });
    }

    public void notificarBajoStock(int limite) {
        productos.stream()
                .filter(p -> p.getCantidad() <= limite)
                .forEach(p -> System.out.println("Advertencia: Bajo stock para " + p.getNombre() + " (ID: " + p.getId() + ")"));
    }
}

public class ProgramaInventario {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        Scanner scanner = new Scanner(System.in);

        // Ejemplo de productos
        inventario.agregarProducto(new Producto(1, "Teclado", 10, 25.99, "Electrónica"));
        inventario.agregarProducto(new Producto(2, "Mouse", 5, 15.99, "Electrónica"));
        inventario.agregarProducto(new Producto(3, "Cuaderno", 50, 3.99, "Papelería"));

        int opcion;

        do {
            System.out.println("\n--- Menú de Inventario ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Buscar producto por ID");
            System.out.println("4. Buscar producto por nombre");
            System.out.println("5. Buscar producto por precio");
            System.out.println("6. Actualizar cantidad de producto");
            System.out.println("7. Resumen del inventario");
            System.out.println("8. Notificar productos con bajo stock");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer
                    System.out.print("Ingrese nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese cantidad: ");
                    int cantidad = scanner.nextInt();
                    System.out.print("Ingrese precio: ");
                    double precio = scanner.nextDouble();
                    scanner.nextLine(); // Clear buffer
                    System.out.print("Ingrese categoría: ");
                    String categoria = scanner.nextLine();
                    inventario.agregarProducto(new Producto(id, nombre, cantidad, precio, categoria));
                    break;
                case 2:
                    System.out.print("Ingrese ID del producto a eliminar: ");
                    id = scanner.nextInt();
                    inventario.eliminarProducto(id);
                    break;
                case 3:
                    System.out.print("Ingrese ID del producto: ");
                    id = scanner.nextInt();
                    Optional<Producto> producto = inventario.buscarPorId(id);
                    if (producto.isPresent()) {
                        System.out.println(producto.get());
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese nombre del producto: ");
                    scanner.nextLine(); // Clear buffer
                    nombre = scanner.nextLine();
                    List<Producto> productosPorNombre = inventario.buscarPorNombre(nombre);
                    if (productosPorNombre.isEmpty()) {
                        System.out.println("Producto no encontrado.");
                    } else {
                        productosPorNombre.forEach(System.out::println);
                    }
                    break;
                case 5:
                    System.out.print("Ingrese precio del producto: ");
                    precio = scanner.nextDouble();
                    List<Producto> productosPorPrecio = inventario.buscarPorPrecio(precio);
                    if (productosPorPrecio.isEmpty()) {
                        System.out.println("Producto no encontrado.");
                    } else {
                        productosPorPrecio.forEach(System.out::println);
                    }
                    break;
                case 6:
                    System.out.print("Ingrese ID del producto: ");
                    id = scanner.nextInt();
                    System.out.print("Ingrese nueva cantidad: ");
                    cantidad = scanner.nextInt();
                    inventario.actualizarCantidad(id, cantidad);
                    break;
                case 7:
                    inventario.resumenInventario();
                    break;
                case 8:
                    System.out.print("Ingrese el límite de stock para notificación: ");
                    int limite = scanner.nextInt();
                    inventario.notificarBajoStock(limite);
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 9);

        scanner.close();
    }
}
