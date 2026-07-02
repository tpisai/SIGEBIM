package archivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import modelo.persona.Usuario;
import modelo.enums.RolUsuario;

public class ArchivoUsuario {

    private final String RUTA = "data/usuarios.txt";

    public void guardarUsuarios(ArrayList<Usuario> lista){
        try(PrintWriter pw = new PrintWriter(new FileWriter(RUTA))){
            for(Usuario u : lista){
                pw.println(
                    u.getCodigo() + "|" +
                    u.getNombre() + "|" +
                    u.getDni() + "|" +
                    u.getCorreo() + "|" +
                    u.getCelular() + "|" +
                    u.getUsername() + "|" +
                    u.getPassword() + "|" +
                    u.getRol().getTexto() + "|" +
                    u.isActivo()
                );
            }
        }catch(IOException e){
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public ArrayList<Usuario> cargarUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(RUTA))){
            String linea;
            while((linea = br.readLine()) != null){
                if(linea.trim().isEmpty()){
                    continue;
                }
                String[] datos = linea.split("\\|");
                if (datos.length < 9) {
                    System.out.println("Registro inválido ignorado: " + linea);
                    continue;
                }
                Usuario u = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], RolUsuario.desdeTexto(datos[7]));
                u.setActivo(Boolean.parseBoolean(datos[8]));
                lista.add(u);
            }
        }catch(IOException e){
            System.out.println("Error al leer Usuarios: " + e.getMessage());
        }
        return lista;
    }
}
