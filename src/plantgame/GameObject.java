package plantgame;
/*
 * 游戏物体的父类
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Date;

public class GameObject {
	int num;//植物与土地序号一一对应
	int x,y;
	int wide,high;
	int speed;
	Image img;
	boolean live=true;
	//无参空构造
	public GameObject() {
	}


	public GameObject(int x, int y) {//
		super();
		this.x = x;
		this.y = y;
	}



	public GameObject(int x, int y, int wide, int high) {
		super();
		this.x = x;
		this.y = y;
		this.wide = wide;
		this.high = high;
	}



	public GameObject(int x, int y, int wide, int high, int speed, Image img) {
		super();
		this.x = x;
		this.y = y;
		this.wide = wide;
		this.high = high;
		this.speed = speed;
		this.img = img;
	}

	public void drawSelf(Graphics g) {
		g.drawImage(img,x,y,null);
	}
	
	
	
	
	/*
	 * 返回物体所在矩形,便于后续碰撞检测
	 */
	public Rectangle getRect() {
		return new Rectangle(x,y,wide,high);
	}
}
//草地格子类,确定植物位置45个实例对象
class Glass extends GameObject{

	public Glass(int x, int y) {		
		super(x, y);
		this.wide=82;
		this.high=100;
		this.live=false;
	}
}
//割草机类
class Car extends GameObject{
	boolean move=false;
	int movetime=0;//方便播放音效
	 public Car(int x, int y, int wide, int high, int speed, Image img) {
		 super(x,y,wide,high,speed,img);
	 }
	 //割草机画自己的方法
	 public void drawSelf(Graphics g) {
		 if(this.live) {
			 g.drawImage(this.img,this.x,this.y,this.wide,this.high,null);//生命值为true则画出，否则不画
			 
			 if(this.move) {//车子处于移动状态
				 this.x+=speed;
				 if(this.movetime==0) {//第一次处于移动状态播放音效
					 GameFrame.util.playBGM("sounds/car.wav",1);
					 movetime++;
				 }
			 }
			 for(int i=0;i<GameFrame.qizhi.size();i++) {
				 if(this.getRect().intersects(GameFrame.qizhi.get(i).getRect())) {
					 this.move =true;
					 if(GameFrame.qizhi.get(i).moving) {
						 GameFrame.qizhi.get(i).moving=false;
					 }else if(GameFrame.qizhi.get(i).attacking) {
						 GameFrame.qizhi.get(i).attacking=false;
					 }
					 GameFrame.qizhi.get(i).count=88;
					 GameFrame.qizhi.get(i).dieing=true;
				 }
			 }
			 for(int i=0;i<GameFrame.tietong.size();i++) {
				 if(this.getRect().intersects(GameFrame.tietong.get(i).getRect())) {
					 this.move =true;
					 if(GameFrame.tietong.get(i).moving) {
						 GameFrame.tietong.get(i).moving=false;
					 }else if(GameFrame.tietong.get(i).attacking) {
						 GameFrame.tietong.get(i).attacking=false;
					 }
					 GameFrame.tietong.get(i).count=191;
					 GameFrame.tietong.get(i).dieing=true;
				 }
			 }
			 for(int i=0;i<GameFrame.baozhi.size();i++) {
				 if(this.getRect().intersects(GameFrame.baozhi.get(i).getRect())) {
					 this.move =true;
					 if(GameFrame.baozhi.get(i).moving) {
						 GameFrame.baozhi.get(i).moving=false;
					 }else if(GameFrame.baozhi.get(i).attacking) {
						 GameFrame.baozhi.get(i).attacking=false;
					 }
					 GameFrame.baozhi.get(i).count=188;
					 GameFrame.baozhi.get(i).dieing=true;
				 }
			 }
			 for(int i=0;i<GameFrame.ganlan.size();i++) {
				 if(this.getRect().intersects(GameFrame.ganlan.get(i).getRect())) {
					 this.move =true;
					 if(GameFrame.ganlan.get(i).moving) {
						 GameFrame.ganlan.get(i).moving=false;
					 }else if(GameFrame.ganlan.get(i).attacking) {
						 GameFrame.ganlan.get(i).attacking=false;
					 }
					 GameFrame.ganlan.get(i).count=192;
					 GameFrame.ganlan.get(i).dieing=true;
				 }
			 }

			 
			 
			 if(this.x>1400) {//超出背景则销毁
				 this.live=false;
			 }
		 }
		
	} 
}
//铲子
class Shove extends GameObject{
	boolean move=false;
	 public Shove(int x, int y, int wide, int high, int speed, Image img) {
		 super(x,y,wide,high,speed,img);
	 }
	 
	 public void drawSelf(Graphics g) {
		 // 只在拖动时绘制铲子图标，toolbar.png已包含铲子区域
		 if(this.move) {
			 g.drawImage(this.img,this.x,this.y,this.x+this.wide,this.y+this.high,
				 0,0,this.img.getWidth(null),this.img.getHeight(null),null);
		 }
	 }
}

class Hammer extends GameObject{
	boolean move=false;
	boolean cool=false;
	int slotX,slotY;
	Date start_time;
	Date stop_time;
	 public Hammer(int x, int y, int wide, int high, int speed, Image img) {
		 super(x,y,wide,high,speed,img);
		 this.slotX=x;
		 this.slotY=y;
		 this.start_time=new Date();
		 this.stop_time=new Date();
	 }
	 public void resetPosition() {
		 this.x=this.slotX;
		 this.y=this.slotY;
	 }
	 public void drawSelf(Graphics g) {
		 if(this.move) {
			 g.drawImage(this.img,this.x,this.y,this.x+this.wide,this.y+this.high,
				 0,0,this.img.getWidth(null),this.img.getHeight(null),null);
		 }
	 }
}

