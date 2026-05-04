package ui;

import characters.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CharacterSelect extends JPanel {

    Image bg;
    GameCharacter player1;
    GameCharacter player2;

    JLabel previewImage;
    JLabel turnLabel;
    JLabel previewName;
    JTextArea previewStats;
    JFrame frame;
    int mode;

    float alpha = 0f;

    Color neon = new Color(0, 255, 255);

    public CharacterSelect(JFrame frame, int mode){

        this.frame = frame;
        this.mode = mode;

        bg = new ImageIcon(getClass().getResource("/resources/bg_mode.jpg")).getImage();

        setLayout(new BorderLayout());

        // FADE IN
        new Timer(30, e -> {
            alpha += 0.05f;
            if(alpha >= 1f){
                alpha = 1f;
                ((Timer)e.getSource()).stop();
            }
            repaint();
        }).start();

        // TITLE
        JLabel title = new JLabel("SELECT YOUR FIGHTER", JLabel.CENTER);
        title.setFont(new Font("Impact", Font.BOLD, 42));
        title.setForeground(neon);
        title.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        add(title, BorderLayout.NORTH);

        new Timer(40, new java.awt.event.ActionListener() {
            float glow = 0;
            boolean up = true;

            public void actionPerformed(java.awt.event.ActionEvent e) {

                if(up){
                    glow += 0.05f;
                    if(glow >= 1f) up = false;
                } else {
                    glow -= 0.05f;
                    if(glow <= 0.3f) up = true;
                }

                int r = (int)(0 * glow);
                int g = (int)(255 * glow);
                int b = (int)(255 * glow);

                title.setForeground(new Color(r, g, b));
            }
        }).start();

        // GRID
        JPanel grid = new JPanel(new GridLayout(2,4,20,20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        grid.add(createCharacterCard("Chrome", "chrome.png", new Chrome()));
        grid.add(createCharacterCard("Ryan07", "ryan07.png", new Ryan07()));
        grid.add(createCharacterCard("Neo", "neo.png", new Neo()));
        grid.add(createCharacterCard("Cardo", "cardo.png", new Cardo()));
        grid.add(createCharacterCard("T700", "t700.png", new T700()));
        grid.add(createCharacterCard("Zenith", "zenith.png", new Zenith()));
        grid.add(createCharacterCard("Vesper", "vesper.png", new Vesper()));
        grid.add(createCharacterCard("Havoc", "havoc.png", new Havoc()));

        add(grid, BorderLayout.CENTER);

        // PREVIEW PANEL
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setPreferredSize(new Dimension(300, 0));
        previewPanel.setBackground(new Color(10,10,30,220));
        previewPanel.setBorder(BorderFactory.createLineBorder(neon, 2));

        // IMAGE (BIG)
        previewImage = new JLabel();
        previewImage.setHorizontalAlignment(JLabel.CENTER);

        // NAME
        previewName = new JLabel("Hover a character", JLabel.CENTER);
        previewName.setForeground(neon);
        previewName.setFont(new Font("Arial", Font.BOLD, 18));

        // STATS
        previewStats = new JTextArea();
        previewStats.setEditable(false);
        previewStats.setOpaque(false);
        previewStats.setForeground(Color.WHITE);

        // BIGGER FONT
        previewStats.setFont(new Font("Consolas", Font.BOLD, 18));

        // CENTER TEXT (important trick)
        previewStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        previewStats.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

// Make text centered line-by-line
        previewStats.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        // LAYOUT
        JPanel centerBox = new JPanel();
        centerBox.setOpaque(false);
        centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));

        previewName.setAlignmentX(Component.CENTER_ALIGNMENT);
        previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        previewStats.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ADD SPACING
        centerBox.add(Box.createVerticalStrut(20));
        centerBox.add(previewName);
        centerBox.add(Box.createVerticalStrut(15));
        centerBox.add(previewImage);
        centerBox.add(Box.createVerticalStrut(15));
        centerBox.add(previewStats);

        previewPanel.add(centerBox, BorderLayout.CENTER);

        add(previewPanel, BorderLayout.EAST);

        // TURN LABEL
        turnLabel = new JLabel("Player 1: Choose your fighter", JLabel.CENTER);
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 14));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        add(turnLabel, BorderLayout.SOUTH);
    }

    private ImageIcon loadImage(String fileName, int w, int h){
        try {
            java.net.URL url = getClass().getResource("/resources/" + fileName);
            if(url == null) return new ImageIcon();

            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);

        } catch (Exception e){
            return new ImageIcon();
        }
    }

    private JPanel createCharacterCard(String name, String imgFile, GameCharacter character){

        JPanel card = new JPanel(new BorderLayout()){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(new Color(15,15,40,220));
                g.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createLineBorder(neon, 2));

        ImageIcon icon = loadImage(imgFile, 110, 110);

        JLabel pic = new JLabel(icon);
        pic.setHorizontalAlignment(JLabel.CENTER);

        JLabel label = new JLabel(name, JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 13));

        card.add(pic, BorderLayout.CENTER);
        card.add(label, BorderLayout.SOUTH);

        // HOVER EFFECT
        card.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e){
                card.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 3));

                previewName.setText(character.getName());

                previewStats.setText(
                        "HP: " + character.getHP() + "\n" +
                                "ATK: " + character.getATK() + "\n" +
                                "DEF: " + character.getDEF() + "\n" +
                                "SPD: " + character.getSPD()
                );

                // BIG IMAGE
                previewImage.setIcon(loadImage(imgFile, 220, 220));
            }

            public void mouseExited(MouseEvent e){
                card.setBorder(BorderFactory.createLineBorder(neon, 2));
            }

            public void mouseClicked(MouseEvent e){
                choose(character);
            }
        });

        return card;
    }



    private void choose(GameCharacter c){

        if(mode == 3){
            frame.setContentPane(new ArcadeScreen(frame, c));
            frame.revalidate();
            return;
        }

        if(player1 == null){
            player1 = c;
            turnLabel.setText("Player 2: Choose your fighter");
        } else {
            player2 = c;
            frame.setContentPane(new VersusScreen(frame, player1, player2));
            frame.revalidate();
        }
    }

    // BACKGROUND PAINT
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}