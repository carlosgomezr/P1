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
public class Simbolo {
  
    public String id;
    public String tipo;
    public String rol;
    public String ambito;
    public String visibilidad="publico";//valor de visibilidad por defecto 
    public String conservar;
    public String valor;//valor de la variable por defecto null
    public int fila;
    public int columna;
    public String lienzo;
    public ListaArreglo arreglo = new ListaArreglo();
    public int size;
    public int dim;
    public NodoArbol root = new NodoArbol();
    public ArrayList<String> heredado = new ArrayList<String>();
    public ArrayList<ListaParametro> parametros = new ArrayList<ListaParametro>();
    
    Simbolo(String id, String tipo, String rol, String ambito){
        this.id = id;
        this.tipo = tipo;
        this.rol = rol;
        this.ambito = ambito;
    }
    Simbolo(){
    }
}
