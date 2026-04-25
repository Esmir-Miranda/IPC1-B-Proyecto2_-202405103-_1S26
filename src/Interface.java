package interfaces;

public interface Crud<T> {
    boolean crear(T obj);
    boolean actualizar(String codigo, T obj);
    boolean eliminar(String codigo);
    T buscar(String codigo);
}
