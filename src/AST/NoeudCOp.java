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
public class NoeudCOp extends Noeud {

    public NoeudCOp(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        Scan.readNextChar(instruction);  // lecture du comparateur
        lireLesEspaces(instruction);
        Character car = Scan.charAt(Scan.INDEX,instruction);
        if( estUneExpression(car)  ){
            NoeudNExp nexp = new NoeudNExp(this,getTypeCommande(), null);
            return nexp.reconnaitre(instruction);
        }else{
            throw new ComandeNonReconnu(getTypeCommande());
        }
    }
    
    
}
