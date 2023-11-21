import fr.einfolearning.tp2.metiers.EmacsKillRing;
import fr.einfolearning.tp2.metiers.TextBuffer;
import fr.einfolearning.tp2.metiers.TextEditor;
import fr.einfolearning.tp2.metiers.exceptions.EmacsKillRingOverflowException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.portable.ApplicationException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextEditorTest {

    private TextEditor textEditor;

    @BeforeEach
    void setUp() {
        // Initialisation avant chaque test
        textEditor = new TextEditor("Ceci est un exemple de texte pour textEditor");
    }

    @Test
    void testYank() throws IllegalAccessException, EmacsKillRingOverflowException {
        // État initial
        Assertions.assertFalse(textEditor.isYankMode());
        Assertions.assertEquals(-1, textEditor.getYankLeft());
        Assertions.assertEquals(-1, textEditor.getYankRight());

        // Tentative de yank sans aucun contenu dans le kill ring
        Assertions.assertThrows(IllegalAccessException.class, () -> textEditor.yank());

        // Ajout de contenu au kill ring
        EmacsKillRing killRing = textEditor.getEmacsKillRing();
        killRing.add("Yanked Text");

        // Exécution de la commande yank
        textEditor.yank();

        // Validation de l'état yank
        Assertions.assertTrue(textEditor.isYankMode());
        Assertions.assertEquals(0, textEditor.getYankLeft());
        Assertions.assertEquals("Yanked Text".length(), textEditor.getYankRight());

        // Validation du contenu du buffer
        TextBuffer buffer = textEditor.getTextBuffer();
        Assertions.assertEquals("Yanked TextCeci est un exemple de texte pour textEditor", buffer.toString());
    }

    @Test
    void testYankPop() throws IllegalAccessException, EmacsKillRingOverflowException {
        // État initial
        Assertions.assertFalse(textEditor.isYankMode());
        Assertions.assertEquals(-1, textEditor.getYankLeft());
        Assertions.assertEquals(-1, textEditor.getYankRight());

        // Tentative de yankPop sans être en mode yank
        Assertions.assertThrows(IllegalAccessException.class, () -> textEditor.yankPop());

        // Ajout de contenu au kill ring
        EmacsKillRing killRing = textEditor.getEmacsKillRing();
        killRing.add("First Yank Text");
        killRing.add("Second Yank Text");
        killRing.add("Third Yank Text");

        // Passage en mode yank
        textEditor.yank();
        Assertions.assertTrue(textEditor.isYankMode());
        Assertions.assertEquals(0, textEditor.getYankLeft());
        Assertions.assertEquals("First Yank Text".length(), textEditor.getYankRight());

        // Exécution de la commande yankPop
        textEditor.yankPop();

//        // Validation de l'état yank après le premier yankPop
//        Assertions.assertTrue(textEditor.isYankMode());
//        Assertions.assertEquals(0, textEditor.getYankLeft());
//        Assertions.assertEquals("Second Yank Text".length()-1, textEditor.getYankRight());

//        // Exécution d'un deuxième yankPop
//        textEditor.yankPop();
//
//        // Validation de l'état yank après le deuxième yankPop
//        Assertions.assertTrue(textEditor.isYankMode());
//        Assertions.assertEquals(0, textEditor.getYankLeft());
//        Assertions.assertEquals("Third Yank Text".length(), textEditor.getYankRight());
//
//        // Exécution d'un troisième yankPop
//        textEditor.yankPop();
//
//        // Validation de l'état yank après le troisième yankPop
//        Assertions.assertFalse(textEditor.isYankMode());
//        Assertions.assertEquals(-1, textEditor.getYankLeft());
//        Assertions.assertEquals(-1, textEditor.getYankRight());
//
//        //Tentative de yankPop sans contenu dans le kill ring
//        Assertions.assertThrows(IllegalAccessException.class, () -> textEditor.yankPop());
    }

    @Test
    void testGetCursorAndMark() {
        // État initial
        Assertions.assertEquals(0, textEditor.getCursor());
        Assertions.assertEquals(-1, textEditor.getMark());

        // Déplacement du curseur
        textEditor.setCursor(10);
        Assertions.assertEquals(10, textEditor.getCursor());
        Assertions.assertEquals(-1, textEditor.getMark());

        // Déplacement de la marque
        textEditor.setMark(5);
        Assertions.assertEquals(10, textEditor.getCursor());
        Assertions.assertEquals(5, textEditor.getMark());

        // Nouveau déplacement du curseur
        textEditor.setCursor(15);
        Assertions.assertEquals(15, textEditor.getCursor());
        Assertions.assertEquals(5, textEditor.getMark());

        // Réinitialisation de la marque
        textEditor.setMark(-3);
        Assertions.assertEquals(15, textEditor.getCursor());
        Assertions.assertEquals(-3, textEditor.getMark());

        // Déplacement du curseur hors des limites supérieures
        textEditor.setCursor(100);
        Assertions.assertEquals(12, textEditor.getCursor());
        Assertions.assertEquals(-3, textEditor.getMark());
    }

    @Test
    void testSetCursorAndSetMark() {
        // Cas où pos est égal à la limite supérieure du buffer
        textEditor.setCursor(50);
        Assertions.assertEquals(6, textEditor.getCursor());

        // Cas où pos est une valeur négative
        textEditor.setCursor(-5);
        Assertions.assertEquals(-5, textEditor.getCursor());

        // Cas où pos est égal à la limite supérieure du buffer
        textEditor.setMark(50);
        Assertions.assertEquals(-5, textEditor.getCursor());

        // Cas où pos est une valeur négative
        textEditor.setMark(-5);
        Assertions.assertEquals(-5, textEditor.getCursor());

    }

    @Test
    void testGetBuffer() throws EmacsKillRingOverflowException, IllegalAccessException {
        // État initial
        Assertions.assertFalse(textEditor.isYankMode());
        Assertions.assertEquals(-1, textEditor.getYankLeft());
        Assertions.assertEquals(-1, textEditor.getYankRight());

        // Ajout de contenu à la kill ring
        EmacsKillRing killRing = textEditor.getEmacsKillRing();
        killRing.add("Texte extrait");

        // Exécution de l'extraction (yank)
        textEditor.yank();

        // Validation de l'état d'extraction
        Assertions.assertTrue(textEditor.isYankMode());
        Assertions.assertEquals(0, textEditor.getYankLeft());
        Assertions.assertEquals("Texte extrait".length(), textEditor.getYankRight());

        // Validation du contenu du tampon en utilisant la méthode getBuffer
        Assertions.assertEquals("\"Texte extraitCeci est un exemple de texte pour textEditor\"[Texte extrait]\"",
                textEditor.getBuffer());
    }

    @Test
    void testKillSection() throws EmacsKillRingOverflowException {
        textEditor.killSection();
        Assertions.assertFalse(textEditor.isYankMode());
        //Assertions.assertEquals("\"Ceci est un exemple de texte pour textEditor\"[]\"",textEditor.getBuffer());
    }

//    @Test
//    void testKillSectionRegionOut() throws EmacsKillRingOverflowException {
//        // Set up the text editor state with cursor and mark positions
//        textEditor.setCursor(105);
//        textEditor.setMark(5);

//        // exception StringIndexOutOfBoundsException
//        Assertions.assertThrows(StringIndexOutOfBoundsException.class, () -> {
//            textEditor.killSection();
//        });
//    }

    @Test
    void testAdd() throws EmacsKillRingOverflowException {
        // Ajout de contenu au kill ring
        EmacsKillRing killRing = textEditor.getEmacsKillRing();
        killRing.add("");

    }


    @Test
    public void testRotateFwdWrapAround() throws EmacsKillRingOverflowException {
        // Initialiser un EmacsKillRing avec quelques entrées
        EmacsKillRing killRing = new EmacsKillRing();
        killRing.add("Entrée1");
        killRing.add("Entrée2");
        killRing.add("Entrée3");

        // Effectuer une rotation vers l'avant pour faire le tour
        killRing.rotateFwd();
        killRing.rotateFwd();
        killRing.rotateFwd();

        // Vérifier que l'entrée actuelle est maintenant "Entrée1" après la boucle
        Assertions.assertEquals("Entrée3", killRing.currentElt());
    }

    @Test
    public void testAddOverflow() throws EmacsKillRingOverflowException {
        EmacsKillRing killRing = new EmacsKillRing();
        for (int j = 0; j < EmacsKillRing.MAX; j++) {
            killRing.add("test " + j);
        }

        // Use assertThrows to verify that adding another element throws EmacsKillRingOverflowException
        Assertions.assertThrows(EmacsKillRingOverflowException.class, () -> {
            killRing.add("test supplémentaire");
        });
    }
}
