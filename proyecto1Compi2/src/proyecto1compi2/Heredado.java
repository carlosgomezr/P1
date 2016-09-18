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
public class Heredado {
    String lienzo;
    String visibilidad;
    ArrayList<Heredado> h = new ArrayList<Heredado>();
            
    Heredado(){
    
    }
    Heredado(String lienzo){
        this.lienzo = lienzo;
    }
    Heredado(String lienzo,String visibilidad){
        this.lienzo = lienzo;
        this.visibilidad = visibilidad;
    }
    
}