class Glove extends GameObject{
	boolean move=false;
	boolean cool=false;
	int slotX,slotY;
	Date start_time;
	Date stop_time;
	 public Glove(int x, int y, int wide, int high, int speed, Image img) {
		 super(x,y,wide,high,speed,img);
		 this.slotX=x;
		 this.slotY=y;
		 this.start_time=new Date();
		 this.stop_time=new Date();
	 }
	 public void resetPosition() {
		 this.x=this.slotX;
		 this.y=this.slotY;
	 }
	 public void drawSelf(Graphics g) {
		 if(this.move) {
			 g.drawImage(this.img,this.x,this.y,this.x+this.wide,this.y+this.high,
				 0,0,this.img.getWidth(null),this.img.getHeight(null),null);
		 }
	 }
}

//卡片
class Card extends GameObject{
	boolean move;
	boolean cool;
	int price;
	int z,w;//z,w给灰色植物使用，保持卡片槽绘制不变
	Image img1;
	Date start_time;//方便判断冷却时间
	Date stop_time;
	//构造函数
	public Card(int x, int y, int wide, int high, int price,Image img,Image img1) {
		this.x=x;this.y=y;this.wide=wide;this.high=high;this.img=img;this.img1=img1;this.price=price;
		this.z=x;this.w=y;this.move=false;this.cool=false;
		this.start_time=new Date();this.stop_time=new Date();
	}
	//画出卡槽
	@Override
	public void drawSelf(Graphics g) {
		// card_*.png自带价格文字，不需要额外绘制数字
		if(this.move) {
			// 拖动中：卡槽位置画半透明卡片 + 跟随鼠标的彩色卡片
			g.drawImage(this.img,this.z,this.w,this.z+this.wide,this.w+this.high,
				0,0,this.img.getWidth(null),this.img.getHeight(null),null);
			// 半透明覆盖表示空槽位
			g.setColor(new java.awt.Color(0,0,0,100));
			g.fillRect(this.z,this.w,this.wide,this.high);
			// 跟随鼠标的彩色卡片
			g.drawImage(this.img,this.x,this.y,this.x+this.wide,this.y+this.high,
				0,0,this.img.getWidth(null),this.img.getHeight(null),null);
		}else {
			if(GameFrame.sun>=this.price&&this.cool==false) {
				// 可用：彩色卡片缩放绘制
				g.drawImage(this.img,this.x,this.y,this.x+this.wide,this.y+this.high,
					0,0,this.img.getWidth(null),this.img.getHeight(null),null);
			}else {
				// 不可用/冷却中：彩色卡片 + 暗色覆盖
				g.drawImage(this.img,this.x,this.y,this.x+this.wide,this.y+this.high,
					0,0,this.img.getWidth(null),this.img.getHeight(null),null);
				g.setColor(new java.awt.Color(0,0,0,120));
				g.fillRect(this.x,this.y,this.wide,this.high);
			}
		}
		
	}
}
//阳光
class Sun extends GameObject {
	Image imgs[];
	int max;
	int count;//指示播放到第几张图片
	int speedx,speedy;//阳光被收集后的移动速度
	boolean plantSun;
	boolean move=false;
	Date start;
	Date end;
	
	
	public  Sun(int x,int y,Image imgs[]) {//向日葵生产的阳光
		this.x=x;this.y=y;//x,y值由向日葵确定
		this.wide=70;this.high=70;//扩大点击范围
		this.imgs=imgs;this.max=y+55;
		this.count=0;this.start=new Date();
		this.plantSun=true;
		this.speedx=(int)((this.x-70)/30);this.speedy=(int)((this.y-45)/30);
	}
	
	public  Sun(int x,int wide,int high,int max,Image imgs[]) {//自动出现的阳光
		this.x=x;this.y=-30;//初始随机一个x值，y值为屏幕上方开始绘制阳光，阳光下落直到最大max值处
		this.wide=70;this.high=70;//扩大点击范围
		this.imgs=imgs;this.max=max;
		this.count=0;this.plantSun=false;this.speedx=(int)((this.x-70)/30);this.speedy=(int)((this.max-45)/30);
	}
	@Override
	public void drawSelf(Graphics g) {
		
		if(this.live) {
			if(this.count<this.imgs.length-1) {//动画轮播，画出太阳
				g.drawImage(imgs[this.count++],this.x-10,this.y-10,50,50,null);
			}else {
				g.drawImage(imgs[this.count],this.x-10,this.y-10,50,50,null);
				this.count=0;
			}
			
			if(this.move) {
				int targetX = 65;
				int targetY = 40;
				int dx = targetX - this.x;
				int dy = targetY - this.y;
				if(Math.abs(dx)<=4 && Math.abs(dy)<=4) {
					if(this.live) {
						GameFrame.sun+=25;
						this.live=false;
					}
				}else {
					this.x += Math.max(-12, Math.min(12, dx));
					this.y += Math.max(-12, Math.min(12, dy));
				}
			}else {
				if(this.y<this.max) {
					this.y+=Math.max(1,(this.max-this.y)/20);
				}
			}
		}
	}
	
}
//花类
class Flower extends GameObject{
	static final int NORMAL_W = 60;
	static final int NORMAL_H = 70;
	static final int DOUBLE_W = 83;
	static final int DOUBLE_H = 84;
	static final int DOUBLE_OFFSET_X = -10;
	Image imgs[];
	boolean doubleHead;
	int queuedSunCount;
	Date burstStart;
	int count;
	int hp;
	Date start,end;//生产太阳
	
	public Flower(int x, int y, int wide, int high,int num) {
		super(x, y, wide, high);
		this.start=new Date();
		this.imgs=GameUtil.getGifFrames("flower/SunFlower.gif");
		this.doubleHead=false;
		this.queuedSunCount=0;
		this.burstStart=new Date();
		this.count=0;
		this.num=num;
		this.hp=100;
		this.wide=NORMAL_W;
		this.high=NORMAL_H;
		
	}

	public boolean isDoubleHead() {
		return this.doubleHead;
	}

