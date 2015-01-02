/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalyseurLexical;

/**
 *
 * @author ishola hamed
 */
public class Scan {
    
    public static int INDEX = -1;
    public static int COMPTEUR_LetEnd = 0;
    public static int COMPTEUR_parenthese = 0;
    public static int COMPTEUR_accolade = 0;
    
    public static int COMPTEUR_IF = 0;
    public static int COMPTEUR_THEN = 0;
    
    public static int COMPTEUR_WHILE = 0;
    
    public static int COMPTEUR_FORK = 0;
    public static int COMPTEUR_WAIT = 0;
    
    public Scan(){
    }
    
    public static void incrementerIndexDe(int valeur){
        Scan.INDEX += valeur;
    }
    
    public static Character readNextChar(String instruction){
        if ( INDEX < instruction.length()-1 ){
            Character c = instruction.charAt(++INDEX);
            return c;
        }else{
            return null;
        }
    }
    
    public static Character charAt(int i, String instruction){
        if( i == -1 ){
            return instruction.charAt(i+1);
        }else{
            if(i < instruction.length() ){
                return instruction.charAt(i);
            }
        }
        return null;
    }
}
