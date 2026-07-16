/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.JInternalFrame;

import archivos.ArchivoLibro;
import archivos.ArchivoPrestamo;
import archivos.ArchivoUsuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.enums.EstadoLibro;
import modelo.enums.EstadoPrestamo;
import modelo.libro.Libro;
import modelo.persona.Usuario;
import modelo.prestamo.Prestamo;
import servicios.prestamos.TicketPrestamo;
import util.TablaUtil;

/**
 *
 * @author zulmi
 */
public class GestionPrestamo extends JInternalFrame {

    private ArrayList<Prestamo> listaPrestamos;
    private ArchivoPrestamo archivoPrestamo = new ArchivoPrestamo();
    
    private ArrayList<Libro> listaLibros;
    private ArrayList<Usuario> listaUsuarios;
    private ArchivoLibro archivoLibro = new ArchivoLibro();
    private ArchivoUsuario archivoUsuario = new ArchivoUsuario();
    
    private static int contador = 0;

    private String generarIdPrestamo() {
        contador++;
        return String.format("TCK-%04d", contador);
    }
    
    /**
     * Creates new form Prestamos
     */
    public GestionPrestamo() {
        super("Gestion Prestamos - Seccion Bibliotecario", true, true, true, true);
        initComponents();
        txtFechaDevolucion.setEditable(false);
        txtNombreDni.setEditable(false);
        txtNombreLibro.setEditable(false);
        txtIdPrestamo.setEditable(false);
        txtIdPrestamo.setText(generarIdPrestamo());
        
        configurarOpcionesDias();
        configurarEstados();
        
        listaLibros = archivoLibro.cargarLibros();
        configurarBusquedaPorIsbn();
        
        configurarCalculoFechaDevolucion();
        
        listaUsuarios = archivoUsuario.cargarUsuarios();
        configurarBusquedaPorDni();
        
        listaPrestamos = archivoPrestamo.cargarPrestamos(listaLibros, listaUsuarios);
        llenarTablaPrestamos();
        mostrarListaPrestamos(listaPrestamos);

    }

    private void configurarOpcionesDias() {
        txtDias.removeAllItems();
        txtDias.addItem("7 días - Poca demanda");
        txtDias.addItem("3 días - Media demanda");
        txtDias.addItem("1 día - Alta demanda");
    }

    private void configurarCalculoFechaDevolucion() {
        txtDias.addActionListener(e -> {
            int dias = obtenerDiasSeleccionados();
            if (dias > 0) {
                LocalDate fechaDevolucion = LocalDate.now().plusDays(dias);
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtFechaDevolucion.setText(fechaDevolucion.format(formato));
            } else {
                txtFechaDevolucion.setText("");
            }
        });
    }

    private int obtenerDiasSeleccionados() {
        String opcion = (String) txtDias.getSelectedItem();
        if (opcion.startsWith("7")) return 7;
        if (opcion.startsWith("3")) return 3;
        if (opcion.startsWith("1")) return 1;
        return 0;
    }
    
    private void configurarEstados() {
        txtEstado.removeAllItems();
        for (EstadoPrestamo estado : EstadoPrestamo.values()) {
            txtEstado.addItem(estado.getTexto());
        }
    }
    
