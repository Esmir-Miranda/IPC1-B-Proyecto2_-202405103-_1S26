package controlador;
    }

private void cargarSistema() {
    try {
        File file = new File(ARCHIVO);
        if (file.exists()) {
            sistema = (SistemaAcademy) Serializador.cargar(ARCHIVO);
        } else {
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
        System.out.println("Error guardando sistema: " + e.getMessage());
    }
}

public Usuario login(String codigo, String password) {
    Usuario u = sistema.autenticar(codigo, password);
    guardarSistema();
    return u;
}

public void logout(Usuario u) {
    sistema.cerrarSesion(u);
    guardarSistema();
}

public boolean crearInstructor(String codigo, String nombre, String fechaNac, String genero, String password) {
    boolean ok = sistema.crearInstructor(new Instructor(codigo, nombre, fechaNac, genero, password));
    guardarSistema();
    return ok;
}

public boolean crearEstudiante(String codigo, String nombre, String fechaNac, String genero, String password) {
    boolean ok = sistema.crearEstudiante(new Estudiante(codigo, nombre, fechaNac, genero, password));
    guardarSistema();
    return ok;
}

public boolean crearCurso(String codigo, String nombre, String descripcion, int creditos) {
    boolean ok = sistema.crear(new Curso(codigo, nombre, descripcion, creditos));
    guardarSistema();
    return ok;
}

public SistemaAcademy getSistema() {
    return sistema;
}
