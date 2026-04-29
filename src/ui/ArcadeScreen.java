package ui;

import characters.*;
import javax.swing.*;
import java.awt.*;

public class ArcadeScreen extends JPanel {

    JFrame frame;

    GameCharacter player;
    GameCharacter enemy;

    JProgressBar playerHP;
    JProgressBar enemyHP;

    JLabel info;

    int round = 1;

    public ArcadeScreen(JFrame frame, GameCharacter player){

        this.frame = frame;
        this.player = player;

        spawnEnemy();

        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2,1));

        playerHP = new JProgressBar(0, player.getMaxHp());
        enemyHP = new JProgressBar(0, enemy.getMaxHp());

        playerHP.setStringPainted(true);
        enemyHP.setStringPainted(true);

        top.add(playerHP);
        top.add(enemyHP);

        info = new JLabel("ROUND 1", JLabel.CENTER);

        JPanel skills = new JPanel();

        JButton s1 = new JButton("Skill 1");
        JButton s2 = new JButton("Skill 2");
        JButton s3 = new JButton("Skill 3");

        skills.add(s1);
        skills.add(s2);
        skills.add(s3);

        s1.addActionListener(e -> playerTurn(1));
        s2.addActionListener(e -> playerTurn(2));
        s3.addActionListener(e -> playerTurn(3));

        add(top, BorderLayout.NORTH);
        add(info, BorderLayout.CENTER);
        add(skills, BorderLayout.SOUTH);

        updateBars();
    }

    void spawnEnemy(){
        enemy = EnemyFactory.getRandomEnemy();

        enemy.setHp(enemy.getHp() + (round * 100));
        enemy.setMaxHp(enemy.getMaxHp() + (round * 100));
    }

    void playerTurn(int skill){

        int dmg = -1;

        if(skill == 1) dmg = player.skill1();
        if(skill == 2) dmg = player.skill2();
        if(skill == 3) dmg = player.skill3();

        if(dmg == -1){
            info.setText("Not enough energy or cooldown!");
            return;
        }

        enemy.takeDamage(dmg);

        if(!enemy.isAlive()){
            round++;
            JOptionPane.showMessageDialog(this, "Enemy defeated! Next round!");
            spawnEnemy();
        } else {
            enemyTurn();
        }

        player.reduceCooldowns();
        updateBars();
    }

    void enemyTurn(){

        int choice = (int)(Math.random()*3)+1;

        int dmg = -1;

        if(choice == 1) dmg = enemy.skill1();
        if(choice == 2) dmg = enemy.skill2();
        if(choice == 3) dmg = enemy.skill3();

        if(dmg == -1) return;

        player.takeDamage(dmg);

        info.setText(enemy.getName() + " used Skill " + choice + " (-" + dmg + ")");

        enemy.reduceCooldowns();

        if(!player.isAlive()){
            JOptionPane.showMessageDialog(this,
                    "GAME OVER!\nYou reached Round " + round);

            frame.setContentPane(new HomeScreen(frame));
            frame.revalidate();
        }
    }

    void updateBars(){

        playerHP.setValue(player.getHP());
        playerHP.setString(player.getName() + " HP: " + player.getHP());

        enemyHP.setMaximum(enemy.getMaxHp());
        enemyHP.setValue(enemy.getHP());
        enemyHP.setString(enemy.getName() + " HP: " + enemy.getHP());
    }
}