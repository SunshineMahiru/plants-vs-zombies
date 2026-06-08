package plantgame;

import plantgame.GameObject;

public class AOEExplodeBehavior implements AttackBehavior {
    @Override
    public void attack(GameObject attacker) {
        // 实现爆炸逻辑，对周围造成伤害
        System.out.println(attacker.getName() + " 执行爆炸攻击！");
        // 这里可以结合游戏内的僵尸列表，实现AOE伤害
    }
}
