import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * วาด sprite ตัวละครทั้งหมดของ Giraffe Escape ใหม่ด้วย Java2D (เวกเตอร์ + antialias)
 * พิกัดทุกตัวออกแบบบนกริด 80x80 แล้ว scale ตามขนาดไฟล์จริง
 * ยีราฟหันซ้าย (เดินจากขวาไปซ้าย) ศัตรูหันขวา
 */
public class Art {
  // สีหลัก
  static final Color BODY = new Color(0xF6B92B), BODY_D = new Color(0xD99A16), SPOT = new Color(0x1E1A16),
      MUZZLE = new Color(0x5A3A12), LINE = new Color(0x3A2408), HORN = new Color(0x6B4A2B),
      TONGUE = new Color(0xE2493C), WHITE = Color.WHITE, INK = new Color(0x1B1B1B),
      ROBOT = new Color(0x9AA89F), ROBOT_D = new Color(0x7D8A82), GLASS = new Color(0x86E8F2),
      ORANGE = new Color(0xF0643A), METAL = new Color(0x6E6B6B), COAT = new Color(0x7C7979),
      SALMON = new Color(0xE86F6A), DINO = new Color(0x707275), DINO_D = new Color(0x5A5C5F),
      UFO = new Color(0xE8716B), DOME = new Color(0xB8EEF6), BEAM = new Color(0xD63AD6),
      FIRE = new Color(0xF39A1E), FIRE2 = new Color(0xFFD23F), SKIN = new Color(0xEDEDED);

  static Stroke stroke(double w) { return new BasicStroke((float) w, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); }
  static final Stroke OUT = stroke(1.4);

  interface Painter { void paint(Graphics2D g); }

  static BufferedImage draw(int size, Painter p) {
    BufferedImage im = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = im.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.scale(size / 80.0, size / 80.0);
    p.paint(g);
    g.dispose();
    return im;
  }

  static void fill(Graphics2D g, Shape s, Color c) { fill(g, s, c, LINE); }
  static void fill(Graphics2D g, Shape s, Color c, Color line) {
    g.setColor(c); g.fill(s);
    if (line != null) { g.setColor(line); g.setStroke(OUT); g.draw(s); }
  }
  static void spots(Graphics2D g, Shape clip, double[][] ellipses) {
    Shape old = g.getClip(); g.clip(clip); g.setColor(SPOT);
    for (double[] e : ellipses) g.fill(new Ellipse2D.Double(e[0], e[1], e[2], e[3]));
    g.setClip(old);
  }
  /** แขน/ขาแบบมีขอบ */
  static void limb(Graphics2D g, double x1, double y1, double x2, double y2, double w, Color c, Color line) {
    g.setColor(line); g.setStroke(stroke(w + 1.6)); g.draw(new Line2D.Double(x1, y1, x2, y2));
    g.setColor(c); g.setStroke(stroke(w)); g.draw(new Line2D.Double(x1, y1, x2, y2));
  }
  static void limb(Graphics2D g, double x1, double y1, double cx, double cy, double x2, double y2, double w, Color c, Color line) {
    QuadCurve2D q = new QuadCurve2D.Double(x1, y1, cx, cy, x2, y2);
    g.setColor(line); g.setStroke(stroke(w + 1.6)); g.draw(q);
    g.setColor(c); g.setStroke(stroke(w)); g.draw(q);
  }
  static void hoof(Graphics2D g, double x, double y) { g.setColor(SPOT); g.fill(new Ellipse2D.Double(x - 2.3, y - 1.6, 4.6, 3.2)); }
  static void speedLines(Graphics2D g, double x, double y, int n) {
    g.setColor(new Color(0x555555)); g.setStroke(stroke(1));
    for (int i = 0; i < n; i++) g.draw(new Line2D.Double(x, y + i * 3.5, x + 7 - i, y + i * 3.5 + 1));
  }

  // ------------------------------------------------------------------ หัวยีราฟ (ใช้ร่วมทุกสายพันธุ์)

