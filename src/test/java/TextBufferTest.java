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

        // Test insertion au début du texte
        TextBuffer textBuffer1 = new TextBuffer("ceci est un d'insertion");
        textBuffer1.insert("test ", 0);
        Assertions.assertEquals("test ceci est un d'insertion", textBuffer1.toString());

        // Test insertion à la fin du texte
        TextBuffer textBuffer2 = new TextBuffer("ceci est un d'insertion");
        textBuffer2.insert(" test", textBuffer2.maxP());
        Assertions.assertEquals("ceci est un d'insertion", textBuffer2.toString());



        // Test insertion plus petit que la position
        TextBuffer textBuffer4 = new TextBuffer("ceci est un d'insertion");
        textBuffer4.insert(" test ", -12);
        Assertions.assertEquals("ceci est un d'insertion", textBuffer4.toString());
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
    public void testSubstringNegative() {
        TextBuffer textBuffer = new TextBuffer("Hello, World!");
        Assertions.assertEquals("Hello, World", textBuffer.substr(-7, 12));
    }

    @Test
    public void testSubstringNegative2() {
        TextBuffer textBuffer = new TextBuffer("Hello, World!");
        Assertions.assertEquals("", textBuffer.substr(-5, -3));
    }

    @Test
    public void testSubstringMax() {
        TextBuffer textBuffer = new TextBuffer("Hello, World!");
        Assertions.assertEquals("World!", textBuffer.substr(7, 25));
    }
    @Test
    public void testSubstringMax2() {
        TextBuffer textBuffer = new TextBuffer("Hello, World!");
        Assertions.assertEquals("", textBuffer.substr(70, 25));
    }


    @Test
    public void testMaxP() {
        TextBuffer textBuffer = new TextBuffer("ceci est un test de MaxP");
        int maxLength = textBuffer.maxP();
        Assertions.assertEquals(24, maxLength);
    }

    @Test
    public void testDelWithNegativeFrom() {
        TextBuffer textBuffer = new TextBuffer("Example Text");
        textBuffer.del(-5, 10);
        Assertions.assertEquals("xt", textBuffer.toString());
    }

    @Test
    public void testDelWithExcessiveFrom() {
        TextBuffer textBuffer = new TextBuffer("Example Text");
        textBuffer.del(15, 20);
        Assertions.assertEquals("Example Text", textBuffer.toString());
    }
}
