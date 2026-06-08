package plantgame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 开场动画类
 * 实现镜头移动、ReadySetPlant、卡片栏下落等动画效果
 */
public class IntroAnimation {
    
    // 动画状态
    public static final int STATE_IDLE = 0;
    public static final int STATE_CAMERA_RIGHT = 1;
    public static final int STATE_SHOW_ZOMBIES = 2;
    public static final int STATE_CAMERA_LEFT = 3;
    public static final int STATE_READY_SET = 4;
    public static final int STATE_TOOLBAR_DROP = 5;
    public static final int STATE_CARD_PLACE = 6;
    public static final int STATE_FINISHED = 7;
    
    public static int state = STATE_IDLE;
    public static boolean live = false;
    
    // 镜头位置
    public static double cameraX = 0;
    public static double cameraTargetX = 0;
    public static double cameraStartX = 0;
    
    // 动画时间控制
    public static long animStartTime = 0;
    public static long animDuration = 0;
    
    // 背景图
    public static Image bgImage;
    public static int bgWidth = 1400;
    public static int screenWidth = 900;
    
    // 镜头偏移：初始镜头位置，让左侧露出部分房子
    // cameraX=170 时，屏幕显示 back0 的 x=170-1070 区域
    // 左侧露出房子 x=0-170，右侧露出马路 x=980-1070
    public static final int LAWN_OFFSET = 170;
    
    // 工具栏缩放参数
    // 7.png原始981x102，沿用原缩放比例，仅增加显示长度
    public static final double TOOLBAR_SCALE = 560.0 / 712.0; // ≈0.787
    public static final int TOOLBAR_W = (int)(981 * TOOLBAR_SCALE); // 771
    public static final int TOOLBAR_H = (int)(102 * TOOLBAR_SCALE); // 80
    public static final int TOOLBAR_X = 10; // 左侧留一点间距
    public static final int TOOLBAR_Y_BASE = 0; // 工具栏顶部y位置
    
    // 工具栏
    public static Image toolbarImg;
    public static double toolbarY = -100;
    public static double toolbarTargetY = 0;
    public static double toolbarStartY = -100;
    
    // 卡片（使用IntroAnimation目录下的完整卡片图片，带背景）
    public static Image[] cardImages = new Image[4];
    public static Image[] cardGrayImages = new Image[4]; // 灰色冷却卡片
    public static Image shovelImg;
    
    // 缩放后的卡片位置（基于toolbar缩放后的卡槽位置）
    // 原始toolbar卡槽: x=86,152,216,279 → 缩放后: x=68,120,170,219
    // 卡片原始64x90 → 缩放后50x71
    public static final int CARD_W = (int)(64 * TOOLBAR_SCALE); // 50
    public static final int CARD_H = (int)(90 * TOOLBAR_SCALE); // 71
    public static int[] cardX = {
        (int)(86 * TOOLBAR_SCALE) + TOOLBAR_X,   // 68+10=78
        (int)(152 * TOOLBAR_SCALE) + TOOLBAR_X,  // 120+10=130
        (int)(216 * TOOLBAR_SCALE) + TOOLBAR_X,  // 170+10=180
        (int)(279 * TOOLBAR_SCALE) + TOOLBAR_X   // 219+10=229
    };
    public static int cardY = (int)(6 * TOOLBAR_SCALE); // 卡片在toolbar中的y偏移
    
    public static int currentCardIndex = -1;
    public static boolean[] cardPlaced = {false, false, false, false};
    
    // Ready Set Plant 图片
    public static Image[] readyImages = new Image[3];
    public static int currentReadyIndex = 0;
    
    // 僵尸预览动画
    public static Image[][] zombieWalkFrames;
    public static final int[] zombieWalkFrameCounts = {48, 48, 47, 48};
    public static final int[] zombieBodyHeights = {60, 60, 70, 60};
    public static ArrayList<int[]> zombies = new ArrayList<>();
    
