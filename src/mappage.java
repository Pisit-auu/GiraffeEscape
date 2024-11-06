import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

class Map extends JFrame{
        private boolean map1= true;
        private boolean map2= true;
        private  boolean map3= true;
        private JButton btstart = new JButton("Start");
       private JLayeredPane layeredpane = new JLayeredPane();
        private ArrayList<String> namemap = new ArrayList<>();
        private JComboBox<String> namemapgiraffe;
        private Image mapimg;
        private Image girrafe;
        private drawmappage p;
        void addmap2(){
               if(map2){
                    namemap.add("map" + 2);
                namemapgiraffe.addItem("map" + 2);
                map2=false;
               }
        }
        void addmap3(){
               if(map3){
                    namemap.add("map" + 3);
                namemapgiraffe.addItem("map" + 3);
                map3=false;
               }
        }
    Map(){
         setTitle("Giraffe Escapse");
         namemap.add("map1");
        namemapgiraffe = new JComboBox<>(namemap.toArray(new String[0])); 
        namemapgiraffe.setBounds(50, 50, 150, 30); 
                 
                 
         layeredpane.setBounds(0,0,1000,800);
         btstart.setBounds(800, 600, 100, 50);
                  btstart.setBackground(Color.darkGray);
         btstart.setForeground(Color.WHITE);
       btstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(namemapgiraffe.getSelectedItem() == "map1"){
                                        map1 r1 = new map1(Map.this); 
                                        r1.setSize(1600,800);
                                        r1.setTitle(namemap.get(0));
                                        r1.setVisible(true); 
                                        r1.setResizable(false);
                                        dispose();
                    }else  if(namemapgiraffe.getSelectedItem() == "map2"){
                                        map2 r2 = new map2(Map.this); 
                                        r2.setSize(1600,800);
                                        r2.setTitle(namemap.get(1));
                                        r2.setVisible(true); 
                                        r2.setResizable(false);
                                        dispose();
                    }else  if(namemapgiraffe.getSelectedItem() == "map3"){
                                        map3 r3 = new map3(Map.this); 
                                        r3.setSize(1600,800);
                                        r3.setTitle(namemap.get(2));
                                        r3.setResizable(false);
                                        r3.setVisible(true); 
                                        dispose();
                    }
            }
        });
        namemapgiraffe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMap = (String) namemapgiraffe.getSelectedItem();
                if (selectedMap != null) {
                    p.updatePosition(selectedMap);
                    p.repaint();
                }
            }
        });
        try {
                    mapimg = ImageIO.read(getClass().getResource("/projectgame/map/map1.png"));
                    girrafe = ImageIO.read(getClass().getResource("/projectgame/default/defaultwalk0.png"));
                } catch (IOException e) {
                    System.out.println("Error loading image: " + e.getMessage());
                    e.printStackTrace();
                }
        p= new drawmappage(mapimg,girrafe);
        layeredpane.add(p, Integer.valueOf(0));
        layeredpane.add(btstart, Integer.valueOf(1));
        layeredpane.add(namemapgiraffe, Integer.valueOf(1));
        add(layeredpane);
    }
    
    public class drawmappage extends JPanel{
        private Image map;
        private Image girrafe;
        private int x;
        private int y;
         public drawmappage(Image map,Image girrafe) {
            this.map = map;  
            this.girrafe = girrafe;
            x=420;
            y=130;
            setBounds(0, 0, 1000, 800);
         }
         
        @Override
        public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(map, 0, 0, getWidth(), getHeight(), this);
                g.drawImage(girrafe, x, y, 150, 150, this);
        }
        void updatePosition(String namemapgiraffe) {
            if (namemapgiraffe.equals("map2")) {
                 x = 300;  
                y = 285; ; 
            }else  if (namemapgiraffe.equals("map3")) {
                 x = 540;  
                y = 440;
            }else if (namemapgiraffe.equals("map1")) {
                x = 420;  
                y = 130;  

            }
        
        }
}

}