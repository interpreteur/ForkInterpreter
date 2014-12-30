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
public class EtatBOp extends Etat{

    public EtatBOp(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        String opBooleen = lireMot(instruction); // lecture de l'operateur Booleen (OR) ou (AND)
        enleverLesEspaces(instruction);
      
        if( opBooleen.equalsIgnoreCase("and") || opBooleen.equalsIgnoreCase("or") ){
            Character car = Scan.charAt(Scan.INDEX, instruction);
            if( estUneExpression(car) ){
                enleverLesEspaces(instruction);
                EtatBExp bexp = new EtatBExp(this, getTypeCommande(), null);
                return bexp.reconnaitre(instruction);
            }else{
                throw new GrammaireException(getTypeCommande());
            }
        }else{
                throw new GrammaireException(getTypeCommande());
        }
    }
    
    
}
