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
public class EtatParentheseFermante extends Etat {

    public EtatParentheseFermante(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException {
        Scan.readNextChar(instruction); // lecture de la parenthese fermente
        Scan.COMPTEUR_parenthese --;   // on decremente le compteur des parentheses, pour signaler qu'on a rencontré un parenthese fermente
        enleverLesEspaces(instruction);
        
        if( enFinDeMot(instruction) ){
            if(Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                  && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                  && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0  ){  //permet de tester si, les parenthese (OUVRANTES) et (FERMENTE) sont bien mis
                return "-- Reconnu --";
            }else {
                throw new GrammaireException(getTypeCommande());
            }
        }
        
        Character car = Scan.charAt(Scan.INDEX, instruction);
        if(estUneParentheseFermante(car)){ // permet de gerer la double parenthèse fermante 
            return reconnaitre(instruction);
        }else if (estUneAccoladeFermante(car)) {
            EtatAccoladeFermante accF = new EtatAccoladeFermante(this, getTypeCommande(), null);
            return accF.reconnaitre(instruction);
        } else if(estPointVirgule(car)){
            EtatCom com = new EtatCom(this, getTypeCommande(), null);
            return com.reconnaitre(instruction);
        }else if(estUnOperateurArithmetique(car)){ // Si on trouve un operateur Arithmetique
            EtatNOp nop = new EtatNOp(this, getTypeCommande(), null);
            return nop.reconnaitre(instruction);
        }else if( estUnComparateur(car) ){
            EtatCOp cop = new EtatCOp(this, getTypeCommande(), null);
            return cop.reconnaitre(instruction);
        }else if(estUneLettre(car)){
            String mot = lireMot(instruction);
            if(mot.equalsIgnoreCase("then") || mot.equalsIgnoreCase("else")
                    || mot.equalsIgnoreCase("do") ){
                
                if (mot.equalsIgnoreCase("then")) {
                    Scan.COMPTEUR_THEN++;
                }
                if (mot.equalsIgnoreCase("else")) {
                    Scan.COMPTEUR_THEN--; // un ELSE correspond à un THEN
                    Scan.COMPTEUR_IF--; //un ELSE correspond à un IF
                }
                if (mot.equalsIgnoreCase("do")) {
                    Scan.COMPTEUR_WHILE--; // un DO correspond à un WHILE
                }
                enleverLesEspaces(instruction);
                EtatCom_inter com_inter = new EtatCom_inter(this, getTypeCommande(), null);
                return com_inter.reconnaitre(instruction);
                
            }else if( mot.equalsIgnoreCase("or") || mot.equalsIgnoreCase("and")){
                if( mot.equalsIgnoreCase("or") ){
                    Scan.INDEX-=2; // on decremente le compteur du scanner de -2, car (OR) n'est pas lu dans cet etat
                }else if( mot.equalsIgnoreCase("and") ){
                    Scan.INDEX-=3; // on decremente le compteur du scanner de -3, car (AND) n'est pas lu dans cet etat
                }
                if( getTypeCommande().equalsIgnoreCase(Constante.conditionelle)){
                    EtatBOp bop = new EtatBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }
            }else if(mot.equalsIgnoreCase("end")){
                Scan.COMPTEUR_LetEnd --; //On decremente le compteur_LetEnd, pour signaler qu'un END a été lu 
                
                car = Scan.charAt(Scan.INDEX, instruction);
                if (estUneAccoladeFermante(car)) {
                    EtatAccoladeFermante accF = new EtatAccoladeFermante(this, getTypeCommande(), null);
                    return accF.reconnaitre(instruction);
                }
                
                if( enFinDeMot(instruction) ){
                    if( Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                        && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                        && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0   ){  //permet de tester si, les parenthese (OUVRANTES) et (FERMENTE) sont bien mis
                        //dans cette partie, toute l'expression parenthesée est reconnue
                        //dans l'aliasing | declaration de variable 
                        return "--Grammaire reconnu --";
                    }else {
                        throw new GrammaireException(getTypeCommande());
                    }
                }
            }else{
                throw new GrammaireException(getTypeCommande());
            }
        }else{
                throw new GrammaireException(getTypeCommande());
        }
        return "ggggggggggggggg";
    }
    
}
