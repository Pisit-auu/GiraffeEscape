import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Fortress {
    private JLabel fortressLebel;
     private int x, y;
     private int hp=1000;
     private String frames;
     private  GamePanel drawpanel;
         private String name;
         Fortress(String frames, int x, int y,int hp,GamePanel drawpanel,String name) {
        this.frames = frames;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.drawpanel =drawpanel;
        this.name = name;
       try {
            this.fortressLebel = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource(frames)))); 
            this.fortressLebel.setBounds(x, y, 400, 400); 
        } catch (IOException g) {
            System.out.println("Error loading enemy image: " + g.getMessage());
        }
    }
    JLabel getfortresslebel() {
        return fortressLebel;
    }

    public String getname(){
        return this.name;
    }
    public Rectangle getBounds() {
        return fortressLebel.getBounds();
    }
    public  int gethp(){
        return this.hp;
    }
    public void sethprestartenemi(){
        drawpanel.sethpenemy(1000);
        this.hp= 1000;
    }
     public void sethprestartgiraffe(){
        drawpanel.sethpgirafe(1000);
        this.hp= 1000;
    }
    public void sethpgirafe(int damage){
        this.hp-=damage;
        drawpanel.sethpgirafe(this.hp);
        if(hp<=0){
            drawpanel.sethpgirafe(0);
        }
    }
     public void sethpenemy(int damage){
        this.hp-=damage;
        drawpanel.sethpenemy(this.hp);
        if(hp<=0){
            drawpanel.sethpenemy(0);
        }
    }
}
