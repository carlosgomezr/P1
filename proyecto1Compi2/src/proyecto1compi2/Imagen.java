/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1compi2;

/**
 *
 * @author Carlos Gomez
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Imagen extends javax.swing.JPanel {

    int x, y;

    public Imagen(jPaneColor jPanel1) {
        this.x = jPanel1.getWidth();
        this.y = jPanel1.getHeight();
        this.setSize(x, y);
    }

    @Override
    public void paint(Graphics g) {

        try {

            // Se carga la imagen original
            BufferedImage imagen;
            File f = new File("src/imagen2.png");
            imagen = ImageIO.read(f);
            Graphics2D g2 = (Graphics2D) imagen.getGraphics();

            //Stroke stroke = new BasicStroke((float) 20); // Grosor pincel
            //g2.setStroke(stroke);
            //g2.setColor(Color.RED);
            //g2.setRenderingHint(
            //        RenderingHints.KEY_ANTIALIASING,
            //        RenderingHints.VALUE_ANTIALIAS_ON); // Filtro antialiasing 
            //g2.drawLine(0, 0, imagen.getWidth(), imagen.getHeight()); // Línea diagonal

            
            // Se guarda la imagen y se imprime en el jPanel
            ImageIO.write(imagen, "png", new File("imagenxd.png"));
            g.drawImage(imagen, 0, 0, this);

        } catch (IOException ex) {  }

    }

}