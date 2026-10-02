import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

/** วาดป้อม พื้นหลังด่าน หน้าเริ่มเกม และแผนที่เลือกด่านใหม่ (ขนาดตรงกับที่เกมวาดจริง ไม่ต้องยืดภาพ) */
public class Scenes {
  static final Color INK = new Color(0x2B2B2B);

  static Graphics2D canvas(BufferedImage im) {
    Graphics2D g = im.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    return g;
  }
  static Stroke stroke(double w) { return new BasicStroke((float) w, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); }
  static void fill(Graphics2D g, Shape s, Color c, double w) {
    g.setColor(c); g.fill(s);
    if (w > 0) { g.setColor(INK); g.setStroke(stroke(w)); g.draw(s); }
  }

  // ------------------------------------------------------------------ ป้อม 400x400

  /** บ้านยีราฟ: ป้อมฝั่งผู้เล่น (เกมวางที่ x=1350 จึงเห็นแค่ซีกซ้ายราว 250px) */
  static BufferedImage giraffeFortress() {
    BufferedImage im = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = canvas(im);
    // ผนัง
    RoundRectangle2D wall = new RoundRectangle2D.Double(40, 140, 136, 166, 10, 10);
    fill(g, wall, new Color(0xF6A65F), 4);
    g.setColor(new Color(0xE58F45)); g.setStroke(stroke(2));
    for (int y = 170; y < 300; y += 26) g.draw(new Line2D.Double(48, y, 168, y));
    // ลายจุดยีราฟบนผนัง
    g.setColor(new Color(0xC9762E));
    g.fill(new Ellipse2D.Double(52, 230, 22, 14)); g.fill(new Ellipse2D.Double(142, 200, 20, 16)); g.fill(new Ellipse2D.Double(150, 270, 16, 12));
    // หลังคา
    Path2D roof = new Path2D.Double();
    roof.moveTo(14, 104); roof.lineTo(110, 56); roof.lineTo(122, 56); roof.lineTo(200, 116);
    roof.lineTo(176, 146); roof.lineTo(40, 146); roof.closePath();
    fill(g, roof, new Color(0xF7E89A), 4);
    g.setColor(new Color(0xE6D06A)); g.setStroke(stroke(2.4));
    for (int i = 0; i < 5; i++) g.draw(new Line2D.Double(40 + i * 30, 140, 60 + i * 26, 92 - i * 3));
    // เขายีราฟบนหลังคา
    for (int hx : new int[] {98, 126}) {
      g.setColor(new Color(0x6B4A2B)); g.setStroke(stroke(7)); g.draw(new Line2D.Double(hx, 62, hx - 2, 34));
      g.fill(new Ellipse2D.Double(hx - 11, 22, 16, 16));
    }
    // หน้าต่าง
    Ellipse2D win = new Ellipse2D.Double(88, 160, 60, 50);
    fill(g, win, new Color(0x2DE6DA), 4);
    g.setColor(INK); g.setStroke(stroke(3.5));
    g.draw(new Line2D.Double(118, 160, 118, 210)); g.draw(new Line2D.Double(88, 185, 148, 185));
    g.setColor(new Color(255, 255, 255, 170)); g.setStroke(stroke(4)); g.draw(new Arc2D.Double(96, 167, 30, 30, 100, 60, Arc2D.OPEN));
    // ประตู
    Path2D door = new Path2D.Double();
    door.moveTo(80, 300); door.lineTo(80, 278); door.curveTo(80, 250, 130, 250, 130, 278); door.lineTo(130, 300); door.closePath();
    fill(g, door, new Color(0x9B6B3E), 4);
    g.setColor(INK); g.fill(new Ellipse2D.Double(118, 274, 6, 6));
    // พื้นดินใต้บ้าน
    Path2D ground = new Path2D.Double();
    ground.moveTo(14, 310); ground.curveTo(40, 296, 160, 294, 178, 310); ground.closePath();
    fill(g, ground, new Color(0x8C6239), 3);
    g.dispose();
    return im;
  }

