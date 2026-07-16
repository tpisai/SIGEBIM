/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista.JInternalFrame;

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

        setBackground(new java.awt.Color(255, 255, 249));

        jPanel1.setBackground(new java.awt.Color(255, 255, 249));

        jLabel15.setBackground(new java.awt.Color(150, 111, 51));
        jLabel15.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 95, 115));
        jLabel15.setText("SIGEBIM - GESTION TOTAL DE LIBROS");

        jPanel2.setBackground(new java.awt.Color(0, 95, 115));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ISBN:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Titulo:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Autor:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Categoria:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Año de Publicacion:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ubicacion:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Stock:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tipo:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Descripcion:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("URL:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Estado:");

        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockKeyReleased(evt);
            }
        });

        cboTipoLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoLibroActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUbicacion))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cboTipoLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtURL)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cboAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cboTipoLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6)
                    .addComponent(txtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnRegistrar.setBackground(new java.awt.Color(152, 251, 152));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(60, 60, 60));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(204, 85, 61));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 249));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/escoba.png"))); // NOI18N
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 95, 115));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Buscar Por:");

        cboBusqueda.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(255, 140, 0));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Filtrar Categoria:");

        cboBuscarCategoria.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N

        btnBuscarCategoria.setBackground(new java.awt.Color(255, 140, 0));
        btnBuscarCategoria.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnBuscarCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCategoria.setText("Buscar por Categoria");
        btnBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCategoriaActionPerformed(evt);
            }
        });

        btnMostrarTodo.setBackground(new java.awt.Color(255, 140, 0));
        btnMostrarTodo.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnMostrarTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarTodo.setText("Mostrar Todo");
        btnMostrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarTodoActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jLabel15))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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
    private javax.swing.JLabel jLabel15;
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
