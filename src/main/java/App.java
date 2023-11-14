//
// Utilisation nominale des classes td3.Buffer, Killring et td3.Editor
//
////////////////////////////////////////////////////////////////
import fr.einfolearning.tp2.metiers.TextBuffer;
import fr.einfolearning.tp2.metiers.TextEditor;
import fr.einfolearning.tp2.metiers.exceptions.EmacsKillRingOverflowException;
public class App {

    public static void main(String[] args) throws EmacsKillRingOverflowException {
        //instanciation,
        TextEditor textEditor = new TextEditor("Ceci est un exemple de texte pour textEditor");
        TextBuffer textBuffer = new TextBuffer("Ceci est un exemple de texte pour textBuffer");

        System.out.println("Contenu initial du texte : " + textEditor.getBuffer());

        //copie d’une zone de texte,
        textEditor.setCursor(5); //Curseur a la position 5
        textEditor.setMark(10); //fin de la zone au caracteres 10
        textEditor.killRingBackup(); // Copie la zone marquée dans le kill ring

        System.out.println("Contenu apres copie : " + textEditor.getBuffer());

        //découpe d’une zone
        textBuffer.del(0,14);
        System.out.println("Contenu apres suppression : " + textBuffer);

        //insertions successives au même endroit
        textBuffer.insert("texte changer ",0);
        System.out.println("Contenu apres insertions : " + textBuffer);


        //Utilise yankPop pour coller la section copiée à la nouvelle position
        try {
            textEditor.yankPop();
        } catch (IllegalAccessException e) {
            System.out.println("Erreur lors de la coller : " + e.getMessage());
        }

        // Affiche le contenu du tampon de texte pour vérifier le résultat
        System.out.println("Contenu après yankPop : " + textEditor.getBuffer());

    }
}
