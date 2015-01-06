/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import AST.Parseur;
import ArbreSyntaxique.LanceurCom;
import GestionExceptions.ConflitVariableName;
import GestionExceptions.VariableNotExist;
import GestionExceptions.ComandeNonReconnu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 *
 * @author ishola hamed
 */
public class Interface extends JFrame{

    private JTextArea codeArea;
    private JTextArea environnementArea;
    private JTextArea errorArea ;
    
    public Interface(String title) throws HeadlessException {
        super(title);
        setLayout(new BorderLayout());
        
        
        setJMenuBar(menuBar());
        JToolBar toolB = toolBar();
        toolB.setRollover(true);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        
        codeArea = new JTextArea();
        codeArea.setBackground(Color.black);
        codeArea.setCaretColor(Color.white);
        codeArea.setForeground(Color.white);
        Font f = new Font("Serif", Font.PLAIN, 25);
        codeArea.setFont(f);
        JScrollPane scrollPaneCode = new JScrollPane(codeArea);
        scrollPaneCode.setPreferredSize(new Dimension(800,500));
        
        JTabbedPane ongletCode = new JTabbedPane();
        ongletCode.addTab("Code Utilisateur",  scrollPaneCode);
        
        JPanel p1 = new JPanel(new BorderLayout());
        environnementArea = new JTextArea();
        environnementArea.setEditable(false);
        environnementArea.setForeground(Color.BLUE);
        environnementArea.setFont(f);
        JScrollPane scrollPaneEnv = new JScrollPane(environnementArea);
        
        JTabbedPane ongletEnviron = new JTabbedPane();
        ongletEnviron.addTab("Environnement",  scrollPaneEnv);
        
        p1.add(ongletEnviron);
        p1.setPreferredSize(new Dimension(100, 100));
        
        panel.add(ongletCode);
        
        errorArea = new JTextArea();
        errorArea.setEditable(false);
        errorArea.setForeground(Color.RED);
        JScrollPane scrollPaneErr = new JScrollPane(errorArea);
        
        JTabbedPane ongletErreur = new JTabbedPane();
        ongletErreur.addTab("Sortie d'erreur",  scrollPaneErr);
        
        p1.setPreferredSize(new Dimension(200, 200));
        ongletErreur.setPreferredSize(new Dimension(200, 120));
        
        getContentPane().add(toolB,BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.add(p1, BorderLayout.EAST);
        this.add(ongletErreur,BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    
    public static void main(String[] args) {
        new Interface("Interpreteur");
    }

    public JTextArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(JTextArea codeArea) {
        this.codeArea = codeArea;
    }

    public JTextArea getEnvironnementArea() {
        return environnementArea;
    }

    public void setEnvironnementArea(JTextArea environnementArea) {
        this.environnementArea = environnementArea;
    }

    public JTextArea getErrorArea() {
        return errorArea;
    }

    public void setErrorArea(JTextArea errorArea) {
        this.errorArea = errorArea;
    }
    
    
    
    public JMenuBar menuBar(){
        JMenuBar menuBar = new JMenuBar();
        
                
        /***********  Menu Fichier  *****************/
        JMenu menu = new JMenu("Fichier");
        
        JMenuItem menuItem = new JMenuItem("Charger Code Utilisateur");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chargerFichier();
                } catch (Exception ex){}
            }
        });
        menu.add(menuItem);
        

