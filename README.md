# Giraffe Escape 🦒

เกมแนว **Tower Defense / Lane Battle** แบบ 2D เขียนด้วย **Java Swing** ผู้เล่นคุมฝั่งยีราฟ ส่งยีราฟหลายสายพันธุ์ออกไปสู้กับกองทัพมนุษย์และหุ่นยนต์ที่บุกเข้ามา เป้าหมายคือทำลายป้อมของศัตรูให้ได้ก่อนที่ป้อมยีราฟจะถูกทำลาย

ยูนิตทั้งสองฝั่งเดินเข้าหากันบนเลนเดียว เมื่อชนกันจะต่อสู้จนฝั่งหนึ่งตาย แล้วเดินต่อไปโจมตีป้อมฝ่ายตรงข้าม

---

## สารบัญ

- [วิธีเล่น](#วิธีเล่น)
- [ด่าน](#ด่าน)
- [ตัวละคร](#ตัวละคร)
- [Tech Stack](#tech-stack)
- [การติดตั้งและรันเกม](#การติดตั้งและรันเกม)
- [โครงสร้างโปรเจกต์](#โครงสร้างโปรเจกต์)
- [สถาปัตยกรรมของเกม](#สถาปัตยกรรมของเกม)
- [การปรับสมดุลเกม](#การปรับสมดุลเกม)
- [ข้อจำกัดที่ทราบ](#ข้อจำกัดที่ทราบ)

---

## วิธีเล่น

1. **หน้าเริ่มต้น** กดปุ่ม **Start**
2. **หน้าแผนที่** เลือกด่านจาก dropdown มุมซ้ายบน (ยีราฟบนแผนที่จะย้ายไปยังตำแหน่งของด่านที่เลือก) แล้วกด **Start**
3. **ในด่าน**
   - ป้อมยีราฟของเราอยู่ **ขวา** ป้อมศัตรูอยู่ **ซ้าย**
   - กดไอคอนยีราฟด้านล่างจอเพื่อส่งยีราฟออกไป ยีราฟจะเดินจากขวาไปซ้าย
   - หลังกดส่ง ปุ่มทุกปุ่มจะติด **cooldown** ช่วงสั้น ๆ (1–2 วินาทีตามชนิดยีราฟ)
   - ศัตรูจะเกิดจากฝั่งซ้ายเป็นระยะโดยอัตโนมัติ
   - ยูนิตที่ชนกันจะโจมตีกันจน HP ฝั่งใดฝั่งหนึ่งหมด ยูนิตที่ไปถึงป้อมฝ่ายตรงข้ามจะหยุดและโจมตีป้อม
   - HP ของป้อมทั้งสองฝั่งแสดงอยู่ด้านบนของจอ (เริ่มที่ 1,000)
4. **ชนะ** เมื่อ HP ป้อมศัตรูเหลือ 0 และจะปลดล็อกด่านถัดไปบนหน้าแผนที่
5. **แพ้** เมื่อ HP ป้อมยีราฟเหลือ 0 กด **Try Again** เพื่อเริ่มด่านใหม่ หรือ **Back to Map** เพื่อกลับไปหน้าแผนที่

ระหว่างเล่นมีปุ่ม **restart** (เริ่มด่านใหม่) และ **Exit** (กลับหน้าแผนที่) อยู่มุมขวาล่าง การกดปุ่ม X ปิดหน้าต่างด่านก็จะกลับไปหน้าแผนที่เช่นกัน

---

## ด่าน

| ด่าน | ยีราฟที่ใช้ได้ | ศัตรูที่เกิด | ความถี่การเกิดศัตรู | ปลดล็อก |
|------|----------------|--------------|---------------------|---------|
| **map1** | Default, Tank | สุ่ม Human / Robot Tank / Spaceship | ทุก 2–3 วินาที | เปิดตั้งแต่แรก |
| **map2** | Default, Tank, Titan | สุ่ม 5 ชนิด: Human / Robot Tank / Lizard Robot / Spaceship / Titan Robot | ทุก 7.5–8.5 วินาที | ชนะ map1 |
| **map3** | Default, Tank, Titan, Bird, Lizard | สุ่ม 5 ชนิด (ค่าพลังสูงกว่า map2) | ทุก 7–7.5 วินาที | ชนะ map2 |

แต่ละด่านมีพื้นหลังของตัวเอง (`bgmap1.png`, `bgmap2.png`, `bgmap3.png`)

> การปลดล็อกด่านเก็บไว้ในหน่วยความจำเท่านั้น ปิดเกมแล้วต้องเริ่มจาก map1 ใหม่

---

## ตัวละคร

ค่าพลังกำหนดตอนสร้างยูนิตในไฟล์ `Level1.java`–`Level3.java` ค่าเดียวกันอาจต่างกันในแต่ละด่าน

**คำอธิบายค่า**
- **ความเร็ว:** พิกเซลต่อ tick × 0.1 (เกมอัปเดต 60 ครั้ง/วินาที) ค่าลบคือเดินไปทางซ้าย
- **HP:** พลังชีวิต
- **ดาเมจ:** ความเสียหายต่อการโจมตีหนึ่งครั้ง
- **ความเร็วโจมตี:** มิลลิวินาทีระหว่างการโจมตีแต่ละครั้ง (น้อย = ตีเร็ว)

### ฝั่งยีราฟ (ผู้เล่น)

| ยีราฟ | Class | map1 (HP / ดาเมจ / ตีทุก ms) | map2 | map3 | ความเร็ว | Cooldown |
|-------|-------|------------------------------|------|------|----------|----------|
| Default | `DefaultGiraffe` | 100 / 15 / 500 | 100 / 10 / 500 | 100 / 20 / 300 | 5 | 1.0 วิ |
| Tank | `TankGiraffe` | 300 / 20 / 800 | 400 / 25 / 500 | 300 / 25 / 500 | 3 | 1.2 วิ |
| Titan | `TitanGiraffe` | — | 400 / 30 / 1000 | 400 / 35 / 1000 | 2 | 2.0 วิ (map2), 1.3 วิ (map3) |
| Bird | `BirdGiraffe` | — | — | 2500 / 30 / 400 | 8 | 1.5 วิ |
| Lizard | `LizardGiraffe` | — | — | 300 / 40 / 200 | 6 | 2.0 วิ |

### ฝั่งศัตรู

| ศัตรู | Class | ค่าที่ใช้จริง (HP / ดาเมจ / ตีทุก ms) | ความเร็ว |
|-------|-------|----------------------------------------|----------|
| Human | `Human` | map1: 200 / 20 / 500 · map2/map3: 100 / 15 / 500 | 15 |
| Robot Tank | `RobotTank` | map1: 500 / 18 / 800 · map2/map3: 300 / 20 / 800 | 13 |
| Spaceship | `SpaceShip` | map1: 100 / 20 / 400 · map2/map3: 80 / 10 / 400 | 20 (map1), 18 (map2/map3) |
| Titan Robot | `TitanRobot` | map2: 700 / 100 / 1000 · map3: 1000 / 100 / 1200 | 12 |
| Lizard Robot | `LizardRobot` | map2: 120 / 18 / 600 · map3: 3000 / 100 / 500 | 16 (map2), 15 (map3) |

ป้อมทั้งสองฝั่ง (`FortressGiraffe`, `FortressEnemy`) มี HP เริ่มต้น 1,000

---

## Tech Stack

| ส่วน | เทคโนโลยี |
|------|-----------|
| ภาษา | Java (ตั้งค่า source/target เป็น **Java 22**) |
| GUI | Java Swing (`JFrame`, `JLayeredPane`, `JLabel`, `JButton`) |
| กราฟิก | Sprite PNG แบบเฟรมต่อเฟรม โหลดด้วย `ImageIO` |
| Concurrency | `Thread` แยกสำหรับ game loop, การเกิดศัตรู, แอนิเมชัน และการโจมตี |
| Build | Apache Ant ผ่านโปรเจกต์ NetBeans (`build.xml`, `nbproject/`) |

ไม่มี dependency ภายนอก ใช้แค่ JDK

---

## การติดตั้งและรันเกม

### สิ่งที่ต้องมี
- **JDK 22** ขึ้นไป
- (ทางเลือก) **Apache NetBeans** หรือ **Apache Ant**

### วิธีที่ 1: Apache NetBeans (แนะนำ)

1. `File → Open Project` แล้วเลือกโฟลเดอร์ `GiraffeEscape`
2. ตั้ง Java Platform เป็น JDK 22 ขึ้นไป
3. กด **Run Project** (F6) main class คือ `MainPage`

### วิธีที่ 2: Apache Ant

```bash
git clone https://github.com/Pisit-auu/GiraffeEscape.git
cd GiraffeEscape
ant jar                         # build ไปที่ dist/miniproject.jar
java -jar dist/miniproject.jar
```

### วิธีที่ 3: คอมไพล์ด้วย javac ตรง ๆ

รูปภาพทั้งหมดอยู่ใน `src/` และถูกโหลดผ่าน classpath จึงต้องเอา `src/` ใส่ classpath ตอนรันด้วย

```bash
# macOS / Linux
javac -d build/classes src/*.java
java -cp build/classes:src MainPage

# Windows
javac -d build\classes src\*.java
java -cp "build\classes;src" MainPage
```

> หน้าต่างเกมมีขนาดคงที่ **1600 × 800** พิกเซล (หน้าเริ่มต้นและหน้าแผนที่ 1000 × 800) ควรใช้จอที่ความละเอียดพอ และปิดการขยายขนาด (display scaling) ถ้าหน้าต่างล้นจอ

---

## โครงสร้างโปรเจกต์

```
GiraffeEscape/
├── build.xml                    # Ant build script (NetBeans)
├── nbproject/                   # การตั้งค่าโปรเจกต์ NetBeans (main.class = MainPage)
└── src/
    ├── MainPage.java            # หน้าเริ่มต้น + main()
    ├── MapPage.java             # หน้าเลือกด่าน + ปลดล็อกด่าน
    ├── Level1.java              # ด่าน 1
    ├── Level2.java              # ด่าน 2
    ├── Level3.java              # ด่าน 3
    ├── GamePanel.java           # วาดพื้นหลังและ HP ของป้อม
    ├── GameCharacter.java       # คลาสแม่ของยูนิตทุกตัว: เดิน, แอนิเมชัน, โจมตี, HP
    ├── Fortress.java            # คลาสแม่ของป้อม
    ├── FortressGiraffe.java     # ป้อมยีราฟ
    ├── FortressEnemy.java       # ป้อมศัตรู
    ├── CollisionDetect.java     # ตรวจการชน (ยูนิต–ยูนิต, ยูนิต–ป้อม)
    ├── DefaultGiraffe.java, TankGiraffe.java, TitanGiraffe.java,
    │   BirdGiraffe.java, LizardGiraffe.java          # ยูนิตฝั่งยีราฟ
    ├── Human.java, RobotTank.java, SpaceShip.java,
    │   TitanRobot.java, LizardRobot.java             # ยูนิตฝั่งศัตรู
    ├── startpage.png            # พื้นหลังหน้าเริ่มต้น
    ├── bgmap1.png ... bgmap3.png  # พื้นหลังแต่ละด่าน
    ├── icongiraffe.png
    └── projectgame/
        ├── map/map1.png         # ภาพแผนที่เลือกด่าน
        ├── icon/                # ไอคอนปุ่มส่งยีราฟ
        ├── Giraffefortress.png, enemyfortress.png
        └── default/, tank/, titan/, birdgirafe/, lizard/,
            people/, robottank/, spaceship/, titanrobo/, lizardrobo/
                                 # sprite เดิน (walk) และโจมตี (attack) ของแต่ละตัวละคร
```

---

## สถาปัตยกรรมของเกม

```
MainPage ──Start──► MapPage ──เลือกด่าน──► Level1 / Level2 / Level3
                       ▲                         │
                       └──── Exit / ชนะ ─────────┘  (ชนะแล้วเรียก addmap2() / addmap3())
```

### Game Loop
แต่ละด่าน (`LevelN`) เป็น `JFrame` ที่ implements `Runnable` และรันลูปแบบ fixed timestep **60 ครั้งต่อวินาที** ทุก tick จะ:
1. เรียก `startMoving()` ของยูนิตทุกตัว
2. `checkCollisions()` ตรวจการชน สั่งโจมตี และลบยูนิตที่ HP หมด
3. `popwinlose()` ตรวจว่าแพ้หรือชนะ แล้วแสดงหน้าต่างผลลัพธ์
4. หยุดการเกิดศัตรูเมื่อเกมจบ

### Threads
| Thread | หน้าที่ |
|--------|---------|
| Game loop | อัปเดตตำแหน่งและตรวจการชน 60 FPS |
| Enemy spawner | สุ่มเวลาและสร้างศัตรูใหม่ |
| Animation (ต่อยูนิต) | สลับเฟรม sprite การเดินทุก 300 ms |
| Attack (ต่อยูนิต) | สลับเฟรมโจมตีและลด HP เป้าหมายตามความเร็วโจมตี |

การแก้ UI จาก thread อื่นทำผ่าน `SwingUtilities.invokeLater()`

เมื่อออกจากด่าน (Exit, Back to Map หรือปิดหน้าต่าง) `dispose()` จะเรียก `stopGame()` เพื่อหยุด game loop, thread สร้างศัตรู และเรียก `GameCharacter.destroy()` หยุด thread ของยูนิตทุกตัว ตอนกด restart ก็ใช้ `destroy()` เคลียร์ยูนิตเดิมเช่นกัน

### การเคลื่อนที่
`GameCharacter` เก็บตำแหน่ง x เป็น `double` (`exactX`) แล้วปัดเป็น `int` ตอนวาด เพราะ `velocity × 0.1` น้อยกว่า 1 พิกเซลต่อ tick ถ้าเก็บเป็น `int` ค่าจะถูกตัดทิ้งทุก tick และทุกตัวจะเดินเร็วเท่ากัน

### การชนและการต่อสู้
- ใช้ `Rectangle.intersects()` ของ `JLabel` แต่ละตัว (ขนาด sprite 80 × 80, ป้อม 400 × 400)
- เมื่อยีราฟชนศัตรู ทั้งสองฝ่ายจะหยุดเดินและเรียก `attack()` ใส่กัน
- เมื่อ HP ของฝ่ายใดหมด ฝ่ายที่ชนะจะกลับไปเดินต่อ
- เมื่อยูนิตชนป้อม จะเรียก `attackfortress()` และลด HP ป้อมเรื่อย ๆ

---

## การปรับสมดุลเกม

ค่าพลังทั้งหมดอยู่ในโค้ดของแต่ละด่าน แก้ได้โดยตรง

| ต้องการปรับ | ตำแหน่งในโค้ด |
|-------------|----------------|
| ค่าพลังยีราฟ | `ButtonListener.actionPerformed()` ใน `LevelN.java` เช่น `new TankGiraffe(1300, y, -3, 300, 20, 800)` = (x, y, ความเร็ว, HP, ดาเมจ, ความเร็วโจมตี) |
| Cooldown ปุ่ม | ตัวแปร `cooldownbc1`–`cooldownbc5` ใน `ButtonListener` (มิลลิวินาที) |
| ค่าพลังศัตรู | `spawnRandomEnemy()` ใน `LevelN.java` |
| ชนิดศัตรูที่เกิด | ตัวแปร `enemyType` ใน `spawnRandomEnemy()` (map1 สุ่ม `random.nextInt(3)`, map2/map3 สุ่ม `random.nextInt(5)`) |
| ความถี่การเกิดศัตรู | `randomspawn` ใน `enemySpawnThread` |
| HP ป้อม | อาร์กิวเมนต์ตัวที่ 3 ของ `new FortressGiraffe(...)` / `new FortressEnemy(...)` และ `sethprestart...()` ใน `Fortress.java` |
| ความเร็วโดยรวม | `speedFactor` ใน `GameCharacter.java` (ค่าเริ่มต้น 0.1) |

---

## ข้อจำกัดที่ทราบ

- **ไม่มีการบันทึกความคืบหน้า** ด่านที่ปลดล็อกจะหายเมื่อปิดเกม
- **ไม่มีเสียงและเพลงประกอบ**
- **ขนาดหน้าต่างคงที่** ไม่รองรับการปรับขนาดหรือความละเอียดจออื่น
- **โค้ดซ้ำกันระหว่างด่าน** `Level1`–`Level3` มีโครงสร้างเกือบเหมือนกัน ถ้าจะเพิ่มด่าน ควรแยกเป็นคลาสแม่ `Level` แล้วส่งค่าคอนฟิกของแต่ละด่านเข้าไป
