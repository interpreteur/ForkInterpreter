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
public class EtatParentheseOuvrante extends Etat {

    public EtatParentheseOuvrante(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        Scan.readNextChar(instruction); // lecture de la parenthese fermente
        Scan.COMPTEUR_parenthese ++;   // on incremente le compteur des parentheses, pour signaler qu'on a rencontré un parenthese ouvrante
        enleverLesEspaces(instruction);
        
        Character car = Scan.charAt(Scan.INDEX, instruction);
        //System.out.println("car == "+car);
        if( estUneParentheseOuvrante(car)){  // permet de gerer la double parenthèse ouvrante
            this.setEtatPrecedent(this); // l'etat precedent est cet etat lui-meme
            //this.getExpr().setEstParentheser(true); // On cet expression est donc parenthésée
            return reconnaitre(instruction);
        }else if( getTypeCommande().equalsIgnoreCase(Constante.conditionelle) ){ // Si c'est une commande Conditionnelle
            // Si c'est une commande IF, alors on va dans BEXP
            EtatBExp bexp = new EtatBExp(this, getTypeCommande(), null);
            return bexp.reconnaitre(instruction);
        }else if(estUnChiffre(car)){
            EtatNum num = new EtatNum(this, getTypeCommande(), getExpr());
            return num.reconnaitre(instruction);
        }else if(estUneLettre(car)){
            EtatIDE_inter ide_inter = new EtatIDE_inter(this, getTypeCommande(), getExpr());
            return ide_inter.reconnaitre(instruction);
        }else{
            throw new GrammaireException(getTypeCommande());
        }
    }
}