  /**
   * หัวยีราฟหันซ้าย พื้นที่ราว (3..43, 1..37) ในพิกัดของตัวเอง จุดต่อคอประมาณ (28, 30)
   * mouth: 0 ปิด (แลบลิ้น), 1 อ้า, 2 อ้ากว้างกัดฟัน
   */
  static void head(Graphics2D g, double x, double y, double scale, double rot, int mouth) {
    AffineTransform saved = g.getTransform();
    g.translate(x, y);
    g.rotate(rot, 28, 30);
    g.translate(28, 30); g.scale(scale, scale); g.translate(-28, -30);
    for (double hx : new double[] {21, 27}) {
      g.setColor(HORN); g.setStroke(stroke(2.2));
      g.draw(new Line2D.Double(hx, 12, hx - 1, 4));
      g.fill(new Ellipse2D.Double(hx - 3.2, 1.5, 4.5, 4.5));
    }
    fill(g, new Ellipse2D.Double(4, 9, 10, 7), BODY);
    fill(g, new Ellipse2D.Double(33, 9, 10, 7), BODY);
    Path2D face = new Path2D.Double();
    face.moveTo(10, 15); face.curveTo(14, 7, 34, 7, 38, 14);
    face.curveTo(40, 19, 36, 28, 30, 33); face.lineTo(16, 33); face.curveTo(9, 28, 7, 20, 10, 15); face.closePath();
    fill(g, face, BODY);
    fill(g, new Ellipse2D.Double(3, 22, 22, 15), MUZZLE);
    g.setColor(SPOT);
    g.fill(new Ellipse2D.Double(8, 26, 2.4, 2)); g.fill(new Ellipse2D.Double(13.5, 26, 2.4, 2));
    if (mouth == 0) {
      g.setColor(TONGUE); g.fill(new Ellipse2D.Double(10, 31, 4.5, 3.4));
    } else {
      double h = mouth == 2 ? 6 : 4.5;
      RoundRectangle2D m = new RoundRectangle2D.Double(6, 29, 13, h, 3, 3);
      g.setColor(TONGUE); g.fill(m);
      g.setColor(WHITE);
      g.fill(new Rectangle2D.Double(7, 29, 11, 1.6)); g.fill(new Rectangle2D.Double(7, 29 + h - 1.6, 11, 1.6));
      g.setColor(LINE); g.setStroke(stroke(0.8)); g.draw(m);
    }
    g.setColor(SPOT);
    g.fill(new Ellipse2D.Double(17, 16, 3.6, 3.6)); g.fill(new Ellipse2D.Double(28, 15, 3.6, 3.6));
    g.setColor(WHITE);
    g.fill(new Ellipse2D.Double(17.8, 16.5, 1.2, 1.2)); g.fill(new Ellipse2D.Double(28.8, 15.5, 1.2, 1.2));
    if (mouth > 0) {
      g.setColor(LINE); g.setStroke(stroke(1.3));
      g.draw(new Line2D.Double(15.5, 13.5, 20.5, 15)); g.draw(new Line2D.Double(32.5, 12.5, 27.5, 14));
    }
    g.setTransform(saved);
  }

  /** คอ: จากจุดบนตัวไปถึงจุดต่อคอของหัว */
  static void neck(Graphics2D g, double bx, double by, double hx, double hy, double w) {
    Path2D n = new Path2D.Double();
    n.moveTo(bx - w / 2, by); n.lineTo(hx - w / 2 + 0.5, hy); n.lineTo(hx + w / 2 - 0.5, hy); n.lineTo(bx + w / 2, by); n.closePath();
    fill(g, n, BODY);
  }

  // ------------------------------------------------------------------ Default Giraffe

  static Painter defaultGiraffe(double legPhase, double bob, double hdx, double hdy, int mouth) {
    return g -> {
      g.translate(0, bob);
      double[][] legs = {{38, 1}, {44, -1}, {60, -1}, {66, 1}};
      for (int i = 0; i < 4; i++) {
        double hip = legs[i][0], sw = legPhase * legs[i][1] * 5;
        limb(g, hip, 62, hip + sw, 75, 2.8, (i % 2 == 1) ? BODY_D : BODY, LINE);
        hoof(g, hip + sw, 75);
      }
      neck(g, 43, 57, 28 + hdx, 27 + hdy, 8);
      RoundRectangle2D body = new RoundRectangle2D.Double(33, 47, 41, 18, 16, 16);
      g.setColor(BODY); g.fill(body);
      spots(g, body, new double[][] {{36, 44, 12, 7}, {53, 44, 11, 6}, {66, 47, 9, 9}, {45, 61, 14, 6}, {62, 61, 9, 5}});
      g.setColor(LINE); g.setStroke(OUT); g.draw(body);
      tail(g, 73, 52);
      head(g, hdx, hdy, 1, 0, mouth);
    };
  }

  static void tail(Graphics2D g, double x, double y) {
    g.setColor(LINE); g.setStroke(stroke(1.4));
    g.draw(new QuadCurve2D.Double(x, y, x + 4, y + 2, x + 3, y + 8));
    g.setColor(SPOT); g.fill(new Ellipse2D.Double(x + 1.5, y + 7, 3, 4));
  }

  // ------------------------------------------------------------------ Tank Giraffe: ตัวใหญ่ทรงกล่อง ขาสั้น

  static Painter tankGiraffe(double legPhase, double hx, double hy, double rot, int mouth) {
    return g -> {
      double[][] legs = {{40, 1}, {47, -1}, {64, -1}, {71, 1}};
      for (int i = 0; i < 4; i++) {
        double hip = legs[i][0], sw = legPhase * legs[i][1] * 3;
        limb(g, hip, 66, hip + sw, 76, 3, (i % 2 == 1) ? BODY_D : BODY, LINE);
        hoof(g, hip + sw, 76);
      }
      neck(g, 42, 48, 28 + hx, 28 + hy, 7);
      RoundRectangle2D body = new RoundRectangle2D.Double(32, 44, 46, 24, 8, 8);
      g.setColor(BODY); g.fill(body);
      spots(g, body, new double[][] {{30, 41, 16, 7}, {50, 41, 10, 5}, {64, 41, 16, 7}, {52, 51, 14, 9},
          {36, 64, 10, 6}, {66, 64, 10, 6}});
      g.setColor(LINE); g.setStroke(OUT); g.draw(body);
      // แผ่นเกราะ: เส้นหมุดบนหลัง
      g.setColor(LINE); g.setStroke(stroke(0.9));
      for (double rx = 36; rx < 76; rx += 6) g.fill(new Ellipse2D.Double(rx, 46.5, 1.6, 1.6));
      tail(g, 77, 50);
      head(g, hx, hy, 0.95, rot, mouth);
    };
  }

  // ------------------------------------------------------------------ Titan Giraffe: ยืนสองขาแบบมนุษย์

