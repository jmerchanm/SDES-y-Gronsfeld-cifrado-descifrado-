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

/**
 * Practica 1 de Seguridad de la Información. MUI IA. Centro Universitario de Mérida. 2015/2016
 * Clase CifradoGronsfeld. Se realizará todo lo relacionado con el des/cifrado del algoritmo Gronsfeld desde esta clase.
 * @author Javier Merchan (javier@javiermerchan.com)
 * @version 0.1
 */
public class CifradoGronsfeld {
    private int PosicionClave=0;
    /**
     * Constructor de la clase CifradoGronsfeld.
     */
    public CifradoGronsfeld() {
        
    }

    /**
     * Crea la matriz de cifrado, la cual siempre tiene la misma forma y contenido.
     * @return char[][] Devuelve la Matriz[10][26] completa y rellena con todos los valores posibles de cifrado.
     */
    private char[][] CrearMatrizCifrado() {
        int InicioClave = (int) 'A';
        int DesplazamientoClave = 0;
        int FinalLista = 0; //Si no llega al final de la lista de A a Z, estará en 0, sino pasará a 1 y comenzará a contar desde A.
        //char[] abecedario=new char[26];
        char MatrizCifrado[][] = new char[10][26];
        //char respuesta = 0;

        //for(int i=0;i<abecedario.length;i++){abecedario[i]=(char)(a+i);System.out.println("El caracter es: "+abecedario[i]);}

        for (int FilaMatriz = 0; FilaMatriz < 10; FilaMatriz++) {
            for (int ColumnaMatriz = 0; ColumnaMatriz < 26; ColumnaMatriz++) {
                //int FilaMatriz=8;
                //InicioClave=(int)'X';
                //DesplazamientoClave=3;

                if(FilaMatriz==0) {
                        InicioClave = (int) 'C';
                        DesplazamientoClave = 24;
                }
                if(FilaMatriz==1) {
                        InicioClave = (int) 'D';
                        DesplazamientoClave = 23;
                }
                if(FilaMatriz==2) {
                        InicioClave = (int) 'F';
                        DesplazamientoClave = 21;
                }
                if(FilaMatriz==3) {
                        InicioClave = (int) 'H';
                        DesplazamientoClave = 19;
                }
                if(FilaMatriz==4) {
                        InicioClave = (int) 'L';
                        DesplazamientoClave = 15;
                }
                if(FilaMatriz==5) {
                        InicioClave = (int) 'N';
                        DesplazamientoClave = 13;
                }
                if(FilaMatriz==6) {
                        InicioClave = (int) 'R';
                        DesplazamientoClave = 9;
                }
                if(FilaMatriz==7) {
                        InicioClave = (int) 'T';
                        DesplazamientoClave = 7;
                }
                if(FilaMatriz==8) {
                        InicioClave = (int) 'X';
                        DesplazamientoClave = 3;
                }
                if(FilaMatriz==9) {
                        InicioClave = (int) 'C';
                        DesplazamientoClave = 24;
                }
                //System.out.println("Caso " + FilaMatriz + ", InicioClave=" + InicioClave + ", DesplazamientoClave:" + DesplazamientoClave);

                if (FinalLista == 1) {
                    InicioClave = (int) 'A';
                    MatrizCifrado[FilaMatriz][ColumnaMatriz] = (char) (InicioClave + ColumnaMatriz - DesplazamientoClave);
                } else {
                    MatrizCifrado[FilaMatriz][ColumnaMatriz] = (char) (InicioClave + ColumnaMatriz);
                }
                if ((InicioClave + ColumnaMatriz) == (int) 'Z') {
                    FinalLista = 1;
                    /*System.out.print("FinalLista=1");*/
                }
                //Muestra la tabla de cifrado del Algoritmo Gronsfeld:
                //System.out.print("["+FilaMatriz+"," + MatrizCifrado[FilaMatriz][ColumnaMatriz] + "] ");
            }
            //System.out.println("");
            FinalLista = 0;
        }
        return MatrizCifrado;
    }

