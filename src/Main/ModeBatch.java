/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import AnalyseurLexical.Analyseur;
import AnalyseurLexical.Scan;
import ArbreSyntaxique.Executeur;
import Exceptions.ExceptionConflitNoms;
import Exceptions.ExceptionVariableNonDeclaree;
import Exceptions.GrammaireException;
import java.util.Scanner;

/**
 *
 * @author ishola hamed
 */
public class ModeBatch {
    
    
    public void lancer(){
        
        boolean b = false;
        Scanner s = new Scanner(System.in);
        String suiteDeCommandes = null;
        
        Analyseur a = new Analyseur();
        Executeur exec = new Executeur();
        
        while( !b ){
            System.out.println("\nEntrez une commande");
            System.out.print(">>");
            suiteDeCommandes = s.nextLine();
            if(suiteDeCommandes.equalsIgnoreCase("exit")){
                b = true;
            }else{
                try {
                    //On ajoute un espace comme delimiteur de fin de ligne
                    suiteDeCommandes = suiteDeCommandes+" "; 
                    String resultatAnalyse = a.analyser(suiteDeCommandes);
                    System.out.println(resultatAnalyse);
                    try {
                        exec.executer(suiteDeCommandes);  // evaluation de la commande apres avoir reconnu la COMMANDE
                    } catch (ExceptionConflitNoms ex) {
                        System.out.println(" Erreur ["+ex.getMessage()+"] sur  ["+ex.getVariable()+"] , dans la commande ["+ex.getCommande()+"]");
                    } catch (ExceptionVariableNonDeclaree ex) {
                        System.out.println(" Erreur :["+ex.getMessage()+"], la variable ["+ex.getVarNonDeclaree()+"] n'a pas été declaré dans la commande ["+ex.getLaCommande()+"] ");
                        ex.printStackTrace();
                    }
                    exec.afficherEnvironnement();
                } catch (GrammaireException ge) {
                    System.out.println(a.signalerErreur(Scan.INDEX));
                    String message = ge.getMessage();
                    String erreurAafficher =  " Erreur ["+ message+"] , à la colonne "+ Scan.INDEX+"\n" ;
                    System.out.println(erreurAafficher);
                }
            }
        }
     }
        
    
    
    
    public static void main(String[] args) {
        new ModeBatch().lancer();
    }
    
}