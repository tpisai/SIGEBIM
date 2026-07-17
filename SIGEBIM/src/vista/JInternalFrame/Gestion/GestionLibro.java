/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista.JInternalFrame.Gestion;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.enums.CriterioBusqueda;
import modelo.enums.EstadoLibro;
import modelo.enums.TipoLibro;
import modelo.libro.Autor;
import modelo.libro.Categoria;
import modelo.libro.Libro;
import servicios.libros.AutorService;
import servicios.libros.CategoriaService;
import servicios.libros.LibroService;

/**
 *
 * @author zulmi
 */
public class GestionLibro extends javax.swing.JInternalFrame {

    /**
     * Creates new form GestionLibro2
     */
    private final LibroService servicioLibro = new LibroService();
    private final AutorService servicioAutor = new AutorService();
    private final CategoriaService servicioCategoria = new CategoriaService();
    private ArrayList<Libro> listaLibros = new ArrayList<>();
    private ArrayList<Libro> listaMostrada = new ArrayList<>();
    
    private void cargarAutores(){
        cboAutor.removeAllItems();
        for(Autor autor : servicioAutor.listar()){
            cboAutor.addItem(autor);
        }
    }
    private void cargarCategorias(){
        cboCategoria.removeAllItems();
        cboBuscarCategoria.removeAllItems();
        for(Categoria categoria : servicioCategoria.listar()){
            cboCategoria.addItem(categoria);
            cboBuscarCategoria.addItem(categoria);
        }
    }
    private void cargarTiposLibro(){
        cboTipoLibro.removeAllItems();
        for(TipoLibro tipo : TipoLibro.values()){
            cboTipoLibro.addItem(tipo);
        }
    }
    private void cargarBusqueda(){
        cboBusqueda.removeAllItems();
        cboBusqueda.addItem("ISBN");
        cboBusqueda.addItem("Título");
        cboBusqueda.addItem("Autor");
    }
    private void actualizarEstado(){
        try{
            int stock = Integer.parseInt(txtStock.getText());
            EstadoLibro estado = EstadoLibro.desdeStock(stock);
            txtEstado.setText(estado.getTexto());
        }catch(Exception e){
            txtEstado.setText("");
        }
    }
    private void limpiarCampos(){
        txtISBN.setText("");
        txtTitulo.setText("");
        txtAnio.setText("");
        txtDescripcion.setText("");
        txtStock.setText("");
        txtUbicacion.setText("");
        txtURL.setText("");
        txtEstado.setText("");
        txtBuscar.setText("");
        if(cboAutor.getItemCount()>0) {
            cboAutor.setSelectedIndex(0);
        }
        if(cboCategoria.getItemCount()>0){
            cboCategoria.setSelectedIndex(0);
        }
        cboTipoLibro.setSelectedIndex(0);
        txtISBN.requestFocus();
        habilitarCamposSegunTipo();
        actualizarEstadoAutomatico();
    }
    private void configurarTabla(){
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int fila,int columna){
                return false;
            }
        };
        modelo.addColumn("ISBN");
        modelo.addColumn("Título");
        modelo.addColumn("Autor");
        modelo.addColumn("Categoría");
        modelo.addColumn("Año");
        modelo.addColumn("Tipo");
        modelo.addColumn("Stock");
        modelo.addColumn("Estado");
        modelo.addColumn("Ubicación");
        tblLibros.setModel(modelo);
        ajustarColumnas();
    }
    private void ajustarColumnas(){
        TableColumn columna;
        columna = tblLibros.getColumnModel().getColumn(0);
        columna.setPreferredWidth(110);
        columna = tblLibros.getColumnModel().getColumn(1);
        columna.setPreferredWidth(220);
        columna = tblLibros.getColumnModel().getColumn(2);
        columna.setPreferredWidth(150);
        columna = tblLibros.getColumnModel().getColumn(3);
        columna.setPreferredWidth(150);
        columna = tblLibros.getColumnModel().getColumn(4);
        columna.setPreferredWidth(70);
        columna = tblLibros.getColumnModel().getColumn(5);
        columna.setPreferredWidth(80);
        columna = tblLibros.getColumnModel().getColumn(6);
        columna.setPreferredWidth(60);
        columna = tblLibros.getColumnModel().getColumn(7);
        columna.setPreferredWidth(170);
        columna = tblLibros.getColumnModel().getColumn(8);
        columna.setPreferredWidth(120);
        tblLibros.setRowHeight(25); // Filas más cómodas
        tblLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo una fila
    }
    private void mostrarLibros(ArrayList<Libro> lista){
        listaMostrada = new ArrayList<>(lista);
        DefaultTableModel modelo = (DefaultTableModel) tblLibros.getModel();
        modelo.setRowCount(0);
        for(Libro libro : lista){
            Object[] fila = {
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getCategoria().getNombre(),
                libro.getAnioPublicacion(),
                libro.getTipoLibro().getTexto(),
                libro.getStock(),
                libro.getEstadoLibro().getTexto(),
                libro.getUbicacionEstante()
            };
            modelo.addRow(fila);
        }
    }
    private Libro obtenerLibroFormulario(){
        String isbn = txtISBN.getText().trim();
        String titulo = txtTitulo.getText().trim();
        Autor autor = (Autor) cboAutor.getSelectedItem();
        Categoria categoria = (Categoria) cboCategoria.getSelectedItem();
        int anio = Integer.parseInt(txtAnio.getText().trim());
        String descripcion = txtDescripcion.getText().trim();
        TipoLibro tipo = (TipoLibro) cboTipoLibro.getSelectedItem();
        int stock;
        String ubicacion;
        String url;
        if (tipo == TipoLibro.FISICO) {
            stock = Integer.parseInt(txtStock.getText().trim());
            ubicacion = txtUbicacion.getText().trim();
            url = "";
        } else {
            stock = 0;
            ubicacion = "";
            url = txtURL.getText().trim();
        }
        return new Libro(isbn,titulo,autor,categoria,anio,descripcion,stock,"",tipo,ubicacion,url);
    }
    private void cargarLibroSeleccionado(){
        int fila = tblLibros.getSelectedRow();
        if(fila==-1){
            return;
        }
        Libro libro = listaMostrada.get(fila);
        txtISBN.setText(libro.getIsbn());
        txtTitulo.setText(libro.getTitulo());
        seleccionarAutor(libro.getAutor());
        seleccionarCategoria(libro.getCategoria());
        txtAnio.setText(String.valueOf(libro.getAnioPublicacion()));
        txtDescripcion.setText(libro.getDescripcion());
        cboTipoLibro.setSelectedItem(libro.getTipoLibro());
        habilitarCamposSegunTipo();
        txtStock.setText(String.valueOf(libro.getStock()));
        txtEstado.setText(libro.getEstadoLibro().getTexto());
        txtUbicacion.setText(libro.getUbicacionEstante());
        txtURL.setText(libro.getUrlAcceso());
    }
    private void refrescarTabla(){
        listaLibros = servicioLibro.listar();
        mostrarLibros(listaLibros);
    }
    private void habilitarCamposSegunTipo(){
        TipoLibro tipo = (TipoLibro) cboTipoLibro.getSelectedItem();
        if (tipo == null) {
            return;
        }
        if (tipo == TipoLibro.FISICO) {
            txtStock.setEnabled(true);
            txtUbicacion.setEnabled(true);
            txtURL.setEnabled(false);
            txtURL.setText("");
        } else {
            txtStock.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtStock.setText("0");
            txtUbicacion.setText("");
            txtURL.setEnabled(true);
        }
        actualizarEstadoAutomatico();
    }
    private void actualizarEstadoAutomatico(){
        try{
            int stock = Integer.parseInt(txtStock.getText());
            txtEstado.setText(EstadoLibro.desdeStock(stock).getTexto());
        }catch(Exception e){
            txtEstado.setText("");
        }
    }
    private void seleccionarLibroPorISBN(String isbn){
        for(int i=0;i<tblLibros.getRowCount();i++){
            if(tblLibros.getValueAt(i,0).toString().equalsIgnoreCase(isbn)){
                tblLibros.setRowSelectionInterval(i,i);
                break;
            }
        }
    }
    //
    //Constructor no tocar!!!
    public GestionLibro() {
        super("Gestion Libro - Seccion Bibliotecario", true, true, true, true);
        initComponents();
        cargarAutores();
        cargarCategorias();
        cargarTiposLibro();
        cargarBusqueda();
        configurarTabla();
        mostrarLibros(servicioLibro.listar());
        actualizarEstado();
        txtEstado.setEditable(false);
        habilitarCamposSegunTipo();
    }
    //
    //Metodos de seleccion (apoyo)
    private void seleccionarAutor(Autor autorLibro) {
        for (int i = 0; i < cboAutor.getItemCount(); i++) {
            Autor autor = cboAutor.getItemAt(i);
            if (autor.getId().equalsIgnoreCase(autorLibro.getId())) {
                cboAutor.setSelectedIndex(i);
                return;
            }
        }
    }
    private void seleccionarCategoria(Categoria categoriaLibro) {
        for (int i = 0; i < cboCategoria.getItemCount(); i++) {
            Categoria categoria = cboCategoria.getItemAt(i);
            if (categoria.getId().equalsIgnoreCase(categoriaLibro.getId())) {
                cboCategoria.setSelectedIndex(i);
                return;
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        txtTitulo = new javax.swing.JTextField();
        cboAutor = new javax.swing.JComboBox<>();
        cboCategoria = new javax.swing.JComboBox<>();
        txtAnio = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        cboTipoLibro = new javax.swing.JComboBox<>();
        txtEstado = new javax.swing.JTextField();
        txtUbicacion = new javax.swing.JTextField();
        txtURL = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cboBusqueda = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cboBuscarCategoria = new javax.swing.JComboBox<>();
        btnBuscarCategoria = new javax.swing.JButton();
        btnMostrarTodo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLibros = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 249));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Georgia", 3, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 215, 158));
        jLabel15.setText("SIGEBIM - GESTION TOTAL DE LIBROS");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, -1, 40));

        jPanel2.setBackground(new java.awt.Color(61, 26, 26));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 215, 158));
        jLabel1.setText("ISBN:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 11, -1, -1));

        jLabel2.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 215, 158));
        jLabel2.setText("Titulo:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 11, -1, -1));

        jLabel3.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 215, 158));
        jLabel3.setText("Autor:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 11, 73, -1));

        jLabel4.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 215, 158));
        jLabel4.setText("Categoria:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 76, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 215, 158));
        jLabel5.setText("Año de Publicacion:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 36, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 215, 158));
        jLabel6.setText("Ubicacion:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 64, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 215, 158));
        jLabel7.setText("Stock:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 36, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 215, 158));
        jLabel8.setText("Tipo:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 36, -1, -1));

        jLabel9.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 215, 158));
        jLabel9.setText("Descripcion:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 96, -1, 38));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 215, 158));
        jLabel10.setText("URL:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 64, -1, -1));

        jLabel11.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 215, 158));
        jLabel11.setText("Estado:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 67, 60, -1));

        txtISBN.setBackground(new java.awt.Color(242, 224, 189));
        txtISBN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtISBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 106, -1));

        txtTitulo.setBackground(new java.awt.Color(242, 224, 189));
        txtTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 10, 310, -1));

        cboAutor.setBackground(new java.awt.Color(242, 224, 189));
        cboAutor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(cboAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(667, 8, 210, -1));

        cboCategoria.setBackground(new java.awt.Color(242, 224, 189));
        cboCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(cboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(667, 36, 210, -1));

        txtAnio.setBackground(new java.awt.Color(242, 224, 189));
        txtAnio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 38, 110, -1));

        txtStock.setBackground(new java.awt.Color(242, 224, 189));
        txtStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockKeyReleased(evt);
            }
        });
        jPanel2.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 38, 80, -1));

        cboTipoLibro.setBackground(new java.awt.Color(242, 224, 189));
        cboTipoLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoLibroActionPerformed(evt);
            }
        });
        jPanel2.add(cboTipoLibro, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 36, 120, -1));

        txtEstado.setBackground(new java.awt.Color(242, 224, 189));
        txtEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 66, 106, -1));

        txtUbicacion.setBackground(new java.awt.Color(242, 224, 189));
        txtUbicacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 66, 166, -1));

        txtURL.setBackground(new java.awt.Color(242, 224, 189));
        txtURL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(txtURL, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 66, 400, -1));

        txtDescripcion.setBackground(new java.awt.Color(242, 224, 189));
        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        txtDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 770, 38));

        jLabel14.setText("jLabel14");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, -1, -1));

        jLabel18.setText("jLabel18");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 34, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(17, 92, 58));
        btnRegistrar.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 215, 158));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 182, 138, 35));

        btnActualizar.setBackground(new java.awt.Color(27, 73, 125));
        btnActualizar.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 215, 158));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 182, 155, 35));

        btnEliminar.setBackground(new java.awt.Color(166, 66, 34));
        btnEliminar.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 215, 158));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 182, 144, 35));

        btnLimpiar.setBackground(new java.awt.Color(255, 215, 158));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/escoba.png"))); // NOI18N
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 182, -1, -1));

        jPanel3.setBackground(new java.awt.Color(61, 26, 26));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 215, 158));
        jLabel12.setText("Buscar Por:");

        cboBusqueda.setBackground(new java.awt.Color(242, 224, 189));
        cboBusqueda.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        cboBusqueda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtBuscar.setBackground(new java.awt.Color(242, 224, 189));
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(212, 163, 89));
        btnBuscar.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 215, 158));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 215, 158));
        jLabel13.setText("Filtrar Categoria:");

        cboBuscarCategoria.setBackground(new java.awt.Color(242, 224, 189));
        cboBuscarCategoria.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        cboBuscarCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnBuscarCategoria.setBackground(new java.awt.Color(212, 163, 89));
        btnBuscarCategoria.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        btnBuscarCategoria.setForeground(new java.awt.Color(255, 215, 158));
        btnBuscarCategoria.setText("Buscar por Categoria");
        btnBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCategoriaActionPerformed(evt);
            }
        });

        btnMostrarTodo.setBackground(new java.awt.Color(212, 163, 89));
        btnMostrarTodo.setFont(new java.awt.Font("Georgia", 2, 14)); // NOI18N
        btnMostrarTodo.setForeground(new java.awt.Color(255, 215, 158));
        btnMostrarTodo.setText("Mostrar Todo");
        btnMostrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarTodoActionPerformed(evt);
            }
        });

        tblLibros.setBackground(new java.awt.Color(242, 224, 189));
        tblLibros.setForeground(new java.awt.Color(28, 12, 12));
        tblLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblLibros.setGridColor(new java.awt.Color(140, 106, 74));
        tblLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLibrosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLibros);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboBusqueda, 0, 158, Short.MAX_VALUE)
                            .addComponent(cboBuscarCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMostrarTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cboBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar))
                    .addComponent(txtBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(cboBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarCategoria))
                    .addComponent(btnMostrarTodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 223, 885, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo.png"))); // NOI18N
        jLabel19.setText("jLabel19");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 680));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStockKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyReleased
        actualizarEstadoAutomatico();
    }//GEN-LAST:event_txtStockKeyReleased

    private void cboTipoLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoLibroActionPerformed
        habilitarCamposSegunTipo();
    }//GEN-LAST:event_cboTipoLibroActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try{
            Libro libro = obtenerLibroFormulario();
            servicioLibro.registrar(libro);
            refrescarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this,"Libro registrado correctamente.");
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Año y Stock deben ser numéricos.");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        int fila = tblLibros.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(this,"Seleccione un libro.");
            return;
        }
        try{
            Libro libro = obtenerLibroFormulario();
            int indiceOriginal = listaLibros.indexOf(listaMostrada.get(fila));
            servicioLibro.actualizar(indiceOriginal, libro);
            refrescarTabla();
            seleccionarLibroPorISBN(libro.getIsbn());
            JOptionPane.showMessageDialog(this,"Libro actualizado correctamente.");
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Datos numéricos inválidos.");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblLibros.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(this,"Seleccione un libro.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,"¿Eliminar este libro?",
            "Confirmar",JOptionPane.YES_NO_OPTION);
        if(respuesta==JOptionPane.YES_OPTION){
            int indiceOriginal = listaLibros.indexOf(listaMostrada.get(fila));
            servicioLibro.eliminar(indiceOriginal);
            refrescarTabla();
            limpiarCampos();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String texto = txtBuscar.getText().trim();
        CriterioBusqueda criterio = switch(cboBusqueda.getSelectedIndex()){
            case 0 -> CriterioBusqueda.ISBN;
            case 1 -> CriterioBusqueda.TITULO;
            default -> CriterioBusqueda.AUTOR;
        };
        mostrarLibros(servicioLibro.buscar(criterio,texto)
        );
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCategoriaActionPerformed
        Categoria categoria = (Categoria)cboBuscarCategoria.getSelectedItem();
        if(categoria==null){
            return;
        }
        mostrarLibros(servicioLibro.buscar(CriterioBusqueda.CATEGORIA,categoria.getNombre()
        )
        );
    }//GEN-LAST:event_btnBuscarCategoriaActionPerformed

    private void btnMostrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodoActionPerformed
        refrescarTabla();
    }//GEN-LAST:event_btnMostrarTodoActionPerformed

    private void tblLibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLibrosMouseClicked
        cargarLibroSeleccionado();
    }//GEN-LAST:event_tblLibrosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarCategoria;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMostrarTodo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<Autor> cboAutor;
    private javax.swing.JComboBox<Categoria> cboBuscarCategoria;
    private javax.swing.JComboBox<String> cboBusqueda;
    private javax.swing.JComboBox<Categoria> cboCategoria;
    private javax.swing.JComboBox<TipoLibro> cboTipoLibro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLibros;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtURL;
    private javax.swing.JTextField txtUbicacion;
    // End of variables declaration//GEN-END:variables
}
