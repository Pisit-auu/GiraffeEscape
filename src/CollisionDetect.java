public class CollisionDetect {

    public static boolean isColliding(Character enemy, fortress fortress) {
        if (enemy == null || fortress == null) return false;
        return enemy.getBounds().intersects(fortress.getBounds());
    }


    public static boolean isColliding(Character enemy, Character giraffe) {
        if (enemy == null || giraffe == null) return false;
        return enemy.getBounds().intersects(giraffe.getBounds());
    }


}
