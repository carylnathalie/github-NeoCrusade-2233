import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(){

        setTitle("NEO-CRUSADE");
        setSize(1000,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        setContentPane(new ui.HomeScreen(this));

        setVisible(true);
    }

}