	public void setGridPosition(int gridX,int gridY) {
		this.y=gridY;
		if(this.doubleHead) {
			this.x=gridX+DOUBLE_OFFSET_X;
			this.wide=DOUBLE_W;
			this.high=DOUBLE_H;
		}else {
			this.x=gridX;
			this.wide=NORMAL_W;
			this.high=NORMAL_H;
		}
	}

	public void upgradeToDoubleHead() {
		this.doubleHead=true;
		this.imgs=GameFrame.doubleFlowers;
		setGridPosition(GameFrame.glass.get(this.num).x, GameFrame.glass.get(this.num).y);
		this.count=0;
		this.queuedSunCount=0;
	}

	@Override
	public void drawSelf(Graphics g) {
		if(this.hp<=0) {
			this.live=false;
		}
		if(this.live) {
			if(count<imgs.length-1) {
				g.drawImage(imgs[this.count++],this.x,this.y,null);
			}else {
				g.drawImage(imgs[this.count++],this.x,this.y,null);
				this.count=0;
			}
		}else {			
			GameFrame.glass.get(GameFrame.flower.get(GameFrame.flower.indexOf(this)).num).live =false;
			GameFrame.flower.remove(GameFrame.flower.indexOf(this));

		}
	}	
}
//豌豆射手类
class PlantAttackUtil {
	static int getZombieLaneCenterY(GameObject zombie) {
		return zombie.y + 50 + zombie.high / 2;
	}

	static boolean hasZombieInLane(int plantX,int plantY) {
		int laneY = plantY + GameFrame.CELL_H / 2;
		int visibleRightX = GameFrame.getVisibleRightX();
		for(int i=0;i<GameFrame.qizhi.size();i++) {
			QiZhi zombie = GameFrame.qizhi.get(i);
			if(zombie.live && !zombie.dieing && zombie.hp>0 && getZombieLaneCenterY(zombie) == laneY
				&& zombie.x <= visibleRightX && zombie.x + zombie.wide >= plantX) {
				return true;
			}
		}
		for(int i=0;i<GameFrame.tietong.size();i++) {
			TieTong zombie = GameFrame.tietong.get(i);
			if(zombie.live && !zombie.dieing && zombie.hp>0 && getZombieLaneCenterY(zombie) == laneY
				&& zombie.x <= visibleRightX && zombie.x + zombie.wide >= plantX) {
				return true;
			}
		}
		for(int i=0;i<GameFrame.baozhi.size();i++) {
			BaoZhi zombie = GameFrame.baozhi.get(i);
			if(zombie.live && !zombie.dieing && zombie.hp>0 && getZombieLaneCenterY(zombie) == laneY
				&& zombie.x <= visibleRightX && zombie.x + zombie.wide >= plantX) {
				return true;
			}
		}
		for(int i=0;i<GameFrame.ganlan.size();i++) {
			GanLan zombie = GameFrame.ganlan.get(i);
			if(zombie.live && !zombie.dieing && zombie.hp>0 && getZombieLaneCenterY(zombie) == laneY
				&& zombie.x <= visibleRightX && zombie.x + zombie.wide >= plantX) {
				return true;
			}
		}
		return false;
	}
}

class Wandou extends GameObject{
	static final int HEAD_SINGLE = 1;
	static final int HEAD_DOUBLE = 2;
	static final int HEAD_TRIPLE = 3;
	static final int NORMAL_W = 60;
	static final int NORMAL_H = 70;
	static final int DOUBLE_HEAD_W = 92;
	static final int DOUBLE_HEAD_H = 72;
	static final int TRIPLE_HEAD_W = 73;
	static final int TRIPLE_HEAD_H = 80;
	static final int DOUBLE_HEAD_OFFSET_X = -26;
	static final int TRIPLE_HEAD_OFFSET_X = -12;
	static final int RIGHT_MOUTH_BULLET_X = 11;
	static final int RIGHT_MOUTH_BULLET_Y = 2;
	static final int LEFT_MOUTH_BULLET_X = -58;
	static final int LEFT_MOUTH_BULLET_Y = 14;
	static final int TRIPLE_BURST_INTERVAL_MS = 120;
	static final int TRIPLE_HEAD_TOP_BULLET_X = -10;
	static final int TRIPLE_HEAD_TOP_BULLET_Y = -8;
	static final int TRIPLE_HEAD_LEFT_BULLET_X = -34;
	static final int TRIPLE_HEAD_LEFT_BULLET_Y = 18;
	static final int TRIPLE_HEAD_RIGHT_BULLET_X = 2;
	static final int TRIPLE_HEAD_RIGHT_BULLET_Y = 32;
	Image imgs[];
	boolean attack;
	int headLevel;
	int burstShotsRemaining;
	Date burstStart;
	int count;
	int hp;
	Date start,end;
	
	public Wandou(int x, int y, int wide, int high,Image imgs [],int num) {//数组存豌豆射手
		super(x, y, wide, high);
		start=new Date();
		this.imgs=imgs;
		this.count=0;
		this.num=num;
		this.hp=100;
		this.headLevel=HEAD_SINGLE;
		this.burstShotsRemaining=0;
		this.burstStart=new Date();
		this.wide=NORMAL_W;
		this.high=NORMAL_H;
	}

	public boolean isSingleHead() {
		return this.headLevel==HEAD_SINGLE;
	}

	public boolean isDoubleHead() {
		return this.headLevel==HEAD_DOUBLE;
	}

	public boolean isTripleHead() {
		return this.headLevel==HEAD_TRIPLE;
	}

	public void setGridPosition(int gridX,int gridY) {
		this.y=gridY;
		if(this.headLevel==HEAD_DOUBLE) {
			this.x=gridX+DOUBLE_HEAD_OFFSET_X;
			this.wide=DOUBLE_HEAD_W;
			this.high=DOUBLE_HEAD_H;
		}else if(this.headLevel==HEAD_TRIPLE) {
			this.x=gridX+TRIPLE_HEAD_OFFSET_X;
			this.wide=TRIPLE_HEAD_W;
			this.high=TRIPLE_HEAD_H;
		}else {
			this.x=gridX;
			this.wide=NORMAL_W;
			this.high=NORMAL_H;
		}
	}

