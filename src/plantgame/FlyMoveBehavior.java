package plantgame;

public class FlyMoveBehavior implements MoveBehavior {
    @Override
    public void move(GameObject mover) {
        // 飞行移动逻辑（如无视某些地面障碍）
        mover.setX(mover.getX() - mover.getSpeed());
    }
}
