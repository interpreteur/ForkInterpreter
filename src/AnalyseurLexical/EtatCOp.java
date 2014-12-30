/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalyseurLexical;

import ArbreSyntaxique.Expression;
import Exceptions.GrammaireException;

/**
 *
 * @author ishola hamed
 */
public class EtatCOp extends Etat {

    public EtatCOp(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        Scan.readNextChar(instruction);  // lecture du comparateur
        lireLesEspaces(instruction);
        Character car = Scan.charAt(Scan.INDEX,instruction);
        if( estUneExpression(car)  ){
            EtatNExp nexp = new EtatNExp(this,getTypeCommande(), null);
            return nexp.reconnaitre(instruction);
        }else{
            throw new GrammaireException(getTypeCommande());
        }
    }
    
    
}
