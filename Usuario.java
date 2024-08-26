import java.util.List;

public class Usuario {
    private String nombre;
    private List<String> preferencias;
    private List<Habitacion> reservasPrevias;

    public Usuario(String nombre, List<String> preferencias) {
        this.nombre = nombre;
        this.preferencias = preferencias;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public List<String> getPreferencias() { return preferencias; }
    public void setReservasPrevias(List<Habitacion> reservasPrevias) { this.reservasPrevias = reservasPrevias; }
    public List<Habitacion> getReservasPrevias() { return reservasPrevias; }
}
