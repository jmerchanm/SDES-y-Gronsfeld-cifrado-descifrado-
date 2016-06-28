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
 * Practica 2 de Seguridad de la Información. MUI IA. Centro Universitario de Mérida. 2015/2016
 * @author Javier Merchan (javier@javiermerchan.com)
 * @version 0.1
 */
public class P2_SI_2015_64_frmerchan {

    /**
     * Método principal (MAIN) del programa.
     * @param args Parámetros que se introducen a la hora de ejecutar el programa desde línea de comandos.
     */
    public static void main(String[] args) {
        String NombreEjecutable="java -jar P2_SI2015_64.jar"; //Lo uso para definir el nombre del ejecutable en la ayuda -h
        
        if(args.length==0){
            System.out.println("No has tecleado parámetros... Usa: '"+NombreEjecutable+" -h' para obtener ayuda.");
            System.exit(0);
        }
        
        if(args[0].equals("-h")){
            //for(String key: args){
            //    if(key.toUpperCase().equals("-H")){
            System.out.println("Manual de uso:");
            System.out.println("Sintasis: "+NombreEjecutable+" [-f fichero] | [-h] | [-t]");
            System.out.println("       -h           Muestra esta ayuda.");
            System.out.println("       -f fichero   Introduce el fichero de configuración. Dicho fichero puede contener lo siguiente:");
            System.out.println("                     # : Las líneas que comienzan por # son comentarios y se ignorarán.");
            System.out.println("                     @ : Las líneas que comienzan por @ son banderas. Serán como siguen");
            System.out.println("                        @ salidapantalla ON | OFF  Si está en ON el resultado del cifrado saldrá por pantalla");
            System.out.println("                                                   Si está en OFF el resultado del cifrado no saldrá por pantalla");
            System.out.println("                        @ salidafichero ON | OFF   Si está en ON el resultado del des/cifrado se escribirá en el fichero de salida.");
            System.out.println("                                                   Si está en OFF el resultado del des/cifrado no se escribirá en el fichero de salida.");
//            System.out.println("                        @ gronsfeld ON | OFF       Si está en ON usará el cifrado Gronsfeld.");
//            System.out.println("                                                   Si está en OFF no usará el cifrado Gronsfeld.");
//            System.out.println("                        @ s_des ON | OFF           Si está en ON usará el cifrado DES Simplificado.");
//            System.out.println("                                                   Si está en OFF no usará el cifrado DES Simplificado.");
            System.out.println("                        @ gronsfeld                Usará el cifrado Gronsfeld.");
            System.out.println("                        @ s_des                    Usará el cifrado DES Simplificado.");
            System.out.println("                        @ traza ON | OFF           Si está en ON activará la traza del cifrado DES Simplificado.");
            System.out.println("                                                   Si está en OFF desactivará la traza del cifrado DES Simplificado.");
            System.out.println("                     & : Las líneas que comienzan por & son comandos. Serán como siguen");
            System.out.println("                        & codifica <fichero>       Se incluirá el nombre de fichero TXT a cifrar");
            System.out.println("                        & decodifica <fichero>     Se incluirá el nombre de fichero TXT a descifrar");
            System.out.println("                        & clave                    Se anotará la clave de des/cifrado.");
            System.out.println("                        & ficherosalida <fichero>  En <fichero> se volcará la salida del programa, si procede.");
            System.out.println("");
            System.out.println("       -t           Realiza una prueba de cifrado y descifrado utilizando el algoritmo Gronsfeld.");
            System.out.println("");
            System.out.println(" NOTA: Si una línea de configuración se repite, sólo tendrá efecto la última que aparezca en el fichero.");
            System.out.println("");
            System.out.println("(C) 2015 - Francisco Javier Merchán Macías, Ingeniero Técnico en Telecomunicación, esp. Telemática.");
            System.out.println("           Estudiante Máster Universitario de Investigación en Ingeniería y Arquitectura. CUM.");
            System.exit(0);
        }
        //if(key.toUpperCase().equals("-F")){
        if(args[0].equals("-f")){
            if(args.length!=2){
                System.out.println("Sintasis: "+NombreEjecutable+" [-f ficheroConfiguración]");
            }
            else{        
                GestionFicheros ficheroconfiguracion=new GestionFicheros();
                //System.out.println("El fichero de configuración que se va a buscar es: "+args[1]);
                ficheroconfiguracion.AbrirFicheroConfiguracion(args[1]); //Le pasamos el fichero de configuración a este método.
            }
            System.exit(0);
        }
        
        if(args[0].equals("-t")){
            CifradoGronsfeld cifrado=new CifradoGronsfeld();
            cifrado.PruebaGronsfeld();
            System.exit(0);
        }
        if(args[0]!="-h"||args[0]!="-f"||args[0]!="-t"){
            System.out.println("Has tecleado argumentos incorrectos... Usa: '"+NombreEjecutable+" -h' para obtener ayuda.");
            System.exit(0);
        }
        
    }
}