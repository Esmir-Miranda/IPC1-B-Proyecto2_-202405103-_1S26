package modelo;

import interfaces.Crud;
import util.FechaUtil;

import java.io.Serializable;

public class SistemaAcademy implements Serializable, Crud<modelo.Curso> {
    private modelo.Usuario[] usuarios;
    private int totalUsuarios;
    private modelo.Curso[] cursos;
    private int totalCursos;
    private modelo.Seccion[] secciones;
    private int totalSecciones;
    private modelo.RegistroBitacora[] bitacora;
    private int totalBitacora;
    private int usuariosActivos;

    public SistemaAcademy() {
        usuarios = new modelo.Usuario[500];
        cursos = new modelo.Curso[500];
        secciones = new modelo.Seccion[500];
        bitacora = new modelo.RegistroBitacora[3000];
        totalUsuarios = 0;
        totalCursos = 0;
        totalSecciones = 0;
        totalBitacora = 0;
        usuariosActivos = 0;
        crearAdminInicial();
    }

    private void crearAdminInicial() {
        usuarios[totalUsuarios++] = new modelo.Administrador("admin", "Administrador General", "2000-01-01", "M", "IPC1A");
        registrarBitacora("ADMINISTRADOR", "admin", "INIT", "EXITOSA", "Administrador inicial creado.");
    }

    public void registrarBitacora(String tipo, String codigo, String operacion, String estado, String descripcion) {
        if (totalBitacora < bitacora.length) {
            bitacora[totalBitacora++] = new modelo.RegistroBitacora(FechaUtil.fechaHoraActual(), tipo, codigo, operacion, estado, descripcion);
        }
    }

