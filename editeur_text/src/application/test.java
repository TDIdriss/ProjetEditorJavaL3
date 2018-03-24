package application;
 
import javax.swing.JFrame;
import javax.swing.JTextPane;
 
import com.inet.jortho.SpellChecker;
 
public class test {
 
public static void main(String a) {
 
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
    SpellChecker.registerDictionaries(null, "fr", "fr" ); 
 
    JTextPane textpane = new JTextPane();
 
    
     SpellChecker.registerDictionaries(null, "en", "en" ); 
 
    JTextPane textpane1 = new JTextPane();
     textpane1.setText(a);
    SpellChecker.register(textpane1);
 a=textpane1.getText();
 
    frame.getContentPane().add(textpane1);
 
    frame.setSize(300,300);
    frame.setVisible(true);
 
 }
 
}