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
public class EtatBExp extends Etat {

    public EtatBExp(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        // On fera la lecture que, lorsqu'on lira le (NOT)
        String motLuEnAvance = lireMotEnAvance(instruction);
        
        if( motLuEnAvance.equalsIgnoreCase("not") ){
            Scan.incrementerIndexDe(motLuEnAvance.length());  // lecture du mot (NOT)
            enleverLesEspaces(instruction);
            Character car = Scan.charAt(Scan.INDEX, instruction);
            if(estUneParentheseOuvrante(car)){
                EtatParentheseOuvrante po = new EtatParentheseOuvrante(this, getTypeCommande(), null);
                return po.reconnaitre(instruction);
            }else{
                return reconnaitre(instruction);
            }
        }else if( estUnBooleen(motLuEnAvance)){
            EtatBooleen etatBool = new EtatBooleen(this, getTypeCommande(), null);
            return etatBool.reconnaitre(instruction);
        }else if( !motLuEnAvance.equalsIgnoreCase("") ){
            if( estUneExpression(motLuEnAvance.charAt(0)) ){
                EtatNExp nexp = new EtatNExp(this, getTypeCommande(), null);
                return nexp.reconnaitre(instruction);
            }
        }else{
            throw new GrammaireException(getTypeCommande());
        }
        return "bbbbbbbbbbb";
    }
    
    
    
    
    
}