    public modelo.Usuario autenticar(String codigo, String password) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo) && usuarios[i].getPassword().equals(password)) {
                usuariosActivos++;
                registrarBitacora(usuarios[i].getRol(), codigo, "LOGIN", "EXITOSA", "Inicio de sesión correcto.");
                return usuarios[i];
            }
        }
        registrarBitacora("DESCONOCIDO", codigo, "LOGIN", "FALLIDA", "Credenciales incorrectas.");
        return null;
    }

    public void cerrarSesion(modelo.Usuario usuario) {
        if (usuariosActivos > 0) usuariosActivos--;
        if (usuario != null) registrarBitacora(usuario.getRol(), usuario.getCodigo(), "LOGOUT", "EXITOSA", "Cierre de sesión.");
    }

    public modelo.Usuario buscarUsuario(String codigo) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo)) return usuarios[i];
        }
        return null;
    }

    public boolean crearInstructor(modelo.Instructor instructor) {
        if (buscarUsuario(instructor.getCodigo()) != null || totalUsuarios >= usuarios.length) return false;
        usuarios[totalUsuarios++] = instructor;
        registrarBitacora("ADMINISTRADOR", "admin", "CREAR_INSTRUCTOR", "EXITOSA", "Instructor creado: " + instructor.getCodigo());
        return true;
    }

    public boolean actualizarInstructor(String codigo, String nombre, String password) {
        modelo.Usuario usuario = buscarUsuario(codigo);
        if (usuario instanceof modelo.Instructor) {
            usuario.setNombre(nombre);
            usuario.setPassword(password);
            registrarBitacora("ADMINISTRADOR", "admin", "ACTUALIZAR_INSTRUCTOR", "EXITOSA", codigo);
            return true;
        }
        return false;
    }

    public boolean eliminarInstructor(String codigo) {
        return eliminarUsuarioPorRol(codigo, "INSTRUCTOR");
    }

    public boolean crearEstudiante(modelo.Estudiante estudiante) {
        if (buscarUsuario(estudiante.getCodigo()) != null || totalUsuarios >= usuarios.length) return false;
        usuarios[totalUsuarios++] = estudiante;
        registrarBitacora("ADMINISTRADOR", "admin", "CREAR_ESTUDIANTE", "EXITOSA", "Estudiante creado: " + estudiante.getCodigo());
        return true;
    }

    public boolean actualizarEstudiante(String codigo, String nombre, String password) {
        modelo.Usuario usuario = buscarUsuario(codigo);
        if (usuario instanceof modelo.Estudiante) {
            usuario.setNombre(nombre);
            usuario.setPassword(password);
            registrarBitacora("ADMINISTRADOR", "admin", "ACTUALIZAR_ESTUDIANTE", "EXITOSA", codigo);
            return true;
        }
        return false;
    }

    public boolean eliminarEstudiante(String codigo) {
        return eliminarUsuarioPorRol(codigo, "ESTUDIANTE");
    }

    private boolean eliminarUsuarioPorRol(String codigo, String rol) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo) && usuarios[i].getRol().equals(rol)) {
                for (int j = i; j < totalUsuarios - 1; j++) usuarios[j] = usuarios[j + 1];
                usuarios[--totalUsuarios] = null;
                registrarBitacora("ADMINISTRADOR", "admin", "ELIMINAR_" + rol, "EXITOSA", codigo);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean crear(modelo.Curso curso) {
        if (buscar(curso.getCodigo()) != null || totalCursos >= cursos.length) return false;
        cursos[totalCursos++] = curso;
        registrarBitacora("ADMINISTRADOR", "admin", "CREAR_CURSO", "EXITOSA", "Curso creado: " + curso.getCodigo());
        return true;
    }

    @Override
    public boolean actualizar(String codigo, modelo.Curso curso) {
        modelo.Curso actual = buscar(codigo);
        if (actual == null) return false;
        actual.setNombre(curso.getNombre());
        actual.setDescripcion(curso.getDescripcion());
        actual.setCreditos(curso.getCreditos());
        registrarBitacora("ADMINISTRADOR", "admin", "ACTUALIZAR_CURSO", "EXITOSA", codigo);
        return true;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < totalCursos; i++) {
            if (cursos[i].getCodigo().equals(codigo)) {
                for (int j = i; j < totalCursos - 1; j++) cursos[j] = cursos[j + 1];
                cursos[--totalCursos] = null;
                registrarBitacora("ADMINISTRADOR", "admin", "ELIMINAR_CURSO", "EXITOSA", codigo);
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

    public boolean crearSeccion(modelo.Seccion seccion) {
        if (buscarSeccion(seccion.getCodigoSeccion()) != null || buscar(seccion.getCodigoCurso()) == null) return false;
        secciones[totalSecciones++] = seccion;
        registrarBitacora("ADMINISTRADOR", "admin", "CREAR_SECCION", "EXITOSA", seccion.getCodigoSeccion());
        return true;
    }

    public modelo.Seccion buscarSeccion(String codigo) {
        for (int i = 0; i < totalSecciones; i++) {
            if (secciones[i].getCodigoSeccion().equals(codigo)) return secciones[i];
        }
        return null;
    }

    public boolean asignarInstructor(String codigoInstructor, String codigoSeccion) {
        modelo.Usuario usuario = buscarUsuario(codigoInstructor);
        modelo.Seccion seccion = buscarSeccion(codigoSeccion);
        if (!(usuario instanceof modelo.Instructor) || seccion == null) return false;
        seccion.setCodigoInstructor(codigoInstructor);
        modelo.Instructor instructor = (modelo.Instructor) usuario;
        instructor.setSeccionesAsignadas(instructor.getSeccionesAsignadas() + 1);
        registrarBitacora("ADMINISTRADOR", "admin", "ASIGNAR_INSTRUCTOR", "EXITOSA", codigoInstructor + " -> " + codigoSeccion);
        return true;
    }

    public boolean inscribir(String codigoEstudiante, String codigoSeccion) {
        modelo.Usuario usuario = buscarUsuario(codigoEstudiante);
        modelo.Seccion seccion = buscarSeccion(codigoSeccion);
        if (!(usuario instanceof modelo.Estudiante) || seccion == null || !seccion.isAbierta()) return false;
        if (tieneChoque(codigoEstudiante, seccion.getHorario(), seccion.getSemestre())) return false;
        boolean ok = seccion.inscribir(codigoEstudiante);
        if (ok) {
            modelo.Estudiante estudiante = (modelo.Estudiante) usuario;
            estudiante.setCursosInscritos(estudiante.getCursosInscritos() + 1);
            registrarBitacora("ESTUDIANTE", codigoEstudiante, "INSCRIBIR_SECCION", "EXITOSA", codigoSeccion);
        }
        return ok;
    }

    private boolean tieneChoque(String codigoEstudiante, String horario, String semestre) {
        for (int i = 0; i < totalSecciones; i++) {
            if (secciones[i].estaInscrito(codigoEstudiante)
                    && secciones[i].getHorario().equalsIgnoreCase(horario)
                    && secciones[i].getSemestre().equalsIgnoreCase(semestre)) return true;
        }
        return false;
    }

    public boolean registrarNota(String codigoInstructor, String codigoCurso, String codigoSeccion, String codigoEstudiante,
                                 String actividad, double ponderacion, double nota, String fecha) {
        modelo.Seccion seccion = buscarSeccion(codigoSeccion);
        if (seccion == null || !seccion.getCodigoInstructor().equals(codigoInstructor)) return false;
        if (!seccion.getCodigoCurso().equals(codigoCurso) || !seccion.estaInscrito(codigoEstudiante)) return false;
        if (nota < 0 || nota > 100 || ponderacion <= 0) return false;
        boolean ok = seccion.agregarNota(new modelo.Nota(codigoCurso, codigoSeccion, codigoEstudiante, actividad, ponderacion, nota, fecha));
        if (ok) registrarBitacora("INSTRUCTOR", codigoInstructor, "CREAR_NOTA", "EXITOSA", codigoEstudiante + " " + actividad);
        return ok;
    }

    public Object[][] getUsuariosTableData() {
        Object[][] data = new Object[totalUsuarios][5];
        for (int i = 0; i < totalUsuarios; i++) {
            data[i][0] = usuarios[i].getCodigo();
            data[i][1] = usuarios[i].getNombre();
            data[i][2] = usuarios[i].getFechaNacimiento();
            data[i][3] = usuarios[i].getGenero();
            data[i][4] = usuarios[i].getRol();
        }
        return data;
    }

    public String[] getUsuariosColumns() { return new String[]{"Código", "Nombre", "Nacimiento", "Género", "Rol"}; }

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

    public String[] getCursosColumns() { return new String[]{"Código", "Nombre", "Descripción", "Créditos"}; }

    public Object[][] getSeccionesTableData() {
        Object[][] data = new Object[totalSecciones][5];
        for (int i = 0; i < totalSecciones; i++) {
            data[i][0] = secciones[i].getCodigoSeccion();
            data[i][1] = secciones[i].getCodigoCurso();
            data[i][2] = secciones[i].getSemestre();
            data[i][3] = secciones[i].getHorario();
            data[i][4] = secciones[i].getCodigoInstructor();
        }
        return data;
    }

    public String[] getSeccionesColumns() { return new String[]{"Sección", "Curso", "Semestre", "Horario", "Instructor"}; }

    public Object[][] getBitacoraTableData() {
        Object[][] data = new Object[totalBitacora][6];
        for (int i = 0; i < totalBitacora; i++) {
            data[i][0] = bitacora[i].getFechaHora();
            data[i][1] = bitacora[i].getTipoUsuario();
            data[i][2] = bitacora[i].getCodigoUsuario();
            data[i][3] = bitacora[i].getOperacion();
            data[i][4] = bitacora[i].getEstado();
            data[i][5] = bitacora[i].getDescripcion();
        }
        return data;
    }

    public String[] getBitacoraColumns() { return new String[]{"Fecha", "Tipo", "Código", "Operación", "Estado", "Descripción"}; }

    public int getTotalUsuarios() { return totalUsuarios; }
    public int getTotalCursos() { return totalCursos; }
    public int getTotalSecciones() { return totalSecciones; }
    public int getUsuariosActivos() { return usuariosActivos; }
}
