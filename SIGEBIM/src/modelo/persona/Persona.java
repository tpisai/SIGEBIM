package modelo.persona;

public abstract class Persona {
    private String codigo;
    private String nombre;
    private String dni;
    private String correo;
    private boolean activo;
    //Constructores
    public Persona() {
        this.activo = true;
    }

    public Persona(String codigo, String nombre, String dni, String correo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.dni = dni;
        this.correo = correo;
        this.activo = true;
    }
    //Metodos
    //para evitar mucho codigo en el form
    public boolean tieneDatosBasicosCompletos() {
        return noVacio(codigo) && noVacio(nombre) && noVacio(dni);
    }

    protected boolean noVacio(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
    @Override
    public String toString(){
        return nombre;
    }
    //Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
