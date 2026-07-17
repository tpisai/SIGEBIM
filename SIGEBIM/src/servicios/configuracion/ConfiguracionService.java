package servicios.configuracion;

import archivos.ArchivoConfiguracion;
import modelo.configuracion.ConfiguracionSistema;

public class ConfiguracionService {
    private ArchivoConfiguracion archivoDao;

    public ConfiguracionService() {
        this.archivoDao = new ArchivoConfiguracion();
    }

    public void guardarConfiguracion(ConfiguracionSistema config) {
        archivoDao.guardar(config);
    }

    public ConfiguracionSistema obtenerConfiguracion() {
        return archivoDao.cargar();
    }
}