	public void upgradeToDoubleHead() {
		this.headLevel=HEAD_DOUBLE;
		this.imgs=GameFrame.doubleWandous;
		setGridPosition(GameFrame.glass.get(this.num).x, GameFrame.glass.get(this.num).y);
		this.count=0;
		this.burstShotsRemaining=0;
	}

	public void upgradeToTripleHead() {
		this.headLevel=HEAD_TRIPLE;
		this.imgs=GameFrame.tripleWandous;
		setGridPosition(GameFrame.glass.get(this.num).x, GameFrame.glass.get(this.num).y);
		this.count=0;
		this.burstShotsRemaining=0;
	}

	private void fireSinglePea() {
		GameFrame.bullet.add((new Bullet(this.x,this.y,GameFrame.bullets[0], false,10)));
	}

	private void fireDoublePea() {
		GameFrame.bullet.add((new Bullet(this.x + RIGHT_MOUTH_BULLET_X,this.y + RIGHT_MOUTH_BULLET_Y,GameFrame.bullets[0], false,10, 1)));
		GameFrame.bullet.add((new Bullet(this.x + LEFT_MOUTH_BULLET_X,this.y + LEFT_MOUTH_BULLET_Y,GameFrame.bullets[0], false,10, -1)));
	}

	private void fireTripleBurstPea(int shotIndex) {
		if(shotIndex==0) {
			GameFrame.bullet.add((new Bullet(this.x + TRIPLE_HEAD_TOP_BULLET_X,this.y,GameFrame.bullets[0], false,10, 1, 0, TRIPLE_HEAD_TOP_BULLET_Y)));
		}else if(shotIndex==1) {
			GameFrame.bullet.add((new Bullet(this.x + TRIPLE_HEAD_LEFT_BULLET_X,this.y + TRIPLE_HEAD_LEFT_BULLET_Y,GameFrame.bullets[0], false,10, 1)));
		}else {
			GameFrame.bullet.add((new Bullet(this.x + TRIPLE_HEAD_RIGHT_BULLET_X,this.y + TRIPLE_HEAD_RIGHT_BULLET_Y,GameFrame.bullets[0], false,10, 1)));
		}
	}

	private void updateTripleBurst(boolean hasTarget,boolean canStartBurst) {
		if(!hasTarget) {
			this.burstShotsRemaining=0;
			return;
		}
		if(this.burstShotsRemaining==0) {
			if(!canStartBurst) {
				return;
			}
			this.fireTripleBurstPea(0);
			this.burstShotsRemaining=2;
			this.burstStart=new Date();
			this.start=new Date();
			return;
		}
		Date now=new Date();
		long elapsed=now.getTime()-this.burstStart.getTime();
		int firedShots=2-this.burstShotsRemaining;
		while(this.burstShotsRemaining>0 && elapsed>= (long)TRIPLE_BURST_INTERVAL_MS*(firedShots+1)) {
			int shotIndex=3-this.burstShotsRemaining;
			this.fireTripleBurstPea(shotIndex);
			this.burstShotsRemaining--;
			firedShots++;
		}
	}

	@Override
	public void drawSelf(Graphics g) {
		if(this.hp<=0) {
			this.live=false;
		}
		if(this.live) {
			this.end=new Date();//用来设置豌豆射手发出子弹的频率-->1.5秒
			boolean hasTarget = PlantAttackUtil.hasZombieInLane(this.x,this.y);
			double shootInterval = (this.end.getTime()-this.start.getTime())*0.001;
			if(this.isTripleHead()) {
				boolean canStartBurst = hasTarget && (!this.attack || shootInterval>=1.5);
				if(canStartBurst && this.burstShotsRemaining==0) {
					GameFrame.util.playBGM("sounds/biu.wav", 1);
				}
				updateTripleBurst(hasTarget, canStartBurst);
			}else if(hasTarget && (!this.attack || shootInterval>=1.5)) {
				GameFrame.util.playBGM("sounds/biu.wav", 1);
				if(this.isDoubleHead()) {
					fireDoublePea();
				}else {
					fireSinglePea();
				}
				this.start=new Date();
			}//画出豌豆射手
			this.attack = hasTarget;
			if(count<imgs.length-1) {
				g.drawImage(imgs[this.count++],this.x,this.y,null);
			}else {
				g.drawImage(imgs[this.count++],this.x,this.y,null);
				this.count=0;
			}
		}else {
			GameFrame.glass.get(GameFrame.wandou.get(GameFrame.wandou.indexOf(this)).num).live =false;
			GameFrame.wandou.remove(GameFrame.wandou.indexOf(this));
		}
	}
}
//寒冰射手
class Hanbing extends GameObject{
	Image imgs[];
	int count;
	int hp;
	boolean attack;
	Date start,end;
	
	public Hanbing(int x, int y, int wide, int high,Image imgs [],int num) {//数组存寒冰射手
		super(x, y, wide, high);
		start=new Date();
		this.imgs=imgs;
		this.count=0;
		this.num=num;
		this.hp=100;
	}

	@Override
	public void drawSelf(Graphics g) {
		if(this.hp<=0) {
			this.live=false;
		}
		if(this.live) {
					this.end=new Date();//用来设置豌豆射手发出子弹的频率-->1.5秒
					boolean hasTarget = PlantAttackUtil.hasZombieInLane(this.x,this.y);
					double shootInterval = (this.end.getTime()-this.start.getTime())*0.001;
					if(hasTarget && (!this.attack || shootInterval>=1.5)) {
						GameFrame.util.playBGM("sounds/biu.wav", 1);
						GameFrame.bullet.add((new Bullet(this.x,this.y,GameFrame.bullets[1], true,5)));
						this.start=new Date();}
					//画出豌豆射手
					this.attack = hasTarget;
					if(count<imgs.length-1) {
						g.drawImage(imgs[this.count++],this.x,this.y,null);
						}else {
							g.drawImage(imgs[this.count++],this.x,this.y,null);
							this.count=0;
			}
		}else {
			GameFrame.glass.get(GameFrame.hanbing.get(GameFrame.hanbing.indexOf(this)).num).live =false;
			GameFrame.hanbing.remove(GameFrame.hanbing.indexOf(this));
		}
	}
}
//坚果
class Jianguo extends GameObject{
	Image imgs[];
	int count;
	int hp;
	int maxHp;
	
