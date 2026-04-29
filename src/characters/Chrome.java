package characters;

public class Chrome extends GameCharacter {

    public Chrome(){
        super("Chrome", 2100, 350, 120, 60, 90);
    }

    // Neural Strike
    public int skill1(){
        if(cd1 == 0 && energy >= 40){
            useEnergy(40);
            cd1 = 2;
            return atk; // uses ATK now
        }
        return 0;
    }

    // Tactical Burst
    public int skill2(){
        if(cd2 == 0 && energy >= 60){
            useEnergy(60);
            cd2 = 2;
            return atk + 40;
        }
        return 0;
    }

    // Phantom Override
    public int skill3(){
        if(cd3 == 0 && energy >= 70){
            useEnergy(70);
            cd3 = 3;
            return atk + 80;
        }
        return 0;
    }
}