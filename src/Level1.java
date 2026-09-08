
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Random;


public class Level1 extends JFrame implements Runnable{
        public JButton createButton(String pathImage) {
        JButton b= null; 
          try {
                b = new JButton(new ImageIcon(ImageIO.read(Level1.class.getResource(pathImage))));
        } catch (IOException e) {
             System.out.println("Error loading image: " + e.getMessage());
             e.printStackTrace();
        }
        b.setPreferredSize(new Dimension(80, 80));
        b.setBackground(Color.decode("#f2ff84"));
        b.setBorder(new LineBorder(Color.black, 1));
        return b;
    }

    private JButton bc1 = createButton("/projectgame/icon/defaulticon.png");
    private JButton bc2 = createButton("/projectgame/icon/tankicon.png");
    private JButton exit = new JButton("Exit");
    private JButton exit2 = new JButton("Back to Map");
    private JButton restart = new JButton("Try Agian");
    private JPanel winlose ;
    private JLabel textwinlose;
    private JButton backtomenu;
    private JButton restart2= new JButton("restart"); ;
    private ArrayList<GameCharacter> giraffes = new ArrayList<>(); 
    private ArrayList<GameCharacter> enemies = new ArrayList<>();  
     private ButtonListener bt = new ButtonListener();
     private JLayeredPane layeredpane = new JLayeredPane();  
     private GamePanel drawPanel; 
     public boolean checkspawncharacter =true;
    private Random random = new Random();
    private  Fortress fortressgirafe;
    private  Fortress fortressEnemy;
    private Thread updateThread ; 
    private MapPage mapPage;
    private boolean win =false;
    private boolean lose=false;
    private boolean isRunning = true;
    private  Thread enemySpawnThread;

private void restartGame() {

    for (GameCharacter giraffe : giraffes) {
        giraffe.stopattack();
        layeredpane.remove(giraffe.getCharacterLabel());
        
    }
    giraffes.clear();  

    for (GameCharacter enemy : enemies) {
        enemy.stopattack();
        layeredpane.remove(enemy.getCharacterLabel());
    }
    enemies.clear(); 

   
    fortressgirafe.sethprestartgiraffe();
    fortressEnemy.sethprestartenemi();
    win = false;
    lose = false;
    winlose.setVisible(false);
    

    drawPanel.repaint();
}