    /**
     * Cifra el carácter "LetraACifrar" y lo devuelve el carácter cifrado como char.
     * @param NumeroClave Es el número que le corresponde actualmente de la clave de des/cifrado.
     * @param LetraACifrar Es el carácter que queremos cifrar.
     * @return Char. Nos devuelve en formato char el carácter cifrado.
     */
    private char CifrarCaracter(int NumeroClave,char LetraACifrar){
        char MCifrado[][] = new char[10][26];
        char CaracterCifrado = '#';
        
        //CifradoGronsfeld cifrado=new CifradoGronsfeld();
        MCifrado=this.CrearMatrizCifrado(); //Creo una matriz de cifrado para poder gestionar sus valores.
        
        //Necesito convertir el valor de la LetraACifrar con un valor de 0 a 26. Para ello uso el método ConvertirLetraAPosicion(char)
        CaracterCifrado=MCifrado[NumeroClave][this.ConvertirLetraAPosicion(LetraACifrar)]; //Consulto en MCifrado la posición de Fila(Valor de clave) y columna (Letra a Cifrar).
        
        //Muestro un mensaje para comprobar que todo va bien.
        //System.out.println("CifradoGronsfeld: Numero de la clave: "+NumeroClave+", Caracter a consultar: "+LetraACifrar);
        
        //Devuelvo el carácter cifrado.
        return CaracterCifrado;
    }

    /**
     * Descifra el carácter "LetraADescifrar" y lo devuelve el carácter descifrado como char.
     * @param NumeroClave Es el número que le corresponde actualmente de la clave de des/cifrado.
     * @param LetraADescifrar Es el carácter que queremos descifrar.
     * @return Char. Nos devuelve el formato char el carácter descifrado.
     */
    private char DescifrarCaracter(int NumeroClave,char LetraADescifrar){
        char MCifrado[][] = new char[10][26];
        char CaracterDescifrado = '#';
        int PosicionCaracter=-1;
        
        //Creo la matriz de cifrado.
        MCifrado=this.CrearMatrizCifrado(); //Creo una matriz de cifrado para poder gestionar sus valores.

        //Debo saber la posición que ocupa la clave (filas) y la posición de la letra cifrada para obtener la letra sin cifrar.
        //PosicionCaracter=Arrays.binarySearch(MCifrado[NumeroClave][PosicionCaracter], LetraADescifrar);
        for(int Contador=0;Contador<26;Contador++){
            if(MCifrado[NumeroClave][Contador]==(int)LetraADescifrar) {
                PosicionCaracter=Contador;
                //System.out.println("Carácter encontrado en posición: "+PosicionCaracter);
            }
        }
        //System.out.println("CifradoGronsfeld:DescifrarCaracter; Clave: "+NumeroClave+"-Caracter a descifrar: "+LetraADescifrar+" -Letra Original: "+CaracterDescifrado);
        
        //Una vez que tengo la posición del carácter, sólo tengo que saber que letra corresponde a ese caracter, y la devuelvo con CaracterDescifrado.
        //CaracterDescifrado=cifrado.ConvertirPosiciónALetra(PosicionCaracter);
        CaracterDescifrado=this.ConvertirPosiciónALetra(PosicionCaracter);
        //System.out.println("CifradoGronsfeld:DescifrarCaracter; El caracter descifrado es: "+CaracterDescifrado);
        return CaracterDescifrado;
    }

