class DefaultGiraffe extends GameCharacter {
    DefaultGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/default/defaultwalk0.png", "/projectgame/default/defaultwalk1.png", 
            "/projectgame/default/defaultwalk2.png", "/projectgame/default/defaultwalk3.png" },
                x, y, velocity,hp,damage,attackspeed,new String[] { "/projectgame/default/defaultattack.png", "/projectgame/default/defaultattack1.png", "/projectgame/default/defaultattack2.png" }
            );
    }
}
