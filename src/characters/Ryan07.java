package characters;

import java.util.Random;

public class Ryan07 extends GameCharacter {

    Random rand = new Random();

    public Ryan07(){
        super("Ryan07", 2300, 450, 140, 80, 70);
    }

    // Plasma Edge (basic strike)
    public int skill1(){
        if(cd1 == 0 && energy >= 40){
            useEnergy(40);
            cd1 = 3;

            return atk + rand.nextInt(31); // atk + 0–30
        }
        return 0;
    }

    // Blade Dash (stronger attack)
    public int skill2(){
        if(cd2 == 0 && energy >= 55){
            useEnergy(55);
            cd2 = 3;

            return atk + 50 + rand.nextInt(41); // atk + 50–90
        }
        return 0;
    }

    // Honorable End (big cooldown finisher)
    public int skill3(){
        if(cd3 == 0 && energy >= 100){
            useEnergy(100);
            cd3 = 6;

            return atk + 120 + rand.nextInt(61); // atk + 120–180
        }
        return 0;
    }
}