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
public class EtatAccoladeOuvrante extends Etat {

    
    public EtatAccoladeOuvrante(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }
    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        Scan.readNextChar(instruction); // lecture de la'accolade ouvrante
        Scan.COMPTEUR_accolade++;
        enleverLesEspaces(instruction);
        EtatCom com = new EtatCom(this, getTypeCommande(), null);
        return com.reconnaitre(instruction);
    }
    
}
