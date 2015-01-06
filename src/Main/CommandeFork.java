/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import ArbreSyntaxique.Expression;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import Exceptions.ExceptionVariableNonDeclaree;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 *
 * @author sofiane
 */
/*
public class CommandeFork extends Exception {
    private String ide;
    private Expression Code;
    private String cmdFork;

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public Expression getCode() {
        return Code;
    }

    public void setCode(Expression Code) {
        this.Code = Code;
    }

    public String getCmdFork() {
        return cmdFork;
    }

    public void setCmdFork(String cmdFork) {
        this.cmdFork = cmdFork;
    }
    
    public void executeurFork(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Commande : ");
        String entree = sc.nextLine();

        int index2PointEgale = entree.indexOf(":=");
        int debut = index2PointEgale+2;
        int fin = entree.length();
        String str = entree.substring(debut, fin);

        if (str.compareToIgnoreCase(" fork()")== 0){
            System.out.println(entree);
            String a = entree.substring(0, index2PointEgale);
            this.setIde(a);
            
            
            
        }
        else{
            System.out.println("error");
        }
    }
    
    
    public ArrayList<String> decomposerFork(String cmdFork){
        ArrayList<String> listDecomp = new ArrayList();
        StringTokenizer st = new StringTokenizer(cmdFork, ":=");
        while (st.hasMoreTokens()) {
          String valeur = st.nextToken();
          if( !valeur.equals("") &&  !valeur.equals(" ")){
            listDecomp.add(valeur);
          }
        }
        return listDecomp;
    }
    
    
    
    
    
    public static void main (String[] args){
    }
}
*/
