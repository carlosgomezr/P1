/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1compi2;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Gomez
 */
public class ejecutar {
    public String directory;
    ejecutar(){
    
    } 
    
    ejecutar(String original){
        this.directory = original;
    }
    
    //VARIABLES ESTATICAS
    int banderadef=0;//variable que se activa con un 1 si en la PRODUCCION DE SENCOMPROBAR se cumple un caso x que no sea el default
    String sumarizar=""; //se sumariza la respuesta en este string
    String ordenar="";  //se ordena y retorna 1 si se ordeno correctamente y 0 si fracaso
    int principal=0;    //numero de metodos principales  min 0      max 1
    Simbolo funcion; //esta variable me va a servir para saer que funcion es a la que le tengo que dar el valor en la TS y ese 
                      //valor es el que se retorna cuando invoquen al metodo
    Simbolo m = new Simbolo();
    int invocar=0;
    NodoArbol Sretorno = new NodoArbol();
    //String typosumarizar="";
    String ambitoG="";
    public ArrayList<Heredado> llenarListaH(String lienzoinicial,ArrayList<Simbolo> LS,String idmetodo,ArrayList<ListaParametro> para){
        ArrayList<Heredado> lista = new ArrayList<Heredado>();
        Heredado h = new Heredado();
        h.lienzo = lienzoinicial;
        h.visibilidad = recorrerTSvisi(lienzoinicial,LS);
        lista.add(h);
        //Heredado auxh = lista.get(0);
        //auxh.h.add(h);
        recorrerTS(lista,LS,0);
        for(int i=0;i<lista.size();i++){
            System.out.println("    LISTA heredados -------------"+lista.get(i).lienzo+" "+lista.get(i).visibilidad);
            for(int j=0;j<lista.get(i).h.size();j++){
                System.out.println("            heredado -------------"+lista.get(i).h.get(j).lienzo+" "+lista.get(i).h.get(j).visibilidad);
            }
        }
        
        MetodoTS(lista,para,LS,idmetodo,0,lienzoinicial);
       
        return lista;
    }
    
    public void recorrerTS(ArrayList<Heredado> lista,ArrayList<Simbolo> LS,int nivel){
        System.out.println("    LienzoInicial "+lista.get(nivel).lienzo + " nivel "+nivel);
        Heredado auxh = lista.get(nivel);
        Heredado otroh = new Heredado();
        otroh.lienzo = lista.get(nivel).lienzo;
        otroh.visibilidad = recorrerTSvisi(otroh.lienzo,LS);
        auxh.h.add(otroh);
                        
        for(int i=0;i<LS.size();i++){
            System.out.println("   recorrerTS  i "+i+" nivel "+nivel);
            if(LS.get(i).lienzo.compareTo(lista.get(nivel).lienzo)==0 && LS.get(i).rol.compareTo("extendido")==0){
                
                Heredado h = new Heredado();
                h.lienzo = LS.get(i).id;
                h.visibilidad = recorrerTSvisi(LS.get(i).id,LS);
                System.out.println("            posibles lienzos ....... "+h.visibilidad+" "+h.lienzo+"   "+lista.get(nivel).lienzo);
                  
                if(h.visibilidad.compareTo("privado")!=0){
                    //if( nivel>0 && h.visibilidad.compareTo("protegido")==0 ){
                    System.out.println("            verificando lienzos ....... "+h.visibilidad+" "+h.lienzo+"   "+lista.get(nivel).lienzo);
                    if(h.visibilidad.compareTo("protegido")==0 && recorrerTScompararVisi(lista.get(nivel).lienzo,h.lienzo,LS)==1){
                        lista.add(h);
                        Heredado aux = lista.get(nivel);
                        aux.h.add(h);
                    }else if(h.visibilidad.compareTo("publico")==0){
                        lista.add(h);
                        Heredado aux = lista.get(nivel);
                        aux.h.add(h);
                    }
                    //}else {

                    //}
                }

            }
        }
        if(lista.size()-1>nivel){
            int auxnivel = nivel + 1;
            recorrerTS(lista,LS,auxnivel);
        }
    }
    
    public String recorrerTSvisi(String lienzo,ArrayList<Simbolo> LS){
        String ret="";
        for(int i=0;i<LS.size();i++){
            if(LS.get(i).id.compareTo(lienzo)==0 && LS.get(i).tipo.compareTo("lienzo")==0 && LS.get(i).rol.compareTo("lienzo")==0){
                ret=LS.get(i).visibilidad;
                break;
            }
        }
        return ret;
    }

    public int recorrerTScompararVisi(String lienzo,String heredado,ArrayList<Simbolo> LS){
        int ret=0;
        for(int i=0;i<LS.size();i++){
            if(LS.get(i).id.compareTo(heredado)==0 && LS.get(i).tipo.compareTo("lienzo")==0 && LS.get(i).rol.compareTo("extendido")==0 && LS.get(i).lienzo.compareTo(lienzo)==0){
                ret=1;
                break;
            }
        }
        return ret;
    }

    public int esPrivado(String lienzo,String idmetodo,ArrayList<Simbolo> LS){
        int ret=0;
        for(int i=0;i<LS.size();i++){
            if(LS.get(i).id.compareTo(idmetodo)==0  && LS.get(i).rol.compareTo("metodo")==0 && LS.get(i).lienzo.compareTo(lienzo)==0){
                ret=1;
                break;
            }
        }
        return ret;
    }

    
    public void MetodoTS(ArrayList<Heredado> lista,ArrayList<ListaParametro> para,ArrayList<Simbolo> LS,String idmetodo,int nivel,String invoca){
        int iteracion =0;
        System.out.println("                    MetodoTS "+idmetodo);
        for(int i=0;i<LS.size();i++){
            for(int j=0;j<lista.size();j++){
                Heredado auxh=lista.get(j);
                for(int k=0;k<auxh.h.size();k++){
                    Heredado auxhere = auxh.h.get(k);
                    if(invoca.compareTo(lista.get(j).lienzo)==0){
                        if(LS.get(i).lienzo.compareTo(auxhere.lienzo)==0 && LS.get(i).id.compareTo(idmetodo)==0 && LS.get(i).parametros.size() == para.size()){
                            if(k>0 && LS.get(i).visibilidad.compareTo("privado")==0){

                            }else{
                                VerificarMetodo(LS.get(i),para,iteracion,LS);
                                iteracion = iteracion + 1;
                            }
                        }
                    }
                    
                }
            }
        }
    }

    public int VerificarMetodo(Simbolo s,ArrayList<ListaParametro> para, int iteracion,ArrayList<Simbolo> LS){
        int bandera=0;
        int ret=0;
        System.out.println("                    VerificarMetodo    "+s.id+" i "+iteracion);
        for(int i=0;i<para.size();i++){
            if(s.parametros.get(i).parametro.compareTo(para.get(i).parametro)==0){
                bandera= bandera+1;
            }
        }
        if(bandera==para.size()){
            criterioHerencia(s,iteracion);
            reemplazoParametro(s,para,LS);
            ret=1;
        }
        return ret;
    }
    
    public void reemplazoParametro(Simbolo s,ArrayList<ListaParametro> para, ArrayList<Simbolo> LS){
        System.out.println("                    reemplazoParametro    "+s.id+"          SIZE    "+s.parametros.size());
        for(int i=0;i<para.size();i++){
            if(s.parametros.get(i).parametro.compareTo(para.get(i).parametro)==0){
                System.out.println("                    xxxxxxxxxxxxxxxxx "+s);
                // = para.get(i).valorparametro;
                String valor = para.get(i).valorparametro;
                remplaceP(s,para,LS,valor);
            }
        }
        
    }
    
    public void remplaceP(Simbolo s,ArrayList<ListaParametro> para,ArrayList<Simbolo> LS, String valor){
        System.out.println("                yyyyyyyyyyyy "+s.id+"  "+s.ambito+"  "+s.lienzo);
        for(int i=0;i<LS.size();i++){
            if(LS.get(i).rol.compareTo("parametro")==0 && LS.get(i).ambito.compareTo(s.id)==0 && LS.get(i).lienzo.compareTo(s.lienzo)==0){
                LS.get(i).valor = valor;
            }
        }
    }
    public void criterioHerencia(Simbolo s,int iteracion){
        System.out.println("     ENTRE AL criterioHerencia........ m "+m.conservar+ " s "+s.conservar);
        if(iteracion==0){
            System.out.println("                 M ES NULO ");
            //igualarSimbolos(s,m);
            m = s;
            invocar=1;
        }else if(m.conservar==null && s.conservar==null){ //SIN CONVSERVAR
            System.out.println("                 TODOS SIN CONSREVAR ");
            m = s;
            invocar=1;
        }else if(m.conservar==null && s.conservar!=null){ //SIN CONSERVAR       CON CONSERVAR
            System.out.println("                 SIN CONSERVAR      CON CONSERVAR ");
            m = s;
            invocar=1;
        }else if(m.conservar!=null && s.conservar==null){ //CON CONSERVAR       SIN CONSERVAR
            System.out.println("                 CON CONSERVAR      SIN CONSERVAR ");
            invocar=1;
        }else if(m.conservar!=null && s.conservar!=null){ //CON CONSERVAR
            System.out.println("                 CON CONSERVAR      CON CONSERVAR ");
            m = s;
            invocar=1;
        }
    }
    public NodoArbol CallMetodo(NodoArbol nodo,ArrayList<ListaParametro> para,ArrayList<Simbolo> LS,ArrayList<NodeError> LE){
        NodoArbol n = new NodoArbol();
        int size = LS.size();
        int bandera = -1;
        int indice = -1;
        for(int i=0;i<size;i++){
            if(LS.get(i).id.compareTo(nodo.cadena)==0 && LS.get(i).parametros.size()==para.size()){
                for(int k=0;k<LS.get(i).parametros.size();k++){
                    if(LS.get(i).parametros.get(k).parametro.compareTo(para.get(k).parametro)==0){
                        bandera++;
                    }
                }
                //break;
            }
        }
        
        return n;
    }
    public String convertTipoArreglo(String s){
        String ret="";
        if(s.compareTo("arreglo_entero")==0){
            ret = "entero";
        }else if(s.compareTo("arreglo_doble")==0){
            ret = "doble";
        }else if(s.compareTo("arreglo_cadena")==0){
            ret = "cadena";
        }else if(s.compareTo("arreglo_caracter")==0){
            ret = "caracter";
        }else if(s.compareTo("arreglo_boolean")==0){
            ret = "boolean";
        }
        return ret;
    }
    //METODO PARA ORDENAR 
    
    public void ordenarAscendenteInt(int actual,ListaArreglo D,int size,int tipo){ //T = 0 entero  T = 1 double
        int auxdim = D.dimension.size();
        int auxsig = D.siguientedim.size();
        if(auxdim>0 && actual<size){
            ArrayList<Integer> aux = new ArrayList<Integer>();
            ArrayList<Double> aux2 = new ArrayList<Double>();
            for(int i=0;i<auxdim;i++){
                if(tipo==0){    aux.add(Integer.parseInt(D.dimension.get(i)));   }
                else if(tipo==1){ aux2.add(Double.parseDouble(D.dimension.get(i)));  }
            }
            if(tipo==0){  
                Collections.sort(aux);
                D.dimension.clear();
                for(int i=0;i<auxdim;i++){
                    D.dimension.add(String.valueOf(aux.get(i)));
                }
            }
            else if(tipo==1){ 
                Collections.sort(aux2);
                D.dimension.clear();
                for(int i=0;i<auxdim;i++){
                    D.dimension.add(String.valueOf(aux2.get(i)));
                }
            }
            
        }else if(auxsig>0 && actual<size){
            for(int i=0;i<auxsig;i++){
                ordenarAscendenteInt(actual+1,D.siguientedim.get(i),size,tipo);
            } 
        }
    }

    public void ordenarDescendenteInt(int actual,ListaArreglo D,int size,int tipo){ //T = 0 entero  T = 1 double
        int auxdim = D.dimension.size();
        int auxsig = D.siguientedim.size();
        if(auxdim>0 && actual<size){
            ArrayList<Integer> aux = new ArrayList<Integer>();
            ArrayList<Double> aux2 = new ArrayList<Double>();
            for(int i=0;i<auxdim;i++){
                if(tipo==0){    aux.add(Integer.parseInt(D.dimension.get(i)));   }
                else if(tipo==1){ aux2.add(Double.parseDouble(D.dimension.get(i)));  }
            }
            if(tipo==0){  
                Collections.sort(aux,Collections.reverseOrder());
                D.dimension.clear();
                for(int i=0;i<auxdim;i++){
                    D.dimension.add(String.valueOf(aux.get(i)));
                }
            }
            else if(tipo==1){ 
                Collections.sort(aux2,Collections.reverseOrder());
                D.dimension.clear();
                for(int i=0;i<auxdim;i++){
                    D.dimension.add(String.valueOf(aux2.get(i)));
                }
            }
            
        }else if(auxsig>0 && actual<size){
            for(int i=0;i<auxsig;i++){
                ordenarDescendenteInt(actual+1,D.siguientedim.get(i),size,tipo);
            } 
        }
    }
    
    public void ordenarAscendente(int actual,ListaArreglo D,int size){  
        int auxdim = D.dimension.size();
        int auxsig = D.siguientedim.size();
        if(auxdim>0 && actual<size){
            Collections.sort(D.dimension);
        }else if(auxsig>0 && actual<size){
            for(int i=0;i<auxsig;i++){
                ordenarAscendente(actual+1,D.siguientedim.get(i),size);
            } 
        }
    }
        
    public void ordenarDescendente(int actual,ListaArreglo D,int size){
        int auxdim = D.dimension.size();
        int auxsig = D.siguientedim.size();
        if(auxdim>0 && actual<size){
            Collections.sort(D.dimension,Collections.reverseOrder());
        }else if(auxsig>0 && actual<size){
            for(int i=0;i<auxsig;i++){
                ordenarDescendente(actual+1,D.siguientedim.get(i),size);
            } 
        }
    }
    //METODOS PARA SUMARIZAR
    public String sumari(int actual,ListaArreglo D, int size,String ret,int t){ // t = 0 caracter t=1 cadena
        int auxdim = D.dimension.size();
        int auxsig = D.siguientedim.size();
        if(auxdim>0 && actual<size){
            for(int i=0;i<auxdim;i++){
                if(t==0){//caracter
                    int tam = D.dimension.get(i).length();
                    ret = ret + D.dimension.get(i).substring(1,tam-1);
                }
                else{//cadena
                    ret = ret + D.dimension.get(i);
                }
            }
        }else if(auxsig>0 && actual<size){
            for(int i=0;i<auxsig;i++){
                ret = sumari(actual+1,D.siguientedim.get(i),size,ret,t);
            }
        }
        return ret;
    }

    public String sumariInt(int actual,ListaArreglo D, int size,String ret){
        int auxdim = D.dimension.size();
        int auxsig = D.siguientedim.size();
       if(auxdim>0 && actual<size){
            for(int i=0;i<auxdim;i++){
                double auxret = Double.parseDouble(ret) + Double.parseDouble(D.dimension.get(i));
                System.out.println("    sumarInt<<<<<<<<"+ret);
                ret = String.valueOf(auxret);
            }
        }else if(auxsig>0 && actual<size){
            for(int i=0;i<auxsig;i++){
                ret = sumariInt(actual+1,D.siguientedim.get(i),size,ret);
            }
        }
        return ret;
    }
    
