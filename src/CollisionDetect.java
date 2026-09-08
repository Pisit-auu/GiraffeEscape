public class CollisionDetect {

    public static boolean isColliding(GameCharacter enemy, Fortress Fortress) {
        if (enemy == null || Fortress == null) return false;
        return enemy.getBounds().intersects(Fortress.getBounds());
    }


    public static boolean isColliding(GameCharacter enemy, GameCharacter giraffe) {
        if (enemy == null || giraffe == null) return false;
        return enemy.getBounds().intersects(giraffe.getBounds());
    }
}
