package ui;

import characters.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleScreen extends JPanel {

    GameCharacter p1, p2;

    JLabel p1HP = new JLabel();
    JLabel p2HP = new JLabel();

    JLabel p1Label;
    JLabel p2Label;

    Random rand = new Random();
    boolean playerTurn = true;

    public BattleScreen(JFrame frame, GameCharacter p1, GameCharacter p2, int mode){

        this.p1 = p1;
        this.p2 = p2;

        setLayout(new BorderLayout());

        // ===== IMAGES =====
        p1Label = new JLabel(loadImage(p1.getName().toLowerCase() + ".png", 250, 250));
        p2Label = new JLabel(loadImage(p2.getName().toLowerCase() + ".png", 250, 250));

        JPanel imagePanel = new JPanel(new GridLayout(1,2));
        imagePanel.setOpaque(false);
        imagePanel.add(p1Label);
        imagePanel.add(p2Label);

        add(imagePanel, BorderLayout.CENTER);

        // ===== HP =====
        JPanel stats = new JPanel(new GridLayout(1,2));
        p1HP.setText(p1.getName()+" HP: "+p1.getHP());
        p2HP.setText(p2.getName()+" HP: "+p2.getHP());

        stats.add(p1HP);
        stats.add(p2HP);

        add(stats, BorderLayout.NORTH);

        // ===== SKILLS =====
        JPanel skills = new JPanel();

        JButton skill1 = new JButton("Skill 1");
        JButton skill2 = new JButton("Skill 2");
        JButton skill3 = new JButton("Ultimate");

        skills.add(skill1);
        skills.add(skill2);
        skills.add(skill3);

        add(skills, BorderLayout.SOUTH);

        skill1.addActionListener(e -> attack(frame, 60));
        skill2.addActionListener(e -> attack(frame, 90));
        skill3.addActionListener(e -> attack(frame, 120));
    }

    private ImageIcon loadImage(String fileName, int w, int h){
        try {
            java.net.URL url = getClass().getResource("/resources/" + fileName);

            if(url == null){
                System.out.println("Missing image: " + fileName);
                return new ImageIcon();
            }

            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);

            return new ImageIcon(img);

        } catch (Exception e){
            e.printStackTrace();
            return new ImageIcon();
        }
    }

    private void attack(JFrame frame, int dmg){

        if(!playerTurn) return;

        // PLAYER ATTACK
        p2.takeDamage(dmg);
        p2HP.setText(p2.getName()+" HP: "+p2.getHP());

        if(p2.getHP() <= 0){
            JOptionPane.showMessageDialog(this, p1.getName()+" WINS!");
            frame.setContentPane(new HomeScreen(frame));
            frame.revalidate();
            return;
        }

        playerTurn = false;

        // ENEMY ATTACK
        int enemyDamage = rand.nextInt(60) + 40;
        p1.takeDamage(enemyDamage);

        p1HP.setText(p1.getName()+" HP: "+p1.getHP());

        if(p1.getHP() <= 0){
            JOptionPane.showMessageDialog(this, p2.getName()+" WINS!");
            frame.setContentPane(new HomeScreen(frame));
            frame.revalidate();
            return;
        }

        playerTurn = true;
    }
}