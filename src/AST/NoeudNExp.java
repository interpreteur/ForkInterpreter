/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import ArbreSyntaxique.Expression;
import ArbreSyntaxique.ExpNum;
import GestionExceptions.ComandeNonReconnu;

/**
 *
 * @author ishola hamed
 */
public class NoeudNExp extends Noeud {

    public NoeudNExp(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }


    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu{
        Character car = Scan.charAt(Scan.INDEX, instruction);
        if(estUneParentheseOuvrante(car)){
            NoeudPO pOuvr = new NoeudPO(getEtatPrecedent(), getTypeCommande(), getExpr());
            return pOuvr.reconnaitre(instruction);
        }else if( estUnChiffre(car) ){ // si le caractere est un chiffre, on va dans l'état Num
            NoeudNum num = new NoeudNum(getEtatPrecedent(), getTypeCommande(), getExpr());
            return num.reconnaitre(instruction);
        }else if( estUneLettre(car) ){ // si le caractere est une lettre, on va dans l'état IDE_inter
            NoeudIDE ide_int = new NoeudIDE(getEtatPrecedent(),getTypeCommande(), getExpr());
            return ide_int.reconnaitre(instruction);
        }else{
            throw new ComandeNonReconnu(getTypeCommande());
        }
    }
    
}
