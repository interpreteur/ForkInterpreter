/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

/**
 *
 * @author ishola hamed
 */
public class Compteur {
    private int value;

    public Compteur(int value) {
        this.value = value;
    }

    public void incrementer(){
        this.value = this.value+1;
    }
    
    public void decrementer(){
        this.value = this.value-1;
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    
}
