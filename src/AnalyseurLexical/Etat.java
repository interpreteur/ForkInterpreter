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
public abstract class Etat {

    protected Etat etatPrecedent;
    protected String typeCommande;
    protected Expression expr;

    public Etat(Etat etatPrecedent, String typeCommande, Expression expr) {
        this.etatPrecedent = etatPrecedent;
        this.typeCommande = typeCommande;
        this.expr = expr;
    }

    
    
    /**
     * permet de reconnaitre un COM ou  BEXP ou  IDE ou  NUM, etc........
     * @param instruction
     * @return 
     */
    public abstract String reconnaitre(String instruction) throws GrammaireException;

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }
    
    public String getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(String typeCommande) {
        this.typeCommande = typeCommande;
    }
    
    
    
    public Etat getEtatPrecedent() {
        return etatPrecedent;
    }

    public void setEtatPrecedent(Etat etatPrecedent) {
        this.etatPrecedent = etatPrecedent;
    }

    
    /**
     * Teste si un caractere quelconque, est un chiffre
     * @param c
     * @return 
     */
    public  boolean estUnChiffre(Character c){
        boolean res;
        try {
            Integer.parseInt(c+"");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
    /**
     * permet de tester si, un caractere quelconque est une lettre de l'alphabet
     * @param car
     * @return 
     */
    public boolean estUneLettre(Character car){
        if (car != null) {
            int codeASCII = (int)car;    
            if( (codeASCII >= 97 && codeASCII <= 122)   
                    ||  codeASCII >= 65  &&  codeASCII <= 90 ){
                return true;
            }
        }
        return false;
    }
    
    
    
    /**
     * test si un caractere est egale au caractere ( : )
     * @param car
     * @return 
     */
    public boolean est2Points(Character car){
        if (car != null) {
            return (int)car == 58;
        }else{
            return false;
        }
    }
    
    /**
     * test si un caractere est egale au caractere ( = )
     * @param car
     * @return 
     */
    public boolean estUnEgale(Character car){
        if (car != null) {
            return (int)car == 61;
        } else {
            return false;
        }
        
    }
    
    /**
     * test si un caractere est un ( ; )
     * @param car
     * @return 
     */
    public boolean estPointVirgule(Character car){
        if( car != null ){
            return (int)car == 59;
        }else{
            return false;
        }
    }
    
    
    
    /**
     * permet de lire un mot
     * @param instruction
     * @return retourne le mot lu
     */
    public String lireMot(String instruction){
        StringBuilder sb = new StringBuilder();
        
        Character car = Scan.charAt(Scan.INDEX, instruction);
        while( estUneLettre(car)  ){
            sb.append(car);
            car = Scan.readNextChar(instruction);
            //car = Scanner.charAt(Scanner.INDEX, chaineCourante);
            //Scanner.INDEX++;
        }
        return sb.toString();
    }
    
    
    public String lireMotEnAvance(String instruction){
        StringBuilder sb = new StringBuilder();
        int i = Scan.INDEX;
        Character car = Scan.charAt( i, instruction);
        while( estUneLettre(car) ){
            i++;
            sb.append(car);
            car = Scan.charAt( i, instruction);
        }
        return sb.toString();
    }
    
    
    /**
     * permet de lire tous les entiers
     * @param instruction
     * @return 
     */
    public int lireEntier(String instruction){
        int entier = 0; // Valeur par defaut, quand aucun chiffre n'est lu !
        Character car = Scan.charAt(Scan.INDEX, instruction);
        if( estUnChiffre(car) ){
            entier = lireLesChiffres(instruction);
        }
        return entier;
    }
    
    
    /**
     * permet de lire  un entier
     * @param instruction
     * @return 
     */
    public int lireLesChiffres(String instruction){
        StringBuilder sb = new StringBuilder();
        
        Character car = Scan.charAt(Scan.INDEX, instruction);
        while(estUnChiffre(car) ){   
            sb.append(car);
            car = Scan.readNextChar(instruction);
        }
        try {
            int val = Integer.parseInt(sb.toString());
            return val;
        } catch (Exception e) {
            return 0; // Valeur par defaut, quand aucun chiffre n'est lu !
        }
        
    }
    
    
    /**
     * teste si le mot lu est un mot reservé du language
     * @param motLu
     * @return 
     */
    public String motReserve(String motLu){
        if(motLu.compareToIgnoreCase("if")==0  ||  motLu.compareToIgnoreCase("then")==0   
                || motLu.compareToIgnoreCase("else")==0  || motLu.compareToIgnoreCase("while")==0
                || motLu.compareToIgnoreCase("do")==0 || motLu.compareToIgnoreCase("let")==0
                || motLu.compareToIgnoreCase("in")==0 || motLu.compareToIgnoreCase("end")==0
                || motLu.compareToIgnoreCase("be")==0 || motLu.compareToIgnoreCase("fork")==0 
                || motLu.compareToIgnoreCase("wait")==0 ){
            return motLu;
        }else{
            return null;
        }
    }
    
    /**
     * permet de tester si, un caractere quelconque lu, est un espace
     * @param car
     * @return 
     */
    public boolean estUnEspace(Character car){
        if (car != null) {
            return (int)car == 32 ;
        }
        return false;
    }
    
    public void enleverLesEspaces(String instruction){
        Character car;
        if( Scan.INDEX == -1){
            car = Scan.charAt(++Scan.INDEX, instruction);
        }else{
            car = Scan.charAt(Scan.INDEX, instruction);
        }
        if( estUnEspace(car) ){
                lireLesEspaces(instruction);
        }    
    }
    
    
    /**
     * permet de lire les espaces d'un instruction
     * @param instruction 
     */
    public void lireLesEspaces(String instruction){
        Character car = Scan.charAt(Scan.INDEX, instruction);
        while( estUnEspace(car) ){
            //System.out.println("SCANNER == "+Scanner.INDEX);
           car = Scan.readNextChar(instruction);
        }
    }
    
    
    /**
     * permet de lire le caractere en parametre, et renvoie le meme caractere, si c'est un operateur, 
     * sinon il renvoie NULL
     * @param car
     * @return 
     */
    public Character operateur(Character car){
        switch (car){
                case '+' :  return '+';
                case '-' :  return '-';
                case '*' :  return '*';
                case '/' :  return '/';
                default  : return null;
        }
    }
    
    /**
     * retourne le caractere si le caratere donnée en parametre est un OPERATEUR
     * Sinon il retourne NULL
     * @param car
     * @return 
     */
    public boolean estUnOperateurArithmetique(Character car){
        return operateur(car) != null;
    }
    
    
    
    /**
     * permet de verifier si le caractere est un caractere de comparaison
     * @param car
     * @return 
     */
    public Character comparateur(Character car){
        switch (car) {
            case '<' : return '<';
            case '=' : return '=';
            case '>' : return '>';
            default  : return null;
        }
    }
    
    
    /**
     * test si le caractere est un booleen
     * @param car
     * @return 
     */
    public boolean estUnComparateur(Character car){
        return comparateur(car) != null;
    }
    
    
    /**
     * permet de tester si, ce caractere est le debut d'une expression 
     * @param car
     * @return 
     */
    public boolean estUneExpression(Character car){
        if(estUnChiffre(car)  || estUneLettre(car) || estUneParentheseOuvrante(car)){
            /**
             * il faut aussi tester si le caractere n'est  pas  une parenthèse
             */
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Permet de tester si le mot en paramètre est un IDE
     * @param mot
     * @return 
     */
    public boolean estUnIDE(String mot){
        return motReserve(mot) == null;
    }
    
    
    public boolean estUnMotReserve(String mot){
        return motReserve(mot) != null;
    }
    
    
    
    /**
     * permet de tester si, on est en fin de mot
     * @param instruction
     * @return 
     */
    public boolean enFinDeMot(String instruction){
        Character car = Scan.charAt(Scan.INDEX+1, instruction);
            if( car == null){
                return true;
            }
        return false;
    }
    
    /**
     * permet de tester si, un mot en parametere est un BOOLEEN
     * @param mot
     * @return 
     */
    public boolean estUnBooleen(String mot){
        if(mot.equalsIgnoreCase("true") || mot.equalsIgnoreCase("false")){
            return true;
        }
        return false;
    }
    
    public boolean estUneParentheseOuvrante(Character car){
        return (int)car == 40;
    }
    
    
    public boolean estUneParentheseFermante(Character car){
        return (int)car == 41;
    }
    
    
    public boolean estUnOperateurBooleen(String mot){
        if(mot.equalsIgnoreCase("and") || mot.equalsIgnoreCase("or")){
            return true;
        }else{
            return false;
        }
    }
    
    
    public boolean estUneAssignation(String messageException){
        return messageException.equalsIgnoreCase(Constante.assignation);
    }
    
    public boolean estUneIteration(String messageException){
        return messageException.equalsIgnoreCase(Constante.iteration);
    }
    
    public boolean estUneSequence(String messageException){
        return messageException.equalsIgnoreCase(Constante.sequence);
    }
    
    public boolean estUneConditionelle(String messageException){
        return messageException.equalsIgnoreCase(Constante.conditionelle);
    }
    
    public boolean estUneDeclarationVariable(String messageException){
        return messageException.equalsIgnoreCase(Constante.declarationVariable);
    }
    
    public boolean estUnAliasing(String messageException){
        return messageException.equalsIgnoreCase(Constante.aliasing);
    }
    
    public boolean estUneAccoladeOuvrante(Character car){
        return car == 123;
    }
    
    public boolean estUneAccoladeFermante(Character car){
        return car == 125;
    }
    
    
    
    /*
     * permet d'afficher le mot en parametre
     */
    public void afficherMot(String mot){
        for (int i = 0; i < mot.length(); i++) {
                System.out.println("["+mot.charAt(i)+"]");
        }
    }
    
    
}
