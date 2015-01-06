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
public class NoeudBool extends Noeud{

    public NoeudBool(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        String mot = lireMot(instruction); // lecture du booleen (true OU false)
        enleverLesEspaces(instruction);
        
        //System.out.println("aiaiaiaiaiaiaiaieeeee");
        //System.out.println("booleen  == "+mot);
        if(mot.equalsIgnoreCase("true")  || mot.equalsIgnoreCase("false")  ){
            Character car = Scan.charAt(Scan.INDEX, instruction);
            if( estUneLettre(car) ){
                String motLuEnAvance = lireMotEnAvance(instruction);
                if( estUnOperateurBooleen(motLuEnAvance) ){  // on ne lit pas le mot car,la lecture du mot se fait deja dans l'etatBOp
                    NoeudBOp bop = new NoeudBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }else if(motLuEnAvance.equalsIgnoreCase("do") || motLuEnAvance.equalsIgnoreCase("then")){
                    if(motLuEnAvance.equalsIgnoreCase("do")){
                        Scan.incrementerIndexDe(motLuEnAvance.length());
                    }
                    NoeudComMilieu com_inter = new NoeudComMilieu(this, getTypeCommande(), null);
                    return com_inter.reconnaitre(instruction);
                }else{
                    throw new ComandeNonReconnu(getTypeCommande());
                }
            }else if ( estUneParentheseFermante(car)){
                    //System.out.println("iiiiiouuuuoooouuuuuuuu");
                    enleverLesEspaces(instruction);
                    NoeudPF pf = new NoeudPF(this, getTypeCommande(), null);
                    return pf.reconnaitre(instruction);
            }else{
                throw new ComandeNonReconnu(getTypeCommande());
            }
        }
        return "cccccccccccccc";
    }

    
    
    
}
