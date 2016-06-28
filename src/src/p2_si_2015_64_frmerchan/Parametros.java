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
package p2_si_2015_64_frmerchan;

/**
 * Practica 2 de Seguridad de la Información. MUI IA. Centro Universitario de Mérida. 2015/2016
 * Clase Parametros. Se gestionarán los parámetros obtenidos desde el ficheor en esta clase.
 * En esta clase guardamos todos los datos de configuración cargados desde el fichero de configuración o
 * en ausencia de estos datos, se cargarán unos valores por defecto.
 * @author Javier Merchan (javier@javiermerchan.com)
 * @version 0.1
 */
public class Parametros {
    private boolean SalidaPantalla=true;
    private boolean SalidaFichero=false;
    private String FicheroACifrar="";
    private String FicheroADescifrar="";
    private String Clave="";
    private String FicheroSalida="FJMM-TrazadoXDefecto.txt";
    private boolean TipoCifradoGronsfeld=true; //Por defecto haremos el cifrado o descifrado Gronsfeld.
    private boolean TipoCifradoSDES=false; //Disponibles Gronsfeld y SDES.
    private boolean Traza=false; //Activa o desactiva la traza en el cifrado o descifrado SDES.
    
    /**
     * Constructor de la clase Parametros.
     */
    public Parametros() {

    }

    /**
     * Método que consulta el estado de una variable booleana, la cual nos dice si está activa o no la salida del programa por pantalla.
     * @return SalidaPantalla Nos informa si está activada o no la salida del programa por pantalla.
     */
    public boolean isSalidaPantalla() {
        return SalidaPantalla;
    }

    /**
     * Método para configurar el valor de la variable SalidaPantalla. Activa o no la salida del programa por pantalla.
     * @param SalidaPantalla Variable booleana (True o False) para activar o no la salida del programa por pantalla.
     */
    public void setSalidaPantalla(boolean SalidaPantalla) {
        this.SalidaPantalla = SalidaPantalla;
    }

    /**
     * Método que consulta el estado de una variable booleana, la cual nos dice si está activa o no la salida del programa a un fichero.
     * @return SalidaFichero Nos informa si está activada o no la salida del programa a un fichero.
     */
    public boolean isSalidaFichero() {
        return SalidaFichero;
    }

    /**
     * Método para configurar el valor de la variable SalidaFichero. Activa o no la salida del programa a un fichero.
     * @param SalidaFichero Variable booleana (True o False) para activar o no la salida del programa a un fichero.
     */
    public void setSalidaFichero(boolean SalidaFichero) {
        this.SalidaFichero = SalidaFichero;
    }

    /**
     * Método para obtener el nombre del fichero a cifrar.
     * @return Obtiene un String con la ruta y nombre del fichero a cifrar.
     */
    public String getFicheroACifrar() {
        return FicheroACifrar;
    }

    /**
     * Método para configurar el nombre del fichero a cifrar.
     * @param FicheroACifrar Definimos un String con la ruta y nombre de fichero a cifrar.
     */
    public void setFicheroACifrar(String FicheroACifrar) {
        this.FicheroACifrar = FicheroACifrar;
    }

    /**
     * Método para obtener el nombre del fichero a cifrar.
     * @return Retorna un String con la ruta y nombre de fichero a cifrar.
     */
    public String getFicheroADescifrar() {
        return FicheroADescifrar;
    }

    /**
     * Método para configurar el nombre del fichero a descifrar.
     * @param FicheroADescifrar Definimos un String con la ruta y nombre de fichero a descifrar.
     */
    public void setFicheroADescifrar(String FicheroADescifrar) {
        this.FicheroADescifrar = FicheroADescifrar;
    }

    /**
     * Método para obtener el valor de la clave de cifrado o descifrado.
     * @return String con el valor de la clave de cifrado o descifrado.
     */
    public String getClave() {
        return Clave;
    }

    /**
     * Método para configurar la clave de cifrado o descifrado.
     * @param Clave Definimos un String con el valor de la clave de cifrado o descifrado.
     */
    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    /**
     * Método para obtener el nombre del fichero y su ruta que almacenará la salida del programa en un fichero.
     * @return Retorna String con el nombre y ruta del fichero que almacenará la salida del programa en un fichero.
     */
    public String getFicheroSalida() {
        return FicheroSalida;
    }

    /**
     * Método para configurar el nombre del fichero y su ruta que almacenará la salida del programa en un fichero.
     * @param FicheroSalida Definimos un String con el nombre y ruta del fichero que almacenará la salida del programa en un fichero.
     */
    public void setFicheroSalida(String FicheroSalida) {
        this.FicheroSalida = FicheroSalida;
    }

    /**
     * Método para obtener el estado del cifrado y descifrado Gronsfeld.
     * @return Retorna true=si el cifrado está activado el cifrado, false=si el cifrado está desactivado.
     */
    public boolean isTipoCifradoGronsfeld() {
        return TipoCifradoGronsfeld;
    }

    /**
     * Método para configurar el estado del cifrado y descifrado Gronsfeld.
     * @param TipoCifradoGronsfeld configuramos como true si activamos el cifrado Gronsfeld y false si está desactivado.
     */
    public void setTipoCifradoGronsfeld(boolean TipoCifradoGronsfeld) {
        this.TipoCifradoGronsfeld = TipoCifradoGronsfeld;
    }

    /**
     * Método para obtener el estado del cifrado y descifrado DES Simplificado.
     * @return Retorna true=si el cifrado está activado el cifrado, false=si el cifrado está desactivado.
     */
    public boolean isTipoCifradoSDES() {
        return TipoCifradoSDES;
    }

    /**
     * Método para configurar el estado del cifrado y descifrado DES Simplificado.
     * @param TipoCifradoSDES configuramos como true si activamos el cifrado DES Simplificaod y false si está desactivado.
     */
    public void setTipoCifradoSDES(boolean TipoCifradoSDES) {
        this.TipoCifradoSDES = TipoCifradoSDES;
    }

    /**
     * Método para obtener el estado de la variable Traza.
     * @return Devuelve el estado de la variable Traza.
     */
    public boolean isTraza() {
        return Traza;
    }

    /**
     * Método para configurar el estado de la variable Traza.
     * @param Traza configuramos como true si activamos la Traza o false si la desactivamos.
     */
    public void setTraza(boolean Traza) {
        this.Traza = Traza;
    }
}