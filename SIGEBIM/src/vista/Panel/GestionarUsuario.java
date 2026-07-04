/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.Panel;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.enums.RolUsuario;
import modelo.persona.PersonaReniec;
import modelo.persona.Usuario;
import servicios.usuarios.AdminUsuarioService;
import servicios.usuarios.UsuarioService;

/**
 *
 * @author zulmi
 */
public class GestionarUsuario extends javax.swing.JPanel {

    /**
     * Creates new form GestionUsuario
     */
    UsuarioService usuarioService = new UsuarioService();
    AdminUsuarioService adminService = new AdminUsuarioService(usuarioService);
    private PersonaReniec personaReniec;
    
    private void actualizarTextoActivo() {
        if (chkActivo.isSelected()) {
            chkActivo.setText("Activado");
        } else {
            chkActivo.setText("Desactivado");
        }
    }
    
    private void cargarRol(){
        cboRol.removeAllItems();
        cboRol.addItem(RolUsuario.LECTOR.getTexto());
        cboRol.addItem(RolUsuario.BIBLIOTECARIO.getTexto());
        cboRol.addItem(RolUsuario.ADMINISTRADOR.getTexto());
    }
    
    private void cargarVariableBusqueda(){
        cboVariable.removeAllItems();
        cboVariable.addItem("DNI");
        cboVariable.addItem("Username");
    }
    
    private void llenarTable(){
        DefaultTableModel modeloTabla = new DefaultTableModel(){
            //No poder editar en la tabla Usuario
            @Override   
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Celular");
        modeloTabla.addColumn("Username");
        modeloTabla.addColumn("Password");
        modeloTabla.addColumn("Rol de Usuario");
        modeloTabla.addColumn("Estado");
        tblUsuarios.setModel(modeloTabla);
    }
    
    private void configurarTabla() {
        tblUsuarios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);   // Código
        tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(70);   // DNI
        tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(220);  // Nombre
        tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(120);  // Correo
        tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(80);  // Celular
        tblUsuarios.getColumnModel().getColumn(5).setPreferredWidth(80);  // Username
        tblUsuarios.getColumnModel().getColumn(6).setPreferredWidth(80);  // Password
        tblUsuarios.getColumnModel().getColumn(7).setPreferredWidth(100);  // Rol
        tblUsuarios.getColumnModel().getColumn(8).setPreferredWidth(60);   // Estado
        tblUsuarios.setRowHeight(25); // Filas más cómodas
        tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo una fila
    }
    
    //Sobreescritura, para no repetir codigo
    private void mostrarLista(ArrayList<Usuario> lista){
        DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
        modelo.setRowCount(0);
        for (Usuario u : lista){
            modelo.addRow(new Object[]{
                u.getCodigo(),
                u.getDni(),
                u.getNombre(),
                u.getCorreo(),
                u.getCelular(),
                u.getUsername(),
                u.getPassword(),
                u.getRol().getTexto(),
                u.isActivo() ? "Activo" : "Inactivo"
            });
        }
    }
    private void mostrarLista(){
        mostrarLista(adminService.listar());
    }
    //
    //Constructor no tocar!!!
    public GestionarUsuario() {
        initComponents();
        txtNombreUsuario.setEditable(false);
        llenarTable();
        configurarTabla()   ;
        mostrarLista();
        cargarRol();
        cargarVariableBusqueda();
    }
    //
    //
    private void limpiar(){
        txtCodigo.setText(null);
        txtNombreUsuario.setText(null);
        txtCorreoUsuario.setText(null);
        txtDni.setText(null);
        txtCelular.setText(null);
        chkActivo.setSelected(true);
        actualizarTextoActivo();
        txtUsername.setText(null);
        txtPassword.setText(null);
        personaReniec = null;
        btnValidar.setEnabled(true);
        txtDni.requestFocus();
        mostrarLista();
        tblUsuarios.clearSelection();
    }
    
