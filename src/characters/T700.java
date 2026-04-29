package characters;

import java.util.Random; // Using Random is cleaner than Math.random()

public class T700 extends GameCharacter {

    Random rand = new Random();

    public T700(){
        // Updated to match the 6-parameter constructor in Ryan07
        // Name, HP, Energy, Attack, Defense, Speed
        super("T700", 2300, 320, 120, 90, 60);
    }

    public int skill1(){
        if(cd1 == 0 && energy >= 20){
            useEnergy(20);
            cd1 = 1;
            // logic: (0-20) + 40
            return 40 + rand.nextInt(21);
        }
        return 0; // Changed to 0 for consistency with Ryan07
    }

    public int skill2(){
        if(cd2 == 0 && energy >= 30){
            useEnergy(30);
            cd2 = 2;
            return 60 + rand.nextInt(21);
        }
        return 0;
    }

    public int skill3(){
        if(cd3 == 0 && energy >= 40){
            useEnergy(40);
            cd3 = 3;
            return 90 + rand.nextInt(21);
        }
        return 0;
    }
}