/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1compi2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos Gomez
 */
public class Funcion {
      
String ruta = "";
public void limpiar(JTextArea cuerpo){
    cuerpo.setText("");
}

public String enrutar(){
        String path="";
	javax.swing.JFileChooser jF1= new javax.swing.JFileChooser(); 
        try{ 
	if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
	ruta = jF1.getSelectedFile().getAbsolutePath();
        path = jF1.getSelectedFile().getAbsolutePath();
	} 
	}catch (Exception ex){ 
	ex.printStackTrace(); 
	} 
	System.out.println("Enrutar ruta: "+ruta+" path: "+path);
    return(path);
}


public String getRuta(){

    return ruta;
}

public String leer(JEditorPane cuerpo){
	        String pathr="";
                cuerpo.setText("");
	        try {
	            JFileChooser buscador = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(" (.lz)", "lz");
	            buscador.setFileFilter(filter);
                    buscador.showOpenDialog(buscador);
                    
	            String contenido;
	           
	            String direc = buscador.getSelectedFile().getAbsolutePath();
	            ruta = direc;
                    pathr = direc;
                    System.out.println("Leer ruta: "+ruta);
                    FileReader doc =new FileReader(direc);
	            BufferedReader leer=new BufferedReader(doc);
	            String auxiliar="";
	            while((contenido=leer.readLine())!= null)
	            {
                        auxiliar = auxiliar + contenido + "\n";
                        //cuerpo.append(contenido); 
                        //cuerpo.append(System.getProperty("line.separator"));
	           
	            }
            //        System.out.println("Contenido \n"+auxiliar);
                    cuerpo.setText(auxiliar);
	            doc.close();
	            leer.close();
                    
	        } catch (IOException ex) {
	            System.out.println("Error: "+ex);
	        }
                return pathr;
                //crearArchivo(cuerpo);
	 }


public String guardarArchivo(JEditorPane cuerpo, String ruta){
            String contenido;
	    contenido=cuerpo.getText();
	    File f;
	    FileWriter escribir;
	    try{
	    System.out.println("Guardar ruta: "+ruta);
	    f = new File(ruta);
	    escribir = new FileWriter(f);
	    BufferedWriter bw = new BufferedWriter(escribir);
	    PrintWriter pw = new PrintWriter(bw);
	    pw.write(contenido); 
	    pw.close();
	    bw.close();
             //JOptionPane.showMessageDialog(null,"El archivo se ha guardado exitosamente");
	    }
	    catch(IOException e){System.out.println("Error: "+e.getMessage());}
            return ruta;
}

public void guardarcomoArchivo(JEditorPane cuerpo,String path){
	    String contenido;
	    contenido=cuerpo.getText();
	    File f;
	    FileWriter escribir;
	    try{
	    System.out.println("Guardar como path: "+path);
	    f = new File(path+".lz");
	    escribir = new FileWriter(f);
	    BufferedWriter bw = new BufferedWriter(escribir);
	    PrintWriter pw = new PrintWriter(bw);
	    pw.write(contenido); 
	    pw.close();
	    bw.close();
             //JOptionPane.showMessageDialog(null,"El archivo se ha guardado exitosamente");
	    }
	    catch(IOException e){System.out.println("Error: "+e.getMessage());}
           
}

public void crearArchivo(JEditorPane cuerpo){
            String contenido;
	    contenido=cuerpo.getText();
	    File f;
	    FileWriter escribir;
	    try{
	    f = new File("test.txt");
	    escribir = new FileWriter(f);
	    BufferedWriter bw = new BufferedWriter(escribir);
	    PrintWriter pw = new PrintWriter(bw);
	    pw.write(contenido); 
	    pw.close();
	    bw.close();
         
             //JOptionPane.showMessageDialog(null,"El archivo se ha guardado exitosamente");
	    }
	    catch(IOException e){System.out.println("Error: "+e.getMessage());}
}


public void generarhtmlTS(ArrayList<Simbolo> simbolo){
	    
	    File f;
	    FileWriter escribir;
	    try{
	    System.out.println(ruta);
	    f = new File("tabla_de_simbolos.html");
	    escribir = new FileWriter(f);
	    BufferedWriter bw = new BufferedWriter(escribir);
	    PrintWriter pw = new PrintWriter(bw);
            pw.write("<html>\n<head>\n<title> Tabla de Simbolos \n</title>\n</head>");
            pw.write("");
            pw.write("<body background=\"fondo1.jpg\">");
            pw.write("<center>");
            pw.write("<h1>Tabla de Simbolos</h1>\n");
            pw.write("<table font face=arial black size=12>");
            pw.write("<tr>\n<td>No</td>\n<td>ID</td>\n<td>Tipo</td>\n<td>Rol</td>\n<td>Ambito</td>\n</tr>");
             for(int i=1;i< simbolo.size();i++){
                       pw.write("<tr>\n");
                       pw.write("<td>"+i+"</td>\n");
                       pw.write("<td>"+ simbolo.get(i).id + "</td>\n");
                       pw.write("<td>"+ simbolo.get(i).tipo + "</td>\n");
                       pw.write("<td>"+ simbolo.get(i).rol + "</td>\n");
                       pw.write("<td>"+ simbolo.get(i).ambito + "</td>\n");
                       pw.write("</tr>\n");
            }
           
            pw.write("</table>\n");
           
	    pw.write("</center>"); 
            pw.write("</body>\n");
            pw.write("</html>");
	    pw.close();
	    bw.close();
         
             JOptionPane.showMessageDialog(null,"Tabla De Simbolos: tabla_de_simbolos.html");
	    }
	    catch(IOException e){System.out.println("Error: "+e.getMessage());}
}

public void generarhtmlErrores(ArrayList<Error> error){
	    
	    File f;
	    FileWriter escribir;
	    try{
	    System.out.println(ruta);
	    f = new File("lista_de_errores.html");
	    escribir = new FileWriter(f);
	    BufferedWriter bw = new BufferedWriter(escribir);
	    PrintWriter pw = new PrintWriter(bw);
            pw.write("<html>\n<head>\n<title> Lista de Erroes\n</title>\n</head>");
            pw.write("");
            pw.write("<body background=\"fondo1.jpg\">");
            pw.write("<center>");
            pw.write("<h1>Lista de Errores</h1>\n");
            pw.write("<table font face=arial black size=12>");
            pw.write("<tr>\n<td>No</td>\n<td>Linea</td>\n<td>Columna</td>\n<td>Tipo de error</td>\n<td>Descripcion</td>\n</tr>");
             for(int i=1;i< error.size();i++){
                       pw.write("<tr>\n");
                       pw.write("<td>"+i+"</td>\n");
                       pw.write("<td>"+ error.get(i).linea + "</td>\n");
                       pw.write("<td>"+ error.get(i).columna + "</td>\n");
                       pw.write("<td>"+ error.get(i).tipo_error + "</td>\n");
                       pw.write("<td>"+ error.get(i).descripcion + "</td>\n");
                       pw.write("</tr>\n");
            }
           
            pw.write("</table>\n");
            pw.write("</center>"); 
            pw.write("</body>\n");
            pw.write("</html>");
	    pw.close();
	    bw.close();
         
            JOptionPane.showMessageDialog(null,"Lita de Errores: errores.html");
	    }
	    catch(IOException e){System.out.println("Error: "+e.getMessage());}
}



}

