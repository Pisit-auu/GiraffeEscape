class Human extends GameCharacter {
    Human(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/people/walk0.png", "/projectgame/people/walk1.png",
            "/projectgame/people/walk2.png", "/projectgame/people/walk3.png" }, x, y, velocity,hp,damage,attackspeed,
            new String[] {"/projectgame/people/attackpeople0.png", "/projectgame/people/attackpeople1.png", "/projectgame/people/attackpeople2.png"});
    }
}
