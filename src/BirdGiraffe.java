class BirdGiraffe extends GameCharacter {
    BirdGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/birdgirafe/birdgiraftwalk0.png","/projectgame/birdgirafe/birdgiraftwalk1.png",
            "/projectgame/birdgirafe/birdgiraftwalk2.png" }, x, y, velocity,hp,damage,attackspeed,
            new String[] { "/projectgame/birdgirafe/birdattack0.png", "/projectgame/birdgirafe/birdattack1.png", "/projectgame/birdgirafe/birdattack2.png", "/projectgame/birdgirafe/birdattack3.png" }
            );
    }
}
