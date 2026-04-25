package modelo;

public class Instructor extends modelo.Usuario {
    private int seccionesAsignadas;

    public Instructor(String codigo, String nombre, String fechaNacimiento, String genero, String password) {
        super(codigo, nombre, fechaNacimiento, genero, password);
        this.seccionesAsignadas = 0;
    }

    public int getSeccionesAsignadas() { return seccionesAsignadas; }
    public void setSeccionesAsignadas(int seccionesAsignadas) { this.seccionesAsignadas = seccionesAsignadas; }

    @Override
    public String getRol() {
        return "INSTRUCTOR";
    }
}
