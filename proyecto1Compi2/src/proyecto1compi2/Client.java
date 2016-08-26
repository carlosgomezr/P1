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
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import java.io.Serializable;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class Client{
    private ObjectOutputStream salida;
    private String servidorChat;
    private Socket cliente;
 
    public Client( String host ) {
        servidorChat = host; // establecer el servidor al que se va a conectar este cliente
    }
    private void ejecutarCliente() {
        try {
            conectarAServidor(); // Paso 1: crear un socket para realizar la conexión
            salida = new ObjectOutputStream( cliente.getOutputStream() );
            salida.flush(); // vacíar búfer de salida para enviar información de encabezado
            System.out.println( "\nSe recibieron los flujos de E/S\n" );
            enviarDatos();
        } catch ( EOFException excepcionEOF ) {
            System.err.println( "El cliente termino la conexión" );
        }
 
        // procesar los problemas que pueden ocurrir al comunicarse con el servidor
        catch ( IOException excepcionES ) {
            excepcionES.printStackTrace();
        }
 
        finally {
            cerrarConexion(); // Paso 4: cerrar la conexión
        }
 
    } // fin del método ejecutarCliente
 
    // conectarse al servidor
    private void conectarAServidor() throws IOException {
        System.out.println( "Intentando realizar conexión\n" );
        // crear Socket para realizar la conexión con el servidor
        cliente = new Socket( InetAddress.getByName( servidorChat ), 12345 );
        // mostrar la información de la conexión
        System.out.println( "Conectado a: " +
                cliente.getInetAddress().getHostName() );
    }
    // cerrar flujos y socket
    private void cerrarConexion() {
        System.out.println( "\nCerrando conexión" );
        try {
            salida.close();
            cliente.close();
        } catch( IOException excepcionES ) {
            excepcionES.printStackTrace();
        }
    }
    private void enviarDatos( ) {
        Rectangle rectangleTam = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            Robot robot = new Robot();
            BufferedImage bufferedImage = robot.createScreenCapture(rectangleTam);
            ByteArrayOutputStream salidaImagen = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", salidaImagen);
            byte[] bytesImagen = salidaImagen.toByteArray();
            salida.writeObject( bytesImagen );
            salida.flush();
            System.out.println( "Se ha enviado la imagen" );
        } catch (AWTException e) {
            e.printStackTrace();
        } // procesar los problemas que pueden ocurrir al enviar el objeto
        catch ( IOException excepcionES ) {
            System.out.println( "\nError al escribir el objeto" );
        }
    }
 
    
    /*
    public static void main( String args[] ) {
        Client aplicacion;
 
        if ( args.length == 0 )
            aplicacion = new Client( "127.0.0.1" );
        else
            aplicacion = new Client( args[ 0 ] );
 
        aplicacion.ejecutarCliente();
    }
    */
} // fin de la clase Cliente
