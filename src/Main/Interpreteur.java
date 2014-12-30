/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author ishola hamed
 */
public class Interpreteur {

    public static void usage(){
        System.out.println("");
        System.out.println("<Usage> : Interpreteur -batch ");
        System.out.println("Ou bien");
        System.out.println("<Usage> : Interpreteur -g ");
        System.out.println("Ou bien");
        System.out.println("<Usage> : Interpreteur -f nomFichier.txt ");
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if(args.length == 1){
            if (args[0].equalsIgnoreCase("-batch")) {
                ModeBatch.main(args);
            }else if( args[0].equalsIgnoreCase("-g") ){
                new ModeGraphique("Interpreteur");
            }else{
                usage();
            }
        }else if(args.length == 2){
            if (args[0].equalsIgnoreCase("-f")) {
                File f = new File(args[1]);
                if(f.isFile()){
                    String[] argFichier = {args[1]};
                    ModeFichier.main(argFichier);
                }else{
                    System.out.println("["+args[1]+"] n'est pas un fichier ! ");
                }
            }else{
                usage();
            }
        }else{
            usage();
        }
    }
    
}