  static Painter titanGiraffe(double stride, double armRaise, boolean lunge, int mouth) {
    return g -> {
      if (lunge) { g.translate(8, 2); g.rotate(-0.28, 40, 76); }
      // ขา + รองเท้า
      double[][] legs = {{32, -stride}, {41, stride}};
      for (double[] l : legs) {
        limb(g, l[0], 60, l[0] + l[1] * 0.4, 68, l[0] + l[1], 75, 5, BODY, LINE);
        g.setColor(SPOT); g.fill(new Ellipse2D.Double(l[0] + l[1] - 5, 73, 9, 5));
      }
      // แขนหลัง (ยกง้างหมัดตอน armRaise)
      if (armRaise > 0) {
        limb(g, 44, 42, 54, 40, 58, 30, 3.8, BODY_D, LINE);
        fill(g, new Ellipse2D.Double(52, 18, 13, 11), BODY_D);
      } else {
        limb(g, 44, 42, 51, 47, 53, 54, 3.6, BODY_D, LINE);
        fill(g, new Ellipse2D.Double(50, 52, 7, 6), BODY_D);
      }
      // ลำตัว
      Path2D torso = new Path2D.Double();
      torso.moveTo(30, 40); torso.curveTo(29, 50, 30, 58, 32, 62); torso.lineTo(43, 62);
      torso.curveTo(45, 56, 46, 48, 44, 40); torso.closePath();
      g.setColor(BODY); g.fill(torso);
      spots(g, torso, new double[][] {{27, 42, 5, 8}, {42, 45, 5, 9}, {29, 55, 5, 6}, {41, 56, 4, 6}});
      g.setColor(LINE); g.setStroke(OUT); g.draw(torso);
      // กล้ามท้อง
      g.setColor(BODY_D); g.setStroke(stroke(0.9));
      g.draw(new Line2D.Double(37, 47, 37, 58)); g.draw(new Line2D.Double(34, 51, 40, 51)); g.draw(new Line2D.Double(34, 55, 40, 55));
      neck(g, 37, 41, 30, 28, 6);
      // แขนหน้า (แขนที่ต่อย)
      if (lunge) {
        limb(g, 31, 43, 20, 46, 9, 50, 4, BODY, LINE);
        fill(g, new Ellipse2D.Double(1, 44, 13, 11), BODY);
        g.setColor(LINE); g.setStroke(stroke(0.9));
        g.draw(new Line2D.Double(4, 47, 4, 52)); g.draw(new Line2D.Double(7, 46, 7, 53));
      } else {
        limb(g, 31, 43, 24, 48, 22, 54, 3.6, BODY, LINE);
        fill(g, new Ellipse2D.Double(17, 52, 7, 6), BODY);
      }
      head(g, 2, -2, 0.92, lunge ? 0.15 : 0, mouth);
    };
  }

  // ------------------------------------------------------------------ Bird Giraffe: ยีราฟมีปีก บิน

  static Painter birdGiraffe(double wing, double dx, double dy, double rot, int mouth, boolean speed) {
    return g -> {
      g.translate(dx, dy);
      g.rotate(rot, 36, 44);
      // ปีกหลัง
      wingShape(g, 46, 44, wing - 0.25, BODY_D);
      Ellipse2D body = new Ellipse2D.Double(28, 40, 30, 15);
      g.setColor(BODY); g.fill(body);
      spots(g, body, new double[][] {{30, 50, 8, 6}, {44, 51, 8, 5}, {52, 43, 7, 6}});
      g.setColor(LINE); g.setStroke(OUT); g.draw(body);
      // ขาพับ
      limb(g, 36, 53, 34, 57, 2.4, BODY, LINE); limb(g, 48, 53, 46, 57, 2.4, BODY, LINE);
      g.setColor(LINE); g.setStroke(stroke(1.4));
      g.draw(new QuadCurve2D.Double(57, 46, 62, 46, 63, 50));
      neck(g, 33, 44, 27 + 4, 31 + 9, 7);
      wingShape(g, 44, 43, wing, BODY);
      head(g, 4, 10, 0.78, 0, mouth);
      g.setTransform(new AffineTransform(g.getTransform()));
      if (speed) speedLines(g, 64, 40, 4);
    };
  }

  /** ปีกขนนก 3 แฉก หมุนตามมุม (ค่าบวก = ยกขึ้น) */
  static void wingShape(Graphics2D g, double x, double y, double angle, Color c) {
    AffineTransform t = g.getTransform();
    g.translate(x, y); g.rotate(-angle);
    Path2D w = new Path2D.Double();
    w.moveTo(0, 0); w.curveTo(6, -6, 14, -10, 24, -10); w.lineTo(19, -6.5); w.lineTo(23, -5.5);
    w.lineTo(17, -2.5); w.lineTo(20, -1); w.curveTo(12, 3, 5, 3, 0, 0); w.closePath();
    fill(g, w, c);
    g.setTransform(t);
  }

  // ------------------------------------------------------------------ Lizard Giraffe: ตัวอ้วนกลม ขาดำงอ

