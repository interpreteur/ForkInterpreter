/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

import Exceptions.ExceptionConflitNoms;
import Exceptions.ExceptionVariableNonDeclaree;



/**
 *
 * @author ishola hamed
 */
public class ExecuteurDeclarationVar extends Executeur {
 
    private String ide;
    private String com;
    
    public ExecuteurDeclarationVar(){}

    public ExecuteurDeclarationVar(String ide, String com) {
        this.ide = ide;
        this.com = com;
    }
    
    
    public void executer() throws ExceptionConflitNoms, ExceptionVariableNonDeclaree{
        if ( ! environnement.containsKey(getIde())) {
            // Si c'est une variable locale à la commande
            super.executer(getCom()); // on réxecute la nouvelle commande dans l'executeur Principale
            super.retirerVariable_Environnement(getIde()); // on supprime la variable temporaire de l'environnement
        }else{
            //Si c'est une variable Globale  (c-à-d que, cette variable est deja presente dans l'environnement)
            super.executer(getCom()); // on réxecute la nouvelle commande dans l'executeur Principale
        }
    }
    
    
    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }
    
    
    
    
    
    public static void main(String[] args) {
        ExecuteurDeclarationVar exD = new ExecuteurDeclarationVar();
        //exD.decomposerDeclarationVariable("    let U in if X<3 then X:=2 else X:=3 end");
    }
    
    
}
