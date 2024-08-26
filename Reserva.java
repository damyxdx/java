import java.util.*;
import java.text.SimpleDateFormat;

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
                usuario.addReservaPrevias(habitacion);
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
