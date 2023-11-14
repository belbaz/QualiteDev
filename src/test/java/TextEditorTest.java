import fr.einfolearning.tp2.metiers.TextEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextEditorTest {

    private TextEditor textEditor;

    @BeforeEach
    void setUp() {
        // Initialisation avant chaque test
        textEditor = new TextEditor("Ceci est un exemple de texte pour textEditor");
    }


    @Test
    void testYankPopWithoutYank() {
        // Act & Assert
        assertThrows(IllegalAccessException.class, () -> textEditor.yankPop());
    }
}
