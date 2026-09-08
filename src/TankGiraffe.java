class TankGiraffe extends GameCharacter {
    TankGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/tank/tankwalk0.png", "/projectgame/tank/tankwalk1.png",
            "/projectgame/tank/tankwalk2.png", "/projectgame/tank/tankwalk3.png" }, x, y, velocity,hp,damage,attackspeed
                    ,new String[] { "/projectgame/tank/tankattack0.png", "/projectgame/tank/tankattack1.png", "/projectgame/tank/tankattack2.png", "/projectgame/tank/tankwalk3.png" }
        );
    }

}
