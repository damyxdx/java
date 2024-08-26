import java.util.*;
import java.text.SimpleDateFormat;

// Clase Habitacion
class Habitacion {
    private int id;
    private String tipo;
    private double precio;
    private boolean disponible;

    public Habitacion(int id, String tipo, double precio) {
        this.id = id;
        this.tipo = tipo;
        this.precio = precio;
        this.disponible = true;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public double getPrecio() { return precio; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}

// Clase Usuario
class Usuario {
    private String nombre;
    private List<String> preferencias;
    private List<Habitacion> reservasPrevias;

    public Usuario(String nombre, List<String> preferencias) {
        this.nombre = nombre;
        this.preferencias = preferencias;
        this.reservasPrevias = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public List<String> getPreferencias() { return preferencias; }
    public List<Habitacion> getReservasPrevias() { return reservasPrevias; }
    public void addReservaPrevias(Habitacion habitacion) { this.reservasPrevias.add(habitacion); }
}

// Clase Reserva
class Reserva {
    private List<Habitacion> habitaciones;
    private Map<Usuario, Habitacion> reservas;

    public Reserva(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
        this.reservas = new HashMap<>();
    }

    public Habitacion asignarHabitacion(Usuario usuario, String tipoHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo().equals(tipoHabitacion) && habitacion.isDisponible()) {
                habitacion.setDisponible(false);
                reservas.put(usuario, habitacion);
                usuario.addReservaPrevias(habitacion); // Asegúrate de que este método esté definido
                return habitacion;
            }
        }
        return null; // No hay habitaciones disponibles
    }

    public Habitacion sugerirHabitacion(Usuario usuario) {
        for (String preferencia : usuario.getPreferencias()) {
            Habitacion habitacion = asignarHabitacion(usuario, preferencia);
            if (habitacion != null) {
                return habitacion;
            }
        }
        return null; // No se encontró ninguna habitación que cumpla con las preferencias
    }

    // Método para generar fechas disponibles aleatoriamente
    public List<Date> generarFechasDisponibles() {
        List<Date> fechasDisponibles = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // Empieza desde el día siguiente
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < 5; i++) { // Genera 5 fechas disponibles
            fechasDisponibles.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        return fechasDisponibles;
    }
}

// Clase principal Hotel
public class Hotel {
    public static void main(String[] args) {
        System.out.println("Bienvenido a HOTEL DELIRIO!");

        // Crear algunas habitaciones para el hotel
        List<Habitacion> habitaciones = Arrays.asList(
                new Habitacion(1, "Suite", 150.0),
                new Habitacion(2, "Doble", 100.0),
                new Habitacion(3, "Simple", 50.0)
        );

        // Crear un objeto de Reserva
        Reserva reserva = new Reserva(habitaciones);

        // Simular la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        // Mostrar las opciones de habitaciones disponibles
        System.out.println("Seleccione una opción de habitación:");
        System.out.println("1. Suite - $150.0");
        System.out.println("2. Doble - $100.0");
        System.out.println("3. Simple - $50.0");

        // Leer la selección del usuario
        int opcion = -1;
        String preferencia = "";

        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
            return;
        }

        // Asignar preferencia basada en la opción seleccionada
        switch (opcion) {
            case 1:
                preferencia = "Suite";
                break;
            case 2:
                preferencia = "Doble";
                break;
            case 3:
                preferencia = "Simple";
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        // Crear un objeto Usuario
        Usuario usuario = new Usuario(nombre, Arrays.asList(preferencia));

        // Asignar una habitación basada en la preferencia seleccionada
        Habitacion habitacionAsignada = reserva.asignarHabitacion(usuario, preferencia);

        if (habitacionAsignada != null) {
            System.out.println("Habitación asignada: " + habitacionAsignada.getId() + " - " + habitacionAsignada.getTipo());

            // Mostrar fechas disponibles
            List<Date> fechasDisponibles = reserva.generarFechasDisponibles();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Fechas disponibles para su reserva:");
            for (Date fecha : fechasDisponibles) {
                System.out.println(dateFormat.format(fecha));
            }

            // Solicitar nombre para finalizar la reserva
            System.out.print("Ingrese su nombre para finalizar la reserva: ");
            String nombreFinal = scanner.next();
            if (!nombre.equals(nombreFinal)) {
                System.out.println("El nombre ingresado no coincide con el nombre inicial. La reserva no se ha completado.");
                return;
            }

            System.out.println("Reserva completada exitosamente para " + nombre + " en la habitación " + habitacionAsignada.getTipo() + ".");
        } else {
            System.out.println("Lo siento, no hay habitaciones disponibles que cumplan con su preferencia.");
        }

        // Sugerir habitaciones para futuras reservas
        Habitacion sugerencia = reserva.sugerirHabitacion(usuario);
        if (sugerencia != null) {
            System.out.println("Sugerencia de habitación para su próxima reserva: " + sugerencia.getId() + " - " + sugerencia.getTipo());
        }
    }
}
