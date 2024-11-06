import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

public class mainpage extends JFrame {
    JButton btstart = new JButton("Start");
    JLayeredPane layeredpane = new JLayeredPane();
    mainpage(){
        setTitle("Giraffe Escapse");
         layeredpane.setBounds(0,0,1000,800);
         btstart.setBounds(400, 400, 200, 50);
         btstart.setBackground(Color.darkGray);
         btstart.setForeground(Color.WHITE);
        URL images = this.getClass().getResource("startpage.png");
        Image imgbg = new ImageIcon(images).getImage();
        
      layeredpane.add(new drawmainpage(imgbg), Integer.valueOf(0));
       btstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                        Map frame = new Map();
                        frame.setSize(1000, 800);
                        frame.setResizable(false);
                         frame.setLayout(null);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);
            }
        });
       layeredpane.add(btstart, Integer.valueOf(1));
        add(layeredpane);
       
        
    }
    
   class drawmainpage extends JPanel{
        private Image imbg;
        
       public drawmainpage(Image imbg){
           this.imbg  =imbg ;
           setBounds(0, 0, 1000, 800);
       }
       @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(imbg, 0, 0,1000,800,this);
            
        }
    }
    
    public static void main(String[] args){
        JFrame frame = new mainpage();
        frame.setSize(1000,800);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    
}