        menuItem = new JMenuItem("Enregistrer Code Utilisateur");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enregistrerCode();
                } catch (IOException ex) {}
            }
        });
        menu.add(menuItem);

        
        menuBar.add(menu); // ajout du premier Menu dans La barre du menu

        /***********   Menu Schema   *************/
        menu = new JMenu("Execution");
        menuItem = new JMenuItem("executer");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executer();
            }
        });
        menu.add(menuItem);

        menuBar.add(menu); // ajout du premier Menu dans La barre du menu

        /************  Menu Affichage **************/
        menu = new JMenu("Aide");       
        menuItem = new JMenuItem("Aide Commande");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help();
            }
        });
        menu.add(menuItem);

        menuBar.add(menu); // ajout du premier Menu dans La barre du menu
        
        return menuBar;
    }
    
    
    public JToolBar toolBar(){
        JButton load, exec, save, help ;
        JToolBar toolBar = new JToolBar();

        load = new JButton(new ImageIcon(getClass().getResource("images/open-file.png")));
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chargerFichier();
                }catch (Exception ex) {}
            }
        });
        toolBar.add(load);
        toolBar.addSeparator();
        
        save = new JButton(new ImageIcon(getClass().getResource("images/save.png")));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enregistrerCode();
                } catch (IOException ex) {}
            }
        });
        toolBar.add(save);
        toolBar.addSeparator();
        
        exec = new JButton(new ImageIcon(getClass().getResource("images/exec.png")));
        exec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executer();
            }
        });
        toolBar.add(exec);
        toolBar.addSeparator();
        
        help = new JButton(new ImageIcon(getClass().getResource("images/app_help.png")));
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help();
            }
        });
        toolBar.add(help);
        toolBar.addSeparator();
        return toolBar;
    }
    
    
    public void chargerFichier() throws InterruptedException, IOException{
        File file;
        JFileChooser fileOpen = new JFileChooser(".");
        int reponse = fileOpen.showDialog(null,"Ouvrir Fichier");
        if (reponse == JFileChooser.APPROVE_OPTION){
            file = fileOpen.getSelectedFile();
            if (file.isFile()) {
                 String contenu = readFile(file);
                 this.codeArea.setText(contenu);
            }
        }
    }
    
    public void executer(){
        this.errorArea.setText("");
        this.environnementArea.setText("");
        
        if ( ! this.codeArea.getText().isEmpty() ) {
            String code = this.codeArea.getText();
            // on enleve les retours à la ligne et les tabulations
            code = code.replace("\t", " ").replaceAll("\r", " ").replace("\n", " ");
            Parseur a = new Parseur();
            LanceurCom exec = new LanceurCom();
                try {
                    //On ajoute un espace comme delimiteur de fin de ligne
                    String resultatAnalyse = a.analyser(code+" ");
                    //System.out.println(resultatAnalyse);
                    try {
                        exec.executer(code+" ");  // evaluation de la commande apres avoir reconnu la COMMANDE
                    } catch (ConflitVariableName ex) {
                        String msgErreur = " Erreur ["+ex.getMessage()+"] sur  ["+ex.getVariable()+"] , dans la commande ["+ex.getCommande()+"]";
                        this.errorArea.setText(msgErreur);
                    } catch (VariableNotExist ex) {
                        String msgErreur = " Erreur :["+ex.getMessage()+"], la variable ["+ex.getVarNonDeclaree()+"] n'a pas été declaré dans la commande ["+ex.getLaCommande()+"] ";
                        this.errorArea.setText(msgErreur);
                    }
                    //exec.afficherEnvironnement();
                } catch (ComandeNonReconnu ge) {
                    String msgColonne =  " Erreur ["+ ge.getMessage()+"] , dans la commande \n" ;
                    String msgErreur = msgColonne;
                    this.errorArea.setText(msgErreur);
                    JOptionPane.showMessageDialog( this, msgErreur, "Erreur ", JOptionPane.ERROR_MESSAGE);
                }finally{
                    this.environnementArea.setText(exec.getEnvironnement());
                }
        }
    }
    
      public String readFile(File file) throws FileNotFoundException, IOException{
        StringBuilder sb = new StringBuilder();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while( line != null){
            sb.append(line).append("\n");
            line = br.readLine();
        }
        return sb.toString();
    }
      
      
    public void enregistrerCode() throws IOException{
               JFileChooser choix = new JFileChooser();
               choix.setCurrentDirectory(new File("."));
               int retour = choix.showDialog(choix,"Enregistrer sous");
               if (retour == JFileChooser.APPROVE_OPTION){
                   String nomFichier= choix.getSelectedFile().toString();
                    if(nomFichier.endsWith(".txt")||nomFichier.endsWith(".TXT")){}
                   else{
                       if(nomFichier.contains(".")){
                           nomFichier= nomFichier.replace(nomFichier.substring(nomFichier.lastIndexOf(".")),".txt");
                       }else{
                              nomFichier = nomFichier+ ".txt";
                        }
                    }
                   /*ecriture dans le fichier*/
                   try (PrintWriter pwCode = new PrintWriter(new BufferedWriter(new FileWriter(nomFichier)))) {
                       pwCode.println(getCodeArea().getText());
                       pwCode.close();
                   }

               }
    }

    public void help(){
        String msg = "Com   ::=    Ide := NExp \n"
                   + "               |    Com ; Com \n"
                   + "               |    if BExp then Com else Com \n"
                   + "               |    if (BExp) then { Com } else { Com } \n"
                   + "               |    while BExp do Com \n"
                   + "               |    while (BExp) do { Com } \n"
                   + "               |    let Ide be Ide in Com end \n\n"
                   + "NExp  ::=    Num \n"
                   + "               |    Ide \n"
                   + "               |    NExp NOp NExp \n"
                   + "               |    ( NExp NOp NExp )\n\n"
                   + "BExp  ::=    true  \n"
                   + "               |    false   \n"
                   + "               |    NExp COp NExp \n"
                   + "               |    BExp BOp BExp \n"
                   + "               |    not BExp\n"
                   + "               |    not ( BExp )\n\n"
                   + "NOp   ::=    + | - | * | / \n\n"
                   + "Num   ::=    0 | 1 | 2 | ... \n\n"
                   + "COp   ::=    < | > | = \n\n"
                   + "BOp   ::=    and | or ";
        
        JOptionPane optionPane = new JOptionPane();
        optionPane.setPreferredSize(new Dimension(500, 530));
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Commandes Acceptées !");
        dialog.setVisible(true);
    }
    
    

    
    
}
