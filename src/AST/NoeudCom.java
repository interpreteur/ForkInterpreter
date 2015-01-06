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
public class NoeudCom  extends Noeud {

    public NoeudCom(Noeud etatPrecedent, String typeCommande, Expression expr) {
        super(etatPrecedent, typeCommande, expr);
    }

    
    
    
    
    
    @Override
     public String reconnaitre(String instruction) throws ComandeNonReconnu{
        enleverLesEspaces(instruction);
        
        Character car = Scan.charAt(Scan.INDEX,instruction);
        if(estUneAccoladeFermante(car)){
            NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
            return accF.reconnaitre(instruction);
        }
        if(estUneAccoladeOuvrante(car)){
            NoeudAccO accO = new NoeudAccO(this, getTypeCommande(), null);
            return accO.reconnaitre(instruction);
        }
        if( estPointVirgule(car) ){ // si le caractere est un ( ; )
            Scan.readNextChar(instruction); // lecture du caractere ( ; )
            enleverLesEspaces(instruction);
            
            if(enFinDeMot(instruction) ){  
                throw new ComandeNonReconnu(Typecommande.sequence);
            }else if(getEtatPrecedent() != null ){  //permet de gerer une erreur de sequence
                car = Scan.charAt(Scan.INDEX,instruction);
                if (estUneLettre(car)) { // on verifie si, il ya simplement un point virgule (ou n'importe quoi apres le pointVirgule)
                    String motLuEnAvance = lireMotEnAvance(instruction);
                    if(estUnIDE(motLuEnAvance) || estUnMotReserve(motLuEnAvance)){
                        return reconnaitre(instruction);
                    }else{
                        throw new ComandeNonReconnu(Typecommande.sequence);
                    }
                }
            }
        }else if(estUneLettre(car)){
            String mot = lireMot(instruction); 
            enleverLesEspaces(instruction);
            
            if(  estUnIDE(mot) ){               // si ce n'est pas un mot reservé (c'est un identificateur) alors                   
                car = Scan.charAt(Scan.INDEX, instruction);
                if( est2Points(car) ){ // si le caractere lu est ( : )
                    Scan.readNextChar(instruction); // lecture du caractere( : )
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if( estUnEgale(car) ){  // si le caractere lu est ( = )
                        enleverLesEspaces(instruction);
                        Scan.readNextChar(instruction); // lecture du caractere( = )
                        enleverLesEspaces(instruction);
                        NoeudNExp nExp = new NoeudNExp(this,Typecommande.assignation,null);
                        return nExp.reconnaitre(instruction);
                    }else{
                        throw new ComandeNonReconnu(Typecommande.assignation);
                    }
                }else{
                    throw new ComandeNonReconnu(Typecommande.assignation);
                }  
                
            }else if (estUnMotReserve(mot) ){ // si c'est un mot réservé
                if( mot.equalsIgnoreCase("fork") ){
                    Scan.COMPTEUR_FORK++;  // lecture d'un fork
                    enleverLesEspaces(instruction);
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if (estUneParentheseOuvrante(car)){  // Si il ya une parenthese apres le IF alors 
                        //System.out.println("yoooooyoyoyoyoyoo");
                        NoeudPO po = new NoeudPO(this, Typecommande.conditionelle, null);
                        return po.reconnaitre(instruction);
                    }
                }else
                if( mot.equalsIgnoreCase("if") ){
                    Scan.COMPTEUR_IF++;  // lecture d'un IF
                    enleverLesEspaces(instruction);
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if (estUneParentheseOuvrante(car)){  // Si il ya une parenthese apres le IF alors 
                        //System.out.println("yoooooyoyoyoyoyoo");
                        NoeudPO po = new NoeudPO(this, Typecommande.conditionelle, null);
                        return po.reconnaitre(instruction);
                    }else{ // si il n'ya pas de parenthese apres le IF
                        NoeudBExp bexp = new NoeudBExp(this,Typecommande.conditionelle, null);
                        return bexp.reconnaitre(instruction);
                    }
                }else if( mot.equalsIgnoreCase("while") ){
                    Scan.COMPTEUR_WHILE++; // lecture de WHILE
                    enleverLesEspaces(instruction);
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if (estUneParentheseOuvrante(car)){  // Si il ya une parenthese apres le IF alors 
                        //System.out.println("yoooooyoyoyoyoyoo");
                        NoeudPO po = new NoeudPO(this, Typecommande.conditionelle, null);
                        return po.reconnaitre(instruction);
                    }else{ // si il n'ya pas de parenthese apres le IF
                        NoeudBExp bexp = new NoeudBExp(this,Typecommande.iteration, null);
                        return bexp.reconnaitre(instruction);
                    }
                }else if(mot.equalsIgnoreCase("let")){  // Si c'est un LET, On va dans IDE_inter
                    enleverLesEspaces(instruction);
                    Scan.COMPTEUR_LetEnd++;
                    NoeudIDE ide_inter = new NoeudIDE(this, Typecommande.declarationVariable, null);
                    return ide_inter.reconnaitre(instruction);
                }else if(mot.equalsIgnoreCase("else")){
                    //System.out.println("MONSTRE333333333333333");
                    Scan.COMPTEUR_IF--;
                    Scan.COMPTEUR_THEN--;
                    //System.out.println("mimimmiiiiiiii");
                    return reconnaitre(instruction);
                }else if (mot.equalsIgnoreCase("then")) {
                    Scan.COMPTEUR_THEN++;
                    return reconnaitre(instruction);
                }
                else if(mot.equalsIgnoreCase("end")){
                    //System.out.println("je suis dans END");
                    Scan.COMPTEUR_LetEnd-- ;
                    
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if (estUneAccoladeFermante(car)) {
                        NoeudAccF accF = new NoeudAccF(this, getTypeCommande(), null);
                        return accF.reconnaitre(instruction);
                    }else if(enFinDeMot(instruction) ){
                        /*
                        System.out.println("cptLET_END = "+Scan.COMPTEUR_LetEnd);
                        System.out.println("cptIF == "+Scan.COMPTEUR_IF);
                        System.out.println("cptWHILE == "+Scan.COMPTEUR_WHILE);
                        System.out.println("cptAccolade == "+Scan.COMPTEUR_accolade);
                        System.out.println("cptParenthese == "+Scan.COMPTEUR_parenthese);
                        System.out.println("cptTHEN == "+Scan.COMPTEUR_THEN);
                        */
                       if(Scan.COMPTEUR_LetEnd==0 && Scan.COMPTEUR_parenthese==0 
                             && Scan.COMPTEUR_accolade==0 && Scan.COMPTEUR_IF==0
                             && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0){  //permet de tester si, les LET et END sont bien mis
                                  return "--Grammaire Reconnu --";
                       }else {
                              throw new ComandeNonReconnu(getTypeCommande());
                       }
                    }
                    
                    if(Scan.COMPTEUR_LetEnd >  0){
                        car = Scan.charAt(Scan.INDEX, instruction);
                        if( estPointVirgule(car)){
                             NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                             return com.reconnaitre(instruction);
                        }else if(estUneLettre(car)){
                                NoeudCom com = new NoeudCom(this, getTypeCommande(), null); // apres le mot ( end ) , on garde le message d'exception precedent 
                                return com.reconnaitre(instruction);
                        }else{
                             throw new ComandeNonReconnu(getTypeCommande());
                        }
                    }else if(Scan.COMPTEUR_LetEnd == 0){
                                car = Scan.charAt(Scan.INDEX, instruction);
                                if( estPointVirgule(car)){
                                    enleverLesEspaces(instruction);
                                    return reconnaitre(instruction);
                                }
                                if(enFinDeMot(instruction) ){
                                    // LA COMMANDE est RECONNU ICI !
                                    //System.out.println("------Reconnaissance totale de la grammaire ----");
                                    if(Scan.COMPTEUR_LetEnd==0 && Scan.COMPTEUR_parenthese==0 
                                            && Scan.COMPTEUR_accolade==0 && Scan.COMPTEUR_IF==0
                                            && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0){  //permet de tester si, les LET et END sont bien mis
                                        return "--Grammaire Reconnu --";
                                    }else {
                                        throw new ComandeNonReconnu(getTypeCommande());
                                    }
                                }else{
                                    throw new ComandeNonReconnu("Declaration ou Aliasing");
                                }
                    }else{
                        throw new ComandeNonReconnu("Declaration ou Aliasing");
                    }
                }
                else if(mot.equalsIgnoreCase("wait")){
                    Scan.COMPTEUR_WAIT++;
                    enleverLesEspaces(instruction);
                    car = Scan.charAt(Scan.INDEX, instruction);
                    if (estUneParentheseOuvrante(car)){  // Si il ya une parenthese apres le IF alors 
                        //System.out.println("yoooooyoyoyoyoyoo");
                        NoeudPO po = new NoeudPO(this, Typecommande.conditionelle, null);
                        return po.reconnaitre(instruction);
                    }
                }
            }
        }
        if(enFinDeMot(instruction) ){
            if( ! estPointVirgule(car) ){
                if(Scan.COMPTEUR_LetEnd==0  && Scan.COMPTEUR_accolade==0 
                        && Scan.COMPTEUR_parenthese==0 && Scan.COMPTEUR_IF==0
                        && Scan.COMPTEUR_THEN==0 && Scan.COMPTEUR_WHILE==0 
                        && Scan.COMPTEUR_FORK==0 && Scan.COMPTEUR_WAIT==0){  //permet de tester si, les LET et END sont bien mis
                    return "---Commande Reconnu---";
                }else{
                    throw new ComandeNonReconnu(getTypeCommande());
                }
            }
        }else{
            throw new ComandeNonReconnu("Grammaire");
        }
        return "dddddddddddddd";
    }
}
