/** 
* Práctica de Seguridad de la Información del Máster Universitario en
* Investigación en Ingeniería y Arquitectura, especialidad Tecnologías
* Informáticas y Comunicaciones.
*
* Copyright 2015 Francisco Javier Merchán Macías.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Affero General Public License as published
* by the Free Software Foundation, either version 3 of the License.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Affero General Public License for more details.
*
* You should have received a copy of the GNU Affero General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * Declaración de librerías.
 */
package p2_si_2015_64_frmerchan;
import java.io.*;
import java.util.StringTokenizer;

/**
 * Practica 2 de Seguridad de la Información. MUI IA. Centro Universitario de Mérida. 2015/2016
 * Clase GestionFicheros. Se realizará todo lo relacionado con el acceso/grabación de ficheros desde esta clase.
 * @author Javier Merchan (javier@javiermerchan.com)
 * @version 0.1
 */
public class GestionFicheros {
    Parametros parametros=new Parametros(); //Instanciamos la clase Parametros para usarla dentro de este método.
    CifradoGronsfeld cifradoGronsfeld=new CifradoGronsfeld(); //Instanciamos la clase CifradoGronsfeld para usarla dentro de este método.
    CifradoSDES cifradoSDES=new CifradoSDES(); //Instanciamos la clase CifradoSDES para usarla dentro de este método.
    private boolean PrimeraVezQueGraboEnFichero=true; //Utilizado en el método GuardarFichero(File archivo,String contenido)
    private String FicheroConfiguracion="";

    /**
     * Constructor de la clase GestionFicheros.
     */
    public GestionFicheros(){
    }

