package modelo;

public class Administrador extends modelo.Usuario {
    public Administrador(String codigo, String nombre, String fechaNacimiento, String genero, String password) {
        super(codigo, nombre, fechaNacimiento, genero, password);
    }

    @Override
    public String getRol() {
        return "ADMINISTRADOR";
    }

    @Override
    public String descripcionRol() {
        return "Administrador: gestiona instructores, estudiantes, cursos y secciones.";
    }
}
