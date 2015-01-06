/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbreSyntaxique;

import AST.Typecommande;
import GestionExceptions.ConflitVariableName;
import GestionExceptions.VariableNotExist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author ishola hamed
 */
public class LanceurCom {
    //protected int compteurIndex;
    
    //l'environnement contient l'IDE, et les differentes valeurs que cet IDE a pris 
    static HashMap<String, ArrayList<Integer>> environnement = new HashMap();
    
    
    public LanceurCom(){ }
    
  
    /**
     * permet d'executer la commande donnée en parametre
     * @param suiteDeCommandes 
     */
    /*
    public void executer(String suiteDeCommandes) throws ConflitVariableName{
        //System.out.println("suitesCmd == "+suiteDeCommandes);
        CommandeAssignation execAssignation = new CommandeAssignation();
        CommandeAliasing execAliasing = new CommandeAliasing();
        CommandeLET execDeclVar = new CommandeLET();
        CommandeIF execConditionel = new CommandeIF();
        CommandeWHILE execIteration  = new CommandeWHILE();
        
        ArrayList<String> listCommandeSplites = new ArrayList();
        
        StringTokenizer st = new StringTokenizer(suiteDeCommandes, ";");
        while (st.hasMoreTokens()) {
          String commande = st.nextToken();
          listCommandeSplites.add(commande);
        }
        
        String typeCmd;
        for (String cmd : listCommandeSplites) {
            typeCmd = getTypeCommande(cmd);
            if( isAssignationCommande(typeCmd) ){
                execAssignation.executer(cmd);
            }else if( isDeclarationVarCommande(typeCmd) ){
                execDeclVar.executer(cmd);
            }else if( isAliasingCommande(typeCmd) ){
                execAliasing.executer(cmd);
            }else if (isIterationCommande(typeCmd) ){
                execIteration.executer(cmd);
            }else if ( isConditionnelleCommande(typeCmd) ){
                execConditionel.executer(cmd);
            }
        }
    }
    */
    
    
    /**
     * permet d'executer une sequence de commande
     * (Permet de desambiguer les expressions et commandes ambigues)
     * @param seqCmd
     * @return
     * @throws ConflitVariableName 
     */
    public void executer(String seqCmd) throws ConflitVariableName, VariableNotExist{
        SequenceCommande ptVirguleCmd;
        int indexPV;
        
        String typeCmd = getTypeCommande(seqCmd);
        
        
        if( isAssignationCommande(typeCmd) ){
            ptVirguleCmd = extraireAssignation(seqCmd);
            indexPV = ptVirguleCmd.getIndexPointVirgule();
            CommandeAssignation execAss = (CommandeAssignation)ptVirguleCmd.getExecuteur();
            
            ArrayList<String> decomp = execAss.decomposerAssignation(execAss.getCmdAssignation());
            
            if(decomp.get(1).equalsIgnoreCase("fork();")){
                if ( indexPV != -1 ) {
                    String subAss = seqCmd.substring(indexPV+1);
                    int indexPV2 = subAss.indexOf(";");
                    String comFork = subAss.substring(0, indexPV2);
                    
                    LanceurCom exec = new LanceurCom();
                    exec.executer(subAss);
                }
            }
            else{
                
            }
            execAss.executerAssignation(execAss.getCmdAssignation());  // execution de la commande qui est avant le point Virgule
            if ( indexPV != -1 ) {
                String subAss = seqCmd.substring(indexPV+1); // permet de recuperer la suite de la commande
                LanceurCom exec = new LanceurCom();  // on instancie un nouvelle executeur de commande pour cette nouvelle commande
                exec.executer(subAss);
            }
        }else if ( isConditionnelleCommande(typeCmd) ){
            ptVirguleCmd = extraireConditionelle(seqCmd);
            indexPV = ptVirguleCmd.getIndexPointVirgule();
            CommandeIF execC = (CommandeIF)ptVirguleCmd.getExecuteur();
            execC.executerConditionelle();// execution de la commande qui est avant le point Virgule
            
            if ( indexPV != -1) {
                String subCond = seqCmd.substring(indexPV+1); // permet de recuperer la suite de la commande
                //System.out.println("suite = "+subCond+"\n");
                LanceurCom exec = new LanceurCom();  // on instancie un nouvelle executeur de commande pour cette nouvelle commande
                exec.executer(subCond);
            }
        }else if (isIterationCommande(typeCmd) ){
            //System.out.println("Cmd_While == ["+seqCmd+"]");
            ptVirguleCmd = extraireIteration(seqCmd);
            indexPV = ptVirguleCmd.getIndexPointVirgule();
            CommandeWHILE execI = (CommandeWHILE)ptVirguleCmd.getExecuteur();
            execI.executerIteration();// execution de la commande [iteration] qui est avant le point Virgule
            if ( indexPV != -1) {
                String subIter = seqCmd.substring(indexPV+1); // permet de recuperer la suite de la commande
                LanceurCom exec = new LanceurCom();  // on instancie un nouvelle executeur de commande pour cette nouvelle commande
                exec.executer(subIter);
            }
        }else if( isDeclarationVarCommande(typeCmd) ){
            ptVirguleCmd = extraireDeclarationVar(seqCmd);
            indexPV = ptVirguleCmd.getIndexPointVirgule();
            CommandeLET execD = (CommandeLET)ptVirguleCmd.getExecuteur();
            execD.executer();  // execution de la commande qui est avant le point Virgule
            //System.out.println("\nCmdToExecute = ["+cmdToExecute+"]");
            if ( indexPV != -1) {
                String subDecl = seqCmd.substring(indexPV+1); // permet de recuperer la suite de la commande
                LanceurCom exec = new LanceurCom();  // on instancie un nouvelle executeur de commande pour cette nouvelle commande
                exec.executer(subDecl);
            }
        }else if( isAliasingCommande(typeCmd) ){
            ptVirguleCmd = extraireAliasing(seqCmd);
            indexPV = ptVirguleCmd.getIndexPointVirgule();
            CommandeAliasing execAL = (CommandeAliasing)ptVirguleCmd.getExecuteur();
            execAL.executerAliasing();// execution de la commande qui est avant le point Virgule
            if ( indexPV != -1) {
                String subIter = seqCmd.substring(indexPV+1); // permet de recuperer la suite de la commande
                LanceurCom exec = new LanceurCom();  // on instancie un nouvelle executeur de commande pour cette nouvelle commande
                exec.executer(subIter);
            }
        }
    }
    
    
    /**
     * permet d'extraire une assignation dans une sequence de commande
     * @param seqCmd
     * @return 
     */
    public SequenceCommande extraireAssignation(String seqCmd){
        CommandeAssignation execA;
        SequenceCommande ptVirguleCmd;
        
        int indexPointVirgule = seqCmd.indexOf(";");
        if(indexPointVirgule != -1 ){
            String subCmd = seqCmd.substring(0, indexPointVirgule);
            /*
            if( contientMotReserve(subCmd) || subCmd.contains("}") ){
                int indexAccF = subCmd.indexOf("}");
                if (indexAccF != -1) {
                    if (indexAccF < indexPointVirgule) {
                        subCmd = subCmd.substring(0, indexAccF);
                    }else{
                        subCmd = subCmd.substring(0, indexPointVirgule);
                    }
                }
            }
            */
            subCmd = subCmd.substring(0, indexPointVirgule);
            execA = new CommandeAssignation(subCmd);
            ptVirguleCmd = new SequenceCommande(indexPointVirgule, execA);
            //System.out.println("RESULTAT____EXTRACTION = ["+subCmd+"]");
        }else{
            execA = new CommandeAssignation(seqCmd);
            ptVirguleCmd = new SequenceCommande(-1, execA);
            //System.out.println("RESULTAT____EXTRACTION = ["+seqCmd+"]");
        }
        return ptVirguleCmd;
    }
    
    
    public SequenceCommande extraireFork(String seqCmd){
        CommandeFORK execB;
        SequenceCommande ptVirguleCmd;
        
        int indexPointVirgule = seqCmd.indexOf(";");
        if (indexPointVirgule !=-1){
            String subCmd = seqCmd.substring(0, indexPointVirgule);
            if (contientMotReserve(subCmd) || subCmd.contains("}")){
                int indexAccF = subCmd.indexOf("}");
                if (indexAccF != -1) {
                    if (indexAccF < indexPointVirgule) {
                        subCmd = subCmd.substring(0, indexAccF);
                    }else{
                        subCmd = subCmd.substring(0, indexPointVirgule);
                    }
                }
            }
            execB = new CommandeFORK(subCmd);
            ptVirguleCmd = new SequenceCommande(indexPointVirgule, execB);
        }
        else{
            execB = new CommandeFORK(seqCmd);
            ptVirguleCmd = new SequenceCommande(-1, execB);
        }
        return ptVirguleCmd;
    }
    
 
    public SequenceCommande extraireIteration(String seqCmd){
        String BExp, Com;
        Compteur compteurIndex = new Compteur(0);
        //this.compteurIndex = 0;
        enleverLesEspaces(compteurIndex, seqCmd);
        int indexWhile = seqCmd.indexOf("while");
        int indexDO = seqCmd.indexOf("do");
        
        BExp = seqCmd.substring(indexWhile+5, indexDO);
        BExp = enleverParentheseDebutFinBExp(BExp);
     
        /********  Recuperation du COM2  ( pour desambiguer)  ****************************/
        SequenceCommande ptVirguleCmd = new SequenceCommande();
        int indexAccoladeOuvrante; // initialisation des index des accolades
        int indexAccoladeFermante; 
        compteurIndex.setValue(indexDO+2); // On commence à enlever les espaces à partir du mot (DO)
        enleverLesEspaces(compteurIndex, seqCmd);
        
        int cptAccolade = 0;
        Character car = seqCmd.charAt(compteurIndex.getValue());
        if (estUneAccoladeOuvrante(car)) {
            indexAccoladeOuvrante = compteurIndex.getValue();
            compteurIndex.incrementer();  // lecture de l'accolade Ouvrante
            enleverLesEspaces(compteurIndex, seqCmd);          
            cptAccolade++;  // lecture d'une accolade ouvrante
            
            // on recherche l'accolade fermente correspondant, à cette accolade ouvrante
            while( cptAccolade != 0 ){              
                car = seqCmd.charAt(compteurIndex.getValue());
                if (estUneAccoladeOuvrante(car)) {
                    cptAccolade++;
                }else if (estUneAccoladeFermante(car)) {
                    cptAccolade--;
                }
                compteurIndex.incrementer();   // correspond à la lecture des caractere de la sequence
            }
            indexAccoladeFermante = compteurIndex.getValue()-1; // contient l'index de l'accolade fermante de COM2
            // On reconstitue le COM2 à partir des index des parentheses Ouvrantes et des parenthese fermentes
            Com = seqCmd.substring(indexAccoladeOuvrante+1, indexAccoladeFermante);
            Com = enleverAccoladesDebutFinCOM(Com); //On enleve les accolades ouvrantes et fermante, si elle existe dans la commande
            //System.out.println("Com_iteration = ["+Com+"]");
            
            
            //On recherche un Point Virgule
            // on cherche si il existe d'autre commande  apres la commande d'Iteration,
            String subStr = seqCmd.substring(indexAccoladeFermante+1);
            int indexPointVirgule = subStr.indexOf(";");
            if (indexPointVirgule != -1) {
                ptVirguleCmd.setIndexPointVirgule(compteurIndex.getValue()+indexPointVirgule);
            }else{
                ptVirguleCmd.setIndexPointVirgule(-1);
            }
        }else{
                Com = seqCmd.substring(indexDO+2);
                Com = enleverAccoladesDebutFinCOM(Com); //On enleve les accolades ouvrantes et fermante, si elle existe dans la commande
                ptVirguleCmd.setIndexPointVirgule(-1);
        }
        //On reconstitue la commande conditionelle en entier sans les accolades
        //String cmdEntiere = "while "+BExp+" do "+Com+" ";
        //pvc.setCmdAvantPointVirgule(cmdEntiere);
        CommandeWHILE execI = new CommandeWHILE(BExp, Com);
        ptVirguleCmd.setExecuteur(execI);
        return ptVirguleCmd;
    }
    
