class FortressEnemy extends Fortress {
    FortressEnemy(int x, int y, int hp,GamePanel drawPanel) {
        super("/projectgame/enemyfortress.png", x, y, hp,drawPanel,"enemy");
        drawPanel.sethpgirafe(hp);
    }
   

}
