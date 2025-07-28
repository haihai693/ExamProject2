package character;

import creature.Character;
import creature.Creature;
import weapon.Weapon;

public class Wizard extends Character {
    int mp;

    public Wizard(String name, int hp, int mp, Weapon weapon) {
        super(name, hp, weapon);
        this.mp = mp;
    }

    public void magic(Creature target){
        if (this.mp < this.getWeapon().getCost()) {
            System.out.println("MPが足りない！");
            return;
        }
        this.mp -= this.getWeapon().getCost();
        System.out.println(this.getName() + "は" + this.getWeapon().getName() + this.getWeapon().attackMessage() + target.getName() + "に" + this.getWeapon().getDamage() + "のダメージを与えた！");
        target.setHp(target.getHp() - this.getWeapon().getDamage());
    }

    public void attack(Creature target){
        System.out.println(this.getName() + "は石を投げた！" + target.getName() + "に3のダメージを与えた！" );
        target.setHp(target.getHp() - 3);
    }

    public void showStatus() {
        System.out.println(this.getName() + "：HP " + this.getHp() + ", MP " + this.mp);
    }
}

