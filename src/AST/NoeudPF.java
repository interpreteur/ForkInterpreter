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
public class NoeudPF extends Noeud {

    public NoeudPF(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu {
        Scan.readNextChar(instruction); // lecture de la parenthese fermente
        Scan.COMPTEUR_parenthese --;   // on decremente le compteur des parentheses, pour signaler qu'on a rencontré un parenthese fermente
        enleverLesEspaces(instruction);
        
        if( enFinDeMot(instruction) ){
            if(Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                  && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                  && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0  ){  //permet de tester si, les parenthese (OUVRANTES) et (FERMENTE) sont bien mis
                return "-- Reconnu --";
            }else {
                throw new ComandeNonReconnu(getTypeCommande());
            }
        }
        
        Character car = Scan.charAt(Scan.INDEX, instruction);
        if(estUneParentheseFermante(car)){ // permet de gerer la double parenthèse fermante 
            return reconnaitre(instruction);
        }else if (estUneAccoladeFermante(car)) {
            NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
            return accF.reconnaitre(instruction);
        } else if(estPointVirgule(car)){
            NoeudCom com = new NoeudCom(this, getTypeCommande(), null);
            return com.reconnaitre(instruction);
        }else if(estUnOperateurArithmetique(car)){ // Si on trouve un operateur Arithmetique
            NoeudNOp nop = new NoeudNOp(this, getTypeCommande(), null);
            return nop.reconnaitre(instruction);
        }else if( estUnComparateur(car) ){
            NoeudCOp cop = new NoeudCOp(this, getTypeCommande(), null);
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
                NoeudComMilieu com_inter = new NoeudComMilieu(this, getTypeCommande(), null);
                return com_inter.reconnaitre(instruction);
                
            }else if( mot.equalsIgnoreCase("or") || mot.equalsIgnoreCase("and")){
                if( mot.equalsIgnoreCase("or") ){
                    Scan.INDEX-=2; // on decremente le compteur du scanner de -2, car (OR) n'est pas lu dans cet etat
                }else if( mot.equalsIgnoreCase("and") ){
                    Scan.INDEX-=3; // on decremente le compteur du scanner de -3, car (AND) n'est pas lu dans cet etat
                }
                if( getTypeCommande().equalsIgnoreCase(Typecommande.conditionelle)){
                    NoeudBOp bop = new NoeudBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }
            }else if(mot.equalsIgnoreCase("end")){
                Scan.COMPTEUR_LetEnd --; //On decremente le compteur_LetEnd, pour signaler qu'un END a été lu 
                
                car = Scan.charAt(Scan.INDEX, instruction);
                if (estUneAccoladeFermante(car)) {
                    NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
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
                        throw new ComandeNonReconnu(getTypeCommande());
                    }
                }
            }else{
                throw new ComandeNonReconnu(getTypeCommande());
            }
        }else{
                throw new ComandeNonReconnu(getTypeCommande());
        }
        return "ggggggggggggggg";
    }
    
}
