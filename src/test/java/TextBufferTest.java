import fr.einfolearning.tp2.metiers.TextBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TextBufferTest {

    @Test
    public void testString(){
        TextBuffer textBuffer = new TextBuffer("test string");
        textBuffer.toString();
        Assertions.assertEquals("test string", textBuffer.toString());
    }

    @Test
    public void testInsert(){
        TextBuffer textBuffer = new TextBuffer("ceci est un d'insertion");
        textBuffer.insert("test ",12);
        Assertions.assertEquals("ceci est un test d'insertion", textBuffer.toString());
    }

    @Test
    public void testDel(){
        TextBuffer textBuffer = new TextBuffer("ceci est un test test de suppression");
        textBuffer.del(12,17);
        Assertions.assertEquals("ceci est un test de suppression", textBuffer.toString());
    }

    @Test
    public void testDelNegatif(){
        TextBuffer textBuffer = new TextBuffer("ceci est un test de suppression");
        textBuffer.del(-5,0);
        Assertions.assertEquals("ceci est un test de suppression", textBuffer.toString());
    }

    @Test
    public void testDelNegatif2(){
        TextBuffer textBuffer = new TextBuffer("ceci est un test de suppression");
        textBuffer.del(0,-5);
        Assertions.assertEquals("ceci est un test de suppression", textBuffer.toString());
    }

    @Test
    public void testDelMaxP(){
        TextBuffer textBuffer = new TextBuffer("ceci est un test de suppression");
        textBuffer.del(4,250);
        Assertions.assertEquals("ceci", textBuffer.toString());
    }

    @Test
    public void testDelMaxP2(){
        TextBuffer textBuffer = new TextBuffer("ceci est un test de suppression");
        textBuffer.del(200,250);
        Assertions.assertEquals("ceci est un test de suppression", textBuffer.toString());
    }

    @Test
    public void testSubstring() {
        TextBuffer textBuffer = new TextBuffer("Hello, World!");
        Assertions.assertEquals("World", textBuffer.substr(7, 12));
    }

    @Test
    public void testMaxP() {
        TextBuffer textBuffer = new TextBuffer("ceci est un test de MaxP");
        int maxLength = textBuffer.maxP();
        Assertions.assertEquals(24, maxLength);
    }

}
