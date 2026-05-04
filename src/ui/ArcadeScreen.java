package ui;

import characters.*;
import javax.swing.*;
import java.awt.*;

public class ArcadeScreen extends JPanel {

    JFrame frame;

    GameCharacter player;
    GameCharacter enemy;

    JLabel turnIndicator;
    JLabel info;

    int round = 1;
    boolean isPlayerTurn = true;

    Image bg;
    Image playerImg;
    Image enemyImg;

    private static final int IMG_SIZE = 220;

    JButton s1, s2, s3;

    public ArcadeScreen(JFrame frame, GameCharacter player) {

        this.frame = frame;
        this.player = player;

        // 🔻 LOWER PLAYER HP (FASTER GAME)
        player.setMaxHp(player.getMaxHp() + 100);
        player.setHp(player.getMaxHp());
        bg = loadImage("bg_arcade.jpg"); // change name to your image file

        spawnEnemy();
        setLayout(new BorderLayout());

        // ===== TURN INDICATOR =====
        turnIndicator = new JLabel("⚔ YOUR TURN ⚔", JLabel.CENTER);
        turnIndicator.setFont(new Font("Impact", Font.BOLD, 32));
        turnIndicator.setForeground(new Color(0, 255, 255));
        turnIndicator.setOpaque(true);
        turnIndicator.setBackground(new Color(15, 15, 35));
        turnIndicator.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(turnIndicator, BorderLayout.NORTH);

        // ===== INFO =====
        info = new JLabel("ROUND 1", JLabel.CENTER);
        info.setFont(new Font("Impact", Font.BOLD, 18));
        info.setForeground(Color.WHITE);

        // ===== SKILLS =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(10, 10, 25));
        bottom.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(0, 220, 255)));

        JPanel skills = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        skills.setBackground(new Color(10, 10, 25));

        s1 = createSkillButton("Skill 1");
        s2 = createSkillButton("Skill 2");
        s3 = createSkillButton("Skill 3");

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);

        bottom.add(skills, BorderLayout.CENTER);
        bottom.add(info, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        s1.addActionListener(e -> playerTurn(1));
        s2.addActionListener(e -> playerTurn(2));
        s3.addActionListener(e -> playerTurn(3));

        setBackground(new Color(5, 5, 20));


    }

    // ===== BUTTON STYLE =====
    private JButton createSkillButton(String text) {

        JButton btn = new JButton(text);

        btn.setFont(new Font("Impact", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(25, 25, 45));

        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 220, 255), 2),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));

        // HOVER EFFECT
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 180, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(25, 25, 45));
            }
        });

        return btn;
    }

    // ===== DRAW =====
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, getWidth(), getHeight());

        int w = getWidth();
        int h = getHeight();

        int spriteY = h / 2 - IMG_SIZE / 2;
        int playerX = w / 4;
        int enemyX = (w * 3) / 4;

        if (playerImg != null)
            g.drawImage(playerImg, playerX - 110, spriteY, IMG_SIZE, IMG_SIZE, this);

        if (enemyImg != null)
            g.drawImage(enemyImg, enemyX + 110, spriteY, -IMG_SIZE, IMG_SIZE, this);

        drawHPBar(g, playerX - 110, spriteY - 25, player);
        drawHPBar(g, enemyX - 110, spriteY - 25, enemy);
    }

    private void drawHPBar(Graphics g, int x, int y, GameCharacter c) {
        int w = 220;
        int hp = (int) ((double)c.getHP() / c.getMaxHp() * w);

        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, 12);

        g.setColor(Color.GREEN);
        g.fillRect(x, y, hp, 12);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.PLAIN, 14));
        g.drawString(c.getName() + " " + c.getHP() + "/" + c.getMaxHp(), x, y - 5);
    }

    // ===== SPAWN ENEMY =====
    void spawnEnemy() {
        enemy = EnemyFactory.getRandomEnemy();

        // 🔥 MUCH LOWER HP FOR FAST GAME
        int baseHp = enemy.getMaxHp();

        int newHp = (int)(baseHp * 0.5); // cut HP in half

        // small scaling per round
        newHp += round * 10;

        // safety minimum
        if (newHp < 150) newHp = 150;

        enemy.setMaxHp(newHp);
        enemy.setHp(newHp);

        playerImg = loadImage(player.getName().toLowerCase() + ".png");
        enemyImg = loadImage(enemy.getName().toLowerCase() + ".png");
    }

    // ===== PLAYER TURN =====
    void playerTurn(int skill) {

        if (!isPlayerTurn) return;

        int dmg = 0;

        if (skill == 1) dmg = player.skill1();
        if (skill == 2) dmg = player.skill2();
        if (skill == 3) dmg = player.skill3();

        // 🔥 ALWAYS ATTACK
        if (dmg == 0) {
            dmg = 30;
            info.setText("⚠ SKILL FAILED → BASIC STRIKE (" + dmg + ")");
        } else {
            info.setText("You dealt " + dmg + " damage");
        }

        enemy.takeDamage(dmg);
        player.reduceCooldowns();

        repaint();

        if (!enemy.isAlive()) {
            round++;
            JOptionPane.showMessageDialog(
                    this,
                    "⚔ ROUND CLEARED ⚔\n\nA NEW ENEMY APPROACHES...",
                    "SYSTEM",
                    JOptionPane.INFORMATION_MESSAGE
            );
            spawnEnemy();
            repaint();
            return;
        }

        isPlayerTurn = false;
        updateTurnIndicator(false);
        setButtonsEnabled(false);

        Timer t = new Timer(800, e -> enemyTurn());
        t.setRepeats(false);
        t.start();


    }

    // ===== ENEMY TURN =====
    void enemyTurn() {

        int dmg = 0;
        int attempts = 0;

        while (dmg == 0 && attempts < 3) {
            int choice = (int)(Math.random() * 3) + 1;

            if (choice == 1) dmg = enemy.skill1();
            if (choice == 2) dmg = enemy.skill2();
            if (choice == 3) dmg = enemy.skill3();

            attempts++;
        }

        if (dmg == 0) {
            dmg = 25;
            info.setText("💀 ENEMY STRIKES! -" + dmg + " HP");
        } else {
            info.setText("⚡ YOU STRIKE! -" + dmg + " HP");
        }

        player.takeDamage(dmg);
        enemy.reduceCooldowns();

        repaint();

        if (!player.isAlive()) {
            JOptionPane.showMessageDialog(
                    this,
                    "☠ SYSTEM FAILURE ☠\n\nYOU WERE DEFEATED...",
                    "GAME OVER",
                    JOptionPane.ERROR_MESSAGE
            );
            frame.setContentPane(new HomeScreen(frame));
            frame.revalidate();
            return;
        }

        isPlayerTurn = true;
        updateTurnIndicator(true);
        setButtonsEnabled(true);
    }

    // ===== BUTTON CONTROL =====
    private void setButtonsEnabled(boolean val) {
        s1.setEnabled(val);
        s2.setEnabled(val);
        s3.setEnabled(val);
    }

    // ===== TURN TEXT =====
    private void updateTurnIndicator(boolean playerTurn) {
        if (playerTurn) {
            turnIndicator.setText("⚔ YOUR TURN ⚔");
            turnIndicator.setForeground(new Color(0, 255, 255));
        } else {
            turnIndicator.setText("💀 ENEMY TURN 💀");
            turnIndicator.setForeground(new Color(255, 80, 80));
        }
    }

    // ===== LOAD IMAGE =====
    private Image loadImage(String file) {
        try {
            return new ImageIcon(getClass().getResource("/resources/" + file)).getImage();
        } catch (Exception e) {
            return null;
        }
    }
}