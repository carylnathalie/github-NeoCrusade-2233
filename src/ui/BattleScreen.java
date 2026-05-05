package ui;

import characters.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleScreen extends JPanel {

    JFrame frame;

    GameCharacter p1, p2;

    JLabel turnIndicator;
    JLabel info;

    boolean playerTurn = true;

    Image bg;
    ImageIcon p1Gif;
    ImageIcon p2Gif;

    private static final int IMG_SIZE = 400; // or 450 if you want bigger

    JButton s1, s2, s3;

    Random rand = new Random();

    boolean gameOver = false;
    String resultText = "";

    int round = 1;

    HPBar p1Bar, p2Bar;


    private Image loadImage(String name) {
        try {
            return new ImageIcon(getClass().getResource("/resources/" + name)).getImage();
        } catch (Exception e) {
            System.out.println("BG LOAD ERROR: " + name);
            return null;
        }
    }

    private ImageIcon loadGif(String name) {
        try {
            java.net.URL url = getClass().getResource("/resources/" + name);
            System.out.println("Loading: " + url); // DEBUG
            return new ImageIcon(url);
        } catch (Exception e) {
            System.out.println("GIF LOAD ERROR: " + name);
            return null;
        }
    }


    public BattleScreen(JFrame frame, GameCharacter p1, GameCharacter p2, int mode) {

        this.frame = frame;
        this.p1 = p1;
        this.p2 = p2;

        bg = loadImage("bg_battle.jpg");

        p1Gif = loadGif(p1.getName().toLowerCase() + ".gif");
        p2Gif = loadGif(p2.getName().toLowerCase() + ".gif");

        setLayout(new BorderLayout());

        new Timer(80, e -> repaint()).start();

        turnIndicator = new JLabel("⚔ PLAYER TURN ⚔", JLabel.CENTER);
        turnIndicator.setFont(new Font("Impact", Font.BOLD, 30));
        turnIndicator.setForeground(new Color(0, 255, 255));
        turnIndicator.setOpaque(true);
        turnIndicator.setBackground(new Color(15, 15, 35));
        add(turnIndicator, BorderLayout.NORTH);


        info = new JLabel("⚔ ROUND 1 ⚔", JLabel.CENTER);
        info.setFont(new Font("Impact", Font.BOLD, 36));
        info.setForeground(new Color(0, 255, 255));


        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(10, 10, 25));

        JPanel skills = new JPanel(new FlowLayout());
        skills.setBackground(new Color(10, 10, 25));

        s1 = createButton("Skill 1");
        s2 = createButton("Skill 2");
        s3 = createButton("Ultimate");

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);

        p1Bar = new HPBar(p1.getMaxHp(), p1.getMaxHp(), new Color(0, 200, 100));
        p2Bar = new HPBar(p2.getMaxHp(), p2.getMaxHp(), new Color(220, 60, 60));

        JPanel hpPanel = new JPanel(new GridLayout(1, 2));
        hpPanel.setBackground(new Color(10, 10, 25));

        hpPanel.add(p1Bar);
        hpPanel.add(p2Bar);

        bottom.add(hpPanel, BorderLayout.NORTH);
        bottom.add(skills, BorderLayout.CENTER);
        bottom.add(info, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        s1.addActionListener(e -> playerMove(1));
        s2.addActionListener(e -> playerMove(2));
        s3.addActionListener(e -> playerMove(3));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }

        // Overlay
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, getWidth(), getHeight());

        int w = getWidth();
        int h = getHeight();

        int y = h / 2 - IMG_SIZE / 2;

        int p1X = w / 4;
        int p2X = (w * 3) / 4;

        if (p1Gif != null)
            g.drawImage(p1Gif.getImage(), p1X - (IMG_SIZE / 2), y, IMG_SIZE, IMG_SIZE, this);

        if (p2Gif != null)
            g.drawImage(p2Gif.getImage(), p2X - (IMG_SIZE / 2), y, IMG_SIZE, IMG_SIZE, this);

        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, w, h);

            g.setColor(Color.CYAN);
            g.setFont(new Font("Impact", Font.BOLD, 40));

            int textW = g.getFontMetrics().stringWidth(resultText);
            g.drawString(resultText, (w - textW) / 2, h / 2);
        }
    }


    private void playerMove(int skill) {

        if (!playerTurn || gameOver) return;

        int dmg = p1.useSkill(skill);
        if (dmg == 0) dmg = 30;

        info.setText("⚡ YOU DEALT " + dmg);

        p2.takeDamage(dmg);
        p2Bar.setHP(p2.getHP());

        repaint();

        if (!p2.isAlive()) {
            endGame(p1.getName() + " WINS!");
            return;
        }

        playerTurn = false;
        updateTurn(false);
        setButtons(false);

        new Timer(800, e -> {
            ((Timer) e.getSource()).stop();
            enemyTurn();
        }).start();
    }

    private void enemyTurn() {

        int dmg = p2.useSkill(rand.nextInt(3) + 1);
        if (dmg == 0) dmg = 25;

        info.setText("💀 ENEMY HIT -" + dmg);

        p1.takeDamage(dmg);
        p1Bar.setHP(p1.getHP());

        repaint();

        if (!p1.isAlive()) {
            endGame(p2.getName() + " WINS!");
            return;
        }

        playerTurn = true;
        updateTurn(true);
        setButtons(true);
    }

    private void endGame(String text) {
        gameOver = true;
        resultText = text;
        repaint();
    }

    private void updateTurn(boolean player) {
        turnIndicator.setText(player ? "⚔ PLAYER TURN ⚔" : "💀 ENEMY TURN 💀");
    }

    private void setButtons(boolean val) {
        s1.setEnabled(val);
        s2.setEnabled(val);
        s3.setEnabled(val);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Impact", Font.BOLD, 18));
        return btn;
    }


    static class HPBar extends JPanel {

        int current, max;
        Color color;

        HPBar(int c, int m, Color col) {
            current = c;
            max = m;
            color = col;
            setPreferredSize(new Dimension(200, 20));
        }

        void setHP(int hp) {
            current = hp;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int w = getWidth();
            int fill = (int)((double)current / max * w);

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, w, 20);

            g.setColor(color);
            g.fillRect(0, 0, fill, 20);

            g.setColor(Color.WHITE);
            g.drawString(current + "/" + max, 10, 15);
        }
    }
}