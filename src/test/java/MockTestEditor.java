import fr.einfolearning.tp2.metiers.EmacsKillRing;
import fr.einfolearning.tp2.metiers.TextBuffer;
import fr.einfolearning.tp2.metiers.TextEditor;
import fr.einfolearning.tp2.metiers.exceptions.EmacsKillRingOverflowException;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class MockTestEditor {

    // Teste l'enchaînement de la méthode yank suivi de yankPop avec des mocks
    // les mocks remplace les vrai objets yank et yankPop par des objets simulé
    @Test
    void testMockYankandYankPop() throws IllegalAccessException, EmacsKillRingOverflowException {
        // Créer un mock de l'objet EmacsKillRing et TextBuffer
        EmacsKillRing mockTestEmacs = mock(EmacsKillRing.class);
        TextBuffer mockTextBuffer = mock(TextBuffer.class);

        // Créer une instance de TextEditor avec le mock
        TextEditor textEditor = new TextEditor("Test");

        // Remplace les instances réelles d'EmacsKillRing et TextBuffer dans textEditor par des objets simulés (mocks) pour les tests.
        textEditor.emacsKillring = mockTestEmacs;
        textEditor.buffer = mockTextBuffer;

        // Programmation de la simulation du mock
        when(mockTestEmacs.isEmpty()).thenReturn(false); // Le kill ring n'est pas vide
        when(mockTestEmacs.currentElt()).thenReturn("Mocked Yank Content"); // Contenu simulé du kill ring

        // Appel de la méthode yank et yankPop sur l'éditeur de texte
        textEditor.yank();
        textEditor.yankPop();

        //vérifie getEmacsKillRing
        verify(textEditor.getEmacsKillRing(), times(1)).rotateFwd(); // Vérifie l'appel de rotateFwd une fois
        verify(textEditor.getEmacsKillRing(), times(2)).currentElt(); // Vérifie l'appel de currentElt 2 fois
        // Vérification que la méthode del est appelée avec les arguments appropriés
        verify(mockTextBuffer, times(1)).del(anyInt(), anyInt()); // Vérifie l'appel de del une fois avec n'importe quelles positions
        verify(mockTextBuffer, times(2)).insert(eq("Mocked Yank Content"), anyInt()); // Vérifie l'appel de insert une fois avec le contenu simulé et n'importe quelle position
        //test de la methode currentElt appelé dans yank et yankPop
        verify(mockTestEmacs,  times(2)).currentElt();
    }

    @Test
    void TestMockKillSection() throws EmacsKillRingOverflowException {
        EmacsKillRing mockEmacsKillRing = mock(EmacsKillRing.class);
        TextBuffer mockTextBuffer = mock(TextBuffer.class);

        // Créer une instance de TextEditor avec le mock
        TextEditor textEditor = new TextEditor("Test");

        // Appelez la méthode killSection
        try {
            textEditor.emacsKillring = mockEmacsKillRing;
            textEditor.buffer = mockTextBuffer;
            textEditor.killSection();
        } catch (EmacsKillRingOverflowException e) {
            System.out.println("Erreur : "+e);
            // Gérer l'exception si nécessaire
        }

        // Utilisez Mockito pour vérifier que les méthodes appropriées ont été appelées avec les arguments corrects
        verify(mockTextBuffer, times(1)).del(anyInt(), anyInt());
        verify(mockTextBuffer, times(1)).substr(anyInt(), anyInt()); // Vérifiez l'appel à substr

        // Vérifiez que la méthode add de emacsKillRing n'est PAS appelée ici
         verify(mockEmacsKillRing, never()).add("");
    }
}
