/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

/**
 *
 * @author Abdoulaye
 */
public abstract class Fork {
    private String partieGauche;
    private String operateur;
    private String partieDroite;

    public Fork(String partieGauche, String operateur, String partieDroite) {
        this.partieGauche = partieGauche;
        this.operateur = operateur;
        this.partieDroite = partieDroite;
    }

    public String getPartieGauche() {
        return partieGauche;
    }

    public void setPartieGauche(String partieGauche) {
        this.partieGauche = partieGauche;
    }

    public String getOperateur() {
        return operateur;
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }

    public String getPartieDroite() {
        return partieDroite;
    }

    public void setPartieDroite(String partieDroite) {
        this.partieDroite = partieDroite;
    }
    
    public abstract int valeur();
    
    public abstract boolean evaluer();
}
