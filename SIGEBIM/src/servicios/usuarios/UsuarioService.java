package servicios.usuarios;

import archivos.ArchivoUsuario;
import java.util.ArrayList;
import modelo.persona.Usuario;
import modelo.interfaces.Gestionable;

public class UsuarioService implements Gestionable<Usuario> {
    private final ArrayList<Usuario> usuarios;
    private final ArchivoUsuario archivo;

    public UsuarioService() {
        archivo = new ArchivoUsuario();
        usuarios = archivo.cargarUsuarios();
    }
    
    @Override
    //CRUD de la ficha usuario
    public void registrar(Usuario usuario) {
        validarUsuario(usuario,false,null,null);
        usuarios.add(0,usuario);
        guardarCambios();
    }

    @Override
    public void actualizar(int index, Usuario usuario) {
        if(index<0 || index>=usuarios.size())
            return;
        Usuario original = usuarios.get(index);
        validarUsuario(usuario,true,original.getUsername(),original.getDni());
        usuarios.set(index,usuario);
        guardarCambios();
    }

    @Override
    public boolean eliminar(int index) {
        if(index<0 || index>=usuarios.size())
            return false;
        usuarios.remove(index);
        guardarCambios();
        return true;
    }
    // Validaciones
    private void validarUsuario(Usuario usuario, boolean esEdicion, String usernameOriginal, String dniOriginal){
        if(usuario==null){
            throw new IllegalArgumentException("Usuario inválido.");
        }
        if(usuario.getUsername()==null || usuario.getUsername().isBlank()){
            throw new IllegalArgumentException("Ingrese un username.");
        }
        if(usuario.getPassword()==null || usuario.getPassword().isBlank()){
            throw new IllegalArgumentException("Ingrese una contraseña.");
        }
        if(usuario.getDni()==null || usuario.getDni().isBlank()){
            throw new IllegalArgumentException("Ingrese un DNI.");
        }
        if(usuario.getDni().length()!=8){
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos.");
        }
        if(usuario.getCodigo()==null || usuario.getCodigo().isBlank()){
            throw new IllegalArgumentException("Ingrese el código.");
        }

        if(usuario.getNombre()==null || usuario.getNombre().isBlank()){
            throw new IllegalArgumentException("Ingrese el nombre.");
        }

        if(usuario.getCorreo()==null || usuario.getCorreo().isBlank()){
            throw new IllegalArgumentException("Ingrese el correo.");
        }
        if(!usuario.getCorreo().contains("@")){
            throw new IllegalArgumentException("Correo inválido.");
        }
        
        if(!esEdicion || !usuario.getUsername().equalsIgnoreCase(usernameOriginal)){
            if(existeUsername(usuario.getUsername())){
                throw new IllegalArgumentException("Username repetido.");
            }
        }
        
        if(!esEdicion ||
           !usuario.getDni().equals(dniOriginal)){
            if(existeDni(usuario.getDni())){
                throw new IllegalArgumentException("DNI repetido.");
            }
        }
    }
    
    //Logica para busquedas y simplifcar clases
    public Usuario buscarPorUsername(String username){
        for (Usuario u : usuarios){
            if(u.getUsername().equalsIgnoreCase(username))
                return u;
        }
        return null;
    }
    
    public Usuario buscarPorDni(String dni){
        for(Usuario u : usuarios){
            if(u.getDni().equals(dni)){
                return u;
            }
        }
        return null;
    }
    
    public Usuario buscarPorCodigo(String codigo){
        for (Usuario u : usuarios) {
            if (u.getCodigo().equalsIgnoreCase(codigo)) {
                return u;
            }
        }
        return null;
    }
    //Metodos de Apoyo
    private boolean existeUsername(String username){
        return buscarPorUsername(username) != null;
    }
    private boolean existeDni(String dni){
        return buscarPorDni(dni) != null;
    }
    
    public int cantidadUsuarios(){
        return usuarios.size();
    }
    public void guardarCambios(){
        archivo.guardarUsuarios(usuarios);
    }
    public ArrayList<Usuario> listar() {
        return new ArrayList<Usuario>(usuarios);
    }
}
