class RobotTank extends GameCharacter {
    RobotTank(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/robottank/robottank0.png", "/projectgame/robottank/robottank1.png",
            "/projectgame/robottank/robottank2.png", "/projectgame/robottank/robottank3.png" }, x, y, velocity,hp,damage,attackspeed,
            new String[] {"/projectgame/robottank/robotankattack0.png", "/projectgame/robottank/robotankattack1.png",     "/projectgame/robottank/robotankattack2.png"});
    }
}
