package modelo.interfaces;

import modelo.enums.CriterioBusqueda;

public interface Buscable {
    boolean coincideCon(CriterioBusqueda criterio, String texto);
}
