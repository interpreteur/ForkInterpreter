/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalyseurLexical;

import ArbreSyntaxique.Expression;
import ArbreSyntaxique.NExp;
import Exceptions.GrammaireException;

/**
 *
 * @author ishola hamed
 */
public class EtatNExp extends Etat {

    public EtatNExp(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }


    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException{
        Character car = Scan.charAt(Scan.INDEX, instruction);
        if(estUneParentheseOuvrante(car)){
            EtatParentheseOuvrante pOuvr = new EtatParentheseOuvrante(getEtatPrecedent(), getTypeCommande(), getExpr());
            return pOuvr.reconnaitre(instruction);
        }else if( estUnChiffre(car) ){ // si le caractere est un chiffre, on va dans l'état Num
            EtatNum num = new EtatNum(getEtatPrecedent(), getTypeCommande(), getExpr());
            return num.reconnaitre(instruction);
        }else if( estUneLettre(car) ){ // si le caractere est une lettre, on va dans l'état IDE_inter
            EtatIDE_inter ide_int = new EtatIDE_inter(getEtatPrecedent(),getTypeCommande(), getExpr());
            return ide_int.reconnaitre(instruction);
        }else{
            throw new GrammaireException(getTypeCommande());
        }
    }
    
}
