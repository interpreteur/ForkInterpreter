/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;


import static ArbreSyntaxique.Executeur.environnement;
import Exceptions.ExceptionVariableNonDeclaree;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author abdoulaye
 */
/*
public class ExecuteurFork extends Executeur{
    
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
    
    
    public String extraireIDE(ArrayList<String> listDecomp){
        String ideEspace = listDecomp.get(0);// le premier element est toujours l'IDE
        String ideSansEspace  = enleverEspacesDans(ideEspace);
        return ideSansEspace; 
    }
    
    
    
}
