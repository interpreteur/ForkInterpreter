/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

import static ArbreSyntaxique.Executeur.environnement;
import Exceptions.ExceptionVariableNonDeclaree;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author abdoulaye
 */
public class ExecuteurWait {
    private String ide;
    private Expression exp;
    private String cmdWait;

    public ExecuteurWait(String cmdWait) {
        this.cmdWait = cmdWait;
    }

    public String getIde() {
        return ide;
    }

    public Expression getExp() {
        return exp;
    }

    public String getCmdWait() {
        return cmdWait;
    }
    
    
    
    
}
