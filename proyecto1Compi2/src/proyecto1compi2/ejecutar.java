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
    public String getvalorTS(NodoArbol nodo, ArrayList<Simbolo> LS){
        String resultado="false";
        for(int i=0;i<LS.size();i++){
            if(LS.get(i).id.compareTo(nodo.cadena)==0 && LS.get(i).valor==null){
                    resultado="true";
                    break;
            }
        }
        return resultado;
    }
    
    public NodoArbol buscarSimbolo(NodoArbol nodo, ArrayList<Simbolo> LS){
        NodoArbol s = new NodoArbol();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(nodo.cadena)==0){
                    s.cadena = LS.get(i).valor;
                    s.ty = LS.get(i).tipo;
                }
            }
        return s;
    }
    public NodoArbol buscarSimbolo(Simbolo sim, ArrayList<Simbolo> LS){
        NodoArbol s = new NodoArbol();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(sim.id)==0){
                    s.cadena = LS.get(i).valor;
                    s.ty = LS.get(i).tipo;
                }
            }
        return s;
    }
    
    public void verficarTipo(Simbolo sim,String tipo,ArrayList<Simbolo> LS,ArrayList<NodeError> LE){
        NodoArbol aux = buscarSimbolo(sim,LS);
        System.out.println("    sim.ty "+sim.tipo);
        System.out.println("    tipo "+tipo);
        if(aux.cadena!=null){
            System.out.println("    la variable existe "+aux.ty);
            if(aux.ty.compareTo(tipo)!=0){
                System.out.println("    typos incorrectos ");
                NodeError e = new NodeError(sim.fila,sim.columna,"Error de asignacion ","Tipo incorrecto asignado a variable "+sim.id+"  "+sim.tipo+" se asigno "+tipo);
                LE.add(e);
            }
        }
    }
    public void crearError(int linea,int columna,String tipo, String descripcion,ArrayList<NodeError> LE){
        NodeError e = new NodeError(linea,columna,tipo,descripcion);        
        LE.add(e);
    }
    
    public void igualarNodo(NodoArbol original, NodoArbol copia){
        copia.c = original.c;
        copia.cadena = original.cadena;
        copia.f = original.f;
        copia.grafoname = original.grafoname;
        copia.nombre = original.nombre;
        copia.numero = original.numero;
        copia.ty = original.ty;
        copia.valor = original.valor;
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
    public NodoArbol operar(NodoArbol operador1, NodoArbol operador2, NodoArbol op,ArrayList<Simbolo> LS, ArrayList<NodeError> LE){
        NodoArbol nodo=new NodoArbol();        
        System.out.println("    OPERADOR "+op.cadena);
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
            if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Integer.parseInt(operador1.cadena) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0){
                if(Integer.parseInt(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / Integer.parseInt(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0){
                if(operador2.cadena.charAt(1)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / operador2.cadena.charAt(1);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = operador1.cadena.charAt(1) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("doble")==0){
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
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("boolean")==0){
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
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("caracter")==0){
                if(operador2.cadena.charAt(1)==0){
                    NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                    LE.add(e);
                }else{
                    double resultado = Double.parseDouble(operador1.cadena) / operador2.cadena.charAt(1);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("entero")==0){
                if(Double.parseDouble(operador2.cadena)==0){
                        NodeError e = new NodeError(operador2.f,operador2.c,"Error al dividir ","Division entre 0 "+operador1.ty+"  "+operador2.ty);
                        LE.add(e);
                }else{
                    double resultado = operador1.cadena.charAt(1) / Double.parseDouble(operador2.cadena);
                    nodo.cadena = String.valueOf(resultado);
                    nodo.ty = "doble";
                }
            }
            else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("entero")==0){
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
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("boolean")==0){
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
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("entero")==0){
                System.out.println("    entero div entero");
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
            if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), operador2.cadena.charAt(1));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = (double)Math.pow(operador1.cadena.charAt(1), Double.parseDouble(operador2.cadena));
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
                double resultado = (double)Math.pow(auxoperador,Double.parseDouble(operador2.cadena));
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
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena),auxoperador);
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                double resultado = (double)Math.pow(Double.parseDouble(operador1.cadena), Double.parseDouble(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "doble";
            }
            //INT
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("caracter")==0){
                int resultado = (int)Math.pow(Integer.parseInt(operador1.cadena), operador2.cadena.charAt(1));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("entero")==0){
                int resultado = (int)Math.pow(operador1.cadena.charAt(1),Integer.parseInt(operador2.cadena));
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
                int resultado = (int)Math.pow(Integer.parseInt(operador1.cadena),auxoperador);
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
                int resultado = (int)Math.pow(auxoperador,Integer.parseInt(operador2.cadena));
                nodo.cadena = String.valueOf(resultado);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("entero")==0){
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
            if(operador1.cadena.compareTo(operador2.cadena)!=0){
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
        
        //PRODUCCION INICIAR
        if(nodo.cadena.compareTo("Ini")==0){
            System.out.println("Ini");
            ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
        }
        //PRODUCCION S
        else if(nodo.cadena.compareTo("S")==0){//(visi)? lienzo id (extiende)? (sentencias)?
            System.out.println("S");
            
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
                else if(nodo.hijos.get(2).cadena.compareTo("SENTENCIAS")==0){  //lienzo id sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE);
                    //ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
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
                else if(nodo.hijos.get(0).cadena.compareTo("VISI")==0 && nodo.hijos.get(3).cadena.compareTo("SENTENCIAS")==0){ //visi lienzo id sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE);
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(2).cadena;
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                    //ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                }
                else if(nodo.hijos.get(2).cadena.compareTo("EXT")==0 && nodo.hijos.get(3).cadena.compareTo("SENTENCIAS")==0){ //lienzo id extiende sentencias
                    //JOptionPane.showMessageDialog(null,"lienzo id ext sen sentencia");
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE);
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                    //ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                }
                                
            }
            else if(nodo.hijos.size()==5){ //visi lienzo id ext sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE);
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(2).cadena;
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                    ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                  //  ejecucion(nodo.hijos.get(5),LS,auxnodo,token,LE);
                
            }

        }
        //PRODUCCION VISI
        else if(nodo.cadena.compareTo("VISI")==0){
            System.out.println("VISI");
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
            System.out.println("EXT");
            
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
            System.out.println("EXT1");
            
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
            System.out.println("SENTENCIAS");
            
            if(nodo.hijos.size()==2){
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE);    
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE);    
            }
            
        }
        //PRODUCCION SEN
        else if(nodo.cadena.compareTo("SEN")==0){
            System.out.println("SEN");
            
            if(nodo.hijos.size()==0){
                System.out.println("    COMENTARIO");
            }
            //-----------------CONTENIDO
            else if(nodo.hijos.get(0).cadena.compareTo("CONTENIDO")==0){
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
            System.out.println("CONTENIDO");
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
            System.out.println("CONT");
            
            if(nodo.hijos.get(0).cadena.compareTo("DECLARACION")==0){//DECLARACION
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }else if(nodo.hijos.get(0).cadena.compareTo("CONTENIDO3")==0){//CONTENIDO3
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
        }
        //PRODUCCION DECLARACION
        else if(nodo.cadena.compareTo("DECLARACION")==0){
            System.out.println("DECLARACION");
            token.tipo = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE);   //TIPO
            ejecucion(nodo.hijos.get(1),LS,padre,token,LE);   //VARIABLE
        }
        //PRODUCCION TIPO
        else if(nodo.cadena.compareTo("TIPO")==0){
            System.out.println("TIPO");
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
            System.out.println("VARIABLE");
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
            System.out.println("NOMBRES");
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

                    NodoArbol aux = new NodoArbol();
                    aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //ASIGN
                    token.valor = aux.cadena;   //pasar el valor del return al token
                    System.out.println("    id ASIGN - "+aux.cadena);
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);                  //añadir a la trabla de simbolos
                    verficarTipo(s,aux.ty,LS,LE);
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
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //NOM
                } 
            }
            else if(nodo.hijos.size()==3){ //id ASIGN NOM
                token.id = nodo.hijos.get(0).cadena;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                
                NodoArbol aux = new NodoArbol();
                aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //ASIGN
                token.valor = aux.cadena;   //pasar el valor del return al token
                
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);                  //añadir a la trabla de simbolos
                verficarTipo(s,aux.ty,LS,LE);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE); //NOM
            }
        }
        //PRODUCCION NOM
        else if(nodo.cadena.compareTo("NOM")==0){
            System.out.println("NOM");
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

                    NodoArbol aux = new NodoArbol();
                    aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //ASIGN
                    token.valor = aux.cadena;   //pasar el valor del return al token
                    System.out.println("    id ASIGN - "+aux.cadena);
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);                  //añadir a la trabla de simbolos
                    verficarTipo(s,aux.ty,LS,LE);    
                    //ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //NOM
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
                
                NodoArbol aux = new NodoArbol();
                aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //ASIGN
                token.valor = aux.cadena;   //pasar el valor del return al token
                
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);                  //añadir a la trabla de simbolos
                verficarTipo(s,aux.ty,LS,LE);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE); //NOM
            }
        }
        //PRODUCCION ASIGN
        else if(nodo.cadena.compareTo("ASIGN")==0){
            System.out.println("ASIGN");
            if(nodo.hijos.size()==1){
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//VALOR
            }
        }
        //PRODUCCION VALOR
        else if(nodo.cadena.compareTo("VALOR")==0){
            System.out.println("VALOR");
            if(nodo.hijos.size()==1){
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//LOGICA
            }
        }
        //PRODUCCION CONT2
        else if(nodo.cadena.compareTo("CONT2")==0){
            System.out.println("CONT2");
            if(nodo.hijos.get(0).cadena.compareTo("++")==0){
            
            }else if(nodo.hijos.get(0).cadena.compareTo("--")==0){
            }
            else if(nodo.hijos.get(0).cadena.compareTo("+=")==0){
            }
            else if(nodo.hijos.get(0).cadena.compareTo("-=")==0){
            }
        }
        //PRODUCCION LOGICA
        else if(nodo.cadena.compareTo("LOGICA")==0){
            System.out.println("LOGICA");
            return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//L
            //ACA ME QUEDE
            //TOMAR EN CUENTA QUE NO SE A ASIGNADO AUN VALOR Y QUE LA DECLARACION DE ARREGLOS AUN NO ESTA ECHA 
        }
        //PRODUCCION L
        else if(nodo.cadena.compareTo("L")==0){
            System.out.println("L"); 
            
            if(nodo.hijos.size()==1){ //M
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }else if(nodo.hijos.size()==2){ //M LP
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
        }
        //PRODUCCION LP
        else if(nodo.cadena.compareTo("LP")==0){
            System.out.println("LP"); 
            if(nodo.hijos.size()==2){ // or|nor|xor M
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==3){ //or|nor|xor M LP
                //aca debo de operar la relacion
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE);
            }
        }
        //PRODUCCION M
        else if(nodo.cadena.compareTo("M")==0){
            System.out.println("M");
            if(nodo.hijos.size()==1){ //N
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }else if(nodo.hijos.size()==2){ //N MP
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
        }
        //PRODUCCION MP
        else if(nodo.cadena.compareTo("MP")==0){
            System.out.println("MP"); 
            if(nodo.hijos.size()==2){ // and|nand N
                //aca debo operar la relacion
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==3){ //and|nand N MP
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE);
            }
        }
        //PRODUCCION N
        else if(nodo.cadena.compareTo("N")==0){
            System.out.println("N");
            if(nodo.hijos.size()==1){ //R
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
        }
        //PRODUCCION R
        else if(nodo.cadena.compareTo("R")==0){
            System.out.println("R");
            if(nodo.hijos.size()==1){//Z
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==2){ //not Z
                //NOT !
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
        }
        //PRODUCCION Z
        else if(nodo.cadena.compareTo("Z")==0){
            System.out.println("Z");
            if(nodo.hijos.size()==1){
                if(nodo.hijos.get(0).cadena.compareTo("RELACIONAL")==0){ //RELACIONAL
                    return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                }
                else if(nodo.hijos.get(0).cadena.compareTo("LOGICA")==0){//LOGICA
                    return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                }
            }
        }
        //PRODUCCION RELACIONAL
        else if(nodo.cadena.compareTo("RELACIONAL")==0){
            System.out.println("RELACIONAL");
            if(nodo.hijos.size()==1){// A
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
        }
        //PRODUCCION A
        else if(nodo.cadena.compareTo("A")==0){
            System.out.println("A");
            if(nodo.hijos.size()==1){// EXP
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            if(nodo.hijos.size()==2){// EXP AP
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
        }
        //PRODUCCION AP
        else if(nodo.cadena.compareTo("AP")==0){
            System.out.println("AP");
            if(nodo.hijos.size()==2){// == EXP
                //aca debo mandar a comparar ambos operadores
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
        }
        //PRODUCCION EXP
        else if(nodo.cadena.compareTo("EXP")==0){
            System.out.println("EXP");

            if(nodo.hijos.size()==1){// E
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==2){ //Nulo E
                //aca debo mandar a comparar al operador
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
            }
            
        }
        //PRODUCCION E
        else if(nodo.cadena.compareTo("E")==0){
            System.out.println("E ");
            
            if(nodo.hijos.size()==1){// T
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==2){//T EP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//T
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE);//EP
                System.out.println("    REES "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION EP
        else if(nodo.cadena.compareTo("EP")==0){
            System.out.println("EP");
            if(nodo.hijos.size()==2){// +/- T
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO EP "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){// +/- T EP
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //T
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO EP2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE); //EP
                return ret;
            }
        
        }
        //PRODUCCION T
        else if(nodo.cadena.compareTo("T")==0){                       
            System.out.println("T");
            if(nodo.hijos.size()==1){// F
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==2){//F TP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//F
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE);//TP
                System.out.println("    REES "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION TP
        else if(nodo.cadena.compareTo("TP")==0){
            System.out.println("TP");
            if(nodo.hijos.size()==2){// */div F
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO EP "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){// */div F TP
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //T
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO EP2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE); //EP
                return ret;
            }
        
        }
        //PRODUCCION F
        else if(nodo.cadena.compareTo("F")==0){                       
            System.out.println("F");
            if(nodo.hijos.size()==1){// G
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
            }
            else if(nodo.hijos.size()==2){//G FP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE);//G
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE);//FP
                System.out.println("    REES "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION FP
        else if(nodo.cadena.compareTo("FP")==0){
            System.out.println("FP");
            if(nodo.hijos.size()==2){// ^ G
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO EP "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){// ^ G FP
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //G
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO EP2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE); //FP
                return ret;
            }
        
        }
        //PRODUCCION G
        else if(nodo.cadena.compareTo("G")==0){                       
            System.out.println("G");
            if(nodo.hijos.size()==1){ //entero | doble | boolean | caracter | cadena | id | ORDEN | SUMARI
                System.out.println("    G return"+nodo.hijos.get(0).cadena);
                NodoArbol aux = new NodoArbol();
                aux = nodo.hijos.get(0);
                //return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                return aux;
            }
            else if(nodo.hijos.size()==2){ //id DIMENSION |  id PARAMETROS  |  id CONT2
                System.out.println("    G return"+nodo.hijos.get(0).cadena);
                NodoArbol aux = new NodoArbol();
                aux = nodo.hijos.get(0);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE);
                return aux;
            }
        }
        //PRODUCCION HH
        else if(nodo.cadena.compareTo("HH")==0){
            if(nodo.hijos.size()==0){ // <parenti> PARAMETROS? <parentd>
                //VIENE UN METODO SIN PARAMETROS
            }
            else if(nodo.hijos.size()==1){
                if(nodo.hijos.get(0).cadena.compareTo("DIMENSION")==0){ //DIMENSION
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                }
                else if(nodo.hijos.get(0).cadena.compareTo("PARAMETROS")==0){//PARAMETROS
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                }
                else if(nodo.hijos.get(0).cadena.compareTo("CONT2")==0){//CONT2
                    //ERROR 
                    NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error al operar ","No se pueden operar "+nodo.hijos.get(0).cadena);
                    LE.add(e);
                }
            }
        }
       
       return retorno;
    }
}