    /**
     * Teniendo una Letra de entrada, nos da el número de posición que ocupa esa letra en las columnas
     * de la Matriz de Cifrado (de 0 a 26). Útil para cifrar.
     * @param Letra Nos da el número de posición que tiene una letra de la leyenda de las columnas en la matriz de cifrado.
     * @return int. Nos devuelve el número de posición que tiene una letra en la matriz de cifrado. (de 0 a 26).
     */
    private int ConvertirLetraAPosicion(char Letra){
        int PosicionLetra;
        switch (Letra){
            case 'A':
                PosicionLetra=0;
                break;
            case 'B':
                PosicionLetra=1;
                break;
            case 'C':
                PosicionLetra=2;
                break;
            case 'D':
                PosicionLetra=3;
                break;
            case 'E':
                PosicionLetra=4;
                break;
            case 'F':
                PosicionLetra=5;
                break;
            case 'G':
                PosicionLetra=6;
                break;
            case 'H':
                PosicionLetra=7;
                break;
            case 'I':
                PosicionLetra=8;
                break;
            case 'J':
                PosicionLetra=9;
                break;
            case 'K':
                PosicionLetra=10;
                break;
            case 'L':
                PosicionLetra=11;
                break;
            case 'M':
                PosicionLetra=12;
                break;
            case 'N':
                PosicionLetra=13;
                break;
            case 'O':
                PosicionLetra=14;
                break;
            case 'P':
                PosicionLetra=15;
                break;
            case 'Q':
                PosicionLetra=16;
                break;
            case 'R':
                PosicionLetra=17;
                break;
            case 'S':
                PosicionLetra=18;
                break;
            case 'T':
                PosicionLetra=19;
                break;
            case 'U':
                PosicionLetra=20;
                break;
            case 'V':
                PosicionLetra=21;
                break;
            case 'W':
                PosicionLetra=22;
                break;
            case 'X':
                PosicionLetra=23;
                break;
            case 'Y':
                PosicionLetra=24;
                break;
            case 'Z':
                PosicionLetra=25;
                break;
            default:
                PosicionLetra=-1;
                break;
        }
        return PosicionLetra;
    }
    
    /**
     * Teniendo la posición de una letra de la Matriz de Cifrado, nos devuelve que letra corresponde en el nombre de columnas.
     * Útil para descifrar.
     * @param posicion Número de posición que ocupa una letra cifrada en la matriz de cifrado.
     * @return char con la letra que aparece en la leyenda de las columnas correspondiente a la posición de la letra cifrada.
     */
    private char ConvertirPosiciónALetra(int posicion){
        char LetraDescifrada=0;
        switch (posicion){
            case 0:
                LetraDescifrada='A';
                break;
            case 1:
                LetraDescifrada='B';
                break;
            case 2:
                LetraDescifrada='C';
                break;
            case 3:
                LetraDescifrada='D';
                break;
            case 4:
                LetraDescifrada='E';
                break;
            case 5:
                LetraDescifrada='F';
                break;
            case 6:
                LetraDescifrada='G';
                break;
            case 7:
                LetraDescifrada='H';
                break;
            case 8:
                LetraDescifrada='I';
                break;
            case 9:
                LetraDescifrada='J';
                break;
            case 10:
                LetraDescifrada='K';
                break;
            case 11:
                LetraDescifrada='L';
                break;
            case 12:
                LetraDescifrada='M';
                break;
            case 13:
                LetraDescifrada='N';
                break;
            case 14:
                LetraDescifrada='O';
                break;
            case 15:
                LetraDescifrada='P';
                break;
            case 16:
                LetraDescifrada='Q';
                break;
            case 17:
                LetraDescifrada='R';
                break;
            case 18:
                LetraDescifrada='S';
                break;
            case 19:
                LetraDescifrada='T';
                break;
            case 20:
                LetraDescifrada='U';
                break;
            case 21:
                LetraDescifrada='V';
                break;
            case 22:
                LetraDescifrada='W';
                break;
            case 23:
                LetraDescifrada='X';
                break;
            case 24:
                LetraDescifrada='Y';
                break;
            case 25:
                LetraDescifrada='Z';
                break;
            default:
                LetraDescifrada='#';
                break;
        }        
        return LetraDescifrada;
    }

