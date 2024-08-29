package game;

import CommandLine.UI;
import tiles.units.Unit;
import tiles.units.enemies.Enemy;
import tiles.units.player.Player;

public class CombatSystem {
    //╚║╝╔╟╗╢╚═╔═╚═╝╔═╗╚═╝╔═╝╚═╗╔═╝╚═╝╔═╗
    public static void engageCombat(Unit attacker, Unit defender) {

        int attackRoll = (int) (Math.random() * attacker.getAttackPoints());
        int defenseRoll = (int) (Math.random() * defender.getDefensePoints());
        int damage = Math.max(0, attackRoll - defenseRoll);

        defender.getHealth().takeDamage(damage);
        if (defender.getHealth().getCurrent() <= 0) {
            defender.getHealth().setCurrent(0);
        }


        String A = " " + attacker.getName() + " rolled " + attackRoll + " attack points. ";
        String B = " " + defender.getName() + " rolled " + defenseRoll + " defense points. ";
        String C = " " + attacker.getName() + " dealt " + damage + " damage to " + defender.getName() + " ";
        String D = " reducing their health to " + "|HP: " + Math.max(0, defender.getHealth().getCurrent()) + '/' + defender.getHealth().getCapacity() + "| ";
        if (damage == 0) {
            C = " " + attacker.getName() + " dealt no damage to " + defender.getName() + " ";
            D = " as their defense was too high! ";
        }

        int a = A.length();
        int b = B.length();
        int c = C.length();
        int d = D.length();

        int aHP = (String.valueOf(attacker.getHealth().getCurrent()).length() + String.valueOf(attacker.getHealth().getCurrent()).length());
        int dHP = (String.valueOf(defender.getHealth().getCurrent()).length() + String.valueOf(defender.getHealth().getCurrent()).length());

        int maxName = Math.max(attacker.getName().length(), defender.getName().length());
        int maxHP = Math.max(dHP, aHP);
        int maxPoint = Math.max(String.valueOf(attacker.getAttackPoints()).length(), String.valueOf(defender.getDefensePoints()).length());

        String attackerFormat = (" " + attacker.getName() + " ".repeat(maxName - attacker.getName().length()) +
                "  |HP: " + attacker.getHealth().getCurrent() + '/' + attacker.getHealth().getCapacity() +
                " ".repeat(maxHP - aHP) + "| " + " |Attack:  " + attacker.getAttackPoints() +
                " ".repeat(maxPoint - String.valueOf(attacker.getAttackPoints()).length()) + "| ");

        String defenderFormat = (" " + defender.getName() + " ".repeat(maxName - defender.getName().length()) +
                "  |HP: " + defender.getHealth().getCurrent() + '/' + defender.getHealth().getCapacity() +
                " ".repeat(maxHP - dHP) + "| " + " |Defence: " + defender.getDefensePoints() +
                " ".repeat(maxPoint - String.valueOf(defender.getDefensePoints()).length()) + "| ");
        String msg = " " + attacker.getName() + " engaged in combat with " + defender.getName() + "! ";
        int max = Math.max(Math.max(Math.max(Math.max(c,d), Math.max(a,b)), Math.max(attackerFormat.length(), defenderFormat.length())), msg.length());


        UI.print("╔" +                  "═".repeat(max)                                 + "╗");
        UI.print("║"                  +      msg + " ".repeat(max - msg.length()) + "║");
        UI.print("╟" +                  "─".repeat(max)                                 + "╢");
        UI.print("║" + attackerFormat + " ".repeat(max - attackerFormat.length()) + "║");
        UI.print("║" + defenderFormat + " ".repeat(max - defenderFormat.length()) + "║");
        UI.print("╠" +                  "═".repeat(max)                                 + "╣");
        UI.print("║" +        A       + " ".repeat(max - a)                       + "║");
        UI.print("║" +        B       + " ".repeat(max - b)                       + "║");
        UI.print("║" +        C       + " ".repeat(max - c)                       + "║");
        UI.print("║" +        D       + " ".repeat(max - d)                       + "║");
        UI.print("╚" +                  "═".repeat(max)                                 + "╝" + "\n");
    }


}
