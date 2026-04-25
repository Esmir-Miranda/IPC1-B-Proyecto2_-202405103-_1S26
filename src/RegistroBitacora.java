package modelo;

import java.io.Serializable;

public class RegistroBitacora implements Serializable {
    private String fecha;
    private String rol;
    private String codigoUsuario;
    private String operacion;
    private String estado;
    private String descripcion;

    public RegistroBitacora(String fecha, String rol, String codigoUsuario, String operacion, String estado, String descripcion) {
        this.fecha = fecha;
        this.rol = rol;
        this.codigoUsuario = codigoUsuario;
        this.operacion = operacion;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public String getFecha() { return fecha; }
    public String getRol() { return rol; }
    public String getCodigoUsuario() { return codigoUsuario; }
    public String getOperacion() { return operacion; }
    public String getEstado() { return estado; }
    public String getDescripcion() { return descripcion; }
}
