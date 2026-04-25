package modelo;

import java.io.Serializable;

public class Nota implements Serializable {
    private String codigoCurso;
    private String codigoSeccion;
    private String codigoEstudiante;
    private String actividad;
    private double ponderacion;
    private double nota;
    private String fechaRegistro;

    public Nota(String codigoCurso, String codigoSeccion, String codigoEstudiante, String actividad,
                double ponderacion, double nota, String fechaRegistro) {
        this.codigoCurso = codigoCurso;
        this.codigoSeccion = codigoSeccion;
        this.codigoEstudiante = codigoEstudiante;
        this.actividad = actividad;
        this.ponderacion = ponderacion;
        this.nota = nota;
        this.fechaRegistro = fechaRegistro;
    }

    public String getCodigoCurso() { return codigoCurso; }
    public String getCodigoSeccion() { return codigoSeccion; }
    public String getCodigoEstudiante() { return codigoEstudiante; }
    public String getActividad() { return actividad; }
    public double getPonderacion() { return ponderacion; }
    public double getNota() { return nota; }
    public String getFechaRegistro() { return fechaRegistro; }

    public void setPonderacion(double ponderacion) { this.ponderacion = ponderacion; }
    public void setNota(double nota) { this.nota = nota; }
}
