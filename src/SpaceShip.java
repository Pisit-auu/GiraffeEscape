class SpaceShip extends GameCharacter {
    
    SpaceShip(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/spaceship/spaceshipwalk0.png", "/projectgame/spaceship/spaceshipwalk1.png",
        "/projectgame/spaceship/spaceshipwalk2.png" }, x, y, velocity,hp,damage,attackspeed,
        new String[] {"/projectgame/spaceship/spaceshipattack0.png", "/projectgame/spaceship/spaceshipattack1.png",     "/projectgame/spaceship/spaceshipattack2.png"} );
    }
}