    Level1(MapPage mapPage){
         layeredpane.setPreferredSize(new Dimension(1600, 800));
        JPanel pnchoose = new JPanel();
        pnchoose.setBounds(600, 660,440, 90);
        pnchoose.setLayout(new FlowLayout());
        pnchoose.setOpaque(false);
        bc1.addActionListener(bt);
        bc2.addActionListener(bt);

        
        
        pnchoose.add(bc1);
        pnchoose.add(bc2);

        
        
        drawPanel = new GamePanel("/bgmap1.png");
        drawPanel.repaint();
        drawPanel.setBounds(0, 0, 1600, 800);
        drawPanel.setOpaque(false); 
        fortressgirafe = new FortressGiraffe(1350,300,1000,drawPanel);
        fortressEnemy = new FortressEnemy(-200,250,1000,drawPanel);
        layeredpane.add(drawPanel, JLayeredPane.DEFAULT_LAYER); 
        layeredpane.add(pnchoose, JLayeredPane.DRAG_LAYER); 
        layeredpane.add(fortressgirafe.getfortresslebel(), JLayeredPane.DRAG_LAYER);
        layeredpane.add(fortressEnemy.getfortresslebel(), JLayeredPane.DRAG_LAYER);
        
        exit.setBounds(1400, 700, 100, 30);
        exit.setBackground(Color.yellow);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPage.setVisible(true);
                dispose(); 
            }
        });
        layeredpane.add(exit, JLayeredPane.DRAG_LAYER);
         exit2.setBackground(Color.yellow);
        exit2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 mapPage.setVisible(true);
                dispose(); 
            }
        });
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restart2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restart.setBackground(Color.yellow);
        restart2.setBounds(1300, 700, 100, 30);
         restart2.setBackground(Color.yellow);
        layeredpane.add(restart2, JLayeredPane.DRAG_LAYER);

         
        this.mapPage = mapPage; 
        
        winlose = new JPanel();
        winlose.setBounds(600, 300,400, 200);
        winlose.setBackground(Color.DARK_GRAY);
        winlose.setVisible(false);
        layeredpane.add(winlose,JLayeredPane.DRAG_LAYER);
        
        
        add(layeredpane);
         enemySpawnThread = new Thread(() -> {
            while (true) {
                int randomspawn = 2000 + random.nextInt(1000);

                if(checkspawncharacter){
                 spawnRandomEnemy(); 
                }
                
                try {
                    Thread.sleep(randomspawn);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        enemySpawnThread.start();  
        updateThread = new Thread(this);
        updateThread.start();
    }
    
void popwinlose() {
    if (win || lose) {
        return;
    }
    winlose.removeAll();
    winlose.setLayout(null); 
    textwinlose = new JLabel();
    textwinlose.setFont(new Font("Arial", Font.BOLD, 24));
    if (fortressEnemy.gethp() <= 0) {
        textwinlose.setText("You win!");
        mapPage.addmap2();
        textwinlose.setForeground(Color.WHITE);
        win = true;
       winlose.setVisible(true);
       
    } else if (fortressgirafe.gethp() <= 0) {
        textwinlose.setText("You lose!");
        textwinlose.setForeground(Color.WHITE);
        lose = true;
       winlose.setVisible(true);
    }
    textwinlose.setBounds(150, 30, 200, 50);
    winlose.add(textwinlose);
    exit2.setBounds(100, 100, 200, 30);
    if (lose) {
       exit2.setBounds(70, 100, 150, 30);
        restart.setBounds(220, 100, 100, 30);
        winlose.add(restart);
    } 
    winlose.add(exit2);
    winlose.revalidate();
    winlose.repaint();
}



@Override
public void run() {
    long lastTime = System.nanoTime();
    final double ns = 1000000000.0 / 60.0; 
    double delta = 0;
    while (isRunning) {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        
        while (delta >= 1) {
            updateGame();
            delta--;
        }
        try {
            Thread.sleep(2); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
private void updateGame() {
    // ใช้ Iterator สำหรับ giraffes
    Iterator<GameCharacter> giraffeIterator = giraffes.iterator();
    while (giraffeIterator.hasNext()) {
        GameCharacter character = giraffeIterator.next();
        character.startMoving();
    }

    // ใช้ Iterator สำหรับ enemies
    Iterator<GameCharacter> enemyIterator = enemies.iterator();
    while (enemyIterator.hasNext()) {
        GameCharacter character = enemyIterator.next();
        character.startMoving();
    }

    checkCollisions();
    popwinlose();
    checkspawncharacter = checkresult();

    repaint();
}

    private boolean checkresult(){
        if(fortressEnemy.gethp()<=0|| fortressgirafe.gethp()<=0){
            return false;
        }
        return true;
    }
private void checkCollisions() {
    for (GameCharacter playerChar : giraffes) {
        for (GameCharacter enemyChar : enemies) {
            if (playerChar.getBounds().intersects(enemyChar.getBounds())) {
                playerChar.attack(enemyChar);
                enemyChar.attack(playerChar);
            }
        }
    }
    
    Iterator<GameCharacter> giraffeIterator = giraffes.iterator();
    while (giraffeIterator.hasNext()) {
        GameCharacter charac = giraffeIterator.next();
        if (charac.gethp() <= 0) {
            layeredpane.remove(charac.getCharacterLabel());
            giraffeIterator.remove();
        }
    }
    Iterator<GameCharacter> enemyIterator = enemies.iterator();
    while (enemyIterator.hasNext()) {
        GameCharacter charac = enemyIterator.next();
        if (charac.gethp() <= 0) {
            layeredpane.remove(charac.getCharacterLabel());
            enemyIterator.remove(); 
        }
    }
    
    drawPanel.repaint();

    // ตรวจสอบการชนระหว่างยีราฟและป้อมปราการ
    giraffeIterator = giraffes.iterator();
    while (giraffeIterator.hasNext()) {
        GameCharacter giraffe = giraffeIterator.next();
        if (CollisionDetect.isColliding(giraffe, fortressEnemy)) {
            giraffe.stopMoving();
            giraffe.attackfortress(fortressEnemy);
            drawPanel.repaint();
        }
    }
    
    // ตรวจสอบการชนระหว่างศัตรูและป้อมปราการ
    enemyIterator = enemies.iterator();
    while (enemyIterator.hasNext()) {
        GameCharacter enemy = enemyIterator.next();
        if (CollisionDetect.isColliding(enemy, fortressgirafe)) {
            enemy.stopMoving();
            enemy.attackfortress(fortressgirafe);
            drawPanel.repaint();
        }
    }
}
private void setButtonEnabled(boolean enabled) {
    bc1.setEnabled(enabled);
    bc2.setEnabled(enabled);
}

class ButtonListener implements ActionListener {
    
           private int cooldownbc1 = 1000;
       private   int cooldownbc2 = 1200;
        private  int cooldown ;
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        GameCharacter giraffe = null;
        int y = 520+random.nextInt(20);  
        if (e.getSource() == bc1) {
            setButtonEnabled(false);
            cooldown = cooldownbc1;
            giraffe = new DefaultGiraffe(1300, y, -5,100,15,500);
        } else if (e.getSource() == bc2) {
            cooldown = cooldownbc2;
                setButtonEnabled(false);
            giraffe = new TankGiraffe(1300, y, -3,300,20,800); 
        }
        
        if (giraffe != null) {
            giraffe.startMoving();
            giraffes.add(giraffe);
            layeredpane.add(giraffe.getCharacterLabel(), JLayeredPane.DRAG_LAYER);
        }
                Timer timer = new Timer(cooldown, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 setButtonEnabled(true);
            }
        });
        timer.setRepeats(false); // ให้ Timer ทำงานเพียงครั้งเดียว
        timer.start();
        drawPanel.repaint();
    }
}
public void spawnRandomEnemy() {
    int enemyType = random.nextInt(3);  
    int startX = 150;                  
    int startY = 530+random.nextInt(30);  
      GameCharacter enemy = null;
    switch (enemyType) {
        case 0:
            enemy = new Human(startX, startY,15,200,20,500);
            break;
        case 1:
            enemy = new RobotTank(startX, startY, 13,500,18,800);
            break;
        case 2:
            enemy = new SpaceShip(startX, startY, 20,100,20,400);
            break;
        default:
           enemy = new Human(startX, startY,15,100,15,500);
    }
        if (enemy != null) {
               enemy.startMoving();
               enemies.add(enemy);
               layeredpane.add(enemy.getCharacterLabel(), JLayeredPane.DRAG_LAYER);
               layeredpane.setLayer(enemy.getCharacterLabel(), JLayeredPane.DRAG_LAYER);
           }
           drawPanel.repaint();
}
}
