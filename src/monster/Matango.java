package monster;

import creature.Monster;
import creature.Creature;

public class Matango extends Monster {
    public Matango(char suffix, int hp) {
        super("お化けキノコ", suffix, hp);
    }
    public void attack(Creature target) {
        System.out.println("お化けキノコ" + this.getSuffix() + "は体当たり攻撃！" + target.getName() + "に6のダメージを与えた！");
        target.setHp(target.getHp() - 6);
    }
}