    /**
     * Método que descifra un String que se le da de entrada con la Clave que se le da de entrada. Lo devuelve como String.
     * @param TextoADescifrar String que pretendemos descifrar.
     * @param Clave String con la clave que usaremos para descifrar. Debe ser una clave numérica.
     * @param PosicionClaveDescifrado int para indicar en que posición se encuentra la rotación de la clave de descifrado.
     * @return String con el contenido descifrado.
     */
    public String DescifrarTexto(String TextoADescifrar, String Clave, int PosicionClaveDescifrado){
        //TextoADescifrar=TextoADescifrar.toUpperCase();
        String TextoDescifrado="";
        PosicionClave=PosicionClaveDescifrado;//Que posición de la clave usamos para descifrar el caracter seleccionado.
        
        for(int i=0;i<=TextoADescifrar.length()-1;i++){
            //No desciframos si no se corresponden a los caracteres entre la A-Z. Sólo los anotamos al String de salida.
            if (TextoADescifrar.charAt(i)>'Z'||TextoADescifrar.charAt(i)<'A'){
                //System.out.println("El caracter '"+TextoADescifrar.charAt(i)+"' es mayor que Z y menor que A");
                TextoDescifrado += TextoADescifrar.charAt(i);
            } else{
            //Desciframos porque si se corresponde a los caracteres entre A-Z.
                //System.out.println("El caracter '"+TextoADescifrar.charAt(i)+"' es descifrable... lo desciframos.");
                //System.out.println("Desciframos con el caracter '"+Clave.charAt(PosicionClave)+"' de la clave.");
                
                //Seleccionamos la posición de la clave para que descifre con ese caracter.
                TextoDescifrado += this.DescifrarCaracter(Integer.parseInt(""+Clave.charAt(PosicionClave)), TextoADescifrar.charAt(i));
                if (PosicionClave == Clave.length()-1){
                    PosicionClave=0;
                    this.PosicionClave=PosicionClave;
                    //System.out.println("Ponemos la posición 0 de la clave...");
                } else {
                    PosicionClave++;
                    this.PosicionClave=PosicionClave;
                }
            }
        }
        return TextoDescifrado;
    }
    
    /**
     * Método que cifra un String que se le da de entrada con la Clave que se le da de entrada. Lo devuelve como String.
     * @param TextoACifrar String que pretendemos cifrar.
     * @param Clave String con la clave que usaremos para cifrar. Debe ser una clave numérica.
     * @param PosicionClaveCifrado int Se le pasa la posición que tendrá la clave de cifrado cuando empiece con el párrafo.
     *          Es útil cuando empiece a cifrar Strings de cada línea y evita que empiece con la clave desde el principio
     * @return int con la posición que lleva la clave de cifrado, por si hay que volverla a pasar.
     */
    public String CifrarTexto(String TextoACifrar, String Clave, int PosicionClaveCifrado){
        String TextoEntrada=TextoACifrar.toUpperCase();
        String TextoCifrado="";
        int PosicionClave=PosicionClaveCifrado;//Que posición de la clave usamos para cifrar el caracter seleccionado.
        
        for(int i=0;i<=TextoEntrada.length()-1;i++){
            //No ciframos si no se corresponden a los caracteres entre la A-Z. Sólo los anotamos al String de salida.
            if (TextoEntrada.charAt(i)>'Z'||TextoEntrada.charAt(i)<'A'){
                //System.out.println("El caracter '"+TextoEntrada.charAt(i)+"' es mayor que Z y menor que A");
                TextoCifrado += TextoEntrada.charAt(i);
            } else{
            //Ciframos porque si se corresponde a los caracteres entre A-Z.
                //System.out.println("El caracter '"+TextoEntrada.charAt(i)+"' es cifrable... lo ciframos.");
                //System.out.println("Ciframos con el caracter '"+Clave.charAt(PosicionClave)+"' de la clave.");
                
                //Seleccionamos la posición de la clave para que cifre con ese caracter.
                TextoCifrado += this.CifrarCaracter(Integer.parseInt(""+Clave.charAt(PosicionClave)), TextoEntrada.charAt(i));
                if (PosicionClave == Clave.length()-1){
                    PosicionClave=0;
                    this.PosicionClave=PosicionClave;
                    //System.out.println("Ponemos la posición 0 de la clave...");
                } else {
                    PosicionClave++;
                    this.PosicionClave=PosicionClave;
                }
            }
        }
        return TextoCifrado;
    }
    
