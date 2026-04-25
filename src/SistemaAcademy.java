package modelo;

import interfaces.Crud;
import util.FechaUtil;

import java.io.Serializable;

public class SistemaAcademy implements Serializable, Crud<modelo.Curso> {
    private modelo.Usuario[] usuarios;
    private int totalUsuarios;
    private modelo.Curso[] cursos;
    private int totalCursos;
    private modelo.RegistroBitacora[] bitacora;
    private int totalBitacora;
    private int usuariosActivos;

    public SistemaAcademy() {
        usuarios = new modelo.Usuario[500];
        cursos = new modelo.Curso[500];
        bitacora = new modelo.RegistroBitacora[5000];
        totalUsuarios = 0;
        totalCursos = 0;
        totalBitacora = 0;
        usuariosActivos = 0;
        crearAdminInicial();
    }

    private void crearAdminInicial() {
        usuarios[totalUsuarios++] = new modelo.Administrador("admin", "Administrador General", "2000-01-01", "M", "IPC1A");
        registrarBitacora("ADMINISTRADOR", "admin", "INICIO", "EXITOSA", "Administrador inicial creado");
    }

    public void registrarBitacora(String rol, String codigo, String operacion, String estado, String descripcion) {
        if (totalBitacora < bitacora.length) {
            bitacora[totalBitacora++] = new modelo.RegistroBitacora(FechaUtil.ahora(), rol, codigo, operacion, estado, descripcion);
        }
    }

    public modelo.Usuario autenticar(String codigo, String password) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo) && usuarios[i].getPassword().equals(password)) {
                usuariosActivos++;
                registrarBitacora(usuarios[i].getRol(), codigo, "LOGIN", "EXITOSA", "Inicio de sesión exitoso");
                return usuarios[i];
            }
        }
        registrarBitacora("DESCONOCIDO", codigo, "LOGIN", "FALLIDA", "Credenciales inválidas");
        return null;
    }

    public void cerrarSesion(modelo.Usuario usuario) {
        if (usuario != null) {
            if (usuariosActivos > 0) usuariosActivos--;
            registrarBitacora(usuario.getRol(), usuario.getCodigo(), "LOGOUT", "EXITOSA", "Cierre de sesión");
        }
    }

    public modelo.Usuario buscarUsuario(String codigo) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo)) return usuarios[i];
        }
        return null;
    }

    public boolean crearInstructor(modelo.Instructor instructor) {
        if (instructor == null || buscarUsuario(instructor.getCodigo()) != null || totalUsuarios >= usuarios.length) {
            registrarBitacora("ADMINISTRADOR", "admin", "CREAR_INSTRUCTOR", "FALLIDA", "Código duplicado o datos inválidos");
            return false;
        }
        usuarios[totalUsuarios++] = instructor;
        registrarBitacora("ADMINISTRADOR", "admin", "CREAR_INSTRUCTOR", "EXITOSA", "Instructor creado: " + instructor.getCodigo());
        return true;
    }

    public boolean crearEstudiante(modelo.Estudiante estudiante) {
        if (estudiante == null || buscarUsuario(estudiante.getCodigo()) != null || totalUsuarios >= usuarios.length) {
            registrarBitacora("ADMINISTRADOR", "admin", "CREAR_ESTUDIANTE", "FALLIDA", "Código duplicado o datos inválidos");
            return false;
        }
        usuarios[totalUsuarios++] = estudiante;
        registrarBitacora("ADMINISTRADOR", "admin", "CREAR_ESTUDIANTE", "EXITOSA", "Estudiante creado: " + estudiante.getCodigo());
        return true;
    }

    @Override
    public boolean crear(modelo.Curso curso) {
        if (curso == null || buscar(curso.getCodigo()) != null || totalCursos >= cursos.length) {
            registrarBitacora("ADMINISTRADOR", "admin", "CREAR_CURSO", "FALLIDA", "Código duplicado o datos inválidos");
            return false;
        }
        cursos[totalCursos++] = curso;
        registrarBitacora("ADMINISTRADOR", "admin", "CREAR_CURSO", "EXITOSA", "Curso creado: " + curso.getCodigo());
        return true;
    }

    @Override
    public boolean actualizar(String codigo, modelo.Curso nuevo) {
        modelo.Curso actual = buscar(codigo);
        if (actual == null || nuevo == null) return false;
        actual.setNombre(nuevo.getNombre());
        actual.setDescripcion(nuevo.getDescripcion());
        actual.setCreditos(nuevo.getCreditos());
        registrarBitacora("ADMINISTRADOR", "admin", "ACTUALIZAR_CURSO", "EXITOSA", "Curso actualizado: " + codigo);
        return true;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < totalCursos; i++) {
            if (cursos[i].getCodigo().equals(codigo)) {
                for (int j = i; j < totalCursos - 1; j++) cursos[j] = cursos[j + 1];
                cursos[--totalCursos] = null;
                registrarBitacora("ADMINISTRADOR", "admin", "ELIMINAR_CURSO", "EXITOSA", "Curso eliminado: " + codigo);
                return true;
            }
        }
        return false;
    }

    @Override
    public modelo.Curso buscar(String codigo) {
        for (int i = 0; i < totalCursos; i++) {
            if (cursos[i].getCodigo().equals(codigo)) return cursos[i];
        }
        return null;
    }

    public Object[][] getUsuariosTableData() {
        Object[][] data = new Object[totalUsuarios][5];
        for (int i = 0; i < totalUsuarios; i++) {
            data[i][0] = usuarios[i].getCodigo();
            data[i][1] = usuarios[i].getNombre();
            data[i][2] = usuarios[i].getRol();
            data[i][3] = usuarios[i].getFechaNacimiento();
            data[i][4] = usuarios[i].getGenero();
        }
        return data;
    }

    public String[] getUsuariosColumns() {
        return new String[]{"Código", "Nombre", "Rol", "Fecha Nacimiento", "Género"};
    }

    public Object[][] getCursosTableData() {
        Object[][] data = new Object[totalCursos][4];
        for (int i = 0; i < totalCursos; i++) {
            data[i][0] = cursos[i].getCodigo();
            data[i][1] = cursos[i].getNombre();
            data[i][2] = cursos[i].getDescripcion();
            data[i][3] = cursos[i].getCreditos();
        }
        return data;
    }

    public String[] getCursosColumns() {
        return new String[]{"Código", "Nombre", "Descripción", "Créditos"};
    }

    public Object[][] getBitacoraTableData() {
        Object[][] data = new Object[totalBitacora][6];
        for (int i = 0; i < totalBitacora; i++) {
            data[i][0] = bitacora[i].getFecha();
            data[i][1] = bitacora[i].getRol();
            data[i][2] = bitacora[i].getCodigoUsuario();
            data[i][3] = bitacora[i].getOperacion();
            data[i][4] = bitacora[i].getEstado();
            data[i][5] = bitacora[i].getDescripcion();
        }
        return data;
    }

    public String[] getBitacoraColumns() {
        return new String[]{"Fecha", "Rol", "Código", "Operación", "Estado", "Descripción"};
    }

    public int getUsuariosActivos() { return usuariosActivos; }
    public int getTotalUsuarios() { return totalUsuarios; }
    public int getTotalCursos() { return totalCursos; }
}