    private void configurarBusquedaPorDni() {
        txtDni.addActionListener(e -> {
            String dniIngresado = txtDni.getText().trim();
            if (dniIngresado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un DNI válido");
                return;
            }

            Usuario usuarioEncontrado = null;
            for (Usuario u : listaUsuarios) {
                if (u.getDni().equalsIgnoreCase(dniIngresado) && u.esLector()) {
                    usuarioEncontrado = u;
                    break;
                }
            }

            if (usuarioEncontrado != null) {
                txtNombreDni.setText(usuarioEncontrado.getNombre());
            } else {
                txtNombreDni.setText("Usuario no encontrado");
                JOptionPane.showMessageDialog(this,
                        "No se encontró un lector con ese DNI",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void llenarTablaPrestamos() {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("ISBN");
        modeloTabla.addColumn("Libro");
        modeloTabla.addColumn("Usuario");
        modeloTabla.addColumn("Fecha Préstamo");
        modeloTabla.addColumn("Fecha Devolución");
        modeloTabla.addColumn("Estado");
        tblPrestamoDevolucion.setModel(modeloTabla);
    }
    
    private void mostrarListaPrestamos(ArrayList<Prestamo> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblPrestamoDevolucion.getModel();
        modelo.setRowCount(0);
        for (Prestamo p : lista) {
            Object[] fila = {
                p.getIdPrestamo(),
                p.getLibro().getIsbn(),
                p.getLibro().getTitulo(),
                p.getUsuario().getNombre(),
                p.getFechaPrestamo(),
                p.getFechaDevolucion(),
                p.getEstadoPrestamo().getTexto()
            };
            modelo.addRow(fila);
        }
        TablaUtil.ajustarTabla(tblPrestamoDevolucion);
    }

    private void configurarBusquedaPorIsbn() {
        txtLibro.addActionListener(e -> {
            String isbn = txtLibro.getText().trim().toLowerCase();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ISBN válido");
                return;
            }

            Libro libroEncontrado = null;
            for (Libro l : listaLibros) {
                if (l.getIsbn().toLowerCase().equals(isbn)) {
                    libroEncontrado = l;
                    break;
                }
            }

            if (libroEncontrado != null) {
                txtNombreLibro.setText(libroEncontrado.getTitulo());
            } else {
                txtNombreLibro.setText("");
                JOptionPane.showMessageDialog(this,
                        "No se encontró ningún libro con ese ISBN",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void limpiar(){                                         
        txtLibro.setText("");
        txtNombreLibro.setText("");
        txtDni.setText("");
        txtNombreDni.setText("");
        txtDias.setSelectedIndex(-1);
        txtFechaDevolucion.setText("");
        txtEstado.setSelectedIndex(-1);
    }
    

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrllPrestamoDevolucion = new javax.swing.JScrollPane();
        tblPrestamoDevolucion = new javax.swing.JTable();
        txtIdPrestamo = new javax.swing.JTextField();
        txtLibro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNombreDni = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDias = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JComboBox<>();
        btnPrestamo = new javax.swing.JButton();
        btnDevolucion = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNombreLibro = new javax.swing.JTextField();
        txtFechaDevolucion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIdPrest = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblPrestamoDevolucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrllPrestamoDevolucion.setViewportView(tblPrestamoDevolucion);

        jLabel1.setText("Id Prestamo:");

        jLabel2.setText("Isbn:");

        jLabel4.setText("Dias:");

        jLabel5.setText("Estado:");

        btnPrestamo.setText("Registrar Prestamo");
        btnPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamoActionPerformed(evt);
            }
        });

        btnDevolucion.setText("Registrar Devolucion");
        btnDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolucionActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Gestion de Prestamo y Devoluciones");

        jLabel8.setText("Dni:");

        jLabel3.setText("Fecha Devolucion:");

        jLabel7.setText("Id :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPrestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtIdPrestamo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                        .addComponent(txtLibro, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addComponent(txtNombreLibro)
                            .addComponent(txtNombreDni)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFechaDevolucion)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.Alignment.TRAILING, 0, 123, Short.MAX_VALUE)
                                    .addComponent(txtDias, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(24, 24, 24)))
                .addComponent(scrllPrestamoDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtIdPrest, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDevolucion, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIdPrest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtIdPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtNombreLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addComponent(txtNombreDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(58, 58, 58)
                        .addComponent(btnPrestamo)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addGap(72, 72, 72))
                    .addComponent(scrllPrestamoDevolucion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamoActionPerformed
        String id = generarIdPrestamo(); //  generar nuevo ID
        txtIdPrestamo.setText(id);

        String isbn = txtLibro.getText().trim();
        String dni = txtDni.getText().trim();
        int dias = obtenerDiasSeleccionados();
        String fechaDev = txtFechaDevolucion.getText();
        String estadoTexto = (String) txtEstado.getSelectedItem();

        // Buscar libro
        Libro libro = null;
        for (Libro l : listaLibros) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                libro = l;
                break;
            }
        }

        // Buscar usuario
        Usuario usuario = null;
        for (Usuario u : listaUsuarios) {
            if (u.getDni().equalsIgnoreCase(dni) && u.esLector()) {
                usuario = u;
                break;
            }
        }

        if (libro == null || usuario == null) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un libro válido y un lector válido.");
            return;
        }

        // Validar stock
        EstadoLibro estadoLibro = EstadoLibro.desdeStock(libro.getStock());
        if (estadoLibro == EstadoLibro.NO_DISPONIBLE) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "El libro no tiene stock disponible.\n¿Desea agregar al usuario a la cola de espera?",
                    "Stock agotado", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Usuario agregado a la cola de espera.");
            } else {
                JOptionPane.showMessageDialog(this, "Préstamo rechazado.");
            }
            return;
        }

        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = LocalDate.parse(fechaDev, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        EstadoPrestamo estado = EstadoPrestamo.desdeTexto(estadoTexto);

        Prestamo nuevo = new Prestamo(id, libro, usuario, fechaPrestamo, fechaDevolucion, estado);
        listaPrestamos.add(nuevo);

        // Descontar stock
        libro.setStock(libro.getStock() - 1);
        archivoLibro.guardarLibros(listaLibros);

        archivoPrestamo.guardarPrestamos(listaPrestamos);
        mostrarListaPrestamos(listaPrestamos);

        JOptionPane.showMessageDialog(this, "Préstamo registrado correctamente.");

        // preparar el siguiente ID
        txtIdPrestamo.setText(generarIdPrestamo());

        // Generar comprobante PDF
        TicketPrestamo.generarComprobante(nuevo);
    }//GEN-LAST:event_btnPrestamoActionPerformed

    private void btnDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolucionActionPerformed
        String id = txtIdPrest.getText();
        Prestamo prestamoEncontrado = null;
        for (Prestamo p : listaPrestamos) {
            if (p.getIdPrestamo().equalsIgnoreCase(id)) {
                prestamoEncontrado = p;
                break;
            }
        }

        if (prestamoEncontrado != null) {
            prestamoEncontrado.marcarDevuelto();
            Libro libro = prestamoEncontrado.getLibro();
            libro.setStock(libro.getStock() + 1);
            archivoLibro.guardarLibros(listaLibros);
            archivoPrestamo.guardarPrestamos(listaPrestamos);
            mostrarListaPrestamos(listaPrestamos);
            JOptionPane.showMessageDialog(this, "Devolución registrada correctamente.");

            if (modelo.prestamo.ColaDeEspera.tieneCola(libro.getIsbn())) {
                Usuario siguiente = modelo.prestamo.ColaDeEspera.atenderSiguiente(libro.getIsbn());
                if (siguiente != null) {
                    JOptionPane.showMessageDialog(this,
                            "Siguiente en cola:\nNombre: " + siguiente.getNombre()
                            + "\nCelular: " + siguiente.getCelular(),
                            "Cola de espera", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el préstamo con ese ID.");
        }
    }//GEN-LAST:event_btnDevolucionActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDevolucion;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnPrestamo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane scrllPrestamoDevolucion;
    private javax.swing.JTable tblPrestamoDevolucion;
    private javax.swing.JComboBox<String> txtDias;
    private javax.swing.JTextField txtDni;
    private javax.swing.JComboBox<String> txtEstado;
    private javax.swing.JTextField txtFechaDevolucion;
    private javax.swing.JTextField txtIdPrest;
    private javax.swing.JTextField txtIdPrestamo;
    private javax.swing.JTextField txtLibro;
    private javax.swing.JTextField txtNombreDni;
    private javax.swing.JTextField txtNombreLibro;
    // End of variables declaration//GEN-END:variables
}