    public SequenceCommande extraireConditionelle(String seqCmd){
        Compteur compteurIndex = new Compteur(0);
        String BExp, Com1, Com2;
        int indexIF = seqCmd.indexOf("if");
        int firstIndexTHEN = seqCmd.indexOf("then");
        
        BExp = seqCmd.substring(indexIF+2, firstIndexTHEN);
        BExp = enleverParentheseDebutFinBExp(BExp);  // permet d'enlever les Parentheses du BEXP
        //System.out.println("analyse == "+this.BExp);
        
        int compteurIF = 1; 
        //int compteurTHEN = 1;
        compteurIndex.setValue(firstIndexTHEN+4);  // on saute le [THEN] et on lit la suite de la commande
        
        Character car;
        String mot;
        int indexELSE_of_FirstTHEN = 0;
        while (compteurIndex.getValue() < seqCmd.length() ){
            car = seqCmd.charAt(compteurIndex.getValue());
            if ( Character.isLetter(car) ){
                mot = lireMot(compteurIndex, seqCmd);
                if ( mot.equalsIgnoreCase("if") ){
                    compteurIF++;
                }else if( mot.equalsIgnoreCase("else")){
                    compteurIF--;
                    if( compteurIF==0 ){
                        indexELSE_of_FirstTHEN = compteurIndex.getValue()-4;  // le mot [ELSE] est totalement lu, donc on l'enleve
                        break;
                    }
                }
            }
            compteurIndex.incrementer();
        }
        Com1 = seqCmd.substring(firstIndexTHEN+4, indexELSE_of_FirstTHEN);
        Com1 = enleverAccoladesDebutFinCOM(Com1); // permet d'enlever les accolades, si il en a dans le COM
        //System.out.println("COM1_IF = ["+Com1+"]");
        
        /********  Recuperation du COM2  (on enleve l'ambiguité)  ****************************/
        SequenceCommande ptVirguleCmd = new SequenceCommande();
        int indexAccOCom2; // initialisation des valeurs
        int indexAccFCom2; // initialisation des valeurs
        compteurIndex.setValue(indexELSE_of_FirstTHEN+4);
        enleverLesEspaces(compteurIndex, seqCmd);

        int cptAccolade = 0;
        car = seqCmd.charAt(compteurIndex.getValue());
        if (estUneAccoladeOuvrante(car)) {
            indexAccOCom2 = compteurIndex.getValue();
            compteurIndex.incrementer();  // lecture de l'accolade Ouvrante
            enleverLesEspaces(compteurIndex, seqCmd);
            cptAccolade++;  // lecture d'une accolade ouvrante
            
            // on recherche l'accolade fermente correspondant, à cette accolade ouvrante
            while( cptAccolade != 0 ){
                car = seqCmd.charAt(compteurIndex.getValue());
                if (estUneAccoladeOuvrante(car)) {
                    cptAccolade++;
                }else if (estUneAccoladeFermante(car)) {
                    cptAccolade--;
                }
                compteurIndex.incrementer();   // correspond à la lecture des caractere de la sequence
            }
            indexAccFCom2 = compteurIndex.getValue()-1; // contient l'index de l'accolade fermante de COM2 
            // On reconstitue le COM2 à partir des index des parentheses Ouvrantes et des parenthese fermentes
            Com2 = seqCmd.substring(indexAccOCom2+1, indexAccFCom2); 
            Com2 = enleverAccoladesDebutFinCOM(Com2);
            //System.out.println("COM2_IF = ["+Com2+"]");
            
            // on cherche si il existe d'autre commande  apres la commande CONDITIONELLE,
            String subStr = seqCmd.substring(indexAccFCom2+1);
            compteurIndex.setValue(indexAccFCom2+1);
            int indexPointVirgule = subStr.indexOf(";");
            if (indexPointVirgule != -1) {
                ptVirguleCmd.setIndexPointVirgule(compteurIndex.getValue()+indexPointVirgule);
                //System.out.println("i = "+i);
                //System.out.println("[indexPointVirgulePPPPPPP] == "+ptVirguleCmd.getIndexPointVirgule());
                
            }else{
                ptVirguleCmd.setIndexPointVirgule(-1);
            }    
        }else{
            Com2 = seqCmd.substring(indexELSE_of_FirstTHEN+4);
            Com2 = enleverAccoladesDebutFinCOM(Com2);
            ptVirguleCmd.setIndexPointVirgule(-1);
        }
        CommandeIF execC = new CommandeIF(BExp, Com1, Com2);
        ptVirguleCmd.setExecuteur(execC);
        
        return ptVirguleCmd;
    }
    
