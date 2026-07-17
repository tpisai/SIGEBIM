package archivos;

import java.io.*;
import modelo.configuracion.ConfiguracionSistema;

public class ArchivoConfiguracion {
    private final String RUTA_ARCHIVO = "configuracion.txt";

    public void guardar(ConfiguracionSistema config) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            bw.write(config.getCarpetaRespaldo() + "|");
            bw.write(config.getDiasMaximoPrestamo() + "|");
            bw.write(config.getMultaDiaria() + "|");
            bw.write(config.getMaximoPersonasCola() + "|");
            bw.write(config.getCostoReposicion() + "");
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public ConfiguracionSistema cargar() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) return new ConfiguracionSistema(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();
            if (linea != null) {
                String[] d = linea.split("|");
                return new ConfiguracionSistema(d[0], Integer.parseInt(d[1]), 
                        Double.parseDouble(d[2]), Integer.parseInt(d[3]), 
                        Double.parseDouble(d[4]));
            }
        } catch (Exception e) {
            System.err.println("Error al cargar: " + e.getMessage());
        }
        return new ConfiguracionSistema();
    }
}