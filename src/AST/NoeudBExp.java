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
public class NoeudBExp extends Noeud {

    public NoeudBExp(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        // On fera la lecture que, lorsqu'on lira le (NOT)
        String motLuEnAvance = lireMotEnAvance(instruction);
        
        if( motLuEnAvance.equalsIgnoreCase("not") ){
            Scan.incrementerIndexDe(motLuEnAvance.length());  // lecture du mot (NOT)
            enleverLesEspaces(instruction);
            Character car = Scan.charAt(Scan.INDEX, instruction);
            if(estUneParentheseOuvrante(car)){
                NoeudPO po = new NoeudPO(this, getTypeCommande(), null);
                return po.reconnaitre(instruction);
            }else{
                return reconnaitre(instruction);
            }
        }else if( estUnBooleen(motLuEnAvance)){
            NoeudBool etatBool = new NoeudBool(this, getTypeCommande(), null);
            return etatBool.reconnaitre(instruction);
        }else if( !motLuEnAvance.equalsIgnoreCase("") ){
            if( estUneExpression(motLuEnAvance.charAt(0)) ){
                NoeudNExp nexp = new NoeudNExp(this, getTypeCommande(), null);
                return nexp.reconnaitre(instruction);
            }
        }else{
            throw new ComandeNonReconnu(getTypeCommande());
        }
        return "bbbbbbbbbbb";
    }
    
    
    
    
    
}
