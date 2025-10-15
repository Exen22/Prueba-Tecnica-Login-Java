package gui;

import javax.swing.SwingUtilities;

/**
 *
 * @author Willian Coral
 */
public class Main{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new LoginFrame();
            }
            
        });
    }
    
}
