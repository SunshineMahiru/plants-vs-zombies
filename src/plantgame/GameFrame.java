package plantgame;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

public class GameFrame {
    // ========== 游戏场地常量 ==========
    // back0.png: 1400x600
    // 草坪区域: x≈260-980, y≈80-570
    // 房子/石板区域: x≈0-250
    // 马路区域: x≈990-1400
    public static final int SCREEN_W = 900;       // 屏幕可见宽度
    public static final int SCREEN_H = 600;       // 屏幕高度（客户区高度）
    public static final int BG_W = 1400;           // 背景总宽度
    public static final int BG_H = 600;            // 背景总高度

    // 草坪网格参数（基于back0.png实际草坪位置分析）
    // 草坪5行9列
    // 行方向：草坪y=80~570，5行不均匀，用实际行中心定位
    // 列方向：草坪x=260~980，9列均匀分布
    public static final int LAWN_START_X = 260;    // 草坪左边界x
    public static final int LAWN_START_Y = 80;     // 草坪上边界y
    public static final int CELL_W = 80;           // 网格宽度
    public static final int CELL_H = 98;           // 网格高度（5行均匀分布：(570-80)/5≈98）
    public static final int LAWN_ROWS = 5;
    public static final int LAWN_COLS = 9;
    public static final int CAR_W = 70;
    public static final int CAR_H = 57;

    // 每行草坪的Y中心坐标（基于back0.png亮度分析）
    public static final int[] ROW_CENTER_Y = {124, 222, 325, 420, 520};

    // 镜头参数
    public static double cameraX = 0;              // 镜头左边界在背景中的x坐标
    public static final double CAMERA_MIN = 0;     // 镜头最左位置
    public static final double CAMERA_MAX = BG_W - SCREEN_W; // 镜头最右位置=500
    // 初始镜头位置：让草坪居中，左侧露出部分房子，右侧露出部分马路
    // 草坪中心x = 260 + 9*80/2 = 620, 屏幕中心 = 450, cameraX = 620-450 = 170
    public static final double CAMERA_DEFAULT = 170;

    // 工具栏参数
    public static final double TOOLBAR_SCALE = 560.0 / 712.0;
    public static final int TOOLBAR_W = (int)(712 * TOOLBAR_SCALE);
    public static final int TOOLBAR_H = (int)(102 * TOOLBAR_SCALE);
    public static final int TOOLBAR_X = 10;
    public static final int TOOLBAR_Y_BASE = 0;    // 工具栏在屏幕顶部
    public static final int CARD_W = (int)(64 * TOOLBAR_SCALE);
    public static final int CARD_H = (int)(90 * TOOLBAR_SCALE);
    public static int[] cardX = {
        (int)(86 * TOOLBAR_SCALE) + TOOLBAR_X,
        (int)(152 * TOOLBAR_SCALE) + TOOLBAR_X,
        (int)(216 * TOOLBAR_SCALE) + TOOLBAR_X,
        (int)(279 * TOOLBAR_SCALE) + TOOLBAR_X
    };
    public static int cardY = (int)(6 * TOOLBAR_SCALE);

    // ========== 游戏对象列表 ==========
    static ArrayList<Glass> glass = new ArrayList<>();
    static ArrayList<Car> car = new ArrayList<>();
    static ArrayList<Card> card = new ArrayList<>();
    static ArrayList<Flower> flower = new ArrayList<>();
    static ArrayList<Wandou> wandou = new ArrayList<>();
    static ArrayList<Hanbing> hanbing = new ArrayList<>();
    static ArrayList<Jianguo> jianguo = new ArrayList<>();
    static ArrayList<Sun> sunings = new ArrayList<>();
    static ArrayList<Bullet> bullet = new ArrayList<>();
    static ArrayList<QiZhi> qizhi = new ArrayList<>();
    static ArrayList<TieTong> tietong = new ArrayList<>();
    static ArrayList<BaoZhi> baozhi = new ArrayList<>();
    static ArrayList<GanLan> ganlan = new ArrayList<>();
    static Shove shove;

