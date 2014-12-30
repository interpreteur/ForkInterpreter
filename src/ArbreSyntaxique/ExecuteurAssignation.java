/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

import static ArbreSyntaxique.Executeur.environnement;
import Exceptions.ExceptionVariableNonDeclaree;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author ishola hamed
 */
public class ExecuteurAssignation extends Executeur {
    
    private String ide;
    private Expression nexp ;
    private String cmdAssignation;
    
    public ExecuteurAssignation(){
        nexp = new NExp(new Num(0), "+", null);  // pour debuter les calculs
        //nexp = new Num(10);
    }

    public ExecuteurAssignation(String cmdAssignation) {
        this.cmdAssignation = cmdAssignation;
        nexp = new NExp(new Num(0), "+", null);  // pour debuter les calculs
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public Expression getNexp() {
        return nexp;
    }

    public void setNexp(Expression nexp) {
        this.nexp = nexp;
    }

    public String getCmdAssignation() {
        return cmdAssignation;
    }

    public void setCmdAssignation(String cmdAssignation) {
        this.cmdAssignation = cmdAssignation;
    }
    
    
    
    
    /**
     * permet d'executer la commande d'assignation
     * @param cmdAssignation 
     */
    public void executerAssignation(String cmdAssignation) throws ExceptionVariableNonDeclaree{
        ArrayList<String> decomp = decomposerAssignation(cmdAssignation);
        this.ide = extraireIDE(decomp);
        String expr = extraireExpression(decomp);
        int valeurExpr = calculer(expr);
        //permet d'ajouter l'IDE dans l'environnement
        ajouterVariable_Environnement(ide, valeurExpr);
    }
    
    
    
    
    
    
    
    /****************************************************************
     * Decomposition de la commande d'Assignation
     ***************************************************************/
    public ArrayList<String> decomposerAssignation(String cmdAssignation){
        ArrayList<String> listDecomp = new ArrayList();
        StringTokenizer st = new StringTokenizer(cmdAssignation, ":=");
        while (st.hasMoreTokens()) {
          String valeur = st.nextToken();
          if( !valeur.equals("") &&  !valeur.equals(" ")){
            listDecomp.add(valeur);
          }
        }
        
        
        return listDecomp;
    }
    
    public String extraireIDE(ArrayList<String> listDecomp){
        String ideEspace = listDecomp.get(0);// le premier element est toujours l'IDE
        String ideSansEspace  = enleverEspacesDans(ideEspace);
        return ideSansEspace; 
    }
    
    public String extraireExpression( ArrayList<String> listDecomp){
        //listDecomp.remove(0); //on enleve l'IDE qui est la premiere Valeur.
        String expEspace = listDecomp.get(1);
        String expSansEspace = enleverEspacesDans(expEspace); // on enleve les espaces dans l'expression
        return expSansEspace;
    }
    
  
    
    
    
    
    public Expression calculToken( ArrayList<String> listTerme ) throws ExceptionVariableNonDeclaree{
        //System.out.println("List Terme = "+listTerme);
        
        String firstTerme = listTerme.get(0);
        Character carSigne = firstTerme.charAt(0);
        int entier; Num num ;Num num0;
        //System.out.println("firstExp == ["+firstTerme+"]");
            
        if( estUnOperateurArithmetique(carSigne) ){  // si le 1er caractere est un SIGNE
            if( listTerme.size() > 1){
                    String secondTerme = listTerme.get(1);
                    //System.out.println("second terme == "+secondTerme);
                    entier = Integer.parseInt(secondTerme);
                    
                    if(carSigne == '-'){
                        num0 = new Num(nexp.valeur());
                        num = new Num(entier); 
                        this.nexp = new NExp(num0, "-", num);
                    }else if(carSigne == '*' ){  
                        num0 = new Num(nexp.valeur());
                        num = new Num(entier); 
                        this.nexp = new NExp(num0, "*", num);
                    }else if(carSigne == '/' ){   
                        num0 = new Num(nexp.valeur());
                        num = new Num(entier); 
                        this.nexp = new NExp(num0, "/", num);
                    }else{ //sinon On fait une addition NORMALE
                        num0 = new Num(nexp.valeur());
                        num = new Num(entier); 
                        nexp = new NExp(num0, "+", num);
                    }

                    // on enleve le 1er element qui contient le signe
                    for (int i = 2; i < listTerme.size(); i++) {  
                        String str = listTerme.get(i);
                        try {
                            int val = Integer.parseInt(str);
                            Num nbreNum = new Num(val);
                            nexp.setExpDroite(nbreNum);
                        } catch (NumberFormatException nFe) {
                            if(Character.isLetter(str.charAt(0))){ // Si le 1er Caractere est une lettre, alors, c'est un IDE
                                if(environnement.containsKey(str)){  //permet de tester si la variable a été declarée
                                    ArrayList<Integer> listEntier =  environnement.get(str);
                                    int val = listEntier.get(listEntier.size()-1);
                                    nexp.setExpDroite(new Num(val));
                                }else{
                                    throw new ExceptionVariableNonDeclaree("Variable",str, getCmdAssignation());
                                }
                            }else{  // Si le symbole est un SIGNE / OPERATEUR
                                nexp = new NExp(nexp, str, null);
                            }
                        }
                    }
            }else{
                nexp = new NExp(nexp, carSigne+"", null);
            }
        }else{ // Si le 1er terme n'est pas un [SIGNE]
            Expression nvExpr = new NExp(new Num(0),"+", null);
            for(int i=0; i<listTerme.size(); i++){
                String str = listTerme.get(i);
                try {
                    entier = Integer.parseInt(str);
                    num = new Num(entier);
                    
                        nvExpr.setExpDroite(num);
                    
                } catch (NumberFormatException nFe) {
                    
                    if(Character.isLetter(str.charAt(0))){ // Si le 1er Caractere est une lettre, alors, c'est un IDE
                       if(environnement.containsKey(str)){  //permet de tester si la variable a été declarée
                           ArrayList<Integer> listEntier =  environnement.get(str);
                           int val = listEntier.get(listEntier.size()-1);
                           nvExpr.setExpDroite(new Num(val));
                       }else{
                           throw new ExceptionVariableNonDeclaree("Variable",str, getCmdAssignation());
                       }
                    }else{  //Si c'est un OPERATEUR
                        if( i==listTerme.size()-1 ){
                            if( this.nexp.getOperateur() != null ){
                                this.nexp.setExpDroite(nvExpr);
                                this.nexp = new NExp(nexp, str, null);
                            }
                        }else{
                            nvExpr = new NExp(nvExpr, str, null);
                        }
                    }
                }
            }
            if( this.nexp.getOperateur() != null ){
                this.nexp.setExpDroite(nvExpr);
            }
            
        }
        
        return nexp;
    }
    
    
    public int calculer(String expr) throws ExceptionVariableNonDeclaree{
        ArrayList<String> listTerme;
        StringTokenizer st = new StringTokenizer(expr, "()");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            listTerme = decompEnListTerme(str);
            this.nexp = calculToken(listTerme);
        }
        return this.nexp.valeur();
    }
    
}