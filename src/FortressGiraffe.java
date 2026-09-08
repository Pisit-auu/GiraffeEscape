class FortressGiraffe extends Fortress {

    FortressGiraffe(int x, int y, int hp,GamePanel drawPanel) {
        super("/projectgame/Giraffefortress.png", x, y, hp,drawPanel,"giraffe");
        drawPanel.sethpenemy(hp);
    }
    
}