  static Painter lizardGiraffe(double step, double hx, double hy, double rot, int mouth, boolean spit) {
    return g -> {
      // ขาดำแบบกิ้งก่า
      double[][] feet = {{38, 74, -1}, {50, 76, 1}, {62, 74, 1}, {72, 76, -1}};
      for (double[] f : feet) {
        double sx = step * f[2] * 3;
        limb(g, f[0] + 2, 64, f[0] - 3 + sx, 70, f[0] + sx, f[1], 4, SPOT, SPOT);
        g.setColor(SPOT); g.fill(new Ellipse2D.Double(f[0] + sx - 6, f[1] - 2.5, 9, 4));
      }
      neck(g, 40, 50, 28 + hx, 28 + hy, 8);
      Ellipse2D body = new Ellipse2D.Double(32, 44, 46, 28);
      g.setColor(BODY); g.fill(body);
      spots(g, body, new double[][] {{48, 41, 10, 6}, {62, 42, 9, 6}, {70, 48, 10, 16}, {42, 64, 9, 6}});
      g.setColor(LINE); g.setStroke(OUT); g.draw(body);
      // เกล็ดบนตัว
      g.setColor(BODY_D); g.setStroke(stroke(0.9));
      for (double sx = 42; sx < 66; sx += 7) g.draw(new Arc2D.Double(sx, 54, 6, 5, 200, 140, Arc2D.OPEN));
      // หางกิ้งก่า
      limb(g, 76, 62, 83, 66, 78, 72, 3, BODY, LINE);
      head(g, hx, hy, 0.95, rot, mouth);
      if (spit) {
        Path2D f = new Path2D.Double();
        f.moveTo(9 + hx, 32 + hy); f.lineTo(-2, 30 + hy); f.lineTo(2, 35 + hy); f.lineTo(-3, 40 + hy);
        f.lineTo(9 + hx, 37 + hy); f.closePath();
        fill(g, f, FIRE, new Color(0xC2550E));
        g.setColor(FIRE2); g.fill(new Ellipse2D.Double(1, 33 + hy, 5, 3));
      }
    };
  }

  // ------------------------------------------------------------------ Human: มนุษย์ก้านไม้ขีด หันขวา

  static Painter human(double legA, double legB, int pose) {
    return g -> {
      double hx = 56, hy = 46;
      g.setColor(INK); g.setStroke(stroke(2.2));
      g.draw(new Line2D.Double(hx, 54, hx, 68));                       // ลำตัว
      g.draw(new Line2D.Double(hx, 68, hx - 7 + legA, 79));           // ขาหลัง
      g.draw(new Line2D.Double(hx, 68, hx + 7 + legB, 79));           // ขาหน้า
      g.draw(new Line2D.Double(hx, 58, hx - 9, 64));                  // แขนหลัง
      Color stick = new Color(0x8B5A2B);
      if (pose == 0) {                                                // เดิน: แขนหน้ายกขึ้น
        g.setColor(INK); g.draw(new QuadCurve2D.Double(hx, 58, hx + 8, 57, hx + 12, 50));
      } else if (pose == 1) {                                         // ง้างไม้
        g.setColor(INK); g.draw(new Line2D.Double(hx, 58, hx + 6, 50));
        g.setColor(stick); g.setStroke(stroke(2.6)); g.draw(new Line2D.Double(hx + 6, 50, hx + 2, 34));
      } else if (pose == 2) {                                         // ฟาดลง
        g.setColor(INK); g.draw(new Line2D.Double(hx, 58, hx + 9, 58));
        g.setColor(stick); g.setStroke(stroke(2.6)); g.draw(new Line2D.Double(hx + 9, 58, hx + 17, 46));
        motion(g, hx + 13, 38);
      } else {                                                        // ตามจังหวะตี
        g.setColor(INK); g.draw(new Line2D.Double(hx, 58, hx + 9, 62));
        g.setColor(stick); g.setStroke(stroke(2.6)); g.draw(new Line2D.Double(hx + 9, 62, hx + 18, 68));
        motion(g, hx + 14, 56);
      }
      fill(g, new Ellipse2D.Double(hx - 8, hy - 8, 16, 15), SKIN, INK);
      // หน้าโกรธ
      g.setColor(INK); g.setStroke(stroke(1.1));
      g.draw(new Line2D.Double(hx - 1, hy - 3.5, hx + 2, hy - 2.5)); g.draw(new Line2D.Double(hx + 7, hy - 3.5, hx + 4, hy - 2.5));
      g.fill(new Ellipse2D.Double(hx + 0.6, hy - 1.6, 1.8, 1.8)); g.fill(new Ellipse2D.Double(hx + 4.4, hy - 1.6, 1.8, 1.8));
      g.draw(new Line2D.Double(hx + 1, hy + 3, hx + 5, hy + 3.6));
    };
  }

  static void motion(Graphics2D g, double x, double y) {
    g.setColor(new Color(0x555555)); g.setStroke(stroke(1));
    g.draw(new Line2D.Double(x, y, x + 4, y - 3)); g.draw(new Line2D.Double(x + 3, y + 3, x + 7, y));
    g.draw(new Line2D.Double(x + 4, y + 7, x + 8, y + 5));
  }

  // ------------------------------------------------------------------ Robot Tank: หุ่นกล่องมีหน้าต่าง แขนก้ามปู