	public Jianguo(int x, int y, int wide, int high,Image imgs [],int num) {//数组存坚果
		super(x, y, wide, high); 
		this.imgs=imgs;
		this.count=0;
		this.num=num;
		this.hp=200;
		this.maxHp=200;
	}

	@Override
	public void drawSelf(Graphics g) {
		if(this.live) {
			if(this.hp>0) {
				int aliveFrameCount = imgs.length - 1;
				int damage = this.maxHp - Math.max(0, this.hp);
				int frameIndex = (int)((long)damage * aliveFrameCount / this.maxHp);
				if(frameIndex >= aliveFrameCount) {
					frameIndex = aliveFrameCount - 1;
				}
				this.count = Math.max(0, frameIndex);
				g.drawImage(imgs[this.count],this.x,this.y,null);
			}else {
				this.count = imgs.length - 1;
				g.drawImage(imgs[this.count],this.x,this.y,null);
				this.live=false;
			}
		}else {
			GameFrame.glass.get(GameFrame.jianguo.get(GameFrame.jianguo.indexOf(this)).num).live =false;
			GameFrame.jianguo.remove(GameFrame.jianguo.indexOf(this));
		}
	}
}
//子弹
class Bullet extends GameObject{
	boolean slow;
	int attack;
	int direction;
	int drawOffsetX;
	int drawOffsetY;
	public Bullet(int x, int y,Image img,boolean slow,int attack) {
		this(x, y, img, slow, attack, 1, 0, 0);
	}
	public Bullet(int x, int y,Image img,boolean slow,int attack,int direction) {
		this(x, y, img, slow, attack, direction, 0, 0);
	}
	public Bullet(int x, int y,Image img,boolean slow,int attack,int direction,int drawOffsetX,int drawOffsetY) {
		this.x=68+x;this.y=y;
		this.wide=26;this.high=26;
		this.img=img;
		this.attack=attack;
		this.slow=slow;
		this.direction=direction >= 0 ? 1 : -1;
		this.drawOffsetX=drawOffsetX;
		this.drawOffsetY=drawOffsetY;
	}
	@Override
	public void drawSelf(Graphics g) {
		if(this.live) {
			g.drawImage(img,this.x + this.drawOffsetX,this.y + this.drawOffsetY,null);
			this.x+=5*this.direction;//子弹的速度
		}
		if(this.x>1400 || this.x<-50) {this.live=false;}
	}
}
//旗帜僵尸
class QiZhi extends GameObject {
	boolean attacking,moving,dieing;
	int attack,hp,count,pengtime;//pengtime处理刚碰得植物计时
	Date attack_start,attack_end;//处理攻击间隔
	double x,speed,slow_speed;
	Image imgs[];
	
