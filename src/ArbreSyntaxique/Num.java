/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

/**
 *
 * @author ishola hamed
 */
public class Num extends Expression {

    private int entierNat;

    public Num(int entierNat) {
        this.entierNat = entierNat;
    }

    public int getEntierNat() {
        return entierNat;
    }

    public void setEntierNat(int entierNat) {
        this.entierNat = entierNat;
    }
    
    
    @Override
    public int valeur() {
        return this.entierNat;
    }

    
    
    
    
    /**
     * Methode non-utilisé
     * @return 
     */
    @Override
    public boolean evaluer() {
        return false;
    }

    
    

    
}
