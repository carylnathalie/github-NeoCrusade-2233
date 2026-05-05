package ui;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {

    float glowPhase = 0f;
    int bgX = 0;
    Timer timer;
    Image bg;

    public HomeScreen(JFrame frame){

        bg = new ImageIcon("src/resources/bg.jpg").getImage();

        // ===== ANIMATION =====
        timer = new Timer(30, e -> {
            bgX -= 2;

            if (bgX <= -getWidth()) {
                bgX = 0;
            }

            glowPhase += 0.08f; // speed of glow animation

            repaint();
        });
        timer.start();

        setLayout(new BorderLayout());

        // ===== TITLE PANEL =====
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("N E O   C R U S A D E") {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                // Smooth glow animation (sin wave)
                float intensity = (float)(Math.sin(glowPhase) + 1) / 2; // 0 to 1

                int glowSize = 8 + (int)(intensity * 6);

                // ===== GLOW LAYERS =====
                for(int i = glowSize; i > 0; i--) {
                    g2.setColor(new Color(0, 255, 255, 20));
                    g2.drawString(getText(), i, getHeight() - 10);
                }

                g2.setColor(new Color(0, 255, 255));
                g2.setFont(getFont());
                g2.drawString(getText(), 0, getHeight() - 12);
            }
        };

        title.setFont(new Font("Impact", Font.BOLD, 90));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setText("N E O   C R U S A D E");
        title.setForeground(new Color(0,255,255));

        JLabel subtitle = new JLabel("CYBER WARFARE PROTOCOL", JLabel.CENTER);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Consolas", Font.BOLD, 26));
        subtitle.setForeground(new Color(255,0,180));

        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titlePanel);
        centerPanel.add(Box.createVerticalStrut(20)); // space between title & menu
        centerPanel.add(menuPanel);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(new Color(0,0,0,150));
        box.setBorder(BorderFactory.createLineBorder(new Color(0,255,255), 2));
        box.setMaximumSize(new Dimension(500, 380));

        JButton btn1 = createNeonButton("1 PLAYER vs COMPUTER", new Color(0,200,255));
        JButton btn2 = createNeonButton("2 PLAYERS", new Color(0,255,150));
        JButton btnArcade = createNeonButton("ARCADE MODE", new Color(255,0,200));
        JButton btnQuit = createNeonButton("QUIT", new Color(255,140,0));

        box.add(Box.createVerticalStrut(15));
        box.add(btn1);
        box.add(Box.createVerticalStrut(10));
        box.add(btn2);
        box.add(Box.createVerticalStrut(10));
        box.add(btnArcade); // ✅ Arcade now above Quit
        box.add(Box.createVerticalStrut(10));
        box.add(btnQuit);
        box.add(Box.createVerticalStrut(15));

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(box);
        menuPanel.add(Box.createVerticalGlue());

        title.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);


        btn1.addActionListener(e -> {
            frame.setContentPane(new CharacterSelect(frame, 1));
            frame.revalidate();
        });

        btn2.addActionListener(e -> {
            frame.setContentPane(new CharacterSelect(frame, 2));
            frame.revalidate();
        });

        btnArcade.addActionListener(e -> {
            frame.setContentPane(new CharacterSelect(frame, 3));
            frame.revalidate();
        });

        btnQuit.addActionListener(e -> System.exit(0));
    }

    private JButton createNeonButton(String text, Color color){

        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 20));
        button.setForeground(color);

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color, 2, true));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 45));
        button.setMaximumSize(new Dimension(400, 55));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(color);
            }
        });

        return button;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(bg, bgX, 0, getWidth(), getHeight(), this);
        g.drawImage(bg, bgX + getWidth(), 0, getWidth(), getHeight(), this);

        // dark overlay
        g.setColor(new Color(0,0,0,120));
        g.fillRect(0,0,getWidth(),getHeight());
    }
}