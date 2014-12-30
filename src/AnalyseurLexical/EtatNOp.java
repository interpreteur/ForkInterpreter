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
public class EtatNOp extends Etat {
    
    public EtatNOp(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        Scan.readNextChar(instruction);  // lecture de l'operateur
        enleverLesEspaces(instruction);

        Character car = Scan.charAt(Scan.INDEX, instruction);
        if(estUneExpression(car)){ // On test si c'est une expression [ Parenthèse ouvrante ,lettre, ou chiffre ]
            enleverLesEspaces(instruction);
            EtatNExp nexp = new EtatNExp(this, getTypeCommande(), getExpr());
            return nexp.reconnaitre(instruction);
        }else{
            throw new GrammaireException(getTypeCommande());
        }
    }

}
