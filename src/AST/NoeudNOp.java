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
public class NoeudNOp extends Noeud {
    
    public NoeudNOp(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        Scan.readNextChar(instruction);  // lecture de l'operateur
        enleverLesEspaces(instruction);

        Character car = Scan.charAt(Scan.INDEX, instruction);
        if(estUneExpression(car)){ // On test si c'est une expression [ Parenthèse ouvrante ,lettre, ou chiffre ]
            enleverLesEspaces(instruction);
            NoeudNExp nexp = new NoeudNExp(this, getTypeCommande(), getExpr());
            return nexp.reconnaitre(instruction);
        }else{
            throw new ComandeNonReconnu(getTypeCommande());
        }
    }

}
