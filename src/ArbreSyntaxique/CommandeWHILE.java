/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

import GestionExceptions.ConflitVariableName;
import GestionExceptions.VariableNotExist;


/**
 *
 * @author ishola hamed
 */
public class CommandeWHILE extends LanceurCom {

    private String BExp;
    private String Com;
    
    public CommandeWHILE() {}

    public CommandeWHILE(String BExp, String Com) {
        this.BExp = BExp;
        this.Com = Com;
    }
    
    
    public void executerIteration() throws ConflitVariableName{
        //System.out.println("Executeur_Iteration = ["+getCom()+"]");
        try {
            boolean eval = evaluerBExp(getBExp());
            while ( eval ){
                super.executer(getCom());
                eval = evaluerBExp(getBExp());
            }
        } catch (VariableNotExist ex) {
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
        CommandeWHILE e = new CommandeWHILE();
        //e.extraire_Com_BExp("  while C<2 and true or false  do  X:=1+2-(2+3)");
    }
    
    
}
