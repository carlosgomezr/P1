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
public class Simbolo {
  
    public String id;
    public String tipo;
    public String rol;
    public String ambito;
    
    Simbolo(String id, String tipo, String rol, String ambito){
        this.id = id;
        this.tipo = tipo;
        this.rol = rol;
        this.ambito = ambito;
    }
}