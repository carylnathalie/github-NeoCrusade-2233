package characters;

import java.util.Random;

public class Cardo extends GameCharacter {

    Random rand = new Random();

    public Cardo(){
        super("Cardo", 1100, 300, 160, 40, 120);
    }

    // Deadeye Focus
    public int skill1(){
        if(cd1 == 0 && energy >= 40){
            useEnergy(40);
            cd1 = 2;

            // scale with ATK
            return atk + rand.nextInt(61); // atk + 0–60
        }
        return 0;
    }

    // Shadow Strike (fast + stronger)
    public int skill2(){
        if(cd2 == 0 && energy >= 60){
            useEnergy(60);
            cd2 = 3;

            return atk + 80 + rand.nextInt(41); // atk + 80–120
        }
        return 0;
    }

    // Guardian Last Stand (burst damage)
    public int skill3(){
        if(cd3 == 0 && energy >= 90){
            useEnergy(90);
            cd3 = 4;

            return atk + 150 + rand.nextInt(71); // atk + 150–220
        }
        return 0;
    }
}