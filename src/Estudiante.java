package modelo;

public class Estudiante extends modelo.Usuario {
    private int cursosInscritos;

    public Estudiante(String codigo, String nombre, String fechaNacimiento, String genero, String password) {
        super(codigo, nombre, fechaNacimiento, genero, password);
        this.cursosInscritos = 0;
    }

    public int getCursosInscritos() { return cursosInscritos; }
    public void setCursosInscritos(int cursosInscritos) { this.cursosInscritos = cursosInscritos; }

    @Override
    public String getRol() {
        return "ESTUDIANTE";
    }
}
