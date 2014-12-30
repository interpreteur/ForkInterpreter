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
public class EtatCom_inter extends Etat{

    public EtatCom_inter(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        
        enleverLesEspaces(instruction);
        Character car = Scan.charAt(Scan.INDEX, instruction);
        //System.out.println("car = "+car);
        if (estUneAccoladeOuvrante(car)){
            //System.out.println("accolade == ["+car+"]");
            EtatAccoladeOuvrante accO = new EtatAccoladeOuvrante(this, getTypeCommande(), null);
            return accO.reconnaitre(instruction);
        }
        if(Character.isLetter(car)){
            // On le lit en avance parceque, c'est l'EtatCom qui lit tous ses mots reservés  
            String motLuEnAvance = lireMotEnAvance(instruction);
            if(estUnMotReserve(motLuEnAvance) || estUnIDE(motLuEnAvance) ){
                //System.out.println("motLuEnAvance == "+motLuEnAvance);
                //System.out.println("MONSTREEE2222222222222");
                EtatCom com = new EtatCom(this, getTypeCommande(), null);
                return com.reconnaitre(instruction);
            }
        }else{
            throw new GrammaireException(getTypeCommande());
        }
        return "eeeeeeeeeeeeeeeeeee";
    }
    
    
}
