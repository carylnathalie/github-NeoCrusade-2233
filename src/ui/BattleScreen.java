package ui;

import characters.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleScreen extends JPanel {

    // SMOOTH HP (for animation)
    double displayHP1;
    double displayHP2;
    JFrame frame;

    GameCharacter p1, p2;

    JLabel turnIndicator;
    JLabel info;

    boolean playerTurn = true;

    Image bg;
    Image p1Img;
    Image p2Img;

    private static final int IMG_SIZE = 220;

    JButton s1, s2, s3;

    Random rand = new Random();

    // ===== GAME STATE =====
    boolean gameOver = false;
    String resultText = "";

    int round = 1;
    final int MAX_ROUNDS = 3;

    private String getFinalWinner(){
        if(p1.getHP() > p2.getHP()) return p1.getName();
        else return p2.getName();
    }

    public BattleScreen(JFrame frame, GameCharacter p1, GameCharacter p2, int mode){

        this.frame = frame;
        this.p1 = p1;
        this.p2 = p2;
        displayHP1 = p1.getHP();
        displayHP2 = p2.getHP();

        bg = loadImage("bg_battle.jpg");

        p1Img = loadImage(p1.getName().toLowerCase() + ".png");
        p2Img = loadImage(p2.getName().toLowerCase() + ".png");

        setLayout(new BorderLayout());

        // ===== TURN INDICATOR =====
        turnIndicator = new JLabel("⚔ PLAYER TURN ⚔", JLabel.CENTER);
        turnIndicator.setFont(new Font("Impact", Font.BOLD, 30));
        turnIndicator.setForeground(new Color(0, 255, 255));
        turnIndicator.setOpaque(true);
        turnIndicator.setBackground(new Color(15, 15, 35));
        add(turnIndicator, BorderLayout.NORTH);

        // ===== INFO =====
        info = new JLabel("⚔ ROUND 1 ⚔", JLabel.CENTER);
        info.setFont(new Font("Impact", Font.BOLD, 36));
        info.setForeground(new Color(0,255,255));

        // ===== BOTTOM =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(10, 10, 25));
        bottom.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(0, 220, 255)));

        JPanel skills = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        skills.setBackground(new Color(10, 10, 25));

        s1 = createButton("Skill 1");
        s2 = createButton("Skill 2");
        s3 = createButton("Ultimate");

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);

        bottom.add(skills, BorderLayout.CENTER);
        bottom.add(info, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        s1.addActionListener(e -> playerMove(1));
        s2.addActionListener(e -> playerMove(2));
        s3.addActionListener(e -> playerMove(3));
    }

    private JButton createButton(String text){
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

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if(bg != null){
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(0,0,0,100));
        g.fillRect(0,0,getWidth(),getHeight());

        int w = getWidth();
        int h = getHeight();

        int y = h/2 - IMG_SIZE/2;

        int p1X = w/4;
        int p2X = (w*3)/4;

        if(p1Img != null)
            g.drawImage(p1Img, p1X - 110, y, IMG_SIZE, IMG_SIZE, this);

        if(p2Img != null)
            g.drawImage(p2Img, p2X + 110, y, -IMG_SIZE, IMG_SIZE, this);

        drawHPBar(g, p1X - 110, y - 25, p1, true);
        drawHPBar(g, p2X - 110, y - 25, p2, false);
        if(gameOver){
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(new Color(0,0,0,200));
            g2.fillRect(0,0,getWidth(),getHeight());

            // BIG WIN TEXT
            g2.setFont(new Font("Impact", Font.BOLD, 60));
            g2.setColor(new Color(0,255,180));

            String mainText = resultText;

            int textW = g2.getFontMetrics().stringWidth(mainText);

            g2.drawString(mainText,
                    (getWidth()/2) - (textW/2),
                    getHeight()/2 - 20);

            // SUB TEXT (LONGER DISPLAY MESSAGE)
            g2.setFont(new Font("Impact", Font.PLAIN, 24));
            g2.setColor(Color.WHITE);

            String subText = "Preparing next round...";

            int subW = g2.getFontMetrics().stringWidth(subText);

            g2.drawString(subText,
                    (getWidth()/2) - (subW/2),
                    getHeight()/2 + 30);
        }
    }

    private void drawHPBar(Graphics g, int x, int y, GameCharacter c, boolean isP1){

        int width = 220;

        double currentHP = isP1 ? displayHP1 : displayHP2;
        double realHP = c.getHP();

        // 🔥 SMOOTH ANIMATION
        if(currentHP > realHP){
            currentHP -= 1.5; // speed of animation
        }

        if(isP1) displayHP1 = currentHP;
        else displayHP2 = currentHP;

        double ratio = currentHP / c.getMaxHp();
        if(ratio < 0) ratio = 0;

        int hpWidth = (int)(ratio * width);

        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, 12);

        if(ratio > 0.6) g.setColor(Color.GREEN);
        else if(ratio > 0.3) g.setColor(Color.ORANGE);
        else g.setColor(Color.RED);

        g.fillRect(x, y, hpWidth, 12);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.BOLD, 14));
        g.drawString(c.getName() + " " + c.getHP() + "/" + c.getMaxHp(), x, y - 5);
    }

    private void playerMove(int skill){

        if(!playerTurn || gameOver) return;

        int dmg = p1.useSkill(skill);
        if(dmg == 0) dmg = 30;

        info.setText("⚡ YOU DEALT " + dmg);

        p2.takeDamage(dmg);

        revalidate();
        repaint();

        if(!p2.isAlive()){
            endGame("🏆 " + p1.getName() + " WINS ROUND " + round + "!");
            return;
        }

        playerTurn = false;
        updateTurn(false);
        setButtons(false);

        new Timer(900, e -> {
            ((Timer)e.getSource()).stop();
            enemyTurn();
        }).start();
    }

    private void enemyTurn(){

        if(gameOver) return;

        int choice = rand.nextInt(3) + 1;
        int dmg = p2.useSkill(choice);

        if(dmg == 0) dmg = 25;

        info.setText("💀 ENEMY HIT -" + dmg);

        p1.takeDamage(dmg);
        repaint();

        if(!p1.isAlive()){
            endGame(p2.getName() + " WINS ROUND " + round + "!");
            return;
        }

        p1.reduceCooldowns();
        p2.reduceCooldowns();

        playerTurn = true;
        updateTurn(true);
        setButtons(true);
    }

    private void endGame(String text){

        gameOver = true;
        resultText = text;

        setButtons(false);
        repaint();

        new Timer(4000, e -> {
            ((Timer)e.getSource()).stop();

            if(round < MAX_ROUNDS){

                round++;

                // CLEAR OVERLAY FIRST
                gameOver = false;
                resultText = "";
                repaint();

                // RESET
                p1.setHp(p1.getMaxHp());
                p2.setHp(p2.getMaxHp());

                p1.resetCooldowns();
                p2.resetCooldowns();

                playerTurn = true;
                updateTurn(true);
                setButtons(true);

                // SHOW ROUND TEXT AFTER SHORT DELAY
                new Timer(800, ev -> {
                    info.setText("⚔ ROUND " + round + " ⚔");
                    info.setForeground(new Color(0,255,255));
                }).start();

                revalidate();
                repaint();

            } else {

                resultText = "🏆 " + getFinalWinner() + " WINS THE GAME! 🏆";
                repaint();

                new Timer(4000, ev -> {
                    frame.setContentPane(new HomeScreen(frame));
                    frame.revalidate();
                }).start();
            }

        }).start();


    }

    private void updateTurn(boolean player){
        if(player){
            turnIndicator.setText("⚔ PLAYER TURN ⚔");
            turnIndicator.setForeground(new Color(0,255,255));
        } else {
            turnIndicator.setText("💀 ENEMY TURN 💀");
            turnIndicator.setForeground(new Color(255,80,80));
        }
    }

    private void setButtons(boolean val){
        s1.setEnabled(val);
        s2.setEnabled(val);
        s3.setEnabled(val);
    }

    private Image loadImage(String name){
        try{
            return new ImageIcon(getClass().getResource("/resources/" + name)).getImage();
        }catch(Exception e){
            return null;
        }
    }
}