/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

import GestionExceptions.VariableNotExist;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Abdoulaye
 */
public class CommandeFORK extends LanceurCom{
    private String ide;
    private String cmdFork;

    public CommandeFORK() {
    }

    public CommandeFORK(String cmdFork) {
        this.cmdFork = cmdFork;
    }

    
    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getCmdFork() {
        return cmdFork;
    }

    public void setCmdFork(String cmdFork) {
        this.cmdFork = cmdFork;
    }
    
    public void executerFork(String cmdFork) throws VariableNotExist{
        ArrayList<String> decomp = decomposerFork(cmdFork);
        this.ide = extraireIDE(decomp);
        String fork = extraireFork(decomp);
        int valeurFork = calculer(fork);
        //permet d'ajouter l'IDE dans l'environnement
        //ajouterVariable_Environnement(ide, valeurExpr);
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
    
    public String extraireFork( ArrayList<String> listDecomp){
        String forkEspace = listDecomp.get(1);
        String forkSansEspace = enleverEspacesDans(forkEspace); // on enleve les espaces dans l'expression
        return forkSansEspace;
    }
    
    
    public void calculToken() throws VariableNotExist{
       
    }
    
    public int calculer(String expr) throws VariableNotExist{
        return 0;
    }
}
