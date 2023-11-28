import fr.einfolearning.tp2.metiers.EmacsKillRing;
import fr.einfolearning.tp2.metiers.TextBuffer;
import fr.einfolearning.tp2.metiers.TextEditor;
import fr.einfolearning.tp2.metiers.exceptions.EmacsKillRingOverflowException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class EditorTest {

    // Test d'un appel isolé de la méthode yankPop
    @Test
    void testYankPopIsolated() throws IllegalAccessException, EmacsKillRingOverflowException {
        // Créer un mock de l'objet EmacsKillRing
        EmacsKillRing testEmacs = mock(EmacsKillRing.class);
        // Créer un mock de l'objet TextBuffer
        TextBuffer mockTextBuffer = mock(TextBuffer.class);
        // Créer une instance de TextEditor avec le mock
        TextEditor textEditor = new TextEditor("Test");
        textEditor.emacsKillring = testEmacs;

        // Programmation de la simulation du mock
        when(testEmacs.isEmpty()).thenReturn(false); // Le kill ring n'est pas vide
        when(testEmacs.currentElt()).thenReturn("Mocked Yank Content"); // Contenu simulé du kill ring

        testEmacs.add("test");
        // Appel de la méthode yank sur l'éditeur de texte
        textEditor.yank();

        // Appel de la méthode yankPop sur l'éditeur de texte
        textEditor.yankPop();

        //vérifie getEmacsKillRing
        verify(textEditor.getEmacsKillRing(), times(1)).rotateFwd(); // Vérifie l'appel de rotateFwd une fois
        verify(textEditor.getEmacsKillRing(), times(2)).currentElt(); // Vérifie l'appel de currentElt 2 fois
        //vérifie del
        // Vérification que la méthode del est appelée avec les arguments appropriés
        verify(mockTextBuffer, times(1)).del(anyInt(), anyInt()); // Vérifie l'appel de del une fois avec n'importe quelles positions
        //verify(mockTextBuffer, times(1)).insert(eq("Mocked Yank Content"), anyInt()); // Vérifie l'appel de insert une fois avec le contenu simulé et n'importe quelle position
    }
}