  /** หอคอยศัตรู (เกมวางที่ x=-200 จึงเห็นแค่ซีกขวา) */
  static BufferedImage enemyFortress() {
    BufferedImage im = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = canvas(im);
    RoundRectangle2D tower = new RoundRectangle2D.Double(268, 150, 116, 250, 12, 12);
    fill(g, tower, new Color(0xF8E9B0), 4);
    // ก้อนอิฐ
    g.setColor(new Color(0xE3CC86)); g.setStroke(stroke(2));
    for (int y = 175, row = 0; y < 395; y += 22, row++) {
      g.draw(new Line2D.Double(272, y, 380, y));
      for (int x = 290 + (row % 2) * 18; x < 380; x += 36) g.draw(new Line2D.Double(x, y, x, y + 22));
    }
    g.setColor(new Color(0xF2D98E)); g.fill(new Rectangle2D.Double(270, 290, 112, 6));
    // มงกุฎเชิงเทิน
    Path2D crown = new Path2D.Double();
    crown.moveTo(250, 100); crown.lineTo(266, 92); crown.lineTo(280, 118); crown.lineTo(296, 118); crown.lineTo(292, 90);
    crown.lineTo(308, 86); crown.lineTo(318, 112); crown.lineTo(336, 112); crown.lineTo(340, 86); crown.lineTo(356, 90);
    crown.lineTo(358, 116); crown.lineTo(374, 92); crown.lineTo(396, 100); crown.lineTo(382, 150);
    crown.curveTo(340, 162, 300, 162, 268, 152); crown.closePath();
    fill(g, crown, new Color(0xF5BE0B), 4);
    g.setColor(new Color(0xE11D48)); g.fill(new Ellipse2D.Double(318, 128, 16, 16));
    g.setColor(new Color(0x2563EB)); g.fill(new Ellipse2D.Double(284, 130, 12, 12)); g.fill(new Ellipse2D.Double(356, 130, 12, 12));
    // หน้าต่าง
    Ellipse2D win = new Ellipse2D.Double(296, 192, 52, 72);
    fill(g, win, new Color(0x9EE3F5), 4);
    g.setColor(INK); g.setStroke(stroke(3.5));
    g.draw(new Line2D.Double(322, 192, 322, 264)); g.draw(new Line2D.Double(296, 228, 348, 228));
    // ประตู
    Path2D door = new Path2D.Double();
    door.moveTo(304, 400); door.lineTo(304, 352); door.curveTo(304, 318, 350, 318, 350, 352); door.lineTo(350, 400); door.closePath();
    fill(g, door, new Color(0xB86B45), 4);
    g.setColor(INK); g.fill(new Ellipse2D.Double(336, 366, 6, 6));
    g.dispose();
    return im;
  }

  // ------------------------------------------------------------------ พื้นหลังด่าน 1600x800

  static final int W = 1600, H = 800;

  static void hills(Graphics2D g, Color back, Color front, double horizon) {
    Path2D far = new Path2D.Double();
    far.moveTo(0, horizon - 20); far.curveTo(300, horizon - 70, 600, horizon - 60, 900, horizon - 30);
    far.curveTo(1200, horizon - 10, 1400, horizon - 60, W, horizon - 40); far.lineTo(W, H); far.lineTo(0, H); far.closePath();
    g.setColor(back); g.fill(far);
    Path2D near = new Path2D.Double();
    near.moveTo(0, horizon + 10); near.curveTo(400, horizon - 30, 900, horizon - 30, W, horizon + 6);
    near.lineTo(W, H); near.lineTo(0, H); near.closePath();
    g.setPaint(new GradientPaint(0, (float) horizon, front, 0, H, front.darker()));
    g.fill(near);
  }

  static void cloud(Graphics2D g, double x, double y, double s) {
    g.setColor(new Color(255, 255, 255, 235));
    Area a = new Area(new Ellipse2D.Double(x, y + 20 * s, 140 * s, 50 * s));
    a.add(new Area(new Ellipse2D.Double(x + 25 * s, y, 60 * s, 55 * s)));
    a.add(new Area(new Ellipse2D.Double(x + 65 * s, y - 12 * s, 55 * s, 60 * s)));
    g.fill(a);
  }

