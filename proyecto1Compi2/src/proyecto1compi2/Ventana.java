/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1compi2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.JTabbedPane;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import jsyntaxpane.DefaultSyntaxKit;

/**
 *
 * @author Carlos Gomez
 */


public class Ventana extends javax.swing.JFrame {

//ArrayList estatico que contiene referencia de los JEditorPane dinamicos    
ArrayList<JEditorPane> ListaEditor = new ArrayList<JEditorPane>();
//ArrayList estatico que contiene referencia de los dibujos
ArrayList<dibujo> LDibujo = new ArrayList<dibujo>();
//ArrayList estatico que contiene la tabla de simbolos
ArrayList<Simbolo> LS = new ArrayList<Simbolo>();
ArrayList<Simbolo> LS2 = new ArrayList<Simbolo>();

//ArrayList estatico que contiene la tabla de errores
ArrayList<NodeError> LE = new ArrayList<NodeError>();
       


compilador ana = new compilador();
    
    /**
     * Creates new form Ventana
     */
    public Ventana() {
        initComponents();
        this.iniciarEditor();
    }
    
      private void iniciarEditor()
    {        
        ListaEditor.add(jEditorPane1);
        DefaultSyntaxKit.initKit();
        this.jEditorPane1.setContentType("text/java");
        this.jEditorPane1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                CursorPosition(evt);
            }
        });
      //  jEditorPane1.setFont(new Font("Comic Sans", jEditorPane1.getFont().getStyle(), 14));
    }

      private void iniciarEditor(JEditorPane editor){
        ListaEditor.add(editor);
        DefaultSyntaxKit.initKit();
        editor.setContentType("text/java");  
        // jEditorPane1.setFont(new Font("Comic Sans", jEditorPane1.getFont().getStyle(), 14));
    
        editor.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                CursorPosition(evt);
            }
        });
      }
      
      
      
      private void CursorPosition(javax.swing.event.CaretEvent evt) {                                 
        try 
        {
            int x= jTabbedPane1.getSelectedIndex();
            int caretPos = ListaEditor.get(x).getCaretPosition();
            int rowNum = (caretPos == 0) ? 1 : 0;
            for (int offset = caretPos; offset > 0;)
            {
                offset = Utilities.getRowStart(ListaEditor.get(x), offset) - 1;
                rowNum++;
            }
            int offset = Utilities.getRowStart(ListaEditor.get(x), caretPos);
            int colNum = caretPos - offset + 1;
            //lcaret.setText("Linea: "+rowNum+"       Columna: "+colNum);
            jLabel10.setText(rowNum+"");
            jLabel11.setText(colNum+"");
        } 
        catch (BadLocationException ex) 
        {Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);}
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
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
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico_images/ico_debug.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 14, 80, 80));

        jSlider1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jSlider1AncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        getContentPane().add(jSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico_images/ico_play.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 14, 80, 80));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico_images/ico_error.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, 80));

        jScrollPane2.setViewportView(jEditorPane1);

        jTabbedPane1.addTab("tab1", jScrollPane2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 182, 1190, 480));

        jLabel1.setText("Reporte de Errores");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, 111, -1));

        jLabel2.setText("Velocidad del Debugger");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 127, -1));

        jLabel3.setText("Ejecutar");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel4.setText("Debuggear");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        jLabel5.setText("10");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 50, -1));

        jLabel6.setText("10,000");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        jLabel7.setText("milisegundos");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        jLabel8.setText("Linea:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, -1, -1));

        jLabel9.setText("Columna:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 160, -1, -1));

        jLabel10.setText("0");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, -1, -1));

        jLabel11.setText("0");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, -1, -1));

        jLabel12.setText("0");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 30, -1));

        jButton4.setText("Close Tab");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 153, -1, 20));

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Nuevo Archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Nueva Pestaña");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Abrir");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Guardar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Guardar Como");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Salir");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Herramientas");

        jMenuItem7.setText("Ejecutar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setText("Debuggear");
        jMenu2.add(jMenuItem8);

        jMenuItem9.setText("Tabla de Simbolos");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Errores");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem10.setText("Lista de Errores");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
    
    int x= jTabbedPane1.getSelectedIndex();
    //JOptionPane.showMessageDialog(null, "Numero de TAB activada"+x,"TAB", JOptionPane.INFORMATION_MESSAGE);

        Funcion f = new Funcion();
        //f.enrutar();

        if(jTabbedPane1.getTitleAt(x).compareTo("")!=0){
            f.guardarArchivo(ListaEditor.get(x),jTabbedPane1.getTitleAt(x));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
    
    int x= jTabbedPane1.getSelectedIndex();
    //JOptionPane.showMessageDialog(null, "Numero de TAB activada"+x,"TAB", JOptionPane.INFORMATION_MESSAGE);
        Funcion f = new Funcion();
        String ruta = f.leer(ListaEditor.get(x));
        jTabbedPane1.setTitleAt(x, ruta);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

    int x= jTabbedPane1.getSelectedIndex();
    //JOptionPane.showMessageDialog(null, "Numero de TAB activada"+x,"TAB", JOptionPane.INFORMATION_MESSAGE);

        Funcion f = new Funcion();
        String ruta = f.enrutar();
        f.guardarcomoArchivo(ListaEditor.get(x),ruta);
        jTabbedPane1.setTitleAt(x, ruta);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jSlider1AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jSlider1AncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jSlider1AncestorMoved

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int x= jTabbedPane1.getSelectedIndex();
        String aux = ListaEditor.get(x).getText();
        /*final String UTF8_BOM = new String("\u00bf".getBytes(StandardCharsets.UTF_8));
        System.out.println("Archivo con boom \n"+aux);
        System.out.println("booom "+UTF8_BOM);
        aux = aux.replace("\u00bf",UTF8_BOM);
        System.out.println("Archivo sin boom \n"+aux);*/
        Funcion f = new Funcion();
        f.crearArchivo(aux);
    try {
        BufferedReader br = new BufferedReader(
                            new FileReader("arch.lz"));
        String s, s2 = new String();

        while ((s = br.readLine()) != null){
            s2 += s+"\n";
        }
        br.close(); 
  
        ana.analizar(s2);
        f.graphArbol(ana.root);
        ejecutar ejec = new ejecutar();
        Simbolo sim = new Simbolo();
        ejec.ejecucion(ana.root, LS,ana.root,sim,LE);
        System.out.println("TABLA DE SIMBOLOS \n");
        for(int i=0; i<LS.size();i++){
            System.out.println("Simbolo "+LS.get(i).id+" size "+LS.size()+"\n");
        }
        //System.out.println("Root \n Nombre root "+ana.root.nombre+" grafoname "+ana.root.grafoname+" cadena "+ana.root.cadena+" numero "+ana.root.numero); 
        //System.out.println("S\n Nombre "+ana.root.hijos.get(0).nombre+" grafoname "+ana.root.hijos.get(0).grafoname);
        //System.out.println("COMMM\n Nombre "+ana.root.hijos.get(0).hijos.get(0).nombre+" grafoname "+ana.root.hijos.get(0).hijos.get(0).grafoname);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
        Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int x;
        x = jSlider1.getValue() * 99 + 10; 
        JOptionPane.showMessageDialog(null, "Velocidad del Debugger "+x,"DEBUGGER", JOptionPane.INFORMATION_MESSAGE);
       
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jSlider1StateChanged

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //JTabbedPane j = new JTabbedPane();
        
        //----------Declaro un JEditPane y lo añado al JScrollPane
        JEditorPane editor = new javax.swing.JEditorPane();
        //DefaultSyntaxKit.initKit();
        //editor.setContentType("text/java");  
        
        
        //----------Declaro el JScrollPane y lo añado al jTabbedPane1
        JScrollPane scroll = new javax.swing.JScrollPane(editor);
        scroll.setBounds(0,0,810, 290);        
        jTabbedPane1.addTab("new", scroll);
        int x = 0;
        int y = x+= 0+2;
        this.iniciarEditor(editor);

        
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    int x= jTabbedPane1.getSelectedIndex();
    //JOptionPane.showMessageDialog(null, "Numero de TAB activada"+x,"TAB", JOptionPane.INFORMATION_MESSAGE);
    
        ListaEditor.remove(x);
        jTabbedPane1.remove(x);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        System.out.println("Tabla de Simbolos --\n");
        Funcion f = new Funcion();
        f.generarhtmlTS(LS);
        
        String x="2.5";
        String g="8";
        double resultado = (double)Math.pow(Double.parseDouble(x), Double.parseDouble(g));
        System.out.println("resultado "+resultado);        
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
         int x= jTabbedPane1.getSelectedIndex();
         ListaEditor.get(x).setText("");  
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        Funcion f = new Funcion();
        f.generarhtmlErrores(LE);
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        Panel p = new Panel();
        //circles
        dibujo d = new dibujo(0,0,"circulo","",0,0,20,Color.BLUE);
        dibujo d1 = new dibujo(20,0,"circulo","",0,0,30,Color.RED);
        dibujo d2 = new dibujo(50,60,"circulo","",0,0,40,Color.YELLOW);
        dibujo d3 = new dibujo(70,70,"circulo","",0,0,50,Color.BLACK);
        dibujo d4 = new dibujo(100,100,"circulo","",0,0,60,Color.GREEN);
        
        //rectangles
        dibujo d5 = new dibujo(120,120,"rectangulo","",10,10,0,Color.ORANGE);
        dibujo d6 = new dibujo(50,0,"rectangulo","",20,20,0,Color.DARK_GRAY);
        dibujo d7 = new dibujo(80,0,"rectangulo","",20,30,0,Color.MAGENTA);
        dibujo d8 = new dibujo(300,100,"rectangulo","",30,20,0,Color.CYAN);
        dibujo d9 = new dibujo(300,200,"rectangulo","",50,10,0,Color.PINK);
       
        
        //ovalos
        dibujo c1 = new dibujo(300,300,"ovalo","",10,10,0,Color.ORANGE);
        dibujo c2 = new dibujo(350,0,"ovalo","",20,20,0,Color.DARK_GRAY);
        dibujo c3 = new dibujo(200,0,"ovalo","",20,30,0,Color.MAGENTA);
        dibujo c4 = new dibujo(200,300,"ovalo","",30,20,0,Color.CYAN);
        dibujo c5 = new dibujo(300,150,"ovalo","",50,10,0,Color.PINK);
       
        
        //cadenas
        dibujo c6 = new dibujo(0,50,"cadena","hola",0,0,0,Color.BLUE);
        dibujo c7 = new dibujo(0,80,"cadena","mundo",0,0,0,Color.RED);
        dibujo c8 = new dibujo(90,60,"cadena","estoy pintando",0,0,0,Color.YELLOW);
        dibujo c9 = new dibujo(100,70,"cadena","para ver como se ve",0,0,0,Color.BLACK);
        dibujo c10 = new dibujo(150,100,"cadena","esto",0,0,0,Color.decode("#990000"));
        
        LDibujo.add(d);
        LDibujo.add(d1);
        LDibujo.add(d2);
        LDibujo.add(d3);
        LDibujo.add(d4);
        LDibujo.add(d5);        
        LDibujo.add(d6);        
        LDibujo.add(d7);
        LDibujo.add(d8);
        LDibujo.add(d9);
        LDibujo.add(c1);
        LDibujo.add(c2);
        LDibujo.add(c3);
        LDibujo.add(c4);
        LDibujo.add(c5);        
        LDibujo.add(c6);
        LDibujo.add(c7);
        LDibujo.add(c8);
        LDibujo.add(c9);
        LDibujo.add(c10);        
        
        for(int i=0;i<LDibujo.size();i++){
            p.LDibujo.add(LDibujo.get(i));
        }
        p.setVisible(true);
       
        /*
        JFrame frame = new JFrame( "Uso de colores" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        jPaneColor jPanelColor = new jPaneColor(); // create JPanelColor
        frame.add( jPanelColor ); // agrega jPanelColor a marco
        frame.setSize( 800, 400 ); // establece el tamaño del marco
        frame.setVisible( true ); // muestra el marco
        */
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