    // ========== 游戏状态 ==========
    static boolean backgame, backmenu, restart;
    static int loadtime;
    static int op;
    static int grade;
    static boolean live;
    static GameUtil util;
    static Image img[];
    static Image suns[];
    static Image flowers[];
    static Image wandous[];
    static Image hanbings[];
    static Image jianguos[];
    static Image bullets[];
    static Image qizhis[];
    static Image tietongs[];
    static Image ganlans[];
    static Image baozhis[];
    static Image lawnMowerImg;
    static int sun;
    static int num;           // 已消灭僵尸数
    static Date startTime;
    static Date stopTime;
    static Date start;
    static Date stop;
    static int lastSkySunSecond;
    static Sun suning;

    // ========== 初始化 ==========
    static {
        // 初始化草坪网格（5行9列）
        // 每个格子的位置：x = LAWN_START_X + CELL_W * j, y = ROW_CENTER_Y[i] - CELL_H/2
        for (int i = 0; i < LAWN_ROWS; i++) {
            for (int j = 0; j < LAWN_COLS; j++) {
                glass.add(new Glass(LAWN_START_X + CELL_W * j, ROW_CENTER_Y[i] - CELL_H / 2));
            }
        }

        grade = 1;
        sun = 50;
        loadtime = 0;
        op = 2;
        lastSkySunSecond = -1;
        live = false;
        util = new GameUtil();
        util.loadBGM("sounds/bgm8.wav");

        // 加载图片资源
        img = new Image[21];
        wandous = new Image[43];
        hanbings = new Image[39];
        jianguos = new Image[115];
        bullets = new Image[2];
        qizhis = new Image[134];
        tietongs = new Image[239];
        ganlans = new Image[239];
        baozhis = new Image[235];
        lawnMowerImg = GameUtil.getImage("GameFrame/LawnMower.gif");

        for (int i = 0; i < img.length; i++) {
            img[i] = GameUtil.getImage("GameFrame/back" + i + ".png");
        }
        suns = GameUtil.getGifFrames("Sun/Sun.gif");
        flowers = GameUtil.getGifFrames("flower/SunFlower.gif");
        for (int i = 0; i < wandous.length; i++) {
            wandous[i] = GameUtil.getImage("wandou/wandou (" + (i + 1) + ").png");
        }
        for (int i = 0; i < hanbings.length; i++) {
            hanbings[i] = GameUtil.getImage("hanbing/hanbing (" + (i + 1) + ").png");
        }
        for (int i = 0; i < jianguos.length; i++) {
            jianguos[i] = GameUtil.getImage("jianguo/jianguo (" + (i + 1) + ").png");
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = GameUtil.getImage("Bullets/bullet" + (i + 1) + ".png");
        }
        for (int i = 0; i < qizhis.length; i++) {
            qizhis[i] = GameUtil.getImage("Zombies/qizhi/qizhi (" + (i + 1) + ").png");
        }
        for (int i = 0; i < tietongs.length; i++) {
            tietongs[i] = GameUtil.getImage("Zombies/tietong/tietong (" + (i + 1) + ").png");
        }
        for (int i = 0; i < ganlans.length; i++) {
            ganlans[i] = GameUtil.getImage("Zombies/ganlan/ganlan (" + (i + 1) + ").png");
        }
        for (int i = 0; i < baozhis.length; i++) {
            baozhis[i] = GameUtil.getImage("Zombies/baozhi/baozhi (" + (i + 1) + ").png");
        }

        // 初始化小推车（放在房子门前石板上，每行一个）
        // 石板区域 x≈140-240，小推车放在 x=200 附近
        createLawnMowers();

        // 初始化铲子
        double sc = TOOLBAR_SCALE;
        int shovelX = (int)(630 * sc) + TOOLBAR_X;
        int shovelY = (int)(10 * sc) + TOOLBAR_Y_BASE;
        int shovelW = (int)(80 * sc);
        int shovelH = (int)(80 * sc);
        shove = new Shove(shovelX, shovelY, shovelW, shovelH, 0, img[5]);

        // 初始化卡片
        card.add(new Card(cardX[0], cardY + TOOLBAR_Y_BASE,
            CARD_W, CARD_H, 50, IntroAnimation.cardImages[0], img[13]));
        card.add(new Card(cardX[1], cardY + TOOLBAR_Y_BASE,
            CARD_W, CARD_H, 100, IntroAnimation.cardImages[1], img[15]));
        card.add(new Card(cardX[2], cardY + TOOLBAR_Y_BASE,
            CARD_W, CARD_H, 175, IntroAnimation.cardImages[2], img[17]));
        card.add(new Card(cardX[3], cardY + TOOLBAR_Y_BASE,
            CARD_W, CARD_H, 50, IntroAnimation.cardImages[3], img[19]));
    }