  static Painter robotTank(double legA, double legB, int pose) {
    return g -> {
      limb(g, 32, 64, 30 + legA, 72, 29 + legA, 78, 3.4, ORANGE, new Color(0xA8381A));
      limb(g, 44, 64, 46 + legB, 72, 48 + legB, 78, 3.4, ORANGE, new Color(0xA8381A));
      // แขนหลัง
      limb(g, 18, 42, 8, 44, 5, 56, 4, ROBOT_D, LINE);
      claw(g, 5, 58, Math.PI / 2);
      RoundRectangle2D box = new RoundRectangle2D.Double(14, 16, 46, 50, 20, 20);
      fill(g, box, ROBOT);
      g.setColor(ROBOT_D); g.setStroke(stroke(1));
      g.draw(new Line2D.Double(18, 60, 56, 60));
      RoundRectangle2D win = new RoundRectangle2D.Double(32, 28, 22, 26, 10, 10);
      fill(g, win, GLASS);
      // คนขับตัวจิ๋ว
      fill(g, new Ellipse2D.Double(39, 33, 8, 6), WHITE, INK);
      g.setColor(INK); g.setStroke(stroke(0.9));
      g.draw(new Line2D.Double(43, 39, 43, 46)); g.draw(new Line2D.Double(40, 42, 46, 42));
      g.fill(new Ellipse2D.Double(41, 35, 1.2, 1.2)); g.fill(new Ellipse2D.Double(44.5, 35, 1.2, 1.2));
      g.setColor(new Color(255, 255, 255, 150)); g.setStroke(stroke(1.6));
      g.draw(new Arc2D.Double(34, 30, 10, 10, 100, 70, Arc2D.OPEN));
      // แขนหน้า
      if (pose == 0) { limb(g, 58, 40, 68, 38, 70, 24, 4, ROBOT, LINE); claw(g, 70, 22, -Math.PI / 2); }
      else if (pose == 1) { limb(g, 58, 36, 64, 24, 66, 13, 4, ROBOT, LINE); claw(g, 66, 11, -Math.PI / 2); }
      else if (pose == 2) { limb(g, 56, 40, 48, 44, 36, 44, 4, ROBOT, LINE); claw(g, 34, 44, Math.PI); motion(g, 64, 32); }
      else { limb(g, 58, 42, 66, 46, 72, 48, 4, ROBOT, LINE); claw(g, 74, 48, 0); motion(g, 70, 36); }
    };
  }

  /** ก้ามรูปตัว U ปากหันไปทางมุม dir */
  static void claw(Graphics2D g, double x, double y, double dir) {
    AffineTransform t = g.getTransform();
    g.translate(x, y); g.rotate(dir);
    Path2D c = new Path2D.Double();
    c.moveTo(0, -4); c.curveTo(6, -6, 8, -3, 8, -3); c.moveTo(0, 4); c.curveTo(6, 6, 8, 3, 8, 3);
    g.setColor(new Color(0xA8381A)); g.setStroke(stroke(4)); g.draw(c);
    g.setColor(ORANGE); g.setStroke(stroke(2.4)); g.draw(c);
    g.setTransform(t);
  }

  // ------------------------------------------------------------------ Spaceship: จานบิน

  static Painter spaceship(double dy, double tilt, int beam) {
    return g -> {
      g.translate(0, dy);
      g.rotate(tilt, 38, 26);
      Path2D dome = new Path2D.Double();
      dome.moveTo(22, 27); dome.curveTo(20, 10, 34, 4, 44, 6); dome.curveTo(54, 8, 56, 18, 54, 27); dome.closePath();
      fill(g, dome, DOME, INK);
      // นักบิน
      fill(g, new Ellipse2D.Double(35, 14, 7, 6), WHITE, INK);
      g.setColor(INK); g.setStroke(stroke(0.9));
      g.draw(new Line2D.Double(38.5, 20, 37, 26)); g.draw(new Line2D.Double(38, 22, 42, 22));
      g.setColor(new Color(255, 255, 255, 170)); g.setStroke(stroke(1.6));
      g.draw(new Arc2D.Double(26, 9, 14, 14, 110, 60, Arc2D.OPEN));
      RoundRectangle2D saucer = new RoundRectangle2D.Double(12, 24, 54, 10, 10, 10);
      fill(g, saucer, UFO, INK);
      g.setColor(new Color(0xFFE08A));
      for (double lx = 18; lx < 64; lx += 9) g.fill(new Ellipse2D.Double(lx, 27.5, 3, 3));
      // ปืนใต้จาน
      fill(g, new RoundRectangle2D.Double(52, 33, 8, 4, 2, 2), new Color(0x8A8A8A), INK);
      if (beam == 1) {
        g.setColor(BEAM);
        double[][] dots = {{62, 38}, {65, 41}, {61, 43}, {67, 37}, {64, 45}, {69, 42}};
        for (double[] d : dots) g.fill(new Ellipse2D.Double(d[0], d[1], 1.8, 1.8));
      } else if (beam == 2) {
        Path2D b = new Path2D.Double();
        b.moveTo(58, 36); b.lineTo(80, 50); b.lineTo(74, 60); b.lineTo(56, 38); b.closePath();
        g.setColor(new Color(0xD63AD6)); g.fill(b);
        g.setColor(new Color(255, 190, 255, 200)); g.setStroke(stroke(1.6)); g.draw(new Line2D.Double(58, 37, 77, 54));
      }
    };
  }

  // ------------------------------------------------------------------ Titan Robot: หุ่นหัวกล่อง ใส่โค้ท ถือขวาน

