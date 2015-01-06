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
public class NoeudNum extends Noeud {

    public NoeudNum(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    @Override
    public String reconnaitre(String instruction) throws ComandeNonReconnu{
        int entierLu = lireEntier(instruction); // lecture de l'entier
        enleverLesEspaces(instruction);
        
        Character car = Scan.charAt(Scan.INDEX, instruction);
        if( estUneParentheseFermante(car)){ 
            NoeudPF pferm = new NoeudPF(this, getTypeCommande(), null);
            return pferm.reconnaitre(instruction);
        }else if (estUneAccoladeFermante(car)) { // Si on trouve une accolade fermante
            NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
            return accF.reconnaitre(instruction);
        }else if( estUneLettre(car)){ // permet de tester si le mot (end) a été rencontré
               
                String chaine = lireMot(instruction);

                /***********************************************************/
                // IL ne faut pas enlever les espaces avant de faire le test de (OR) ou le (AND)
                if( chaine.equalsIgnoreCase("or") ){
                    Scan.INDEX -=2; //on decremente le compteur de lecture car (OR) ne doit pas etre lu dans (l'NoeudNum) 
                    NoeudBOp bop = new NoeudBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }
                if ( chaine.equalsIgnoreCase("and") ){
                    Scan.INDEX -=3; //on decremente le compteur de lecture car (AND) ne doit pas etre lu dans (l'NoeudNum) 
                    NoeudBOp bop = new NoeudBOp(this, getTypeCommande(), null);
                    return bop.reconnaitre(instruction);
                }
                /**********************************************************/
                
                
                
                
                /*********************************************************/
                if( chaine.equalsIgnoreCase("then") || chaine.equals("do")
                        || chaine.equalsIgnoreCase("else")) {
                    
                    if (chaine.equalsIgnoreCase("then")) {
                                Scan.INDEX -=4; //on decremente le compteur de lecture car (ELSE) ne doit pas etre lu dans (l'NoeudCom)
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
                /*********************************************************/
                
                if(chaine.equalsIgnoreCase("end")){
                    enleverLesEspaces(instruction);
                    Scan.COMPTEUR_LetEnd-- ;  // decremente le nombre de LET_END, pour signaler la lecture de END
                    
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if (estUneAccoladeFermante(car)) {
                        NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
                        return accF.reconnaitre(instruction);
                    }
                    if(Scan.COMPTEUR_LetEnd >  0){
                        car = Scan.charAt(Scan.INDEX, instruction);
                        //System.out.println("car ================ "+car);
                        if( estPointVirgule(car)){
                             NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde la commande precedent 
                             return com.reconnaitre(instruction);
                        }else if(estUneLettre(car)){
                                NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message de la commande precedent 
                                return com.reconnaitre(instruction);
                        }else if (estUneAccoladeFermante(car)) { // Si on trouve une accolade fermante
                            NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
                            return accF.reconnaitre(instruction);
                        }else{
                             //System.out.println("gagagagagaga");
                             throw new ComandeNonReconnu(getTypeCommande());
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
                                NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                return com.reconnaitre(instruction);
                            }else if(estUneLettre(car)){
                                NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                return com.reconnaitre(instruction);
                            }else{
                                throw new ComandeNonReconnu(Typecommande.declarationOuAliasing);
                            }
                    }else{
                        throw new ComandeNonReconnu(Typecommande.declarationOuAliasing);
                    }
                }else{
                    throw new ComandeNonReconnu(getTypeCommande());
                }
        }
        if( estPointVirgule(car) ){ // Si un caractere (;) est rencontré, on repart à l'état COM
                    NoeudCom com = new NoeudCom(this, null, null); //NULL == apres le caractere ( ; ) , on repart à une nouvelle commande 
                    return com.reconnaitre(instruction);
        }
        if(estUnOperateurArithmetique(car)){ // Si le caractere lu est un operateur
                    NoeudNOp nop = new NoeudNOp(this,getTypeCommande(), null);
                    return nop.reconnaitre(instruction);
        }
        if( estUnComparateur(car) ){ // Si c'est le caractere est un comparateur
                    NoeudCOp cop = new NoeudCOp(this,getTypeCommande(), null);
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
                throw new ComandeNonReconnu(getTypeCommande());
            }
        }else{
                throw new ComandeNonReconnu(getTypeCommande());
        }
    }
}