  static void flower(Graphics2D g, double x, double y, Color petal, double s) {
    g.setColor(new Color(0x1FA34A)); g.setStroke(stroke(3 * s));
    g.draw(new QuadCurve2D.Double(x, y + 22 * s, x - 2 * s, y + 10 * s, x, y));
    g.draw(new QuadCurve2D.Double(x, y + 18 * s, x - 10 * s, y + 10 * s, x - 14 * s, y + 12 * s));
    g.draw(new QuadCurve2D.Double(x, y + 18 * s, x + 10 * s, y + 10 * s, x + 14 * s, y + 12 * s));
    g.setColor(petal);
    for (int i = 0; i < 5; i++) {
      double a = i * Math.PI * 2 / 5;
      g.fill(new Ellipse2D.Double(x + Math.cos(a) * 4 * s - 3.5 * s, y + Math.sin(a) * 4 * s - 3.5 * s, 7 * s, 7 * s));
    }
    g.setColor(new Color(0xFFE36E)); g.fill(new Ellipse2D.Double(x - 2.5 * s, y - 2.5 * s, 5 * s, 5 * s));
  }

  static void tuft(Graphics2D g, double x, double y, Color c) {
    g.setColor(c); g.setStroke(stroke(3));
    g.draw(new QuadCurve2D.Double(x, y, x - 4, y - 10, x - 10, y - 14));
    g.draw(new QuadCurve2D.Double(x, y, x + 1, y - 12, x + 2, y - 18));
    g.draw(new QuadCurve2D.Double(x, y, x + 6, y - 9, x + 12, y - 12));
  }

  static void scatter(Graphics2D g, long seed, int n, boolean flowers, Color tuftColor) {
    Random r = new Random(seed);
    Color[] petals = {new Color(0xFF6B8B), new Color(0x60A5FA), new Color(0xFFFFFF), new Color(0xC084FC), new Color(0xF87171)};
    for (int i = 0; i < n; i++) {
      double x = 30 + r.nextDouble() * (W - 60), y = 500 + r.nextDouble() * 280;
      if (flowers && r.nextInt(3) == 0) flower(g, x, y, petals[r.nextInt(petals.length)], 1 + (y - 500) / 300);
      else tuft(g, x, y, tuftColor);
    }
  }

  static void bird(Graphics2D g, double x, double y, double s) {
    g.setColor(INK); g.setStroke(stroke(4 * s));
    g.draw(new QuadCurve2D.Double(x - 22 * s, y, x - 11 * s, y - 12 * s, x, y));
    g.draw(new QuadCurve2D.Double(x, y, x + 11 * s, y - 12 * s, x + 22 * s, y));
  }

