import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Character {
    
    private JLabel characterlabel;
    private int x, y, velocity;
    private int hp;
    private String[] frames;
    private int frameIndex = 0;
    private Thread animationThread;
   private  Thread attackThread;
    private volatile boolean running;
    private volatile boolean attackking;
    private boolean isAlive; 
    private int damage;
    private  String[] attack;
    private double speedFactor = 0.1;
    private int attackspeed;
    Character(String[] frames, int x, int y, int velocity,int hp,int damage,int attackspeed,String[] attack) {
        this.frames = frames;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.characterlabel = new JLabel(new ImageIcon(frames[0]));
        characterlabel.setBounds(x, y, 80, 80);
        this.hp = hp;
        this.isAlive = true;
        this.attack = attack;
        this.running=true;
        this.damage = damage;
        this.attackspeed = attackspeed;
        anmationwalk();
    }

        public boolean isAlive() {
                return isAlive; 
            }
        public void startMoving() {
            
               if(running){
                    x += velocity*speedFactor;
                 SwingUtilities.invokeLater(() -> {
                characterlabel.setBounds(x, y, characterlabel.getWidth(), characterlabel.getHeight());
            });
               }
    }
    public void stopMoving() {
                 running=false;
    }
    public void anmationwalk() {
    if (attackking) return;

    attackking = false;

    if (animationThread != null && animationThread.isAlive()) {
        running = false; 
        try {
            animationThread.join();  
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    running = true;
    animationThread = new Thread(() -> {
        while (running) {
            frameIndex = (frameIndex + 1) % frames.length;
            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResource(frames[frameIndex])));
                SwingUtilities.invokeLater(() -> characterlabel.setIcon(icon));
            } catch (IOException s) {
                s.printStackTrace();
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;  
            }
        }
    });
    animationThread.start(); 
}


public void attackfortress(fortress Fortress) {
    stopMoving(); 
    if (attackking) return; 
    attackking = true;
    if (attackThread != null && attackThread.isAlive()) {
        attackThread.interrupt(); 
    }
    attackThread = new Thread(() -> {
        while (attackking ) {
            frameIndex = (frameIndex + 1) % attack.length;
            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResource(attack[frameIndex])));
                SwingUtilities.invokeLater(() -> characterlabel.setIcon(icon));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Fortress.getname() == "giraffe"&&Fortress.gethp()>0) {
                Fortress.sethpgirafe(damage);  
            } else  if (Fortress.getname() == "enemy"&&Fortress.gethp()>0)  {
                Fortress.sethpenemy(damage);   
            }
            try {
                Thread.sleep(attackspeed); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        if (isAlive) anmationwalk();
    });
    attackThread.start();
}
    public void stopattack(){
        attackking =false;
        running=false;
    }
public void attack(Character other) {
   stopMoving(); 
     if (attackking) return; 
    attackking = true;
    if (attackThread != null && attackThread.isAlive()) {
        attackThread.interrupt(); 
    }
    attackThread = new Thread(() -> {
        while (attackking) {
            frameIndex = (frameIndex + 1) % attack.length;
            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResource(attack[frameIndex])));
                SwingUtilities.invokeLater(() -> characterlabel.setIcon(icon));
            } catch (IOException e) {
                e.printStackTrace();
            }
            other.sethp(damage);
            if (other.gethp() <= 0) {
                attackking = false;
                running = true;
                other.setfalseattacking();
                other.setAlive(false); 
            }
            try {
                Thread.sleep(attackspeed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; 
            }
        }
        if (isAlive) anmationwalk();
    });
    attackThread.start(); 
}
public void setAlive(boolean set){
    isAlive= set;
}

 public  void setfalseattacking(){
    this.attackking=false;
    }   


public void sethp(int damage) {
    this.hp -= damage;

}
   public  int gethp(){
        return this.hp;
    }
   
    public JLabel getCharacterLabel() {
        return characterlabel;
    }
    public Rectangle getBounds() {
        return characterlabel.getBounds();
    }
}

class DefaultGiraffe extends Character {
    DefaultGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/default/defaultwalk0.png", "/projectgame/default/defaultwalk1.png", 
            "/projectgame/default/defaultwalk2.png", "/projectgame/default/defaultwalk3.png" },
                x, y, velocity,hp,damage,attackspeed,new String[] { "/projectgame/default/defaultattack.png", "/projectgame/default/defaultattack1.png", "/projectgame/default/defaultattack2.png" }
            );
    }
}

