@echo off
cls
echo Práctica de Seguridad de la Información del Máster Universitario en
echo Investigación en Ingeniería y Arquitectura, especialidad Tecnologías
echo Informáticas y Comunicaciones.
echo.
echo Copyright 2015 Francisco Javier Merchán Macías.
echo.
echo This program is free software: you can redistribute it and/or modify
echo it under the terms of the GNU Affero General Public License as published
echo by the Free Software Foundation, either version 3 of the License.
echo.
echo This program is distributed in the hope that it will be useful,
echo but WITHOUT ANY WARRANTY; without even the implied warranty of
echo MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
echo GNU Affero General Public License for more details.
echo.
echo You should have received a copy of the GNU Affero General Public License
echo along with this program.  If not, see <http://www.gnu.org/licenses/>.
pause
@echo off
cls
echo ---------------------------------------------------------------------------
echo ---  PRUEBA DE CIFRADO. Cargamos el fichero 'config_cod.txt             ---
echo ---------------------------------------------------------------------------
echo COMANDO: java -jar P2_SI_2015_64_frmerchan.jar -f config_cod.txt
echo.
java -jar P2_SI_2015_64_frmerchan.jar -f config_cod.txt
pause

cls
echo ---------------------------------------------------------------------------
echo ---  PRUEBA DE DESCIFRADO. Cargamos el fichero 'config_desc.txt         ---
echo ---------------------------------------------------------------------------
echo COMANDO: java -jar P2_SI_2015_64_frmerchan.jar -f config_desc.txt
echo.
java -jar P2_SI_2015_64_frmerchan.jar -f config_desc.txt
pause

cls
echo ---------------------------------------------------------------------------
echo ---  PRUEBA DE CARGA DE FICHERO DE CONFIGURACIÓN. Fichero inexistente.  ---
echo ---------------------------------------------------------------------------
echo COMANDO:java -jar P2_SI_2015_64_frmerchan.jar -f c
echo.
java -jar P2_SI_2015_64_frmerchan.jar -f c
pause

cls
echo ---------------------------------------------------------------------------
echo ---  PRUEBA PARA MOSTRAR LA AYUDA DEL PROGRAMA                          ---
echo ---------------------------------------------------------------------------
echo COMANDO: java -jar P2_SI_2015_64_frmerchan.jar -h
echo.
java -jar P2_SI_2015_64_frmerchan.jar -h 
pause

cls
echo ---------------------------------------------------------------------------
echo ---  PRUEBA PARA MOSTRAR ERROR AL INTRODUCIR COMANDO ERRONEO            ---
echo ---------------------------------------------------------------------------
echo COMANDO: java -jar P2_SI_2015_64_frmerchan.jar -c
echo.
java -jar P2_SI_2015_64_frmerchan.jar -c
pause

cls
echo ---------------------------------------------------------------------------
echo ---  PRUEBA PARA MOSTRAR AYUDA AL NO INTRODUCIR COMANDOS                ---
echo ---------------------------------------------------------------------------
echo COMANDO: java -jar P2_SI_2015_64_frmerchan.jar
echo.
java -jar P2_SI_2015_64_frmerchan.jar
pause