    // ========== 坐标转换 ==========
    // 屏幕坐标转背景坐标
    public static int screenToBgX(int screenX) {
        return screenX + (int)cameraX;
    }
    // 背景坐标转屏幕坐标
    public static int bgToScreenX(int bgX) {
        return bgX - (int)cameraX;
    }

    // ========== 绘制背景（带镜头偏移） ==========
    public static void drawBackground(Graphics g) {
        if (img[0] != null) {
            int drawX = (int)Math.round(-cameraX);
            int imgW = img[0].getWidth(null);
            int imgH = img[0].getHeight(null);
            int srcX = Math.max(0, -drawX);
            int srcW = Math.min(imgW - srcX, SCREEN_W);
            int dstX = Math.max(0, drawX);
            if (srcW > 0 && srcX < imgW) {
                g.drawImage(img[0],
                    dstX, 0, dstX + srcW, imgH,
                    srcX, 0, srcX + srcW, imgH, null);
            }
        }
    }

    // ========== 绘制工具栏 ==========
    public static void drawToolbar(Graphics g) {
        if (IntroAnimation.toolbarImg != null) {
            g.drawImage(IntroAnimation.toolbarImg,
                TOOLBAR_X, TOOLBAR_Y_BASE,
                TOOLBAR_X + TOOLBAR_W, TOOLBAR_Y_BASE + TOOLBAR_H,
                0, 0, IntroAnimation.toolbarImg.getWidth(null), IntroAnimation.toolbarImg.getHeight(null), null);
        }
        // 阳光数值
        Font font = g.getFont();
        g.setFont(new Font("微软雅黑", Font.BOLD + Font.ITALIC, 25));
        String sunStr = sun + "";
        int sunBoxX = TOOLBAR_X + (int)(15 * TOOLBAR_SCALE);
        int sunBoxW = (int)(55 * TOOLBAR_SCALE);
        int sunBoxY = TOOLBAR_Y_BASE + (int)(75 * TOOLBAR_SCALE);
        int sunBoxH = (int)(25 * TOOLBAR_SCALE);
        java.awt.FontMetrics fm = g.getFontMetrics();
        int textW = fm.stringWidth(sunStr);
        int textH = fm.getAscent();
        int textX = sunBoxX + (sunBoxW - textW) / 2;
        int textY = sunBoxY + (sunBoxH + textH) / 2 - 2;
        g.drawString(sunStr, textX, textY);
        g.setFont(font);
    }

    // ========== 主绘制方法 ==========
    public static void createLawnMowers() {
        for (int i = 0; i < LAWN_ROWS; i++) {
            car.add(new Car(200, ROW_CENTER_Y[i] - CAR_H / 2, CAR_W, CAR_H, 8, lawnMowerImg));
        }
    }

