package plantgame;

import plantgame.GameObject;

public class MoveBackwardsBehavior implements MoveBehavior {
    @Override
    public void move(GameObject mover) {
        // 倒退移动逻辑
        mover.setX(mover.getX() + Math.abs(mover.getSpeed()));
    }
}
