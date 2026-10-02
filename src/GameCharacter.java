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

public class GameCharacter {
    
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
    // ตำแหน่ง x จริงแบบทศนิยม: ต้องสะสมเป็น double ไม่งั้น velocity*speedFactor (<1 px)
    // จะถูกตัดทิ้งทุก tick ทำให้ทุกตัวเคลื่อนที่เร็วเท่ากันหมดไม่ว่าตั้ง velocity เท่าไหร่
    private double exactX;
    private int attackspeed;
    GameCharacter(String[] frames, int x, int y, int velocity,int hp,int damage,int attackspeed,String[] attack) {
        this.frames = frames;
        this.x = x;
        this.exactX = x;
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
                    exactX += velocity * speedFactor;
                    x = (int) Math.round(exactX);
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


public void attackfortress(Fortress target) {
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
            if ("giraffe".equals(target.getname())&&target.gethp()>0) {
                target.sethpgirafe(damage);  
            } else  if ("enemy".equals(target.getname())&&target.gethp()>0)  {
                target.sethpenemy(damage);   
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
    // หยุดยูนิตถาวร: ใช้ตอนออกจากด่านหรือเริ่มด่านใหม่
    // ต้องตั้ง isAlive=false ก่อน ไม่งั้น attack thread จะเรียก anmationwalk() เริ่ม thread เดินใหม่ตอนจบลูป
    public void destroy(){
        isAlive = false;
        attackking = false;
        running = false;
        if (attackThread != null) attackThread.interrupt();
        if (animationThread != null) animationThread.interrupt();
    }
public void attack(GameCharacter other) {
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
