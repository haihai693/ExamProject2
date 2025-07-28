package character;
import creature.Character;
import creature.Creature;
import weapon.Weapon;

public class SuperHero extends Hero {
    public SuperHero(Hero hero) {
        super(hero.getName(), hero.getHp(), hero.getWeapon());
        this.setHp(this.getHp() - 30);
        System.out.println(this.getName() + "はスーパーヒーローに変身した代償として30のダメージを受けた！");
    }

    public void attack(Creature target) { System.out.println(this.getName() + "は" + this.getWeapon().getName() + this.getWeapon().attackMessage() + target.getName() + "に" + (int) (this.getWeapon().getDamage() * 2.5) + "のダメージを与えた！");
        target.setHp(target.getHp() - (int) (this.getWeapon().getDamage() * 2.5));
    }
}

