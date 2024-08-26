public class Habitacion {
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
