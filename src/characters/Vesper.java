package characters;

import java.util.Random;

public class Vesper extends GameCharacter {

    Random rand = new Random();

    public Vesper(){
        super("Vesper", 1500, 350, 160, 60, 95);
    }

    // Skill 1: Quick Strike
    public int skill1(){
        if(cd1 == 0 && energy >= 30){
            useEnergy(30);
            cd1 = 1;
            // logic: (0-30) + 70 + your base attack
            return atk + 70 + rand.nextInt(31);
        }
        return 0; // Return 0 instead of -1 for consistency with Ryan07
    }

    // Skill 2: Shadow Veil
    public int skill2(){
        if(cd2 == 0 && energy >= 45){
            useEnergy(45);
            cd2 = 2;
            return atk + 90 + rand.nextInt(31);
        }
        return 0;
    }

    // Skill 3: Nightfall Finisher
    public int skill3(){
        if(cd3 == 0 && energy >= 70){
            useEnergy(70);
            cd3 = 3;
            return atk + 130 + rand.nextInt(31);
        }
        return 0;
    }
}