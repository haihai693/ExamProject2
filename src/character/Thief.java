package character;

import creature.Character;
import creature.Creature;
import weapon.Weapon;

public class Thief extends Character {
    private boolean guard;

    public Thief(String name, int hp, Weapon weapon) {
        super(name, hp, weapon);
        this.guard = false;
    }

    public boolean isGuard() {
        return guard;
    }

    public void setGuard(boolean guard) {
        this.guard = guard;
    }

    public void attack(Creature target) {
        System.out.println(this.getName() + "は素早く2回攻撃した！" + this.getWeapon().attackMessage() + target.getName() + "に" + (this.getWeapon().getDamage() * 2) + "のダメージを与えた！");
        target.setHp(target.getHp() - (this.getWeapon().getDamage() * 2));
    }

    public void guard() {
        this.setGuard(true);
        System.out.println(this.getName() + "は身を守っている！");
    }

    public void setHp(int hp) {
        if (this.isGuard()) {
            System.out.println("しかし、" + this.getName() + "は攻撃を回避し、ダメージが入らなかった！");
            this.setGuard(false);
        } else {
            super.setHp(hp);
        }
    }
}
