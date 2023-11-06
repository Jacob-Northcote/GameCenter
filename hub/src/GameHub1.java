import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameHub1 extends JFrame {
    private JPanel currentPanel;

    public GameHub1() {
        setTitle("Game Hub");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentPanel = createHubPanel();
        add(currentPanel);

        setVisible(true);
    }

    private JPanel createHubPanel() {
        JPanel hubPanel = new JPanel();
        hubPanel.setLayout(new GridBagLayout()); 
        
        JLabel welcomeLabel = new JLabel("Welcome to GameCenter!");
        welcomeLabel.setForeground(Color.RED); 
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36)); 

        
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 6; 
        labelConstraints.insets = new Insets(10, 10, 10, 10); 
        hubPanel.add(welcomeLabel, labelConstraints);

        JButton game1Button = new JButton("Snake Game");
        JButton game2Button = new JButton("Laser Bash");
        JButton game3Button = new JButton("Memory Game");
        JButton game4Button = new JButton("Black Jack");
        JButton game5Button = new JButton("Pong Game");
        JButton game6Button = new JButton("Brick Breaker");

        
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.gridwidth = 1; 
        buttonConstraints.anchor = GridBagConstraints.NORTH; 
        buttonConstraints.insets = new Insets(10, 10, 10, 10); 

        hubPanel.add(game1Button, buttonConstraints);
        buttonConstraints.gridx = 1;
        hubPanel.add(game2Button, buttonConstraints);
        buttonConstraints.gridx = 2;
        hubPanel.add(game3Button, buttonConstraints);
        buttonConstraints.gridx = 3;
        hubPanel.add(game4Button, buttonConstraints);
        buttonConstraints.gridx = 4;
        hubPanel.add(game5Button, buttonConstraints);
        buttonConstraints.gridx = 5;
        hubPanel.add(game6Button, buttonConstraints);

        return hubPanel;
    }
    private JPanel createGamePanel1() {
        JPanel gamePanel1 = new JPanel();
       
        return gamePanel1;
    }

    private JPanel createGamePanel2() {
        JPanel gamePanel2 = new JPanel();
        
        return gamePanel2;
    }
    private JPanel createGamePanel3() {
        JPanel gamePanel3 = new JPanel();
       
        return gamePanel3;
    }
    private JPanel createGamePanel4() {
        JPanel gamePanel4 = new JPanel();
        
        return gamePanel4;
    }
    private JPanel createGamePanel5() {
        JPanel gamePanel5 = new JPanel();
        
        return gamePanel5;
    }
    private JPanel createGamePanel6() {
        JPanel gamePanel6 = new JPanel();
        
        return gamePanel6;
    }

    private void switchToGamePanel(JPanel panel) {
        getContentPane().removeAll();
        currentPanel = panel;
        add(currentPanel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameHub1();
            }
        });
    }
}