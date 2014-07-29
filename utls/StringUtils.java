/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.utls;

/**
 *
 * @author fede
 */
public class StringUtils {
    
    public static String pad(String toPad, int targetLength, char padValue, boolean left){
        String target = toPad;
        while(target.length() < targetLength){
            if (left){
                target = padValue + target;
            }else{
                target = target + padValue;
            }
        }
        return target;
    }
    
    
}
