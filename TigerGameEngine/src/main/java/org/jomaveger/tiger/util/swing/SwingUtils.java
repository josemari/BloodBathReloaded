package org.jomaveger.tiger.util.swing;

import java.awt.Window;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.UIManager;
import org.apache.log4j.Logger;

public final class SwingUtils {
    
    private static final Logger LOGGER = Logger.getLogger(SwingUtils.class);
    
    private SwingUtils() {
    }
    
    public static void useNativeLookAndFeel() {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
        } catch (Exception ex) {
            
            LOGGER.error("Failed to set native GUI look and feel.");
        }
    }
    
    public static void setWindowIcon(Window window, InputStream imageInputStream) {
        try {
            
            window.setIconImage(ImageIO.read(imageInputStream));
        
        } catch (Exception ex) {
            
            LOGGER.error("Failed to ser window icon");
        }
    }
    
    public static void setDialogIcon(JDialog dialog, InputStream imageInputStream) {
        try {
            
            dialog.setIconImage(ImageIO.read(imageInputStream));
        
        } catch (Exception ex) {
            
            LOGGER.error("Failed to ser window icon");
        }
    }
}
