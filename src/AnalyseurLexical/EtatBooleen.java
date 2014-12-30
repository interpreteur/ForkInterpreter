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
public class EtatBooleen extends Etat{

    public EtatBooleen(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        String mot = lireMot(instruction); // lecture du booleen (true OU false)
        enleverLesEspaces(instruction);
        
        //System.out.println("aiaiaiaiaiaiaiaieeeee");
        //System.out.println("booleen  == "+mot);
        if(mot.equalsIgnoreCase("true")  || mot.equalsIgnoreCase("false")  ){
            Character car = Scan.charAt(Scan.INDEX, instruction);
            if( estUneLettre(car) ){
                String motLuEnAvance = lireMotEnAvance(instruction);
                if( estUnOperateurBooleen(motLuEnAvance) ){  // on ne lit pas le mot car,la lecture du mot se fait deja dans l'etatBOp
                    EtatBOp bop = new EtatBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }else if(motLuEnAvance.equalsIgnoreCase("do") || motLuEnAvance.equalsIgnoreCase("then")){
                    if(motLuEnAvance.equalsIgnoreCase("do")){
                        Scan.incrementerIndexDe(motLuEnAvance.length());
                    }
                    EtatCom_inter com_inter = new EtatCom_inter(this, getTypeCommande(), null);
                    return com_inter.reconnaitre(instruction);
                }else{
                    throw new GrammaireException(getTypeCommande());
                }
            }else if ( estUneParentheseFermante(car)){
                    //System.out.println("iiiiiouuuuoooouuuuuuuu");
                    enleverLesEspaces(instruction);
                    EtatParentheseFermante pf = new EtatParentheseFermante(this, getTypeCommande(), null);
                    return pf.reconnaitre(instruction);
            }else{
                throw new GrammaireException(getTypeCommande());
            }
        }
        return "cccccccccccccc";
    }

    
    
    
}
