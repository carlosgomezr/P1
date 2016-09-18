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
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class jPaneColor extends JPanel
{ 
    
public ArrayList<dibujo> LDibujo = new ArrayList<dibujo>();
public int espera;
// dibuja rectángulos y objetos String en distintos colores
public void paintComponent( Graphics g )
{
    super.paintComponent( g ); // llama el método paintComponent de la superclase
    this.setBackground( Color.WHITE );
    BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
    Graphics g2d = bufferedImage.createGraphics();
    g2d.fillRect(0, 0, 750, 600);
    
    for(int i=0;i<LDibujo.size();i++){
        if(LDibujo.get(i).figura.compareTo("ovalo")==0){
            
            Color color = LDibujo.get(i).color;
            g.setColor( color );
            g.fillOval(LDibujo.get(i).x, LDibujo.get(i).y, LDibujo.get(i).anchura, LDibujo.get(i).altura);
            
            g2d.setColor( color );
            g2d.fillOval(LDibujo.get(i).x, LDibujo.get(i).y, LDibujo.get(i).anchura, LDibujo.get(i).altura);
            
        }
        else if(LDibujo.get(i).figura.compareTo("circulo")==0){
            Color color = LDibujo.get(i).color;
            g.setColor( color );
            g.fillOval(LDibujo.get(i).x, LDibujo.get(i).y, LDibujo.get(i).diametro, LDibujo.get(i).diametro);
            
            g2d.setColor( color );
            g2d.fillOval(LDibujo.get(i).x, LDibujo.get(i).y, LDibujo.get(i).diametro, LDibujo.get(i).diametro);
        
        }
        else if(LDibujo.get(i).figura.compareTo("rectangulo")==0){
            Color color = LDibujo.get(i).color;
            g.setColor( color );
            g.fillRect(LDibujo.get(i).x, LDibujo.get(i).y, LDibujo.get(i).anchura, LDibujo.get(i).altura);
        
            g2d.setColor( color );
            g2d.fillRect(LDibujo.get(i).x, LDibujo.get(i).y, LDibujo.get(i).anchura, LDibujo.get(i).altura);
        
        }
        else if(LDibujo.get(i).figura.compareTo("cadena")==0){
            Color color = LDibujo.get(i).color;
            g.setColor( color );
            g.drawString(LDibujo.get(i).cadena, LDibujo.get(i).x, LDibujo.get(i).y );
            
            g2d.setColor( color );
            g2d.drawString(LDibujo.get(i).cadena, LDibujo.get(i).x, LDibujo.get(i).y );
        }
        /*try {
            Thread.sleep(3000);
            //wait(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(jPaneColor.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
    
            
            File file = new File("src/Imagen.png");

        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
    }      
        
        /*
        // establece nuevo color de dibujo, usando valores enteros
        g.setColor( new Color( 255, 0, 0 ) );
        g.fillRect( 15, 25, 100, 20 );
        g.drawString( "RGB actual: " + g.getColor(), 130, 40 );
        
        // establece nuevo color de dibujo, usando valores de punto flotante
        g.setColor( new Color( 0.50f, 0.75f, 0.0f ) );
        g.fillRect( 15, 50, 100, 20 );
        g.drawString( "RGB actual: " + g.getColor(), 130, 65 );
        
        // establece nuevo color de dibujo, usando objetos Color static
        g.setColor( Color.BLUE );
        g.fillRect( 15, 75, 100, 20 );
        g.drawString( "RGB actual: " + g.getColor(), 130, 90 );
        
        // muestra los valores RGB individuales
        Color color = Color.MAGENTA;
        g.setColor( color );
        g.fillRect( 15, 100, 100, 20 );
        g.drawString( "Valores RGB: " + color.getRed() + ", " +
        color.getGreen() + ", " + color.getBlue(), 130, 200 );
    */  
} // fin del método paintComponent

} // fin de la clase JPanelColor