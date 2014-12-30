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
public class EtatAccoladeFermante extends Etat {

    
    public EtatAccoladeFermante(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }
    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        Scan.readNextChar(instruction); // lecture de l'accolade fermante
        Scan.COMPTEUR_accolade--;
        
        enleverLesEspaces(instruction);
        Character car = Scan.charAt(Scan.INDEX, instruction);
        
        if (estUneAccoladeFermante(car)) {
            return reconnaitre(instruction);
        }
        if (estPointVirgule(car)) {
            EtatCom com = new EtatCom(this, getTypeCommande(), null);
            return com.reconnaitre(instruction);
        }
        if(estUneLettre(car)){
            String motLuEnAvance = lireMotEnAvance(instruction);
            //System.out.println("motLuEnAvance = ["+motLuEnAvance+"]");
            if (motLuEnAvance.equalsIgnoreCase("end") 
                    || motLuEnAvance.equalsIgnoreCase("else")){
                EtatCom com = new EtatCom(this, getTypeCommande(), null);
                return com.reconnaitre(instruction);
            }
        }
        if (enFinDeMot(instruction)){
            if(Scan.COMPTEUR_LetEnd==0 && Scan.COMPTEUR_accolade==0
                    && Scan.COMPTEUR_parenthese==0  && Scan.COMPTEUR_IF==0
                    && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0){
                return "--Grammaire reconnu--";
            }
        }else{
            throw new GrammaireException(getTypeCommande());
        }
        return "iiiii";
    }
    
}