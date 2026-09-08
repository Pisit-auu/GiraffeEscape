class LizardGiraffe extends GameCharacter {
    LizardGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/lizard/wa0.png", "/projectgame/lizard/wa1.png" }, x, y, velocity,hp,damage,attackspeed,
                new String[]  { "/projectgame/lizard/lizardattack0.png", "/projectgame/lizard/lizardattack1.png", "/projectgame/lizard/lizardattack2.png" }
                );
    }
}
