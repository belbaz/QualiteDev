//
// Utilisation nominale des classes td3.Buffer, Killring et td3.Editor
//
////////////////////////////////////////////////////////////////
import fr.einfolearning.tp2.metiers.EmacsKillRing;
import fr.einfolearning.tp2.metiers.TextBuffer;
import fr.einfolearning.tp2.metiers.TextEditor;
import fr.einfolearning.tp2.metiers.exceptions.EmacsKillRingOverflowException;

public class AppTP2 {

    public static void main(String[] args) throws EmacsKillRingOverflowException, IllegalAccessException {
        TextEditor testyank = new TextEditor("test");

        EmacsKillRing killRing = testyank.getEmacsKillRing();
        killRing.add("First Yank Text");

        testyank.yank();
        testyank.yankPop();

    }
}
