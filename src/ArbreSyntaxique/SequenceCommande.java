/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

/**
 *
 * @author ishola hamed
 */
public class SequenceCommande {
    
   private int indexPointVirgule;
   private LanceurCom executeur;

    public SequenceCommande() {
    }

   
   
    public SequenceCommande(int indexPointVirgule, LanceurCom executeurCmd) {
        this.indexPointVirgule = indexPointVirgule;
        this.executeur = executeurCmd;
    }

    public int getIndexPointVirgule() {
        return indexPointVirgule;
    }

    public void setIndexPointVirgule(int indexPointVirgule) {
        this.indexPointVirgule = indexPointVirgule;
    }

    public LanceurCom getExecuteur() {
        return executeur;
    }

    public void setExecuteur(LanceurCom executeur) {
        this.executeur = executeur;
    }

    

    
   
    public static void main(String[] args) {
        SequenceCommande seq = new SequenceCommande();
        seq.setIndexPointVirgule(1);
        System.out.println("PV = "+seq.getIndexPointVirgule());
    }
   
}
