/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalyseurLexical;

import Exceptions.GrammaireException;
/**
 *
 * @author ishola hamed
 */
public class Analyseur {
    
   
    public Analyseur(){ 
    }
    
    public void initialiserLesCompteurs(){
        Scan.INDEX = 0;
        Scan.COMPTEUR_parenthese = 0;
        Scan.COMPTEUR_LetEnd = 0;
        Scan.COMPTEUR_accolade = 0;
        Scan.COMPTEUR_IF = 0;
        Scan.COMPTEUR_THEN = 0;
        Scan.COMPTEUR_WHILE = 0;
        Scan.COMPTEUR_FORK = 0;
        Scan.COMPTEUR_WAIT = 0;
    }
    
    
    public String analyser(String s) throws GrammaireException{
        initialiserLesCompteurs();  // permet d'initialiser l'index de deplacement des caractères dans l'instruction
        EtatCom com = new EtatCom(null, "Grammaire", null);
        String res =  com.reconnaitre(s);
        return res;
    }
    
    
    public String signalerErreur(int indexErreur){
        StringBuilder sb = new StringBuilder();
        if(indexErreur > 0){
            sb.append("  ");
            for (int i = 0; i < indexErreur-1; i++) {
                sb.append(" ");
            }
            sb.append("#");
        }else if(indexErreur == 0) {
            sb.append("  *");
        }
        return sb.toString();
    }
}