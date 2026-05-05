package characters;

public abstract class GameCharacter {

    protected String name;
    protected int hp;
    protected int maxHp;
    protected int energy;

    protected int atk;
    protected int def;
    protected int spd;

    // Cooldowns
    protected int cd1 = 0;
    protected int cd2 = 0;
    protected int cd3 = 0;

    public GameCharacter(String name, int hp, int energy, int atk, int def, int spd){
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.energy = energy;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
    }

    // ===== SKILL CALL =====
    public int useSkill(int choice){
        switch(choice){
            case 1:
                if(cd1 == 0) return skill1();
                break;
            case 2:
                if(cd2 == 0) return skill2();
                break;
            case 3:
                if(cd3 == 0) return skill3();
                break;
        }
        return 0; // skill failed (cooldown or invalid)
    }

    //GETTERS
    public String getName(){
        return name;
    }

    public int getHP(){
        return hp;
    }

    public int getEnergy(){
        return energy;
    }

    public int getATK(){
        return atk;
    }

    public int getDEF(){
        return def;
    }

    public int getSPD(){
        return spd;
    }
    public int getMaxHp(){
        return maxHp;
    }

    // SETTERS
    public void setHp(int hp){
        this.hp = hp;
        if(this.hp > maxHp) this.hp = maxHp;
        if(this.hp < 0) this.hp = 0;
    }

    public void setMaxHp(int maxHp){
        this.maxHp = maxHp;
    }

    public void setEnergy(int energy){
        this.energy = energy;
        if(this.energy < 0) this.energy = 0;
    }

    public void takeDamage(int dmg){
        int reduced = dmg - def;
        if(reduced < 1) reduced = 1;

        hp -= reduced;

        if(hp < 0) hp = 0;

        System.out.println(name + " HP: " + hp);
    }

    public void useEnergy(int cost){
        energy -= cost;
        if(energy < 0) energy = 0;
    }

    public void reduceCooldowns(){
        if(cd1 > 0) cd1--;
        if(cd2 > 0) cd2--;
        if(cd3 > 0) cd3--;
    }

    public void resetCooldowns(){
        cd1 = 0;
        cd2 = 0;
        cd3 = 0;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    //IMPLEMENT IN CHILD CLASSES
    public abstract int skill1();
    public abstract int skill2();
    public abstract int skill3();
}