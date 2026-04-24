package modelo;
                return true;
                        }
                        }
                        return false;
                        }

@Override
public Curso buscar(String codigo) {
    for (int i = 0; i < totalCursos; i++) {
        if (cursos[i].getCodigo().equals(codigo)) return cursos[i];
    }
    return null;
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

public Object[][] getUsuariosTableData() {
    Object[][] data = new Object[totalUsuarios][4];
    for (int i = 0; i < totalUsuarios; i++) {
        data[i][0] = usuarios[i].getCodigo();
        data[i][1] = usuarios[i].getNombre();
        data[i][2] = usuarios[i].getRol();
        data[i][3] = usuarios[i].getGenero();
    }
    return data;
}

public String[] getUsuariosColumns() {
    return new String[]{"Código", "Nombre", "Rol", "Género"};
}

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

public String[] getBitacoraColumns() {
    return new String[]{"Fecha", "Tipo Usuario", "Código", "Operación", "Estado", "Descripción"};
}

public int getUsuariosActivos() {
    return usuariosActivos;
}

public int getTotalUsuarios() {
    return totalUsuarios;
}

public int getTotalCursos() {
    return totalCursos;
}
}