package ui;

import java.util.Random;
import characters.*;

public class EnemyFactory {

    static Random rand = new Random();

    public static GameCharacter getRandomEnemy(){

        int r = rand.nextInt(4);

        switch(r){
            case 0: return new T700();
            case 1: return new Zenith();
            case 2: return new Vesper();
            default: return new Havoc();
        }
    }
}