    //METODO PARA PARSERAR 
    public NodoArbol parser(String archivo,ArrayList<Simbolo> LS, ArrayList<NodeError> LE, ArrayList<dibujo> LDibujo){
        NodoArbol ret = new NodoArbol();
        try {
            BufferedReader br = new BufferedReader(
                                new FileReader(directory+"\\"+archivo));
            String s, s2 = new String();

            while ((s = br.readLine()) != null){
                s2 += s+"\n";
            }
            br.close(); 
            compilador ana = new compilador();
            ana.analizar(s2);
            Funcion f = new Funcion();
            f.graphArbol(ana.root,archivo);
            ret = ana.root;
            ejecutar ejec = new ejecutar(directory);
            Simbolo sim = new Simbolo();
            ArrayList<String> Dim = new ArrayList<String>();
            ArrayList<String> words = new ArrayList<String>();
            NodoArbol otronodo = new NodoArbol();
            ArrayList<ListaParametro> para = new ArrayList<ListaParametro>();
            String ambito="";
            ejec.ejecucion(ana.root, LS,ana.root,sim,LE,Dim,LDibujo,words,otronodo,0,para,ambito);
            System.out.println("    root "+archivo+"  "+ret);
            //System.out.println("Root \n Nombre root "+ana.root.nombre+" grafoname "+ana.root.grafoname+" cadena "+ana.root.cadena+" numero "+ana.root.numero); 
            //System.out.println("S\n Nombre "+ana.root.hijos.get(0).nombre+" grafoname "+ana.root.hijos.get(0).grafoname);
            //System.out.println("COMMM\n Nombre "+ana.root.hijos.get(0).hijos.get(0).nombre+" grafoname "+ana.root.hijos.get(0).hijos.get(0).grafoname);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
   
    
    //METODOS PARA MANEJO DE ARREGLOS
        public void crearArreglo(ArrayList<String> Dim,int actual,ListaArreglo D){
        ListaArreglo auxiliar = new ListaArreglo();
        
        if(actual==0 && Dim.size()-1>0){
//            System.out.println("    -Dimension "+Dim.get(actual) +" "+actual+"  D.size "+D.siguientedim.size());
            for(int i=0;i<(int)Double.parseDouble(Dim.get(actual));i++){
                ListaArreglo nueva = new ListaArreglo();
                //D.siguientedim = new ArrayList<ListaArreglo>();
                D.siguientedim.add(nueva);
                auxiliar.siguientedim.add(nueva);
//                System.out.println("    actual<Dim.size "+i+" actual "+actual);
            }
            int auxactual = actual +1;
            crearArreglo(Dim,auxactual,auxiliar);
        }
        else if(actual==0 && Dim.size()-1==0){
            for(int i=0;i<(int)Double.parseDouble(Dim.get(actual));i++){
                String nueva=null;
                D.dimension.add(nueva);
//                System.out.println(" >> insertando word"+i);
            }
        }
        else if(actual<Dim.size()-1 && actual>0)
        {
            System.out.println("    -Dimension "+Dim.get(actual) +" "+actual+"  D.size "+D.siguientedim.size());
            int x = actual -1;
            
            for(int i=0;i<D.siguientedim.size();i++){
             
                for(int j=0;j<(int)Double.parseDouble(Dim.get(actual));j++){
                    ListaArreglo nueva = new ListaArreglo();
                    D.siguientedim.get(i).siguientedim.add(nueva);
                    auxiliar.siguientedim.add(nueva);
  //                  System.out.println(" >> insertando "+i+"  "+j);
                }
            }
            
            int auxactual = actual + 1;
            crearArreglo(Dim,auxactual,auxiliar);
        }
        else if(actual==Dim.size()-1){
    //        System.out.println("    -Dimension "+Dim.get(actual) +" "+actual+"  D.size "+D.siguientedim.size());
            int x = actual -1;
            for(int i=0;i<D.siguientedim.size();i++){
                
                for(int j=0;j<(int)Double.parseDouble(Dim.get(actual));j++){
                    String nueva=null;
                    D.siguientedim.get(i).dimension.add(nueva);
        //            System.out.println(" >> insertando palabras"+i+"  "+j);
                    //auxiliar.siguientedim.add(nueva);
                }
            }
        }
    }


    public void insertArreglo(ArrayList<String> Dim,int actual,ListaArreglo D,String word){
        
        if(actual==0 && Dim.size()-1>0){
            int auxactual = actual +1;
            insertArreglo(Dim,auxactual,D.siguientedim.get((int)Double.parseDouble(Dim.get(actual))),word);
        }
        else if(actual==0 && Dim.size()-1==0){
            D.dimension.remove((int)Double.parseDouble(Dim.get(actual)));
            D.dimension.add((int)Double.parseDouble(Dim.get(actual)),word);
        }
        else if(actual<Dim.size()-1 && actual>0)
        {
            int auxactual = actual + 1;
            insertArreglo(Dim,auxactual,D.siguientedim.get((int)Double.parseDouble(Dim.get(actual))),word);
        }
        else if(actual==Dim.size()-1){
            D.dimension.remove((int)Double.parseDouble(Dim.get(actual)));
            D.dimension.add((int)Double.parseDouble(Dim.get(actual)),word);
        }
    }

    int position=0;
    public void insertAllArreglo(ArrayList<String> Dim,int actual,ListaArreglo D,ArrayList<String> word){
        
        if(actual==0 && Dim.size()-1>0){
            int auxactual = actual +1;
            for(int i=0; i<D.siguientedim.size();i++){
                insertAllArreglo(Dim,auxactual,D.siguientedim.get(i),word);
            }
            
        }
        else if(actual==0 && Dim.size()-1==0){
            System.out.println("    ---"+(int)Double.parseDouble(Dim.get(actual))+word.get(0) + word.size());
            for(int i=0; i<(int)Double.parseDouble(Dim.get(actual));i++){
                D.dimension.remove(i);
                D.dimension.add(i, word.get(i));
                System.out.println("    insertado word --"+i+" "+word.get(i)+" "+D.dimension.get(i));
            }
           
        }
        else if(actual<Dim.size()-1 && actual>0)
        {
            int auxactual = actual +1;
            for(int i=0; i<D.siguientedim.size();i++){
                insertAllArreglo(Dim,auxactual,D.siguientedim.get(i),word);
            }
        }
        else if(actual==Dim.size()-1){
            for(int i=0; i<(int)Double.parseDouble(Dim.get(actual));i++){
                D.dimension.remove(i);
                D.dimension.add(i,word.get(position));
                position++;
                System.out.println("    insertado word "+i+" "+word.get(i)+" "+D.dimension.get(i));
                //word.remove(0);
            }
            for(int i=0; i<(int)Double.parseDouble(Dim.get(actual));i++){
                //word.remove(0);
            }
        }
    }    

    
    public String getvalArreglo(ArrayList<String> Dim,int actual,ListaArreglo D,String word){
        String ret = word;
        if(actual==0 && Dim.size()-1>0){
            int auxactual = actual +1;
            ret = getvalArreglo(Dim,auxactual,D.siguientedim.get((int)Double.parseDouble(Dim.get(actual))),word);
        }
        else if(actual==0 && Dim.size()-1==0){
            ret = D.dimension.get((int)Double.parseDouble(Dim.get(actual)));
        }
        else if(actual<Dim.size()-1 && actual>0)
        {
            int auxactual = actual + 1;
            ret = getvalArreglo(Dim,auxactual,D.siguientedim.get((int)Double.parseDouble(Dim.get(actual))),word);
        }
        else if(actual==Dim.size()-1){
            ret = D.dimension.get((int)Double.parseDouble(Dim.get(actual)));
            //System.out.println("    find---"+D.dimension.get(Integer.parseInt(Dim.get(actual)))+"   "+ret);            
        }
        
        return ret;
    }

    
    public int multiDimension(ArrayList<String> Dim, int ListaSize,ArrayList<NodeError> LE,String arreglo,int fila,int columna){
        int size = Dim.size();
        int aux=1; 
        int ret =0;
        
        for(int i=0; i<size; i++){
            aux = aux * Integer.parseInt(Dim.get(i));
        }
        if(aux!=ListaSize){
            ret = 1;
            //CREAR ERROR
            if(ListaSize!=0){
                System.out.println("    Size Error Array");
                NodeError e = new NodeError(fila,columna,"Error en tamanio del arreglo","Arreglo "+arreglo+" El tamano de arreglo es  "+aux + " valores recibidos "+ListaSize);
                LE.add(e);
            }
        }
        System.out.println("    mmultisize "+aux);
        return ret; // 1 exite eerror                   0 no existe error
    }
    
    
    //METODOS PARA LA VALIDACION DE COORDENADAS
    public int verCoordenada(NodoArbol n,ArrayList<NodeError> LE){
        int bandera = 1;
        if(n.ty.compareTo("entero")!=0){
            NodeError e = new NodeError(n.f,n.c,"Error al asignar coordenada","El tipo de dato no coincide "+n.ty);
            LE.add(e);
            bandera = 0;
        }
        return bandera;
    }
    
    public int verCadena(NodoArbol n,ArrayList<NodeError> LE){
        int bandera = 1;
        if(n.ty.compareTo("cadena")!=0){
            NodeError e = new NodeError(n.f,n.c,"Error al asignar cadena","El tipo de dato no coincide "+n.ty);
            LE.add(e);
            bandera = 0;
        }   
        return bandera;
    }
    
    public int verColor(NodoArbol n,ArrayList<NodeError> LE){
        int bandera = 1;
        if(n.ty.compareTo("cadena")!=0){
            NodeError e = new NodeError(n.f,n.c,"Error al asignar color","El tipo de dato no coincide "+n.ty);
            LE.add(e);
            bandera = 0;
        }   
        return bandera;
    }
    
    public int verFigura(NodoArbol n,ArrayList<NodeError> LE){
        int bandera = 1;
        if(n.ty.compareTo("caracter")!=0){
            NodeError e = new NodeError(n.f,n.c,"Error al asignar figura","El tipo de dato no coincide "+n.ty);
            LE.add(e);
            bandera = 0;
        }   
        return bandera;
    }
    
    //METODOS PARA EL MANEJO DE LA TABLA DE SIMBOLOS
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
        
    
    public void buscarRSimboloParametro(Simbolo simbolo,ArrayList<ListaParametro> para, ArrayList<Simbolo> LS, String lienzo){ //buscar simbolo en la tabla 
                                                                                         //y le asigna el nuevo valor
                                                                                         //esto se usa en la asignacion de variables
        //NodoArbol s = new NodoArbol();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i)==simbolo && LS.get(i).lienzo.compareTo(lienzo)==0){
                    LS.get(i).parametros = para;
                    //igualarSimbolos(simbolo,LS.get(i));
                    System.out.println("    añadiendo parametros.... ");
                    break;
                }
            }
        //return s;
    }
    