  static Painter titanRobot(double legA, double legB, int pose) {
    return g -> {
      if (pose == 3) { g.translate(-4, 2); g.rotate(0.22, 34, 76); }
      // ขา
      limb(g, 28, 58, 26 + legA, 68, 22 + legA, 76, 6, COAT, INK);
      limb(g, 40, 58, 43 + legB, 68, 46 + legB, 76, 6, COAT, INK);
      fill(g, new RoundRectangle2D.Double(17 + legA, 74, 10, 4, 3, 3), METAL, INK);
      fill(g, new RoundRectangle2D.Double(42 + legB, 74, 10, 4, 3, 3), METAL, INK);
      // ขวาน (อยู่หลังตัวตอนง้าง)
      if (pose == 1) axe(g, 14, 48, 10, 24, false);
      if (pose == 2) axe(g, 22, 34, 12, 14, true);
      // แขนหลัง
      limb(g, 22, 36, 16, 44, 14, 50, 4.4, COAT, INK);
      fill(g, new RoundRectangle2D.Double(8, 47, 9, 6, 3, 3), SALMON, INK);
      // โค้ท
      Path2D coat = new Path2D.Double();
      coat.moveTo(20, 32); coat.lineTo(46, 32); coat.lineTo(48, 60); coat.lineTo(18, 60); coat.closePath();
      fill(g, coat, COAT, INK);
      g.setColor(INK); g.setStroke(stroke(1));
      g.draw(new Line2D.Double(33, 32, 33, 60));
      g.draw(new Line2D.Double(27, 32, 33, 40)); g.draw(new Line2D.Double(40, 32, 33, 40));
      g.draw(new Rectangle2D.Double(36, 46, 6, 5));
      g.fill(new Ellipse2D.Double(30.6, 44, 1.6, 1.6)); g.fill(new Ellipse2D.Double(30.6, 50, 1.6, 1.6));
      // หัวกล่อง
      Path2D top = new Path2D.Double();
      top.moveTo(20, 8); top.lineTo(26, 3); top.lineTo(54, 3); top.lineTo(48, 8); top.closePath();
      fill(g, top, new Color(0x8A8787), INK);
      fill(g, new Rectangle2D.Double(20, 8, 28, 24), METAL, INK);
      Path2D side = new Path2D.Double();
      side.moveTo(48, 8); side.lineTo(54, 3); side.lineTo(54, 26); side.lineTo(48, 32); side.closePath();
      fill(g, side, GLASS, INK);
      // นักบินในหน้าจอ
      fill(g, new Ellipse2D.Double(48.6, 11, 4.6, 4), WHITE, INK);
      g.setColor(INK); g.setStroke(stroke(0.8));
      g.draw(new Line2D.Double(51, 15, 51, 21)); g.draw(new Line2D.Double(49, 17.5, 53, 17));
      // แขนหน้า
      if (pose == 3) {
        limb(g, 44, 36, 54, 42, 60, 48, 4.4, COAT, INK);
        axe(g, 60, 50, 66, 66, false);
        fill(g, new RoundRectangle2D.Double(57, 45, 9, 6, 3, 3), SALMON, INK);
        motion(g, 66, 40);
      } else if (pose == 1 || pose == 2) {
        limb(g, 44, 36, 40, 30, pose == 1 ? 18 : 26, pose == 1 ? 46 : 34, 4.4, COAT, INK);
        fill(g, new RoundRectangle2D.Double(pose == 1 ? 12 : 20, pose == 1 ? 44 : 31, 9, 6, 3, 3), SALMON, INK);
      } else {
        limb(g, 44, 36, 50, 44, 52, 50, 4.4, COAT, INK);
        fill(g, new RoundRectangle2D.Double(48, 49, 9, 6, 3, 3), SALMON, INK);
      }
    };
  }

  /** ขวาน: ด้ามจาก (x1,y1) ถึง (x2,y2) ใบมีดที่ปลาย */
  static void axe(Graphics2D g, double x1, double y1, double x2, double y2, boolean big) {
    g.setColor(INK); g.setStroke(stroke(3.4)); g.draw(new Line2D.Double(x1, y1, x2, y2));
    g.setColor(new Color(0x6B6B6B)); g.setStroke(stroke(2)); g.draw(new Line2D.Double(x1, y1, x2, y2));
    double ang = Math.atan2(y2 - y1, x2 - x1);
    AffineTransform t = g.getTransform();
    g.translate(x2, y2); g.rotate(ang);
    double s = big ? 1.25 : 1;
    Path2D blade = new Path2D.Double();
    blade.moveTo(-4 * s, -2 * s); blade.lineTo(-8 * s, -10 * s); blade.quadTo(2 * s, -12 * s, 4 * s, -6 * s);
    blade.lineTo(4 * s, 6 * s); blade.quadTo(2 * s, 12 * s, -8 * s, 10 * s); blade.lineTo(-4 * s, 2 * s); blade.closePath();
    fill(g, blade, SALMON, INK);
    g.setTransform(t);
  }

  // ------------------------------------------------------------------ Lizard Robot: ไดโนเสาร์หุ่นยนต์