    public SequenceCommande extraireDeclarationVar(String seqCmd){
        //System.out.println("COMMAND == "+seqCmd);
        int cptLet = 0;
        int cptIn = 0;
        Compteur compteurIndex = new Compteur(0);
        
        // On enleve les espaces au debue de la commande
        //enleverLesEspaces(compteurIndex, seqCmd);
        //String cmdSansEspaceDebut = seqCmd.substring(compteurIndex);
        int indexLET = seqCmd.indexOf("let");  
        int indexIN = seqCmd.indexOf("in");
        cptLet++;
        cptIn++;
        String ideEspacee = seqCmd.substring(indexLET+3, indexIN);  // On ajoute 3 pour recuperer le mot  [let]
        String ide  = enleverEspacesDans(ideEspacee);
        
        compteurIndex.setValue(indexIN+2);
        //System.out.println("in == ["+seqCmd.substring(indexIN, indexIN+2)+"]");
        enleverLesEspaces(compteurIndex, seqCmd);
        
        Character car;
        while(cptLet !=0 && cptIn != 0  && compteurIndex.getValue() < seqCmd.length()){
            car = seqCmd.charAt(compteurIndex.getValue());
            if(Character.isLetter(car)){
                String mot = lireMot(compteurIndex, seqCmd);
                //System.out.println("mot = "+mot);
                if (mot.equalsIgnoreCase("let")) {
                    cptLet++;
                }
                if (mot.equalsIgnoreCase("in")) {
                    cptIn++;
                }
                if (mot.equalsIgnoreCase("end")) {
                    cptLet--;
                    cptIn--;
                }
            }else{
                compteurIndex.incrementer();
            }
        }
        //on recupere le dernier index END du LET de debut
        int indexEnd = seqCmd.substring(0, compteurIndex.getValue()).lastIndexOf("end");
        String com = seqCmd.substring(seqCmd.indexOf("in")+2, indexEnd); 
        com = enleverAccoladesDebutFinCOM(com);
        
        // on cherche si il existe d'autre commande  apres l'aliasing,
        SequenceCommande ptVirguleCmd = new SequenceCommande();      
        //On recherche un Point Virgule
        String subStr = seqCmd.substring(indexEnd+3);
        //System.out.println("subStr======== = ["+subStr+"]");
        int indexPointVirgule = subStr.indexOf(";");
        if (indexPointVirgule != -1) {
            ptVirguleCmd.setIndexPointVirgule(compteurIndex.getValue()+indexPointVirgule);
        }else{
            ptVirguleCmd.setIndexPointVirgule(-1);
        }
            
        //on reconstitue la commande en entier
        //String cmdEntiere = " let "+ide+" in "+com+" end ";
        //ptVirguleCmd.setCmdAvantPointVirgule(cmdEntiere);
        CommandeLET execD = new CommandeLET(ide, com);
        ptVirguleCmd.setExecuteur(execD);
        return ptVirguleCmd;
    }
    
    
    public SequenceCommande extraireAliasing(String seqCmd){
        int cptLet = 0;
        int cptIn = 0;
        Compteur compteurIndex = new Compteur(0);
        
        //String cmdSansEspaceDebut = seqCmd.substring(compteurIndex);
        int indexLET = seqCmd.indexOf("let");  
        int indexBE = seqCmd.indexOf("be");
        String ide1Espacee  = seqCmd.substring(indexLET+3, indexBE);// +3 pour en compte le mot (LET)
        String ide1 = enleverEspacesDans(ide1Espacee);
        //System.out.println("ide1 = ["+ide1+"]");
        
        int indexIN = seqCmd.indexOf("in");
        String ide2Espacee  = seqCmd.substring(indexBE+2, indexIN); // +2 pour en compte le mot (BE)
        String ide2 = enleverEspacesDans(ide2Espacee);
        //System.out.println("ide2 = ["+ide2+"]");
        cptIn++;
        cptLet++;
        
        
        // on cherche la fermeture de LET  (on cherche le END)
        compteurIndex.setValue(indexIN+2);
        //System.out.println("in == ["+seqCmd.substring(indexIN, indexIN+2)+"]");
        enleverLesEspaces(compteurIndex, seqCmd);
        
        Character car;
        while(cptLet !=0 && cptIn != 0  && compteurIndex.getValue() < seqCmd.length()){
            car = seqCmd.charAt(compteurIndex.getValue());
            if(Character.isLetter(car)){
                String mot = lireMot(compteurIndex, seqCmd);
                //System.out.println("mot = "+mot);
                if (mot.equalsIgnoreCase("let")) {
                    cptLet++;
                }
                if (mot.equalsIgnoreCase("in")) {
                    cptIn++;
                }
                if (mot.equalsIgnoreCase("end")) {
                    cptLet--;
                    cptIn--;
                }
            }else{
                compteurIndex.incrementer();
            }
        }
        //on recupere le dernier index END du LET de debut
        int indexEnd = seqCmd.substring(0, compteurIndex.getValue()).lastIndexOf("end");
        String com = seqCmd.substring(seqCmd.indexOf("in")+2, indexEnd); 
        com = enleverAccoladesDebutFinCOM(com);
        
        // on cherche si il existe d'autre commande  apres l'aliasing,
        SequenceCommande ptVirguleCmd = new SequenceCommande();      
        //On recherche un Point Virgule
        String subStr = seqCmd.substring(indexEnd+3);
        int indexPointVirgule = subStr.indexOf(";");
                if (indexPointVirgule != -1) {
                    ptVirguleCmd.setIndexPointVirgule(compteurIndex.getValue()+indexPointVirgule);
                }else{
                    ptVirguleCmd.setIndexPointVirgule(-1);
                }
        //on reconstitue la commande en entier
        CommandeAliasing execAL = new CommandeAliasing(ide1, ide2, com);
        ptVirguleCmd.setExecuteur(execAL);
        return ptVirguleCmd;
    }
        
    
    
    
    /********************************************************************* 
     * LES FONCTIONS POUR L'ENVIRONNEMENT
     ********************************************************************/
    public void ajouterVariable_Environnement(String ide, int valeur){
        if( ! environnement.containsKey(ide) ){
            ArrayList<Integer> listValeurs = new ArrayList();
            listValeurs.add(valeur);
            environnement.put(ide, listValeurs);
        }else{
            ArrayList<Integer> listValeurs = environnement.get(ide);
            listValeurs.add(valeur);
        }
    }
    
