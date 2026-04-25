package controlador;

import modelo.Curso;
import modelo.Estudiante;
import modelo.Instructor;
import modelo.SistemaAcademy;
import modelo.Usuario;
import util.Serializador;

import java.io.File;

public class AppController {
    private SistemaAcademy sistema;
    private static final String ARCHIVO = "academy.ser";

    public AppController() {
        cargarSistema();
    }

    private void cargarSistema() {
        try {
            File file = new File(ARCHIVO);
            if (file.exists()) sistema = (SistemaAcademy) Serializador.cargar(ARCHIVO);
            else {
                sistema = new SistemaAcademy();
                guardarSistema();
            }
        } catch (Exception e) {
            sistema = new SistemaAcademy();
        }
    }

    public void guardarSistema() {
        try {
            Serializador.guardar(sistema, ARCHIVO);
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public Usuario login(String codigo, String password) {
        Usuario usuario = sistema.autenticar(codigo, password);
        guardarSistema();
        return usuario;
    }

    public void logout(Usuario usuario) {
        sistema.cerrarSesion(usuario);
        guardarSistema();
    }

    public boolean crearInstructor(String codigo, String nombre, String fecha, String genero, String password) {
        boolean ok = sistema.crearInstructor(new Instructor(codigo, nombre, fecha, genero, password));
        guardarSistema();
        return ok;
    }

    public boolean crearEstudiante(String codigo, String nombre, String fecha, String genero, String password) {
        boolean ok = sistema.crearEstudiante(new Estudiante(codigo, nombre, fecha, genero, password));
        guardarSistema();
        return ok;
    }

    public boolean crearCurso(String codigo, String nombre, String descripcion, int creditos) {
        boolean ok = sistema.crear(new Curso(codigo, nombre, descripcion, creditos));
        guardarSistema();
        return ok;
    }

    public SistemaAcademy getSistema() { return sistema; }
}
