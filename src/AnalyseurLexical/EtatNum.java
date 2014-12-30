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
public class EtatNum extends Etat {

    public EtatNum(Etat etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws GrammaireException{
        int entierLu = lireEntier(instruction); // lecture de l'entier
        enleverLesEspaces(instruction);
        
        Character car = Scan.charAt(Scan.INDEX, instruction);
        if( estUneParentheseFermante(car)){ 
            EtatParentheseFermante pferm = new EtatParentheseFermante(this, getTypeCommande(), null);
            return pferm.reconnaitre(instruction);
        }else if (estUneAccoladeFermante(car)) { // Si on trouve une accolade fermante
            EtatAccoladeFermante accF = new EtatAccoladeFermante(this, getTypeCommande(), null);
            return accF.reconnaitre(instruction);
        }else if( estUneLettre(car)){ // permet de tester si le mot (end) a été rencontré
               
                String chaine = lireMot(instruction);

                /***********************************************************/
                // IL ne faut pas enlever les espaces avant de faire le test de (OR) ou le (AND)
                if( chaine.equalsIgnoreCase("or") ){
                    Scan.INDEX -=2; //on decremente le compteur de lecture car (OR) ne doit pas etre lu dans (l'EtatNum) 
                    EtatBOp bop = new EtatBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }
                if ( chaine.equalsIgnoreCase("and") ){
                    Scan.INDEX -=3; //on decremente le compteur de lecture car (AND) ne doit pas etre lu dans (l'EtatNum) 
                    EtatBOp bop = new EtatBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }
                /**********************************************************/
                
                
                
                
                /*********************************************************/
                if( chaine.equalsIgnoreCase("then") || chaine.equals("do")
                        || chaine.equalsIgnoreCase("else")) {
                    
                    if (chaine.equalsIgnoreCase("then")) {
                                Scan.INDEX -=4; //on decremente le compteur de lecture car (ELSE) ne doit pas etre lu dans (l'EtatCom)
                    }
                    if (chaine.equalsIgnoreCase("else")) {
                        Scan.INDEX -=4; //on decremente le compteur de lecture car (ELSE) ne doit pas etre lu dans (l'EtatCom)
                    }
                    if (chaine.equalsIgnoreCase("do")) {
                        Scan.COMPTEUR_WHILE--; // un DO correspond à un WHILE
                    }
                    EtatCom_inter com_inter = new EtatCom_inter(this, getTypeCommande(), null);
                    return com_inter.reconnaitre(instruction);
                }
                /*********************************************************/
                
                if(chaine.equalsIgnoreCase("end")){
                    enleverLesEspaces(instruction);
                    Scan.COMPTEUR_LetEnd-- ;  // decremente le nombre de LET_END, pour signaler la lecture de END
                    
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if (estUneAccoladeFermante(car)) {
                        EtatAccoladeFermante accF = new EtatAccoladeFermante(this, getTypeCommande(), null);
                        return accF.reconnaitre(instruction);
                    }
                    if(Scan.COMPTEUR_LetEnd >  0){
                        car = Scan.charAt(Scan.INDEX, instruction);
                        //System.out.println("car ================ "+car);
                        if( estPointVirgule(car)){
                             EtatCom com = new EtatCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde la commande precedent 
                             return com.reconnaitre(instruction);
                        }else if(estUneLettre(car)){
                                EtatCom com = new EtatCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message de la commande precedent 
                                return com.reconnaitre(instruction);
                        }else if (estUneAccoladeFermante(car)) { // Si on trouve une accolade fermante
                            EtatAccoladeFermante accF = new EtatAccoladeFermante(this, getTypeCommande(), null);
                            return accF.reconnaitre(instruction);
                        }else{
                             //System.out.println("gagagagagaga");
                             throw new GrammaireException(getTypeCommande());
                        }
                    }else if(Scan.COMPTEUR_LetEnd == 0){ //permet de tester si, les LET et END sont bien mis
                            
                            /*
                            System.out.println("cptLET_END = "+Scan.COMPTEUR_LetEnd);
                            System.out.println("cptIF == "+Scan.COMPTEUR_IF);
                            System.out.println("cptWHILE == "+Scan.COMPTEUR_WHILE);
                            System.out.println("cptAccolade == "+Scan.COMPTEUR_accolade);
                            System.out.println("cptParenthese == "+Scan.COMPTEUR_parenthese);
                            System.out.println("cptTHEN == "+Scan.COMPTEUR_THEN);
                            */
                            
                            if( enFinDeMot(instruction)  ){
                                if (Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                                      && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                                     && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0) {
                                    return "--Grammaire Reconnue --";
                                }
                            }
                            car = Scan.charAt(Scan.INDEX, instruction);
                            if( estPointVirgule(car)){
                                EtatCom com = new EtatCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                return com.reconnaitre(instruction);
                            }else if(estUneLettre(car)){
                                EtatCom com = new EtatCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                return com.reconnaitre(instruction);
                            }else{
                                throw new GrammaireException(Constante.declarationOuAliasing);
                            }
                    }else{
                        throw new GrammaireException(Constante.declarationOuAliasing);
                    }
                }else{
                    throw new GrammaireException(getTypeCommande());
                }
        }
        if( estPointVirgule(car) ){ // Si un caractere (;) est rencontré, on repart à l'état COM
                    EtatCom com = new EtatCom(this, null, null); //NULL == apres le caractere ( ; ) , on repart à une nouvelle commande 
                    return com.reconnaitre(instruction);
        }
        if(estUnOperateurArithmetique(car)){ // Si le caractere lu est un operateur
                    EtatNOp nop = new EtatNOp(this,getTypeCommande(), null);
                    return nop.reconnaitre(instruction);
        }
        if( estUnComparateur(car) ){ // Si c'est le caractere est un comparateur
                    EtatCOp cop = new EtatCOp(this,getTypeCommande(), null);
                    return cop.reconnaitre(instruction);
        }
        
        if( enFinDeMot(instruction) ){  
            
            //System.out.println("coucouccccccc");
            // Si le chiffre est reconnu et qu'il  a des espaces à la fin
            // Si le dernier chiffre est un Nombre, et qu'il est suivi de rien alors !
            if(  (Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                        && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                    && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0 )
                    ||  
                             (estUnChiffre(instruction.charAt(instruction.length()-1)) &&
                                (Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                                    && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                                    && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0))
                    ){  //permet de tester si, les LET et END sont bien mis
                return "--Grammaire Reconnu --";
            }else {
                throw new GrammaireException(getTypeCommande());
            }
        }else{
                throw new GrammaireException(getTypeCommande());
        }
    }
}

