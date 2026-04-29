package ui;

import characters.*;
import javax.swing.*;
import java.awt.*;

public class VersusScreen extends JPanel {

    float alpha = 0f;

    public VersusScreen(JFrame frame, GameCharacter p1, GameCharacter p2){

        setBackground(Color.BLACK);

        new Timer(30, e -> {
            alpha += 0.05f;
            if(alpha >= 1f){
                ((Timer)e.getSource()).stop();

                // after animation → go to battle
                new Timer(1000, ev -> {
                    frame.setContentPane(new BattleScreen(frame, p1, p2, 2));
                    frame.revalidate();
                }).start();
            }
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Impact", Font.BOLD, 60));

        g2.drawString("PLAYER 1", 100, 200);
        g2.drawString("VS", getWidth()/2 - 50, getHeight()/2);
        g2.drawString("PLAYER 2", getWidth()-300, 200);
    }
}