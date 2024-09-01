import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;

public class Card {
    private JButton button;
    private boolean matched;
    private boolean revealed;
    private ImageIcon frontIcon;
    private ImageIcon backIcon;
    private String imagePath;

    public Card(String frontImagePath, String backImagePath) {
        this.frontIcon = new ImageIcon(frontImagePath);
        this.backIcon = new ImageIcon(backImagePath);
        this.imagePath = frontImagePath; 
        this.matched = false;
        this.revealed = false;
        this.button = new JButton();
        this.button.setIcon(this.backIcon);
        
        if (frontIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Front image failed to load: " + frontImagePath);
        }
        if (backIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Back image failed to load: " + backImagePath);
        }
        
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reveal();
                // if (!matched) {
                //     if (revealed) {
                //         hide();
                //     } else {
                //         reveal();
                //     }
                // }
            }
        });
    }

    public JButton getButton() {
        return button;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        if (!matched) {
            revealed = true;
            button.setIcon(frontIcon);
        }
    }

    public void hide() {
        if (!matched) {
            revealed = false;
            button.setIcon(backIcon);
        }
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
        if (matched) {
            button.setIcon(frontIcon);
            button.setEnabled(false);
        }
    }

    public String getImagePath() {
        return imagePath;
    }

}
