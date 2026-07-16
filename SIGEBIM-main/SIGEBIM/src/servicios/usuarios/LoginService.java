package servicios.usuarios;
// @author zulmi
import modelo.enums.RolUsuario;
import modelo.persona.Usuario;

public class LoginService {
    private final UsuarioService usuarioService;
    private int intentos;
    
    public LoginService(){
        usuarioService = new UsuarioService();
    }
    
    public Usuario iniciarSesion(String username,String password){
        Usuario usuario = usuarioService.buscarPorUsername(username);
        if(usuario==null || !usuario.isActivo()){
            return null;
        }
        if(usuario.getPassword().equalsIgnoreCase(password)){
            return usuario;
        }
        return null;
    }
    //Validaciones para el ingreso seguro
    public boolean tienePermiso(Usuario usuario,RolUsuario rol){
        return usuario!=null && usuario.getRol()==rol;
    }
    
    public String validarCamposLogin(String username, String password){
        if(username.isBlank() && password.isBlank()){
            return "Debe ingresar usuario y contraseña.";
        }
        if(username.isBlank()){
            return "Debe ingresar el usuario.";
        }
        if(password.isBlank()){
            return "Debe ingresar la contraseña.";
        }
        return null;
    }
    
    public int registrarIntentoFallido(){
        return ++intentos;
    }

    public void reiniciarIntentos(){
        intentos = 0;
    }

    public boolean excedioIntentos(){
        return intentos >= 3;
    }

    public int getIntentos(){
        return intentos;
    }
}
