/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author ishola hamed
 */
public class ExceptionConflitNoms extends Exception {

    private String commande;
    private String variable;
    
    public ExceptionConflitNoms(String message) {
        super(message);
    }

    public ExceptionConflitNoms(String message, String var, String commande) {
        super(message);
        this.commande = commande;
        this.variable = var;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    
    
    public String getCommande() {
        return commande;
    }

    public void setCommande(String commande) {
        this.commande = commande;
    }
    
    
    
    
    
}