    /**
     * Abre un fichero TXT para leer sus líneas.
     * @param archivo Introduce la ruta y nombre del fichero a abrir. EJ: "c:\\datos.txt"
     */
    public void AbrirFicheroConfiguracion(String archivo) {
        String TextoDesCifrado=""; //Texto Cifrado o Descifrado devuelto por la función AbrirFicheroADesCifrar.
        int VueltaWhile=0;//Líneas leidas en el fichero. Serán las mismas vueltas que haga el While.
        this.setFicheroConfiguracion(archivo);//Almaceno el nombre del fichero de configuración. Lo necesito en this.ImprimirPantallaOFichero("");
        
        try{
            BufferedReader Buffer=new BufferedReader(new FileReader(archivo));
            String temporal="";
            String bfRead="";
            String NombreBandera="";
            String NombreComando="";
            String OnOff="";
            String NombreFicheroOClave="";
            
            while((bfRead=Buffer.readLine()) != null){
                //Hace el ciclo mientras bfRead tiene datos. Es decir, lee el fichero línea a línea.
                //System.out.print(" bfRead-La línea "+(VueltaWhile+1)+" del fichero contiene: "+bfRead);
                if (bfRead.length()==0){/*System.out.println("Una línea vacía.");*/}/*Si existe la línea, pero está a 0,*/
                                                                            /*es decir, vacía, nos la saltamos sin*/
                                                                            /*hacer nada.*/
                else if (bfRead.charAt(0)=='#'){/*System.out.println("Es un comentario... Lo ignoramos.");*/}/*Si encontramos*/
                                                                            /*el carácter #, es un comentarios, por lo*/
                                                                            /*que nos saltamos la línea sin hacer nada.*/
                else if (bfRead.charAt(0)=='@'){ //Es una bandera. La analizamos.
                    bfRead=bfRead.substring(1); //Quitamos el primer carácter.
                    //bfRead=bfRead.toUpperCase(); //Pasamos a mayúsculas.
                    //System.out.println(" bfRead-La bandera @ es;"+bfRead);
                    StringTokenizer Tok=new StringTokenizer(bfRead); //Hacemos una instancia de StringTokenizer para saltar entre parámetros.
                    NombreBandera=Tok.nextToken(); //Pasamos al siguiente Token.
                    //System.out.println("    El Tokens es: '"+NombreBandera+"'");
                    switch (NombreBandera){
                        case "s_des":
                            //No hago nada aquí. Lo dejo por previsión.
                            break;
                        case "gronsfeld":
                            //No hago nada aquí. Lo dejo por previsión.
                            break;
                        default:
                            OnOff=Tok.nextToken();
                            break;
                    }

                    if ("".equals(OnOff)){
                        this.ImprimirPantallaOFichero("La bandera "+NombreBandera+" está vacía. Salimos del programa.",false);
                        System.exit(0);                        
                    }
                    //this.ImprimirPantallaOFichero("    El parámetro del Tokens es (on/off): '"+OnOff+"'",false);

                    switch (NombreBandera){
                        case "salidapantalla":
                            if ("ON".equals(OnOff)){
                                parametros.setSalidaPantalla(true);
                                //this.ImprimirPantallaOFichero("     - Salida a pantalla activada.",false);
                            }
                            else if ("OFF".equals(OnOff)){
                                parametros.setSalidaPantalla(false);
                                //this.ImprimirPantallaOFichero("     - Salida a pantalla desactivada.",false);
                            }
                            else{
                                this.ImprimirPantallaOFichero("     - Cod SP-Hemos encontrado un error en la línea "+(VueltaWhile+1)+" del fichero de configuración. Revíselo",false);
                                Buffer.close();
                                System.exit(0);
                            }
                            break;
                        case "salidafichero":
                            if ("ON".equals(OnOff)){
                                parametros.setSalidaFichero(true);
                                //this.ImprimirPantallaOFichero("     - Salida a fichero activada.",false);
                            }
                            else if ("OFF".equals(OnOff)){
                                parametros.setSalidaFichero(false);
                                //this.ImprimirPantallaOFichero("     - Salida a fichero desactivada.",false);
                            }
                            else{
                                this.ImprimirPantallaOFichero("     - Cod SF-Hemos encontrado un error en la línea "+(VueltaWhile+1)+" del fichero de configuración. Revíselo",false);
                                Buffer.close();
                                System.exit(0);
                            }
                            break;
                        case "gronsfeld":
//                            if ("ON".equals(OnOff)){
//                                parametros.setTipoCifradoGronsfeld(true); //Activamos el cifrado Gronsfeld.
//                                parametros.setTipoCifradoSDES(false); //Desactivamos el cifrado DES simplificado.
//                                //this.ImprimirPantallaOFichero("     - Cifrado Gronsfeld activado.",false);
//                                //this.ImprimirPantallaOFichero("     - Cifrado DES Simplificado desactivado.",false);
//                            }
//                            else if ("OFF".equals(OnOff)){
//                                parametros.setTipoCifradoGronsfeld(false); //Desactivamos el cifrado Gronsfeld.
//                                parametros.setTipoCifradoSDES(false); //Desactivamos el cifrado DES simplificado.
//                                //this.ImprimirPantallaOFichero("     - Cifrado Gronsfeld desactivado.",false);
//                                //this.ImprimirPantallaOFichero("     - Cifrado DES Simplificado desactivado.",false);
//                            }
//                            else{
                                //this.ImprimirPantallaOFichero("     - Cod GF-Hemos encontrado parámetros en @ gronsfeld, en la línea "+(VueltaWhile+1)+" del fichero de configuración.",false);
                                parametros.setTipoCifradoGronsfeld(true); //Activamos el cifrado Gronsfeld.
                                parametros.setTipoCifradoSDES(false); //Desactivamos el cifrado DES simplificado.
                                //this.ImprimirPantallaOFichero("     - Cifrado Gronsfeld activado.",false);
                                //this.ImprimirPantallaOFichero("     - Cifrado DES Simplificado desactivado.",false);
//                            }
                            break;
                        case "s_des":
//                            if ("ON".equals(OnOff)){
//                                parametros.setTipoCifradoGronsfeld(false); //Activamos el cifrado Gronsfeld.
//                                parametros.setTipoCifradoSDES(true); //Desactivamos el cifrado DES simplificado.
//                                //this.ImprimirPantallaOFichero("     - Cifrado Gronsfeld desactivado.",false);
//                                //this.ImprimirPantallaOFichero("     - Cifrado DES Simplificado activado.",false);
//                            }
//                            else if ("OFF".equals(OnOff)){
//                                parametros.setTipoCifradoGronsfeld(false); //Desactivamos el cifrado Gronsfeld.
//                                parametros.setTipoCifradoSDES(false); //Desactivamos el cifrado DES simplificado.
//                                //this.ImprimirPantallaOFichero("     - Cifrado Gronsfeld desactivado.",false);
//                                //this.ImprimirPantallaOFichero("     - Cifrado DES Simplificado desactivado.",false);
//                            }
//                            else{
                                //this.ImprimirPantallaOFichero("     - Cod SDES-No hemos encontrado parámetros en @ s_des, en la línea "+(VueltaWhile+1)+" del fichero de configuración.",false);
                                parametros.setTipoCifradoGronsfeld(false); //Desactivamos el cifrado Gronsfeld.
                                parametros.setTipoCifradoSDES(true); //Activamos el cifrado DES simplificado.
                                //this.ImprimirPantallaOFichero("     - Cifrado Gronsfeld desactivado.",false);
                                //this.ImprimirPantallaOFichero("     - Cifrado DES Simplificado activado.",false);
//                            }
                            break;
                        case "traza":
                            if ("ON".equals(OnOff)){
                                parametros.setTraza(true); //Activamos la Traza del cifrado S_DES.
                                //this.ImprimirPantallaOFichero("     - Traza del cifrado DES Simplificado activada.",false);
                            }
                            else if ("OFF".equals(OnOff)){
                                parametros.setTraza(false); //Activamos la Traza del cifrado S_DES.
                                //this.ImprimirPantallaOFichero("     - Traza del cifrado DES Simplificado desactivada.",false);
                            }
                            else{
                                this.ImprimirPantallaOFichero("     - Cod TrazaSDES-Fallo en la línea "+(VueltaWhile+1)+" del fichero de configuración.",false);
                                parametros.setTraza(true); //Activamos la Traza del cifrado S_DES.
                                //Buffer.close();
                                //System.exit(0);
                            }
                            break;
                        default:
                            this.ImprimirPantallaOFichero("     - Cod DF-Hemos encontrado un error en la línea "+(VueltaWhile+1)+" del fichero de configuración. Revíselo",false);
                            Buffer.close();
                            System.exit(0);
                    }
                }
                else if (bfRead.charAt(0)=='&'){ //Es un comando. La analizamos.
                    bfRead=bfRead.substring(1); //Quitamos el primer carácter.
                    //bfRead=bfRead.toUpperCase(); //Pasamos a mayúsculas.
                    //System.out.println(" bfRead-El comando & es;"+bfRead);
                    StringTokenizer Tok=new StringTokenizer(bfRead); //Hacemos una instancia de StringTokenizer para saltar entre parámetros.
                    NombreComando=Tok.nextToken(); //Pasamos al siguiente Token. Almacenamos en NombreComando el comando que hemos detectado.
                    //System.out.println("    El Tokens es: '"+NombreComando+"'");
                    NombreFicheroOClave=Tok.nextToken(); // Almacenamos en NombreFicheroOClave el nombre de fichero o la clave de cifrado, según opción.
                    //System.out.println("    El parámetro del Tokens es (NombreFicheroOClave): '"+NombreFicheroOClave+"'");
                    if ("".equals(OnOff)){
                        this.ImprimirPantallaOFichero("El comando "+NombreComando+" está vacío. Salimos del programa.",false);
                        System.exit(0);                        
                    }
                    
                    switch (NombreComando){
                        case "codifica":
                            //this.ImprimirPantallaOFichero("     - El fichero a codificar es: "+NombreFicheroOClave,false);
                            parametros.setFicheroACifrar(NombreFicheroOClave);
                            parametros.setFicheroADescifrar(""); //Dejamos en blanco el fichero a descifrar, si se hubiese cargado uno anteriormente.
                            break;
                        case "descodifica":
                            //this.ImprimirPantallaOFichero("     - El fichero a descodificar es: "+NombreFicheroOClave,false);
                            parametros.setFicheroADescifrar(NombreFicheroOClave);
                            parametros.setFicheroACifrar(""); //Dejamos en blanco el fichero a cifrar, si se hubiese cargado uno anteriormente.
                            break;
                        case "clave":
                            //this.ImprimirPantallaOFichero("     - Configuramos la clave de cifrado/descifrado '"+NombreFicheroOClave+"'",false);
                            parametros.setClave(NombreFicheroOClave);
                            break;
                        case "ficherosalida":
                            //this.ImprimirPantallaOFichero("     - El fichero de salida es: "+NombreFicheroOClave,false);
                            parametros.setFicheroSalida(NombreFicheroOClave);
                            break;
                        default:
                            this.ImprimirPantallaOFichero("     - NC-Hemos encontrado un error en la línea "+(VueltaWhile+1)+" del fichero de configuración. Revíselo",false);
                            Buffer.close();
                            System.exit(0);
                    }
                }
                else{
                    this.ImprimirPantallaOFichero("     - Hemos encontrado un error en la línea "+(VueltaWhile+1)+" del fichero de configuración. Revíselo",false);
                    Buffer.close();
                    System.exit(0);
                }
                VueltaWhile++;
            }//FIN BUCLE WHILE
            if (parametros.getFicheroSalida()==""){
                parametros.setFicheroSalida("FJMM-TrazadoXDefecto.txt");//Configuro un fichero de salida por defecto.
                this.ImprimirPantallaOFichero(" Configuro el fichero de salida por defecto, con el nombre: "+parametros.getFicheroSalida(),false);
            }
            Buffer.close();
        }
        catch (IOException ex){
            this.ImprimirPantallaOFichero("Se ha producido un error al abrir el fichero de configuración. Por favor, revíse el archivo: "+archivo,false);
        }

        finally{
            this.ImprimirPantallaOFichero("",false);
            this.ImprimirPantallaOFichero(" ---- PROGRAMA DE DES-CIFRADO CREADO PARA LA ASIGNATURA 'SEGURIDAD DE LA INFORMACIÓN' - CURSO 2015-2016 ----",false);
            this.ImprimirPantallaOFichero(" -----------------------------------------------------------------------------------------------------------",false);
            this.ImprimirPantallaOFichero(" -  Bienvenid@ al programa de la segunda práctica de Seguridad de la información, correspondiente a la     -",false);
            this.ImprimirPantallaOFichero(" - asignatura SEGURIDAD DE LA INFORMACIÓN del MÁSTER UNIVERSITARIO DE INVESTIGACIÓN EN INGENIERÍA Y        -",false);
            this.ImprimirPantallaOFichero(" - ARQUITECTURA, ESPECIALIDAD EN TECNOLOGÍAS INFORMÁTICAS Y DE COMUNICACIONES.                             -",false);
            this.ImprimirPantallaOFichero(" -  Utilizamos los algoritmos: Gronsfeld y DES simplificado.                                               -",false);
            this.ImprimirPantallaOFichero(" -----------------------------------------------------------------------------------------------------------",false);
            this.ImprimirPantallaOFichero(" ---- (C) 2015 FRANCISCO JAVIER MERCHÁN MACÍAS - INGENIERO TÉCNICO EN TELECOMUNICACIÓN, ESP. TELEMÁTICA ----",false);
            this.ImprimirPantallaOFichero("",false);
            
            //System.out.println("El valor de Fichero Cifrar es: '"+parametros.getFicheroACifrar()+"'",false);
            //System.out.println("El valor de Fichero Descifrar es: '"+parametros.getFicheroADescifrar()+"'",false);
            if (parametros.getFicheroACifrar().length()>0){
                if(parametros.isSalidaPantalla()) this.ImprimirPantallaOFichero("          - Salida a pantalla activada.",false);
                else this.ImprimirPantallaOFichero("          - Salida a pantalla desactivada.",false);
                if(parametros.isSalidaFichero()) this.ImprimirPantallaOFichero("          - Salida a fichero activada.",false);
                else this.ImprimirPantallaOFichero("          - Salida a fichero desactivada.",false);
                this.ImprimirPantallaOFichero("          - Vamos a CIFRAR el fichero "+parametros.getFicheroACifrar(),false);
                this.ImprimirPantallaOFichero("          - Clave de cifrado: '"+parametros.getClave()+"'",false);
                this.ImprimirPantallaOFichero("          - El fichero de salida es: "+parametros.getFicheroSalida(),false);
                if(parametros.isTipoCifradoGronsfeld())this.ImprimirPantallaOFichero("          - Usamos el cifrado Gronsfeld.",false);
                if(parametros.isTipoCifradoSDES())this.ImprimirPantallaOFichero("          - Usamos el cifrado DES Simplificado.",false);
                if (parametros.isTraza()) this.ImprimirPantallaOFichero("          - La traza está activada.",false);
                else this.ImprimirPantallaOFichero("          - La traza está desactivada.",false);
                this.ImprimirPantallaOFichero("",false);//Dejo un espacio en blanco.
                this.ImprimirPantallaOFichero("-- INICIO DEL PROCESO DE CIFRADO A CONTINUACIÓN ------------------------------------",false);
                TextoDesCifrado=this.AbrirFicheroADesCifrar(parametros.getFicheroACifrar(), "CIFRAR");
                this.ImprimirPantallaOFichero(TextoDesCifrado,true);
                this.ImprimirPantallaOFichero("-- FIN DEL PROCESO DE CIFRADO ------------------------------------------------------",false);
                this.ImprimirPantallaOFichero("\r\n >>>   Hasta pronto...   <<< \r\n",false);
            }
            else if (parametros.getFicheroADescifrar().length()>0){
                if(parametros.isSalidaPantalla()) this.ImprimirPantallaOFichero("          - Salida a pantalla activada.",false);
                else this.ImprimirPantallaOFichero("          - Salida a pantalla desactivada.",false);
                if(parametros.isSalidaFichero()) this.ImprimirPantallaOFichero("          - Salida a fichero activada.",false);
                else this.ImprimirPantallaOFichero("          - Salida a fichero desactivada.",false);
                this.ImprimirPantallaOFichero("          - Vamos a DESCIFRAR el fichero "+parametros.getFicheroADescifrar(),false);
                this.ImprimirPantallaOFichero("          - Clave de descifrado: '"+parametros.getClave()+"'",false);
                this.ImprimirPantallaOFichero("          - El fichero de salida es: "+parametros.getFicheroSalida(),false);
                if(parametros.isTipoCifradoGronsfeld())this.ImprimirPantallaOFichero("          - Usamos el cifrado Gronsfeld.",false);
                if(parametros.isTipoCifradoSDES())this.ImprimirPantallaOFichero("          - Usamos el cifrado DES Simplificado.",false);
                if (parametros.isTraza()) this.ImprimirPantallaOFichero("          - La traza está activada.",false);
                else this.ImprimirPantallaOFichero("          - La traza está desactivada.",false);
                this.ImprimirPantallaOFichero("",false);//Dejo un espacio en blanco.
                this.ImprimirPantallaOFichero("-- INICIO DEL PROCESO DE DESCIFRADO A CONTINUACIÓN ---------------------------------",false);
                TextoDesCifrado=this.AbrirFicheroADesCifrar(parametros.getFicheroADescifrar(), "DESCIFRAR");
                this.ImprimirPantallaOFichero(TextoDesCifrado,true);
                this.ImprimirPantallaOFichero("-- FIN DEL PROCESO DE DESCIFRADO ---------------------------------------------------",false);
                this.ImprimirPantallaOFichero("\r\n >>>   Hasta pronto...   <<< \r\n",false);
            }
        }
    }

