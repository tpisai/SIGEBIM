/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista.JInternalFrame.Consultas;

import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.enums.RolUsuario;
import servicios.respaldos.RegistroActividadService;
import modelo.reporte.RegistroActividad;

/**
 *
 * @author zulmi
 */
public class ConsultarRegistroActividad extends javax.swing.JInternalFrame {

    /**
     * Creates new form ConsultarRegistroActividad
     */
    private final RegistroActividadService registroService;
    private final DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    private void configurarTabla() {
        DefaultTableModel modeloTabla = new DefaultTableModel(
                new Object[]{"Código", "Fecha y hora", "Usuario", "Rol", "Módulo", "Acción"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tblRegistros.setModel(modeloTabla);
        tblRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        txtDetalle.setEditable(false);
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);
    }
    
    private void configurarFiltros() {
        cboTipoFiltro.removeAllItems();
        cboTipoFiltro.addItem("Todos");
        cboTipoFiltro.addItem("Usuario");
        cboTipoFiltro.addItem("Rol");
        cboTipoFiltro.addItem("Módulo");
        cboRol.removeAllItems();
        for (RolUsuario rol : RolUsuario.values()) {
            cboRol.addItem(rol);
        }
        configurarCamposFiltro();
    }
    
    private void configurarCamposFiltro() {
        String filtro = (String) cboTipoFiltro.getSelectedItem();
        boolean usaValor = "Usuario".equals(filtro) || "Módulo".equals(filtro);
        boolean usaRol = "Rol".equals(filtro);
        txtValor.setEnabled(usaValor);
        cboRol.setEnabled(usaRol);
        lblValor.setEnabled(usaValor);
        lblRol.setEnabled(usaRol);
    }
    
    private void mostrarDetalleSeleccionado() {
        int fila = tblRegistros.getSelectedRow();
        if (fila == -1) {
            txtDetalle.setText("");
            return;
        }
        String codigo = tblRegistros.getValueAt(fila, 0).toString();
        RegistroActividad seleccionado = null;
        for (RegistroActividad registro : registroService.listar()) {
            if (registro.getCodigo().equals(codigo)) {
                seleccionado = registro;
                break;
            }
        }
        if (seleccionado == null) {
            txtDetalle.setText("");
            return;
        }
        txtDetalle.setText("Código: " + seleccionado.getCodigo()
                + "\nFecha y hora: " + seleccionado.getFechaHora().format(formatoFechaHora)
                + "\nUsuario: " + seleccionado.getUsuario()
                + "\nRol: " + seleccionado.getRol()
                + "\nMódulo: " + seleccionado.getModulo()
                + "\nAcción: " + seleccionado.getAccion()
                + "\nResultado: " + seleccionado.getResultado()
                + "\n\nDetalle:\n" + seleccionado.getDetalle());
        txtDetalle.setCaretPosition(0);
    }
    
    private void cargarRegistros(ArrayList<RegistroActividad> registros) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tblRegistros.getModel();
        modeloTabla.setRowCount(0);
        for (int i = registros.size() - 1; i >= 0; i--) {
            RegistroActividad registro = registros.get(i);
            modeloTabla.addRow(new Object[]{
                registro.getCodigo(),
                registro.getFechaHora().format(formatoFechaHora),
                registro.getUsuario(),
                registro.getRol(),
                registro.getModulo(),
                registro.getAccion(),
                registro.getResultado()
            });
        }
        txtDetalle.setText("");
        if (registros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron registros de actividad.", 
                    "Registro de actividad",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void configurarFechas() {
        dcFechaInicial.setDateFormatString("dd/MM/yyyy");
        dcFechaFinal.setDateFormatString("dd/MM/yyyy");
        // Comienzan sin una fecha seleccionada.
        dcFechaInicial.setDate(null);
        dcFechaFinal.setDate(null);
        // Impide que el usuario escriba manualmente.
        dcFechaInicial.getDateEditor().setEnabled(false);
        dcFechaFinal.getDateEditor().setEnabled(false);
    }
    
    private LocalDate obtenerFecha(JDateChooser dateChooser) {
        Date fechaSeleccionada = dateChooser.getDate();
        if (fechaSeleccionada == null) {
            return null;
        }
        return fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    //
    // CONSTRUCTOR NO TOCAR!!!
    public ConsultarRegistroActividad(RegistroActividadService registroActividadService) {
        initComponents();
        this.registroService = registroActividadService;
        configurarFechas();
        configurarTabla();
        configurarFiltros();
        cargarRegistros(registroService.listar());
        util.TablaUtil.aplicarEstiloYFormato(jScrollPane1, tblRegistros);
    }
    //
    //
    private void limpiar(){
        cboTipoFiltro.setSelectedItem("Todos");
        txtValor.setText("");
        dcFechaInicial.setDate(null);
        dcFechaFinal.setDate(null);
        if (cboRol.getItemCount() > 0) {
            cboRol.setSelectedIndex(0);
        }
        configurarCamposFiltro();
        cargarRegistros(registroService.listar());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jDialog3 = new javax.swing.JDialog();
        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cboTipoFiltro = new javax.swing.JComboBox<>();
        lblFechaInicial = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        lblRol = new javax.swing.JLabel();
        cboRol = new javax.swing.JComboBox<>();
        lblFechaFinal = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        dcFechaFinal = new com.toedter.calendar.JDateChooser();
        dcFechaInicial = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDetalle = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRegistros = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel15.setBackground(new java.awt.Color(150, 111, 51));
        jLabel15.setFont(new java.awt.Font("Segoe Print", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 95, 115));
        jLabel15.setText("SIGEBIM - Registro de Actividad de Usuarios");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel13.setText("Criterio:");

        cboTipoFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoFiltroActionPerformed(evt);
            }
        });

        lblFechaInicial.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblFechaInicial.setText("Desde:");

        lblValor.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblValor.setText("Valor:");

        lblRol.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblRol.setText("Rol:");

        lblFechaFinal.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblFechaFinal.setText("Hasta:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 249));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/escoba.png"))); // NOI18N
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(cboTipoFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblValor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblRol)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiar)
                                .addGap(53, 53, 53))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblFechaInicial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcFechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaFinal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTipoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblValor)
                            .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRol))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaFinal)
                            .addComponent(dcFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lblFechaInicial))
                            .addComponent(dcFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel19.setText("Detalle del Registro Seleccionado:");

        txtDetalle.setColumns(20);
        txtDetalle.setRows(5);
        jScrollPane2.setViewportView(txtDetalle);

        tblRegistros.setBackground(new java.awt.Color(101, 67, 33));
        tblRegistros.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblRegistros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRegistrosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRegistros);

        jScrollPane3.setViewportView(jScrollPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/close.png"))); // NOI18N
        btnClose.setBorder(null);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(31, 31, 31)
                        .addComponent(btnClose)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnClose)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cboTipoFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoFiltroActionPerformed
        configurarCamposFiltro();
    }//GEN-LAST:event_cboTipoFiltroActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String filtro = (String) cboTipoFiltro.getSelectedItem();
        if (filtro == null) {
            return;
        }
        LocalDate fechaInicial = obtenerFecha(dcFechaInicial);
        LocalDate fechaFinal = obtenerFecha(dcFechaFinal);
        if (fechaInicial == null || fechaFinal == null) {
            JOptionPane.showMessageDialog(this,"Seleccione la fecha inicial y final.",
            "Fechas requeridas",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (fechaInicial.isAfter(fechaFinal)) { 
            JOptionPane.showMessageDialog(this,
            "La fecha inicial no puede ser posterior a la fecha final.","Rango Incorrecto",
            JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<RegistroActividad> resultados = registroService.buscarPorRangoFechas(fechaInicial, fechaFinal);
        switch (filtro) {
            case "Usuario": String username = txtValor.getText().trim();
                if(username.isEmpty()){
                    JOptionPane.showMessageDialog(this,"Ingrese el nombre de usuario.",
                        "Dato requerido",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                resultados.retainAll(registroService.buscarPorUsuario(username));
                break;
            case "Rol": RolUsuario rol = (RolUsuario) cboRol.getSelectedItem();
                if (rol == null) {
                    JOptionPane.showMessageDialog(this,"Seleccione un rol.",
                    "Dato requerido",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                resultados.retainAll(registroService.buscarPorRol(rol));
                break;
            case "Módulo": String modulo = txtValor.getText().trim();
                if(modulo.isEmpty()){
                    JOptionPane.showMessageDialog(this,"Ingrese el nombre del modulo.",
                    "Dato requerido",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                resultados.retainAll(registroService.buscarPorModulo(modulo));
                break;
            case "Todos":/*No se aplica algun criterio mas*/ break;
        }
        cargarRegistros(resultados);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblRegistrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRegistrosMouseClicked
        mostrarDetalleSeleccionado();
    }//GEN-LAST:event_tblRegistrosMouseClicked

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<RolUsuario> cboRol;
    private javax.swing.JComboBox<String> cboTipoFiltro;
    private com.toedter.calendar.JDateChooser dcFechaFinal;
    private com.toedter.calendar.JDateChooser dcFechaInicial;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblFechaFinal;
    private javax.swing.JLabel lblFechaInicial;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblValor;
    private javax.swing.JTable tblRegistros;
    private javax.swing.JTextArea txtDetalle;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
