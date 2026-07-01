package modelo.interfaces;

import java.util.ArrayList;

public interface Gestionable<T> {
    void registrar(T elemento);
    void actualizar(int index, T elemento);
    boolean eliminar(int index);
    ArrayList<T> listar();
}