  static void sun(Graphics2D g, double cx, double cy, double r, Color c, Color ray) {
    g.setColor(ray); g.setStroke(stroke(8));
    for (int i = 0; i < 12; i++) {
      double a = i * Math.PI / 6;
      g.draw(new Line2D.Double(cx + Math.cos(a) * (r + 18), cy + Math.sin(a) * (r + 18), cx + Math.cos(a) * (r + 48), cy + Math.sin(a) * (r + 48)));
    }
    g.setPaint(new RadialGradientPaint((float) cx, (float) cy, (float) r, new float[] {0f, 1f}, new Color[] {c.brighter(), c}));
    g.fill(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r));
  }

  static BufferedImage bg1() {
    BufferedImage im = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = canvas(im);
    g.setPaint(new GradientPaint(0, 0, new Color(0x5FD3F5), 0, 480, new Color(0xB8F2FF))); g.fillRect(0, 0, W, H);
    sun(g, 1170, 140, 90, new Color(0xFFD60A), new Color(0xFFE45C));
    cloud(g, 120, 120, 1.6); cloud(g, 640, 90, 1.4); cloud(g, 1380, 170, 1.2);
    hills(g, new Color(0x9BE15D), new Color(0xB9E92A), 470);
    scatter(g, 1, 40, true, new Color(0x1FA34A));
    g.dispose();
    return im;
  }

  static BufferedImage bg2() {
    BufferedImage im = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = canvas(im);
    g.setPaint(new GradientPaint(0, 0, new Color(0xF59E5B), 0, 460, new Color(0xFFD38A))); g.fillRect(0, 0, W, H);
    sun(g, 800, 470, 210, new Color(0xFF7A1A), new Color(0xFF9A3C));
    cloud(g, 140, 90, 1.1); cloud(g, 1020, 40, 1.2); cloud(g, 1280, 230, 1.0); cloud(g, 280, 290, 0.9);
    bird(g, 400, 220, 1.2); bird(g, 850, 110, 1.4); bird(g, 1300, 160, 1);
    hills(g, new Color(0x8BC34A), new Color(0xA5E400), 470);
    // ต้นไม้
    g.setColor(new Color(0x8D5A3B)); g.setStroke(stroke(1));
    Path2D trunk = new Path2D.Double();
    trunk.moveTo(1210, 470); trunk.lineTo(1222, 360); trunk.lineTo(1200, 330); trunk.lineTo(1230, 340); trunk.lineTo(1250, 320);
    trunk.lineTo(1244, 360); trunk.lineTo(1258, 470); trunk.closePath();
    fill(g, trunk, new Color(0xA86B45), 0);
    g.setColor(new Color(0x16A34A));
    Area crown = new Area(new Ellipse2D.Double(1150, 280, 110, 90));
    crown.add(new Area(new Ellipse2D.Double(1200, 250, 120, 100))); crown.add(new Area(new Ellipse2D.Double(1240, 300, 100, 80)));
    g.fill(crown);
    g.setColor(new Color(0x22C55E)); g.fill(new Ellipse2D.Double(1210, 262, 70, 50));
    scatter(g, 2, 40, true, new Color(0x16A34A));
    g.dispose();
    return im;
  }

  static BufferedImage bg3() {
    BufferedImage im = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = canvas(im);
    g.setPaint(new GradientPaint(0, 0, new Color(0x9FE6EE), 0, 460, new Color(0xD9FAFC))); g.fillRect(0, 0, W, H);
    sun(g, 1560, 50, 140, new Color(0xFFE600), new Color(0xFFE94D));
    // รุ้ง
    Color[] rainbow = {new Color(0xEF4444), new Color(0xF97316), new Color(0xFACC15), new Color(0x22C55E),
        new Color(0x67E8F9), new Color(0x4F46E5), new Color(0xA855F7)};
    for (int i = 0; i < rainbow.length; i++) {
      double r = 400 - i * 24;
      g.setColor(rainbow[i]); g.setStroke(new BasicStroke(25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
      g.draw(new Arc2D.Double(780 - r, 470 - r * 0.9, 2 * r, 2 * r * 0.9, 0, 180, Arc2D.OPEN));
    }
    cloud(g, 300, 90, 1.5); cloud(g, 1040, 140, 1.4); cloud(g, 120, 330, 1.0);
    hills(g, new Color(0xA3E635), new Color(0xC4F21A), 470);
    scatter(g, 3, 45, false, new Color(0x16A34A));
    g.dispose();
    return im;
  }

  // ------------------------------------------------------------------ หน้าเริ่มเกม + แผนที่ (1000x800)

  static void sprite(Graphics2D g, Art.Painter p, double x, double y, double size, double rot) {
    AffineTransform t = g.getTransform();
    g.translate(x, y); g.rotate(rot, size / 2, size / 2); g.scale(size / 80.0, size / 80.0);
    p.paint(g);
    g.setTransform(t);
  }

  static BufferedImage startPage() {
    BufferedImage im = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = canvas(im);
    g.setPaint(new RadialGradientPaint(500, 380, 650, new float[] {0f, 1f}, new Color[] {new Color(0xFFF6B8), new Color(0xF2E37E)}));
    g.fillRect(0, 0, 1000, 800);
    // ลายจุดยีราฟจาง ๆ เป็นพื้นหลัง
    Random r = new Random(7);
    g.setColor(new Color(0xE8C860));
    for (int i = 0; i < 26; i++) {
      double x = r.nextDouble() * 1000, y = r.nextDouble() * 800, w = 40 + r.nextDouble() * 60;
      g.fill(new Ellipse2D.Double(x, y, w, w * (0.6 + r.nextDouble() * 0.3)));
    }
    // ชื่อเกม
    Font f = new Font("DejaVu Sans", Font.BOLD, 88);
    drawTitle(g, "GIRAFFE", 500, 170, f);
    drawTitle(g, "ESCAPE", 500, 285, f);
    // ตัวละคร
    sprite(g, Art.titanGiraffe(0, 0, false, 0), 30, 70, 270, -0.08);
    sprite(g, Art.birdGiraffe(0.5, 0, 0, 0.05, 0, false), 720, 110, 250, 0);
    sprite(g, Art.tankGiraffe(0, 6, 4, 0, 0), 120, 450, 280, -0.12);
    sprite(g, Art.lizardGiraffe(0, 4, 4, 0, 0, false), 500, 470, 260, 0);
    sprite(g, Art.defaultGiraffe(1, 0, 0, 0, 0), 740, 420, 250, 0.06);
    g.dispose();
    return im;
  }

  static void drawTitle(Graphics2D g, String s, double cx, double y, Font f) {
    g.setFont(f);
    FontMetrics fm = g.getFontMetrics();
    double spacing = 18, w = 0;
    for (char c : s.toCharArray()) w += fm.charWidth(c) + spacing;
    w -= spacing;
    double x = cx - w / 2;
    for (char c : s.toCharArray()) {
      String ch = String.valueOf(c);
      Shape glyph = f.createGlyphVector(g.getFontRenderContext(), ch).getOutline((float) x, (float) y);
      g.setColor(new Color(0x2B1A08)); g.setStroke(stroke(10)); g.draw(glyph);
      g.setColor(new Color(0xF6B92B)); g.fill(glyph);
      g.setColor(new Color(0x5A3A12)); g.setStroke(stroke(2.5)); g.draw(glyph);
      x += fm.charWidth(c) + spacing;
    }
  }

  static BufferedImage mapPage() {
    BufferedImage im = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = canvas(im);
    g.setColor(new Color(0xF4F6DF)); g.fillRect(0, 0, 1000, 800);
    // ทะเล
    g.setColor(new Color(0xDCEFD8));
    for (int i = 0; i < 9; i++) {
      g.setStroke(stroke(2)); g.setColor(new Color(0xBFD9C0));
      double y = 90 + i * 80;
      g.draw(new QuadCurve2D.Double(30, y, 60, y - 10, 90, y));
    }
    // เกาะ
    Path2D island = new Path2D.Double();
    island.moveTo(250, 260); island.curveTo(240, 200, 330, 190, 370, 200);
    island.curveTo(400, 170, 460, 180, 520, 220); island.curveTo(560, 240, 600, 240, 598, 262);
    island.curveTo(596, 290, 560, 300, 560, 340); island.curveTo(560, 380, 620, 380, 700, 390);
    island.curveTo(760, 400, 750, 460, 740, 500); island.curveTo(730, 560, 730, 610, 700, 640);
    island.curveTo(660, 690, 560, 700, 500, 705); island.curveTo(440, 708, 420, 640, 350, 640);
    island.curveTo(280, 640, 240, 630, 240, 560); island.curveTo(240, 480, 220, 440, 235, 400);
    island.curveTo(250, 350, 250, 300, 250, 260); island.closePath();
    g.setColor(new Color(0xBFE3B4)); g.setStroke(stroke(28)); g.draw(island);
    g.setPaint(new GradientPaint(300, 200, new Color(0x7FB685), 700, 700, new Color(0x5E9468))); g.fill(island);
    g.setColor(INK); g.setStroke(stroke(4)); g.draw(island);
    // ต้นไม้เล็ก ๆ บนเกาะ
    Random r = new Random(5);
    for (int i = 0; i < 14; i++) {
      double x = 290 + r.nextDouble() * 400, y = 240 + r.nextDouble() * 420;
      if (!island.contains(x, y + 20) || Math.abs(x - 500) + Math.abs(y - 420) < 60) continue;
      g.setColor(new Color(0x3F7A4A)); g.fill(new Ellipse2D.Double(x, y, 26, 20));
      g.setColor(new Color(0x4E8F59)); g.fill(new Ellipse2D.Double(x + 4, y - 2, 16, 12));
    }
    // เส้นทางด่าน
    double[][] pts = {{502, 273}, {383, 413}, {636, 572}};
    g.setColor(new Color(0xE11D48)); g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] {14, 14}, 0));
    g.draw(new QuadCurve2D.Double(pts[0][0], pts[0][1], 400, 300, pts[1][0], pts[1][1]));
    g.draw(new QuadCurve2D.Double(pts[1][0], pts[1][1], 430, 540, pts[2][0], pts[2][1]));
    Font label = new Font("Noto Sans Thai", Font.BOLD, 18);
    g.setFont(label);
    for (int i = 0; i < 3; i++) {
      fill(g, new Ellipse2D.Double(pts[i][0] - 16, pts[i][1] - 16, 32, 32), new Color(0xE11D48), 3);
      g.setColor(Color.WHITE);
      String n = String.valueOf(i + 1);
      g.drawString(n, (float) (pts[i][0] - g.getFontMetrics().stringWidth(n) / 2.0), (float) (pts[i][1] + 7));
    }
    // ธงเส้นชัย
    g.setColor(INK); g.setStroke(stroke(4)); g.draw(new Line2D.Double(652, 572, 652, 450));
    Path2D flag = new Path2D.Double();
    flag.moveTo(652, 452); flag.curveTo(670, 444, 685, 460, 700, 452); flag.lineTo(700, 500); flag.curveTo(685, 508, 670, 492, 652, 500); flag.closePath();
    fill(g, flag, new Color(0x4F6BFF), 3);
    // คำอธิบายยีราฟ
    String[][] info = {
        {"Default Giraffe", "เป็น Giraffe ที่มีความสมดุล"},
        {"Tank Giraffe", "จะมี Hp เยอะกว่า Default Giraffe"},
        {"Bird Giraffe", "จะมีความเร็ว และตีไวกว่า Default Giraffe"},
        {"Titan Giraffe", "จะมีพลังโจมตี และเลือดเยอะมาก แต่โจมตีช้า"},
        {"Lizard Giraffe", "เดินเร็วและโจมตีไว แต่มีเลือดน้อย"}};
    Art.Painter[] icons = {Art.defaultGiraffe(0, 0, 0, 0, 0), Art.tankGiraffe(0, 6, 4, 0, 0),
        Art.birdGiraffe(0.4, 0, 4, 0, 0, false), Art.titanGiraffe(0, 0, false, 0), Art.lizardGiraffe(0, 4, 4, 0, 0, false)};
    RoundRectangle2D panel = new RoundRectangle2D.Double(600, 22, 384, 290, 18, 18);
    g.setColor(new Color(255, 255, 255, 210)); g.fill(panel);
    g.setColor(new Color(0xC9CFAE)); g.setStroke(stroke(2)); g.draw(panel);
    for (int i = 0; i < info.length; i++) {
      double y = 32 + i * 56;
      sprite(g, icons[i], 608, y, 52, 0);
      g.setColor(new Color(0x3A2408)); g.setFont(new Font("Noto Sans Thai", Font.BOLD, 15));
      g.drawString(info[i][0], 666, (float) y + 22);
      g.setColor(new Color(0x444444)); g.setFont(new Font("Noto Sans Thai", Font.PLAIN, 13));
      g.drawString(info[i][1], 666, (float) y + 42);
    }
    g.dispose();
    return im;
  }

  public static void main(String[] a) throws Exception {
    File out = new File(a[0]);
    new File(out, "projectgame/map").mkdirs();
    ImageIO.write(giraffeFortress(), "png", new File(out, "projectgame/Giraffefortress.png"));
    ImageIO.write(enemyFortress(), "png", new File(out, "projectgame/enemyfortress.png"));
    ImageIO.write(bg1(), "png", new File(out, "bgmap1.png"));
    ImageIO.write(bg2(), "png", new File(out, "bgmap2.png"));
    ImageIO.write(bg3(), "png", new File(out, "bgmap3.png"));
    ImageIO.write(startPage(), "png", new File(out, "startpage.png"));
    ImageIO.write(mapPage(), "png", new File(out, "projectgame/map/map1.png"));
  }
}
