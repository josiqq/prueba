/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package omoikane.exceptions;

/**
 *
 * @author Octavio
 */
public class Misc {
    public static String getStackTraceString(Throwable exc)
        {
            String salida = exc.toString() + "\n";
            StackTraceElement[] elementos = exc.getStackTrace();
            for(int i = 0; i < elementos.length; i++)
            {
                salida += elementos[i].toString() + " at \n" ;
            }
            return salida;
        }
}