    // 音效播放标记
    public static boolean readySoundPlayed = false;
    public static boolean[] cardSoundPlayed = {false, false, false, false};
    public static boolean bgmVolumeRestored = false;
    
    // 缓动函数
    public static double smootherStep(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    
    public static double easeOutBounce(double t) {
        if (t < 0.5) {
            return 2 * t * t;
        } else {
            return 1 - (-2 * t + 2) * (-2 * t + 2) / 2;
        }
    }

    public static void shuffleArray(int[] values) {
        for (int i = values.length - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1));
            int temp = values[i];
            values[i] = values[j];
            values[j] = temp;
        }
    }
    
    // 初始化资源
    static {
        bgImage = GameUtil.getImage("GameFrame/back0.png");
        toolbarImg = GameUtil.getImage("fresh/7.png");
        shovelImg = GameUtil.getImage("IntroAnimation/shovel.png");
        
        cardImages[0] = GameUtil.getImage("IntroAnimation/card_1.png");
        cardImages[1] = GameUtil.getImage("IntroAnimation/card_2.png");
        cardImages[2] = GameUtil.getImage("IntroAnimation/card_snowpea.png");
        cardImages[3] = GameUtil.getImage("IntroAnimation/card_wallnut.png");
        
        readyImages[0] = GameUtil.getImage("IntroAnimation/readySetPlant1.png");
        readyImages[1] = GameUtil.getImage("IntroAnimation/readySetPlant2.png");
        readyImages[2] = GameUtil.getImage("IntroAnimation/readySetPlant3.png");
        
        zombieWalkFrames = new Image[4][];
        zombieWalkFrames[0] = new Image[zombieWalkFrameCounts[0]];
        zombieWalkFrames[1] = new Image[zombieWalkFrameCounts[1]];
        zombieWalkFrames[2] = new Image[zombieWalkFrameCounts[2]];
        zombieWalkFrames[3] = new Image[zombieWalkFrameCounts[3]];
        for (int i = 0; i < zombieWalkFrameCounts[0]; i++) {
            zombieWalkFrames[0][i] = GameUtil.getImage("Zombies/qizhi/qizhi (" + (i + 1) + ").png");
        }
        for (int i = 0; i < zombieWalkFrameCounts[1]; i++) {
            zombieWalkFrames[1][i] = GameUtil.getImage("Zombies/tietong/tietong (" + (i + 1) + ").png");
        }
        for (int i = 0; i < zombieWalkFrameCounts[2]; i++) {
            zombieWalkFrames[2][i] = GameUtil.getImage("Zombies/baozhi/baozhi (" + (i + 1) + ").png");
        }
        for (int i = 0; i < zombieWalkFrameCounts[3]; i++) {
            zombieWalkFrames[3][i] = GameUtil.getImage("Zombies/ganlan/ganlan (" + (i + 1) + ").png");
        }
    }
    
    // 开始动画
    public static void start() {
        live = true;
        state = STATE_CAMERA_RIGHT;
        cameraX = LAWN_OFFSET;
        cameraStartX = LAWN_OFFSET;
        cameraTargetX = bgWidth - screenWidth;
        animStartTime = System.currentTimeMillis();
        animDuration = 3500;
        
        toolbarY = -100;
        currentCardIndex = -1;
        cardPlaced = new boolean[]{false, false, false, false};
        cardSoundPlayed = new boolean[]{false, false, false, false};
        currentReadyIndex = 0;
        readySoundPlayed = false;
        bgmVolumeRestored = false;
        zombies.clear();

        int previewCount = 5 + (int)(Math.random() * 3); // 5 to 7
        int[] rows = {0, 1, 2, 3, 4};
        int[] types = {0, 1, 2, 3};
        int[] xSlots = {1030, 1090, 1150, 1210, 1270};
        shuffleArray(rows);
        shuffleArray(types);
        shuffleArray(xSlots);

        for (int i = 0; i < 4; i++) {
            int type = types[i];
            int row = rows[i];
            int bgX = xSlots[i] + (int)(Math.random() * 21) - 10;
            int spriteY = GameFrame.getZombieSpriteY(row, zombieBodyHeights[type]);
            zombies.add(new int[]{spriteY, 0, type, bgX});
        }

        for (int i = 4; i < previewCount; i++) {
            int extraType = (int)(Math.random() * zombieWalkFrames.length);
            int extraRow = rows[i % rows.length];
            int[] extraSlots = {1010, 1070, 1130, 1190, 1250, 1310, 1370};
            int extraX = extraSlots[i] + (int)(Math.random() * 21) - 10;
            int extraY = GameFrame.getZombieSpriteY(extraRow, zombieBodyHeights[extraType]);
            zombies.add(new int[]{extraY, 0, extraType, extraX});
        }
    }
    
    // 绘制动画
    public static void draw(Graphics g) {
        if (!live) return;
        
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                RenderingHints.VALUE_RENDER_SPEED);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        
        long currentTime = System.currentTimeMillis();
        double progress;
        
        switch (state) {
            case STATE_CAMERA_RIGHT:
                progress = (double)(currentTime - animStartTime) / animDuration;
                if (progress >= 1.0) {
                    progress = 1.0;
                    state = STATE_SHOW_ZOMBIES;
                    animStartTime = currentTime;
                    animDuration = 2000;
                }
                cameraX = cameraStartX + (cameraTargetX - cameraStartX) * smootherStep(progress);
                drawBackground(g);
                drawZombies(g);
                break;
                
            case STATE_SHOW_ZOMBIES:
                drawBackground(g);
                drawZombies(g);
                if (currentTime - animStartTime >= animDuration) {
                    state = STATE_CAMERA_LEFT;
                    animStartTime = currentTime;
                    animDuration = 3500;
                    cameraStartX = cameraX;
                    cameraTargetX = LAWN_OFFSET;
                }
                break;
                
            case STATE_CAMERA_LEFT:
                progress = (double)(currentTime - animStartTime) / animDuration;
                if (progress >= 1.0) {
                    progress = 1.0;
                    state = STATE_READY_SET;
                    animStartTime = currentTime;
                    animDuration = 2000;
                    cameraX = LAWN_OFFSET;
                }
                cameraX = cameraStartX + (cameraTargetX - cameraStartX) * smootherStep(progress);
                drawBackground(g);
                drawZombies(g);
                break;
                
            case STATE_READY_SET:
                drawBackground(g);
                progress = (double)(currentTime - animStartTime) / animDuration;
                if (!readySoundPlayed) {
                    GameFrame.util.stopBGM();
                    GameFrame.util.playBGM("IntroAnimation/readySetPlant.wav", 1.0f);
                    readySoundPlayed = true;
                }
                if (progress < 0.33) {
                    currentReadyIndex = 0;
                } else if (progress < 0.66) {
                    currentReadyIndex = 1;
                } else if (progress < 1.0) {
                    currentReadyIndex = 2;
                } else {
                    state = STATE_TOOLBAR_DROP;
                    animStartTime = currentTime;
                    animDuration = 800;
                    toolbarStartY = -100;
                    toolbarTargetY = 0;
                }
                if (currentReadyIndex < 3 && readyImages[currentReadyIndex] != null) {
                    int imgW = readyImages[currentReadyIndex].getWidth(null);
                    int imgH = readyImages[currentReadyIndex].getHeight(null);
                    g.drawImage(readyImages[currentReadyIndex], 
                        (screenWidth - imgW) / 2, (600 - imgH) / 2, null);
                }
                break;
                
            case STATE_TOOLBAR_DROP:
                drawBackground(g);
                progress = (double)(currentTime - animStartTime) / animDuration;
                if (progress >= 1.0) {
                    progress = 1.0;
                    state = STATE_CARD_PLACE;
                    animStartTime = currentTime;
                    animDuration = 300;
                    currentCardIndex = 0;
                }
                toolbarY = toolbarStartY + (toolbarTargetY - toolbarStartY) * easeOutBounce(progress);
                drawToolbar(g);
                break;
                
            case STATE_CARD_PLACE:
                drawBackground(g);
                drawToolbar(g);
                progress = (double)(currentTime - animStartTime) / animDuration;
                if (currentCardIndex < 4) {
                    if (!cardSoundPlayed[currentCardIndex]) {
                        GameFrame.util.playBGM("IntroAnimation/cardLift.wav", 1);
                        cardSoundPlayed[currentCardIndex] = true;
                    }
                    if (progress >= 1.0) {
                        cardPlaced[currentCardIndex] = true;
                        currentCardIndex++;
                        animStartTime = currentTime;
                        if (currentCardIndex >= 4) {
                            state = STATE_FINISHED;
                            live = false;
                            GameFrame.util.loadBGM("sounds/bgm8.wav");
                            GameFrame.util.playBGM();
                        }
                    }
                }
                drawCards(g);
                break;
                
            case STATE_FINISHED:
                live = false;
                if (!bgmVolumeRestored) {
                    GameFrame.util.loadBGM("sounds/bgm8.wav");
                    GameFrame.util.playBGM();
                    bgmVolumeRestored = true;
                }
                break;
        }
    }
    
    // 绘制背景
    public static void drawBackground(Graphics g) {
        if (bgImage != null) {
            int drawX = (int)Math.round(-cameraX);
            int imgW = bgImage.getWidth(null);
            int imgH = bgImage.getHeight(null);
            int srcX = Math.max(0, -drawX);
            int srcW = Math.min(imgW - srcX, screenWidth);
            int dstX = Math.max(0, drawX);
            if (srcW > 0 && srcX < imgW) {
                g.drawImage(bgImage, 
                    dstX, 0, dstX + srcW, imgH,
                    srcX, 0, srcX + srcW, imgH, null);
            }
        }
    }
    
    // 绘制僵尸
    public static void drawZombies(Graphics g) {
        for (int i = 0; i < zombies.size(); i++) {
            int[] z = zombies.get(i);
            int y = z[0];
            int frame = z[1];
            int type = z[2];
            int zombieX = (int)Math.round(z[3] - cameraX);
            if (zombieX > -50 && zombieX < screenWidth + 50) {
                Image[] frames = zombieWalkFrames[type];
                if (frames != null && frame < frames.length) {
                    g.drawImage(frames[frame], zombieX, y, null);
                }
                z[1] = (z[1] + 1) % zombieWalkFrameCounts[type];
            }
        }
    }
    
    // 绘制工具栏（缩放绘制）
    public static void drawToolbar(Graphics g) {
        if (toolbarImg != null) {
            int tbScreenY = (int)Math.round(toolbarY) + TOOLBAR_Y_BASE;
            g.drawImage(toolbarImg, TOOLBAR_X, tbScreenY, 
                TOOLBAR_X + TOOLBAR_W, tbScreenY + TOOLBAR_H,
                0, 0, toolbarImg.getWidth(null), toolbarImg.getHeight(null), null);
        }
    }
    
    // 绘制卡片（随工具栏一起移动，缩放绘制）
    public static void drawCards(Graphics g) {
        int tbScreenY = (int)Math.round(toolbarY) + TOOLBAR_Y_BASE;
        for (int i = 0; i < 4; i++) {
            if (cardPlaced[i] && cardImages[i] != null) {
                g.drawImage(cardImages[i], 
                    cardX[i], cardY + tbScreenY,
                    cardX[i] + CARD_W, cardY + tbScreenY + CARD_H,
                    0, 0, cardImages[i].getWidth(null), cardImages[i].getHeight(null), null);
            }
        }
    }
    
    public static boolean isFinished() {
        return state == STATE_FINISHED || !live;
    }
}