    /**
     * Método para probar que el cifrado y descifrado funciona bien...
     */
    public void PruebaGronsfeld(){
        //Variables para la parte 1 de este método.
        int ValorPassword=7;
        char LetraAConsultar='Q';
        char ResultadoCifrado=0;
        char ResultadoDescifrado=0;
        String TextoLeido="#";  
        
        //Variables para la parte 2 de este método.
        String TextoACifrar="seguridad pruEbAs@desEgUri/dad.es||ek12345";
        String TextoCifrado="";
        String TextoDescifradoDespuesDeCifrar="";
        String ClaveCifrado="2587";

        System.out.println("");
        System.out.println(" ------------------------------------------");
        System.out.println(" ---- TESTEO DEL DES-CIFRADO GRONSFELD ----");
        System.out.println(" ------------------------------------------");
        System.out.println("");

        //PARTE 1 DEL MÉTODO
        //Cifrar carácter...
        ResultadoCifrado=this.CifrarCaracter(ValorPassword, LetraAConsultar);
        System.out.println("- PruebaGronsfeld-Cifrado carácter: Letra de entrada: "+LetraAConsultar+", con valor de contraseña: "+ValorPassword+". Letra codificada: "+ResultadoCifrado);
        
        //Descifrar caracter
        ResultadoDescifrado=this.DescifrarCaracter(ValorPassword, LetraAConsultar);  //Me tiene que devolver la X (Fila: 7; columna: Q=24, tiene la letra X en el título de la columna).
        System.out.println("- PruebaGronsfeld-Descifrado carácter; Letra de entrada: "+LetraAConsultar+", con valor de contraseña: "+ValorPassword+". Letra descodificada: "+ResultadoDescifrado);      
        
        //PARTE 2 DEL MÉTODO                        
        CifradoGronsfeld cifrado=new CifradoGronsfeld();
        TextoCifrado=cifrado.CifrarTexto(TextoACifrar,ClaveCifrado, 0);
        System.out.println("- El texto a cifrar es: '"+TextoACifrar+"'");
        System.out.println("     La clave de des-cifrado es: "+ClaveCifrado);
        System.out.println("     El texto cifrado es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, ClaveCifrado, 0);
        System.out.println("     El texto descifrado despues de cifrar es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        //PARTE 3
        System.out.println("- El texto a cifrar es 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.");
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0", 0);
        System.out.println("     - El texto cifrado con clave 0 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "0", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "1", 0);
        System.out.println("     - El texto cifrado con clave 1 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "1", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "2", 0);
        System.out.println("     - El texto cifrado con clave 2 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "2", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "3", 0);
        System.out.println("     - El texto cifrado con clave 3 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "3", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "4", 0);
        System.out.println("     - El texto cifrado con clave 4 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "4", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "5", 0);
        System.out.println("     - El texto cifrado con clave 5 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "5", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "6", 0);
        System.out.println("     - El texto cifrado con clave 6 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "6", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "7", 0);
        System.out.println("     - El texto cifrado con clave 7 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "7", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "8", 0);
        System.out.println("     - El texto cifrado con clave 8 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "8", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "9", 0);
        System.out.println("     - El texto cifrado con clave 9 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "9", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
        
        TextoCifrado=cifrado.CifrarTexto("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789", 0);
        System.out.println("     - El texto cifrado con clave 0123456789 es: '"+TextoCifrado+"'");
        TextoDescifradoDespuesDeCifrar=cifrado.DescifrarTexto(TextoCifrado, "0123456789", 0);
        System.out.println("          El texto descifrado es: '"+TextoDescifradoDespuesDeCifrar+"'");
    }

    /**
     * Obtiene la posición que lleva la clave de cifrado o descifrado en el proceso de cifrado o descifrado.
     * @return int con la posición que llevamos en la clave de cifrado.
     */
    public int getPosicionClave() {
        return PosicionClave;
    }

    /**
     * Configura la posición que lleva la clave de cifrado o descifrado en el proceso de cifrado o descifrado.
     * @param PosicionClave Se le pasa un int con la posición que lleva la clave en ese momento.
     */
    public void setPosicionClave(int PosicionClave) {
        this.PosicionClave = PosicionClave;
    }
}