    public void buscarRSimbolo(String simbolo,String nuevovalor, ArrayList<Simbolo> LS, String lienzo, String ambito){ //buscar simbolo en la tabla 
                                                                                         //y le asigna el nuevo valor
                                                                                         //esto se usa en la asignacion de variables
        //NodoArbol s = new NodoArbol();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(simbolo)==0 && LS.get(i).lienzo.compareTo(lienzo)==0 && LS.get(i).ambito.compareTo(ambito)==0){
                    LS.get(i).valor = nuevovalor;
                    //igualarSimbolos(simbolo,LS.get(i));
                    System.out.println("    lo encontre ");
                    break;
                }
            }
        //return s;
    }
    
    public Simbolo buscarRetornarSimbolo(String simbolo,ArrayList<Simbolo> LS, String lienzo){ //busca y retorna el simbolo                                                                                   //esto se usa en la asignacion de variables
        Simbolo s = new Simbolo();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(simbolo)==0 && LS.get(i).lienzo.compareTo(lienzo)==0){
                    //igualarSimbolos(LS.get(i),s);
                    s.id = LS.get(i).id;
                    s.valor = LS.get(i).valor;
                    s.tipo = LS.get(i).tipo;
                    s.ambito = LS.get(i).ambito;
                    s.fila = LS.get(i).fila;
                    s.columna = LS.get(i).columna;
                    
                 
                    System.out.println("    buscar Retornar Simbolo "+simbolo);
                    break;
                }
            }
        return s;
    }
    
    //busca simbolo de la tabla de simbolos (solo arreglos y reemplaza
    public void buscarRArregloSimbolo(String simbolo,ListaArreglo nuevovalor, ArrayList<Simbolo> LS, String lienzo){
               for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(simbolo)==0 && LS.get(i).lienzo.compareTo(lienzo)==0){
                    LS.get(i).arreglo = nuevovalor;
                    //igualarSimbolos(simbolo,LS.get(i));
                    System.out.println("    lo encontre ");
                    break;
                }
            }
    }
    
    public NodoArbol buscarSimbolo(NodoArbol nodo, ArrayList<Simbolo> LS, String lienzo){
        NodoArbol s = new NodoArbol();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(nodo.cadena)==0){
                    s.cadena = LS.get(i).valor;
                    s.ty = LS.get(i).tipo;
                    s.f = LS.get(i).fila;
                    s.c = LS.get(i).columna; 
                }
            }
        return s;
    }

    public Simbolo buscarSimboloArr(NodoArbol nodo, ArrayList<Simbolo> LS, String lienzo,ArrayList<NodeError> LE){
        Simbolo s = new Simbolo();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(nodo.cadena)==0){
                    s.id = LS.get(i).id;
                    s.columna = LS.get(i).columna;
                    s.fila = LS.get(i).fila;
                    s.tipo = LS.get(i).tipo;
                    s.valor = LS.get(i).valor;
                    s.tipo = LS.get(i).tipo;
                    s.size = LS.get(i).size;
                    s.arreglo = LS.get(i).arreglo;
                    
                }
            }
            if(s.tipo==null){
                        NodeError e = new NodeError(nodo.f,nodo.c,"Error al asignar valor","No existe valor en el arreglo "+nodo.cadena+" l ");
                        LE.add(e);
                        System.out.println(" s"+s.valor);
            }
        return s;
    }
    
    public NodoArbol buscarSimbolo(Simbolo sim, ArrayList<Simbolo> LS, String lienzo){
        NodoArbol s = new NodoArbol();
            for(int i=0;i<LS.size();i++){
                if(LS.get(i).id.compareTo(sim.id)==0){
                    s.cadena = LS.get(i).valor;
                    s.ty = LS.get(i).tipo;
                    s.f = LS.get(i).fila;
                    s.c = LS.get(i).columna;
                }
            }
        return s;
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
        copia.hacer = original.hacer;
        copia.para = original.para;
    }
    public void igualarSimbolos(Simbolo original, Simbolo copia){
        copia.ambito = original.ambito;
        copia.arreglo = new ListaArreglo();
        copia.arreglo = original.arreglo;
        copia.size = original.size;
        copia.conservar = original.conservar;
        copia.id = original.id;
        copia.rol = original.rol;
        copia.tipo = original.tipo;
        copia.valor = original.valor;
        copia.visibilidad = original.visibilidad;
        copia.fila = original.fila;
        copia.columna = original.columna;
        copia.lienzo = original.lienzo;
        copia.root = original.root;
        copia.dim = original.dim;
        copia.heredado = new ArrayList<String>();
        copia.heredado = original.heredado;
        copia.parametros = new ArrayList<ListaParametro>();
        copia.parametros = original.parametros;
    }
    
    
    public int ExisteMetodo(Simbolo s,ArrayList<ListaParametro> para){
        int bandera=0;
        int ret=0;
        int aux=0;
        if(s.parametros.size()>=para.size()){
            aux = para.size();
        }
        else{
            aux = s.parametros.size();
        }
        System.out.println("                    Existe Metodo?    "+s.id);
        for(int i=0;i<aux;i++){
            if(s.parametros.get(i).parametro.compareTo(para.get(i).parametro)==0){
                bandera= bandera+1;
            }
        }
        if(bandera==para.size()){
            ret=1;
        }
        return ret;
    }
        
    public void verificarSimbolo(Simbolo s,ArrayList<Simbolo> LS,ArrayList<NodeError> LE){
        int size = LS.size();
        int conteo =0;
        for(int i=0;i<size;i++){
            
            if(LS.get(i).id.compareTo(s.id)==0 && LS.get(i).ambito.compareTo(s.ambito)==0 && LS.get(i).lienzo.compareTo(s.lienzo)==0){
                if(LS.get(i).rol.compareTo("metodo")==0 && s.rol.compareTo("metodo")==0){
                    if(ExisteMetodo(LS.get(i),s.parametros)==1){
                        conteo=conteo+1;
                    }   
                }else{
                    NodeError e = new NodeError(s.fila,s.columna,"redefinicion de variable ", s.id +" ya fue definida anteriormente en ambito "+s.ambito+" lienzo "+s.lienzo);
                    LE.add(e); 
                    break;
                }
            }
        }
        if(conteo>=2){
            NodeError e = new NodeError(s.fila,s.columna,"redefinicion de metodo ", s.id +" ya fue definida anteriormente en ambito "+s.ambito+" lienzo "+s.lienzo);
            LE.add(e);
        }
        
    }
    
    public void verificarSimboloMetodo(Simbolo s,ArrayList<Simbolo> LS,ArrayList<NodeError> LE){
        int size = LS.size();
        int bandera = -1;
        int existentes = 0;
        for(int i=0;i<size;i++){
            if(LS.get(i).id.compareTo(s.id)==0 && LS.get(i).ambito.compareTo(s.ambito)==0 && LS.get(i).lienzo.compareTo(s.lienzo)==0 && LS.get(i).parametros.size()==s.parametros.size()){
                for(int k=0;k<LS.get(i).parametros.size();k++){
                    if(LS.get(i).parametros.get(k).parametro.compareTo(s.parametros.get(k).parametro)==0){
                        bandera++;
                    }
                }
                //break;
            }
        }
        if(bandera==s.parametros.size()-1){
            NodeError e = new NodeError(s.fila,s.columna,"redefinicion de Metodo... ", s.id +" ya fue definida anteriormente en ambito "+s.ambito+" parametros "+s.parametros.size());
            LE.add(e); 
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
    
    public NodoArbol operarSimplificado(NodoArbol operador1, NodoArbol operador2, NodoArbol op,ArrayList<Simbolo> LS, ArrayList<NodeError> LE){
        NodoArbol nodo = new NodoArbol();  
        System.out.println(" operador 1 "+operador1.cadena+"  "+operador1.ty +"  "+ operador2.cadena+" "+operador2.ty);
        if(op.cadena.compareTo("++")==0){
            if(operador1.ty.compareTo("doble")==0){
                double aux = Double.parseDouble(operador1.cadena);
                aux++;
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("entero")==0){
                int aux = Integer.parseInt(operador1.cadena);
                aux++;
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("caracter")==0){
                int aux = operador1.cadena.charAt(1);
                aux++;
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "entero";
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al aumentar ","No se puede aumentar "+operador1.cadena+" tipo "+operador1.ty);
                LE.add(e);     
            }
        }
        else if(op.cadena.compareTo("--")==0){
            if(operador1.ty.compareTo("doble")==0){
                double aux = Double.parseDouble(operador1.cadena);
                aux--;
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("entero")==0){
                int aux = Integer.parseInt(operador1.cadena);
                aux--;
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("caracter")==0){
                int aux = operador1.cadena.charAt(1);
                aux--;
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "entero";
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al aumentar ","No se puede aumentar "+operador1.cadena+" tipo "+operador1.ty);
                LE.add(e);     
            }
        }
        else if(op.cadena.compareTo("+=")==0){
            //CADENA
            if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("caracter")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.cadena = nodo.cadena.replace("'","");
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("cadena")==0){
                nodo.cadena = operador1.cadena.replace("'","") + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("entero")==0){
                nodo.cadena = operador1.cadena.replace("'","") + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("caracter")==0 && operador2.ty.compareTo("doble")==0){
                nodo.cadena = operador1.cadena.replace("'","") + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("cadena")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("caracter")==0){
                nodo.cadena = operador1.cadena + operador2.cadena.replace("'","");
                nodo.ty = "cadena";
            }
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("entero")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }            
            else if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("doble")==0){
                nodo.cadena = operador1.cadena + operador2.cadena;
                nodo.ty = "cadena";
            }   
            //ENTERO
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("entero")==0){
                int aux = Integer.parseInt(operador1.cadena) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0){
                double aux = Integer.parseInt(operador1.cadena) + Double.parseDouble(operador2.cadena);
                int aux2 = (int)aux;
                nodo.cadena = String.valueOf(aux2);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("caracter")==0){
                int aux = Integer.parseInt(operador1.cadena) + operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(aux);
                nodo.ty="entero";
            }
            //DOBLE
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                double aux = Double.parseDouble(operador1.cadena) + Double.parseDouble(operador2.cadena);
                nodo.cadena = String.valueOf(aux);
                nodo.ty="doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0){
                double aux = Double.parseDouble(operador1.cadena) + Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(aux);
                nodo.ty="doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0){
                double aux = Double.parseDouble(operador1.cadena) + operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(aux);
                nodo.ty="doble";
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al sumar simplificado","Valor tipo "+operador1.ty+" incompatible con tipo "+operador2.ty);
                LE.add(e);
            }            
        }
        else if(op.cadena.compareTo("-=")==0){
        //String
            if(operador1.ty.compareTo("cadena")==0 && operador2.ty.compareTo("entero")==0){
                int aux = operador1.cadena.length() - Integer.parseInt(operador2.cadena);
                nodo.cadena = operador1.cadena.substring(0,aux);
                nodo.ty="cadena";
            }
        //ENTERO    
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("entero")==0){
                int aux = Integer.parseInt(operador1.cadena) - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "entero";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("doble")==0){
                double aux = Integer.parseInt(operador1.cadena) - Double.parseDouble(operador2.cadena);
                int aux2 = (int)aux;
                nodo.cadena = String.valueOf(aux2);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("entero")==0 && operador2.ty.compareTo("caracter")==0){
                double aux = Integer.parseInt(operador1.cadena) - operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "entero";
            }
        //DOBLE
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("doble")==0){
                double aux = Double.parseDouble(operador1.cadena) - Double.parseDouble(operador2.cadena);
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "doble";
            }
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("entero")==0){
                double aux = Double.parseDouble(operador1.cadena) - Integer.parseInt(operador2.cadena);
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "doble";
            }            
            else if(operador1.ty.compareTo("doble")==0 && operador2.ty.compareTo("caracter")==0){
                double aux = Double.parseDouble(operador1.cadena) - operador2.cadena.charAt(1);
                nodo.cadena = String.valueOf(aux);
                nodo.ty = "doble";
            }else{
                NodeError e = new NodeError(operador1.f,operador1.c,"Error al restar simplificado","Valor tipo "+operador1.ty+" incompatible con tipo "+operador2.ty);
                LE.add(e);
            }
            
        }
        return nodo;
    }
    
    public int pasarBoolean(String valor){
        int ret=-1;
        if(valor.compareTo("verdadero")==0 || valor.compareTo("true")==0 || valor.compareTo("1")==0){
            ret=1;
        }else if(valor.compareTo("falso")==0 || valor.compareTo("false")==0 || valor.compareTo("0")==0){
            ret=0;
        }
        return ret;
    } 
    public NodoArbol casteo(Simbolo operador1, String operador2, ArrayList<Simbolo> LS, ArrayList<NodeError> LE, String lienzo){
        NodoArbol nodo = new NodoArbol();
        NodoArbol aux1 = buscarSimbolo(operador1,LS,lienzo);
        System.out.println("    CASTEO  >>>>"+operador1.id+" "+operador1.valor+" "+operador1.tipo+ "  "+operador2);
        if(aux1.cadena==null){
            NodeError e = new NodeError(operador1.fila,operador1.columna,"Error al asignar ","Error en el valor asignado a "+operador1.id);
            LE.add(e);        
        }
        if(operador1.tipo.compareTo("entero")==0 && operador2.compareTo("entero")==0){
            nodo.ty = "entero";
        }
        else if(operador1.tipo.compareTo("entero")==0 && operador2.compareTo("cadena")==0){
            //error
            NodeError e = new NodeError(operador1.fila,operador1.columna,"Error al asignar ","Variable "+operador1.id+" tipo "+operador1.tipo+" se asigno "+operador2);
            LE.add(e); 
        }
        else if(operador1.tipo.compareTo("entero")==0 && operador2.compareTo("boolean")==0){
            nodo.ty = "entero";
            int aux = pasarBoolean(operador1.valor);
            operador1.valor = String.valueOf(aux);
        }
        else if(operador1.tipo.compareTo("entero")==0 && operador2.compareTo("doble")==0){
            nodo.ty = "entero";
            double aux2 = Double.parseDouble(operador1.valor);
            int aux = (int)Double.valueOf(aux2).intValue();
            System.out.println("                aux "+aux);
            operador1.valor = String.valueOf(aux);
            System.out.println("                operador1.valor "+operador1.valor);
        }
        else if(operador1.tipo.compareTo("entero")==0 && operador2.compareTo("caracter")==0){
            nodo.ty = "entero";
            int aux = operador1.valor.charAt(1);
            operador1.valor = String.valueOf(aux);
        }
        else if(operador1.tipo.compareTo("cadena")==0){
            nodo.ty = "cadena";            
        }
        else if(operador1.tipo.compareTo("boolean")==0){
            if(operador2.compareTo("boolean")==0){
                nodo.ty = "boolean";
            }
            else if(operador2.compareTo("entero")==0 && (operador1.valor.compareTo("1")==0 || operador1.valor.compareTo("0")==0)){
                nodo.ty = "boolean";
            }
            else{
                //error
                NodeError e = new NodeError(operador1.fila,operador1.columna,"Error al asignar ","Variable "+operador1.id+" tipo "+operador1.tipo+" se asigno "+operador2);
                LE.add(e); 
            }
        }
        else if(operador1.tipo.compareTo("doble")==0 && operador2.compareTo("entero")==0){
            nodo.ty = "doble";
            double aux = Double.parseDouble(operador1.valor);
            operador1.valor = String.valueOf(aux);
        }
        else if(operador1.tipo.compareTo("doble")==0 && operador2.compareTo("doble")==0){
            nodo.ty = "doble";
        } 
        else if(operador1.tipo.compareTo("doble")==0 && operador2.compareTo("cadena")==0){
            //error
            NodeError e = new NodeError(operador1.fila,operador1.columna,"Error al asignar ","Variable "+operador1.id+" tipo "+operador1.tipo+" se asigno "+operador2);
            LE.add(e); 
        } 
        else if(operador1.tipo.compareTo("doble")==0 && operador2.compareTo("boolean")==0){
            //error
            NodeError e = new NodeError(operador1.fila,operador1.columna,"Error al asignar ","Variable "+operador1.id+" tipo "+operador1.tipo+" se asigno "+operador2);
            LE.add(e); 
        }
        else if(operador1.tipo.compareTo("doble")==0 && operador2.compareTo("caracter")==0){
            nodo.ty = "doble";
            double aux = operador1.valor.charAt(1);
            operador1.valor = String.valueOf(aux);
        }
        else if(operador1.tipo.compareTo("caracter")==0 && operador2.compareTo("entero")==0){
            nodo.ty = "caracter";
            int aux =  Integer.parseInt(operador1.valor);
            char aux2 =  Integer.toString(aux).charAt(0);
            operador1.valor = String.valueOf(aux2);
        } 
        else if(operador1.tipo.compareTo("caracter")==0 && operador2.compareTo("caracter")==0){
            nodo.ty = "caracter";
        } 
        else if(operador1.tipo.compareTo("caracter")==0 && (operador2.compareTo("boolean")==0 || operador2.compareTo("doble")==0) || operador2.compareTo("cadena")==0){
            //error
            NodeError e = new NodeError(operador1.fila,operador1.columna,"Error al asignar ","Variable "+operador1.id+" tipo "+operador1.tipo+" se asigno "+operador2);
            LE.add(e);
        } 
        return nodo;
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
    
    public NodoArbol relacional(NodoArbol operador1, NodoArbol operador2, NodoArbol op, ArrayList<Simbolo> LS, ArrayList<NodeError> LE){
        NodoArbol nodo = new NodoArbol();
        nodo.ty="boolean";
        System.out.println("    operador1 "+operador1.cadena + " op "+op.cadena+" operador2 "+operador2.cadena);
        
        if(operador1.ty.compareTo("boolean")==0 && (operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0)){
            operador1.cadena = "true";
        }else if(operador1.ty.compareTo("boolean")==0 && (operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0)){
            operador1.cadena = "false";
        }
        
        if(operador2.ty.compareTo("boolean")==0 && operador1.ty.compareTo("entero")==0 && operador1.cadena.compareTo("1")==0){
            operador1.cadena = "true";
        }else if(operador2.ty.compareTo("boolean")==0 && operador1.ty.compareTo("entero")==0 && operador1.cadena.compareTo("0")==0){
            operador1.cadena = "false";
        }      
        
        if(operador2.ty.compareTo("boolean")==0 && (operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0)){
            operador2.cadena = "true";
        }else if(operador2.ty.compareTo("boolean")==0 && (operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0)){
            operador2.cadena = "false";
        }

        if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("entero")==0 && operador2.cadena.compareTo("1")==0){
            operador2.cadena = "true";
        }else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("entero")==0 && operador2.cadena.compareTo("0")==0){
            operador2.cadena = "false";
        }
                
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
        else if(op.cadena.compareTo("nulo")==0){
            nodo.cadena=getvalorTS(operador1,LS);
            
            if(operador1.ty.compareTo("id")!=0){
                crearError(operador1.f,operador1.c,"Error Nulo ","No se puede aplicar nulo a "+operador1.cadena,LE);
            }
        }
        //debo revisar en la tabla de simbolos para saber si la variable ya tiene asignado un valor
        
        
        return nodo;
    }
   
    
    public NodoArbol logica(NodoArbol operador1, NodoArbol operador2, NodoArbol op, ArrayList<Simbolo> LS, ArrayList<NodeError> Le){
        NodoArbol nodo = new NodoArbol();
        nodo.ty = "boolean";
      
        System.out.println("    operador1 "+operador1.cadena + " op "+op.cadena+" operador2 "+operador2.cadena);
        
        if(operador1.ty.compareTo("boolean")==0 && (operador1.cadena.compareTo("verdadero")==0 || operador1.cadena.compareTo("1")==0)){
            operador1.cadena = "true";
        }else if(operador1.ty.compareTo("boolean")==0 && (operador1.cadena.compareTo("falso")==0 || operador1.cadena.compareTo("0")==0)){
            operador1.cadena = "false";
        }
        
        if(operador2.ty.compareTo("boolean")==0 && operador1.ty.compareTo("entero")==0 && operador1.cadena.compareTo("1")==0){
            operador1.cadena = "true";
        }else if(operador2.ty.compareTo("boolean")==0 && operador1.ty.compareTo("entero")==0 && operador1.cadena.compareTo("0")==0){
            operador1.cadena = "false";
        }      
        
        if(operador2.ty.compareTo("boolean")==0 && (operador2.cadena.compareTo("verdadero")==0 || operador2.cadena.compareTo("1")==0)){
            operador2.cadena = "true";
        }else if(operador2.ty.compareTo("boolean")==0 && (operador2.cadena.compareTo("falso")==0 || operador2.cadena.compareTo("0")==0)){
            operador2.cadena = "false";
        }

        if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("entero")==0 && operador2.cadena.compareTo("1")==0){
            operador2.cadena = "true";
        }else if(operador1.ty.compareTo("boolean")==0 && operador2.ty.compareTo("entero")==0 && operador2.cadena.compareTo("0")==0){
            operador2.cadena = "false";
        }
        
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
    
    public Object ejecucion(NodoArbol nodo, ArrayList<Simbolo> LS,NodoArbol padre,Simbolo token, ArrayList<NodeError> LE, ArrayList<String> Dim, ArrayList<dibujo> LDibujo,ArrayList<String> words,NodoArbol otronodo,int nivel,ArrayList<ListaParametro> para, String ambito){
        NodoArbol retorno= new NodoArbol();
        
        //PRODUCCION INICIAR
        if(nodo.cadena.compareTo("Ini")==0){
            System.out.println("Ini");
            ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
        }
        //PRODUCCION S
        else if(nodo.cadena.compareTo("S")==0){//(visi)? lienzo id (extiende)? (sentencias)?
            System.out.println("S");
            
            if(nodo.hijos.size()==2){ //lienzo id
                Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                s.visibilidad = "publico";
                s.lienzo = nodo.hijos.get(1).cadena;
                LS.add(s);
            }
            else if(nodo.hijos.size()==3){
                if(nodo.hijos.get(0).cadena.compareTo("VISI")==0){ //visi lienzo id
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);                    
                    s.lienzo = nodo.hijos.get(2).cadena;
                    LS.add(s);                    
                }
                else if(nodo.hijos.get(2).cadena.compareTo("EXT")==0){  //lienzo id extiende
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    s.lienzo = nodo.hijos.get(1).cadena;
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                }
                else if(nodo.hijos.get(2).cadena.compareTo("SENTENCIAS")==0){  //lienzo id sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    s.lienzo = nodo.hijos.get(1).cadena;
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    token.lienzo = nodo.hijos.get(1).cadena;//aca le mando el lienzo a todas las variables 
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    //ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE);
                }
            }
            else if(nodo.hijos.size()==4){
                if(nodo.hijos.get(0).cadena.compareTo("VISI")==0 && nodo.hijos.get(3).cadena.compareTo("EXT")==0){ //visi lienzo id extiende
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    s.lienzo = nodo.hijos.get(2).cadena;
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(2).cadena;
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                }
                else if(nodo.hijos.get(0).cadena.compareTo("VISI")==0 && nodo.hijos.get(3).cadena.compareTo("SENTENCIAS")==0){ //visi lienzo id sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    s.lienzo = nodo.hijos.get(2).cadena;
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(2).cadena;
                    token.lienzo = nodo.hijos.get(2).cadena;//aca le mando el lienzo a todas las variables 
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    //ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                }
                else if(nodo.hijos.get(2).cadena.compareTo("EXT")==0 && nodo.hijos.get(3).cadena.compareTo("SENTENCIAS")==0){ //lienzo id extiende sentencias
                    //JOptionPane.showMessageDialog(null,"lienzo id ext sen sentencia");
                    Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = "publico";
                    s.lienzo = nodo.hijos.get(1).cadena;
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(1).cadena;
                    token.lienzo = nodo.hijos.get(1).cadena;//aca le mando el lienzo a todas las variables 
                    ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    //ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE);
                }
                                
            }
            else if(nodo.hijos.size()==5){ //visi lienzo id ext sentencias
                    Simbolo s = new Simbolo(nodo.hijos.get(2).cadena,"lienzo","lienzo","principal");
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,nodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    s.lienzo = nodo.hijos.get(2).cadena;
                    LS.add(s);
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(2).cadena;
                    token.lienzo = nodo.hijos.get(2).cadena;//aca le mando el lienzo a todas las variables 
                    ejecucion(nodo.hijos.get(3),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    ejecucion(nodo.hijos.get(4),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
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
                System.out.println("    extiende "+nodo.hijos.get(1).cadena);
                Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","extendido","principal");
                s.root = parser(nodo.hijos.get(1).cadena+".lz",LS,LE,LDibujo);
                s.lienzo = token.lienzo;
                //token.heredado.add(nodo.hijos.get(1).cadena);
                LS.add(s);
                //return nodo.hijos.get(1);
            }
            else if(nodo.hijos.size()==3){ //extiende id (ext1)?
                System.out.println("    extiende "+nodo.hijos.get(1).cadena);
                Simbolo s = new Simbolo(nodo.hijos.get(1).cadena,"lienzo","extendido","principal");
                s.root = parser(nodo.hijos.get(1).cadena+".lz",LS,LE,LDibujo);
                s.lienzo = token.lienzo;
                LS.add(s);
                //token.heredado.add(nodo.hijos.get(1).cadena);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                //return nodo.hijos.get(1);
            }
        }
        //PRODUCCION EXT1
        else if(nodo.cadena.compareTo("EXT1")==0){ 
            System.out.println("EXT1");
            
            if(nodo.hijos.size()==1){//id
                System.out.println("    extiende "+nodo.hijos.get(0).cadena);
                Simbolo s = new Simbolo(nodo.hijos.get(0).cadena,"lienzo","extendido","principal");
                s.root = parser(nodo.hijos.get(0).cadena+".lz",LS,LE,LDibujo);
                s.lienzo = token.lienzo;
                LS.add(s);
                //return nodo.hijos.get(0);
            }
            else if(nodo.hijos.size()==2){// id ext1
                System.out.println("    extiende "+nodo.hijos.get(0).cadena);
                Simbolo s = new Simbolo(nodo.hijos.get(0).cadena,"lienzo","extendido","principal");
                s.root = parser(nodo.hijos.get(0).cadena+".lz",LS,LE,LDibujo);
                s.lienzo = token.lienzo;
                LS.add(s);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                //return nodo.hijos.get(0);
            }
        }
        //PRODUCCION SENTENCIAS
        else if(nodo.cadena.compareTo("SENTENCIAS")==0){
            System.out.println("SENTENCIAS");
            
            if(nodo.hijos.size()==2){
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);    
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);    
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
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SENSI
            else if(nodo.hijos.get(0).cadena.compareTo("SENSI")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SENCOMPROBAR
            else if(nodo.hijos.get(0).cadena.compareTo("SENCOMPROBAR")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SENPARA
            else if(nodo.hijos.get(0).cadena.compareTo("SENPARA")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SENMIENTRAS
            else if(nodo.hijos.get(0).cadena.compareTo("SENMIENTRAS")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SENHACER
            else if(nodo.hijos.get(0).cadena.compareTo("SENHACER")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SENSALIR
            else if(nodo.hijos.get(0).cadena.compareTo("SENSALIR")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------RETO
            else if(nodo.hijos.get(0).cadena.compareTo("RETO")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SENCONTINUAR
            else if(nodo.hijos.get(0).cadena.compareTo("SENCONTINUAR")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------DIBUJAR_P
            else if(nodo.hijos.get(0).cadena.compareTo("DIBUJAR_P")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------DIBUJAR_OR
            else if(nodo.hijos.get(0).cadena.compareTo("DIBUJAR_OR")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }            
            //----------------DIBUJAR_S
            else if(nodo.hijos.get(0).cadena.compareTo("DIBUJAR_S")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------PRI
            else if(nodo.hijos.get(0).cadena.compareTo("PRI")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------ORDEN
             else if(nodo.hijos.get(0).cadena.compareTo("ORDEN")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            //----------------SUMARI
            else if(nodo.hijos.get(0).cadena.compareTo("SUMARI")==0){
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
        }
        //PRODUCCION CONTENIDO
        else if(nodo.cadena.compareTo("CONTENIDO")==0){
            System.out.println("CONTENIDO");
            if(nodo.hijos.size()==1){   //CONT
                Simbolo s = new Simbolo();
                s.lienzo = token.lienzo;
                ejecucion(nodo.hijos.get(0),LS,padre,s,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            else if(nodo.hijos.size()==2){//CONSERV CONT | VISI CONT
                if(nodo.hijos.get(0).cadena.compareTo("CONSERV")==0){
                    Simbolo s = new Simbolo();
                    s.conservar = "conservar";
                    s.lienzo = token.lienzo;
                    ejecucion(nodo.hijos.get(1),LS,padre,s,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //CONT
                }
                else if(nodo.hijos.get(0).cadena.compareTo("VISI")==0){
                    Simbolo s = new Simbolo();
                    s.visibilidad = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //VISI
                    s.lienzo = token.lienzo;
                    ejecucion(nodo.hijos.get(1),LS,padre,s,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //CONT
                }
            }
            else if(nodo.hijos.size()==3){//CONSERV VISI CONT
                Simbolo s = new Simbolo();
                s.conservar = "conservar";
                s.lienzo = token.lienzo;
                s.visibilidad = (String)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //VISI
                ejecucion(nodo.hijos.get(2),LS,padre,s,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //CONT
            }
        }
        //PRODUCCION CONT
        else if(nodo.cadena.compareTo("CONT")==0){
            System.out.println("CONT");
            
            if(nodo.hijos.get(0).cadena.compareTo("DECLARACION")==0){//DECLARACION
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }else if(nodo.hijos.get(0).cadena.compareTo("CONTENIDO3")==0){//CONTENIDO3
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
        }
        //PRODUCCION DECLARACION
        else if(nodo.cadena.compareTo("DECLARACION")==0){
            System.out.println("DECLARACION");
            token.tipo = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);   //TIPO
            ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);   //VARIABLE
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
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            else if(nodo.hijos.size()==2){ //NOMARREGLO DIMENSION
                
                ArrayList<String> listadim = new ArrayList<String>();
                ArrayList<String> listadim2 = new ArrayList<String>();
                listadim2 = (ArrayList)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,listadim,LDibujo,words,otronodo,nivel,para,ambito); //DIMENSION
                for(int i=0;i<listadim2.size();i++){
                    System.out.println("    dim "+i+"  "+listadim2.get(i));
                }
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,listadim2,LDibujo,words,otronodo,nivel,para,ambito); //NOMARREGLO                
            }
            else if(nodo.hijos.size()==3){ //NOMARREGLO DIMENSION ASIGNARREGLO
                
                ArrayList<String> listadim = new ArrayList<String>();
                ArrayList<String> listadim2 = new ArrayList<String>();
                ArrayList<String> word = new ArrayList<String>();
                ArrayList<String> word2 = new ArrayList<String>();
                
                listadim2 = (ArrayList)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,listadim,LDibujo,words,otronodo,nivel,para,ambito); //DIMENSION
                for(int i=0;i<listadim2.size();i++){
                    System.out.println("    dim "+i+"  "+listadim2.get(i));
                }
                NodoArbol auxnodo = new NodoArbol();
                auxnodo.ty= token.tipo;
                System.out.println("           ------------------ VARIABLES "+token.tipo);
                word2 = (ArrayList)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,word,LDibujo,words,auxnodo,nivel,para,ambito); //ASIGNARREGLO
                for(int i=0;i<word2.size();i++){
                    System.out.println("    contenido array "+i+"  "+word2.get(i));
                }
                
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,listadim2,LDibujo,word2,otronodo,nivel,para,ambito); //NOMARREGLO
                
            }
        }
        //PRODUCCION NOMARREGLO 
        else if(nodo.cadena.compareTo("NOMARREGLO")==0){
            System.out.println("NOMARREGLO");
            
            if(nodo.hijos.size()==1){//id
                token.id = nodo.hijos.get(0).cadena;
                String auxtipo =token.tipo;
                token.tipo = "arreglo_"+auxtipo;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                token.size = Dim.size();
                position =0;
                ArrayList<String> auxDim = new ArrayList<String>();
                for(int i=Dim.size()-1; i>=0;i--){
                    auxDim.add(Dim.get(i));
                }
                
//                System.out.println("Dim 1 "+auxDim.get(0) + " Dim 2 "+auxDim.get(1)+" Dim 3 "+auxDim.get(2));
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                
                crearArreglo(auxDim,0,s.arreglo);
                
                if(multiDimension(auxDim,words.size(),LE,s.id,s.fila,s.columna)==0){
                    insertAllArreglo(auxDim,0,s.arreglo,words);
                }
                token.tipo = auxtipo;
                return s;
                
            }
            else if(nodo.hijos.size()==2){//id NOMARR
                
                token.id = nodo.hijos.get(0).cadena;
                String auxtipo =token.tipo;
                token.tipo = "arreglo_"+auxtipo;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                token.size = Dim.size();
                position = 0;
                String auxidtoken = token.id;
//                System.out.println("Dim 1 "+auxDim.get(0) + " Dim 2 "+auxDim.get(1)+" Dim 3 "+auxDim.get(2));
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                
                ArrayList<String> auxDim = new ArrayList<String>();
                System.out.println("  -----------------------------  id NOMARR "+s.id);
                for(int i=Dim.size()-1; i>=0;i--){
                    auxDim.add(Dim.get(i));
                    System.out.println("  -----------------------------  dim "+i+" "+s.id+" "+Dim.get(i));
                }

                crearArreglo(auxDim,0,s.arreglo);
                System.out.println("    words.size "+words.size()+" auxwords.size "+words.size());
                //if(multiDimension(auxDim,words.size(),LE,s.id,s.fila,s.columna)==0){
                //    insertAllArreglo(auxDim,0,s.arreglo,words);
                // }

                token.tipo = auxtipo;
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //NOMARR
                //return token;
                return s;
               
            }
        }    
        //PRODUCCION NOMARR
        else if(nodo.cadena.compareTo("NOMARR")==0){
            System.out.println("NOMARR");
            
            if(nodo.hijos.size()==1){// id
                token.id = nodo.hijos.get(0).cadena;
                String auxtipo =token.tipo;
                token.tipo = "arreglo_"+auxtipo;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                token.size = Dim.size();
                position = 0;
                String auxidtoken = token.id;
                
//                System.out.println("Dim 1 "+auxDim.get(0) + " Dim 2 "+auxDim.get(1)+" Dim 3 "+auxDim.get(2));
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                
                
                ArrayList<String> auxDim = new ArrayList<String>();
                System.out.println("  -----------------------------  id "+s.id);
                for(int i=Dim.size()-1; i>=0;i--){
                    auxDim.add(Dim.get(i));
                    System.out.println("  -----------------------------  dim "+i+" "+s.id+" "+Dim.get(i));
                }

                crearArreglo(auxDim,0,s.arreglo);
                System.out.println("    words.size "+words.size());
                //if(multiDimension(auxDim,words.size(),LE,s.id,s.fila,s.columna)==0){
                //    insertAllArreglo(auxDim,0,s.arreglo,words);
                // }
                
                //return token;
                return s;
            }
            else if(nodo.hijos.size()==2){//id NOMARR
                token.id = nodo.hijos.get(0).cadena;
                String auxtipo =token.tipo;
                token.tipo = "arreglo_"+auxtipo;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                token.size = Dim.size();
                position = 0;
                String auxidtoken = token.id;
                
//                System.out.println("Dim 1 "+auxDim.get(0) + " Dim 2 "+auxDim.get(1)+" Dim 3 "+auxDim.get(2));
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                
                ArrayList<String> auxDim = new ArrayList<String>();
                System.out.println("  -----------------------------  id NOMARR "+s.id);
                for(int i=Dim.size()-1; i>=0;i--){
                    auxDim.add(Dim.get(i));
                    System.out.println("  -----------------------------  dim "+i+" "+s.id+" "+Dim.get(i));
                }
                crearArreglo(auxDim,0,s.arreglo);
                System.out.println("    words.size "+words.size());
                //if(multiDimension(auxDim,words.size(),LE,s.id,s.fila,s.columna)==0){
                //    insertAllArreglo(auxDim,0,s.arreglo,words);
                //}
                
                token.tipo = auxtipo;
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //NOMARR
                //System.out.println("    auxidtoken "+auxidtoken+" auxcadena "+aux.cadena);
                //return token;
                //return aux;
                return s;
            }
        }
        //PRODUCCION DIMENSION
        else if(nodo.cadena.compareTo("DIMENSION")==0){
            System.out.println("DIMENSION");
            if(nodo.hijos.size()==1){ //VALOR 
                NodoArbol aux =(NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALOR
                if(aux.ty.compareTo("entero")==0 || aux.ty.compareTo("doble")==0){
                    Dim.add(aux.cadena);
                    return Dim;
                }else{
                    NodeError e = new NodeError(aux.f,aux.c,"Error al asignar dimension","Valor de dimension no valido "+aux.cadena+" tipo  "+aux.ty);
                    LE.add(e);
                }

            }
            else if(nodo.hijos.size()==2){//VALOR DIMENSION
                NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALOR
                
                if(aux.ty.compareTo("entero")==0 || aux.ty.compareTo("doble")==0){
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//DIMENSION
                    Dim.add(aux.cadena);
                    return Dim;
                }else{
                    NodeError e = new NodeError(aux.f,aux.c,"Error al asignar dimension","Valor de dimension no valido "+aux.cadena+" tipo  "+aux.ty);
                    LE.add(e);
                }
            }
            
        }
        //PRODUCCION ASIGNARREGLO
        else if(nodo.cadena.compareTo("ASIGNARREGLO")==0){
            System.out.println("ASIGNARREGLO");
            ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALORARREGLO
            return Dim;
        }
        //PRODUCCION VALORARREGLO
        else if(nodo.cadena.compareTo("VALORARREGLO")==0){
            System.out.println("VALORARREGLO");
            ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALARR
            return Dim;
        }

        //PRODUCCION VALARR
        else if(nodo.cadena.compareTo("VALARR")==0){ //R2 | VALARR LVAL2?
            System.out.println("VALARR");
            if(nodo.hijos.size()==1){   //R2 | VALARR
                if(nodo.hijos.get(0).cadena.compareTo("R2")==0){
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//R2
                    return Dim;
                }
                else if(nodo.hijos.get(0).cadena.compareTo("VALARR")==0){
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALARR
                    return Dim;
                }
            }
            else if(nodo.hijos.size()==2){//VALARR LVAL2
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALARR
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//LVAL2
                return Dim;
            }
        }
        //PRODUCCION R2
        else if(nodo.cadena.compareTo("R2")==0){ //VALOR |VALOR LVAL
            System.out.println("R2");
            
            if(nodo.hijos.size()==1){//VALOR
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALOR
                if(ret.ty.compareTo(otronodo.ty)==0){
                    Dim.add(ret.cadena);
                    //typosumarizar=ret.ty;
                    return Dim;
                }else{
                    NodeError e = new NodeError(ret.f,ret.c,"Error al asignar valor al arreglo "+otronodo.ty,"Dato no valido "+ret.cadena+" tipo "+ret.ty);
                    LE.add(e);
                }
                
            }
            else if(nodo.hijos.size()==2){//VALOR LVAL
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALOR
                if(ret.ty.compareTo(otronodo.ty)==0){
                    Dim.add(ret.cadena);
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//LVAL
                    return Dim;
                }else{
                    NodeError e = new NodeError(ret.f,ret.c,"Error al asignar valor al arreglo "+otronodo.ty,"Dato no valido "+ret.cadena+" tipo "+ret.ty);
                    LE.add(e);
                }
                
            }
        }
        //PRODUCCION LVAL
        else if(nodo.cadena.compareTo("LVAL")==0){ //VALOR |VALOR LVAL
            System.out.println("LVAL");
            if(nodo.hijos.size()==1){//VALOR
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALOR
                if(ret.ty.compareTo(otronodo.ty)==0){
                    Dim.add(ret.cadena);
                    return Dim;
                }
                else{
                    NodeError e = new NodeError(ret.f,ret.c,"Error al asignar valor al arreglo","Dato no valido "+ret.cadena+" tipo "+ret.ty);
                    LE.add(e);
                }
            }
            else if(nodo.hijos.size()==2){//VALOR LVAL
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALOR
                if(ret.ty.compareTo(otronodo.ty)==0){
                    Dim.add(ret.cadena);
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//LVAL
                    return Dim;
                }
                else{
                    NodeError e = new NodeError(ret.f,ret.c,"Error al asignar valor al arreglo","Dato no valido "+ret.cadena+" tipo "+ret.ty);
                    LE.add(e);
                }
            }
        }
        //PRODUCCION LVAL2
        else if(nodo.cadena.compareTo("LVAL2")==0){ //VALARR |VALARR LVAL2
            System.out.println("LVAL2");
            if(nodo.hijos.size()==1){//VALARR
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALARR
                return Dim;
            }
            else if(nodo.hijos.size()==2){//VALOR LVAL
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALARR
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//LVAL2
                return Dim;
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
                    aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //ASIGN
                    token.valor = aux.cadena;   //pasar el valor del return al token
                    System.out.println("    id ASIGN - "+aux.cadena);
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);                  //añadir a la trabla de simbolos
                    //verficarTipo(s,aux.ty,LS,LE);
                    casteo(s,aux.ty,LS,LE,s.lienzo);
                }
                else if(nodo.hijos.get(1).cadena.compareTo("NOM")==0){//id NOM
                    token.id = nodo.hijos.get(0).cadena;
                    token.rol = "variable";
                    token.ambito = darAmbitio(padre);
                    token.fila = nodo.hijos.get(0).f;
                    token.columna = nodo.hijos.get(0).c;
                    String auxidtoken = token.id;
                    //NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo); //NOM
                    //token.valor = aux.cadena;
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);
                    NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //NOM
                    buscarRSimbolo(auxidtoken,aux.cadena,LS,s.lienzo,s.ambito);
                    System.out.println("    auxidtoken "+auxidtoken+" auxcadena "+aux.cadena);
                } 
            }
            else if(nodo.hijos.size()==3){ //id ASIGN NOM
                token.id = nodo.hijos.get(0).cadena;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                
                NodoArbol aux = new NodoArbol();
                aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //ASIGN
                token.valor = aux.cadena;   //pasar el valor del return al token
                
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);                  //añadir a la trabla de simbolos
                //verficarTipo(s,aux.ty,LS,LE);
                casteo(s,aux.ty,LS,LE,s.lienzo);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //NOM
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
                    aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //ASIGN
                    token.valor = aux.cadena;   //pasar el valor del return al token
                    System.out.println("    id ASIGN - "+aux.cadena);
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);                  //añadir a la trabla de simbolos
                    //verficarTipo(s,aux.ty,LS,LE);   
                    casteo(s,aux.ty,LS,LE,s.lienzo);
                    return aux;
                    //ejecucion(nodo.hijos.get(1),LS,padre,token,LE); //NOM
                }
                else if(nodo.hijos.get(1).cadena.compareTo("NOM")==0){// id NOM
                    token.id = nodo.hijos.get(0).cadena;
                    token.rol = "variable";
                    token.ambito = darAmbitio(padre);
                    token.fila = nodo.hijos.get(0).f;
                    token.columna = nodo.hijos.get(0).c;
                    String auxidtoken = token.id;
                    Simbolo s = new Simbolo();
                    igualarSimbolos(token,s);
                    verificarSimbolo(s,LS,LE);
                    LS.add(s);
                    //ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo);
                    NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //NOM
                    buscarRSimbolo(auxidtoken,aux.cadena,LS,s.lienzo,s.ambito);
                    System.out.println("    auxidtoken "+auxidtoken+" auxcadena "+aux.cadena);
                    return aux;
                } 
            }
            else if(nodo.hijos.size()==3){ //id ASIGN NOM
                token.id = nodo.hijos.get(0).cadena;
                token.rol = "variable";
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                
                NodoArbol aux = new NodoArbol();
                aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //ASIGN
                token.valor = aux.cadena;   //pasar el valor del return al token
                
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);                  //añadir a la trabla de simbolos
                //verficarTipo(s,aux.ty,LS,LE);
                casteo(s,aux.ty,LS,LE,s.lienzo);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //NOM
            }
        }
        //PRODUCCION ASIGN
        else if(nodo.cadena.compareTo("ASIGN")==0){
            System.out.println("ASIGN");
            if(nodo.hijos.size()==1){
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//VALOR
            }
        }
        //PRODUCCION VALOR
        else if(nodo.cadena.compareTo("VALOR")==0){
            System.out.println("VALOR");
            if(nodo.hijos.size()==1){
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//LOGICA
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
            return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//L
            //ACA ME QUEDE
            //TOMAR EN CUENTA QUE NO SE A ASIGNADO AUN VALOR Y QUE LA DECLARACION DE ARREGLOS AUN NO ESTA ECHA 
        }
        //PRODUCCION L
        else if(nodo.cadena.compareTo("L")==0){
            System.out.println("L"); 
            
            if(nodo.hijos.size()==1){ //M
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }else if(nodo.hijos.size()==2){ //M LP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//M
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//LP
                System.out.println("    RESLOGICA "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION LP
        else if(nodo.cadena.compareTo("LP")==0){
            System.out.println("LP"); 
            if(nodo.hijos.size()==2){ // or|nor|xor M
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//M
                NodoArbol resultado = logica(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO LOGICA "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){ //or|nor|xor M LP
                //aca debo de operar la relacion
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //M
                NodoArbol resultado = logica(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO LOGICA 2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //LP
                return ret;
            }
        }
        //PRODUCCION M
        else if(nodo.cadena.compareTo("M")==0){
            System.out.println("M");
            if(nodo.hijos.size()==1){ //N
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }else if(nodo.hijos.size()==2){ //N MP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//N
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//MP
                System.out.println("    RESLOGICA "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION MP
        else if(nodo.cadena.compareTo("MP")==0){
            System.out.println("MP"); 
            if(nodo.hijos.size()==2){ // and|nand N
                //aca debo operar la relacion
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol resultado = logica(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO LOGICIA "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){ //and|nand N MP
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //T
                NodoArbol resultado = logica(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO LOGICA 2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //EP
                return ret;
            }
        }
        //PRODUCCION N
        else if(nodo.cadena.compareTo("N")==0){
            System.out.println("N");
            if(nodo.hijos.size()==1){ //R
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
        }
        //PRODUCCION R
        else if(nodo.cadena.compareTo("R")==0){
            System.out.println("R");
            if(nodo.hijos.size()==1){//Z
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            else if(nodo.hijos.size()==2){ //not Z
                //NOT !
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol resultado = logica(operador1,operador1,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO NOT R "+operador1.cadena+" "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION Z
        else if(nodo.cadena.compareTo("Z")==0){
            System.out.println("Z");
            if(nodo.hijos.size()==1){
                if(nodo.hijos.get(0).cadena.compareTo("RELACIONAL")==0){ //RELACIONAL
                    return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                }
                else if(nodo.hijos.get(0).cadena.compareTo("LOGICA")==0){//LOGICA
                    return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                }
            }
        }
        //PRODUCCION RELACIONAL
        else if(nodo.cadena.compareTo("RELACIONAL")==0){
            System.out.println("RELACIONAL");
            if(nodo.hijos.size()==1){// A
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
        }
        //PRODUCCION A
        else if(nodo.cadena.compareTo("A")==0){
            System.out.println("A");
            if(nodo.hijos.size()==1){// EXP
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            if(nodo.hijos.size()==2){// EXP AP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//EXP
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//AP
                System.out.println("    RESREL "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION AP
        else if(nodo.cadena.compareTo("AP")==0){
            System.out.println("AP");
            if(nodo.hijos.size()==2){// == EXP
                //aca debo mandar a comparar ambos operadores               
                NodoArbol operador2 = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol resultado = relacional(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO AP "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION EXP
        else if(nodo.cadena.compareTo("EXP")==0){
            System.out.println("EXP");

            if(nodo.hijos.size()==1){// E
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            else if(nodo.hijos.size()==2){ //Nulo E
                //aca debo mandar a comparar al operador
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol resultado = relacional(operador1,operador1,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO Nulo E "+operador1.cadena+" "+resultado.cadena);
                return resultado;
            }
            
        }
        //PRODUCCION E
        else if(nodo.cadena.compareTo("E")==0){
            System.out.println("E ");
            
            if(nodo.hijos.size()==1){// T
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            else if(nodo.hijos.size()==2){//T EP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//T
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//EP
                System.out.println("    REES "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION EP
        else if(nodo.cadena.compareTo("EP")==0){
            System.out.println("EP");
            if(nodo.hijos.size()==2){// +/- T
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO EP "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){// +/- T EP
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //T
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO EP2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //EP
                return ret;
            }
        
        }
        //PRODUCCION T
        else if(nodo.cadena.compareTo("T")==0){                       
            System.out.println("T");
            if(nodo.hijos.size()==1){// F
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            else if(nodo.hijos.size()==2){//F TP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//F
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//TP
                System.out.println("    REES "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION TP
        else if(nodo.cadena.compareTo("TP")==0){
            System.out.println("TP");
            if(nodo.hijos.size()==2){// */div F
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO EP "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){// */div F TP
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //T
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO EP2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //EP
                return ret;
            }
        
        }
        //PRODUCCION F
        else if(nodo.cadena.compareTo("F")==0){                       
            System.out.println("F");
            if(nodo.hijos.size()==1){// G
                return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            }
            else if(nodo.hijos.size()==2){//G FP
                NodoArbol operador1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//G
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,operador1,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//FP
                System.out.println("    REES "+resultado.cadena);
                return resultado;
            }
        }
        //PRODUCCION FP
        else if(nodo.cadena.compareTo("FP")==0){
            System.out.println("FP");
            if(nodo.hijos.size()==2){// ^ G
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);
                System.out.println(" RESULTADO EP "+padre.cadena+" "+operador2.cadena+" "+resultado.cadena);
                return resultado;
            }
            else if(nodo.hijos.size()==3){// ^ G FP
                NodoArbol operador2=(NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //G
                NodoArbol resultado = operar(padre,operador2,nodo.hijos.get(0),LS,LE);  
                System.out.println(" RESULTADO EP2 "+resultado.cadena);
                NodoArbol ret = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,resultado,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //FP
                return ret;
            }
        
        }
        //PRODUCCION G
        else if(nodo.cadena.compareTo("G")==0){                       
            System.out.println("G");
            if(nodo.hijos.size()==1){ //entero | doble | boolean | caracter | cadena | id | ORDEN | SUMARI
                System.out.println("    node "+nodo.hijos.get(0).cadena + "  typo "+nodo.hijos.get(0).ty);
                if(nodo.hijos.get(0).cadena.compareTo("SUMARI")==0){ // SUMARI
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    System.out.println("    sumarizar xd"+sumarizar);
                    NodoArbol aux = new NodoArbol();
                    aux.cadena = sumarizar;
                    aux.ty = "cadena";                      
                    return aux;
                }else if(nodo.hijos.get(0).cadena.compareTo("ORDEN")==0){ //ORDEN
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    System.out.println("    ordenar xd"+ordenar);
                    NodoArbol aux = new NodoArbol();
                    aux.cadena = ordenar;
                    aux.ty = "entero";
                    return aux;
                }else if(nodo.hijos.get(0).ty.compareTo("entero")==0 || nodo.hijos.get(0).ty.compareTo("doble")==0 || nodo.hijos.get(0).ty.compareTo("caracter")==0 || nodo.hijos.get(0).ty.compareTo("cadena")==0 || nodo.hijos.get(0).ty.compareTo("boolean")==0){    
                        System.out.println("    G return"+nodo.hijos.get(0).cadena);
                        NodoArbol aux = new NodoArbol();
                        aux = nodo.hijos.get(0);
                        if(nodo.hijos.get(0).ty.compareTo("boolean")==0){
                            if(nodo.hijos.get(0).cadena.compareTo("verdadero")==0 || nodo.hijos.get(0).cadena.compareTo("1")==0){
                                aux.cadena = "true";
                            }else if(nodo.hijos.get(0).cadena.compareTo("falso")==0 || nodo.hijos.get(0).cadena.compareTo("0")==0){
                                aux.cadena = "false";
                            }
                        }    
                        System.out.println("    G return........"+nodo.hijos.get(0).cadena);
                        //return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                        return aux;
                }
                else{ //estoy retornando el valor que el id tiene asignado
                    NodoArbol aux = new NodoArbol();
                    aux = nodo.hijos.get(0);
                    NodoArbol aux2 = buscarSimbolo(aux,LS,token.lienzo);
                    if(aux2.cadena!=null){
                        System.out.println("    G ID "+nodo.hijos.get(0).cadena+"  aux2  "+aux2.cadena+" "+aux2.ty);
                    }
                    else{
                        NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error id no declarado","No se ha declarado id "+nodo.hijos.get(0).cadena);
                        LE.add(e);
                    }
                    //return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                    return aux2;
                }

            }
            else if(nodo.hijos.size()==2){ //id DIMENSION |  id PARAMETROS  |  id CONT2
                System.out.println("    G return D|P|C"+nodo.hijos.get(0).cadena);
                NodoArbol aux = new NodoArbol();
                aux = nodo.hijos.get(0);
                //aca tendriamos que retornar el arreglo o parametro con el valor que corresponde
                NodoArbol aux2 = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,aux,nivel,para,ambito);//HH
                
                return aux2;
            }
        }
        //PRODUCCION HH
        else if(nodo.cadena.compareTo("HH")==0){
            System.out.println("HH");
            if(nodo.hijos.size()==0){ // <parenti> PARAMETROS? <parentd>
                //VIENE UN METODO SIN PARAMETROS
            }
            else if(nodo.hijos.size()==1){
                if(nodo.hijos.get(0).cadena.compareTo("DIMENSION")==0){ //DIMENSION
                    ArrayList<String> listadim = new ArrayList<String>();
                    ArrayList<String> listadim2 = new ArrayList<String>();
                    ArrayList<String> listadim3 = new ArrayList<String>();
                    listadim2 = (ArrayList)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,listadim,LDibujo,words,otronodo,nivel,para,ambito);
                    for(int i=listadim2.size()-1;i>=0;i--){
                        listadim3.add(listadim2.get(i));
                        System.out.println("    HH listadim2 "+listadim2.get(i)+" token.lienzo "+token.lienzo);
                    }
                    
                        Simbolo s = buscarSimboloArr(otronodo,LS,token.lienzo,LE);
                        if(listadim3.size()==s.size){
                            String valArr = getvalArreglo(listadim3,0,s.arreglo,"");
                            System.out.println("    getvalArreglo "+valArr+" s"+s.valor);
                            NodoArbol aux2 = new NodoArbol();
                            aux2.cadena = valArr;
                            String nuevoTy = convertTipoArreglo(s.tipo);
                            aux2.ty = nuevoTy;
                            System.out.println("    valArr "+valArr+" s.tipo "+s.tipo+" nuevoTy "+nuevoTy);
                            return aux2;
                        }else{
                            NodeError e = new NodeError(s.fila,s.columna,"Error en dimension del arreglo ","Las dimensiones de "+s.id+" no coinciden "+s.size+" dim ingresadas "+listadim3.size());
                            LE.add(e);
                        }
                                   
                }
                else if(nodo.hijos.get(0).cadena.compareTo("PARAMETROS")==0){//PARAMETROS
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                }
                else if(nodo.hijos.get(0).cadena.compareTo("CONT2")==0){//CONT2
                    
                    //ERROR
                    if(nodo.hijos.get(0).hijos.get(0).cadena.compareTo("+=")==0 || nodo.hijos.get(0).hijos.get(0).cadena.compareTo("-=")==0){
                        if(padre.cadena.compareTo("FOR")==0 || padre.cadena.charAt(0)=='F'){
                            NodoArbol aux = buscarSimbolo(otronodo,LS,token.lienzo);
                            System.out.println("    += Y -= aux "+aux.cadena+" "+aux.ty);
                            NodoArbol auxop = new NodoArbol();
                            auxop.cadena="0";
                            auxop.ty = aux.ty;
                            NodoArbol aux2 = operarSimplificado(aux,auxop,nodo.hijos.get(0).hijos.get(0),LS,LE);
                            //NodoArbol aux2 =(NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel);
                            return aux2;
                        }
                        else{
                            NodeError e = new NodeError(otronodo.f,otronodo.c,"Error al operar ","No se pueden operar += -=");
                            LE.add(e);
                        }
                        
                    }else{
                    // ++ Y --
                        NodoArbol aux = buscarSimbolo(otronodo,LS,token.lienzo);
                        System.out.println("    ++ Y -- aux "+aux.cadena+" "+aux.ty);
                        NodoArbol aux2 = operarSimplificado(aux,aux,nodo.hijos.get(0).hijos.get(0),LS,LE);
                        return aux2;
                        
                    }
                }
            }
        }
        //SENTENCIAS DE CONTROL
        //SENSI
        else if(nodo.cadena.compareTo("SENSI")==0){ // SI LOGICA (SEN SENTENCIAS)? SINO?
            System.out.println("SENSI "+nodo.hijos.size());
            if(nodo.hijos.size()==2){   //SI LOGICA
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                if(resultado.cadena.compareTo("true")==0){
                    //No se hace nada porque no hay sentencia
                }
            }
            else if(nodo.hijos.size()==3){
                if(nodo.hijos.get(2).cadena.compareTo("SENSINO")==0){ //SI LOGICA SINO
                    NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    if(resultado.cadena.compareTo("true")==0){
                    
                    }
                    else{
                        ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SINO
                    }
                }
            }
            else if(nodo.hijos.size()==4){ //SI LOGICA SEN SENTENCIAS
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                if(resultado.cadena.compareTo("true")==0){
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SEN
                    ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SENTENCIAS
                }
            }
            else if(nodo.hijos.size()==5){ //SI LOGICA SEN SENTENCIAS SINO
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                if(resultado.cadena.compareTo("true")==0){
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SEN
                    ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SENTENCIAS
                }else{
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(4),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SINO
                }
            }
        }
        //PRODUCCION SENSINO
        else if(nodo.cadena.compareTo("SENSINO")==0){ //SINO (SEN SENTENCIAS)?
            System.out.println("SENSINO");
            if(nodo.hijos.size()==1){//SINO
                System.out.println("    SINO SIZE 1");
            }
            else if(nodo.hijos.size()==3){//SINO SEN SENTENCIAS
                //NodoArbol auxnodo = new NodoArbol();
                //auxnodo.cadena = nodo.hijos.get(0).cadena;
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SEN
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//SENTENCIAS
                System.out.println("    SINO SIZE 2");
            }
        }
        //PRODUCCION SENCOMPROBAR
        else if(nodo.cadena.compareTo("SENCOMPROBAR")==0){ //COMPROBAR VALOR CUERPO? DEFE?
            System.out.println("SENCOMPROBAR");
            if(nodo.hijos.size()==2){ //COMPROBAR VALOR
                
            }
            else if(nodo.hijos.size()==3){ //COMPROBAR VALOR CUERPO    |   COMPROBAR VALOR DEFE
                if(nodo.hijos.get(2).cadena.compareTo("CUERPO")==0){//VALOR CUERPO
                    NodoArbol valor= (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //VALOR
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,valor,nivel,para,ambito); //CUERPO
                    
                }else if(nodo.hijos.get(2).cadena.compareTo("DEFE")==0){//VALOR DEFECTO
                    NodoArbol valor= (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //VALOR
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,valor,nivel,para,ambito); //DEFE
                }
                
            }
            else if(nodo.hijos.size()==4){ //COMPROBAR VALOR CUERPO DEFE
                    NodoArbol valor= (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //VALOR
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,valor,nivel,para,ambito); //CUERPO
                    System.out.println("flag -------->"+banderadef);
                    if(banderadef==0){

                        ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,valor,nivel,para,ambito); //DEFE
                    }
                    banderadef = 0;
                    
            }
        }
        //PRODUCCION CUERPO
        else if(nodo.cadena.compareTo("CUERPO")==0){ //CASO VALOR (SEN|SENTENCIA)S? CUERPO?
            System.out.println("CUERPO");
            if(nodo.hijos.size()==2){//CASO VALOR 
                NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                System.out.println("    CUERPO------"+aux.cadena+"  "+otronodo.cadena);
                if(aux.cadena.compareTo(otronodo.cadena)==0 && (nivel!=4 && nivel!=5)){
                    banderadef = 1;
                }
                
            }
            else if(nodo.hijos.size()==3){//CASO VALOR CUERPO
                NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                System.out.println("    CUERPO------"+aux.cadena+"  "+otronodo.cadena);
                if(aux.cadena.compareTo(otronodo.cadena)==0 && (nivel!=4 && nivel!=5)){
                    banderadef = 1;
                    //ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel);//CUERPO
                }
                else if(aux.cadena.compareTo(otronodo.cadena)!=0 && (nivel!=4 && nivel!=5)){
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);//CUERPO
                    
                }
            }
            else if(nodo.hijos.size()==4){//CASO VALOR SEN SENTENCIA
                NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                System.out.println("    CUERPO------"+aux.cadena+"  "+otronodo.cadena);
                if(aux.cadena.compareTo(otronodo.cadena)==0 && (nivel!=4 && nivel!=5)){
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SEN
                    ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SENTENCIAS
                    banderadef = 1;
                }
                
            }
            else if(nodo.hijos.size()==5){//CASO VALOR SEN SENTENCIA CUERPO
                NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                System.out.println("    CUERPO------"+aux.cadena+"  "+otronodo.cadena);
                if(aux.cadena.compareTo(otronodo.cadena)==0 && (nivel!=4 && nivel!=5)){
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SEN
                    ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SENTENCIAS       
                    //ejecucion(nodo.hijos.get(4),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel); //CUERPO
                    banderadef = 1;
                }
                else if(aux.cadena.compareTo(otronodo.cadena)!=0 && (nivel!=4 && nivel!=5)){
                    ejecucion(nodo.hijos.get(4),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //CUERPO
                    
                }
            }
        }
        
        //PRODUCCION DEFE
        else if(nodo.cadena.compareTo("DEFE")==0){ //DEFE (SEN|SENTENCIAS)?
            System.out.println("DEFE");
            if(nodo.hijos.size()==1){//DEFE
            
            }
            else if(nodo.hijos.size()==3){//DEFE SEN SENTENCIAS
                System.out.println("    DEFE------");
                if((nivel!=4 && nivel!=5)){
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SEN
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SENTENCIAS
                }
            }
        }
        //PRODUCCION SENSALIR
        else if(nodo.cadena.compareTo("SENSALIR")==0){ //SENSALIR
            System.out.println("SENSALIR");
            String ambito1 = darAmbitio(padre);
            System.out.println("            AMBITO DE SEN SALIR "+ambito+" "+padre.cadena);
            /*if(ambito1.compareTo("para")==0 || ambito1.compareTo("mientras")==0 || ambito1.compareTo("hacer-mientras")==0 || ambito1.compareTo("si")==0 || ambito1.compareTo("sino")==0 || ambito1.compareTo("comprobar")==0){
            
            }
            else{
                NodeError e = new NodeError(0,0,"Error en Ambito ","Ambito de sentencia salir incorrecto "+ambito1);
                LE.add(e);
            }
            */
        }
        //PRODUCCION SENCONTINUAR
        else if(nodo.cadena.compareTo("SENCONTINUAR")==0){ //SENCONTINUAR
            System.out.println("SENCONTINUAR");
            String ambito1 = darAmbitio(padre);
            System.out.println("            AMBITO DE SEN CONTINUAR "+ambito+" "+padre.cadena);
            
            /*if(ambito1.compareTo("para")==0 || ambito1.compareTo("mientras")==0 || ambito1.compareTo("hacer-mientras")==0){
            
            }
            else{
                NodeError e = new NodeError(0,0,"Error en Ambito ","Ambito de sentencia continuar incorrecto "+ambito1);
                LE.add(e);
            }*/
        }
        //PRODUCCION SENPARA
        else if(nodo.cadena.compareTo("SENPARA")==0){ //SENPARA INIPARA LOGICA G (FINPARA)? (SEN SENTENCIAS)?
            System.out.println("SENPARA");
            //NodoArbol auxnodo = new NodoArbol();
            //auxnodo.cadena = nodo.hijos.get(0).cadena;
            
            NodoArbol auxnodo2 = new NodoArbol();
            auxnodo2.cadena = "FOR";    
            if(nodo.hijos.size()==4){   //SENPARA INIPARA LOGICA G 
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);    //INIPARA
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);    //LOGICA
                ejecucion(nodo.hijos.get(3),LS,auxnodo2,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);    //G
                System.out.println("    FOR LOGICA1------"+resultado.cadena);
            }
            else if(nodo.hijos.size()==5){  //SENPARA INIPARA LOGICA G FINPARA
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //INIPAR
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //LOGICA
                ejecucion(nodo.hijos.get(3),LS,auxnodo2,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //G
                ejecucion(nodo.hijos.get(4),LS,padre,token,LE,Dim,LDibujo,words,nodo.hijos.get(3),nivel,para,ambito);  //FINPARA
                System.out.println("    FOR LOGICA2------"+resultado.cadena);
            }            
            
            else if(nodo.hijos.size()==6){  //SENPARA INIPARA LOGICA G SEN SENTENCIAS
                if(nodo.para==0){
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //INIPARA
                    nodo.para=1;
                    System.out.println("    PARA... INICIALIZAR 1.....");
                }
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //LOGICA
                System.out.println("    FOR LOGICA3------"+resultado.cadena);
                
                if(resultado.cadena.compareTo("true")==0){
                    ejecucion(nodo.hijos.get(4),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //SEN
                    ejecucion(nodo.hijos.get(5),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //SENTENCIAS
                    
                    if(nodo.hijos.get(3).hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("++")==0 || nodo.hijos.get(3).hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("--")==0){
                        System.out.println("    ++      --"+nodo.hijos.get(3).hijos.get(1).hijos.get(0).cadena);
                        NodoArbol n = (NodoArbol)ejecucion(nodo.hijos.get(3),LS,auxnodo2,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //G
                        String auxambit = darAmbitio(padre);
                        buscarRSimbolo(nodo.hijos.get(3).hijos.get(0).cadena,n.cadena,LS,token.lienzo,auxambit);
                        ejecucion(nodo,LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //SENPARA
                    }
                    else{
                        System.out.println("    +=   ........   -="+nodo.hijos.get(3).hijos.get(1).hijos.get(0).cadena);
                        NodeError e = new NodeError(nodo.hijos.get(3).hijos.get(0).f,nodo.hijos.get(3).hijos.get(0).c,"Error al asignar ","Operador incorrecto += -=");
                        LE.add(e);    
                    }
                    
                    
                }
                else{
                    nodo.para=0;
                }
            }
            else if(nodo.hijos.size()==7){  //SENPARA INIPARA LOGICA G FINPARA SEN SENTENCIAS 
                if(nodo.para==0){
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //INIPARA
                    nodo.para=1;
                    System.out.println("    PARA... INICIALIZAR 2.....");
                }
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //LOGICA
                System.out.println("    FOR LOGICA4------"+resultado.cadena);
                
                if(resultado.cadena.compareTo("true")==0){
                    ejecucion(nodo.hijos.get(5),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //SEN
                    ejecucion(nodo.hijos.get(6),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //SENTENCIAS 
                    NodoArbol ope2 = (NodoArbol)ejecucion(nodo.hijos.get(4),LS,padre,token,LE,Dim,LDibujo,words,nodo.hijos.get(3),nivel,para,ambito);  //FINPARA
                    NodoArbol n = (NodoArbol)ejecucion(nodo.hijos.get(3),LS,auxnodo2,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //G
                   
                    if(nodo.hijos.get(3).hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("+=")==0 || nodo.hijos.get(3).hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("-=")==0){
                        if(nodo.hijos.get(4).hijos.size()==1){ //valor
                            System.out.println("     -------------------- ope "+nodo.hijos.get(3).hijos.get(1).hijos.get(0).hijos.get(0));
                            //NodoArbol res1 = operarSimplificado(n,n,nodo.hijos.get(3).hijos.get(1),LS,LE);
                            NodoArbol res2 = operarSimplificado(n,ope2,nodo.hijos.get(3).hijos.get(1).hijos.get(0).hijos.get(0),LS,LE);
                            System.out.println("    -----------------res "+res2.cadena+" "+n.cadena+" "+ope2.cadena);
                            String auxambit = darAmbitio(padre);
                            buscarRSimbolo(nodo.hijos.get(3).hijos.get(0).cadena,res2.cadena,LS,token.lienzo,auxambit);
                            ejecucion(nodo,LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //SENPARA
                        }else if(nodo.hijos.get(4).hijos.size()==2){ // igual valor
                            NodeError e = new NodeError(nodo.hijos.get(3).hijos.get(0).f,nodo.hijos.get(3).hijos.get(0).c,"Error al asignar ","Operador incorrecto = ");
                            LE.add(e);
                        }

                    }else{
                        NodeError e = new NodeError(nodo.hijos.get(3).hijos.get(0).f,nodo.hijos.get(3).hijos.get(0).c,"Error al asignar ","Operador incorrecto ++ --");
                        LE.add(e);
                    }

                }
                else{
                    nodo.para=0;
                }
            }
        }
        
        //PRODUCCION INIPARA
        else if(nodo.cadena.compareTo("INIPARA")==0){ //DECLARACION | G (ASIGN)? | TIPO G ASIGN
            System.out.println("INIPARA");
            if(nodo.hijos.size()==1){
                if(nodo.hijos.get(0).cadena.compareTo("DECLARACION")==0){//DECLARACION
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //DECLARACION
                }
                else if(nodo.hijos.get(0).cadena.compareTo("G")==0){//G
                    NodoArbol n = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //G
                    //buscarRSimbolo(nodo.hijos.get(0).cadena,n.cadena,LS,token.lienzo);
                }
            }
            else if(nodo.hijos.size()==2){//G ASIGN
                    ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //G
                    NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);  //ASIGN
                    if(nodo.hijos.get(0).hijos.get(0).ty.compareTo("id")==0){
                        if(nodo.hijos.get(0).hijos.size()==1){
                            String auxambit = darAmbitio(padre);
                            buscarRSimbolo(nodo.hijos.get(0).hijos.get(0).cadena,aux.cadena,LS,token.lienzo,auxambit);
                        }
                        else{
                            NodeError e = new NodeError(nodo.hijos.get(0).hijos.get(0).f,nodo.hijos.get(0).hijos.get(0).c,"Error al asignar ","No se puede utilizar operador += -=");
                            LE.add(e);
                        }    
                    }
                    else{
                        NodeError e = new NodeError(nodo.hijos.get(0).hijos.get(0).f,nodo.hijos.get(0).hijos.get(0).c,"Error al asignar ","No se puede asignar valor a variable tipo "+nodo.hijos.get(0).hijos.get(0).ty);
                        LE.add(e);
                    }
            }
            else if(nodo.hijos.size()==3){//TIPO G ASIGN
                    NodeError e = new NodeError(nodo.hijos.get(1).hijos.get(0).f,nodo.hijos.get(1).hijos.get(0).c,"Error al asignar ","Sentencia no valida "+nodo.hijos.get(0).cadena);
                    LE.add(e);
                    //ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel);  //TIPO
                    //ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel);  //G
                    //ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel);  //ASIGN
            
            }
            
        }
       
         //PRODUCCION FINPARA
        else if(nodo.cadena.compareTo("FINPARA")==0){ //igual VALOR | VALOR
            System.out.println("FINPARA");
            if(nodo.hijos.size()==1){   //VALOR
                NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                //operarSimplificado(aux2,aux,);
                return aux;
            }
            else if(nodo.hijos.size()==2){ // IGUAL VALOR
                NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                return aux;
            }
        }
        
        
        //PRODUCCION SENMIENTRAS
        else if(nodo.cadena.compareTo("SENMIENTRAS")==0){ //MIENTRAS LOGICA (SEN SENTENCIAS)?
            System.out.println("SENMIENTRAS");
            if(nodo.hijos.size()==2){   //MIENTRAS LOGICA
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                boolean bandera = false;
                if(resultado.cadena.compareTo("true")==0){
                    
                    bandera = true;
                }
                System.out.println("    Entro al Mientas Logica");
            }
            else if(nodo.hijos.size()==4){  //MIENTRAS LOGICA SEN SENTENCIAS
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                if(resultado.cadena.compareTo("true")==0){
                    System.out.println("    Entro al Mientras Logica Sentencias");
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SEN
                    ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SENTENCIAS
                    ejecucion(nodo,LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                }
            }
        }
        //PRODUCCION SENHACER
        else if(nodo.cadena.compareTo("SENHACER")==0){ //HACER (SEN SENTENCIAS)? LOGICA
            System.out.println("SENHACER");
            if(nodo.hijos.size()==2){   //HACER LOGICA
            
            }
            else if(nodo.hijos.size()==4){ //HACER SEN SENTENCIAS LOGICA
                NodoArbol resultado = (NodoArbol)ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                if(nodo.hacer==0){
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SEN
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SENTENCIAS
                    nodo.hacer=1;
                }
                
                if(resultado.cadena.compareTo("true")==0){
                    //NodoArbol auxnodo = new NodoArbol();
                    //auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SEN
                    ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //SENTENCIAS
                    ejecucion(nodo,LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                }else{
                    nodo.hacer=0;
                }
            }
        }    
        

        //PRODUCCION PRI
        else if(nodo.cadena.compareTo("PRI")==0){   //(SEN SENTENCIAS)?
            System.out.println("PRI");
            principal = principal + 1;
            if(principal <= 1){
                if(nodo.hijos.size()==0){
                //NO TIENE HIJO SENTENCIAS

                }
                else if(nodo.hijos.size()==2){//SEN SENTECIAS
                    NodoArbol auxnodo = new NodoArbol();
                    auxnodo.cadena = nodo.hijos.get(0).cadena;
                    ejecucion(nodo.hijos.get(0),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,1,para,ambito); //SEN
                    ejecucion(nodo.hijos.get(1),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,1,para,ambito); //SENTENCIAS
                }
            }else{
                NodeError e = new NodeError(-1,-1,"Error en metodo principal","Existen mas de 1 metodo principal  ");
                LE.add(e);
            }
            
        }
        //PRODUCCION RETO
        else if(nodo.cadena.compareTo("RETO")==0){
            System.out.println("RETO");
            System.out.println("        lienzo "+token.lienzo+"     padre "+padre.cadena);
            System.out.println("        TY RETO-------------------- "+m.tipo+"   "+ m.root);
            if(m.tipo.compareTo("void")==0){
                NodeError e = new NodeError(m.fila,m.columna,"Error al retornar","El metodo es de tipo void  en "+m.lienzo);
                LE.add(e);
            }
            else{
                NodoArbol res = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,1,para,ambito); 
                //kayadesu
                //retornar dato;
                if(res.ty.compareTo(m.tipo)==0){
                    Sretorno = res;
                }else{
                    NodeError e = new NodeError(m.fila,m.columna,"Error al retornar","El metodo es de tipo "+m.tipo+"  retorno  "+res.ty+"  lienzo "+m.lienzo);
                    LE.add(e);
                }
                
            }
        }    
        
        //PRODUCCION CONTENIDO3
        else if(nodo.cadena.compareTo("CONTENIDO3")==0){
            System.out.println("CONTENIDO3");
            //(TIPOMETODO(nodo))? <id> (DIMENSION(nodo))? METODO(nodo)  
            
            if(nodo.hijos.size()==2){ //id METODO
                if(nodo.hijos.get(1).hijos.get(0).cadena.compareTo("VALOR")==0){ //ASIGNACION DE VALOR A VARIABLES
                    NodoArbol valor = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //METODO
                    String auxambit = darAmbitio(padre);
                    buscarRSimbolo(nodo.hijos.get(0).cadena,valor.cadena,LS,token.lienzo,auxambit);
                    Simbolo sim = buscarRetornarSimbolo(nodo.hijos.get(0).cadena,LS,token.lienzo);
                    System.out.println("    Valor asignado-------- "+valor.cadena+valor.ty+" a "+sim.valor+sim.tipo);
                    System.out.println("    nodo.hijos.get(0)-------- "+nodo.hijos.get(0).cadena);
                    casteo(sim,valor.ty,LS,LE,token.lienzo);
                    buscarRSimbolo(nodo.hijos.get(0).cadena,sim.valor,LS,token.lienzo,auxambit);
                  //casteo(s,aux.ty,LS,LE,s.lienzo);
                    
                }else if(nodo.hijos.get(1).hijos.get(0).cadena.compareTo("CONT2")==0){//CONT 2
                    //SOLO PUEDE VENIR ++ Y --
                    //ERROR
                    System.out.println("    CONT 2 "+nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena);
                    if(nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("++")==0 || nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("--")==0){
                        NodoArbol aux = buscarSimbolo(nodo.hijos.get(0),LS,token.lienzo);
                        System.out.println("    ++ Y -- aux "+aux.cadena+" "+aux.ty);
                         if(nodo.hijos.get(1).hijos.size()==1){
                            NodoArbol aux2 = operarSimplificado(aux,aux,nodo.hijos.get(1).hijos.get(0).hijos.get(0),LS,LE);
                            System.out.println("    aux2 "+aux2.cadena+"  "+aux2.ty);
                            String auxambit = darAmbitio(padre);
                            buscarRSimbolo(nodo.hijos.get(0).cadena,aux2.cadena,LS,token.lienzo,auxambit);
                         }else{
                            NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error al operar ","Operador "+nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena);
                            LE.add(e);
                         }
                        
                    }
                    else if(nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("+=")==0 || nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("-=")==0){
                        NodoArbol aux = buscarSimbolo(nodo.hijos.get(0),LS,token.lienzo);
                        System.out.println("    += Y -= aux "+aux.cadena+" "+aux.ty);
                        if(nodo.hijos.get(1).hijos.size()==2){
                            NodoArbol aux3 = (NodoArbol)ejecucion(nodo.hijos.get(1).hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //G
                        //    NodoArbol aux2 = operarSimplificado(aux,aux,nodo.hijos.get(1).hijos.get(0).hijos.get(0),LS,LE);
                            NodoArbol aux4 = operarSimplificado(aux,aux3,nodo.hijos.get(1).hijos.get(0).hijos.get(0),LS,LE);
                            String auxambit = darAmbitio(padre);
                            System.out.println("    aux3 "+aux3.cadena+"  "+aux3.ty+"    aux4 "+aux4.cadena+"  "+aux4.ty);
                            buscarRSimbolo(nodo.hijos.get(0).cadena,aux4.cadena,LS,token.lienzo,auxambit);
                        }else{
                            NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error al operar ","Operador += -= falta valor");
                            LE.add(e);
                        }
                        
                    }else{
                    // ++ Y --
                        NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error al operar ","No se pueden operar += -=");
                        LE.add(e);
                    } 
                }else{ // PARAMETROS
                    
                    if((nodo.hijos.get(1).hijos.size()==1 && nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena.compareTo("dolar")==0)|| (nodo.hijos.get(1).hijos.size()==2 &&  nodo.hijos.get(1).hijos.get(1).hijos.get(0).cadena.compareTo("dolar")==0)){//LLAMADA A METODO
                        System.out.println("    llamando a metodo o funcion "+nodo.hijos.get(0).cadena);
                        if(nodo.hijos.get(1).hijos.get(0).cadena.compareTo("PARAMETROS")==0){   //LLAMADA CON PARAMETROS
                            ArrayList<ListaParametro> listap2 = new ArrayList<ListaParametro>();
                            ArrayList<ListaParametro> listap = (ArrayList)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,listap2,ambito); //METODO
                            invocar=0;
                            System.out.println("    metodo o funcion con parametros...."+listap.size());
                            for(int i=0;i<listap.size();i++){
                                System.out.println("                parametro "+i+" "+listap.get(i).parametro+" "+listap.get(i).valorparametro);
                            }
                            llenarListaH(token.lienzo,LS,nodo.hijos.get(0).cadena,listap);
                            System.out.println("    NODE M.............. "+m.root+m.root+" "+m.root.cadena+" "+m.root.hijos.size()+"  id "+nodo.hijos.get(0).cadena+" invocar "+invocar+token.lienzo);
                            if(invocar==1){
                                token.lienzo = m.lienzo;
                                ejecucion(m.root,LS,nodo.hijos.get(0),token,LE,Dim,LDibujo,words,otronodo,1,para,ambito);
                            }
                            //ejecucion(m.root,LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para);  //EJECUCION DEL METODO
                        }else if(nodo.hijos.get(1).hijos.get(0).cadena.compareTo("METOD")==0){  //LLAMADA SIN PARAMETROS
                            ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //METODO
                            System.out.println("    metodo o funcion sin parametros....");
                            ArrayList<ListaParametro> listap = new ArrayList<ListaParametro>();
                            invocar=0;
                            llenarListaH(token.lienzo,LS,nodo.hijos.get(0).cadena,listap);
                            System.out.println("    NODE M sin parametros.............. "+m.root+" "+m.root.cadena+" "+m.root.hijos.size()+"  id "+nodo.hijos.get(0).cadena+" invocar "+invocar+token.lienzo);
                            if(invocar==1){
                                token.lienzo = m.lienzo;
                                ejecucion(m.root,LS,nodo.hijos.get(0),token,LE,Dim,LDibujo,words,otronodo,1,para,ambito);  //EJECUCION DEL METODO
                            }
                            //System.out.println("    NODE M sin parametros.............. "+m.root);
                        }
                    }else{
                        System.out.println("contenido 3 parametros ");
                        token.id = nodo.hijos.get(0).cadena;
                        token.rol = "metodo";
                        token.ambito = darAmbitio(padre);
                        token.tipo = "void";
                        token.fila = nodo.hijos.get(0).f;
                        token.columna = nodo.hijos.get(0).c;
                        if(nodo.hijos.get(1).hijos.get(0).cadena.compareTo("PARAMETROS")==0){
                            token.root = nodo.hijos.get(1).hijos.get(1);
                            System.out.println("            TOKEN.ROOT P------------"+token.root+"  nodo.hijos. "+nodo.hijos.get(1).hijos.get(1));
                        }else if(nodo.hijos.get(1).hijos.get(0).cadena.compareTo("METOD")==0){
                            token.root = nodo.hijos.get(1).hijos.get(0);
                            System.out.println("            TOKEN.ROOT M------------"+token.root+"  nodo.hijos. "+nodo.hijos.get(1).hijos.get(0));
                       
                        }

                        //padre.cadena = nodo.hijos.get(0).cadena;// LE PASO EL ID COMO AMBITO
                        Simbolo s = new Simbolo();
                        igualarSimbolos(token,s);
                        //verificarSimbolo(s,LS,LE);
                        LS.add(s);
                        NodoArbol auxnodo = new NodoArbol();
                        auxnodo.cadena = nodo.hijos.get(0).cadena;

                        if(nodo.hijos.get(1).hijos.get(0).cadena.compareTo("PARAMETROS")==0){
                            ArrayList<ListaParametro> listap = (ArrayList)ejecucion(nodo.hijos.get(1),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //METODO
                            buscarRSimboloParametro(s,listap,LS,token.lienzo);
                            //s.parametros = listap;
                        }else{
                            ejecucion(nodo.hijos.get(1),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //METODO
                        }
                        verificarSimbolo(s,LS,LE);
                            
                    }
                    
                }
                
                //ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel); //METODO
                
            }
            else if(nodo.hijos.size()==3){
                if(nodo.hijos.get(0).cadena.compareTo("TIPOMETODO")==0){//TIPOMETODO id METODO
                    token.tipo = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);   //TIPOMETODO
                    if(nodo.hijos.get(2).hijos.get(0).cadena.compareTo("VALOR")==0){ //ASIGNACION DE VALOR A VARIABLES
                        NodeError e = new NodeError(nodo.hijos.get(1).f,nodo.hijos.get(1).c,"Error al asignar","No es valida la asignacion de valores "+nodo.hijos.get(1).cadena);
                        LE.add(e);
                    }else if(nodo.hijos.get(2).hijos.get(0).cadena.compareTo("CONT2")==0){//CONT 2
                        NodeError e = new NodeError(nodo.hijos.get(1).f,nodo.hijos.get(1).c,"Error en dimension","No es valida la asignacion de dimensiones "+nodo.hijos.get(1).cadena);
                        LE.add(e);
                    }else{ //PARAMETROS
                        if(nodo.hijos.get(2).hijos.size()==0){//ERROR
                        
                        }
                        token.id = nodo.hijos.get(1).cadena;
                        token.rol = "metodo";
                        token.ambito = darAmbitio(padre);
                        token.tipo = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                        token.fila = nodo.hijos.get(0).f;
                        token.columna = nodo.hijos.get(0).c;
                        if(nodo.hijos.get(2).hijos.get(0).cadena.compareTo("PARAMETROS")==0){
                            token.root = nodo.hijos.get(2).hijos.get(1);
                        }else if(nodo.hijos.get(2).hijos.get(0).cadena.compareTo("METOD")==0){
                            token.root = nodo.hijos.get(2).hijos.get(0);
                        }
                        //padre.cadena = nodo.hijos.get(0).cadena;// LE PASO EL ID COMO AMBITO
                        Simbolo s = new Simbolo();
                        igualarSimbolos(token,s);
                        //verificarSimbolo(s,LS,LE);
                        LS.add(s);
                        NodoArbol auxnodo = new NodoArbol();
                        auxnodo.cadena = nodo.hijos.get(0).cadena;
                        System.out.println("    parametros nodo.size = 3 else ");
                        if(nodo.hijos.get(2).hijos.get(0).cadena.compareTo("PARAMETROS")==0){
                            ArrayList<ListaParametro> listap = (ArrayList)ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //METODO
                            buscarRSimboloParametro(s,listap,LS,token.lienzo);
                            //s.parametros = listap;
                        }else{
                            ejecucion(nodo.hijos.get(2),LS,auxnodo,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //METODO
                        }
                        verificarSimbolo(s,LS,LE);    
                    }
                }
                else if(nodo.hijos.get(1).cadena.compareTo("DIMENSION")==0){//id DIMENSION METODO
                    if(nodo.hijos.get(2).hijos.get(0).cadena.compareTo("VALOR")==0){ //ASIGNACION DE VALOR A VARIABLES
                        ArrayList<String> listadim = new ArrayList<String>();
                        ArrayList<String> listadim2 = new ArrayList<String>();
                        ArrayList<String> listadim3 = new ArrayList<String>();
                        listadim2 = (ArrayList)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,listadim,LDibujo,words,otronodo,nivel,para,ambito);
                        for(int i=listadim2.size()-1;i>=0;i--){
                            listadim3.add(listadim2.get(i));
                            System.out.println("    DIMENSION CONTENIDO 3----- "+listadim2.get(i)+" token.lienzo "+token.lienzo);
                        }                    
                        Simbolo s = buscarSimboloArr(nodo.hijos.get(0),LS,token.lienzo,LE);
                        if(listadim3.size()==s.size){
                            String nuevoty = convertTipoArreglo(s.tipo);
                            NodoArbol valor = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //METODO                   
                            if(nuevoty.compareTo(valor.ty)==0){
                                insertArreglo(listadim3,0,s.arreglo,valor.cadena);
                            }else{
                                NodeError e = new NodeError(s.fila,s.columna,"Error al asignar ","Tipo de dato no coincide "+s.id+" tipo "+s.tipo+" dato ingresado "+valor.cadena+ " tipo "+valor.ty);
                                LE.add(e);
                            }
                            
                        }else{
                            NodeError e = new NodeError(s.fila,s.columna,"Error en dimension del arreglo ","Las dimensiones de "+s.id+" no coinciden "+s.size+" dim ingresadas "+listadim3.size());
                            LE.add(e);
                        }
                    }
                    else if(nodo.hijos.get(2).hijos.get(0).cadena.compareTo("CONT2")==0){//CONT 2
                        ArrayList<String> listadim = new ArrayList<String>();
                        ArrayList<String> listadim2 = new ArrayList<String>();
                        ArrayList<String> listadim3 = new ArrayList<String>();
                        listadim2 = (ArrayList)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,listadim,LDibujo,words,otronodo,nivel,para,ambito);
                        for(int i=listadim2.size()-1;i>=0;i--){
                            listadim3.add(listadim2.get(i));
                            System.out.println("    DIMENSION CONTENIDO 3----- "+listadim2.get(i)+" token.lienzo "+token.lienzo);
                        }                    
                        Simbolo s = buscarSimboloArr(nodo.hijos.get(0),LS,token.lienzo,LE);
                        if(listadim3.size()==s.size){
                            System.out.println("    CONT 2 "+nodo.hijos.get(1).hijos.get(0).hijos.get(0).cadena);
                            if(nodo.hijos.get(2).hijos.get(0).hijos.get(0).cadena.compareTo("++")==0 || nodo.hijos.get(2).hijos.get(0).hijos.get(0).cadena.compareTo("--")==0){
                                if(nodo.hijos.get(2).hijos.size()==1){
                                    //NodoArbol aux = buscarSimbolo(nodo.hijos.get(0),LS,token.lienzo);
                                    //System.out.println("    ++ Y -- aux "+aux.cadena+" "+aux.ty);
                                    String valArr = getvalArreglo(listadim3,0,s.arreglo,"");
                                    System.out.println("    getvalArreglo "+valArr+" s"+s.valor);
                                    NodoArbol aux2 = new NodoArbol();
                                    aux2.cadena = valArr;
                                    String nuevoTy = convertTipoArreglo(s.tipo);
                                    aux2.ty = nuevoTy;
                                    System.out.println("    valArr "+valArr+" s.tipo "+s.tipo+" nuevoTy "+nuevoTy);
                                    NodoArbol aux3 = operarSimplificado(aux2,aux2,nodo.hijos.get(2).hijos.get(0).hijos.get(0),LS,LE);
                                    System.out.println("    aux3 "+aux3.cadena+"  "+aux3.ty+"    aux2 "+aux2.cadena+"  "+aux2.ty);
                                    //buscarRSimbolo(nodo.hijos.get(0).cadena,aux3.cadena,LS,token.lienzo);
                                    insertArreglo(listadim3,0,s.arreglo,aux3.cadena);
                                }else{
                                    NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error al operar ","Operador "+nodo.hijos.get(2).hijos.get(0).hijos.get(0).cadena);
                                    LE.add(e);
                                }
                                
                            }else if(nodo.hijos.get(2).hijos.get(0).hijos.get(0).cadena.compareTo("+=")==0 || nodo.hijos.get(2).hijos.get(0).hijos.get(0).cadena.compareTo("-=")==0){
                                NodoArbol aux = buscarSimbolo(nodo.hijos.get(0),LS,token.lienzo);
                                System.out.println("    += Y -= aux "+aux.cadena+" "+aux.ty);
                                if(nodo.hijos.get(2).hijos.size()==2){
                                //    NodoArbol aux3 = (NodoArbol)ejecucion(nodo.hijos.get(1).hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel); //G
                                //    NodoArbol aux2 = operarSimplificado(aux,aux,nodo.hijos.get(1).hijos.get(0).hijos.get(0),LS,LE);
                                //    NodoArbol aux4 = operarSimplificado(aux,aux3,nodo.hijos.get(1).hijos.get(0).hijos.get(0),LS,LE);
                                //    System.out.println("    aux3 "+aux3.cadena+"  "+aux3.ty+"    aux4 "+aux4.cadena+"  "+aux4.ty);
                                //    buscarRSimbolo(nodo.hijos.get(0).cadena,aux4.cadena,LS,token.lienzo);
                                    String valArr = getvalArreglo(listadim3,0,s.arreglo,"");
                                    System.out.println("    getvalArreglo "+valArr+" s"+s.valor);
                                    NodoArbol aux2 = new NodoArbol();
                                    aux2.cadena = valArr;
                                    NodoArbol aux4 = (NodoArbol)ejecucion(nodo.hijos.get(2).hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //G
                                    String nuevoTy = convertTipoArreglo(s.tipo);
                                    aux2.ty = nuevoTy;
                                    System.out.println("    valArr "+valArr+" s.tipo "+s.tipo+" nuevoTy "+nuevoTy);
                                    NodoArbol aux3 = operarSimplificado(aux2,aux4,nodo.hijos.get(2).hijos.get(0).hijos.get(0),LS,LE);
                                    System.out.println("    aux4 "+aux4.cadena+"  "+aux4.ty+"    aux3 "+aux3.cadena+"  "+aux3.ty+"    aux2 "+aux2.cadena+"  "+aux2.ty);
                                    //buscarRSimbolo(nodo.hijos.get(0).cadena,aux3.cadena,LS,token.lienzo);
                                    insertArreglo(listadim3,0,s.arreglo,aux3.cadena);
                                }else{
                                    NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error al operar ","Operador += -= falta valor");
                                    LE.add(e);
                                }

                            }                            
                        }else{
                            NodeError e = new NodeError(s.fila,s.columna,"Error en dimension del arreglo ","Las dimensiones de "+s.id+" no coinciden "+s.size+" dim ingresadas "+listadim3.size());
                            LE.add(e);
                        }
                        
                    }else{//PARAMETROS  
                        //Error no puede venir dimension
                        NodeError e = new NodeError(nodo.hijos.get(0).f,nodo.hijos.get(0).c,"Error en dimension","No es valida la asignacion de dimensiones "+nodo.hijos.get(0).cadena);
                        LE.add(e);
                    }
                }
            }
            else if(nodo.hijos.size()==4){ //TIPOMETODO id DIMENSION METODO
                NodeError e = new NodeError(nodo.hijos.get(1).f,nodo.hijos.get(1).c,"Error en dimension","No es valida la asignacion de dimensiones "+nodo.hijos.get(1).cadena);
                LE.add(e);
            }
        }
        
        //PRODUCCION TIPOMETODO
        else if(nodo.cadena.compareTo("TIPOMETODO")==0){
            System.out.println("TIPOMETODO");
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
            else if(nodo.hijos.get(0).cadena.compareTo("tvoid")==0){
                return "void";
            }
        }
        
        //PRODUCCION METODO
        else if(nodo.cadena.compareTo("METODO")==0){    //PARAMETROS?  METOD?  | VALOR     |CONT2 VALOR?
            System.out.println("METODO");
            if(nodo.hijos.size()==0){ //PARAMETROS ? METOD?
                //NO HACE NADA SOLO DECLARA UN METODO VACIO o LO INVOCA
            }else if(nodo.hijos.size()==1){
                if(nodo.hijos.get(0).cadena.compareTo("PARAMETROS")==0){
                    //METODO CON PARAMETROS SIN SENTENCIAS
                    ArrayList<ListaParametro> listap = new ArrayList<ListaParametro>();
                    System.out.println("    parametros entro aca ");
                    ArrayList<ListaParametro> listap2 = (ArrayList)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,listap,ambito);//PARAMETROS
                    return listap2;
                }else if(nodo.hijos.get(0).cadena.compareTo("METOD")==0){
                    //METODO SIN PARAMETROS CON SENTENCIAS
                    if(nivel!=0){   //esto es para que no se ejecute al parsearse sino solo al ser invocado 
                         ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,1,para,ambito);//METOD
                    }
                    //ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para);//METOD
                }else if(nodo.hijos.get(0).cadena.compareTo("VALOR")==0){
                    //RETORNAR VALOR PARA ASIGNAR A VARIABLE
                    NodoArbol aux = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    return aux;
                }else if(nodo.hijos.get(0).cadena.compareTo("CONT2")==0){
                    //SI ES ++ Y -- ES CORRECTO SI ES += Y -= ES INCORRECTO
                }
            }else if(nodo.hijos.size()==2){
                System.out.println("    hijo 1 "+nodo.hijos.get(0).cadena + " hijos 2 "+nodo.hijos.get(1).cadena);
                if(nodo.hijos.get(0).cadena.compareTo("CONT2")==0){
                    //SI ES += Y -= ES CORRECTO SI ES ++ Y -- ES INCORRECTO
                    
                }
                else if(nodo.hijos.get(0).cadena.compareTo("PARAMETROS")==0){
                    //METODO CON PARAMETROS CON SENTENCIAS
                    System.out.println("este es el otro parametros ");
                    ArrayList<ListaParametro> listap = new ArrayList<ListaParametro>();
                    ArrayList<ListaParametro> listap2 = new ArrayList<ListaParametro>();
                    System.out.println("            listap2 PARAMETROS........");
                    listap2 = (ArrayList)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,listap,ambito);//PARAMETROS
                    if(nivel!=0){   //esto es para que no se ejecute al parsearse sino solo al ser invocado
                        System.out.println("            me voy a METOD........");
                        ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,1,para,ambito);//METOD
                    }
                    //ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para);//METOD
                    
                    return listap2;
                }
            }
        }
        
        
        //PRODUCCION METOD
        else if(nodo.cadena.compareTo("METOD")==0){ //SENTENCIAS ?      |   dolar
            System.out.println("METOD");
            if(nodo.hijos.size()==2){
                NodoArbol auxnodo = new NodoArbol();
                auxnodo.cadena = nodo.hijos.get(0).cadena;
                    
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); // SEN
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); // SENTENCIAS
            }
        }
        
        
        //PRODUCCION PARAMETROS
        else if(nodo.cadena.compareTo("PARAMETROS")==0){ //TIPO ID (PARAP)? | LISTAPARAMETRO
            System.out.println("PARAMETROS");
            if(nodo.hijos.size()==1){//LISTAPARAMETRO
                ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                return para;
            }
            else if(nodo.hijos.size()==2){//TIPO ID
                token.id = nodo.hijos.get(1).cadena;
                token.rol = "parametro";
                String auxty = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                token.tipo = auxty;
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = auxty;
                nueva.valorparametro = nodo.hijos.get(1).cadena;
                para.add(nueva);
                System.out.println("            TIPO ID 1");
                return para;
                
            }else if(nodo.hijos.size()==3){//TIPO ID PARAP
                token.id = nodo.hijos.get(1).cadena;
                token.rol = "parametro";
                String auxty = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                token.tipo = auxty;
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = auxty;
                nueva.valorparametro = nodo.hijos.get(1).cadena;
                para.add(nueva);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //PARAP
                System.out.println("            TIPO ID 2");
                return para;
            }
        }
        //PRODUCCION PARAP
        else if(nodo.cadena.compareTo("PARAP")==0){ //TIPO ID PARAP?
            System.out.println("PARAP");
             
            if(nodo.hijos.size()==2){
                token.id = nodo.hijos.get(1).cadena;
                token.rol = "parametro";
                String auxty = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                token.tipo = auxty;
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = auxty;
                nueva.valorparametro = nodo.hijos.get(1).cadena;
                para.add(nueva);
                return para;
            }else if(nodo.hijos.size()==3){
                token.id = nodo.hijos.get(1).cadena;
                token.rol = "parametro";
                String auxty = (String)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                token.tipo = auxty;
                token.ambito = darAmbitio(padre);
                token.fila = nodo.hijos.get(0).f;
                token.columna = nodo.hijos.get(0).c;
                Simbolo s = new Simbolo();
                igualarSimbolos(token,s);
                verificarSimbolo(s,LS,LE);
                LS.add(s);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = auxty;
                nueva.valorparametro = nodo.hijos.get(1).cadena;
                para.add(nueva);
                ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //PARAP
                return para;
            }
        }
        //PRODUCCION LISTAPARAMETRO
        else if(nodo.cadena.compareTo("LISTPARAMETRO")==0){ //VALOR LISTAPARAMETRO2?
            System.out.println("LISTPARAMETRO");
            if(nodo.hijos.size()==1){ //VALOR
                NodoArbol res = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = res.ty;
                nueva.valorparametro = res.cadena;
                para.add(nueva);
                return para;
            }else if(nodo.hijos.size()==2){//VALOR LISTAPARAMETRO2
                NodoArbol res = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = res.ty;
                nueva.valorparametro = res.cadena;
                para.add(nueva);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                return para;
            }
        }
        //PRODUCCION LISTAPARAMETRO2
        else if(nodo.cadena.compareTo("LISTPARAMETRO2")==0){ //VALOR LISTAPARAMETRO2?
            System.out.println("LISTPARAMETRO2");
            if(nodo.hijos.size()==1){//VALOR
                NodoArbol res = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = res.ty;
                nueva.valorparametro = res.cadena;
                para.add(nueva);
                return para;
            }
            else if(nodo.hijos.size()==2){//VALOR LISTAPARAMETRO2
                NodoArbol res = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                ListaParametro nueva = new ListaParametro();
                nueva.parametro = res.ty;
                nueva.valorparametro = res.cadena;
                para.add(nueva);
                ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                return para;
            }
        }
        //PRODUCCION C
        else if(nodo.cadena.compareTo("C")==0){
            System.out.println("C");
            return ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
        }
        //PRODUCCION COLOR
        else if(nodo.cadena.compareTo("COLOR")==0){
            System.out.println("COLOR");
            if(nodo.hijos.size()==1){ //C | color
               
                if(nodo.hijos.get(0).ty==null){ //C
                    NodoArbol n = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
                    System.out.println("    COLOR return "+n.cadena+" "+n.ty);
                    return n;
                }else if(nodo.hijos.get(0).ty.compareTo("cadena")==0){
                    NodoArbol aux = new NodoArbol();
                    aux = nodo.hijos.get(0);
                    //return ejecucion(nodo.hijos.get(0),LS,padre,token,LE);
                    return aux;
                }

            }
            
        }
        //PRODUCCION DIBUJAR_P
        else if(nodo.cadena.compareTo("DIBUJAR_P")==0){ //  C=x  C=y  COLOR=cadena C=diametro
            System.out.println("DIBUJAR_P");
            NodoArbol para1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            NodoArbol para2 = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            NodoArbol para3 = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            NodoArbol para4 = (NodoArbol)ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito);
            int x = verCoordenada(para1,LE);
            int y = verCoordenada(para2,LE);
            int color = verColor(para3,LE);
            int diametro = verCoordenada(para4,LE);
            if(x==1 && y ==1 && color ==1 && diametro ==1){   
                dibujo d = new dibujo(Integer.parseInt(para1.cadena),Integer.parseInt(para2.cadena),"circulo","",0,0,Integer.parseInt(para4.cadena),Color.decode(para3.cadena));
                LDibujo.add(d);
            }
            
        }
        //PRODUCCION DIBUJAR_OR
        else if(nodo.cadena.compareTo("DIBUJAR_OR")==0){ //  C=x  C=y  COLOR=cadena C=Ancho C=Alto C=figura
            System.out.println("DIBUJAR_OR");
            NodoArbol para1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //x
            NodoArbol para2 = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //y
            NodoArbol para3 = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //color
            NodoArbol para4 = (NodoArbol)ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //ancho
            NodoArbol para5 = (NodoArbol)ejecucion(nodo.hijos.get(4),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //alto
            NodoArbol para6 = (NodoArbol)ejecucion(nodo.hijos.get(5),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //figura
            
            int x = verCoordenada(para1,LE);
            int y = verCoordenada(para2,LE);
            int color = verColor(para3,LE);
            int ancho = verCoordenada(para4,LE);
            int alto = verCoordenada(para5,LE);
            int figura = verFigura(para6,LE);
            if(x==1 && y ==1 && color ==1 && ancho ==1 && alto ==1 && figura ==1){   
                if(para6.cadena.compareTo("'o'")==0){
                    dibujo d = new dibujo(Integer.parseInt(para1.cadena),Integer.parseInt(para2.cadena),"ovalo","",Integer.parseInt(para5.cadena),Integer.parseInt(para4.cadena),0,Color.decode(para3.cadena));
                    LDibujo.add(d);
                }else if(para6.cadena.compareTo("'r'")==0){
                    dibujo d = new dibujo(Integer.parseInt(para1.cadena),Integer.parseInt(para2.cadena),"rectangulo","",Integer.parseInt(para5.cadena),Integer.parseInt(para4.cadena),0,Color.decode(para3.cadena));        
                    LDibujo.add(d);
                }
            }
            
        }
        //PRODUCCION DIBUJAR_S
        else if(nodo.cadena.compareTo("DIBUJAR_S")==0){ //  C=x  C=y  COLOR=cadena C=cadena
            System.out.println("DIBUJAR_S");
            NodoArbol para1 = (NodoArbol)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //x
            NodoArbol para2 = (NodoArbol)ejecucion(nodo.hijos.get(1),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //y
            NodoArbol para3 = (NodoArbol)ejecucion(nodo.hijos.get(2),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //color
            NodoArbol para4 = (NodoArbol)ejecucion(nodo.hijos.get(3),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //cadena
            
            int x = verCoordenada(para1,LE);
            int y = verCoordenada(para2,LE);
            int color = verColor(para3,LE);
            int cadena = verCadena(para4,LE);

            if(x==1 && y ==1 && color ==1 && cadena ==1){   
               dibujo d = new dibujo(Integer.parseInt(para1.cadena),Integer.parseInt(para2.cadena),"cadena",para4.cadena,0,0,0,Color.decode(para3.cadena));
               LDibujo.add(d);
            }
        }
        
        //PRODUCCION ORDEN
        else if(nodo.cadena.compareTo("ORDEN")==0){ //  
            System.out.println("                ORDEN");
            ordenar="0";
            if(nodo.hijos.size()==2){
                
                FindOrdenar(nodo.hijos.get(0),token.lienzo,LS,LE,nodo.hijos.get(1).hijos.get(0).cadena);
            }
        }
        
        //PRODUCCION SUMARI
        else if(nodo.cadena.compareTo("SUMARI")==0){ //  
            System.out.println("SUMARI");
            ejecucion(nodo.hijos.get(0),LS,padre,token,LE,Dim,LDibujo,words,otronodo,nivel,para,ambito); //CONT5
        }
        
        //PRODUCCION CONT5
        else if(nodo.cadena.compareTo("CONT5")==0){ //ID | VALORARREGLO
            System.out.println("CONT5");
            sumarizar="";
            //typosumarizar="";
            if(nodo.hijos.get(0).cadena.compareTo("VALORARREGLO")==0){ //VALORARREGLO
                ArrayList<String> word = new ArrayList<String>();
                ArrayList<String> word2 = new ArrayList<String>();
                NodoArbol auxtipo = new NodoArbol();
                auxtipo.ty = "cadena";
                auxtipo.cadena = "sumarizar";
                System.out.println("    auxtipo ----"+auxtipo.ty);
                word2 = (ArrayList)ejecucion(nodo.hijos.get(0),LS,padre,token,LE,word,LDibujo,words,auxtipo,nivel,para,ambito); 
                for(int i=0;i<word2.size();i++){
                    //System.out.println("    sumarizando array.... "+i+"  "+word2.get(i));
                    sumarizar=sumarizar + word2.get(i);
                }
            }
            else{ //id
                 Simbolo s = buscarSimboloArr(nodo.hijos.get(0),LS,token.lienzo,LE);
                 System.out.println("SUMARI nodo.hijos.get(0)           "+nodo.hijos.get(0).cadena+" "+s.size);
                 if(s.tipo.compareTo("arreglo_entero")==0){
                     System.out.println("arrreglo_entero");
                     sumarizar="0";
                     sumarizar=sumariInt(0,s.arreglo,s.size,sumarizar);
                     int auxsumari=(int)Double.parseDouble(sumarizar);
                     sumarizar = String.valueOf(auxsumari);
                 }
                 else if(s.tipo.compareTo("arreglo_doble")==0){
                     System.out.println("arrreglo_doble");
                     sumarizar="0";
                     sumarizar=sumariInt(0,s.arreglo,s.size,sumarizar);
                 }
                 else if(s.tipo.compareTo("arreglo_caracter")==0){
                     System.out.println("arrreglo_caracter");
                     sumarizar=sumari(0,s.arreglo,s.size,sumarizar,0);
                 }
                 else if(s.tipo.compareTo("arreglo_cadena")==0){
                     System.out.println("arrreglo_cadena");
                     sumarizar=sumari(0,s.arreglo,s.size,sumarizar,1);
                 }else{
                     NodeError e = new NodeError(s.fila,s.columna,"Error al sumarizar ","No se puede sumarizar variable "+s.id+" tipo "+s.tipo);
                     LE.add(e);
                 }
            }
        }
       
       return retorno;
    }

public void FindOrdenar(NodoArbol nodo,String lienzo,ArrayList<Simbolo> LS,ArrayList<NodeError> LE,String orden){
    
    Simbolo s = buscarSimboloArr(nodo,LS,lienzo,LE);
    System.out.println("Find ordenar         "+s.id+" "+s.size+" "+orden);
    if(s.tipo.compareTo("arreglo_entero")==0){
        System.out.println("arrreglo_entero");
        
        if(orden.compareTo("ascendente")==0){  System.out.println("     >ascendente"); ordenarAscendenteInt(0,s.arreglo,s.size,0);  ordenar = "1"; }      
        else if(orden.compareTo("descendente")==0){  System.out.println("       >descendente"); ordenarDescendenteInt(0,s.arreglo,s.size,0);    ordenar = "1";}
        else{   NodeError e = new NodeError(s.fila,s.columna,"Error al ordenar ","Tipo de ordenamiento erroneo "+orden);     LE.add(e);}
    }
    else if(s.tipo.compareTo("arreglo_doble")==0){
        System.out.println("arrreglo_doble");
        
        if(orden.compareTo("ascendente")==0){ System.out.println("     >ascendente"); ordenarAscendenteInt(0,s.arreglo,s.size,1); ordenar = "1"; }      
        else if(orden.compareTo("descendente")==0){  System.out.println("     >descendente"); ordenarDescendenteInt(0,s.arreglo,s.size,1); ordenar = "1";  }
        else{   NodeError e = new NodeError(s.fila,s.columna,"Error al ordenar ","Tipo de ordenamiento erroneo "+orden);     LE.add(e);}
 
    }
    else if(s.tipo.compareTo("arreglo_caracter")==0){
        System.out.println("arrreglo_caracter");
        
        if(orden.compareTo("ascendente")==0){ ordenarAscendente(0,s.arreglo,s.size); ordenar = "1"; }      
        else if(orden.compareTo("descendente")==0){  ordenarDescendente(0,s.arreglo,s.size); ordenar = "1";  }
        else{   NodeError e = new NodeError(s.fila,s.columna,"Error al ordenar ","Tipo de ordenamiento erroneo "+orden);     LE.add(e);}
 
    }
    else if(s.tipo.compareTo("arreglo_cadena")==0){
        System.out.println("arrreglo_cadena");
        
        if(orden.compareTo("ascendente")==0){ ordenarAscendente(0,s.arreglo,s.size); ordenar = "1"; }      
        else if(orden.compareTo("descendente")==0){  ordenarDescendente(0,s.arreglo,s.size); ordenar = "1"; }
        else{   NodeError e = new NodeError(s.fila,s.columna,"Error al ordenar ","Tipo de ordenamiento erroneo "+orden);     LE.add(e);}
 
    }else{
        NodeError e = new NodeError(s.fila,s.columna,"Error al ordenar ","No se puede ordenar variable "+s.id+" tipo "+s.tipo);
        LE.add(e);
    }
}    
    
}


