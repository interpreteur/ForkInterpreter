/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import ArbreSyntaxique.Expression;
import GestionExceptions.ComandeNonReconnu;

/**
 *
 * @author ishola hamed
 */
public class NoeudAccO extends Noeud {

    
    public NoeudAccO(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }
    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        Scan.readNextChar(instruction); // lecture de la'accolade ouvrante
        Scan.COMPTEUR_accolade++;
        enleverLesEspaces(instruction);
        NoeudCom com = new NoeudCom(this, getTypeCommande(), null);
        return com.reconnaitre(instruction);
    }
    
}
