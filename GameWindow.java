import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameWindow extends JFrame {
    private int level;
    private int triesLeft;
    private int score;
    private List<Card> cards;
    private Card firstSelectedCard;
    private Card secondSelectedCard;
    private JLabel triesLabel;
    private JPanel gamePanel;
    

    public GameWindow(int level) {
        this.level = level;
        initializeGame();
    }

    private void initializeGame() {
        setTitle("Flash Cards Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(4, 4));
        cards = createCards();
        //Collections.shuffle(cards);
        new Thread(() -> {
            Collections.shuffle(cards);
            SwingUtilities.invokeLater(() -> {
                for (Card card : cards) {
                    gamePanel.add(card.getButton());
                }
                gamePanel.revalidate();
                gamePanel.repaint();
            });
        }).start();

        for (Card card : cards) {
            gamePanel.add(card.getButton());
        }

        add(gamePanel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(100, 145, 235));
        infoPanel.setLayout(new FlowLayout());
        triesLabel = new JLabel("Tries Left: " + triesLeft);
        JLabel levelLabel = new JLabel("LEVEL: " + level);

        Font font = new Font("Arial", Font.BOLD, 16);
        triesLabel.setFont(font);
        levelLabel.setFont(font);
        triesLabel.setForeground(Color.WHITE);
        levelLabel.setForeground(Color.WHITE);
        infoPanel.add(levelLabel);
        infoPanel.add(triesLabel);
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        

        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        gameMenu.add(restartItem);
        gameMenu.add(highScoresItem);

        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutDeveloper = new JMenuItem("About Developer");
        JMenuItem aboutGame = new JMenuItem("About Game");
        aboutMenu.add(aboutDeveloper);
        aboutMenu.add(aboutGame);


        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        highScoresItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLast10Scores();
            }
        });

        aboutDeveloper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Developer Name & Surname: Goktug Emre Tutar\nStudent number: 20210702056");
            }
        });

        aboutGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Flash Cards Game is a fun memory game where players match cards to test their memory skills.\n"
                    + "The game features three levels of increasing difficulty, each with themed cards.\n"
                    + "The goal is to complete each level to achieve high scores.");
            }
        });
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        menuBar.add(aboutMenu);
        menuBar.add(exitButton);

        setJMenuBar(menuBar);

        mainPanel.add(infoPanel);

        add(mainPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private void restartGame() {
        getContentPane().removeAll();
        firstSelectedCard = null;
        secondSelectedCard = null;
        initializeGame();
        revalidate();
        repaint();
    }

    private List<Card> createCards() {
        List<Card> cardList = new ArrayList<>();
        String[] images = {};
        String backImagePath = "";
        String basePath = new File("").getAbsolutePath();
        switch (level) {
            case 1:
                String baseDirLevel1 = basePath + "/Assets/Level1-InternetAssets/";
                images = new String[] {
                     baseDirLevel1 + "0.png", baseDirLevel1 + "1.png", 
                     baseDirLevel1 + "2.png", baseDirLevel1 + "3.png", 
                     baseDirLevel1 + "4.png", baseDirLevel1 + "5.png", 
                     baseDirLevel1 + "6.png", baseDirLevel1 + "7.png"
                };
                backImagePath = basePath + "/Assets/Level1-InternetAssets/no_image.png";
                break;
            case 2:
                String baseDirLevel2 = basePath + "/Assets/Level2-CyberSecurityAssets/";
                images = new String[] {
                    baseDirLevel2 + "0.png", baseDirLevel2 + "1.png", 
                    baseDirLevel2 + "2.png", baseDirLevel2 + "3.png", 
                    baseDirLevel2 + "4.png", baseDirLevel2 + "5.png", 
                    baseDirLevel2 + "6.png", baseDirLevel2 + "7.png"
                };
                backImagePath = basePath + "/Assets/Level2-CyberSecurityAssets/no_image.png";
                break;
            case 3:
                String baseDirLevel3 = basePath + "/Assets/Level3-GamingComputerAssets/";
                images = new String[] {
                    baseDirLevel3 + "0.png", baseDirLevel3 + "1.png", 
                    baseDirLevel3 + "2.png", baseDirLevel3 + "3.png", 
                    baseDirLevel3 + "4.png", baseDirLevel3 + "5.png", 
                    baseDirLevel3 + "6.png", baseDirLevel3 + "7.png"
                };
                backImagePath = basePath + "/Assets/Level3-GamingComputerAssets/no_image.png";
                break;
            default:
                break;
        }
        
        for (String image : images) {
            cardList.add(new Card(image,backImagePath));
            cardList.add(new Card(image,backImagePath));
        }
    
        for (Card card : cardList) {
            card.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCardSelection(card);
                }
            });
        }
    
        switch (level) {
            case 1:
                triesLeft = 18;
                break;
            case 2:
                triesLeft = 15;
                break;
            case 3:
                triesLeft = 12;
                break;
        }
        score = 0;
    
        return cardList;
    }

        private void handleCardSelection(Card selectedCard) {
        // İlk olarak, eğer seçilen kart zaten eşleşmişse veya açılmışsa, işlem yapmaya gerek yok.
        if (selectedCard.isMatched() || selectedCard.isRevealed()) {
            return;
        } else if (firstSelectedCard == null) {
            firstSelectedCard = selectedCard;
        } else if (secondSelectedCard == null ) {
            secondSelectedCard = selectedCard;
            checkMatch();
        }
    }
    private void checkMatch() {
        disableAllCards();
        if (firstSelectedCard.getImagePath().equals(secondSelectedCard.getImagePath())) {
            // if (firstSelectedCard.cardnum == secondSelectedCard.cardnum) {
            //     triesLeft--;
            //     JOptionPane.showMessageDialog(null, "Do not select same place");
            //     firstSelectedCard.hide();
            //     enableAllCards();
            // }
                score += getScoreForMatch();
                firstSelectedCard.setMatched(true);
                secondSelectedCard.setMatched(true);
                resetSelections();
                enableAllCards();

        }else {
            triesLeft--;
            score += reduceScoreForMatch();
            Timer timer = new Timer(550, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(level == 3){
                        //threads ile yaz
                        new Thread(() -> shuffleAllCards()).start();
                    }
                    firstSelectedCard.hide();
                    secondSelectedCard.hide();
                    resetSelections();
                    enableAllCards();
                }
            });
            timer.setRepeats(false);//false oldugu icin sadece bir kez saat tetiklenir
            timer.start();
        }
       
        updateLabels();
        checkGameOver();
    }
    //lvl 3 te her yanlis yaptiginda kartlari hareket ettir matched haric
    // tek hatasi ilk basta matchlenen kartlar hareket ediyor daha sonra etmiyor !!
    private void shuffleAllCards() {
        List<Card> UnmatchedCards = new ArrayList<>();
        List<Card> matchedCards = new ArrayList<>();
        List<Integer> matchedcardsindex = new ArrayList<>();
        int counter = 0 ;
        for (Card card : cards) {
            if (card.isMatched()) {
                matchedCards.add(card);
                matchedcardsindex.add(counter);
            }
            else UnmatchedCards.add(card);
            counter++;
        }
        Collections.shuffle(UnmatchedCards);

        SwingUtilities.invokeLater(() -> {
            gamePanel.removeAll();
            int j = 0;
            for (int i = 0; i < 16; i++) {
                if (matchedcardsindex.contains(i)) {
                    int index = matchedcardsindex.indexOf(i);
                    Card currentcard = matchedCards.get(index);
                    gamePanel.add(currentcard.getButton());
                    j--;
                }
                else {
                    gamePanel.add(UnmatchedCards.get(j).getButton());
                }
                j++;
            }
            UnmatchedCards.clear();
            matchedCards.clear();
            matchedcardsindex.clear();
            gamePanel.revalidate();
            gamePanel.repaint();
        });
    }

    private void disableAllCards() {
        for (Card card : cards) {
            card.getButton().setEnabled(false);
        }
    }
    
    private void enableAllCards() {
        for (Card card : cards) {
            card.getButton().setEnabled(true);
        }
    }

    private void resetSelections() {
        firstSelectedCard = null;
        secondSelectedCard = null;
    }

    private int getScoreForMatch() {
        switch (level) {
            case 1:
                return 5;
            case 2:
                return 4;
            case 3:
                return 3;
        }
        return 0;
    }

    private int reduceScoreForMatch(){
        switch (level) {
            case 1:
                return -1;
            case 2:
                return -2;
            case 3:
                return -3;
        }
        return 0;
    }

    private void updateLabels() {
        triesLabel.setText("Tries Left: " + triesLeft);        
    }
    private void displayLast10Scores() {
        try {
            List<String> scores = new ArrayList<>();
            File file = new File("high_scores.txt");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    scores.add(scanner.nextLine());
                }
                scanner.close();
            }
            if (scores.size() > 10) {
                scores = scores.subList(scores.size() - 10, scores.size());
            }
            JOptionPane.showMessageDialog(this, String.join("\n", scores), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveScore(String playerName, int score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("high_scores.txt", true));
            writer.write(playerName + ": " + score);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkGameOver() {
        if (triesLeft <= 0) {
            String playerName = JOptionPane.showInputDialog(this, "Game Over! Your score: " + score + "\nEnter your name:");
            saveScore(playerName, score);
            dispose();
            new MemoryGameMenu();
        } else if (allCardsMatched()) {
            level++;
            if (level == 4) {
                String playerName = JOptionPane.showInputDialog(this, "You Win! Your score: " + score + "\nEnter your name:");
                saveScore(playerName, score);
                dispose();
                new MemoryGameMenu();
            }
            else{
                JOptionPane.showMessageDialog(this, "You win! Are you ready for next level: "+level);
                dispose();
                new GameWindow(level);
            }
            
        }
    }

    private boolean allCardsMatched() {
        for (Card card : cards) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow(1));
    }
}