    public void copierValeurIDE2_dans_IDE1(String ide2, String ide1){
        if ( environnement.containsKey(ide1)  && environnement.containsKey(ide2) ) {
            ArrayList<Integer> list = environnement.get(ide2);
            if (list != null) {
                int valeurIDE2 = list.get(list.size()-1);
                environnement.get(ide1).add(valeurIDE2);
            }
        }
    }
    
    public void retirerVariable_Environnement(String ide){
        if( environnement.containsKey(ide) ){
            environnement.remove(ide);
        }
    }
    
    public void afficherEnvironnement(){
        StringBuilder sb = new StringBuilder();
        for (String ide : environnement.keySet()) {
            ArrayList<Integer> list = environnement.get(ide);
            int valeur = list.get(list.size()-1); // on recupere la derniere valeur de la liste
            sb.append("["+ide+"]").append(" = [").append(valeur).append("]\n");
        }
        System.out.println(sb.toString());
    }
    
    public String getEnvironnement(){
        StringBuilder sb = new StringBuilder();
        for (String ide : environnement.keySet()) {
            ArrayList<Integer> list = environnement.get(ide);
            int valeur = list.get(list.size()-1); // on recupere la derniere valeur de la liste
            sb.append("["+ide+"]").append(" = ").append(valeur).append("\n");
        }
        return sb.toString();
    }
    /*******************************************************************************/
    
    
    
    
    /**
     * permet d'evaluer une expression Binaire
     * @param cmdBExp
     * @return 
     */
    public boolean evaluerBExp(String cmdBExp) throws VariableNotExist{
        String mot1, mot2;
        Compteur compteurIndex = new Compteur(0);
        Character car = cmdBExp.charAt(compteurIndex.getValue());
        if (car == ' '){  // si il y a des espaces 
            enleverLesEspaces(compteurIndex, cmdBExp);  // on enleves les espaces
        }
        
        //System.out.println("commande à evaluer : "+cmdBExp.substring(compteurIndex));
        if(cmdBExp.indexOf(" and ") == -1
                && cmdBExp.indexOf(" or ") == -1){  // Si ce n'est pas un BExp de type ( X=2 OR/AND Y=2) alors 
            //System.out.println("CMD_BEXP == ["+cmdBExp+"]");
            car = cmdBExp.charAt(compteurIndex.getValue());
            if (Character.isLetter(car)) {
                mot1 = lireMot(compteurIndex, cmdBExp+" "); // [espace] pour delimiter la fin de mot
                if(mot1.equalsIgnoreCase("true") ){
                    mot2 = enleverEspacesDans(mot1); // On enleve les espaces dans tout le BExp, pour voir si il y a que TRUE
                    if( mot2.equalsIgnoreCase("true")){
                        return true;
                    }
                }else if( mot1.equalsIgnoreCase("false") ){
                    mot2 = enleverEspacesDans(mot1); // On enleve les espaces dans tout le BExp, pour voir si il y a que FALSE
                    if( mot2.equalsIgnoreCase("false")){
                        return false;
                    }
                }else if( mot1.equalsIgnoreCase("not") ){
                    enleverLesEspaces(compteurIndex, cmdBExp);
                    car = cmdBExp.charAt(compteurIndex.getValue());
                    String subCommand;
                    if( estUneParentheseOuvrante(car) ){ // On cherche si, le (NOT)  a des parentheses ouvrantes/fermantes 
                        String subStr = cmdBExp.substring(compteurIndex.getValue()+1);
                        int indexParFermante = subStr.indexOf(")");
                        subCommand = subStr.substring(0,indexParFermante);
                        subCommand = subCommand+" "; // [Espace] est ajouté pour la fin de lecture du mot
                    }else{
                        subCommand = lireMot(compteurIndex, cmdBExp);
                        // Si c'est un BEXP du type ( NOT X=2 )
                        if ( estUnIDE(subCommand) ){ 
                            enleverLesEspaces(compteurIndex, cmdBExp);
                            car = cmdBExp.charAt(compteurIndex.getValue());
                            if (estUnComparateur(car)) {
                                compteurIndex.incrementer();  // on incremente le compteur du scanner, pour la lecture du COMPARATEUR
                                subCommand = subCommand+car;
                                
                                enleverLesEspaces(compteurIndex, cmdBExp);
                                
                                car = cmdBExp.charAt(compteurIndex.getValue());
                                if (Character.isLetter(car)) {  // Si c'est un IDE qui est après le comparateur
                                    String mot = lireMot(compteurIndex, cmdBExp);
                                    subCommand = subCommand+mot;
                                }
                                if (Character.isDigit(car)) { // Si c'est un chiffre qui est après le comparateur
                                    int chiffre = lireChiffre(compteurIndex, cmdBExp);
                                    subCommand = subCommand+chiffre;
                                }
                            }
                        }
                        subCommand = subCommand+" "; //[Espace] est ajouté pour la fin de lecture du mot
                        
                    }
                    //System.out.println("subCommande = "+subCommand);
                    return ! evaluerBExp(subCommand);  // On retourne le CONTRAIRE de la valeur calculée
                
                }else if(cmdBExp.contains("=") || cmdBExp.contains("<")
                        || cmdBExp.contains(">")){
                    return evaluerNExpCOpNExp(cmdBExp);  // evaluer l'
                }
            }
        }else {  // Si la commande est une commande de type (  X=2 OR/AND Y=2  )
            
            if ( !cmdBExp.contains("(") && !cmdBExp.contains(")") ){
            // si il n'y a pas de parenthese alors !    
                int indexAND = cmdBExp.indexOf("and");
                if ( indexAND != -1 ){ // Si c'est un AND
                    String bexpGauche = cmdBExp.substring(0, indexAND);
                    bexpGauche = bexpGauche+" ";

                    String bexpDroite = cmdBExp.substring(indexAND+3);
                    bexpDroite = bexpDroite+" ";
                    return evaluerBExp(bexpGauche) && evaluerBExp(bexpDroite);
                }
                
                int indexOR = cmdBExp.indexOf("or");
                if ( indexOR != -1 ){  // si c'est un OR
                    String bexpGauche = cmdBExp.substring(0, indexOR);
                    bexpGauche = bexpGauche+" ";

                    String bexpDroite = cmdBExp.substring(indexOR+2);
                    bexpDroite = bexpDroite+" ";
                    return evaluerBExp(bexpGauche) || evaluerBExp(bexpDroite);
                }
            }
        }
        return false;
    }
    
