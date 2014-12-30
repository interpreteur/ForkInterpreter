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
public class ExecuteurAliasing extends Executeur{
    private String ide1;
    private String ide2;
    private String com;
    
    public ExecuteurAliasing(){}

    public ExecuteurAliasing(String ide1, String ide2, String com) {
        this.ide1 = ide1;
        this.ide2 = ide2;
        this.com = com;
    }
    
    
    public void executerAliasing() throws ExceptionConflitNoms, ExceptionVariableNonDeclaree{
        //System.out.println("Executeur___Aliasing");
        //decomposerAliasing(suiteDeCommandes);
        String cmdEntiere = "let "+getIde1()+" be "+getIde2()+" in "+getCom()+" end ";
        if(environnement.containsKey(getIde1())){
            if (environnement.containsKey(getIde2())) {
                throw new ExceptionConflitNoms("Conflit de nom",getIde2(), cmdEntiere);
            }else{
                super.executer(getCom()); // on réxecute la nouvelle commande dans l'executeur Principale
                // on affecte la valeur de la variable temporaire dans la variable principale
                copierValeurIDE2_dans_IDE1(getIde2(), getIde1());
                super.retirerVariable_Environnement(getIde2()); // on supprime la variable temporaire de l'environnement
            }
        }else{
            throw new ExceptionVariableNonDeclaree("Variable", getIde1(), cmdEntiere);
        } 
    }
    
    /*
    public void decomposerAliasing(String cmdAliasing){
        this.compteurIndex = 0;
        enleverLesEspaces(compteurIndex, cmdAliasing);
        
        String cmdSansEspaceDebut = cmdAliasing.substring(compteurIndex);
        int indexLET = cmdSansEspaceDebut.indexOf("let");  
        int indexBE = cmdSansEspaceDebut.indexOf("be");
        String ide1Espacee  = cmdSansEspaceDebut.substring(indexLET+3, indexBE);// +3 pour en compte le mot (LET)
        ide1 = enleverEspacesDans(ide1Espacee);
        //System.out.println("ide1 = ["+ide1+"]");
        
        int indexIN = cmdSansEspaceDebut.indexOf("in");
        String ide2Espacee  = cmdSansEspaceDebut.substring(indexBE+2, indexIN); // +2 pour en compte le mot (BE)
        this.ide2 = enleverEspacesDans(ide2Espacee);
        //System.out.println("ide2 = ["+ide2+"]");
        
        
        int indexEND = cmdSansEspaceDebut.lastIndexOf("end");
        this.com = cmdSansEspaceDebut.substring(indexIN+2, indexEND);
        this.com = enleverAccoladesDebutFinCOM(this.com);  // permet d'enlever les accolades autour de la commande

    }
    * */
    
    public String getIde1() {
        return ide1;
    }

    public void setIde1(String ide1) {
        this.ide1 = ide1;
    }

    public String getIde2() {
        return ide2;
    }

    public void setIde2(String ide2) {
        this.ide2 = ide2;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }
    
}
