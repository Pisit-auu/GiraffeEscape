class TitanGiraffe extends GameCharacter {
    TitanGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/titan/titanwalk0.png", "/projectgame/titan/titanwalk1.png", 
            "/projectgame/titan/titanwalk2.png" }, x, y, velocity,hp,damage,attackspeed,
              new String[] { "/projectgame/titan/attack0.png", "/projectgame/titan/attack1.png", "/projectgame/titan/attack2.png" }
        );
    }

}