    /**
     * permet d'evaluer une expression de type ( X+2=1+3  ou  Y<2+2 )
     * (il les decompose en 2 expression : Gauche et Droite, et il les evaluent )
     * @param cmdNExpCOpNExp
     * @return 
     */
    
    public boolean evaluerNExpCOpNExp(String cmdNExpCOpNExp) throws VariableNotExist{
        String expGauche=null;
        String expDroite=null;
        StringTokenizer st;
        
        int indexSup = cmdNExpCOpNExp.indexOf(">"); // on trouve l'index de >
        int indexInf = cmdNExpCOpNExp.indexOf("<"); // on trouve l'index de <
        int indexEgal = cmdNExpCOpNExp.indexOf("=");
        
        if( indexSup != -1 ){
            st = new StringTokenizer(cmdNExpCOpNExp,">");
            while(st.hasMoreElements()){
                expGauche = st.nextToken();
                expDroite = st.nextToken();
            }
            int valG = calculerExpression(expGauche);
            int valD = calculerExpression(expDroite);
            return valG > valD;
        }else if( indexInf != -1 ){
            st = new StringTokenizer(cmdNExpCOpNExp,"<");
            while(st.hasMoreElements()){
                expGauche = st.nextToken();
                expDroite = st.nextToken();
            }
            int valG = calculerExpression(expGauche);
            int valD = calculerExpression(expDroite);
            return valG < valD;
        
        }else if( indexEgal != -1 ){
            //System.out.println("cmd =   ["+cmdNExpCOpNExp+"]");
            st = new StringTokenizer(cmdNExpCOpNExp, "=");
            while(st.hasMoreElements()){
                expGauche = st.nextToken();
                expDroite = st.nextToken();
            }
            int valG = calculerExpression(expGauche);
            int valD = calculerExpression(expDroite);
            return valG == valD;
        }
        return false;
    }
    
    
    /**
     * permet de calculer les expressions de type X+Y ou X+3 ou 2+3 
     * @param exp
     * @return
     * @throws VariableNotExist 
     */
    public int calculerExpression(String exp) throws VariableNotExist{
        String expSsEspace = enleverEspacesDans(exp);
        Expression nvExpr = new ExpNum(new Numerique(0),"+", null);
        ArrayList<String> listTerme = decompEnListTerme(expSsEspace);
        //System.out.println("listTerme == "+listTerme);
        for(int i=0; i<listTerme.size(); i++){
                String str = listTerme.get(i);
                
                try {
                    int entier = Integer.parseInt(str);
                    Numerique num = new Numerique(entier);
                    nvExpr.setExpDroite(num);
                } catch (NumberFormatException nFe) {
                     if(Character.isLetter(str.charAt(0))){ // Si le 1er Caractere est une lettre, alors, c'est un IDE
                         if(environnement.containsKey(str)){  //permet de tester si la variable a été declarée
                            ArrayList<Integer> listEntier =  environnement.get(str);
                            int val = listEntier.get(listEntier.size()-1);
                            nvExpr.setExpDroite(new Numerique(val));
                        }else{
                            throw new VariableNotExist("",str,exp);
                        }
                     }else{ // Si c'est un OPERATEUR
                        nvExpr = new ExpNum(nvExpr, str, null);
                     }
                }                
         }
        return nvExpr.valeur();
    }
    
    
    /**
     * cette fonction permet de decomposer les terme d'une expression (sans les parenthèses)
     * (en groupant les chiffres en eux pour former des entiers
     * (  cette fonction transforme : l'expression -11+5 en une liste { -11, + , 5 }   
     * @param expr
     * @return 
     */
    public ArrayList<String> decompEnListTerme(String expr){
            Compteur compteurIndex = new Compteur(0);
            ArrayList<String> listTerme = new ArrayList();
            
            Character car;
            while (compteurIndex.getValue() < expr.length()) {
                car = expr.charAt(compteurIndex.getValue());
                if (Character.isDigit(car)){
                    int entier = lireChiffre(compteurIndex, expr);
                    listTerme.add(entier+"");
                    // l'incrementation de l'index se fait deja dans la fonction [lireChiffres] 
                }else if(Character.isLetter(car)){
                    String ide = lireMot(compteurIndex, expr);
                    listTerme.add(ide+"");
                }else {
                    if( car != '(' && car != ')' ) {
                        listTerme.add(car+"");
                    }
                    compteurIndex.incrementer(); // incrementation
                }
            }
            return listTerme;
    }
    
    
    
