package characters;

import java.util.Random;

public class Neo extends GameCharacter {

    Random rand = new Random();

    public Neo(){
        super("Neo", 2000, 500, 130, 70, 100);
    }

    // Force Punch (basic balanced hit)
    public int skill1(){
        if(cd1 == 0 && energy >= 40){
            useEnergy(40);
            cd1 = 2;

            return atk + rand.nextInt(51); // atk + 0–50
        }
        return 0;
    }

    // Impact Rush (stronger combo)
    public int skill2(){
        if(cd2 == 0 && energy >= 60){
            useEnergy(60);
            cd2 = 3;

            return atk + 60 + rand.nextInt(51); // atk + 60–110
        }
        return 0;
    }

    // Cyber Overdrive (burst skill)
    public int skill3(){
        if(cd3 == 0 && energy >= 100){
            useEnergy(100);
            cd3 = 4;

            return atk + 120 + rand.nextInt(81); // atk + 120–200
        }
        return 0;
    }
}