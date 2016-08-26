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
public class NodeError {
    
 
    public int linea;
    public int columna;
    public String tipo_error;
    public String descripcion;
    
    
    NodeError(int linea, int columna, String tipo_error, String descripcion){
        this.linea = linea;
        this.columna = columna;
        this.tipo_error = tipo_error;
        this.descripcion = descripcion;
    }


}