    private Usuario obtenerUsuarioFormulario() {
        Usuario usuario = new Usuario(
            txtCodigo.getText().trim(),
            txtNombreUsuario.getText().trim(),
            txtDni.getText().trim(),
            txtCorreoUsuario.getText().trim(),
            txtCelular.getText().trim(),
            txtUsername.getText().trim(),
            txtPassword.getText().trim(),
            RolUsuario.desdeTexto(
                (String)cboRol.getSelectedItem())
        );
        usuario.setActivo(chkActivo.isSelected());
        return usuario;
    }
    
    private void mostrarUsuario(Usuario usuario){
        if(usuario==null){
            JOptionPane.showMessageDialog(this,"Usuario no encontrado.");
            return;
        }
        txtCodigo.setText(usuario.getCodigo());
        txtDni.setText(usuario.getDni());
        txtNombreUsuario.setText(usuario.getNombre());
        txtCorreoUsuario.setText(usuario.getCorreo());
        txtCelular.setText(usuario.getCelular());
        txtUsername.setText(usuario.getUsername());
        txtPassword.setText(usuario.getPassword());
        cboRol.setSelectedItem(usuario.getRol().getTexto());
        chkActivo.setSelected(usuario.isActivo());
        actualizarTextoActivo();
        txtNombreUsuario.setBackground(Color.WHITE);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jCheckBox1 = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboRol = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        txtCorreoUsuario = new javax.swing.JTextField();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        chkActivo = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        btnValidar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiarYMostrar = new javax.swing.JButton();
        cboVariable = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

        jScrollPane2.setViewportView(jTree1);

        jMenuItem1.setText("jMenuItem1");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jCheckBox1.setText("jCheckBox1");

        jRadioButton1.setText("jRadioButton1");

        setBackground(new java.awt.Color(255, 255, 249));

        jPanel2.setBackground(new java.awt.Color(255, 255, 249));

        jPanel4.setBackground(new java.awt.Color(0, 95, 115));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Username:");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Password:");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Rol del Usuario:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addComponent(cboRol, 0, 160, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPassword)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboRol)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 95, 115));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DNI (Validar Nombre):");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Correo (@dominio):");

        txtDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniActionPerformed(evt);
            }
        });
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDniKeyReleased(evt);
            }
        });

        txtNombreUsuario.setBackground(new java.awt.Color(60, 60, 60));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre de la Persona:");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Celular:");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Codigo:");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("U-#");

        chkActivo.setForeground(new java.awt.Color(255, 255, 255));
        chkActivo.setText("Elija el estado de la cuenta");
        chkActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActivoActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Estado del Usuario");

        btnValidar.setBackground(new java.awt.Color(255, 140, 0));
        btnValidar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnValidar.setForeground(new java.awt.Color(255, 255, 255));
        btnValidar.setText("Validar Dni");
        btnValidar.setBorderPainted(false);
        btnValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(txtCorreoUsuario)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCelular, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnValidar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtNombreUsuario)
                                .addContainerGap())))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnValidar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActivo))
                .addContainerGap())
        );

        jLabel15.setBackground(new java.awt.Color(150, 111, 51));
        jLabel15.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 95, 115));
        jLabel15.setText("SIGEBIM - USUARIOS");

        jPanel1.setBackground(new java.awt.Color(0, 95, 115));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblUsuarios);

        btnRegistrar.setBackground(new java.awt.Color(152, 251, 152));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(64, 64, 64));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setBorderPainted(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(204, 85, 61));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorderPainted(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiarYMostrar.setBackground(new java.awt.Color(255, 140, 0));
        btnLimpiarYMostrar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnLimpiarYMostrar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarYMostrar.setText("Limpiar y Mostrar Lista");
        btnLimpiarYMostrar.setBorderPainted(false);
        btnLimpiarYMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarYMostrarActionPerformed(evt);
            }
        });

        cboVariable.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        cboVariable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elegir Variable a buscar" }));

        btnBuscar.setBackground(new java.awt.Color(60, 60, 60));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiarYMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                        .addComponent(cboVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboVariable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBuscar)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiarYMostrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(412, 412, 412)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try{
            if(personaReniec == null || !personaReniec.isValido()){
                JOptionPane.showMessageDialog(this,"Primero debe comprobar el DNI.");
                return;
            }
            Usuario usuario = obtenerUsuarioFormulario();
            adminService.registrar(usuario);
            JOptionPane.showMessageDialog(this,"Usuario registrado correctamente.");
            limpiar();
            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(this,
                ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarActionPerformed
        personaReniec = usuarioService.validarDni(txtDni.getText().trim());
        //Hacemos el cambio visual en el form
        if(personaReniec.isValido()){
            txtNombreUsuario.setText(personaReniec.getNombreCompleto());
            txtNombreUsuario.setBackground(Color.WHITE);
            btnValidar.setEnabled(false);
        }
        //Mostramos mensaje de exito o error
        JOptionPane.showMessageDialog(this,personaReniec.getMensaje());
    }//GEN-LAST:event_btnValidarActionPerformed

    private void txtDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniActionPerformed

    private void txtDniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyReleased
        personaReniec = null;
        txtNombreUsuario.setText("");
        txtNombreUsuario.setBackground(Color.GRAY);
        btnValidar.setEnabled(true);
    }//GEN-LAST:event_txtDniKeyReleased

    private void chkActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActivoActionPerformed
        actualizarTextoActivo();
    }//GEN-LAST:event_chkActivoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tblUsuarios.getSelectedRow();
        if(fila ==-1){
            JOptionPane.showMessageDialog(this, "Seleccione un suario.");
            return;
        }
        try{
            Usuario seleccionado = adminService.listar().get(fila);
            int opcion = JOptionPane.showConfirmDialog(this,
            "¿Desea editar al autor: " + seleccionado.getNombre() + "? \n "
            + "Recuerda que esto se repercutira en todos sus prestamos y multas asociadas",
            "Confirmar edición",JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                Usuario usuario = obtenerUsuarioFormulario();
                adminService.actualizar(fila,usuario);
                limpiar();
                }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblUsuarios.getSelectedRow();
        if(fila ==-1){
            JOptionPane.showMessageDialog(this, "Seleccione un suario.");
            return;
        }
        Usuario seleccionado = adminService.listar().get(fila);
        int opcion = JOptionPane.showConfirmDialog(this,
        "¿Desea eliminar al usuario: " + seleccionado.getNombre() + "?",
        "Confirmar eliminación",JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        boolean eliminado = adminService.eliminar(fila);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Se borro correctamente al usuario: " + seleccionado.getNombre());
            limpiar();
            } else {
            JOptionPane.showMessageDialog(this,"No se puede eliminar: el usuario tiene aun prestamos no devueltos o multas por pagar.");
            }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarYMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarYMostrarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarYMostrarActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        int fila = tblUsuarios.getSelectedRow();
        if(fila==-1){
            return;
        }
        Usuario usuario = adminService.listar().get(fila);
        mostrarUsuario(usuario);
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String dato = txtBuscar.getText().trim();
        if(dato.isBlank()){
            JOptionPane.showMessageDialog(this,"Ingrese un dato.");
            return;
        }
        ArrayList<Usuario> resultado = new ArrayList<>();
        switch (cboVariable.getSelectedItem().toString()){
            case "DNI" -> {
                Usuario u = adminService.buscarPorDni(dato);
                if(u!=null){
                    resultado.add(u);
                }
            }
            case "Username" -> {
                Usuario u = adminService.buscarPorUsername(dato);
                if(u!=null){
                    resultado.add(u);
                }
            }
        }
        mostrarLista(resultado);
        if(resultado.isEmpty()){
            JOptionPane.showMessageDialog(this, "No se encontraron usuarios ocn ese termino de busqueda");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiarYMostrar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnValidar;
    private javax.swing.JComboBox<String> cboRol;
    private javax.swing.JComboBox<String> cboVariable;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTree jTree1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreoUsuario;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
