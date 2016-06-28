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

import java.io.IOException;

/**
 * Practica 2 de Seguridad de la Información. MUI IA. Centro Universitario de Mérida. 2015/2016
 * Clase CifradoSDES. Se realizará todo lo relacionado con el des/cifrado del algoritmo DES Simplificado desde esta clase.
 * @author Javier Merchan (javier@javiermerchan.com)
 * @version 0.1
 */
public class CifradoSDES {
    byte[] K1 = new byte[8]; //K1
    byte[] K2 = new byte[8]; //K2
    private boolean KeyGenerada = false;
    private boolean TrazaActivada;
    private boolean SalidaPantallaSDES = false;
        
    /**
     * Constructor de la clase CifradoSDES.
     */
    public CifradoSDES() {
    }

    /**
     * Método que cifra un carácter de texto en claro a DES Simplificado.
     * @param LetraACifrar Se trata del carácter que queremos cifrar.
     * @return Devuelve el String con el caracter cifrado en 0s y 1s.
     */
    private String CifrarCaracter(char LetraACifrar){
        String CaracterCifrado = "";
        byte[] PlaintextCifrar = new byte[8];
        byte[] Vuelta1SalidaIP = new byte[8];
        byte[] CaracterCifradoEnByte = new byte[8];
        
        //Pasar LetraACifrar de char a la variable PlaintextCifrar[8] en byte[8].
        //*********El caracter de entrada en char lo tenemos que pasar a byte[], convirtiendo cada posición en bits del char
        //********* en una posición de Byte, y debemos introducirlo en la variable PlaintextCifrar[n] para comenzar el cifrado.
        byte LetraACifrarEnByteEntrada= (byte) LetraACifrar;
        String LetraACifrarEnByteSalida = String.format("%8s", Integer.toBinaryString(LetraACifrarEnByteEntrada & 0xFF)).replace(' ', '0');
        //if(TrazaActivada && SalidaPantallaSDES) System.out.println("La variable LetraACifrarEnByteEntrada es: "+LetraACifrarEnByteEntrada+". Y la variable LetraACifrarEnByteSalida es: "+LetraACifrarEnByteSalida);

        PlaintextCifrar=this.ConvertirCadenaAByte(LetraACifrarEnByteSalida);
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n###### Iniciamos cifrado del caracter: "+LetraACifrar+", que corresponde al Plaintext ["+PlaintextCifrar[0]+PlaintextCifrar[1]+PlaintextCifrar[2]+PlaintextCifrar[3]+PlaintextCifrar[4]+PlaintextCifrar[5]+PlaintextCifrar[6]+PlaintextCifrar[7]+"]");
        
        //if(TrazaActivada && SalidaPantallaSDES) System.out.print(" ["+(byte)LetraACifrar+"] ");
        //CONVERTIR LETRAACIFRAR DE CHAR A BYTE[]
        
        //if(TrazaActivada && SalidaPantallaSDES) System.out.print(" --> "+Prueba[0]+Prueba[1]+Prueba[2]+Prueba[3]+Prueba[4]+Prueba[5]+Prueba[6]+Prueba[7]+Prueba[8]+" <-- ");
        
        //ESCRIBIR ALGORITMO CIFRADO.
//        this.IP(PlaintextCifrar, "IzquierdaYDerecha");
//        this.IP(PlaintextCifrar, "Izquierda");
//        this.IP(PlaintextCifrar, "Derecha");
//        this.EP(this.IP(PlaintextCifrar, "Derecha"));
        //Con el EP hacemos un XOR con la clave K1 (para cifrar) o con la K2 (para descifrar). En este caso con K1.
//        this.XOR(this.EP(this.IP(PlaintextCifrar, "Derecha")), K1);
//        this.CajasS0yS1(this.XOR(this.EP(this.IP(PlaintextCifrar, "Derecha")), K1));
//        this.P4(this.CajasS0yS1(this.XOR(this.EP(this.IP(PlaintextCifrar, "Derecha")), K1)));
//        this.XOR2(this.P4(this.CajasS0yS1(this.XOR(this.EP(this.IP(PlaintextCifrar, "Derecha")), K1))), this.IP(PlaintextCifrar, "Izquierda"));
        Vuelta1SalidaIP=this.IP(PlaintextCifrar, "IzquierdaYDerecha");
        CaracterCifradoEnByte=this.PermutacionFinalVuelta2(this.Vuelta(this.PermutacionVuelta1AVuelta2(this.Vuelta(Vuelta1SalidaIP, K1)), K2)); //Cifrado vuelta 1 permutada, vuelta 2
        
        //Una vez obtenido el caracter cifrado en formato byte[], debemos devolverlo, por lo que hay que adaptar la
        //función de origen para que trate de mostrar el caracter cifrado en  formato de 0s y 1s, ej: '10100111'.

        //Convertimos el Byte[] a String.
        for(int i=0; i<8; i++){
            CaracterCifrado+=CaracterCifradoEnByte[i];
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("   => El caracter cifrado es: "+CaracterCifrado+"\r\n");
        return CaracterCifrado;
    }

    /**
     * Método que descifra un carácter de DES Simplificado a texto en claro.
     * @param ChipertextDescifrar Se trata del carácter que queremos descifrar.
     * @return Devuelve el char con el caracter descifrado.
     */
    private char DescifrarCaracter(byte[] ChipertextDescifrar){
        char CaracterDescifrado = '!';
        int CD=0;
        byte[] Vuelta1SalidaIP = new byte[8];
        byte[] CaracterDescifradoEnByte = new byte[8];

        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n###### Iniciamos descifrado del caracter que corresponde al Chipertext ["+ChipertextDescifrar[0]+ChipertextDescifrar[1]+ChipertextDescifrar[2]+ChipertextDescifrar[3]+ChipertextDescifrar[4]+ChipertextDescifrar[5]+ChipertextDescifrar[6]+ChipertextDescifrar[7]+"]");
        Vuelta1SalidaIP=this.IP(ChipertextDescifrar, "IzquierdaYDerecha");
        CaracterDescifradoEnByte=this.PermutacionFinalVuelta2(this.Vuelta(this.PermutacionVuelta1AVuelta2(this.Vuelta(Vuelta1SalidaIP, K2)), K1)); //Cifrado vuelta 1 permutada, vuelta 2
        
        //Convertimos el Byte[] a CHAR.
        //if(TrazaActivada) System.out.println("   => El caracter descifrado es: "+CaracterDescifrado);
        //Pasamos CaracterDescifradoEnByte de byte[8] a la variable CaracterDescifrado en char.
        //******Una vez obtenido el caracter cifrado en formato byte[], debemos devolverlo, por lo que hay que adaptar la
        //******función de origen para que trate de mostrar el caracter descifrado como CHAR.
        //******También hay que ocultar todos los mensajes de cifrado si Traza=off. y ordenarlo todo.
        if (CaracterDescifradoEnByte[0]==1) CD=CD|128;
        if (CaracterDescifradoEnByte[1]==1) CD=CD|64;
        if (CaracterDescifradoEnByte[2]==1) CD=CD|32;
        if (CaracterDescifradoEnByte[3]==1) CD=CD|16;
        if (CaracterDescifradoEnByte[4]==1) CD=CD|8;
        if (CaracterDescifradoEnByte[5]==1) CD=CD|4;
        if (CaracterDescifradoEnByte[6]==1) CD=CD|2;
        if (CaracterDescifradoEnByte[7]==1) CD=CD|1;
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("  --> El carácter descifrado en BINARIO es: "+CaracterDescifradoEnByte[0]+CaracterDescifradoEnByte[1]+CaracterDescifradoEnByte[2]+CaracterDescifradoEnByte[3]+CaracterDescifradoEnByte[4]+CaracterDescifradoEnByte[5]+CaracterDescifradoEnByte[6]+CaracterDescifradoEnByte[7]);
        CaracterDescifrado=(char) CD;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("  --> El carácter descifrado en ASCII es: "+CaracterDescifrado+"\r\n");
        return CaracterDescifrado;
    }

    /**
     * Método que descifra una línea completa que leemos del fichero a Descifrar.
     * @param TextoADescifrar Línea de texto a descifrar.
     * @param Clave Clave de descifrado.
     * @param Traza ¿Tenemos activa la traza?
     * @param SalidaAPantalla ¿Tenemos activa la salida a pantalla?
     * @return Devuelve un String con todo el texto descifrado.
     */
    public String DescifrarTexto(String TextoADescifrar, String Clave, boolean Traza, boolean SalidaAPantalla){
        String TextoDescifrado="";
        int LeerOchoCaracteres=0;
        byte[] CaracterADescifrar = new byte[8];
        char Temporal = 0;
        TrazaActivada=Traza;
        SalidaPantallaSDES=SalidaAPantalla;

        if (KeyGenerada==false){ //Generamos las claves si aún no las hemos generado anteriormente
            this.GeneracionClaves(this.ConvertirClaveAByte(Clave));
            KeyGenerada=true;
        }
        
        for(int i=0;i<=TextoADescifrar.length()-1;i++){ //Vamos leyendo el caracter a caracter el TextoADescifrar y hacemos comprobaciones.
            if(TextoADescifrar.charAt(i)==48||TextoADescifrar.charAt(i)==49){ //Comprobamos que el carácter leido es un 0 o 1.
                if (TextoADescifrar.charAt(i)==49) Temporal=1; //Si encuentro el valor 49 es un 1.
                else if (TextoADescifrar.charAt(i)==48) Temporal=0;  //Si encuentro el valor 48 es un 0.
                CaracterADescifrar[LeerOchoCaracteres]=(byte) Temporal;
                if(LeerOchoCaracteres==7){
                    TextoDescifrado += this.DescifrarCaracter(CaracterADescifrar);
                    LeerOchoCaracteres=0; //He leido los 8 bytes de un carácter, por lo que inicio el siguiente.
                }
                else{
                    LeerOchoCaracteres++; //Aun no he leido 8 caracteres 0s y 1s. Sigo leyendo.
                }
            }
            else{
                i++;//Sumo 1 unidad a la posición de i para informar al usuario en que posición del fichero a descifrar falla el carácter, ya que empieza a contar desde 0 y la gente acostumbra a contar desde 1.
                if(SalidaPantallaSDES) System.out.println("     ATENCIÓN: El carácter a descifrar, de la posición ["+i+"] del fichero a descifrar no contiene 0s y 1s, por lo que no es un texto cifrado con SDES");
                System.exit(-2);
            }
        }
        return TextoDescifrado;
    }

    /**
     * Método que cifra una línea completa que leemos del fichero a Cifrar.
     * @param TextoACifrar Línea de texto a cifrar.
     * @param Clave Clave de cifrado.
     * @param Traza ¿Tenemos activa la traza?
     * @param SalidaAPantalla ¿Tenemos activa la salida a pantalla?
     * @return Devuelve un String con todo el texto cifrado.
     */    
    public String CifrarTexto(String TextoACifrar, String Clave, boolean Traza, boolean SalidaAPantalla){
        String TextoCifrado="";
        TrazaActivada=Traza;
        SalidaPantallaSDES=SalidaAPantalla;

        if (KeyGenerada==false){ //Generamos las claves si aún no las hemos generado anteriormente
            this.GeneracionClaves(this.ConvertirClaveAByte(Clave));
            KeyGenerada=true;
        }
        
        for(int i=0;i<=TextoACifrar.length()-1;i++){
            TextoCifrado += this.CifrarCaracter(TextoACifrar.charAt(i));
        }
        return TextoCifrado;
    }

    /**
     * Metodo para convertir la clave de cifrado de SDES de String a byte[10] y poder usarlo en el código.
     * @param Clave Clave SDES de tamaño 10 caracteres 0s y 1s.
     * @return Byte[10] con un número en cada posición de byte.
     */
    private byte[] ConvertirClaveAByte(String Clave){
        byte[] ClaveEnByte = new byte[10];
        if (Clave.length()==10){
            for (int i=0; i<10; i++){
                ClaveEnByte[i]=(byte)Clave.charAt(i);
            }
            
            for (int i=0; i<10; i++){
                if (ClaveEnByte[i]==49) ClaveEnByte[i]=1;
                else if (ClaveEnByte[i]==48) ClaveEnByte[i]=0;
                else{
                    if(SalidaPantallaSDES) System.out.println("     La clave contiene caracteres diferentes al 0 o al 1, por lo que es incorrecta. Revisela...");
                    System.exit(-2);
                }
            }
        }
        else{
            if(SalidaPantallaSDES) System.out.println("     La longitud de la clave es diferente a 10 bits. Salimos del programa.");
            System.exit(-2);
        }
        return ClaveEnByte;
    }

    /**
     * Método para convertir un String a byte[8]
     * @param Cadena Cadena de entrada para convertir a byte[8]
     * @return Byte[8] con el valor del String en cada uno de las posiciones del byte[].
     */
    private byte[] ConvertirCadenaAByte(String Cadena){
        byte[] CadenaAByte = new byte[8];
        if (Cadena.length()==8){
            for (int i=0; i<8; i++){
                CadenaAByte[i]=(byte)Cadena.charAt(i);
            }
            
            for (int i=0; i<8; i++){
                if (CadenaAByte[i]==49) CadenaAByte[i]=1;
                else if (CadenaAByte[i]==48) CadenaAByte[i]=0;
                else{
                    if(SalidaPantallaSDES) System.out.println("     La cadena contiene caracteres diferentes al 0 o al 1, por lo que es incorrecta. Revisela...");
                    System.exit(-2);
                }
            }
        }
        else{
            if(SalidaPantallaSDES) System.out.println("     La longitud de la cadena es diferente a 8 bits. Salimos del programa.");
            System.exit(-2);
        }
        return CadenaAByte;
    }
    
    /**
     * Método para generar las claves de cifrado y descifrado.
     * @param Entrada Cadena de entrada para generar las claves.
     */
    private void GeneracionClaves(byte[] Entrada){
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("###### Comenzamos a generar las claves...");
        K1=P8(LS1(P10(Entrada),"IzquierdaYDerecha"));
        K2=P8(LS2(LS1(P10(Entrada),"IzquierdaYDerecha"),"IzquierdaYDerecha"));
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("   -La clave K1 generada es: "+K1[0]+K1[1]+K1[2]+K1[3]+K1[4]+K1[5]+K1[6]+K1[7]);
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("   -La clave K2 generada es: "+K2[0]+K2[1]+K2[2]+K2[3]+K2[4]+K2[5]+K2[6]+K2[7]);
    }

    /**
     * Método para el cálculo de módulo P10
     * @param ClaveDesCifrado Introducimos la clave de cifrado o descifrado, según procesa
     * @return  Devuelve un byte[10] para pasarlo al siguiente módulo.
     */
    private byte[] P10(byte[] ClaveDesCifrado){ //Permutación P10
        byte[] P10temporal = new byte[10];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada P10: Bit0["+ClaveDesCifrado[0]+"], Bit1["+ClaveDesCifrado[1]+"], Bit2["+ClaveDesCifrado[2]+"], Bit3["+ClaveDesCifrado[3]+"], Bit4["+ClaveDesCifrado[4]+"], Bit5["+ClaveDesCifrado[5]+"], Bit6["+ClaveDesCifrado[6]+"], Bit7["+ClaveDesCifrado[7]+"], Bit8["+ClaveDesCifrado[8]+"], Bit9["+ClaveDesCifrado[9]+"]");
        P10temporal[6] = ClaveDesCifrado[0];
        P10temporal[2] = ClaveDesCifrado[1];
        P10temporal[0] = ClaveDesCifrado[2];
        P10temporal[4] = ClaveDesCifrado[3];
        P10temporal[1] = ClaveDesCifrado[4];
        P10temporal[9] = ClaveDesCifrado[5];
        P10temporal[3] = ClaveDesCifrado[6];
        P10temporal[8] = ClaveDesCifrado[7];
        P10temporal[7] = ClaveDesCifrado[8];
        P10temporal[5] = ClaveDesCifrado[9];
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida P10: Bit0["+P10temporal[0]+"], Bit1["+P10temporal[1]+"], Bit2["+P10temporal[2]+"], Bit3["+P10temporal[3]+"], Bit4["+P10temporal[4]+"], Bit5["+P10temporal[5]+"], Bit6["+P10temporal[6]+"], Bit7["+P10temporal[7]+"], Bit8["+P10temporal[8]+"], Bit9["+P10temporal[9]+"]");
        
        return P10temporal;
    }
    
    /**
     * Método para el cálculo de P8.
     * @param LS1oLS2 Entra la permutación LS1 o LS2.
     * @return Devuelve P8, un byte[8].
     */
    private byte[] P8(byte[] LS1oLS2){ //Permutación LS1 o LS2
        byte[] P8temporal = new byte[8];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada P8: Bit0["+LS1oLS2[0]+"], Bit1["+LS1oLS2[1]+"], Bit2["+LS1oLS2[2]+"], Bit3["+LS1oLS2[3]+"], Bit4["+LS1oLS2[4]+"], Bit5["+LS1oLS2[5]+"], Bit6["+LS1oLS2[6]+"], Bit7["+LS1oLS2[7]+"], Bit8["+LS1oLS2[8]+"], Bit9["+LS1oLS2[9]+"]");
        P8temporal[1] = LS1oLS2[2];
        P8temporal[3] = LS1oLS2[3];
        P8temporal[5] = LS1oLS2[4];
        P8temporal[0] = LS1oLS2[5];
        P8temporal[2] = LS1oLS2[6];
        P8temporal[4] = LS1oLS2[7];
        P8temporal[7] = LS1oLS2[8];
        P8temporal[6] = LS1oLS2[9];
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida P8: Bit0["+P8temporal[0]+"], Bit1["+P8temporal[1]+"], Bit2["+P8temporal[2]+"], Bit3["+P8temporal[3]+"], Bit4["+P8temporal[4]+"], Bit5["+P8temporal[5]+"], Bit6["+P8temporal[6]+"], Bit7["+P8temporal[7]+"]");
        
        return P8temporal;
    }

    /**
     * Método para el cálculo de LS1
     * @param P10entrada Entrada de P10 que se trata de un byte[10].
     * @param IzquierdaODerecha Seleccionamos los 5 bytes de la parte izquierda o derecha de la salida LS1
     * @return Devuelve LS1 completo o sólo la parte izquierda o derecha, según selecionemos en "IzquierdaODerecha".
     */
    private byte[] LS1(byte[] P10entrada, String IzquierdaODerecha){ //Rotar a izquierda 1 posición
        byte[] LS1IzqTemporal = new byte[5]; //Parte Izquierda de LS1
        byte[] LS1DerTemporal = new byte[5]; //Parte Derecha de LS1
        byte[] LS1IzqYDerTemporal = new byte[10]; //LS1
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada P10: Bit0["+P10entrada[0]+"], Bit1["+P10entrada[1]+"], Bit2["+P10entrada[2]+"], Bit3["+P10entrada[3]+"], Bit4["+P10entrada[4]+"], Bit5["+P10entrada[5]+"], Bit6["+P10entrada[6]+"], Bit7["+P10entrada[7]+"], Bit8["+P10entrada[8]+"], Bit9["+P10entrada[9]+"]");
        //Preparamos parte izquierda en el mismo array.
        LS1IzqTemporal[4] = P10entrada[0];
        LS1IzqTemporal[0] = P10entrada[1];
        LS1IzqTemporal[1] = P10entrada[2];
        LS1IzqTemporal[2] = P10entrada[3];
        LS1IzqTemporal[3] = P10entrada[4];
        
        //Preparamos parte derecha en el mismo array.
        LS1DerTemporal[4] = P10entrada[5];
        LS1DerTemporal[0] = P10entrada[6];
        LS1DerTemporal[1] = P10entrada[7];
        LS1DerTemporal[2] = P10entrada[8];
        LS1DerTemporal[3] = P10entrada[9];
        
        //Preparamos tanto izquierda y derecha en el mismo array.
        LS1IzqYDerTemporal[0] = LS1IzqTemporal[0];
        LS1IzqYDerTemporal[1] = LS1IzqTemporal[1];
        LS1IzqYDerTemporal[2] = LS1IzqTemporal[2];
        LS1IzqYDerTemporal[3] = LS1IzqTemporal[3];
        LS1IzqYDerTemporal[4] = LS1IzqTemporal[4];
        LS1IzqYDerTemporal[5] = LS1DerTemporal[0];
        LS1IzqYDerTemporal[6] = LS1DerTemporal[1];
        LS1IzqYDerTemporal[7] = LS1DerTemporal[2];
        LS1IzqYDerTemporal[8] = LS1DerTemporal[3];
        LS1IzqYDerTemporal[9] = LS1DerTemporal[4];

        if (IzquierdaODerecha=="Izquierda"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS1 izq: Bit0["+LS1IzqTemporal[0]+"], Bit1["+LS1IzqTemporal[1]+"], Bit2["+LS1IzqTemporal[2]+"], Bit3["+LS1IzqTemporal[3]+"], Bit4["+LS1IzqTemporal[4]+"]");
            return LS1IzqTemporal;
        }
        if (IzquierdaODerecha=="Derecha"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS1 der: Bit0["+LS1DerTemporal[0]+"], Bit1["+LS1DerTemporal[1]+"], Bit2["+LS1DerTemporal[2]+"], Bit3["+LS1DerTemporal[3]+"], Bit4["+LS1DerTemporal[4]+"]");
            return LS1DerTemporal;
        }
        if (IzquierdaODerecha=="IzquierdaYDerecha"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS1 izq y der: Bit0["+LS1IzqYDerTemporal[0]+"], Bit1["+LS1IzqYDerTemporal[1]+"], Bit2["+LS1IzqYDerTemporal[2]+"], Bit3["+LS1IzqYDerTemporal[3]+"], Bit4["+LS1IzqYDerTemporal[4]+"], Bit5["+LS1IzqYDerTemporal[5]+"], Bit6["+LS1IzqYDerTemporal[6]+"], Bit7["+LS1IzqYDerTemporal[7]+"], Bit8["+LS1IzqYDerTemporal[8]+"], Bit9["+LS1IzqYDerTemporal[9]+"]");
            return LS1IzqYDerTemporal;
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS1 izq y der (Fuera if): Bit0["+LS1IzqYDerTemporal[0]+"], Bit1["+LS1IzqYDerTemporal[1]+"], Bit2["+LS1IzqYDerTemporal[2]+"], Bit3["+LS1IzqYDerTemporal[3]+"], Bit4["+LS1IzqYDerTemporal[4]+"], Bit5["+LS1IzqYDerTemporal[5]+"], Bit6["+LS1IzqYDerTemporal[6]+"], Bit7["+LS1IzqYDerTemporal[7]+"], Bit8["+LS1IzqYDerTemporal[8]+"], Bit9["+LS1IzqYDerTemporal[9]+"]");
        return LS1IzqYDerTemporal; //Si no entra en ningún if, retorna las dos partes (izquierda y derecha).
    }

    /**
     * Metodo para calcular LS2, es decir, rota LS1 dos posiciones a la izquierda
     * @param LS1entrada Entrada de LS1 en Byte[10].
     * @param IzquierdaODerecha Seleccionamos si queremos obtener la parte izquierda, derecha o el conjunto completo de LS2
     * @return Obtenemos LS2 o una de sus partes (izquierda o derecha), según convenga.
     */
    private byte[] LS2(byte[] LS1entrada, String IzquierdaODerecha){ //Rotar LS1 2 posiciones a la izquierda obteniendo LS2
        byte[] LS2IzqTemporal = new byte[5]; //Parte Izquierda de LS2
        byte[] LS2DerTemporal = new byte[5]; //Parte Derecha de LS2
        byte[] LS2IzqYDerTemporal = new byte[10]; //LS2 completo
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada LS1: Bit0["+LS1entrada[0]+"], Bit1["+LS1entrada[1]+"], Bit2["+LS1entrada[2]+"], Bit3["+LS1entrada[3]+"], Bit4["+LS1entrada[4]+"], Bit5["+LS1entrada[5]+"], Bit6["+LS1entrada[6]+"], Bit7["+LS1entrada[7]+"], Bit8["+LS1entrada[8]+"], Bit9["+LS1entrada[9]+"]");
        //Preparamos parte izquierda en el mismo array.
        LS2IzqTemporal[3] = LS1entrada[0];
        LS2IzqTemporal[4] = LS1entrada[1];
        LS2IzqTemporal[0] = LS1entrada[2];
        LS2IzqTemporal[1] = LS1entrada[3];
        LS2IzqTemporal[2] = LS1entrada[4];
        
        //Preparamos parte derecha en el mismo array.
        LS2DerTemporal[3] = LS1entrada[5];
        LS2DerTemporal[4] = LS1entrada[6];
        LS2DerTemporal[0] = LS1entrada[7];
        LS2DerTemporal[1] = LS1entrada[8];
        LS2DerTemporal[2] = LS1entrada[9];
        
        //Preparamos tanto izquierda y derecha en el mismo array.
        LS2IzqYDerTemporal[0] = LS2IzqTemporal[0];
        LS2IzqYDerTemporal[1] = LS2IzqTemporal[1];
        LS2IzqYDerTemporal[2] = LS2IzqTemporal[2];
        LS2IzqYDerTemporal[3] = LS2IzqTemporal[3];
        LS2IzqYDerTemporal[4] = LS2IzqTemporal[4];
        LS2IzqYDerTemporal[5] = LS2DerTemporal[0];
        LS2IzqYDerTemporal[6] = LS2DerTemporal[1];
        LS2IzqYDerTemporal[7] = LS2DerTemporal[2];
        LS2IzqYDerTemporal[8] = LS2DerTemporal[3];
        LS2IzqYDerTemporal[9] = LS2DerTemporal[4];

        if (IzquierdaODerecha=="Izquierda"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS2 izq: Bit0["+LS2IzqTemporal[0]+"], Bit1["+LS2IzqTemporal[1]+"], Bit2["+LS2IzqTemporal[2]+"], Bit3["+LS2IzqTemporal[3]+"], Bit4["+LS2IzqTemporal[4]+"]");
            return LS2IzqTemporal;
        }
        if (IzquierdaODerecha=="Derecha"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS2 der: Bit0["+LS2DerTemporal[0]+"], Bit1["+LS2DerTemporal[1]+"], Bit2["+LS2DerTemporal[2]+"], Bit3["+LS2DerTemporal[3]+"], Bit4["+LS2DerTemporal[4]+"]");
            return LS2DerTemporal;
        }
        if (IzquierdaODerecha=="IzquierdaYDerecha"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS2 izq y der: Bit0["+LS2IzqYDerTemporal[0]+"], Bit1["+LS2IzqYDerTemporal[1]+"], Bit2["+LS2IzqYDerTemporal[2]+"], Bit3["+LS2IzqYDerTemporal[3]+"], Bit4["+LS2IzqYDerTemporal[4]+"], Bit5["+LS2IzqYDerTemporal[5]+"], Bit6["+LS2IzqYDerTemporal[6]+"], Bit7["+LS2IzqYDerTemporal[7]+"], Bit8["+LS2IzqYDerTemporal[8]+"], Bit9["+LS2IzqYDerTemporal[9]+"]");
            return LS2IzqYDerTemporal;
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida LS2 izq y der (Fuera if): Bit0["+LS2IzqYDerTemporal[0]+"], Bit1["+LS2IzqYDerTemporal[1]+"], Bit2["+LS2IzqYDerTemporal[2]+"], Bit3["+LS2IzqYDerTemporal[3]+"], Bit4`["+LS2IzqYDerTemporal[4]+"], Bit5["+LS2IzqYDerTemporal[5]+"], Bit6["+LS2IzqYDerTemporal[6]+"], Bit7["+LS2IzqYDerTemporal[7]+"], Bit8["+LS2IzqYDerTemporal[8]+"], Bit9["+LS2IzqYDerTemporal[9]+"]");
        return LS2IzqYDerTemporal; //Si no entra en ningún if, retorna las dos partes (izquierda y derecha).
    }
    
    /**
     * Método para obtener IP
     * @param PlaintextEntrada Entramos en byte[8] el Plaintext a tratar
     * @param IzquierdaODerecha Seleccionamos si queremos obtener la parte izquierda, derecha o el conjunto completo de IP.
     * @return Obtenemos IP o una de sus partes (izquierda o derecha), según convenga.
     */
    private byte[] IP(byte[] PlaintextEntrada, String IzquierdaODerecha){
        byte[] IPIzqTemporal = new byte[5]; //Parte Izquierda de IP
        byte[] IPDerTemporal = new byte[5]; //Parte Derecha de IP
        byte[] IPIzqYDerTemporal = new byte[10]; //IP completo
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("   -Entrada Plaintext: Bit0["+PlaintextEntrada[0]+"], Bit1["+PlaintextEntrada[1]+"], Bit2["+PlaintextEntrada[2]+"], Bit3["+PlaintextEntrada[3]+"], Bit4["+PlaintextEntrada[4]+"], Bit5["+PlaintextEntrada[5]+"], Bit6["+PlaintextEntrada[6]+"], Bit7["+PlaintextEntrada[7]+"]");
        //Preparamos parte izquierda en el mismo array.
        IPIzqTemporal[0] = PlaintextEntrada[1];
        IPIzqTemporal[1] = PlaintextEntrada[5];
        IPIzqTemporal[2] = PlaintextEntrada[2];
        IPIzqTemporal[3] = PlaintextEntrada[0];
        
        //Preparamos parte derecha en el mismo array.
        IPDerTemporal[0] = PlaintextEntrada[3];
        IPDerTemporal[1] = PlaintextEntrada[7];
        IPDerTemporal[2] = PlaintextEntrada[4];
        IPDerTemporal[3] = PlaintextEntrada[6];
        
        //Preparamos tanto izquierda y derecha en el mismo array.
        IPIzqYDerTemporal[0] = IPIzqTemporal[0];
        IPIzqYDerTemporal[1] = IPIzqTemporal[1];
        IPIzqYDerTemporal[2] = IPIzqTemporal[2];
        IPIzqYDerTemporal[3] = IPIzqTemporal[3];
        IPIzqYDerTemporal[4] = IPDerTemporal[0];
        IPIzqYDerTemporal[5] = IPDerTemporal[1];
        IPIzqYDerTemporal[6] = IPDerTemporal[2];
        IPIzqYDerTemporal[7] = IPDerTemporal[3];

        if (IzquierdaODerecha=="Izquierda"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("     Salida IP izq: Bit0["+IPIzqTemporal[0]+"], Bit1["+IPIzqTemporal[1]+"], Bit2["+IPIzqTemporal[2]+"], Bit3["+IPIzqTemporal[3]+"]");
            return IPIzqTemporal;
        }
        if (IzquierdaODerecha=="Derecha"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("     Salida IP der: Bit0["+IPDerTemporal[0]+"], Bit1["+IPDerTemporal[1]+"], Bit2["+IPDerTemporal[2]+"], Bit3["+IPDerTemporal[3]+"]");
            return IPDerTemporal;
        }
        if (IzquierdaODerecha=="IzquierdaYDerecha"){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("     Salida IP izq y der: Bit0["+IPIzqYDerTemporal[0]+"], Bit1["+IPIzqYDerTemporal[1]+"], Bit2["+IPIzqYDerTemporal[2]+"], Bit3["+IPIzqYDerTemporal[3]+"], Bit4["+IPIzqYDerTemporal[4]+"], Bit5["+IPIzqYDerTemporal[5]+"], Bit6["+IPIzqYDerTemporal[6]+"], Bit7["+IPIzqYDerTemporal[7]+"]");
            return IPIzqYDerTemporal;
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     Salida IP izq y der (Fuera if): Bit0["+IPIzqYDerTemporal[0]+"], Bit1["+IPIzqYDerTemporal[1]+"], Bit2["+IPIzqYDerTemporal[2]+"], Bit3["+IPIzqYDerTemporal[3]+"], Bit4`["+IPIzqYDerTemporal[4]+"], Bit5["+IPIzqYDerTemporal[5]+"], Bit6["+IPIzqYDerTemporal[6]+"], Bit7["+IPIzqYDerTemporal[7]+"]");
        return IPIzqYDerTemporal; //Si no entra en ningún if, retorna las dos partes (izquierda y derecha).
    }

    /**
     * Método para calcula EP.
     * @param IPderecha Introducimos como parámetro la parte derecha de IP.
     * @return Obtenemos EP en byte[8].
     */
    private byte[] EP(byte[] IPderecha){ //Entra IP derecha y sale EP
        byte[] EPtemporal = new byte[8];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada IP derecha o Permutador 1 vuelta derecha: Bit0["+IPderecha[0]+"], Bit1["+IPderecha[1]+"], Bit2["+IPderecha[2]+"], Bit3["+IPderecha[3]+"]");
        EPtemporal[0] = IPderecha[3];
        EPtemporal[1] = IPderecha[0];
        EPtemporal[2] = IPderecha[1];
        EPtemporal[3] = IPderecha[2];
        EPtemporal[4] = IPderecha[1];
        EPtemporal[5] = IPderecha[2];
        EPtemporal[6] = IPderecha[3];
        EPtemporal[7] = IPderecha[0];
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida EP: Bit0["+EPtemporal[0]+"], Bit1["+EPtemporal[1]+"], Bit2["+EPtemporal[2]+"], Bit3["+EPtemporal[3]+"], Bit4["+EPtemporal[4]+"], Bit5["+EPtemporal[5]+"], Bit6["+EPtemporal[6]+"], Bit7["+EPtemporal[7]+"]");
        
        return EPtemporal;
    }
    
    /**
     * Método para hacer XOR con dos byte[].
     * @param EP Introducimos EP completo en byte[8].
     * @param ClaveK1oK2 Introducimos la Key1 o Key2 según proceda.
     * @return Devolvemos el resultado del XOR en un byte[8].
     */
    private byte[] XOR(byte[] EP, byte[] ClaveK1oK2){ //Entra EP y hacemos XOR con K1 o K2
        byte[] XORtemporal = new byte[8];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada EP para hacer XOR: Bit0["+EP[0]+"], Bit1["+EP[1]+"], Bit2["+EP[2]+"], Bit3["+EP[3]+"], Bit4["+EP[4]+"], Bit5["+EP[5]+"], Bit6["+EP[6]+"], Bit7["+EP[7]+"]");
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Clave de entrada hacer XOR: Bit0["+ClaveK1oK2[0]+"], Bit1["+ClaveK1oK2[1]+"], Bit2["+ClaveK1oK2[2]+"], Bit3["+ClaveK1oK2[3]+"], Bit4["+ClaveK1oK2[4]+"], Bit5["+ClaveK1oK2[5]+"], Bit6["+ClaveK1oK2[6]+"], Bit7["+ClaveK1oK2[7]+"]");
        for(int i=0; i<XORtemporal.length; i++){
            XORtemporal[i] = (byte) (EP[i]^ClaveK1oK2[i]);
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("        Salida XOR: Bit0["+XORtemporal[0]+"], Bit1["+XORtemporal[1]+"], Bit2["+XORtemporal[2]+"], Bit3["+XORtemporal[3]+"], Bit4["+XORtemporal[4]+"], Bit5["+XORtemporal[5]+"], Bit6["+XORtemporal[6]+"], Bit7["+XORtemporal[7]+"]");
        
        return XORtemporal;
    }
    
    /**
     * Método para obtener los valores de las cajas S0 y S1.
     * @param XOR Introducimos el resultado de la operación XOR.
     * @return Devolvemos el valor de las cajar, obtenido después de tratar el XOR introducido.
     */
    private byte[] CajasS0yS1(byte[] XOR){
        byte CajaS0[][] = new byte[4][4]; //Creamos la caja S0
        byte CajaS1[][] = new byte[4][4]; //Creamos la caja S1
        byte SalidaCajas[] = new byte[4]; //Creamos la salida de S0 y S1 juntos
        int PosicionFilasS0=-1;
        int PosicionColumnasS0=-1;
        int PosicionFilasS1=-1;
        int PosicionColumnasS1=-1;
        int S0=-1;
        int S1=-1;
        
        try{
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Inicio cajas S0 y S1");
            //Rellenamos la caja S0
            CajaS0[0][0]=1; CajaS0[0][1]=0; CajaS0[0][2]=3; CajaS0[0][3]=2;
            CajaS0[1][0]=3; CajaS0[1][1]=2; CajaS0[1][2]=1; CajaS0[1][3]=0;
            CajaS0[2][0]=0; CajaS0[2][1]=2; CajaS0[2][2]=1; CajaS0[2][3]=3;
            CajaS0[3][0]=3; CajaS0[3][1]=1; CajaS0[3][2]=3; CajaS0[3][3]=2;
            //Rellenamos la caja S1
            CajaS1[0][0]=0; CajaS1[0][1]=1; CajaS1[0][2]=2; CajaS1[0][3]=3;
            CajaS1[1][0]=2; CajaS1[1][1]=0; CajaS1[1][2]=1; CajaS1[1][3]=3;
            CajaS1[2][0]=3; CajaS1[2][1]=0; CajaS1[2][2]=1; CajaS1[2][3]=0;
            CajaS1[3][0]=2; CajaS1[3][1]=1; CajaS1[3][2]=0; CajaS1[3][3]=3;

//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[0]="+XOR[0]);
//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[1]="+XOR[1]);
//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[2]="+XOR[2]);
//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[3]="+XOR[3]);
//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[4]="+XOR[4]);
//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[5]="+XOR[5]);
//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[6]="+XOR[6]);
//            if(TrazaActivada && SalidaPantallaSDES) System.out.println("El valor de XOR[7]="+XOR[7]);
            
            if (XOR[0]==0&&XOR[3]==0) PosicionFilasS0=0;
            if (XOR[0]==0&&XOR[3]==1) PosicionFilasS0=1;
            if (XOR[0]==1&&XOR[3]==0) PosicionFilasS0=2;
            if (XOR[0]==1&&XOR[3]==1) PosicionFilasS0=3;

            if (XOR[1]==0&&XOR[2]==0) PosicionColumnasS0=0;
            if (XOR[1]==0&&XOR[2]==1) PosicionColumnasS0=1;
            if (XOR[1]==1&&XOR[2]==0) PosicionColumnasS0=2;
            if (XOR[1]==1&&XOR[2]==1) PosicionColumnasS0=3;

            if (XOR[4]==0&&XOR[7]==0) PosicionFilasS1=0;
            if (XOR[4]==0&&XOR[7]==1) PosicionFilasS1=1;
            if (XOR[4]==1&&XOR[7]==0) PosicionFilasS1=2;
            if (XOR[4]==1&&XOR[7]==1) PosicionFilasS1=3;

            if (XOR[5]==0&&XOR[6]==0) PosicionColumnasS1=0;
            if (XOR[5]==0&&XOR[6]==1) PosicionColumnasS1=1;
            if (XOR[5]==1&&XOR[6]==0) PosicionColumnasS1=2;
            if (XOR[5]==1&&XOR[6]==1) PosicionColumnasS1=3;

            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      -La posición de la Fila S0 es: "+PosicionFilasS0);
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("       La posición de la Columna S0 es: "+PosicionColumnasS0);
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      -La posición de la Fila S1 es: "+PosicionFilasS1);
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("       La posición de la Columna S1 es: "+PosicionColumnasS1);

            S0=CajaS0[PosicionFilasS0][PosicionColumnasS0];
            if (S0==0){
                SalidaCajas[0]=0;
                SalidaCajas[1]=0;
            }
            if (S0==1){
                SalidaCajas[0]=0;
                SalidaCajas[1]=1;
            }
            if (S0==2){
                SalidaCajas[0]=1;
                SalidaCajas[1]=0;
            }
            if (S0==3){
                SalidaCajas[0]=1;
                SalidaCajas[1]=1;
            }

            S1=CajaS1[PosicionFilasS1][PosicionColumnasS1];
            if (S1==0){
                SalidaCajas[2]=0;
                SalidaCajas[3]=0;
            }
            if (S1==1){
                SalidaCajas[2]=0;
                SalidaCajas[3]=1;
            }
            if (S1==2){
                SalidaCajas[2]=1;
                SalidaCajas[3]=0;
            }
            if (S1==3){
                SalidaCajas[2]=1;
                SalidaCajas[3]=1;
            }
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("      La salida de la caja S0 es ["+SalidaCajas[0]+SalidaCajas[1]+"] y la salida de la caja S1 es ["+SalidaCajas[2]+SalidaCajas[3]+"]");
        }
        catch (Exception ex){
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Se ha producido un error al generar el resultado de las cajas S0 y S1");
        }
        return SalidaCajas;
    }

    /**
     * Método para cacular el valor de P4
     * @param SalidaCajas Introducimos la salida de las cajas como entrada de este método.
     * @return Obtenemos el valor de P4 como byte[8].
     */
    private byte[] P4(byte[] SalidaCajas){ //Entra SalidaCajas y sale P4
        byte[] P4temporal = new byte[8];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada SalidaCajas: Bit0["+SalidaCajas[0]+"], Bit1["+SalidaCajas[1]+"], Bit2["+SalidaCajas[2]+"], Bit3["+SalidaCajas[3]+"]");
        P4temporal[3] = SalidaCajas[0];
        P4temporal[0] = SalidaCajas[1];
        P4temporal[2] = SalidaCajas[2];
        P4temporal[1] = SalidaCajas[3];
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      Salida P4: Bit0["+P4temporal[0]+"], Bit1["+P4temporal[1]+"], Bit2["+P4temporal[2]+"], Bit3["+P4temporal[3]+"]");
        
        return P4temporal;
    }

    /**
     * Método para hacer un segundo XOR
     * @param P4 Introducimos P4 como byte[4].
     * @param IPIzq Introducimos IP Izquierda como byte[4].
     * @return Devolvemos el resultado del XOR como byte[4].
     */
    private byte[] XOR2(byte[] P4, byte[] IPIzq){ //Entra P4 y hacemos XOR con IP Izquierda.
        byte[] XOR2temporal = new byte[4];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Entrada P4 para hacer XOR: Bit0["+P4[0]+"], Bit1["+P4[1]+"], Bit2["+P4[2]+"], Bit3["+P4[3]+"]");
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("      IP Izquierda hacer XOR: Bit0["+IPIzq[0]+"], Bit1["+IPIzq[1]+"], Bit2["+IPIzq[2]+"], Bit3["+IPIzq[3]+"]");
        for(int i=0; i<XOR2temporal.length; i++){
            XOR2temporal[i] = (byte) (P4[i]^IPIzq[i]);
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("       Salida XOR2: Bit0["+XOR2temporal[0]+"], Bit1["+XOR2temporal[1]+"], Bit2["+XOR2temporal[2]+"], Bit3["+XOR2temporal[3]+"]");
        
        return XOR2temporal;
    }

    /**
     * Método que realiza los pasos comunes a toda vuelta de la codificación DES Simplificado.
     * @param IPoSalidaPrimeraVuelta Introducimos IP si es la primera vuelta o SalidaPrimeraVuelta si es la segunda vuelta.
     * @param K1oK2 Introducimos la Key1 o Key2 según proceda.
     * @return Obtendremos la salida de la primera o segunda vuelta, según proceda.
     */
    private byte[] Vuelta(byte[] IPoSalidaPrimeraVuelta, byte[] K1oK2){ //Entra (Salida IP o salida Permutación 1ª vuelta) y sale vuelta 1 con inversión.
        byte[] UnPaso = new byte[8];
        byte[] XOR2ALaIzquierda = new byte[4];
        byte[] Derecha = new byte[4];
        byte[] Izquierda = new byte[4];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("   -Iniciamos una Vuelta");
        //Cogemos IP o la Salida de la permutación de la 1ª vuelta y los separamos. Primeros 4 bits a la izquierda. Ultimos 4 bits a la derecha.
        for (int i=0; i<4; i++){
            Izquierda[i]=IPoSalidaPrimeraVuelta[i]; //if(TrazaActivada) System.out.println("Asigno a Izquieda: "+Izquierda[i]);
            Derecha[i]=IPoSalidaPrimeraVuelta[4+i]; //if(TrazaActivada) System.out.println("Asigno a Derecha: "+Derecha[i]);
        }
        
        XOR2ALaIzquierda=this.XOR2(this.P4(this.CajasS0yS1(this.XOR(this.EP(Derecha), K1oK2))), Izquierda);
        
        //4 bits de XOR a la izquierda y 4 bits de IP derecha.
        //Salida de la vuelta.
        UnPaso[0]=XOR2ALaIzquierda[0];
        UnPaso[1]=XOR2ALaIzquierda[1];
        UnPaso[2]=XOR2ALaIzquierda[2];
        UnPaso[3]=XOR2ALaIzquierda[3];
        UnPaso[4]=Derecha[0];
        UnPaso[5]=Derecha[1];
        UnPaso[6]=Derecha[2];
        UnPaso[7]=Derecha[3];
        
        return UnPaso;
    }

    /**
     * Método para realizar la permutación de bits (bytes en nuestro caso) del paso 1 al paso 2 del cifrado DES Simplificado.
     * @param PrimerPaso Introducimos la salida del primer paso.
     * @return Obtenemos la salida permutada para poder iniciar el segundo paso en formato byte[8].
     */
    private byte[] PermutacionVuelta1AVuelta2(byte[] PrimerPaso){ //Entra PrimerPaso y sale PrimerPasoPermutado.
        byte[] Permutado = new byte[8];
        
        //Permutamos (SWITCH) la salida del primer paso para prepararla para el segundo paso.
        Permutado[0]=PrimerPaso[4];
        Permutado[1]=PrimerPaso[5];
        Permutado[2]=PrimerPaso[6];
        Permutado[3]=PrimerPaso[7];
        Permutado[4]=PrimerPaso[0];
        Permutado[5]=PrimerPaso[1];
        Permutado[6]=PrimerPaso[2];
        Permutado[7]=PrimerPaso[3];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Salida Primer Paso Permutado: Bit0["+Permutado[0]+"], Bit1["+Permutado[1]+"], Bit2["+Permutado[2]+"], Bit3["+Permutado[3]+"], Bit4["+Permutado[4]+"], Bit5["+Permutado[5]+"], Bit6["+Permutado[6]+"], Bit7["+Permutado[7]+"]");
        
        return Permutado;
    }

    /**
     * Método para realizar la permutación final de las 2 vueltas del sistema DES simplificado.
     * @param SegundoPaso Entramos como parámetro, la salida del segundo paso.
     * @return Obtenemos la salida permutada del segundo paso de las vueltas del cifrado DES Simplificado.
     */
    private byte[] PermutacionFinalVuelta2(byte[] SegundoPaso){ //Entra SegundoPaso y sale Final2vueltas permutado.
        byte[] Permutado = new byte[8];
        
        //Permutamos (SWITCH) la salida del primer paso para prepararla para el segundo paso de cifrado.
        Permutado[0]=SegundoPaso[3];
        Permutado[1]=SegundoPaso[0];
        Permutado[2]=SegundoPaso[2];
        Permutado[3]=SegundoPaso[4];
        Permutado[4]=SegundoPaso[6];
        Permutado[5]=SegundoPaso[1];
        Permutado[6]=SegundoPaso[7];
        Permutado[7]=SegundoPaso[5];
        
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("     -Salida Segundo Paso Permutado: Bit0["+Permutado[0]+"], Bit1["+Permutado[1]+"], Bit2["+Permutado[2]+"], Bit3["+Permutado[3]+"], Bit4["+Permutado[4]+"], Bit5["+Permutado[5]+"], Bit6["+Permutado[6]+"], Bit7["+Permutado[7]+"]");
        
        return Permutado;
    }

    /**
     * Método para convertir un CHAR a BYTE[8]. No ha funcionado totalmente bien por lo que he
     * usado un código encontrado por internet para realizar este proceso, aunque lo mantengo
     * ya que puede serme útil para futuras prácticas ya que realiza movimientos reales de bits.
     * @param LetraACifrar Char a convertir.
     * @return Carácter convertido a formato Byte[8], para poder tratarlo dentro del código del cifrado DES Simplificado.
     */
    private byte[] ConvertirCadenaAArrayByte(char LetraACifrar){
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("--CONVERTIR DE CHAR A BYTE[]-----------------");
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La variable LetraACifrar(entrada) es: "+LetraACifrar+". Si la pongo a byte es: "+(byte)LetraACifrar+". Si la pongo en int es: "+(int)LetraACifrar);
        int LetraACifrarEnByte = LetraACifrar;
        //byte LetraACifrarEnByte = (byte) LetraACifrar;
        byte[] b = new byte[8];

        //bit 0
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es: " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrar;
        LetraACifrarEnByte = (LetraACifrarEnByte >>> 7); if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[0] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[0]=1");
        } else {
            b[0] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[0]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[0]): " + b[0]);

        //bit 1
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        LetraACifrarEnByte = LetraACifrar;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es (antes b[1]): " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte << 1; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -1 posiciones a la izquierda: "+LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte >>> 7; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[1] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[1]=1");
        } else {
            b[1] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[1]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[1]): " + LetraACifrarEnByte);

        //bit 2
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        LetraACifrarEnByte = LetraACifrar;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es (antes b[2]): " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte << 10; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -2 posiciones a la izquierda: "+LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte >>> 17; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[2] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[2]=1");
        } else {
            b[2] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[2]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[2]): " + LetraACifrarEnByte);

        //bit 3
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        LetraACifrarEnByte = LetraACifrar;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es (antes b[3]): " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte << 11; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -3 posiciones a la izquierda: "+LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte >> 17; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[3] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[3]=1");
        } else {
            b[3] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[3]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[3]): " + LetraACifrarEnByte);

        //bit 4
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        LetraACifrarEnByte = LetraACifrar;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es (antes b[4]): " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte << 12; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -4 posiciones a la izquierda: "+LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte >> 17; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[4] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[4]=1");
        } else {
            b[4] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[4]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[4]): " + LetraACifrarEnByte);

        //bit 5
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        LetraACifrarEnByte = LetraACifrar;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es (antes b[5]): " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte << 5; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -5 posiciones a la izquierda: "+LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte >> 7; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[5] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[5]=1");
        } else {
            b[5] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[5]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[5]): " + LetraACifrarEnByte);