  static Painter lizardRobot(double step, int pose) {
    return g -> {
      g.translate(-4, 0);
      // ขาหลัง (ไกล)
      limb(g, 40, 62, 38 - step, 70, 42 - step, 76, 5, DINO_D, INK);
      fill(g, new RoundRectangle2D.Double(38 - step, 74, 11, 4, 3, 3), DINO_D, INK);
      // ลำตัว + หาง
      Path2D body = new Path2D.Double();
      body.moveTo(2, 40); body.curveTo(12, 46, 22, 46, 30, 44);
      body.curveTo(36, 36, 44, 32, 52, 34);
      body.lineTo(58, 46); body.curveTo(62, 56, 58, 66, 48, 68);
      body.curveTo(38, 70, 28, 66, 22, 58); body.curveTo(14, 54, 6, 48, 2, 40); body.closePath();
      fill(g, body, DINO, INK);
      g.setColor(DINO_D); g.setStroke(stroke(1));
      g.draw(new Line2D.Double(36, 50, 39, 58)); g.draw(new Line2D.Double(41, 49, 44, 58)); g.draw(new Line2D.Double(46, 50, 48, 58));
      // ขาหน้า (ใกล้)
      limb(g, 48, 62, 50 + step, 70, 46 + step, 76, 5, DINO, INK);
      fill(g, new RoundRectangle2D.Double(44 + step, 74, 12, 4, 3, 3), DINO, INK);
      // แขนเล็ก
      limb(g, 58, 50, 63, 52, 63, 56, 2.4, DINO, INK);
      // หัว
      AffineTransform t = g.getTransform();
      if (pose == 1) { g.translate(-1, -5); g.rotate(-0.22, 54, 36); }
      Path2D head = new Path2D.Double();
      head.moveTo(48, 36); head.curveTo(48, 22, 58, 18, 66, 22);
      head.curveTo(72, 25, 76, 30, 76, 35);
      if (pose >= 2) { head.lineTo(64, 36); head.lineTo(74, 44); head.curveTo(66, 48, 56, 48, 50, 44); }
      else { head.curveTo(70, 42, 58, 44, 50, 42); }
      head.closePath();
      fill(g, head, DINO, INK);
      if (pose >= 2) {
        Path2D mouth = new Path2D.Double();
        mouth.moveTo(64, 36); mouth.lineTo(76, 35); mouth.lineTo(74, 44); mouth.closePath();
        fill(g, mouth, new Color(0xD8231B), INK);
        g.setColor(WHITE); g.fill(new Polygon(new int[] {68, 70, 72}, new int[] {36, 39, 36}, 3));
      } else if (pose == 0) {
        Path2D mouth = new Path2D.Double();
        mouth.moveTo(62, 37); mouth.lineTo(75, 35); mouth.lineTo(70, 41); mouth.closePath();
        fill(g, mouth, new Color(0xD8231B), INK);
      } else {
        g.setColor(INK); g.setStroke(stroke(1.1)); g.draw(new QuadCurve2D.Double(62, 38, 69, 41, 75, 35));
      }
      // ครอบแก้วบนหัว + นักบิน
      Path2D visor = new Path2D.Double();
      visor.moveTo(54, 30); visor.curveTo(52, 18, 60, 12, 68, 16); visor.curveTo(72, 20, 72, 28, 68, 32); visor.closePath();
      fill(g, visor, GLASS, INK);
      fill(g, new Ellipse2D.Double(59, 20, 7, 7), WHITE, INK);
      g.setColor(new Color(255, 255, 255, 160)); g.setStroke(stroke(1.4));
      g.draw(new Arc2D.Double(55, 16, 8, 10, 100, 70, Arc2D.OPEN));
      g.setTransform(t);
      if (pose == 3) {
        Path2D fire = new Path2D.Double();
        fire.moveTo(74, 39); fire.curveTo(78, 34, 82, 32, 82, 32); fire.lineTo(80, 38); fire.lineTo(84, 42);
        fire.lineTo(80, 44); fire.lineTo(82, 50); fire.curveTo(78, 46, 76, 44, 74, 42); fire.closePath();
        fill(g, fire, FIRE, new Color(0xC2550E));
        g.setColor(FIRE2); g.fill(new Ellipse2D.Double(75, 38, 4, 4));
      }
    };
  }

  // ------------------------------------------------------------------ main

  static String OUTDIR;
  static void save(String rel, int size, Painter p) throws IOException {
    File f = new File(OUTDIR, rel); f.getParentFile().mkdirs();
    ImageIO.write(draw(size, p), "png", f);
  }