    /**
     * Método que abre un fichero para cifrarlo o descifrarlo.
     * @param archivo Especifica el nombre del fichero que se va a abrir.
     * @param accion Especifica si se va a CIFRAR o DESCIFRAR el contenido del fichero que se va a abrir.
     * @return String con el contenido del fichero cifrado o descifrado, según la acción que se lleve a cabo.
     */
    public String AbrirFicheroADesCifrar(String archivo, String accion) {
        String texto="";
        int VueltaWhile=0;//Líneas leidas en el fichero. Serán las mismas vueltas que haga el While.
        
        if (parametros.getClave()==""){
            this.ImprimirPantallaOFichero("CLAVE VACIA EN FICHERO DE CONFIGURACIÓN!!!. Salimos del programa.",false);
            System.exit(-1);
        }
        
        try{
            BufferedReader BufferDesCifrar=new BufferedReader(new FileReader(archivo));
            String temporal="";
            String bfRead="";
            cifradoGronsfeld.setPosicionClave(0); //La primera vez que usamos el método, ponemos la clave de cifrado o descifrado a 0.
            
            while((bfRead=BufferDesCifrar.readLine()) != null){
                if("CIFRAR".equals(accion)){
                    if (parametros.isTipoCifradoGronsfeld()==true&&parametros.isTipoCifradoSDES()==false){ //¿Es cifrado Gronsfeld?
                        temporal=cifradoGronsfeld.CifrarTexto(bfRead, parametros.getClave(),cifradoGronsfeld.getPosicionClave());
                        //this.ImprimirPantallaOFichero("Linea "+VueltaWhile+" cifrada: "+temporal,false);
                        //this.ImprimirPantallaOFichero(temporal,false);
                    }
                    else if (parametros.isTipoCifradoSDES()==true&&parametros.isTipoCifradoGronsfeld()==false){ //¿Es cifrado SDES?
                        temporal=cifradoSDES.CifrarTexto(bfRead, parametros.getClave(), parametros.isTraza(), parametros.isSalidaPantalla());
                    }
                    else{
                        this.ImprimirPantallaOFichero(" + No se ha seleccionado ningún tipo de cifrado. Por favor especifícalo en el fichero de configuración.",false);
                        System.exit(-1);
                    }
                }
                else if("DESCIFRAR".equals(accion)){
                    if (parametros.isTipoCifradoGronsfeld()==true&&parametros.isTipoCifradoSDES()==false){ //¿Es cifrado Gronsfeld?
                        temporal=cifradoGronsfeld.DescifrarTexto(bfRead, parametros.getClave(), cifradoGronsfeld.getPosicionClave());
                        //this.ImprimirPantallaOFichero("Linea "+VueltaWhile+" descifrada: "+temporal,false);
                        //this.ImprimirPantallaOFichero(temporal,false);
                    }
                    else if (parametros.isTipoCifradoSDES()==true&&parametros.isTipoCifradoGronsfeld()==false){ //¿Es cifrado SDES?
                        temporal=cifradoSDES.DescifrarTexto(bfRead, parametros.getClave(), parametros.isTraza(), parametros.isSalidaPantalla());
                    }
                    else{
                        this.ImprimirPantallaOFichero(" + No se ha seleccionado ningún tipo de cifrado. Por favor especifícalo en el fichero de configuración.",false);
                        System.exit(-1);
                    }
                }
                else{
                    this.ImprimirPantallaOFichero("No se si hay que CIFRAR o DESCIFRAR. Por favor indíque el fichero en el fichero de configuración.",false);
                }

                //temporal=temporal+bfRead; //Guardamos el texto del archivo.
                texto+=temporal+"\r\n";
                VueltaWhile++;
            }//FIN BUCLE WHILE
            //texto=temporal;
            BufferDesCifrar.close();
        }
        catch (IOException e){
            this.ImprimirPantallaOFichero("Se ha producido un error al abrir el fichero a cifrar o descifrar. Por favor, revíse el fichero: "+archivo,false);
        }
        finally{
            return texto;
        }    
    }
    
