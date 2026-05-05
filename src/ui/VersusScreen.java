package ui;

import characters.*;
import javax.swing.*;
import java.awt.*;

public class VersusScreen extends JPanel {

    float alpha = 0f;
    float scale = 0.8f;
    float flash = 1f;

    int p1Offset = -400;
    int p2Offset = 400;

    GameCharacter p1, p2;
    JFrame frame;

    Image bg;
    Image p1Img;
    Image p2Img;

    public VersusScreen(JFrame frame, GameCharacter p1, GameCharacter p2){

        this.frame = frame;
        this.p1 = p1;
        this.p2 = p2;

        setBackground(Color.BLACK);

        bg = new ImageIcon(getClass().getResource("/resources/bg_vs.jpg")).getImage();
        p1Img = loadImage(p1.getName().toLowerCase() + ".png");
        p2Img = loadImage(p2.getName().toLowerCase() + ".png");

        // 🎬 ANIMATION
        new Timer(30, e -> {

            // Fade
            if(alpha < 1f) alpha += 0.02f;
            if(alpha > 1f) alpha = 1f;

            // Slide
            if(p1Offset < 0) p1Offset += 10;
            if(p1Offset > 0) p1Offset = 0;

            if(p2Offset > 0) p2Offset -= 10;
            if(p2Offset < 0) p2Offset = 0;

            // Zoom
            if(scale < 1f) scale += 0.01f;

            // Flash effect
            if(flash > 0f) flash -= 0.05f;

            // Finish animation
            if(alpha == 1f && p1Offset == 0 && p2Offset == 0 && scale >= 1f){
                ((Timer)e.getSource()).stop();

                new Timer(2000, ev -> {
                    frame.setContentPane(new BattleScreen(frame, p1, p2, 2));
                    frame.revalidate();
                }).start();
            }

            repaint();
        }).start();
    }

    private Image loadImage(String file){
        try{
            java.net.URL url = getClass().getResource("/resources/" + file);

            if(url == null){
                System.out.println("❌ Missing image: " + file);
                return null;
            }

            return new ImageIcon(url).getImage();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if(bg != null){
            g2.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }

        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,getWidth(),getHeight());

        if(flash > 0){
            g2.setColor(new Color(255,255,255,(int)(flash * 255)));
            g2.fillRect(0,0,getWidth(),getHeight());
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        int centerY = getHeight()/2;

        int imgW = (int)(300 * scale);
        int imgH = (int)(300 * scale);

        if(p1Img != null){
            g2.drawImage(p1Img,
                    getWidth()/4 - imgW/2 + p1Offset,
                    centerY - imgH/2,
                    imgW, imgH, this);
        }

        // ===== PLAYER 2 IMAGE (FLIPPED) =====
        if(p2Img != null){
            g2.drawImage(p2Img,
                    (getWidth()*3)/4 + imgW/2 + p2Offset,
                    centerY - imgH/2,
                    -imgW, imgH, this);
        }

        // ===== TEXT =====
        Font big = new Font("Impact", Font.BOLD, 60);
        Font mid = new Font("Impact", Font.BOLD, 50);

        g2.setFont(big);
        FontMetrics fmBig = g2.getFontMetrics();

        String p1Text = p1.getName();
        String p2Text = p2.getName();

        int p1X = getWidth()/4 - fmBig.stringWidth(p1Text)/2 + p1Offset;
        int p2X = (getWidth()*3)/4 - fmBig.stringWidth(p2Text)/2 + p2Offset;

        int textY = centerY + imgH/2 + 40;

        g2.setColor(Color.CYAN);
        g2.drawString(p1Text, p1X, textY);

        g2.setColor(Color.PINK);
        g2.drawString(p2Text, p2X, textY);

        // ===== VS =====
        g2.setFont(mid);
        FontMetrics fmMid = g2.getFontMetrics();

        String vs = "VS";
        int vsX = getWidth()/2 - fmMid.stringWidth(vs)/2;
        int vsY = centerY;

        // Glow
        for(int i = 10; i > 0; i--){
            g2.setColor(new Color(255, 0, 0, 20));
            g2.drawString(vs, vsX + i, vsY);
        }

        g2.setColor(Color.RED);
        g2.drawString(vs, vsX, vsY);
    }
}