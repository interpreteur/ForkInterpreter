/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author ishola hamed
 */
public class ExceptionVariableNonDeclaree  extends Exception{
    private String varNonDeclaree;
    private String laCommande;

    public ExceptionVariableNonDeclaree(String message, String varNonDeclaree, String cmd) {
        super(message);
        this.varNonDeclaree = varNonDeclaree;
        this.laCommande = cmd;
    }

    public String getVarNonDeclaree() {
        return varNonDeclaree;
    }

    public void setVarNonDeclaree(String varNonDeclaree) {
        this.varNonDeclaree = varNonDeclaree;
    }

    public String getLaCommande() {
        return laCommande;
    }

    public void setLaCommande(String laCommande) {
        this.laCommande = laCommande;
    }
    
    
    
    
}