    /**
     * Guardar datos en un fichero TXT.
     * @param archivo Introduce la ruta y nombre del fichero a grabar. EJ: c:\\datos.txt
     * @param contenido Especifica que contenido se ha de guardar en el fichero.
     */
    /*Guardar fichero de texto*/
    public void GuardarFichero(String archivo,String contenido){
        //File file=new File(archivo);
        if (this.isPrimeraVezQueGraboEnFichero()==true){//Si el fichero no existe o es la primera vez que grabamos algo, se crea un nuevo fichero.
            try{
                //System.out.println("Añadimos información nueva. PRIMERA VEZ TRUE");
                FileWriter Escribir = new FileWriter(archivo,false); //Variable a FALSE, borra información del fichero y añade la nueva.
                Escribir.write(contenido);
                Escribir.close();
                this.setPrimeraVezQueGraboEnFichero(false);
                //System.out.println("El fichero "+file+" se ha creado. PRIMERA VEZ TRUE");
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        else{
            //Añadimos información
            try{
                //System.out.println("Añadimos información nueva. A PARTIR DE SEGUNDA VEZ FALSE");
                FileWriter Escribir = new FileWriter(archivo,true); //Variable a TRUE, añade información al fichero.
                Escribir.append(contenido);
                Escribir.close();
            }catch(IOException ex1){
                ex1.printStackTrace();
            }
        }
    }

    /**
     * Método que salva en el fichero de salida todo lo que aparece en pantalla.
     * @param Texto String con el texto a grabar.
     * @param GuardarEnFichero Si está a false no guardará nada de lo que aparezca en el fichero. Si está a true si lo guardará. Esto es para guardar solamente la salida del cifrado o descifrado y no todo lo que salga en la pantalla.
     */
    public void ImprimirPantallaOFichero(String Texto, boolean GuardarEnFichero){
        if (this.parametros.isSalidaPantalla()==true){
            System.out.print(Texto+"\r\n");
        }
        if (this.parametros.isSalidaFichero()==true){
            if (!"".equals(parametros.getFicheroSalida())){
                if (GuardarEnFichero==true){
                    this.GuardarFichero(parametros.getFicheroSalida(), Texto+"\r\n");
                }
            }
            else{
                //Es posible que aún no se haya leido el nombre del fichero de salida,
                //por lo que vamos a intentar leerlo, y si no lo encontramos, se pondrá
                //un nombre por defecto.
                try{
                    BufferedReader bfBuscarFicheroSalida=new BufferedReader(new FileReader(this.getFicheroConfiguracion()));
                    //String temporal="";
                    String bfBFSalida="";
                    //String NombreBandera="";
                    String NombreComando="";
                    //String OnOff="";
                    String NombreFicheroOClave="";

                    while((bfBFSalida=bfBuscarFicheroSalida.readLine()) != null){
                        if (bfBFSalida.charAt(0)=='&'){ //Es un comando. La analizamos.
                            bfBFSalida=bfBFSalida.substring(1); //Quitamos el primer carácter.
                            StringTokenizer Tok=new StringTokenizer(bfBFSalida); //Hacemos una instancia de StringTokenizer para saltar entre parámetros.
                            NombreComando=Tok.nextToken(); //Pasamos al siguiente Token. Almacenamos en NombreComando el comando que hemos detectado.
                            NombreFicheroOClave=Tok.nextToken(); // Almacenamos en NombreFicheroOClave el nombre de fichero o la clave de cifrado, según opción.

                            switch (NombreComando){
                                case "ficherosalida":
                                    parametros.setFicheroSalida(NombreFicheroOClave);
                                    //this.ImprimirPantallaOFichero("     - El fichero de salida es: "+NombreFicheroOClave,false);
                                    break;
                                default:
                                    parametros.setFicheroSalida("FJMM-TrazadoXDefecto.txt");
                                    //this.ImprimirPantallaOFichero("     - No hemos encontrado fichero de salida. POR DEFECTO es: "+NombreFicheroOClave,false);
                                    break;
                            }
                        }
                    }//FIN BUCLE WHILE
                    bfBuscarFicheroSalida.close();
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
                this.GuardarFichero(parametros.getFicheroSalida(), Texto+"\r\n");
            }
        }
    }

    /**
     * Método para consultar si es la primera vez que grabo en el fichero de salida.
     * @return booleano. True si es la primera vez que grabo en el fichero de salida.
     */
    public boolean isPrimeraVezQueGraboEnFichero() {
        return PrimeraVezQueGraboEnFichero;
    }

    /**
     * Método para configurar si es la primera vez que grabo en el fichero de salida.
     * @param PrimeraVezQueGraboEnFichero Configura booleano para configurar si es la primera vez que se graba en el fichero de salida.
     */
    public void setPrimeraVezQueGraboEnFichero(boolean PrimeraVezQueGraboEnFichero) {
        this.PrimeraVezQueGraboEnFichero = PrimeraVezQueGraboEnFichero;
    }

    /**
     * Método que devuelve el nombre del fichero de configuración.
     * @return Devuelve el nombre del fichero de configuración
     */
    public String getFicheroConfiguracion() {
        return FicheroConfiguracion;
    }

    /**
     * Método que configura el nombre del fichero de configuración.
     * @param FicheroConfiguracion Configura el nombre del fichero de configuración
     */
    public void setFicheroConfiguracion(String FicheroConfiguracion) {
        this.FicheroConfiguracion = FicheroConfiguracion;
    }
}