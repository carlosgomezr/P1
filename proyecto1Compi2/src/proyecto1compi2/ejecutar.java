/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1compi2;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Gomez
 */
public class ejecutar {
    
    ejecutar(){
    
    }
    
    public NodoArbol operar(NodoArbol operador1, NodoArbol operador2, NodoArbol op, ArrayList<NodeError> LE){
        NodoArbol nodo=new NodoArbol();
        //SUMA 
        if(op.cadena.compareTo("+")==0){ 
            //DOBLE--------------------------------------------------------------------------------------------
            if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0) //int + doble = doble
            {
                int resultado = Integer.parseInt(operador1.cadena) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0) //doble + int = doble
            {
                int resultado = Integer.parseInt(operador1.cadena) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0) //doble + char = doble
            {
                int resultado = Integer.parseInt(operador1.cadena) + operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0) //char + doble = doble
            {
                int resultado = operador1.cadena.charAt(1) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("doble")==0){
                int auxoperador=-1;
                if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = auxoperador + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("true")==0 || operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = Integer.parseInt(operador1.cadena) + auxoperador;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                int resultado = Integer.parseInt(operador1.cadena) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            //INT-------------------------------------------------------------------------------------------
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("caracter")==0){
                int resultado = Integer.parseInt(operador1.cadena) + operador1.cadena.charAt(1);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = operador1.cadena.charAt(1) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("entero")==0){
                int auxoperador=-1;
                if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = auxoperador + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("true")==0 || operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = Integer.parseInt(operador1.cadena) + auxoperador;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = Integer.parseInt(operador1.cadena) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            //STRING------------------------------------------------------------------------
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("entero")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("cadena")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("doble")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("cadena")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("caracter")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("cadena")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("cadena")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            //BOOLEAN
            else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("boolean")==0){
                int auxoperador=-1;
                int auxoperador2=-1;
                if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                if(operador2.cadena.compareTo("true")==0 || operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0){
                    auxoperador2= 1;
                }
                else if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    auxoperador2= 0;
                }
                String resultado="false";
                if((auxoperador==1) || (auxoperador2==1) ){ resultado="true";}
                nodo.cadena=resultado;
                nodo.ty="boolean";
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al sumar ","No se pueden operar los tipos "+operador1.ty+"  "+operador2.ty);
                LE.add(e);
            }
        }
        //RESTA
        else if(op.cadena.compareTo("-")==0){ 
            //DOBLE---------------------------------------------------------
            if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0){
                int resultado = Integer.parseInt(operador1.cadena) - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = Integer.parseInt(operador1.cadena) - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0){
                int resultado = operador1.cadena.charAt(1) - Integer.parseInt(operador2.cadena);       
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0){
                int resultado = Integer.parseInt(operador1.cadena) - operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("doble")==0){
                int auxoperador=-1;
                if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = auxoperador - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("true")==0 || operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = Integer.parseInt(operador2.cadena)- auxoperador ;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                int resultado = Integer.parseInt(operador1.cadena) - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            //INT
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("caracter")==0){
                int resultado = Integer.parseInt(operador1.cadena) - operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = operador1.cadena.charAt(1) - Integer.parseInt(operador1.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("entero")==0){
                int auxoperador=-1;
                if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = auxoperador - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("true")==0 || operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = Integer.parseInt(operador1.cadena) - auxoperador ;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = Integer.parseInt(operador1.cadena) - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
        }
        
        //MULTIPLICACION
        
        
        //DIVISION
        
        
        //POTENCIA
        return nodo;
    }
    
    public NodoArbol relacional(NodoArbol operador1, NodoArbol operador2, NodoArbol op, ArrayList<Simbolo> LS){
        NodoArbol nodo = new NodoArbol();
        //IGUALACION
        if(op.cadena.compareTo("==")==0){
            if(operador1.cadena.compareTo(operador2.cadena)==0){
                nodo.cadena="true";
            }
            else{
                nodo.cadena="false";
            }
        }
        //DIFERENCIACION
        else if(op.cadena.compareTo("!=")==0){
            if(operador1.cadena.compareTo(operador2.cadena)==1){
                nodo.cadena="true";
            }
            else{
                nodo.cadena="false";
            }
        }
        //MENORQUE
        else if(op.cadena.compareTo("<")==0){
            if(Integer.parseInt(operador1.cadena) < Integer.parseInt(operador2.cadena)){
                nodo.cadena="true";
            }
            else{
                nodo.cadena="false";
            }
        }
        //MENORIGUALQUE
        else if(op.cadena.compareTo("<=")==0){
            if(Integer.parseInt(operador1.cadena) <= Integer.parseInt(operador2.cadena)){
                nodo.cadena="true";
            }
            else{
                nodo.cadena="false";
            }
        }
        //MAYORQUE
        else if(op.cadena.compareTo(">")==0){
            if(Integer.parseInt(operador1.cadena) > Integer.parseInt(operador2.cadena)){
                nodo.cadena="true";
            }
            else{
                nodo.cadena="false";
            }
        }
        //MAYORIGUALQUE
        else if(op.cadena.compareTo(">=")==0){
            if(Integer.parseInt(operador1.cadena) >= Integer.parseInt(operador2.cadena)){
                nodo.cadena="true";
            }
            else{
                nodo.cadena="false";
            }
        }
        //NULO
        else if(op.cadena.compareTo("!¡")==0){
            nodo.cadena=getvalorTS(operador1,LS);
        }
        //debo revisar en la tabla de simbolos para saber si la variable ya tiene asignado un valor
        
        
        return nodo;
    }
    
    public String getvalorTS(NodoArbol nodo, ArrayList<Simbolo> LS){
        String resultado="false";
        for(int i=0;i<LS.size();i++){
            if(LS.get(i).id.compareTo(nodo.cadena)==0 && LS.get(i).valor.compareTo("false")==1){
                    resultado="true";
                    break;
            }
        }
        
        return resultado;
    }
    
    public NodoArbol logica(NodoArbol operador1, NodoArbol operador2, NodoArbol op, ArrayList<Simbolo> LS){
        NodoArbol nodo = new NodoArbol();
        //OR ||
        if(op.cadena.compareTo("||")==0){
            if(operador1.cadena.compareTo("true")==0 || operador2.cadena.compareTo("true")==0)
            {
                nodo.cadena="true";
            }else{
                nodo.cadena="false";
            }
        }
        //AND &&
        else if(op.cadena.compareTo("&&")==0){
            if(operador1.cadena.compareTo("true")==0 && operador2.cadena.compareTo("true")==0)
            {
                nodo.cadena="true";
            }else{
                nodo.cadena="false";
            }
        }
        //NAND !&&
        else if(op.cadena.compareTo("!&&")==0){
            if(operador1.cadena.compareTo("true")==0 && operador2.cadena.compareTo("true")==0)
            {
                nodo.cadena="false";
            }else{
                nodo.cadena="true";
            }
        }
        
        //NOR !||
        else if(op.cadena.compareTo("!||")==0){
            if(operador1.cadena.compareTo("true")==0 || operador2.cadena.compareTo("true")==0)
            {
                nodo.cadena="false";
            }else{
                nodo.cadena="true";
            }
        }        
        //XOR &|
        else if(op.cadena.compareTo("&|")==0){
            if(operador1.cadena.compareTo("true")==0 && operador2.cadena.compareTo("false")==0)
            {
                nodo.cadena="true";
            }
            else if(operador1.cadena.compareTo("false")==0 && operador2.cadena.compareTo("true")==0)
            {
                nodo.cadena="true";
            }
            else{
                nodo.cadena="false";
            }
        }
        //NOT !
        else if(op.cadena.compareTo("!")==0){
            if(operador1.cadena.compareTo("true")==0){
                nodo.cadena="false";
            }else{
                nodo.cadena="true";
            }
        }
        return nodo;
    }
    
    public Object ejecucion(NodoArbol nodo, ArrayList<Simbolo> LS){
        NodoArbol retorno= new NodoArbol();
        JOptionPane.showMessageDialog(null,"Metodo Ejecutar");
        //PRODUCCION INICIAR
        if(nodo.cadena.compareTo("Ini")==0){
            JOptionPane.showMessageDialog(null,"Ini");
            ejecucion(nodo.hijos.get(0),LS);
        }
        //PRODUCCION S
        else if(nodo.cadena.compareTo("S")==0){//(visi)? lienzo id (extiende)? (sentencias)?
            JOptionPane.showMessageDialog(null,"S");
            
            if(nodo.hijos.size()==2){ //lienzo id
                Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                s.visibilidad = "publico";
                LS.add(s);
            }
            else if(nodo.hijos.size()==3){
                if(nodo.hijos.get(0).cadena.compareTo("VISI")==0){ //visi lienzo id
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS);                    
                    LS.add(s);                    
                }
                else if(nodo.hijos.get(2).cadena.compareTo("EXT")==0){  //lienzo id extiende
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    ejecucion(nodo.hijos.get(2),LS);
                }                
            }
            else if(nodo.hijos.size()==4){
                if(nodo.hijos.get(0).cadena.compareTo("VISI")==0 && nodo.hijos.get(3).cadena.compareTo("EXT")==0){ //visi lienzo id extiende
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS);
                    LS.add(s);
                    ejecucion(nodo.hijos.get(3),LS);
                }
                else if(nodo.hijos.get(3).cadena.compareTo("SENTENCIAS")==0){  //lienzo id sen sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    ejecucion(nodo.hijos.get(3),LS);
                }                
            }
            else if(nodo.hijos.size()==5){ 
                if(nodo.hijos.get(0).cadena.compareTo("VISI")==0 && nodo.hijos.get(4).cadena.compareTo("SENTENCIAS")==0){ //visi lienzo id sen sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS);
                    LS.add(s);
                    ejecucion(nodo.hijos.get(4),LS);
                }
                else if(nodo.hijos.get(2).cadena.compareTo("EXT")==0 && nodo.hijos.get(4).cadena.compareTo("SENTENCIAS")==0){ //lienzo id extiende sen sentencias
                    JOptionPane.showMessageDialog(null,"lienzo id ext sen sentencia");
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    ejecucion(nodo.hijos.get(2),LS);
                    ejecucion(nodo.hijos.get(4),LS);
                }
            }
            else if(nodo.hijos.size()==6){ //visi lienzo id ext sen sentencias
                Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS);
                LS.add(s);
                ejecucion(nodo.hijos.get(3),LS);
                ejecucion(nodo.hijos.get(5),LS);         
            }
        }
        //PRODUCCION VISI
        else if(nodo.cadena.compareTo("VISI")==0){
            JOptionPane.showMessageDialog(null,"VISI");
            if(nodo.hijos.get(0).cadena.compareTo("publico")==0){
                return "publico";
            }
            else if(nodo.hijos.get(0).cadena.compareTo("privado")==0){
                return "privado";
            }
            else if(nodo.hijos.get(0).cadena.compareTo("protegido")==0){
                return "protegido";
            }
        }
        //PRODUCCION EXT 
        else if(nodo.cadena.compareTo("EXT")==0){
            JOptionPane.showMessageDialog(null,"EXT");
            
            if(nodo.hijos.size()==2){   //extiende id
                Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","extendido","principal");
                LS.add(s);
                //return nodo.hijos.get(1);
            }
            else if(nodo.hijos.size()==3){ //extiende id (ext1)?
                Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","extendido","principal");
                LS.add(s);
                ejecucion(nodo.hijos.get(2),LS);
                //return nodo.hijos.get(1);
            }
        }
        //PRODUCCION EXT1
        else if(nodo.cadena.compareTo("EXT1")==0){ 
            JOptionPane.showMessageDialog(null,"EXT1");
            
            if(nodo.hijos.size()==1){//id
                Simbolo s = new Simbolo(nodo.hijos.get(0).cadena,"lienzo","extendido","principal");
                LS.add(s);
                //return nodo.hijos.get(0);
            }
            else if(nodo.hijos.size()==2){// id ext1
                Simbolo s = new Simbolo(nodo.hijos.get(0).cadena,"lienzo","extendido","principal");
                LS.add(s);
                ejecucion(nodo.hijos.get(1),LS);
                //return nodo.hijos.get(0);
            }
        }
        //PRODUCCION SENTENCIAS
        else if(nodo.cadena.compareTo("SENTENCIAS")==0){
            JOptionPane.showMessageDialog(null,"SENTENCIAS");
            
            
        }
       return retorno;
    }
}
