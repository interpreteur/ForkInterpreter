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
public class ExecuteurConditionelle extends Executeur{

    private String BExp;
    private String Com1;
    private String Com2;
    
    
    public ExecuteurConditionelle(String BExp, String Com1, String Com2) {
        this.BExp = BExp;
        this.Com1 = Com1;
        this.Com2 = Com2;
    }
    
    
    public void executerConditionelle() throws ExceptionConflitNoms, ExceptionVariableNonDeclaree{
        boolean eval = evaluerBExp(getBExp());
        if( eval ){
            super.executer(getCom1());
        }else{
            super.executer(getCom2());
        }
    }

    public String getBExp() {       
        return BExp;
    }

    public void setBExp(String BExp) {
        this.BExp = BExp;
    }

    public String getCom1() {
        return Com1;
    }

    public void setCom1(String Com1) {
        this.Com1 = Com1;
    }

    public String getCom2() {
        return Com2;
    }

    public void setCom2(String Com2) {
        this.Com2 = Com2;
    }
   
}