    public static void draw(Graphics g) {
        Font font = g.getFont();
        g.setFont(new Font("微软雅黑", Font.BOLD + Font.ITALIC, 25));
        stopTime = new Date();

        if (loadtime == 0) {
            // 启动开场动画
            IntroAnimation.screenWidth = SCREEN_W;
            IntroAnimation.start();
            loadtime++;
        } else if (loadtime == 1) {
            // 播放开场动画
            if (IntroAnimation.live) {
                IntroAnimation.draw(g);
                cameraX = IntroAnimation.cameraX;
            } else {
                // 动画刚结束，绘制最后一帧动画画面（工具栏+卡片）避免闪烁
                IntroAnimation.drawBackground(g);
                IntroAnimation.drawToolbar(g);
                IntroAnimation.drawCards(g);
                loadtime = 2;
                cameraX = CAMERA_DEFAULT;
                suning = new Sun((int)(Math.random() * 500) + 200, 50, 50, (int)(Math.random() * 350) + 200, suns);
                startTime = new Date();
                start = new Date();
            }
        } else if (loadtime == 2) {
            // ========== 游戏进行中 ==========
            // 天空阳光
            int elapsedSeconds = (int)((stopTime.getTime() - startTime.getTime()) * 0.001);
            if (elapsedSeconds > 0 && elapsedSeconds % 7 == 0 && elapsedSeconds != lastSkySunSecond) {
                lastSkySunSecond = elapsedSeconds;
                suning = new Sun((int)(Math.random() * 500) + 200, 50, 50, (int)(Math.random() * 350) + 200, suns);
                suning.live = true;
                suning.move = false;
            }

            // 绘制背景（不使用translate，手动裁剪）
            drawBackground(g);

            // 使用Graphics平移实现镜头效果
            // 所有游戏对象使用背景坐标，通过translate自动转换为屏幕坐标
            int offsetX = -(int)cameraX;
            g.translate(offsetX, 0);

            // 绘制小推车
            for (int i = 0; i < car.size(); i++) {
                car.get(i).drawSelf(g);
            }

            // 绘制植物
            for (int i = 0; i < flower.size(); i++) {
                if (flower.get(i).live) {
                    flower.get(i).drawSelf(g);
                } else {
                    flower.remove(i);
                }
            }
            for (int i = 0; i < wandou.size(); i++) {
                if (wandou.get(i).live) {
                    wandou.get(i).drawSelf(g);
                }
            }
            for (int i = 0; i < hanbing.size(); i++) {
                if (hanbing.get(i).live) {
                    hanbing.get(i).drawSelf(g);
                }
            }
            for (int i = 0; i < jianguo.size(); i++) {
                if (jianguo.get(i).live) {
                    jianguo.get(i).drawSelf(g);
                }
            }

            // 绘制僵尸
            for (int i = 0; i < qizhi.size(); i++) {
                if (qizhi.get(i).live) {
                    qizhi.get(i).drawSelf(g);
                } else {
                    qizhi.remove(i);
                }
            }
            for (int i = 0; i < tietong.size(); i++) {
                if (tietong.get(i).live) {
                    tietong.get(i).drawSelf(g);
                } else {
                    tietong.remove(i);
                }
            }
            for (int i = 0; i < baozhi.size(); i++) {
                if (baozhi.get(i).live) {
                    baozhi.get(i).drawSelf(g);
                } else {
                    baozhi.remove(i);
                }
            }
            for (int i = 0; i < ganlan.size(); i++) {
                if (ganlan.get(i).live) {
                    ganlan.get(i).drawSelf(g);
                } else {
                    ganlan.remove(i);
                }
            }

            // 绘制子弹
            if (!bullet.isEmpty()) {
                for (int i = 0; i < bullet.size(); i++) {
                    bullet.get(i).drawSelf(g);
                    if (!bullet.get(i).live) {
                        bullet.remove(i);
                    }
                }
            }

            // 恢复Graphics平移（阳光和工具栏使用屏幕坐标）
            g.translate(-offsetX, 0);

            // 绘制阳光（屏幕坐标）
            suning.drawSelf(g);
            if (!sunings.isEmpty()) {
                for (int i = 0; i < sunings.size(); i++) {
                    sunings.get(i).drawSelf(g);
                    if (!sunings.get(i).live) {
                        sunings.remove(i);
                    }
                }
            }

            // 绘制工具栏（固定在屏幕顶部，不随镜头移动）
            drawToolbar(g);

            // 绘制卡片
            for (int i = 0; i < card.size(); i++) {
                card.get(i).drawSelf(g);
            }
            shove.drawSelf(g);

            g.setFont(font);

            // ========== 僵尸生成逻辑 ==========
            stop = new Date();
            if ((int)((stopTime.getTime() - startTime.getTime()) * 0.001) >= 20) {
                if (num >= 0 && num <= 5) {
                    if ((int)((stop.getTime() - start.getTime()) * 0.001) >= ((int)(Math.random() * 5) + 20)) {
                        int row = (int)(Math.random() * 5);
                        qizhi.add(new QiZhi(qizhis, ROW_CENTER_Y[row], 0.6, 10, 100));
                        start = new Date();
                    }
                } else if (num > 5 && num <= 25) {
                    if ((int)((stop.getTime() - start.getTime()) * 0.001) >= ((int)(Math.random() * 5) + 15)) {
                        int x = (int)(Math.random() * 16);
                        int row = (int)(Math.random() * 5);
                        if (x < 5) {
                            qizhi.add(new QiZhi(qizhis, ROW_CENTER_Y[row], 1, 20, 100));
                        } else if (x < 11) {
                            tietong.add(new TieTong(tietongs, ROW_CENTER_Y[row], 0.8, 15, 200));
                        } else if (x < 14) {
                            baozhi.add(new BaoZhi(baozhis, ROW_CENTER_Y[row], 0.8, 15, 200));
                        } else {
                            ganlan.add(new GanLan(ganlans, ROW_CENTER_Y[row], 0.8, 15, 200));
                        }
                        start = new Date();
                    }
                } else if (num > 25 && num <= 50) {
                    if ((int)((stop.getTime() - start.getTime()) * 0.001) >= ((int)(Math.random() * 5) + 10)) {
                        for (int i = 0; i < 2; i++) {
                            int x = (int)(Math.random() * 16);
                            int row = (int)(Math.random() * 5);
                            if (x < 4) {
                                qizhi.add(new QiZhi(qizhis, ROW_CENTER_Y[row], 1, 20, 100));
                            } else if (x < 11) {
                                tietong.add(new TieTong(tietongs, ROW_CENTER_Y[row], 0.8, 15, 200));
                            } else if (x < 14) {
                                baozhi.add(new BaoZhi(baozhis, ROW_CENTER_Y[row], 0.8, 15, 200));
                            } else {
                                ganlan.add(new GanLan(ganlans, ROW_CENTER_Y[row], 0.8, 15, 200));
                            }
                        }
                        start = new Date();
                    }
                } else if (num > 50) {
                    if ((int)((stop.getTime() - start.getTime()) * 0.001) >= 10 + ((int)(Math.random() * 5))) {
                        for (int i = 0; i < 2; i++) {
                            int x = (int)(Math.random() * 16);
                            int row = (int)(Math.random() * 5);
                            if (x < 3) {
                                qizhi.add(new QiZhi(qizhis, ROW_CENTER_Y[row], 1, 20, 100));
                            } else if (x < 9) {
                                tietong.add(new TieTong(tietongs, ROW_CENTER_Y[row], 0.8, 15, 200));
                            } else if (x < 14) {
                                baozhi.add(new BaoZhi(baozhis, ROW_CENTER_Y[row], 0.8, 15, 200));
                            } else {
                                ganlan.add(new GanLan(ganlans, ROW_CENTER_Y[row], 0.8, 15, 200));
                            }
                        }
                        start = new Date();
                    }
                }
            }

            // ========== 卡片冷却 ==========
            for (int i = 0; i < card.size(); i++) {
                card.get(i).stop_time = new Date();
                if ((card.get(i).stop_time.getTime() - card.get(i).start_time.getTime()) * 0.001 > 7.5) {
                    card.get(i).cool = false;
                }
            }

            // ========== 向日葵产阳光 ==========
            if (!flower.isEmpty()) {
                for (int i = 0; i < flower.size(); i++) {
                    flower.get(i).end = new Date();
                    if ((flower.get(i).end.getTime() - flower.get(i).start.getTime()) * 0.001 > 15) {
                        flower.get(i).start = new Date();
                        sunings.add(new Sun(flower.get(i).x, flower.get(i).y, suns));
                    }
                }
            }

            // ========== 阳光超时消失 ==========
            if (!sunings.isEmpty()) {
                for (int i = 0; i < sunings.size(); i++) {
                    if (!sunings.get(i).live) {
                        sunings.remove(i);
                    } else {
                        sunings.get(i).end = new Date();
                        if ((sunings.get(i).end.getTime() - sunings.get(i).start.getTime()) * 0.001 > 10) {
                            sunings.get(i).live = false;
                        }
                    }
                }
            }

        } else if (loadtime == 3) {
            // 游戏暂停/失败界面
            util.stopBGM();
            g.drawImage(img[8], 0, 0, SCREEN_W, SCREEN_H, null);
            if (backmenu) {
                g.drawImage(img[9], 0, 0, SCREEN_W, SCREEN_H, null);
            } else if (restart) {
                g.drawImage(img[10], 0, 0, SCREEN_W, SCREEN_H, null);
            } else if (backgame) {
                g.drawImage(img[11], 0, 0, SCREEN_W, SCREEN_H, null);
            }
        } else if (loadtime == 4) {
            util.playBGM("sounds/shibai.wav", 1);
            loadtime++;
        } else if (loadtime == 5) {
            g.drawImage(img[20], 0, 0, SCREEN_W, SCREEN_H, null);
        }
    }

