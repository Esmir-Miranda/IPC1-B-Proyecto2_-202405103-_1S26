package controlador;

import modelo.*;
import util.Serializador;

import java.io.File;

public class AppController {
    private modelo.SistemaAcademy sistema;
    private final String archivo = "academy.ser";

    public AppController() {
        cargarSistema();
    }

    private void cargarSistema() {
        try {
            File f = new File(archivo);
            if (f.exists()) sistema = (modelo.SistemaAcademy) Serializador.cargar(archivo);
            else {
                sistema = new modelo.SistemaAcademy();
                guardarSistema();
            }
        } catch (Exception e) {
            sistema = new modelo.SistemaAcademy();
        }
    }

    public void guardarSistema() {
        try {
            Serializador.guardar(sistema, archivo);
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public modelo.Usuario login(String codigo, String password) {
        modelo.Usuario usuario = sistema.autenticar(codigo, password);
        guardarSistema();
        return usuario;
    }

    public void logout(modelo.Usuario usuario) {
        sistema.cerrarSesion(usuario);
        guardarSistema();
    }

    public boolean crearInstructor(String codigo, String nombre, String fecha, String genero, String password) {
        boolean ok = sistema.crearInstructor(new modelo.Instructor(codigo, nombre, fecha, genero, password));
        guardarSistema();
        return ok;
    }

    public boolean actualizarInstructor(String codigo, String nombre, String password) {
        boolean ok = sistema.actualizarInstructor(codigo, nombre, password);
        guardarSistema();
        return ok;
    }

    public boolean eliminarInstructor(String codigo) {
        boolean ok = sistema.eliminarInstructor(codigo);
        guardarSistema();
        return ok;
    }

    public boolean crearEstudiante(String codigo, String nombre, String fecha, String genero, String password) {
        boolean ok = sistema.crearEstudiante(new modelo.Estudiante(codigo, nombre, fecha, genero, password));
        guardarSistema();
        return ok;
    }

    public boolean actualizarEstudiante(String codigo, String nombre, String password) {
        boolean ok = sistema.actualizarEstudiante(codigo, nombre, password);
        guardarSistema();
        return ok;
    }

    public boolean eliminarEstudiante(String codigo) {
        boolean ok = sistema.eliminarEstudiante(codigo);
        guardarSistema();
        return ok;
    }

    public boolean crearCurso(String codigo, String nombre, String descripcion, int creditos) {
        boolean ok = sistema.crear(new modelo.Curso(codigo, nombre, descripcion, creditos));
        guardarSistema();
        return ok;
    }

    public boolean actualizarCurso(String codigo, String nombre, String descripcion, int creditos) {
        boolean ok = sistema.actualizar(codigo, new modelo.Curso(codigo, nombre, descripcion, creditos));
        guardarSistema();
        return ok;
    }

    public boolean eliminarCurso(String codigo) {
        boolean ok = sistema.eliminar(codigo);
        guardarSistema();
        return ok;
    }

    public boolean crearSeccion(String codigoSeccion, String codigoCurso, String semestre, String horario) {
        boolean ok = sistema.crearSeccion(new Seccion(codigoSeccion, codigoCurso, semestre, horario));
        guardarSistema();
        return ok;
    }

    public boolean asignarInstructor(String codigoInstructor, String codigoSeccion) {
        boolean ok = sistema.asignarInstructor(codigoInstructor, codigoSeccion);
        guardarSistema();
        return ok;
    }

    public boolean inscribir(String codigoEstudiante, String codigoSeccion) {
        boolean ok = sistema.inscribir(codigoEstudiante, codigoSeccion);
        guardarSistema();
        return ok;
    }

    public boolean registrarNota(String codigoInstructor, String codigoCurso, String codigoSeccion, String codigoEstudiante,
                                 String actividad, double ponderacion, double nota, String fecha) {
        boolean ok = sistema.registrarNota(codigoInstructor, codigoCurso, codigoSeccion, codigoEstudiante, actividad, ponderacion, nota, fecha);
        guardarSistema();
        return ok;
    }

    public modelo.SistemaAcademy getSistema() { return sistema; }
}