  public static void main(String[] a) throws Exception {
    OUTDIR = a[0];
    String P = "projectgame/";
    // Default
    double[] ph = {1, 0, -1, 0}, bob = {0, -1, 0, -1};
    for (int i = 0; i < 4; i++) save(P + "default/defaultwalk" + i + ".png", 80, defaultGiraffe(ph[i], bob[i], 0, 0, 0));
    save(P + "default/defaultattack.png", 80, defaultGiraffe(0, 0, -2, 1, 1));
    save(P + "default/defaultattack1.png", 80, defaultGiraffe(0, 0, -3, 2, 2));
    save(P + "default/defaultattack2.png", 80, defaultGiraffe(0, 0, -1, 0, 1));
    // Tank
    for (int i = 0; i < 4; i++) save(P + "tank/tankwalk" + i + ".png", 80, tankGiraffe(ph[i], 6, 4, 0, 0));
    save(P + "tank/tankattack0.png", 80, tankGiraffe(0, 8, 0, 0.45, 1));
    save(P + "tank/tankattack1.png", 80, tankGiraffe(0, 0, 10, -0.35, 2));
    save(P + "tank/tankattack2.png", 80, tankGiraffe(0, 4, 6, -0.1, 1));
    // Titan
    double[] st = {3, 0, -3};
    for (int i = 0; i < 3; i++) save(P + "titan/titanwalk" + i + ".png", 80, titanGiraffe(st[i], 0, false, 0));
    save(P + "titan/attack0.png", 80, titanGiraffe(0, 0, false, 1));
    save(P + "titan/attack1.png", 80, titanGiraffe(2, 1, false, 2));
    save(P + "titan/attack2.png", 80, titanGiraffe(4, 0, true, 2));
    // Bird
    double[] wings = {0.55, 0.1, -0.35};
    double[] bdy = {0, 1, 2};
    for (int i = 0; i < 3; i++) save(P + "birdgirafe/birdgiraftwalk" + i + ".png", 80, birdGiraffe(wings[i], 0, bdy[i], 0, 0, false));
    save(P + "birdgirafe/birdgiraffe0.png", 80, birdGiraffe(0.3, 0, 0, 0, 0, false));
    save(P + "birdgirafe/birdattack0.png", 80, birdGiraffe(0.6, 0, -2, 0.1, 1, false));
    save(P + "birdgirafe/birdattack1.png", 80, birdGiraffe(0.9, 2, -12, 0.2, 1, false));
    save(P + "birdgirafe/birdattack2.png", 80, birdGiraffe(-0.2, -6, 14, -0.45, 2, true));
    save(P + "birdgirafe/birdattack3.png", 80, birdGiraffe(0.2, 0, 2, 0, 1, false));
    // Lizard
    save(P + "lizard/wa0.png", 80, lizardGiraffe(1, 4, 4, 0, 0, false));
    save(P + "lizard/wa1.png", 80, lizardGiraffe(-1, 4, 5, 0, 0, false));
    save(P + "lizard/giraffelizard0.png", 80, lizardGiraffe(0, 4, 4, 0, 0, false));
    save(P + "lizard/lizardattack0.png", 80, lizardGiraffe(0, 8, -2, 0.4, 1, false));
    save(P + "lizard/lizardattack1.png", 80, lizardGiraffe(0, 0, 12, -0.25, 2, true));
    save(P + "lizard/lizardattack2.png", 80, lizardGiraffe(0, 4, 4, 0, 1, false));
    // Human
    double[][] hl = {{0, 0}, {-4, -3}, {3, -6}, {-2, -9}};
    for (int i = 0; i < 4; i++) save(P + "people/walk" + i + ".png", 80, human(hl[i][0], hl[i][1], 0));
    save(P + "people/attackpeople0.png", 80, human(0, 0, 1));
    save(P + "people/attackpeople1.png", 80, human(0, 0, 2));
    save(P + "people/attackpeople2.png", 80, human(0, 0, 3));
    // Robot tank
    double[][] rl = {{0, 0}, {-3, 3}, {2, -2}, {3, -4}};
    for (int i = 0; i < 4; i++) save(P + "robottank/robottank" + i + ".png", 80, robotTank(rl[i][0], rl[i][1], 0));
    save(P + "robottank/robotankattack0.png", 80, robotTank(0, 0, 1));
    save(P + "robottank/robotankattack1.png", 80, robotTank(0, 0, 2));
    save(P + "robottank/robotankattack2.png", 80, robotTank(0, 0, 3));
    // Spaceship
    save(P + "spaceship/spaceshipwalk0.png", 80, spaceship(0, 0, 0));
    save(P + "spaceship/spaceshipwalk1.png", 80, spaceship(-3, -0.06, 0));
    save(P + "spaceship/spaceshipwalk2.png", 80, spaceship(0, 0.04, 0));
    save(P + "spaceship/spaceshipattack0.png", 80, spaceship(0, 0, 0));
    save(P + "spaceship/spaceshipattack1.png", 80, spaceship(0, 0, 1));
    save(P + "spaceship/spaceshipattack2.png", 80, spaceship(0, 0, 2));
    save(P + "spaceship/spaceship attack3.png", 80, spaceship(0, 0.05, 2));
    // Titan robot
    double[][] tl = {{0, 0}, {4, -4}, {-3, 3}};
    for (int i = 0; i < 3; i++) save(P + "titanrobo/titanrobowalk" + i + ".png", 80, titanRobot(tl[i][0], tl[i][1], 0));
    save(P + "titanrobo/titanattack0.png", 80, titanRobot(0, 0, 1));
    save(P + "titanrobo/titanattack1.png", 80, titanRobot(-4, 4, 2));
    save(P + "titanrobo/titanattack2.png", 80, titanRobot(0, 0, 3));
    // Lizard robot
    save(P + "lizardrobo/lizardwalk0.png", 80, lizardRobot(3, 0));
    save(P + "lizardrobo/lizardwalk1.png", 80, lizardRobot(-3, 0));
    save(P + "lizardrobo/robolizardattack0.png", 80, lizardRobot(0, 1));
    save(P + "lizardrobo/robolizardattack1.png", 80, lizardRobot(0, 2));
    save(P + "lizardrobo/robolizardattack2.png", 80, lizardRobot(0, 3));
    // ไอคอนปุ่ม 60x60 (ใช้ท่ายืนของแต่ละตัว)
    save(P + "icon/defaulticon.png", 60, defaultGiraffe(0, 0, 0, 0, 0));
    save(P + "icon/tankicon.png", 60, tankGiraffe(0, 6, 4, 0, 0));
    save(P + "icon/titanicon.png", 60, titanGiraffe(0, 0, false, 0));
    save(P + "icon/birdicon.png", 60, birdGiraffe(0.4, 0, 6, 0, 0, false));
    save(P + "icon/lizardicon.png", 60, lizardGiraffe(0, 4, 4, 0, 0, false));
  }
}
