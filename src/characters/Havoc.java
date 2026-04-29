package characters;

import java.util.Random;

public class Havoc extends GameCharacter {

    Random rand = new Random();

    public Havoc(){
        super("Havoc", 2800, 500, 100, 120, 40);
    }

    // Heavy Slam
    public int skill1(){
        if(cd1 == 0 && energy >= 35){
            useEnergy(35);
            cd1 = 1;

            return atk + rand.nextInt(31); // atk + 0–30
        }
        return 0;
    }

    // Iron Crush
    public int skill2(){
        if(cd2 == 0 && energy >= 60){
            useEnergy(60);
            cd2 = 2;

            return atk + 40 + rand.nextInt(31); // atk + 40–70
        }
        return 0;
    }

    // Doomsday Impact (big slow hit)
    public int skill3(){
        if(cd3 == 0 && energy >= 90){
            useEnergy(90);
            cd3 = 4;

            return atk + 100 + rand.nextInt(41); // atk + 100–140
        }
        return 0;
    }
}