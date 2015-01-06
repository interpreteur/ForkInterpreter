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
public class NoeudComMilieu extends Noeud{

    public NoeudComMilieu(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        
        enleverLesEspaces(instruction);
        Character car = Scan.charAt(Scan.INDEX, instruction);
        //System.out.println("car = "+car);
        if (estUneAccoladeOuvrante(car)){
            //System.out.println("accolade == ["+car+"]");
            NoeudAccO accO = new NoeudAccO(this, getTypeCommande(), null);
            return accO.reconnaitre(instruction);
        }
        if(Character.isLetter(car)){
            // On le lit en avance parceque, c'est l'NoeudCom qui lit tous ses mots reservés  
            String motLuEnAvance = lireMotEnAvance(instruction);
            if(estUnMotReserve(motLuEnAvance) || estUnIDE(motLuEnAvance) ){
                //System.out.println("motLuEnAvance == "+motLuEnAvance);
                //System.out.println("MONSTREEE2222222222222");
                NoeudCom com = new NoeudCom(this, getTypeCommande(), null);
                return com.reconnaitre(instruction);
            }
        }else{
            throw new ComandeNonReconnu(getTypeCommande());
        }
        return "eeeeeeeeeeeeeeeeeee";
    }
    
    
}
