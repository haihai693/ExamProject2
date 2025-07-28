import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import creature.*;
import character.*;
import monster.*;
import weapon.*;



public class GameMaster {
    public static void main(String[] args) {
        Random random = new Random();
        ArrayList<creature.Character> party = new ArrayList<>();
        Hero hero = new Hero( "勇者", 100, new Sword());
        Wizard wizard = new Wizard("魔法使い", 60, 20, new Wand());
        Thief thief = new Thief("盗賊", 70, new Dagger());
        party.add(hero);
        party.add(wizard);
        party.add(thief);
        ArrayList<Monster> monsters = new ArrayList<>();
        int matangoCount = 0;
        int goblinCount = 0;
        int slimeCount = 0;
        System.out.println("--- 敵グループ生成 ---");
        for (int i = 0; i < 5; i++) {
            int monsterType = random.nextInt(3);
            char suffix;
            Monster newMonster = null;
            switch (monsterType) {
                case 0:
                    suffix = (char) ('A' + matangoCount++);
                    newMonster = new Matango(suffix, 45);
                    break;
                case 1:
                    suffix = (char) ('A' + goblinCount++);
                    newMonster = new Goblin(suffix, 50);
                    break;
                case 2:
                    suffix = (char) ('A' + slimeCount++);
                    newMonster = new Slime(suffix, 40);
                    break;
            }
            if (newMonster != null) {
                monsters.add(newMonster);
                System.out.println(newMonster.getName() + newMonster.getSuffix() + "が現れた！");
            }
        }
        System.out.println("\n--- 戦闘開始！ ---");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (!party.isEmpty() && !monsters.isEmpty()) {
                displayStatus(party, monsters);
                System.out.println("\n--- 味方のターン ---");
                Iterator<creature.Character> partyIterator = party.iterator();
                while (partyIterator.hasNext()) {
                    creature.Character currentCharacter = partyIterator.next();
                    if (!currentCharacter.isAlive()) {
                        continue;
                    }
                    if (monsters.isEmpty()) {
                        break;
                    }
                    System.out.println("\n" + currentCharacter.getName() + "の行動を選択してください:");

                    System.out.println("攻撃対象を選択してください:");
                    for (int i = 0; i < monsters.size(); i++) {
                        Monster m = monsters.get(i);
                        System.out.println((i + 1) + ": " + m.getName() + m.getSuffix() + " (HP:" + m.getHp() + ")");
                    }
                    Monster targetMonster = null;
                    int targetIndex = -1;
                    while (targetMonster == null) {
                        System.out.print("番号を入力してください: ");
                        try {
                            targetIndex = Integer.parseInt(br.readLine()) - 1;
                            if (targetIndex >= 0 && targetIndex < monsters.size()) {
                                targetMonster = monsters.get(targetIndex);
                            } else {
                                System.out.println("無効な番号です。再度入力してください。");
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            System.out.println("無効な入力です。数字を入力してください。");
                        } catch (IOException e) {
                            System.out.println("入力エラーが発生しました: " + e.getMessage());
                        }
                    }
                    int actionChoice = -1;
                    boolean validAction = false;
                    while (!validAction) {
                        if (currentCharacter instanceof Hero) {
                            if (currentCharacter instanceof SuperHero) {
                                System.out.println("1: 攻撃");
                            } else {
                                System.out.println("1: 攻撃");
                                System.out.println("2: スーパーヒーローになる");
                            }
                        } else if (currentCharacter instanceof Wizard) {
                            System.out.println("1: 攻撃");
                            System.out.println("2: 魔法攻撃");
                        } else if (currentCharacter instanceof Thief) {
                            System.out.println("1: 攻撃");
                            System.out.println("2: 守る");
                        } else {
                            System.out.println("1: 攻撃");
                        }
                        System.out.print("行動の番号を入力してください: ");
                        try {
                            actionChoice = Integer.parseInt(br.readLine());
                            if (currentCharacter instanceof Hero) {
                                if (actionChoice == 1 || (actionChoice == 2 && !(currentCharacter instanceof SuperHero))) {
                                    validAction = true;
                                } else {
                                    System.out.println("無効な選択です。");
                                }
                            } else if (currentCharacter instanceof Wizard) {
                                if (actionChoice == 1 || actionChoice == 2) {
                                    validAction = true;
                                } else {
                                    System.out.println("無効な選択です。");
                                }
                            } else if (currentCharacter instanceof Thief) {
                                if (actionChoice == 1 || actionChoice == 2) {
                                    validAction = true;
                                } else {
                                    System.out.println("無効な選択です。");
                                }
                            } else if (actionChoice == 1) {
                                validAction = true;
                            } else {
                                System.out.println("無効な選択です。");
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            System.out.println("無効な入力です。数字を入力してください。");
                        } catch (IOException e) {
                            System.out.println("入力エラーが発生しました: " + e.getMessage());
                        }
                    }
                    if (currentCharacter instanceof Hero) {
                        if (actionChoice == 1) {
                            currentCharacter.attack(targetMonster);
                        } else if (actionChoice == 2) {
                            if (!(currentCharacter instanceof SuperHero)) {
                                SuperHero superHero = new SuperHero((Hero) currentCharacter);
                                if (superHero.getHp() <= 0) {
                                    superHero.die();
                                    partyIterator.remove();
                                } else {
                                    int heroIndex = party.indexOf(currentCharacter);
                                    if (heroIndex != -1) {
                                        party.set(heroIndex, superHero);
                                    }
                                    System.out.println(superHero.getName() + "はスーパーヒーローに進化した！");
                                }
                            }
                        }
                    } else if (currentCharacter instanceof Wizard) {
                        Wizard wizardChar = (Wizard) currentCharacter;
                        if (actionChoice == 1) {
                            wizardChar.attack(targetMonster);
                        } else if (actionChoice == 2) {
                            wizardChar.magic(targetMonster);
                        }
                    } else if (currentCharacter instanceof Thief) {
                        Thief thiefChar = (Thief) currentCharacter;
                        if (actionChoice == 1) {
                            thiefChar.attack(targetMonster);
                        } else if (actionChoice == 2) {
                            thiefChar.guard();
                        }
                    } else {
                        currentCharacter.attack(targetMonster);
                    }
                    checkMonsterStatus(monsters);

                    if (monsters.isEmpty()) {
                        break;
                    }
                }
                if (monsters.isEmpty()) {
                    break;
                }
                System.out.println("\n--- 敵のターン ---");
                Iterator<Monster> monsterIterator = monsters.iterator();
                while (monsterIterator.hasNext()) {
                    Monster currentMonster = monsterIterator.next();
                    if (!currentMonster.isAlive()) {
                        continue;
                    }
                    if (party.isEmpty()) {
                        break;
                    }
                    ArrayList<creature.Character> livingPartyMembers = new ArrayList<>();
                    for (creature.Character member : party) {
                        if (member.isAlive()) {
                            livingPartyMembers.add(member);
                        }
                    }
                    if (livingPartyMembers.isEmpty()) {
                        break;
                    }
                    creature.Character targetCharacter = livingPartyMembers.get(random.nextInt(livingPartyMembers.size()));
                    currentMonster.attack(targetCharacter);
                    checkCharacterStatus(party);
                    if (party.isEmpty()) {
                        break;
                    }
                }
            }
            System.out.println("\n--- 戦闘終了 ---");
            if (monsters.isEmpty()) {
                System.out.println("敵を全て倒した！" + hero.getName() + "達は勝利した！");
            } else if (party.isEmpty()) {
                System.out.println("味方パーティは全滅してしまった…");
            }
        } catch (IOException e) {
            System.err.println("入出力エラーが発生しました: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void displayStatus(ArrayList<creature.Character> party, ArrayList<Monster> monsters) {
        System.out.println("\n--- 味方パーティ ---");
        if (party.isEmpty()) {
            System.out.println("（パーティは全滅しています）");
        } else {
            for (creature.Character member : party) {
                member.showStatus();
            }
        }
        System.out.println("\n--- 敵グループ ---");
        if (monsters.isEmpty()) {
            System.out.println("（敵は全滅しています）");
        } else {
            for (Monster monster : monsters) {
                monster.showStatus();
            }
        }
    }

    public static void checkMonsterStatus(ArrayList<Monster> monsters) {
        Iterator<Monster> it = monsters.iterator();
        while (it.hasNext()) {
            Monster m = it.next();
            if (!m.isAlive()) {
                m.die();
                it.remove();
            } else if (m.getHp() <= 5) {
                m.run();
                it.remove();
            }
        }
    }

    public static void checkCharacterStatus(ArrayList<creature.Character> party) {
        Iterator<creature.Character> it = party.iterator();
        while (it.hasNext()) {
            creature.Character c = it.next();
            if (!c.isAlive()) {
                c.die();
                it.remove();
            }
        }
    }
}

