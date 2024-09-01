import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MemoryGameMenu extends JFrame {

    private BufferedImage backgroundImage;

    public MemoryGameMenu() {      
        super("Memory Game"); 
        // setTitle("Memory Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        try {
            String basePath = new File("").getAbsolutePath();
            File imageFile = new File(basePath + "/Assets/background.jpg");
            backgroundImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Image file not found: /Assets/background.jpg");
            e.printStackTrace();
        }
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(null);
        
        JTextArea textArea = new JTextArea("Memory Card Game");
        textArea.setEditable(false); 
        textArea.setForeground(Color.BLUE); 
        textArea.setFont(new Font("Arial", Font.PLAIN, 24)); 
        textArea.setOpaque(false); 
        textArea.setBounds(300, 50, 300, 50); 



        JButton startButton = new JButton("Start Game");
        JButton selectLevelButton = new JButton("Select Level");
        JButton instructionsButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");

        startButton.setBounds(300, 150, 200, 50);
        selectLevelButton.setBounds(300, 220, 200, 50);
        instructionsButton.setBounds(300, 290, 200, 50);
        exitButton.setBounds(300, 360, 200, 50);

        panel.add(textArea);
        panel.add(startButton);
        panel.add(selectLevelButton);
        panel.add(instructionsButton);
        panel.add(exitButton);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GameWindow(1).setVisible(true);
            }
        });

        selectLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Level 3", "Level 2", "Level 1"};
                int choice = JOptionPane.showOptionDialog(null, "Select a level:", "Select Level", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (choice == 2) {
                    new GameWindow(1).setVisible(true);
                } else if (choice == 1) {
                    new GameWindow(2).setVisible(true);
                } else if (choice == 0) {
                    new GameWindow(3).setVisible(true);
                }
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Instructions:\n\n" +
                        "There are 3 levels in the game. It gets gradually harder!\n" +
                        "Match all pairs of the cards to win.");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(panel);
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MemoryGameMenu();
            }
        });
    }
}