    // ========== 右键取消选择植物 ==========
    public static void cancelSelect() {
        for (int i = 0; i < card.size(); i++) {
            if (card.get(i).move) {
                card.get(i).move = false;
                card.get(i).x = cardX[i];
                card.get(i).y = cardY + TOOLBAR_Y_BASE;
            }
        }
        if (shove.move) {
            shove.move = false;
            shove.x = (int)(630 * TOOLBAR_SCALE) + TOOLBAR_X;
            shove.y = (int)(10 * TOOLBAR_SCALE) + TOOLBAR_Y_BASE;
        }
    }

    // ========== 鼠标移动事件 ==========
    public static void MouseMove(MouseEvent e) {
        if (loadtime == 2) {
            // 铲子跟随鼠标
            if (shove.move) {
                shove.x = e.getX() - shove.wide / 2;
                shove.y = e.getY() - shove.high / 2;
            }
            // 卡片跟随鼠标
            for (int i = 0; i < card.size(); i++) {
                if (card.get(i).move) {
                    card.get(i).x = e.getX() - 32;
                    card.get(i).y = e.getY() - 24;
                }
            }
        } else if (loadtime == 3) {
            if (GameUtil.ifRect(e.getX(), e.getY(), 301, 338, 502, 377)) {
                backmenu = true;
            } else if (GameUtil.ifRect(e.getX(), e.getY(), 301, 389, 502, 425)) {
                restart = true;
            } else if (GameUtil.ifRect(e.getX(), e.getY(), 241, 460, 571, 528)) {
                backgame = true;
            } else {
                backmenu = false;
                restart = false;
                backgame = false;
            }
        }
    }

