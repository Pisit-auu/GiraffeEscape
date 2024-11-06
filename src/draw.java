
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

     class draw extends JPanel {
        private Image background ;
        private int hpfortress=0;
        private int hpfortressenemy=0;
        private String path;
        draw(String path){
            try {
                this.path = path;
                background = ImageIO.read(getClass().getResource(path));


        } catch (IOException e) {
             System.out.println("Error loading image: " + e.getMessage());
             e.printStackTrace();
        }
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background, 0, 0, 1600, 800, this);
             g.setFont(new Font("SansSerif ", Font.BOLD, 20));
             g.drawString("HP Enemies: "+hpfortressenemy, 20, 40);
            g.drawString("HP Giraffe: "+hpfortress, 1300, 40);   

        }    

        void sethpgirafe(int hp){
            this.hpfortress =  hp;
            repaint();
        }
        void sethpenemy(int hp){
            this.hpfortressenemy =  hp;
            repaint();
        }
    }