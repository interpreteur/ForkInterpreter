/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

/**
 *
 * @author ishola hamed
 */
public abstract class Expression {

    
    private Expression expGauche;
    private String operateur;
    private Expression expDroite;
    
    
    //permet de savoir si l'expression est complete
    private boolean exprComplete;
    
    //permet de savoir si l'expression est parenthésé ou PAS !
    private boolean estParentheser;

    public Expression() {
        this.exprComplete = false;
        this.estParentheser = false;
    }

    public Expression(Expression expGauche, String operateur, Expression expDroite, boolean exprComplete) {
        this.expGauche = expGauche;
        this.operateur = operateur;
        this.expDroite = expDroite;
        this.exprComplete = exprComplete;
    }

    public Expression(Expression expGauche, String operateur, Expression expDroite) {
        this.expGauche = expGauche;
        this.operateur = operateur;
        this.expDroite = expDroite;
        this.exprComplete = false;
        this.estParentheser = false;
    }

    public Expression(Expression expGauche, String operateur, Expression expDroite, boolean exprComplete, boolean estParentheser) {
        this.expGauche = expGauche;
        this.operateur = operateur;
        this.expDroite = expDroite;
        this.exprComplete = exprComplete;
        this.estParentheser = estParentheser;
    }

    
    
    public boolean isEstParentheser() {
        return estParentheser;
    }

    public void setEstParentheser(boolean estParentheser) {
        this.estParentheser = estParentheser;
    }
    
    
    

    public Expression getExpGauche() {
        return expGauche;
    }

    public void setExpGauche(Expression expGauche) {
        this.expGauche = expGauche;
    }

    public String getOperateur() {
        return operateur;
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }

    public Expression getExpDroite() {
        return expDroite;
    }

    public void setExpDroite(Expression expDroite) {
        this.expDroite = expDroite;
    }

    
    
    
    
    public boolean isExprComplete() {
        return exprComplete;
    }

    public void setExprComplete(boolean exprComplete) {
        this.exprComplete = exprComplete;
    }
    
    
    
    public abstract int valeur();
    
    public abstract boolean evaluer();

}