class TankGiraffe extends Character {
    TankGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/tank/tankwalk0.png", "/projectgame/tank/tankwalk1.png",
            "/projectgame/tank/tankwalk2.png", "/projectgame/tank/tankwalk3.png" }, x, y, velocity,hp,damage,attackspeed
                    ,new String[] { "/projectgame/tank/tankattack0.png", "/projectgame/tank/tankattack1.png", "/projectgame/tank/tankattack2.png", "/projectgame/tank/tankwalk3.png" }
        );
    }

}

class TitanGiraffe extends Character {
    TitanGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/titan/titanwalk0.png", "/projectgame/titan/titanwalk1.png", 
            "/projectgame/titan/titanwalk2.png" }, x, y, velocity,hp,damage,attackspeed,
              new String[] { "/projectgame/titan/attack0.png", "/projectgame/titan/attack1.png", "/projectgame/titan/attack2.png" }
        );
    }

}

class BirdGiraffe extends Character {
    BirdGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/birdgirafe/birdgiraftwalk0.png","/projectgame/birdgirafe/birdgiraftwalk1.png",
            "/projectgame/birdgirafe/birdgiraftwalk2.png" }, x, y, velocity,hp,damage,attackspeed,
            new String[] { "/projectgame/birdgirafe/birdattack0.png", "/projectgame/birdgirafe/birdattack1.png", "/projectgame/birdgirafe/birdattack2.png", "/projectgame/birdgirafe/birdattack3.png" }
            );
    }
}

class LizardGiraffe extends Character {
    LizardGiraffe(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/lizard/wa0.png", "/projectgame/lizard/wa1.png" }, x, y, velocity,hp,damage,attackspeed,
                new String[]  { "/projectgame/lizard/lizardattack0.png", "/projectgame/lizard/lizardattack1.png", "/projectgame/lizard/lizardattack2.png" }
                );
    }
}

class people extends Character {
    people(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/people/walk0.png", "/projectgame/people/walk1.png",
            "/projectgame/people/walk2.png", "/projectgame/people/walk3.png" }, x, y, velocity,hp,damage,attackspeed,
            new String[] {"/projectgame/people/attackpeople0.png", "/projectgame/people/attackpeople1.png", "/projectgame/people/attackpeople2.png"});
    }
}

class robottank extends Character {
    robottank(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[] { "/projectgame/robottank/robottank0.png", "/projectgame/robottank/robottank1.png",
            "/projectgame/robottank/robottank2.png", "/projectgame/robottank/robottank3.png" }, x, y, velocity,hp,damage,attackspeed,
            new String[] {"/projectgame/robottank/robotankattack0.png", "/projectgame/robottank/robotankattack1.png",     "/projectgame/robottank/robotankattack2.png"});
    }
}

class lizardrobo extends Character {
  
    lizardrobo(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/lizardrobo/lizardwalk0.png", "/projectgame/lizardrobo/lizardwalk1.png" }, x, y, velocity,hp,damage,attackspeed,
         new String[] {"/projectgame/lizardrobo/robolizardattack0.png", "/projectgame/lizardrobo/robolizardattack1.png",  "/projectgame/lizardrobo/robolizardattack2.png"});
    }
}

class spaceship extends Character {
    
    spaceship(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/spaceship/spaceshipwalk0.png", "/projectgame/spaceship/spaceshipwalk1.png",
        "/projectgame/spaceship/spaceshipwalk2.png" }, x, y, velocity,hp,damage,attackspeed,
        new String[] {"/projectgame/spaceship/spaceshipattack0.png", "/projectgame/spaceship/spaceshipattack1.png",     "/projectgame/spaceship/spaceshipattack2.png"} );
    }
}

class titanrobo extends Character {
    
    titanrobo(int x, int y, int velocity,int hp,int damage,int attackspeed) {
        super(new String[]{ "/projectgame/titanrobo/titanrobowalk0.png", "/projectgame/titanrobo/titanrobowalk1.png",
                "/projectgame/titanrobo/titanrobowalk2.png" }, x, y, velocity,hp,damage,attackspeed,
                 new String[]  {"/projectgame/titanrobo/titanattack0.png", "/projectgame/titanrobo/titanattack1.png",     "/projectgame/titanrobo/titanattack2.png"});
    }
}


