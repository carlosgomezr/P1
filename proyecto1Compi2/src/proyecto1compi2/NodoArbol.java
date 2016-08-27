/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1compi2;
import java.util.ArrayList;
/**
 *
 * @author Carlos Gomez
 */
public class NodoArbol {
    public String nombre;
    public String grafoname;
    public String cadena;
    public int numero;
    ArrayList<NodoArbol> hijos = new ArrayList<NodoArbol>();
    
    NodoArbol(String nombre, String grafoname, String cadena, int numero){
        this.nombre = nombre;
        this.grafoname = grafoname;
        this.cadena = cadena;
        this.numero = numero;
    }
    
    NodoArbol(){
    
    }
    
   
}
