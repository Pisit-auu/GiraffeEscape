class TitanRobot extends GameCharacter {
    
    TitanRobot(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/titanrobo/titanrobowalk0.png", "/projectgame/titanrobo/titanrobowalk1.png",
                "/projectgame/titanrobo/titanrobowalk2.png" }, x, y, velocity,hp,damage,attackspeed,
                 new String[]  {"/projectgame/titanrobo/titanattack0.png", "/projectgame/titanrobo/titanattack1.png",     "/projectgame/titanrobo/titanattack2.png"});
    }
}
