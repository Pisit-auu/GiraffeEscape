import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class MapPage extends JFrame{
        private boolean level1Unlocked = true;
        private boolean level2Unlocked = true;
        private boolean level3Unlocked = true;
        private JButton btstart = new JButton("Start");
       private JLayeredPane layeredpane = new JLayeredPane();
        private ArrayList<String> namemap = new ArrayList<>();
        private JComboBox<String> namemapgiraffe;
        private Image mapimg;
        private Image girrafe;
        private MapPagePanel p;
        void addmap2(){
               if(level2Unlocked){
                    namemap.add("map" + 2);
                namemapgiraffe.addItem("map" + 2);
                level2Unlocked=false;
               }
        }
        void addmap3(){
               if(level3Unlocked){
                    namemap.add("map" + 3);
                namemapgiraffe.addItem("map" + 3);
                level3Unlocked=false;
               }
        }
    MapPage(){
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
                    if("map1".equals(namemapgiraffe.getSelectedItem())){
                                        Level1 r1 = new Level1(MapPage.this); 
                                        r1.setSize(1600,800);
                                        r1.setTitle(namemap.get(0));
                                        r1.setVisible(true); 
                                        r1.setResizable(false);
                                        dispose();
                    }else  if("map2".equals(namemapgiraffe.getSelectedItem())){
                                        Level2 r2 = new Level2(MapPage.this); 
                                        r2.setSize(1600,800);
                                        r2.setTitle(namemap.get(1));
                                        r2.setVisible(true); 
                                        r2.setResizable(false);
                                        dispose();
                    }else  if("map3".equals(namemapgiraffe.getSelectedItem())){
                                        Level3 r3 = new Level3(MapPage.this); 
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
        p= new MapPagePanel(mapimg,girrafe);
        layeredpane.add(p, Integer.valueOf(0));
        layeredpane.add(btstart, Integer.valueOf(1));
        layeredpane.add(namemapgiraffe, Integer.valueOf(1));
        add(layeredpane);
    }
    
    public class MapPagePanel extends JPanel{
        private Image map;
        private Image girrafe;
        private int x;
        private int y;
         public MapPagePanel(Image map,Image girrafe) {
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