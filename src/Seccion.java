package modelo;

import java.io.Serializable;

public class Seccion implements Serializable {
    private String codigoSeccion;
    private String codigoCurso;
    private String semestre;
    private String horario;
    private String codigoInstructor;
    private boolean abierta;

    private String[] estudiantes;
    private int totalEstudiantes;

    private modelo.Nota[] notas;
    private int totalNotas;

    public Seccion(String codigoSeccion, String codigoCurso, String semestre, String horario) {
        this.codigoSeccion = codigoSeccion;
        this.codigoCurso = codigoCurso;
        this.semestre = semestre;
        this.horario = horario;
        this.codigoInstructor = "";
        this.abierta = true;
        this.estudiantes = new String[200];
        this.notas = new modelo.Nota[1000];
        this.totalEstudiantes = 0;
        this.totalNotas = 0;
    }

    public String getCodigoSeccion() { return codigoSeccion; }
    public String getCodigoCurso() { return codigoCurso; }
    public String getSemestre() { return semestre; }
    public String getHorario() { return horario; }
    public String getCodigoInstructor() { return codigoInstructor; }
    public boolean isAbierta() { return abierta; }
    public int getTotalEstudiantes() { return totalEstudiantes; }
    public int getTotalNotas() { return totalNotas; }
    public modelo.Nota[] getNotas() { return notas; }

    public void setCodigoInstructor(String codigoInstructor) { this.codigoInstructor = codigoInstructor; }
    public void setAbierta(boolean abierta) { this.abierta = abierta; }

    public boolean estaInscrito(String codigoEstudiante) {
        for (int i = 0; i < totalEstudiantes; i++) {
            if (estudiantes[i].equals(codigoEstudiante)) return true;
        }
        return false;
    }

    public boolean inscribir(String codigoEstudiante) {
        if (totalEstudiantes >= estudiantes.length || estaInscrito(codigoEstudiante)) return false;
        estudiantes[totalEstudiantes++] = codigoEstudiante;
        return true;
    }

    public boolean desasignar(String codigoEstudiante) {
        if (tieneNotas(codigoEstudiante)) return false;
        for (int i = 0; i < totalEstudiantes; i++) {
            if (estudiantes[i].equals(codigoEstudiante)) {
                for (int j = i; j < totalEstudiantes - 1; j++) estudiantes[j] = estudiantes[j + 1];
                estudiantes[--totalEstudiantes] = null;
                return true;
            }
        }
        return false;
    }

    public boolean agregarNota(modelo.Nota nota) {
        if (totalNotas >= notas.length) return false;
        for (int i = 0; i < totalNotas; i++) {
            if (notas[i].getCodigoEstudiante().equals(nota.getCodigoEstudiante())
                    && notas[i].getActividad().equalsIgnoreCase(nota.getActividad())) return false;
        }
        notas[totalNotas++] = nota;
        return true;
    }

    public boolean tieneNotas(String codigoEstudiante) {
        for (int i = 0; i < totalNotas; i++) {
            if (notas[i].getCodigoEstudiante().equals(codigoEstudiante)) return true;
        }
        return false;
    }

    public double promedio(String codigoEstudiante) {
        double suma = 0;
        double totalPonderacion = 0;
        for (int i = 0; i < totalNotas; i++) {
            if (notas[i].getCodigoEstudiante().equals(codigoEstudiante)) {
                suma += notas[i].getNota() * notas[i].getPonderacion();
                totalPonderacion += notas[i].getPonderacion();
            }
        }
        if (totalPonderacion == 0) return 0;
        return suma / totalPonderacion;
    }
}