        //bit 6
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        LetraACifrarEnByte = LetraACifrar;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es (antes b[6]): " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte << 6; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -6 posiciones a la izquierda: "+LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte >> 7; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[6] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[6]=1");
        } else {
            b[6] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[6]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[6]): " + LetraACifrarEnByte);

        //bit 7
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("\r\n");
        LetraACifrarEnByte = LetraACifrar;
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es (antes b[7]): " + LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte << 7; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la izquierda: "+LetraACifrarEnByte);
        LetraACifrarEnByte = LetraACifrarEnByte >> 7; if(TrazaActivada && SalidaPantallaSDES) System.out.println("  -7 posiciones a la derecha sin signo: "+LetraACifrarEnByte);
        if (LetraACifrarEnByte > 0) {
            b[7] = 1;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[7]=1");
        } else {
            b[7] = 0;
            if(TrazaActivada && SalidaPantallaSDES) System.out.println("Pongo b[7]=0");
        }
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("La letra a cifrar en byte es(despues b[7]): " + LetraACifrarEnByte);
        if(TrazaActivada && SalidaPantallaSDES) System.out.println("----------> La letra codificada en variable 'b' es: "+b[0]+b[1]+b[2]+b[3]+b[4]+b[5]+b[6]+b[7]);
    return b;
    }
}//Cierre de la clase CifradoSDES