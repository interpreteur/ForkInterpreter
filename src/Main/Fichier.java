/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import AST.Parseur;
import AST.Scan;
import ArbreSyntaxique.LanceurCom;
import GestionExceptions.ConflitVariableName;
import GestionExceptions.VariableNotExist;
import GestionExceptions.ComandeNonReconnu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author ishola hamed
 */
public class Fichier {
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        StringBuilder sb = new StringBuilder();
        
        BufferedReader br = new BufferedReader(new FileReader(new File(args[0])));
        String ligne = br.readLine();
        
        while (ligne != null){
            ligne = ligne+" "; //rajouter espace pour delimiter la fin de ligne
            String val = ligne.replace("\t", " ").replaceAll("\r", " ").replace("\n", " ");
            sb.append(val);
            ligne = br.readLine();
        }
        System.out.println("--- "+sb.toString());
        
        Parseur a = new Parseur();
        LanceurCom exec = new LanceurCom();
        try {
          //On ajoute un espace comme delimiteur de fin de ligne
                    
            String resultatAnalyse = a.analyser(sb.toString()+" ");
            System.out.println(resultatAnalyse);
            try {
                exec.executer(sb.toString());  // evaluation de la commande apres avoir reconnu la COMMANDE
            } catch (ConflitVariableName ex) {
                System.out.println(" Erreur ["+ex.getMessage()+"] sur  ["+ex.getVariable()+"] , dans la commande ["+ex.getCommande()+"]");
            } catch (VariableNotExist ex) {
                System.out.println(" Erreur :["+ex.getMessage()+"], la variable ["+ex.getVarNonDeclaree()+"] n'a pas été declaré dans la commande ["+ex.getLaCommande()+"] ");
            }
            exec.afficherEnvironnement();
        } catch (ComandeNonReconnu ge) {
                    System.out.println(a.signalerErreur(Scan.INDEX));
                    String message = ge.getMessage();
                    String erreurAafficher =  " Erreur ["+ message+"] , à la colonne "+ Scan.INDEX+"\n" ;
                    System.out.println(erreurAafficher);
                    //ge.printStackTrace();
                }
    }


}
