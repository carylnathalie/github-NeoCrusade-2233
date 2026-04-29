package characters;

import java.util.Random;

public class Zenith extends GameCharacter {

    Random rand = new Random();

    public Zenith(){

        super("Zenith", 2350, 400, 130, 110, 50);
    }

    // Skill 1: Photon Burst
    public int skill1(){
        if(cd1 == 0 && energy >= 25){
            useEnergy(25);
            cd1 = 1;
            // logic: atk + (0-20) + 50 base
            return atk + 50 + rand.nextInt(21);
        }
        return 0; // Returning 0 for consistency
    }

    // Skill 2: Starfall
    public int skill2(){
        if(cd2 == 0 && energy >= 40){
            useEnergy(40);
            cd2 = 2;
            return atk + 70 + rand.nextInt(21);
        }
        return 0;
    }

    // Skill 3: Supernova
    public int skill3(){
        if(cd3 == 0 && energy >= 60){
            useEnergy(60);
            cd3 = 3;
            // logic: atk + (0-30) + 100 base
            return atk + 100 + rand.nextInt(31);
        }
        return 0;
    }
}