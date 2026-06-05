package plantgame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
/*
 * 主菜单界面
 */
public class MenuFrame {
	
	 static int x;
	 static int y;
	 static int loadtime;
	 static int op;
	 static boolean live;
 	 static GameUtil util;
	 static Image img[];
	
	static {
		 x=0;
		 y=30;
		 loadtime=0;
		 op=0;
		 live=false;
		 util=new GameUtil();
		 img=new Image[5];
		 img[0]=GameUtil.getImage("MenuFrame/menu (1).png");   // 默认
		 img[1]=GameUtil.getImage("MenuFrame/menu (9).png");   // 开始冒险hover
		 img[2]=GameUtil.getImage("MenuFrame/menu (10).png");  // mini模式hover
		 img[3]=GameUtil.getImage("MenuFrame/menu (11).png");  // puzzle模式hover
		 img[4]=GameUtil.getImage("MenuFrame/menu (12).png");  // survival模式hover
		 }
	//鼠标移动事件处理
	public static void MouseMove(MouseEvent e){
		if(GameUtil.ifRect(e.getX(),e.getY(),460,180,459,233,740,277,752,211)){//开始冒险
			if(loadtime==0) {
				util.playBGM("sounds/bgm4.wav",1);loadtime++;
			}op=1;
		}else if(GameUtil.ifRect(e.getX(),e.getY(),461,248,460,313,719,360,737,298)){//mini模式
			if(loadtime==0) {
				util.playBGM("sounds/bgm4.wav",1);loadtime++;
			}op=2;
		}else if(GameUtil.ifRect(e.getX(),e.getY(),463,327,462,371,693,424,705,375)){//puzzle模式
			if(loadtime==0) {
				util.playBGM("sounds/bgm4.wav",1);loadtime++;
			}op=3;
		}else if(GameUtil.ifRect(e.getX(),e.getY(),465,390,464,434,678,490,690,444)){//survival模式
			if(loadtime==0) {
				util.playBGM("sounds/bgm4.wav",1);loadtime++;
			}op=4;
		}else {loadtime=0;op=0;}
	}
	//鼠标点击事件处理
	public static void MouseClick(MouseEvent e) {
		if(GameUtil.ifRect(e.getX(),e.getY(),460,180,459,233,740,277,752,211)){//开始冒险
			MenuFrame.live=false;GameFrame.live=true;loadtime=0;op=0;LoadFrame.util.stopBGM();
			GameFrame.util.loadBGM("sounds/bgm6.wav");GameFrame.util.playBGM();
		}else if(GameUtil.ifRect(e.getX(),e.getY(),461,248,460,313,719,360,737,298)){//mini模式
			MenuFrame.live=false;MiniFrame.live=true;loadtime=0;op=0;util.playBGM("sounds/bgm0.wav",1);
			LoadFrame.util.stopBGM();
		}else if(GameUtil.ifRect(e.getX(),e.getY(),463,327,462,371,693,424,705,375)){//puzzle模式
			MenuFrame.live=false;PuzzleFrame.live=true;op=0;util.playBGM("sounds/bgm0.wav",1);
			LoadFrame.util.stopBGM();
		}else if(GameUtil.ifRect(e.getX(),e.getY(),465,390,464,434,678,490,690,444)){//survival模式
			MenuFrame.live=false;SurvivalFrame.live=true;op=0;util.playBGM("sounds/bgm0.wav",1);
			LoadFrame.util.stopBGM();
		}
	}

	public static void draw(Graphics g) {
		g.drawImage(img[op],x,y,null);
	} 
}