	 public QiZhi(Image imgs[],int y,double speed,int attack,int hp) {//构造函数,默认初始移动为true，攻击，减速，死亡为false
		this.x=1100;this.y=y;this.attacking=false;this.moving=true;this.dieing=false;
		this.imgs=imgs;this.speed=speed;this.attack=attack;this.hp=hp;
		this.wide=45;this.high=60;this.slow_speed=speed/2.0;
		this.pengtime=1;
	}
	
	
	@Override
	public void drawSelf(Graphics g) {
		if(this.x<-150) {
			GameFrame.loadtime=3;
			this.x+=10;
		}
		if(this.hp<=0) {
			this.attacking=false;
			this.moving=false;
			this.dieing=true;
		}
		if(this.live) {
			if(this.moving) {
				g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
				this.x-=this.speed;
				if(this.count==48) {this.count=0;}
			}else if(this.attacking) {
				g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
				if(this.count==88) {this.count=48;}
			}else if(this.dieing) {
				g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
			    if(this.count==134) {
			    	this.live=false;
			    	GameFrame.num++;
			    }
			}
			if(!GameFrame.bullet.isEmpty()&&this.hp>0) {//生命大于0子弹不空就遍历数组,矩形相交则减生命值,子弹移除
				for(int i=0;i<GameFrame.bullet.size();i++) {
					if(GameFrame.bullet.get(i).getRect().intersects(this.getRect())) {//子弹的矩形和僵尸的矩形相交,即子弹击中僵尸
						if(GameFrame.bullet.get(i).slow) {this.speed=this.slow_speed;}//僵尸减速
						this.hp-=GameFrame.bullet.get(i).attack;//血量降低
						GameFrame.util.playBGM("sounds/attack-qizhi.wav",1);//击中音效
						GameFrame.bullet.remove(i);//移除子弹
						if(this.hp<=0) {
							count=88;
							this.attacking=false;this.moving=false;this.dieing=true;
						}
					}
				}
			}
			//向日葵遭受攻击
			for(int i=0;i<GameFrame.flower.size();i++) {
				if(this.getRect().intersects(GameFrame.flower.get(i).getRect())) {//向日葵与僵尸碰撞
					if(GameFrame.flower.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									this.count=48;this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.flower.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.flower.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//坚果遭受攻击
			for(int i=0;i<GameFrame.jianguo.size();i++) {
				if(this.getRect().intersects(GameFrame.jianguo.get(i).getRect())) {//坚果与僵尸碰撞
					if(GameFrame.jianguo.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									this.count=48;this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.jianguo.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.jianguo.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//豌豆射手遭受攻击
			for(int i=0;i<GameFrame.wandou.size();i++) {
				if(this.getRect().intersects(GameFrame.wandou.get(i).getRect())) {//豌豆与僵尸碰撞
					if(GameFrame.wandou.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									this.count=48;this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.wandou.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.wandou.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//寒冰射手遭受攻击
			for(int i=0;i<GameFrame.hanbing.size();i++) {
				if(this.getRect().intersects(GameFrame.hanbing.get(i).getRect())) {//寒冰射手与僵尸碰撞
					if(GameFrame.hanbing.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									this.count=48;this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.hanbing.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.hanbing.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
		}
		
		
	}


	@Override
	public Rectangle getRect() {
		return new Rectangle((int)(this.x+105),this.y+50,this.wide,this.high);//110是图片左侧离僵尸身体的数字
	}
}
//铁通僵尸
class TieTong extends GameObject{

	boolean attacking,moving,dieing;
	int attack,hp,count,pengtime,maxHp;//pengtime处理刚碰到植物计时
	Date attack_start,attack_end;//处理攻击间隔
	double x,speed,slow_speed;
	Image imgs[];
	
	 public TieTong(Image imgs[],int y,double speed,int attack,int hp) {//构造函数,默认初始移动为true，攻击，减速，死亡为false
		this.x=1100;this.y=y;this.attacking=false;this.moving=true;this.dieing=false;
		this.imgs=imgs;this.speed=speed;this.attack=attack;this.hp=hp;this.maxHp=hp;
		this.wide=45;this.high=60;this.slow_speed=speed/2.0;
		this.pengtime=1;
	}
	
	
	@Override
	public void drawSelf(Graphics g) {
		if(this.x<-150) {
			GameFrame.loadtime=3;
			this.x+=10;
		}
		if(this.live) {
			if(this.moving) {
				if(this.hp>this.maxHp/2) {//高血量僵尸行走
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					this.x-=this.speed;
					if(this.count==48) {this.count=0;}
				}else {//低血量僵尸行走
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					this.x-=2*this.speed;
					if(this.count==143) {this.count=96;}
				}
			}else if(this.attacking) {
				if(this.hp>this.maxHp/2) {//高血量僵尸攻击
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					if(this.count==96) {this.count=48;}
				}else {//低血量僵尸攻击
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					if(this.count==191) {this.count=143;}
				}
			}else if(this.dieing) {
				this.moving=false;this.attacking=false;
				g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
			    if(this.count==239) {
			    	this.live=false;
			    	GameFrame.num++;
			    }
			}
			if(this.hp>0) {//生命大于0子弹不空就遍历数组,矩形相交则减生命值,子弹移除
				for(int i=0;i<GameFrame.bullet.size();i++) {
					if(GameFrame.bullet.get(i).getRect().intersects(this.getRect())) {//子弹的矩形和僵尸的矩形相交,即子弹击中僵尸
						if(GameFrame.bullet.get(i).slow) {this.speed=this.slow_speed;}//僵尸减速
						if(this.hp>this.maxHp/2) {
							GameFrame.util.playBGM("sounds/attack-tietong.wav",1);//高血量击中音效
						}else {
							GameFrame.util.playBGM("sounds/attack-qizhi.wav",1);//低血量击中音效
						}
						if(this.hp>this.maxHp/2 && this.hp-GameFrame.bullet.get(i).attack<=this.maxHp/2) {//僵尸进入低血量阶段
							if(this.moving) {
								this.count=96;
							}else if(this.attacking) {
								this.count=143;
							}
						}else if(this.hp-GameFrame.bullet.get(i).attack<=0) {//判断僵尸血量是否要被击杀
							this.count=191;
							this.attacking=false;this.moving=false;this.dieing=true;
						}
						this.hp-=GameFrame.bullet.get(i).attack;//血量降低
						GameFrame.bullet.remove(i);//移除子弹
					}
				}
			}else {
				this.dieing=true;
			}
			//向日葵遭受攻击
			for(int i=0;i<GameFrame.flower.size();i++) {
				if(this.getRect().intersects(GameFrame.flower.get(i).getRect())) {//向日葵与僵尸碰撞
					if(GameFrame.flower.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.flower.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.flower.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//坚果遭受攻击
			for(int i=0;i<GameFrame.jianguo.size();i++) {
				if(this.getRect().intersects(GameFrame.jianguo.get(i).getRect())) {//坚果与僵尸碰撞
					if(GameFrame.jianguo.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.jianguo.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.jianguo.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//豌豆射手遭受攻击
			for(int i=0;i<GameFrame.wandou.size();i++) {
				if(this.getRect().intersects(GameFrame.wandou.get(i).getRect())) {//豌豆与僵尸碰撞
					if(GameFrame.wandou.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.wandou.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.wandou.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//寒冰射手遭受攻击
			for(int i=0;i<GameFrame.hanbing.size();i++) {
				if(this.getRect().intersects(GameFrame.hanbing.get(i).getRect())) {//寒冰射手与僵尸碰撞
					if(GameFrame.hanbing.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.hanbing.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.hanbing.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
		}
		
		
	}


	@Override
	public Rectangle getRect() {
		return new Rectangle((int)(this.x+75),this.y+50,this.wide,this.high);//110是图片左侧离僵尸身体的数字
	}
}
//报纸僵尸
class BaoZhi extends GameObject {
	boolean attacking,moving,dieing;
	int attack,hp,count,pengtime,maxHp;//pengtime处理刚碰到植物计时
	Date attack_start,attack_end;//处理攻击间隔
	double x,speed,slow_speed;
	Image imgs[];
	
	 public BaoZhi(Image imgs[],int y,double speed,int attack,int hp) {//构造函数,默认初始移动为true，攻击，减速，死亡为false
		this.x=1100;this.y=y;this.attacking=false;this.moving=true;this.dieing=false;
		this.imgs=imgs;this.speed=speed;this.attack=attack;this.hp=hp;this.maxHp=hp;
		this.wide=45;this.high=70;this.slow_speed=speed/2.0;
		this.pengtime=1;
	}

	private boolean isTouchingLivePlant() {
		for(int i=0;i<GameFrame.flower.size();i++) {
			if(GameFrame.flower.get(i).hp>0 && this.getRect().intersects(GameFrame.flower.get(i).getRect())) {
				return true;
			}
		}
		for(int i=0;i<GameFrame.jianguo.size();i++) {
			if(GameFrame.jianguo.get(i).hp>0 && this.getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
				return true;
			}
		}
		for(int i=0;i<GameFrame.wandou.size();i++) {
			if(GameFrame.wandou.get(i).hp>0 && this.getRect().intersects(GameFrame.wandou.get(i).getRect())) {
				return true;
			}
		}
		for(int i=0;i<GameFrame.hanbing.size();i++) {
			if(GameFrame.hanbing.get(i).hp>0 && this.getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void drawSelf(Graphics g) {
		if(this.x<-150) {
			GameFrame.loadtime=3;
			this.x+=10;
		}
		if(this.hp<=0) {
			this.attacking=false;
			this.moving=false;
			this.dieing=true;
		}
		if(this.live) {
			if(this.attacking && !this.dieing && !isTouchingLivePlant()) {
				this.pengtime=1;
				this.attacking=false;
				this.moving=true;
				this.count=this.hp>this.maxHp/2 ? 0 : 94;
			}
			if(this.moving) {
				if(this.hp>this.maxHp/2) {//高血量僵尸行走
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					this.x-=this.speed;
					if(this.count==47) {this.count=0;}
				}else {//低血量僵尸行走
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					this.x-=2*this.speed;
					if(this.count==141) {this.count=94;}
				}
			}else if(this.attacking) {
				if(this.hp>this.maxHp/2) {//高血量僵尸攻击
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					if(this.count==94) {this.count=47;}
				}else {//低血量僵尸攻击
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					if(this.count==187) {this.count=141;}
				}
			}else if(this.dieing) {//187-----234
				this.moving=false;this.attacking=false;
				g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
			    if(this.count==235) {
			    	this.live=false;
			    	GameFrame.num++;
			    }
			}
			if(this.hp>0) {//生命大于0子弹不空就遍历数组,矩形相交则减生命值,子弹移除
				for(int i=0;i<GameFrame.bullet.size();i++) {
					if(GameFrame.bullet.get(i).getRect().intersects(this.getRect())) {//子弹的矩形和僵尸的矩形相交,即子弹击中僵尸
						if(GameFrame.bullet.get(i).slow) {this.speed=this.slow_speed;}//僵尸减速
						if(this.hp>this.maxHp/2) {
							GameFrame.util.playBGM("sounds/attack-qizhi.wav",1);//高血量击中音效
						}else {
							GameFrame.util.playBGM("sounds/attack-qizhi.wav",1);//低血量击中音效
						}
						if(this.hp>this.maxHp/2 && this.hp-GameFrame.bullet.get(i).attack<=this.maxHp/2) {//僵尸进入低血量阶段
							if(this.moving) {
								this.count=94;
							}else if(this.attacking) {
								this.count=141;
							}
						}else if(this.hp-GameFrame.bullet.get(i).attack<=0) {//判断僵尸血量是否要被击杀
							this.count=187;
							this.attacking=false;this.moving=false;this.dieing=true;
						}
						this.hp-=GameFrame.bullet.get(i).attack;//血量降低
						GameFrame.bullet.remove(i);//移除子弹
					}
				}
			}
			//向日葵遭受攻击
			for(int i=0;i<GameFrame.flower.size();i++) {
				if(this.getRect().intersects(GameFrame.flower.get(i).getRect())) {//向日葵与僵尸碰撞
					if(GameFrame.flower.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.flower.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.flower.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//坚果遭受攻击
			for(int i=0;i<GameFrame.jianguo.size();i++) {
				if(this.getRect().intersects(GameFrame.jianguo.get(i).getRect())) {//坚果与僵尸碰撞
					if(GameFrame.jianguo.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.jianguo.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.jianguo.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//豌豆射手遭受攻击
			for(int i=0;i<GameFrame.wandou.size();i++) {
				if(this.getRect().intersects(GameFrame.wandou.get(i).getRect())) {//豌豆与僵尸碰撞
					if(GameFrame.wandou.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.wandou.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.wandou.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//寒冰射手遭受攻击
			for(int i=0;i<GameFrame.hanbing.size();i++) {
				if(this.getRect().intersects(GameFrame.hanbing.get(i).getRect())) {//寒冰射手与僵尸碰撞
					if(GameFrame.hanbing.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=143;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.hanbing.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.hanbing.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
		}
		
		
	}


	@Override
	public Rectangle getRect() {
		return new Rectangle((int)(this.x+45),this.y+50,this.wide,this.high);//110是图片左侧离僵尸身体的数字
	}

}
//橄榄球僵尸
class GanLan extends GameObject{

	boolean attacking,moving,dieing;
	int attack,hp,count,pengtime,maxHp;//pengtime处理刚碰到植物计时
	Date attack_start,attack_end;//处理攻击间隔
	double x,speed,slow_speed;
	Image imgs[];
	
	 public GanLan(Image imgs[],int y,double speed,int attack,int hp) {//构造函数,默认初始移动为true，攻击，减速，死亡为false
		this.x=1100;this.y=y;this.attacking=false;this.moving=true;this.dieing=false;
		this.imgs=imgs;this.speed=speed;this.attack=attack;this.hp=hp;this.maxHp=hp;
		this.wide=35;this.high=60;this.slow_speed=speed/2.0;
		this.pengtime=1;
	}
	
	
	@Override
	public void drawSelf(Graphics g) {
		if(this.x<-150) {
			GameFrame.loadtime=3;
			this.x+=10;
		}
		if(this.hp<=0) {
			this.attacking=false;
			this.moving=false;
			this.dieing=true;
		}
		if(this.live) {
			if(this.moving) {
				if(this.hp>this.maxHp/2) {//高血量僵尸行走
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					this.x-=this.speed;
					if(this.count==48) {this.count=0;}
				}else {//低血量僵尸行走
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					this.x-=2*this.speed;
					if(this.count==144) {this.count=96;}
				}
			}else if(this.attacking) {
				if(this.hp>this.maxHp/2) {//高血量僵尸攻击
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					if(this.count==96) {this.count=48;}
				}else {//低血量僵尸攻击
					g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
					if(this.count==192) {this.count=144;}
				}
			}else if(this.dieing) {
				this.moving=false;this.attacking=false;
				g.drawImage(imgs[this.count++],(int)this.x,this.y, null);
			    if(this.count==239) {
			    	this.live=false;
			    	GameFrame.num++;
			    }
			}
			   if(this.hp>0) {//生命大于0子弹不空就遍历数组,矩形相交则减生命值,子弹移除
				for(int i=0;i<GameFrame.bullet.size();i++) {
					if(GameFrame.bullet.get(i).getRect().intersects(this.getRect())) {//子弹的矩形和僵尸的矩形相交,即子弹击中僵尸
						if(GameFrame.bullet.get(i).slow) {this.speed=this.slow_speed;}//僵尸减速
						if(this.hp>this.maxHp/2) {
							GameFrame.util.playBGM("sounds/attack-tietong.wav",1);//高血量击中音效
						}else {
							GameFrame.util.playBGM("sounds/attack-qizhi.wav",1);//低血量击中音效
						}
						if(this.hp>this.maxHp/2 && this.hp-GameFrame.bullet.get(i).attack<=this.maxHp/2) {//僵尸进入低血量阶段
							if(this.moving) {
								this.count=96;
							}else if(this.attacking) {
								this.count=144;
							}
						}else if(this.hp-GameFrame.bullet.get(i).attack<=0) {//判断僵尸血量是否要被击杀
							this.count=192;
							this.attacking=false;this.moving=false;this.dieing=true;
						}
						this.hp-=GameFrame.bullet.get(i).attack;//血量降低
						GameFrame.bullet.remove(i);//移除子弹
					}
				}
			  }
			//向日葵遭受攻击
			for(int i=0;i<GameFrame.flower.size();i++) {
				if(this.getRect().intersects(GameFrame.flower.get(i).getRect())) {//向日葵与僵尸碰撞
					if(GameFrame.flower.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=144;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.flower.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.flower.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.flower.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//坚果遭受攻击
			for(int i=0;i<GameFrame.jianguo.size();i++) {
				if(this.getRect().intersects(GameFrame.jianguo.get(i).getRect())) {//坚果与僵尸碰撞
					if(GameFrame.jianguo.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=144;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.jianguo.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.jianguo.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.jianguo.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//豌豆射手遭受攻击
			for(int i=0;i<GameFrame.wandou.size();i++) {
				if(this.getRect().intersects(GameFrame.wandou.get(i).getRect())) {//豌豆与僵尸碰撞
					if(GameFrame.wandou.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=144;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.wandou.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.wandou.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.wandou.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
			//寒冰射手遭受攻击
			for(int i=0;i<GameFrame.hanbing.size();i++) {
				if(this.getRect().intersects(GameFrame.hanbing.get(i).getRect())) {//寒冰射手与僵尸碰撞
					if(GameFrame.hanbing.get(i).hp>0){//生命值大于0，被吃中
								if(this.pengtime==1) {//刚接触，起始时间初始化
									if(this.hp>this.maxHp/2) {
										this.count=48;
									}else {
										this.count=144;
									}
									this.attack_start=new Date();this.pengtime++;
									this.moving=false;this.attacking=true;
								}
								this.attack_end=new Date();
								if((this.attack_end.getTime()-this.attack_start.getTime())*0.001>=1){//隔一秒减血量
									if(GameFrame.hanbing.get(i).hp-this.attack<=0) {
										for(int j=0;j<GameFrame.qizhi.size();j++) {//所有在吃的旗帜僵尸恢复状态
											if(GameFrame.qizhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												GameFrame.qizhi.get(j).pengtime=1;GameFrame.qizhi.get(j).count=0;
												GameFrame.qizhi.get(j).attacking=false;GameFrame.qizhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.tietong.size();j++) {//所有在吃的铁桶僵尸恢复状态
											if(GameFrame.tietong.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.tietong.get(j).hp>100) {
													GameFrame.tietong.get(j).count=0;
												}else {
													GameFrame.tietong.get(j).count=96;
												}
												GameFrame.tietong.get(j).pengtime=1;
												GameFrame.tietong.get(j).attacking=false;GameFrame.tietong.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.baozhi.size();j++) {//所有在吃的报纸僵尸恢复状态
											if(GameFrame.baozhi.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.baozhi.get(j).hp>100) {
													GameFrame.baozhi.get(j).count=0;
												}else {
													GameFrame.baozhi.get(j).count=94;
												}
												GameFrame.baozhi.get(j).pengtime=1;
												GameFrame.baozhi.get(j).attacking=false;GameFrame.baozhi.get(j).moving=true;
											}
										}
										for(int j=0;j<GameFrame.ganlan.size();j++) {//所有在吃的橄榄球僵尸恢复状态
											if(GameFrame.ganlan.get(j).getRect().intersects(GameFrame.hanbing.get(i).getRect())) {
												if(GameFrame.ganlan.get(j).hp>100) {
													GameFrame.ganlan.get(j).count=0;
												}else {
													GameFrame.ganlan.get(j).count=96;
												}
												GameFrame.ganlan.get(j).pengtime=1;
												GameFrame.ganlan.get(j).attacking=false;GameFrame.ganlan.get(j).moving=true;
											}
										}
									}
									GameFrame.util.playBGM("sounds/eat"+(int)(Math.random()*3)+".wav",1);//被吃三中音效选一个播放
									GameFrame.hanbing.get(i).hp-=this.attack;
									this.attack_start=new Date();//起始时间重置
								}
							}
				}
			}
		}
		
		
	}


	@Override
	public Rectangle getRect() {
		return new Rectangle((int)(this.x+60),this.y+50,70,this.high);//橄榄球僵尸身体更宽，命中框需要覆盖主要躯干
	}

}
