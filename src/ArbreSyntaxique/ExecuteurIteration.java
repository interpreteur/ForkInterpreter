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
public class ExecuteurIteration extends Executeur {

    private String BExp;
    private String Com;
    
    public ExecuteurIteration() {}

    public ExecuteurIteration(String BExp, String Com) {
        this.BExp = BExp;
        this.Com = Com;
    }
    
    
    public void executerIteration() throws ExceptionConflitNoms{
        //System.out.println("Executeur_Iteration = ["+getCom()+"]");
        try {
            boolean eval = evaluerBExp(getBExp());
            while ( eval ){
                super.executer(getCom());
                eval = evaluerBExp(getBExp());
            }
        } catch (ExceptionVariableNonDeclaree ex) {
            System.out.println("La Variable ["+ex.getVarNonDeclaree()+"] n'a pas été declaré, dans la commande ["+getBExp()+"]");
        }
    }
    
    
    public String getBExp() {
        return BExp;
    }

    public void setBExp(String BExp) {
        this.BExp = BExp;
    }

    public String getCom() {
        return Com;
    }

    public void setCom(String Com) {
        this.Com = Com;
    }
    
    
    public static void main(String[] args) {
        ExecuteurIteration e = new ExecuteurIteration();
        //e.extraire_Com_BExp("  while C<2 and true or false  do  X:=1+2-(2+3)");
    }
    
    
}