    /**
     * permet de connaitre le type de commande qui est executé
     * ( Assignation OU Sequence OU Conditionnel OU Iteration OU DeclarationVariable OU Aliasing )
     * @param commande
     * @return 
     */
    public String getTypeCommande(String commande){
        Compteur compteurIndex = new Compteur(0);
        enleverLesEspaces(compteurIndex, commande);
        
        String mot = lireMot(compteurIndex, commande);
        mot = enleverEspacesDans(mot); // permet d'enlever les espaces AVANT ou APRES le mot lu
        
        if ( estUnIDE(mot) ){    
            /*
            int indexPointEgal = commande.indexOf(":=");
            int indexParentheseOuvrante = commande.indexOf("(");
            String verif = commande.substring(indexPointEgal+2, indexParentheseOuvrante);
            
            
            if (verif.equalsIgnoreCase("fork")){
                return Typecommande.creationProcessus;
            }
            else if(verif.equalsIgnoreCase("wait")){
                return Typecommande.attenteProcessus;
            }
            */
            return Typecommande.assignation;
            
            
        }else{
            if(mot.equalsIgnoreCase("if")){
                return Typecommande.conditionelle;
            }else if ( mot.equalsIgnoreCase("while") ){
                return Typecommande.iteration;
            }else if(mot.equalsIgnoreCase("let")){
                enleverLesEspaces(compteurIndex, commande); // On enleve les espaces apres le (LET)
                String ide = lireMot(compteurIndex, commande); // lecture de l'IDE
                enleverLesEspaces(compteurIndex, commande); // On enleve les espaces apres l'IDE
                String motReserve = lireIDE(compteurIndex, commande); // On lit le mot reserve (BE) ou (IN)
                motReserve = enleverEspacesDans(motReserve);
                if(motReserve.equalsIgnoreCase("in")){
                    return Typecommande.declarationVariable;
                }else if(motReserve.equalsIgnoreCase("be")){
                    return Typecommande.aliasing;
                }
            }
        }
        return null;
    }
    
    
    /**
     * permet d'enlever les parentheses du BEXP pour analyser le contenu du BEXP
     * @param unBexp
     * @return 
     */
    public String enleverParentheseDebutFinBExp(String unBexp){
        String res = unBexp;
        int indexDebut;
        boolean b = false;
        String subStr = null;
        Compteur compteurIndex = new Compteur(0);
        enleverLesEspaces(compteurIndex, unBexp);
        Character car = unBexp.charAt(compteurIndex.getValue());
        if ( estUneParentheseOuvrante(car) ){
            indexDebut = compteurIndex.getValue();
            int pf = unBexp.lastIndexOf(")");
            subStr = unBexp.substring(pf+1);
            if (subStr != null){ // on verifie si il a bien des parentheses de délimitation  du BEXP
                for (int i = 0; i < subStr.length()-1; i++) {
                    if(subStr.charAt(i) != ' ' ){
                        b=true;
                    }
                }
                if( !b ){  
                    // Si b=Vrai, alors il n'ya pas de parenthese de délimitation du BEXP
                    // car la parenthese trouvé est une autre parenthèse, à l'interieur du BEXP
                    res = unBexp.substring(indexDebut+1, pf);   // on recupere le contenu des parenthese ( )
                }
            }else{
                res = unBexp.substring(indexDebut+1, pf);   // on recupere le contenu des parenthese ( )
            }
        }
     return res;   
    }
    
    
    /**
     * permet d'enlever les Accolades du COM pour analyser le contenu du COM
     * @param uneCOM
     * @return 
     */
    public String enleverAccoladesDebutFinCOM(String uneCOM){
        String res = uneCOM;
        int indexDebut;
        boolean b = false;
        String subStr = null;
        Compteur cptIndex = new Compteur(0);
        enleverLesEspaces(cptIndex, uneCOM);
        Character car = uneCOM.charAt(cptIndex.getValue());
        if ( estUneAccoladeOuvrante(car) ){
            indexDebut = cptIndex.getValue();
            int accF = uneCOM.lastIndexOf("}");
            subStr = uneCOM.substring(accF+1);
            if (subStr != null){ // on verifie si il a bien des parentheses de délimitation  du BEXP
                for (int i = 0; i < subStr.length()-1; i++) {
                    if(subStr.charAt(i) != ' ' ){
                        b=true;
                    }
                }
                if( !b ){  
                    // Si b=Vrai, alors il n'ya pas de parenthese de délimitation du BEXP
                    // car la parenthese trouvé est une autre parenthèse, à l'interieur du BEXP
                    res = uneCOM.substring(indexDebut+1, accF);   // on recupere le contenu des parenthese ( )
                }
            }else{
                res = uneCOM.substring(indexDebut+1, accF);   // on recupere le contenu des parenthese ( )
            }
        }
     return res;   
    }
    
    
    
