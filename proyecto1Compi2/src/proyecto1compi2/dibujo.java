/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1compi2;

import java.awt.Color;

/**
 *
 * @author Carlos Gomez
 */
public class dibujo {
    public int x;
    public int y;
    public String figura;
    public String cadena;
    public int altura;
    public int anchura;
    public int diametro;
    public Color color;
    
    dibujo(int x, int y, String figura, String cadena,int altura, int anchura, int diametro, Color color){
        this.x = x;
        this.y = y;
        this.figura = figura;
        this.cadena = cadena;
        this.altura = altura;
        this.anchura = anchura;
        this.diametro = diametro;
        this.color = color;
    }
}
