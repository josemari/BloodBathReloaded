package org.jomaveger.tiger.util.swing;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;

public final class ProgressDialog extends JDialog {
    
    private static final Logger LOGGER = Logger.getLogger(ProgressDialog.class);
    
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private boolean showCancelButton;
    
    public ProgressDialog(Window owner, String title, boolean showCancelButton) {
        super(owner, title);
        
        this.showCancelButton = showCancelButton;
        
        if (owner != null) {
            setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        }
        
        buildUI();
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        setResizable(false);
        setAlwaysOnTop(true);
        
        Dimension preferredDialogSize = getPreferredSize();
        preferredDialogSize.width = this.showCancelButton ? 350 : 300;
        setPreferredSize(preferredDialogSize);
        
        pack();
        
        setLocationRelativeTo(owner);
    }
    
    public ProgressDialog(Window owner, String title, String status) {
        this(owner, title, false);
        
        setStatus(status);
    }
    
    private void buildUI() {
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));
        
        statusLabel = new JLabel("Initializing...", SwingConstants.LEADING);
        
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalGlue());
        container.add(statusPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 2, 0, 2));
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(1000);
        
        buttonPanel.add(progressBar);
        
        progressBar.setPreferredSize(new Dimension(progressBar.getPreferredSize().width, 20));
        
        if (this.showCancelButton) {
            
            buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            JButton cancelButton = new JButton("Cancel");
            buttonPanel.add(cancelButton);
        }
        
        container.add(buttonPanel);
        
        add(container);
        
        pack();
    }
    
    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }
    
    public void setStatus(String status) {
        this.statusLabel.setText(status);
        
        LOGGER.info("Status: " + status);
    }
}