    /**
     * permet de savoir si, la commande est une commande d'assignation
     * @param typeCommande
     * @return 
     */
    public boolean isAssignationCommande(String typeCommande){
        return typeCommande.equalsIgnoreCase(Typecommande.assignation);
    }
    
    /**
     * permet de savoir si, la commande est une commande conditionelle
     * @param typeCommande
     * @return 
     */
    public boolean isConditionnelleCommande(String typeCommande){
        return typeCommande.equalsIgnoreCase(Typecommande.conditionelle);
    }
    
    
    /**
     * permet de savoir si, la commande est une commande d'iteration
     * @param typeCommande
     * @return 
     */
    public boolean isIterationCommande(String typeCommande){
        return typeCommande.equalsIgnoreCase(Typecommande.iteration);
    }
    
    /**
     * permet de savoir si, la commande est une commande de declaration de Variable
     * @param typeCommande
     * @return 
     */
    public boolean isDeclarationVarCommande(String typeCommande){
        return typeCommande.equalsIgnoreCase(Typecommande.declarationVariable);
    }
    
    
    public boolean isForkCommande(String typeCommande){
        return typeCommande.equalsIgnoreCase(Typecommande.creationProcessus);
    }
    
    public boolean isWaitCommande(String typeCommande){
        return typeCommande.equalsIgnoreCase(Typecommande.attenteProcessus);
    }
    
    
    /**
     * permet de savoir si, la commande est une commande d'Aliasing
     * @param typeCommande
     * @return 
     */
    public boolean isAliasingCommande(String typeCommande){
        return typeCommande.equalsIgnoreCase(Typecommande.aliasing);
    }
    
    
    /**
     * Permet d'enlever des espaces d'un seul coup (à tous les endroits) dans une chaine de caractere (
     * @param chaine
     * @return 
     */
    public String enleverEspacesDans(String chaine){
        StringBuilder sansEspace = new StringBuilder();
        for (int i = 0; i < chaine.length(); i++) {
            Character car = chaine.charAt(i);
            if(   ! Character.isWhitespace(car)  ){  // Si le caractere n'est pas un ESPACE
                sansEspace.append(car);
            }
        }
        return sansEspace.toString();
    }
    
    
    /**
     * permet de lire les chiffres
     * @param instruction
     * @return 
     */
    public int lireChiffre(Compteur cpt, String instruction){
        StringBuilder sb = new StringBuilder();
        Character car = instruction.charAt(cpt.getValue());
        while(Character.isDigit(car) ){
            sb.append(car);
            cpt.incrementer();
            if(  cpt.getValue() == instruction.length()){ break; }
            car = instruction.charAt(cpt.getValue());
        }
        try {
            int val = Integer.parseInt(sb.toString());
            return val;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
        /**
     * permet de lire un mot
     * @param instruction
     * @return retourne le mot lu
     */
    public String lireIDE(Compteur cpt,String instruction){
        //compteurIndex = i;
        StringBuilder sb = new StringBuilder();
        
        Character car = instruction.charAt(cpt.getValue());
        while( Character.isAlphabetic(car) && cpt.getValue()<instruction.length() ){
            //if(  cpt.getValue() == instruction.length()){ break; }
            car = instruction.charAt(cpt.getValue());
            sb.append(car);
            cpt.incrementer();
        }
        return sb.toString();
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
                || motLu.compareToIgnoreCase("wait")==0){
            return motLu;
        }else{
            return null;
        }
    }
    
    
    
    
    /**
     * permet de lire un mot
     * @param instruction
     * @return retourne le mot lu
     */
    public String lireMot(Compteur cpt, String instruction){
        StringBuilder sb = new StringBuilder();
        //this.compteurIndex = i;
        Character car = instruction.charAt(cpt.getValue());
        while( Character.isLetter(car) ){
            sb.append(car);
            cpt.incrementer();
            if(  cpt.getValue() == instruction.length()){ break; }
            car = instruction.charAt(cpt.getValue());
        }
        return sb.toString();
    }
    
    
        /**
     * permet de lire les espaces d'un instruction
     * @param instruction 
     */
    public void lireLesEspaces(Compteur cpt, String instruction){
        //this.compteurIndex = i;
        Character car = instruction.charAt(cpt.getValue());
        while( Character.isWhitespace(car) && cpt.getValue() < instruction.length() ){
            cpt.incrementer();
            if(  cpt.getValue() == instruction.length()){ break; }
            car = instruction.charAt(cpt.getValue());
        }
    }
    
    
    
     public void enleverLesEspaces(Compteur cpt, String instruction){
            //this.compteurIndex = i;
            Character car = instruction.charAt(cpt.getValue());
            if( Character.isWhitespace(car) ){
                    lireLesEspaces(cpt, instruction);
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
    
    
    public boolean estUneParentheseOuvrante(Character car){
        return (int)car == 40;
    }
    
    
    public boolean estUneParentheseFermante(Character car){
        return (int)car == 41;
    }
    
    public boolean estUneAccoladeOuvrante(Character car){
        return car == 123;
    }
    
    public boolean estUneAccoladeFermante(Character car){
        return car == 125;
    }
    
    /**
     * permet de verifier si le caractere est un caractere de comparaison
     * @param car
     * @return 
     */
    public boolean estUnComparateur(Character car){
        switch (car) {
            case '<' : return true;
            case '=' : return true;
            case '>' : return true;
            default  : return false;
        }
    }
    
    /**
     * permet de tester, si le chaine en parametre contient un mot clé
     * @param str
     * @return 
     */
    public boolean contientMotReserve(String str){
        String[] motCle = {"if", "then", "else", "while", "do", "let", "in", "end", "be", "fork", "wait"};
        for (int i = 0; i < motCle.length; i++) {
            if (str.contains(motCle[i]) || str.contains(motCle[i].toUpperCase())) {
                return true;
            }
        }
        return false;
    }
    
}
