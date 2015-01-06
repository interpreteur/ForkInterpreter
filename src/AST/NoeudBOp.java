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
public class NoeudBOp extends Noeud{

    public NoeudBOp(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        String opBooleen = lireMot(instruction); // lecture de l'operateur Booleen (OR) ou (AND)
        enleverLesEspaces(instruction);
      
        if( opBooleen.equalsIgnoreCase("and") || opBooleen.equalsIgnoreCase("or") ){
            Character car = Scan.charAt(Scan.INDEX, instruction);
            if( estUneExpression(car) ){
                enleverLesEspaces(instruction);
                NoeudBExp bexp = new NoeudBExp(this, getTypeCommande(), null);
                return bexp.reconnaitre(instruction);
            }else{
                throw new ComandeNonReconnu(getTypeCommande());
            }
        }else{
                throw new ComandeNonReconnu(getTypeCommande());
        }
    }
    
    
}
