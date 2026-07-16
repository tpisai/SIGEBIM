package modelo.persona;
// @author zulmi
public class PersonaReniec {
    private boolean valido;
    private String dni;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String mensaje;

    public PersonaReniec() {
    }

    public PersonaReniec(boolean valido, String mensaje) {
        this.valido = valido;
        this.mensaje = mensaje;
    }

    public String getNombreCompleto() {
         StringBuilder sb = new StringBuilder();
        if(nombres!=null){
            sb.append(nombres);
        }
        if(apellidoPaterno!=null){
            sb.append(" ").append(apellidoPaterno);
        }
        if(apellidoMaterno!=null){
            sb.append(" ").append(apellidoMaterno);
        }
        return sb.toString().trim();
    }

    // Getters y Setters
    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}