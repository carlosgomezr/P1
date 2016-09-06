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
    
    public void igualarSimbolos(Simbolo original, Simbolo copia){
        copia.ambito = original.ambito;
        copia.conservar = original.conservar;
        copia.id = original.id;
        copia.rol = original.rol;
        copia.tipo = original.tipo;
        copia.valor = original.valor;
        copia.visibilidad = original.visibilidad;
        copia.fila = original.fila;
        copia.columna = original.columna;
    }
    public void verificarSimbolo(Simbolo s,ArrayList<Simbolo> LS,ArrayList<NodeError> LE){
        int size = LS.size();
        for(int i=0;i<size;i++){
            if(LS.get(i).id.compareTo(s.id)==0 && LS.get(i).ambito.compareTo(s.ambito)==0){
                NodeError e = new NodeError(s.fila,s.columna,"redefinicion de variable ", s.id +" ya fue definida anteriormente en ambito "+s.ambito);
                LE.add(e); 
                break;
            }
        }
    }
    public String darAmbitio(NodoArbol nodo){
        String resultado="lienzo";
        if(nodo.cadena.compareTo("SENSI")==0){
            resultado="si";
        }
        else if(nodo.cadena.compareTo("SENSINO")==0){
            resultado="sino";
        }
        else if(nodo.cadena.compareTo("SENCOMPROBAR")==0){
            resultado="comprobar";
        }
        else if(nodo.cadena.compareTo("SENPARA")==0){
            resultado="para";
        }
        else if(nodo.cadena.compareTo("SENMIENTRAS")==0){
            resultado="mientras";
        }
        else if(nodo.cadena.compareTo("SENHACER")==0){
            resultado="hacer-mientras";
        }
        else if(nodo.cadena.compareTo("PRI")==0){
            resultado="principal";
        }else{
            resultado=nodo.cadena;
        }
        return resultado;
    }
    public NodoArbol operar(NodoArbol operador1, NodoArbol operador2, NodoArbol op, ArrayList<NodeError> LE){
        NodoArbol nodo=new NodoArbol();
        //SUMA 
        if(op.cadena.compareTo("+")==0){ 
            //DOBLE--------------------------------------------------------------------------------------------
            if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0) //int + doble = doble
            {
                double resultado = Integer.parseInt(operador1.cadena) + Double.parseDouble(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0) //doble + int = doble
            {
                double resultado = Double.parseDouble(operador1.cadena) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0) //doble + char = doble
            {
                double resultado = Double.parseDouble(operador1.cadena) + operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0) //char + doble = doble
            {
                double resultado = operador1.cadena.charAt(1) + Double.parseDouble(operador2.cadena);
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
                double resultado = auxoperador + Double.parseDouble(operador2.cadena);
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
                double resultado = Double.parseDouble(operador1.cadena) + auxoperador;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = Double.parseDouble(operador1.cadena) + Double.parseDouble(operador2.cadena);
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
                double resultado = Integer.parseInt(operador1.cadena) - Double.parseDouble(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0){
                double resultado = Double.parseDouble(operador1.cadena) - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = operador1.cadena.charAt(1) - Double.parseDouble(operador2.cadena);       
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0){
                double resultado = Double.parseDouble(operador1.cadena) - operador2.cadena.charAt(1);
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
                double resultado = auxoperador - Double.parseDouble(operador2.cadena);
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
                double resultado = Double.parseDouble(operador2.cadena)- auxoperador ;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = Double.parseDouble(operador1.cadena) - Double.parseDouble(operador2.cadena);
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
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al restar ","No se pueden operar los tipos "+operador1.ty+"  "+operador2.ty);
                LE.add(e);
            }
        }
        
        //MULTIPLICACION
        else if(op.cadena.compareTo("*")==0){ 
            //DOBLE
            if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = Integer.parseInt(operador1.cadena) * Double.parseDouble(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0){
                double resultado = Double.parseDouble(operador1.cadena) * Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0){
                double resultado = Double.parseDouble(operador1.cadena) * operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = operador1.cadena.charAt(1) * Double.parseDouble(operador2.cadena);
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
                double resultado = auxoperador * Double.parseDouble(operador2.cadena);
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
                double resultado = Double.parseDouble(operador1.cadena) * auxoperador;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = Double.parseDouble(operador1.cadena) * Double.parseDouble(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            //INT
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("caracter")==0){
                int resultado = Integer.parseInt(operador1.cadena) * operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = operador1.cadena.charAt(1) * Integer.parseInt(operador2.cadena);
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
                int resultado = auxoperador * Integer.parseInt(operador2.cadena);
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
                int resultado = Integer.parseInt(operador1.cadena) * auxoperador;
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = Integer.parseInt(operador1.cadena) * Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
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
                if((auxoperador==1) && (auxoperador2==1) ){ resultado="true";}
                nodo.cadena=resultado;
                nodo.ty="boolean";
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al multiplicar ","No se pueden operar los tipos "+operador1.ty+"  "+operador2.ty);
                LE.add(e);
            }
        }        
        
        //DIVISION
        else if(op.cadena.compareTo("/")==0){ 
            if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("doble")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Integer.parseInt(operador1.cadena) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("entero")==0){
                if(Integer.parseInt(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / Integer.parseInt(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("caracter")==0){
                if(operador2.cadena.charAt(1)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / operador2.cadena.charAt(1);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("caracter")==0 && operador2.cadena.compareTo("doble")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = operador1.cadena.charAt(1) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("boolean")==0 && operador2.cadena.compareTo("doble")==0){
               if(Double.parseDouble(operador2.cadena)==0){
                   NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
               }else{
                   int auxoperador=-1;
                   if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                       auxoperador= 1;
                   }
                   else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                       auxoperador= 0;
                   }
                   double resultado = auxoperador / Double.parseDouble(operador2.cadena);
                   nodo.cadena = String.valueOf(resultado);
                   nodo.ty = "doble";
               }
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    auxoperador=1;
                    double resultado = Double.parseDouble(operador1.cadena) / auxoperador;
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }         
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("doble")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("caracter")==0){
                if(operador2.cadena.charAt(1)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / operador2.cadena.charAt(1);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("caracter")==0 && operador2.cadena.compareTo("entero")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                        NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                        LE.add(e);
                }else{
                    double resultado = operador1.cadena.charAt(1) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("boolean")==0 && operador2.cadena.compareTo("entero")==0){
                if(Integer.parseInt(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    int auxoperador=-1;
                    if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                       auxoperador= 1;
                    }
                    else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                        auxoperador= 0;
                    }
                    double resultado = auxoperador / Integer.parseInt(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    auxoperador=1;
                    double resultado = Integer.parseInt(operador1.cadena) / auxoperador;
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("entero")==0){
                double resultado = Integer.parseInt(operador1.cadena) / Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al dividir ","No se pueden operar los tipos "+operador1.ty+"  "+operador2.ty);
                LE.add(e);
            }
        }
        
        //POTENCIA
        else if(op.cadena.compareTo("^")==0){ 
            //DOBLE
            if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("doble")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("entero")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("caracter")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), operador2.cadena.charAt(1));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.cadena.compareTo("caracter")==0 && operador2.cadena.compareTo("doble")==0){
                double resultado = (double)Math.pow(operador1.cadena.charAt(1), Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.cadena.compareTo("boolean")==0 && operador2.cadena.compareTo("doble")==0){
                int auxoperador=-1;
                if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                double resultado = (double)Math.pow(auxoperador,Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("true")==0 || operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena),auxoperador);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.cadena.compareTo("doble")==0 && operador2.cadena.compareTo("doble")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            //INT
            else if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("caracter")==0){
                int resultado = (int)Math.pow(Integer.parseInt(operador1.cadena), operador2.cadena.charAt(1));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.cadena.compareTo("caracter")==0 && operador2.cadena.compareTo("entero")==0){
                int resultado = (int)Math.pow(operador1.cadena.charAt(1),Integer.parseInt(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }                
            else if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("boolean")==0){
                int auxoperador=-1;
                if(operador2.cadena.compareTo("true")==0 || operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador2.cadena.compareTo("false")==0 || operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = (int)Math.pow(Integer.parseInt(operador1.cadena),auxoperador);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            
            }
            else if(operador1.cadena.compareTo("boolean")==0 && operador2.cadena.compareTo("entero")==0){
                int auxoperador=-1;
                if(operador1.cadena.compareTo("true")==0 || operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0){
                    auxoperador= 1;
                }
                else if(operador1.cadena.compareTo("false")==0 || operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0){
                    auxoperador= 0;
                }
                int resultado = (int)Math.pow(auxoperador,Integer.parseInt(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.cadena.compareTo("entero")==0 && operador2.cadena.compareTo("entero")==0){
                int resultado = (int)Math.pow(Integer.parseInt(operador1.cadena),Integer.parseInt(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al potenciar ","No se pueden operar los tipos "+operador1.ty+"  "+operador2.ty);
                LE.add(e);
            }
        }
        
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
    
    public Object ejecucion(NodoArbol nodo, ArrayList<Simbolo> LS,NodoArbol padre,Simbolo token, ArrayList<NodeError> LE){
        NodoArbol retorno= new NodoArbol();
        JOptionPane.showMessageDialog(null,"Metodo Ejecutar");
        //PRODUCCION INICIAR
        if(nodo.cadena.compareTo("Ini")==0){
            JOptionPane.showMessageDialog(null,"Ini");
            ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
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
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE);                    
                    LS.add(s);                    
                }
                else if(nodo.hijos.get(2).cadena.compareTo("EXT")==0){  //lienzo id extiende
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE);
                }                
            }
            else if(nodo.hijos.size()==4){
                if(nodo.hijos.get(0).cadena.compareTo("VISI")==0 && nodo.hijos.get(3).cadena.compareTo("EXT")==0){ //visi lienzo id extiende
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE);
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(2).cadena;
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                }
                else if(nodo.hijos.get(3).cadena.compareTo("SENTENCIAS")==0){  //lienzo id sen sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE);
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                }                
            }
            else if(nodo.hijos.size()==5){ 
                if(nodo.hijos.get(0).cadena.compareTo("VISI")==0 && nodo.hijos.get(4).cadena.compareTo("SENTENCIAS")==0){ //visi lienzo id sen sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE);
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(2).cadena;
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                    ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                }
                else if(nodo.hijos.get(2).cadena.compareTo("EXT")==0 && nodo.hijos.get(4).cadena.compareTo("SENTENCIAS")==0){ //lienzo id extiende sen sentencias
                    JOptionPane.showMessageDialog(null,"lienzo id ext sen sentencia");
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE);
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                    ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                }
            }
            else if(nodo.hijos.size()==6){ //visi lienzo id ext sen sentencias
                Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE);
                LS.add(s);
                NodoArbol auxnodo = new NodoArbol();
                auxnodo.cadena = nodo.hijos.get(2).cadena;
                ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                ejecucion(nodo.hijos.get(5),LS,auxnodo,token,LE);         
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
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE);
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
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                //return nodo.hijos.get(0);
            }
        }
        //PRODUCCION SENTENCIAS
        else if(nodo.cadena.compareTo("SENTENCIAS")==0){
            JOptionPane.showMessageDialog(null,"SENTENCIAS "+nodo.hijos.size());
                
            if(nodo.hijos.size()==2){
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE);    
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
            
        }
        //PRODUCCION SEN
        else if(nodo.cadena.compareTo("SEN")==0){
            JOptionPane.showMessageDialog(null,"SEN");
            //-----------------CONTENIDO
            if(nodo.hijos.get(0).cadena.compareTo("CONTENIDO")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            //----------------SENSI
            
            //----------------SENCOMPROBAR
            
            //----------------SENPARA
            
            //----------------SENMIENTRAS
            
            //----------------SENHACER
            
            //----------------SENSALIR
            
            //----------------RETO
            
            //----------------SENCONTINUAR
            
            //----------------DIBUJAR_P
            
            //----------------DIBUJAR_OR
            
            //----------------DIBUJAR_S
            
            //----------------PRI
            
            //----------------ORDEN
            
            //----------------SUMARI
        }
        //PRODUCCION CONTENIDO
        else if(nodo.cadena.compareTo("CONTENIDO")==0){
            JOptionPane.showMessageDialog(null,"CONTENIDO");
            if(nodo.hijos.size()==1){   //CONT
                Simbolo s = new Simbolo();
                ejecucion(nodo.hijos.get(0),LS,padre,s,LE);
            }
            else if(nodo.hijos.size()==2){//CONSERV CONT | VISI CONT
                if(nodo.hijos.get(0).cadena.compareTo("CONSERV")==0){
                    Simbolo s = new Simbolo();
                    s.conservar = "conservar";
                    ejecucion(nodo.hijos.get(1),LS,padre,s,LE); //CONT
                }
                else if(nodo.hijos.get(0).cadena.compareTo("VISI")==0){
                    Simbolo s = new Simbolo();
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE); //VISI
                    ejecucion(nodo.hijos.get(1),LS,padre,s,LE); //CONT
                }
            }
            else if(nodo.hijos.size()==3){//CONSERV VISI CONT
                Simbolo s = new Simbolo();
                s.visibilidad = (String)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //VISI
                ejecucion(nodo.hijos.get(2),LS,padre,s,LE); //CONT
            }
        }
        //PRODUCCION CONT
        else if(nodo.cadena.compareTo("CONT")==0){
            JOptionPane.showMessageDialog(null,"CONT");
            
            if(nodo.hijos.get(0).cadena.compareTo("DECLARACION")==0){//DECLARACION
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }else if(nodo.hijos.get(0).cadena.compareTo("CONTENIDO3")==0){//CONTENIDO3
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
        }
        //PRODUCCION DECLARACION
        else if(nodo.cadena.compareTo("DECLARACION")==0){
            JOptionPane.showMessageDialog(null,"DECLARACION");
            token.tipo = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE);   //TIPO
            ejecucion(nodo.hijos.get(1),LS,padre,token,LE);   //VARIABLE
        }
        //PRODUCCION TIPO
        else if(nodo.cadena.compareTo("TIPO")==0){
            JOptionPane.showMessageDialog(null,"TIPO");
            if(nodo.hijos.get(0).cadena.compareTo("tentero")==0){
                return "entero";
            }
            else if(nodo.hijos.get(0).cadena.compareTo("tdoble")==0){
                return "doble";
            }
            else if(nodo.hijos.get(0).cadena.compareTo("tboolean")==0){
                return "boolean";
            }
            else if(nodo.hijos.get(0).cadena.compareTo("tcaracter")==0){
                return "caracter";
            }
            else if(nodo.hijos.get(0).cadena.compareTo("tcadena")==0){
                return "cadena";
            }
        }
        //PRODUCCION VARIABLE
        else if(nodo.cadena.compareTo("VARIABLE")==0){
            JOptionPane.showMessageDialog(null,"VARIABLE");
            if(nodo.hijos.size()==1){   //NOMBRES
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==2){ //NOMARREGLO DIMENSION
            
            }
            else if(nodo.hijos.size()==3){ //NOMARREGLO DIMENSION ASIGNARREGLO
            
            }
        }
        //PRODUCCION NOMBRES
        else if(nodo.cadena.compareTo("NOMBRES")==0){
            JOptionPane.showMessageDialog(null,"NOMBRES");
            if(nodo.hijos.size()==1){   //id 
                token.id = nodo.hijos.get(0).cadena;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
            }
            else if(nodo.hijos.size()==2){ //id ASIGN   || id NOM
                if(nodo.hijos.get(1).cadena.compareTo("ASIGN")==0){//id ASIGN
                    token.id = nodo.hijos.get(0).cadena;
                    token.rol = "variable";
                    token.ambito = darAmbitio(padre);
                    token.fila = nodo.hijos.get(0).f;
                    token.columna = nodo.hijos.get(0).c;
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                }
                else if(nodo.hijos.get(1).cadena.compareTo("NOM")==0){//id NOM
                    token.id = nodo.hijos.get(0).cadena;
                    token.rol = "variable";
                    token.ambito = darAmbitio(padre);
                    token.fila = nodo.hijos.get(0).f;
                    token.columna = nodo.hijos.get(0).c;
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                } 
            }
            else if(nodo.hijos.size()==3){ //id ASIGN NOM
                token.id = nodo.hijos.get(0).cadena;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //ASIGN
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE); //NOM
            }
        }
        //PRODUCCION NOM
        else if(nodo.cadena.compareTo("NOM")==0){
            JOptionPane.showMessageDialog(null,"NOM");
            if(nodo.hijos.size()==1){   //id 
                token.id = nodo.hijos.get(0).cadena;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
            }
            else if(nodo.hijos.size()==2){ //id ASIGN   || id NOM
                if(nodo.hijos.get(1).cadena.compareTo("ASIGN")==0){// id ASIGN
                    token.id = nodo.hijos.get(0).cadena;
                    token.rol = "variable";
                    token.ambito = darAmbitio(padre);
                    token.fila = nodo.hijos.get(0).f;
                    token.columna = nodo.hijos.get(0).c;
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                }
                else if(nodo.hijos.get(1).cadena.compareTo("NOM")==0){// id NOM
                    token.id = nodo.hijos.get(0).cadena;
                    token.rol = "variable";
                    token.ambito = darAmbitio(padre);
                    token.fila = nodo.hijos.get(0).f;
                    token.columna = nodo.hijos.get(0).c;
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                } 
            }
            else if(nodo.hijos.size()==3){ //id ASIGN NOM
                token.id = nodo.hijos.get(0).cadena;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //ASIGN
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE); //NOM
            }
        }
        //PRODUCCION ASIGN
        else if(nodo.cadena.compareTo("ASIGN")==0){
            JOptionPane.showMessageDialog(null,"ASIGN");
            if(nodo.hijos.size()==1){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//VALOR
            }
        }
        //PRODUCCION VALOR
        else if(nodo.cadena.compareTo("VALOR")==0){
            JOptionPane.showMessageDialog(null,"VALOR");
            if(nodo.hijos.size()==1){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//LOGICA
            }
        }
        //PRODUCCION LOGICA
        else if(nodo.cadena.compareTo("LOGICA")==0){
            JOptionPane.showMessageDialog(null,"LOGICA");
            //ACA ME QUEDE
            //TOMAR EN CUENTA QUE NO SE A ASIGNADO AUN VALOR Y QUE LA DECLARACION DE ARREGLOS AUN NO ESTA ECHA 
        }
       return retorno;
    }
}


