
package notatoplama;

import java.awt.HeadlessException;
import javax.swing.JFrame;

public class NotaToplama extends JFrame{

    public NotaToplama(String string) throws HeadlessException {
        super(string);
    }


    public static void main(String[] args) {
        NotaToplama oyun = new NotaToplama("Nota Oyunu");
    }
    
}
