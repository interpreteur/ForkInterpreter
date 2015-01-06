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
public class NoeudIDE  extends Noeud{

    public NoeudIDE(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu{
        Character car = Scan.charAt(Scan.INDEX, instruction);
        //System.out.println("car == ["+car+"]");
        if(estUneLettre(car)){
            //System.out.println("car = "+car);
            String mot = lireMot(instruction);
            enleverLesEspaces(instruction);
            if(  estUnIDE(mot) ){  // si ce n'est pas un mot reservé (c'est un identificateur) alors                   
                //System.out.println("IDE = ["+mot+"]");
                //System.out.println("finMot == "+enFinDeMot(instruction));
                
                if( enFinDeMot(instruction) ){  //permet de tester si on est en fin de Mot
                    //System.out.println("FINNNNNNNNNNNNNNNNNNNNNN");
                        if( Scan.COMPTEUR_IF==0 && Scan.COMPTEUR_THEN==0 
                                && Scan.COMPTEUR_WHILE==0 && Scan.COMPTEUR_LetEnd==0  
                                && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_accolade==0
                                ||  
                              estUneLettre(instruction.charAt(instruction.length()-1)) ){  //permet de tester si, les LET et END sont bien mis
                            return "--Grammaire reconnu--";
                        }
                }
                
                car = Scan.charAt(Scan.INDEX,instruction);
                if(estUneAccoladeFermante(car)){
                    NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
                    return accF.reconnaitre(instruction);
                }
                if(estUneParentheseFermante(car)){
                    NoeudPF pferm = new NoeudPF(this, getTypeCommande(), null);
                    return pferm.reconnaitre(instruction);
                }else if( est2Points(car) ){ // si le caractere lu est ( : )
                    //System.out.println("ppppppppppppppppppppp");
                    Scan.readNextChar(instruction); //lecture du caractere ( : )
                    enleverLesEspaces(instruction);
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if( estUnEgale(car) ){  // si le caractere lu est ( = )
                        Scan.readNextChar(instruction);  //lecture du caractere ( = )
                        enleverLesEspaces(instruction);
                        //System.out.println(" On va dans NExp");
                        NoeudNExp nExp = new NoeudNExp(this,getTypeCommande(), null);
                        return nExp.reconnaitre(instruction);
                    }else{
                        //System.out.println("77777777777777777");
                        throw new ComandeNonReconnu(getTypeCommande());
                    }
                }else if(estUneLettre(car)){  //dans le cas d'une declaration de variable 
                    String chaine = lireMot(instruction); //recherche des mots reservée ( in ) et ( be ) etc..
                    
                    /***********************************************************/
                    // IL ne faut pas enlever les espaces avant de faire le test de (OR) ou le (AND)
                    if( mot.equalsIgnoreCase("or") ){
                        Scan.INDEX -=2; //on decremente le compteur de lecture car (OR) ne doit pas etre lu dans (l'EtatNum) 
                        NoeudBOp bop = new NoeudBOp(this, getTypeCommande(), null);
                        return bop.reconnaitre(instruction);
                    }
                    if ( mot.equalsIgnoreCase("and") ){
                        Scan.INDEX -=3; //on decremente le compteur de lecture car (AND) ne doit pas etre lu dans (l'EtatNum) 
                        NoeudBOp bop = new NoeudBOp(this, getTypeCommande(), null);
                        return bop.reconnaitre(instruction);
                    }
                    /******************************************************/

                    
                    //enleverLesEspaces(instruction);
                    //System.out.println("chaine == "+chaine);
                    if(estUnMotReserve(chaine)){
                        if(chaine.equalsIgnoreCase("be")){
                            enleverLesEspaces(instruction);
                            return reconnaitre(instruction);
                        }
                        if(chaine.equalsIgnoreCase("in")  || chaine.equalsIgnoreCase("then")
                                || chaine.equalsIgnoreCase("do") 
                                || chaine.equalsIgnoreCase("else") ){  //dans le cas de ( let IDE in Com end ) ou (if BExp then) ou (while BExp do)
                            
                            if (chaine.equalsIgnoreCase("then")) {
                                Scan.COMPTEUR_THEN++;
                                Scan.INDEX -=4; //on decremente le compteur de lecture car (THEN) ne doit pas etre lu dans (l'NoeudCom)
                            }
                            if (chaine.equalsIgnoreCase("else")) {
                                Scan.INDEX -=4; //on decremente le compteur de lecture car (ELSE) ne doit pas etre lu dans (l'NoeudCom) 
                            }
                            if (chaine.equalsIgnoreCase("do")) {
                                Scan.COMPTEUR_WHILE--; // un DO correspond à un WHILE
                            }
                            NoeudComMilieu com_inter = new NoeudComMilieu(this, getTypeCommande(), null);
                            return com_inter.reconnaitre(instruction);
                        }
                        if(chaine.equalsIgnoreCase("end")){                            
                            enleverLesEspaces(instruction);
                            Scan.COMPTEUR_LetEnd-- ;
                            //System.out.println("iiiiiiiiiiiiiiiiiiiiiii");
                            car = Scan.charAt(Scan.INDEX, instruction);
                            if (estUneAccoladeFermante(car)) {
                                NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
                                return accF.reconnaitre(instruction);
                            }
                            if(Scan.COMPTEUR_LetEnd >  0){
                                //System.out.println("yyyyyyyyyyyyyyyyyyyy");
                                car = Scan.charAt(Scan.INDEX, instruction);
                                if( estPointVirgule(car)){
                                    enleverLesEspaces(instruction);
                                    NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                    return com.reconnaitre(instruction);
                                }else if(estUneLettre(car)){
                                    String motLuEnAvance=lireMotEnAvance(instruction);
                                    if (motLuEnAvance.equalsIgnoreCase("else")) {
                                        //System.out.println("binnnnnnggggggggg");
                                        Scan.INDEX -=4; //on decremente le compteur de lecture car (ELSE) ne doit pas etre lu dans (l'NoeudCom)
                                        NoeudComMilieu com_inter = new NoeudComMilieu(this, getTypeCommande(), null);
                                        return com_inter.reconnaitre(instruction);
                                    }
                                }else if (estUneAccoladeFermante(car)) { // Si on trouve une accolade fermante
                                    NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
                                    return accF.reconnaitre(instruction);
                                }else{
                                     throw new ComandeNonReconnu(getTypeCommande());
                                }
                            }else if(Scan.COMPTEUR_LetEnd == 0){//permet de tester si, les LET et END sont bien mis
                                    if( enFinDeMot(instruction) ){
                                        if (Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                                                && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                                                && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0) {
                                            
                                            // LE NOMBRE est RECONNU ICI !
                                            return "--Grammaire Reconnue --";
                                        }
                                    }
                                    car = Scan.charAt(Scan.INDEX, instruction);
                                    if( estPointVirgule(car)){
                                        enleverLesEspaces(instruction);
                                        NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                        return com.reconnaitre(instruction);
                                    }else if(estUneLettre(car)){
                                            NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                            return com.reconnaitre(instruction);
                                    }else{
                                         throw new ComandeNonReconnu(Typecommande.declarationOuAliasing);
                                    }
                            }
                        }else{
                            //System.out.println("444444444444444444444444");
                            throw new ComandeNonReconnu(getTypeCommande());
                        }
                    }else if( estUnIDE(chaine) ){   // Si on est le cas ou, on fait [ if (X=2) then ... else ... ]
                            enleverLesEspaces(instruction);
                            NoeudCOp cop = new NoeudCOp(this,getTypeCommande(), null);
                            return cop.reconnaitre(instruction);
                    }else{
                            //System.out.println("3333333333333333333333");
                            throw new ComandeNonReconnu(getTypeCommande());
                    }
                }else if(estPointVirgule(car)){
                        NoeudCom com = new NoeudCom(this, null, null); //NULL == apres le caractere ( ; ) , on repart à une nouvelle commande 
                        return com.reconnaitre(instruction);
                }else if(estUnOperateurArithmetique(car)){
                        enleverLesEspaces(instruction);
                        NoeudNOp nop = new NoeudNOp(this,getTypeCommande(), null);
                        return nop.reconnaitre(instruction);
                }else if(estUnComparateur(car)){
                        enleverLesEspaces(instruction);
                        NoeudCOp cop = new NoeudCOp(this,getTypeCommande(), null);
                        return cop.reconnaitre(instruction);
                }else{
                    //System.out.println("000000000000000000");
                    throw new ComandeNonReconnu(getTypeCommande());
                }  
            }else{  
                 //System.out.println("111111111111");
                 throw new ComandeNonReconnu(getTypeCommande());
                
            }
        }else{
                //System.out.println("22222222222222222");
                throw new ComandeNonReconnu(getTypeCommande());
        }
        return "ffffffffffffffffff";
    }
    
    
}
