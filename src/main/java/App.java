//
// Utilisation nominale des classes td3.Buffer, Killring et td3.Editor
//
////////////////////////////////////////////////////////////////

import fr.einfolearning.tp2.metiers.EmacsKillRing;
import fr.einfolearning.tp2.metiers.TextBuffer;
import fr.einfolearning.tp2.metiers.TextEditor;
import fr.einfolearning.tp2.metiers.exceptions.EmacsKillRingOverflowException;

public class App {

    public static void main(String[] args) throws EmacsKillRingOverflowException {
        // A completer

        // Créer une instance de TextEditor en lui passant une chaîne initiale
        TextEditor textEditor = new TextEditor("Ceci est un exemple de texte pour textEditor");

        System.out.println("Contenu initial du texte : " + textEditor.getBuffer());

        textEditor.setCursor(5); // Déplace le curseur à la position 5
        textEditor.setMark(15); // Marque la fin de la zone à copier
        textEditor.killRingBackup(); // Copie la zone marquée dans le kill ring

        // Afficher le contenu après la copie
        System.out.println("Contenu après la copie : " + textEditor.getBuffer());
        
                // Découpe d'une zone
        textEditor.setCursor(8); // Déplace le curseur à la position 8
        textEditor.setMark(20); // Marque la fin de la zone à découper
        textEditor.killSection(); // Découpe la zone marquée

        // Afficher le contenu après la découpe
        System.out.println("Contenu après la découpe : " + textEditor.getBuffer());

        // Insertions successives au même endroit
        textEditor.setCursor(10); // Déplace le curseur à la position 10


        // Afficher le contenu après les insertions
        System.out.println("Contenu après les insertions : " + textEditor.getBuffer());

    }
}