    // ========== 鼠标点击事件 ==========
    public static void MouseClick(MouseEvent e) {
        if (loadtime == 2) {
            // 点击铲子区域暂停
            if (GameUtil.ifRect(e.getX(), e.getY(),
                (int)(625 * TOOLBAR_SCALE) + TOOLBAR_X,
                (int)(5 * TOOLBAR_SCALE) + TOOLBAR_Y_BASE,
                (int)(625 * TOOLBAR_SCALE) + TOOLBAR_X + (int)(80 * TOOLBAR_SCALE),
                (int)(5 * TOOLBAR_SCALE) + TOOLBAR_Y_BASE + (int)(90 * TOOLBAR_SCALE))) {
                loadtime = 3;
                return;
            }

            // 铲子拖动中点击：移除植物
            if (shove.move) {
                int bgX = screenToBgX(e.getX());
                int bgY = e.getY();
                for (int i = 0; i < flower.size(); i++) {
                    if (GameUtil.ifRect(bgX, bgY, flower.get(i).x, flower.get(i).y,
                        flower.get(i).x + flower.get(i).wide, flower.get(i).y + flower.get(i).high)) {
                        util.playBGM("sounds/plant.wav", 1);
                        glass.get(flower.get(i).num).live = false;
                        flower.get(i).live = false;
                        break;
                    }
                }
                for (int i = 0; i < wandou.size(); i++) {
                    if (GameUtil.ifRect(bgX, bgY, wandou.get(i).x, wandou.get(i).y,
                        wandou.get(i).x + wandou.get(i).wide, wandou.get(i).y + wandou.get(i).high)) {
                        util.playBGM("sounds/plant.wav", 1);
                        glass.get(wandou.get(i).num).live = false;
                        wandou.get(i).live = false;
                        break;
                    }
                }
                for (int i = 0; i < hanbing.size(); i++) {
                    if (GameUtil.ifRect(bgX, bgY, hanbing.get(i).x, hanbing.get(i).y,
                        hanbing.get(i).x + hanbing.get(i).wide, hanbing.get(i).y + hanbing.get(i).high)) {
                        util.playBGM("sounds/plant.wav", 1);
                        glass.get(hanbing.get(i).num).live = false;
                        hanbing.get(i).live = false;
                        break;
                    }
                }
                for (int i = 0; i < jianguo.size(); i++) {
                    if (GameUtil.ifRect(bgX, bgY, jianguo.get(i).x, jianguo.get(i).y,
                        jianguo.get(i).x + jianguo.get(i).wide, jianguo.get(i).y + jianguo.get(i).high)) {
                        util.playBGM("sounds/plant.wav", 1);
                        glass.get(jianguo.get(i).num).live = false;
                        jianguo.get(i).live = false;
                        break;
                    }
                }
                return;
            }

            // 卡片拖动中点击：放置植物
            for (int i = 0; i < card.size(); i++) {
                if (card.get(i).move) {
                    int bgX = screenToBgX(e.getX());
                    int bgY = e.getY();
                    for (int j = 0; j < glass.size(); j++) {
                        if (!glass.get(j).live && GameUtil.ifRect(bgX, bgY,
                            glass.get(j).x, glass.get(j).y,
                            glass.get(j).x + glass.get(j).wide,
                            glass.get(j).y + glass.get(j).high)) {
                            switch (i) {
                                case 0:
                                    flower.add(new Flower(glass.get(j).x, glass.get(j).y, 60, 70, j));
                                    break;
                                case 1:
                                    wandou.add(new Wandou(glass.get(j).x, glass.get(j).y, 60, 70, wandous, j));
                                    break;
                                case 2:
                                    hanbing.add(new Hanbing(glass.get(j).x, glass.get(j).y, 60, 70, hanbings, j));
                                    break;
                                case 3:
                                    jianguo.add(new Jianguo(glass.get(j).x, glass.get(j).y, 60, 70, jianguos, j));
                                    break;
                            }
                            glass.get(j).live = true;
                            sun -= card.get(i).price;
                            util.playBGM("sounds/plant.wav", 1);
                            card.get(i).cool = true;
                            card.get(i).start_time = new Date();
                            break;
                        }
                    }
                    card.get(i).move = false;
                    card.get(i).x = cardX[i];
                    card.get(i).y = cardY + TOOLBAR_Y_BASE;
                    return;
                }
            }

            // 点击选择植物卡片
            int cY = cardY + TOOLBAR_Y_BASE;
            int cH = cY + CARD_H;
            for (int i = 0; i < card.size(); i++) {
                if (GameUtil.ifRect(e.getX(), e.getY(), cardX[i], cY, cardX[i] + CARD_W, cH)) {
                    if (sun >= card.get(i).price && !card.get(i).cool) {
                        card.get(i).move = true;
                        util.playBGM("sounds/plant.wav", 1);
                    }
                    return;
                }
            }

            // 点击天空阳光
            if (GameUtil.ifRect(e.getX(), e.getY(), suning.x - 10, suning.y - 10,
                suning.x - 10 + suning.wide, suning.y - 10 + suning.high)) {
                suning.move = true;
                util.playBGM("sounds/yangguang.wav", 1);
                suning.speedx = (int)((suning.x - 70) / 30);
                suning.speedy = (int)((suning.y - 45) / 30);
            }

            // 点击向日葵产出的阳光
            if (!sunings.isEmpty()) {
                for (int i = 0; i < sunings.size(); i++) {
                    if (GameUtil.ifRect(e.getX(), e.getY(),
                        sunings.get(i).x - 10, sunings.get(i).y - 10,
                        sunings.get(i).x - 10 + 70, sunings.get(i).y - 10 + 70)) {
                        sunings.get(i).move = true;
                        util.playBGM("sounds/yangguang.wav", 1);
                    }
                }
            }

        } else if (loadtime == 3) {
            if (GameUtil.ifRect(e.getX(), e.getY(), 301, 338, 502, 377)) {
                loadtime = 2;
                GameFrame.live = false;
                MenuFrame.live = true;
                LoadFrame.util.playBGM();
            } else if (GameUtil.ifRect(e.getX(), e.getY(), 301, 389, 502, 425)) {
                resetGame();
            } else if (GameUtil.ifRect(e.getX(), e.getY(), 241, 460, 571, 528)) {
                util.playBGM();
                loadtime = 2;
            }
        } else if (loadtime == 5) {
            resetGame();
            GameFrame.live = false;
            MenuFrame.live = true;
            LoadFrame.util.playBGM();
        }
    }

    // ========== 重置游戏 ==========
    public static void resetGame() {
        loadtime = 0;
        sun = 50;
        flower.clear();
        wandou.clear();
        hanbing.clear();
        jianguo.clear();
        qizhi.clear();
        tietong.clear();
        baozhi.clear();
        ganlan.clear();
        bullet.clear();
        car.clear();
        sunings.clear();
        for (int i = 0; i < card.size(); i++) {
            card.get(i).cool = false;
        }
        for (int i = 0; i < glass.size(); i++) {
            glass.get(i).live = false;
        }
        createLawnMowers();
        startTime = new Date();
        start = new Date();
        lastSkySunSecond = -1;
        num = 0;
        cameraX = CAMERA_DEFAULT;
        util.playBGM();
    }
}
