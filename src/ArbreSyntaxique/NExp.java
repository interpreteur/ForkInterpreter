/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;


/**
 *
 * @author ishola hamed
 */
public class NExp extends Expression{

    public NExp() {}

    public NExp(Expression expArithGauche, String operateur, Expression expArithDroite, boolean exprComplete) {
        super(expArithGauche, operateur, expArithDroite, exprComplete);
    }

    public NExp(Expression expArithGauche, String operateur, Expression expArithDroite) {
        super(expArithGauche, operateur, expArithDroite);
    }

    @Override
    public int valeur() {
        String operateur = getOperateur(); 
        //System.out.println("operateur == ["+operateur+"]");
        //if(operateur != null){
            switch  (operateur){
                case "+" : return getExpGauche().valeur() + getExpDroite().valeur();
                case "-" : return getExpGauche().valeur() - getExpDroite().valeur();
                case "*" : return getExpGauche().valeur() * getExpDroite().valeur();
                case "/" : return getExpGauche().valeur() / getExpDroite().valeur();
                default  : return 0;
            }
    }

    
    
    
    
    
    
    
    
    
     /**
     * fonction Non-utilisé
     * @return 
     */
    @Override
    public boolean evaluer() {
        return false;
    }
}