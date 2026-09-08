class LizardRobot extends GameCharacter {
  
    LizardRobot(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/lizardrobo/lizardwalk0.png", "/projectgame/lizardrobo/lizardwalk1.png" }, x, y, velocity,hp,damage,attackspeed,
         new String[] {"/projectgame/lizardrobo/robolizardattack0.png", "/projectgame/lizardrobo/robolizardattack1.png",  "/projectgame/lizardrobo/robolizardattack2.png"});
    }
}
