package servicios.usuarios;

import archivos.ArchivoUsuario;
import java.util.ArrayList;
import modelo.persona.Usuario;
import modelo.interfaces.Gestionable;
import modelo.persona.PersonaReniec;

public class UsuarioService implements Gestionable<Usuario> {
    private final ArrayList<Usuario> usuarios;
    private final ArchivoUsuario archivo;
    private final DniService dniService;

    public UsuarioService() {
        archivo = new ArchivoUsuario();
        usuarios = archivo.cargarUsuarios();
        dniService = new DniService();
    }
    
    @Override
    //CRUD de la ficha usuario
    public void registrar(Usuario usuario) {
        usuario.setCodigo(generarCodigo());
        validarNuevoUsuario(usuario);
        usuarios.add(0,usuario);
        guardarCambios();
    }

    @Override
    public void actualizar(int index, Usuario usuario) {
        if(index<0 || index>=cantidadUsuarios())
            return;
        Usuario original = usuarios.get(index);
        validarEdicion(usuario, original);
        usuarios.set(index,usuario);
        guardarCambios();
    }

    @Override
    public boolean eliminar(int index) {
        if(index<0 || index>=cantidadUsuarios())
            return false;
        usuarios.remove(index);
        guardarCambios();
        return true;
    }
    //Validar Correo y DNI en uno solo para el form
    public PersonaReniec validarDni(String dni){
        if (dni == null || !dni.matches("\\d{8}")) {
            return new PersonaReniec(false, "El DNI debe tener 8 dígitos.");
        }
        if (existeDni(dni)) {
            return new PersonaReniec(false, "El DNI ya está registrado.");
        }
        PersonaReniec persona = dniService.validarDni(dni);
        if(!persona.isValido()){
            return persona;
        }
        persona.setMensaje("DNI válido, se ingreso su nombre en el campo correctamente");
        return persona;
    }
    // Validaciones para el CRUD
    private void validarCampos(Usuario usuario){
        if(usuario==null){
            throw new IllegalArgumentException("Usuario inválido.");
        }
        if(!usuario.noVacio(usuario.getUsername())){
            throw new IllegalArgumentException("Ingrese un username.");
        }
        if(!usuario.noVacio(usuario.getPassword())){
            throw new IllegalArgumentException("Ingrese una contraseña.");
        }
        if(!usuario.noVacio(usuario.getDni())){
            throw new IllegalArgumentException("Ingrese un DNI.");
        }
        if(!usuario.noVacio(usuario.getNombre())){
            throw new IllegalArgumentException("Valide antes su DNI porfavor");
        }
        if(!usuario.noVacio(usuario.getCorreo())){
            throw new IllegalArgumentException("Ingrese el correo.");
        }
        if(!usuario.noVacio(usuario.getCelular())){
            throw new IllegalArgumentException("Ingrese el celular.");
        }
        if(!usuario.getCorreo().contains("@")){
            throw new IllegalArgumentException("Correo inválido.");
        }
        if(usuario.getDni().length()!=8){
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos.");
        }
    }
    private void validarNuevoUsuario(Usuario usuario) {
        validarCampos(usuario);
        if (existeUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("Username repetido.");
        }
        if (existeCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException(
                "El correo ya está registrado."
            );
        }
    }
    private void validarEdicion(Usuario nuevo, Usuario original) {
        validarCampos(nuevo);
        if (!nuevo.getUsername().equalsIgnoreCase(original.getUsername())
                && existeUsername(nuevo.getUsername())) {
            throw new IllegalArgumentException("Username ya usado en otra cuenta.");
        }
        if (!nuevo.getCorreo().equalsIgnoreCase(original.getCorreo())
                && existeCorreo(nuevo.getCorreo())) {
            throw new IllegalArgumentException("Correo ya esta siendo usado en otra cuenta.");
        }
        if (!nuevo.getDni().equals(original.getDni())
                && existeDni(nuevo.getDni())) {
            throw new IllegalArgumentException("DNI ya esta asociado a otra cuenta.");
        }
    }
    //Imprenta el codgio de forma automatica, pues al ser usado por caulquier persona el registro, seria tedible editarlo
    public String generarCodigo() {
        int mayor = 0;
        for (Usuario u : usuarios) {
            String codigo = u.getCodigo();
            if (codigo != null && codigo.startsWith("U-")) {
                try {
                    int numero = Integer.parseInt(codigo.substring(2));
                    if (numero > mayor) {
                        mayor = numero;
                    }
                } catch (NumberFormatException e) {
                    //Ignorar codigos mal formados
                }
            }
        }

        return String.format("U-%03d", mayor + 1);
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
    
    public Usuario buscarPorCorreo(String correo){
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
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
    private boolean existeCorreo(String correo){
        return buscarPorCorreo(correo) != null;
    }
    //Reporte
    public int cantidadUsuarios(){
        return usuarios.size();
    }
    //Persistencia
    public void guardarCambios(){
        archivo.guardarUsuarios(usuarios);
    }
    public ArrayList<Usuario> listar() {
        return new ArrayList<Usuario>(usuarios);
    }
}
