/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;
import ArbreSyntaxique.Expression;
import GestionExceptions.ComandeNonReconnu;
import ArbreSyntaxique.Fork;

/**
 *
 * @author Abdoulaye
 */
public class NoeudFork extends Noeud {

    public NoeudFork(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }
    
    
    public String reconnaitre (String instruction) throws ComandeNonReconnu{
        enleverLesEspaces(instruction);
        
        
        
        return "";
    }
    
    
}
