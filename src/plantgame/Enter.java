package plantgame;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Enter extends Frame{
	//一直重画的线程
	class HelpRepaint extends Thread{
		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/*
	 * main方法
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		new LoadFrame();/*提前载入LoadFrame类，防止画面不一致*/
		new GameFrame();
		new Enter();
	}
	//构造方法
	public Enter() {
		Image ico=GameUtil.getImage(("LoadFrame/ico.png"));/*植物大战僵尸图标*/
		this.setTitle("植物VS僵尸");
		this.setIconImage(ico);   /*设置窗口的logo*/
		this.setSize(800,632);/*场景的长和宽*/
		this.setLocation(300,100);
		this.setVisible(true);
		this.setResizable(false);
		LoadFrame.live=true;
		LoadFrame.util.playBGM();
		//新建线程
		HelpRepaint help=new HelpRepaint();
		help.start();
		//内部类完成×关闭功能
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//内部类监听鼠标点击事件
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(LoadFrame.live) {//加载界面单击事件
					LoadFrame.MouseClick(e);
				}else if(MenuFrame.live) {//主菜单的点击事件
					MenuFrame.MouseClick(e);
				}else if(AchieveFrame.live) {//成就系统的点击事件
					AchieveFrame.MouseClick(e);
				}else if(GardenFrame.live) {//花园的点击事件
					GardenFrame.MouseClick(e);
				}else if(TuJianFrame.live) {//图鉴的点击事件
					TuJianFrame.MouseClick(e);
				}else if(ShopFrame.live) {//商店的点击事件
					ShopFrame.MouseClick(e);
				}else if(HelpFrame.live) {//Help的点击事件
					HelpFrame.MouseClick(e);
				}else if(MiniFrame.live) {//Mini的点击事件
					MiniFrame.MouseClick(e);
				}else if(PuzzleFrame.live) {//Puzzle的点击事件
					PuzzleFrame.MouseClick(e);
				}else if(SurvivalFrame.live) {//Survival的点击事件
					SurvivalFrame.MouseClick(e);
				}else if(GameFrame.live) {//GameFrame的点击事件
					GameFrame.MouseClick(e);
				}
				
				
				
				
				
				
			}
		});
		//内部类监听鼠移动击事件
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(LoadFrame.live) {//加载界面的移动事件
					LoadFrame.MouseMove(e);
				}else if(MenuFrame.live) {//主菜单的移动事件
					MenuFrame.MouseMove(e);
				}else if(AchieveFrame.live) {//成就系统的移动事件
					AchieveFrame.MouseMove(e);
				}else if(GardenFrame.live) {//花园的移动事件
					GardenFrame.MouseMove(e);
				}else if(TuJianFrame.live) {//图鉴的移动事件
					TuJianFrame.MouseMove(e);
				}else if(ShopFrame.live) {//商店的移动事件
					ShopFrame.MouseMove(e);
				}else if(HelpFrame.live) {//Help的移动事件
					HelpFrame.MouseMove(e);
				}else if(MiniFrame.live) {//Mini的移动事件
					MiniFrame.MouseMove(e);
				}else if(PuzzleFrame.live) {//Puzzle的移动事件
					PuzzleFrame.MouseMove(e);
				}else if(SurvivalFrame.live) {//Survival的移动事件
					SurvivalFrame.MouseMove(e);
				}else if(GameFrame.live) {//GameFrame的移动事件
					GameFrame.MouseMove(e);
				}
				
				
				
				
				
				
				
				
			}
		});
	}
	
	
	
	
	
	public void paint(Graphics g) {
		if(LoadFrame.live) {//绘制加载界面
			LoadFrame.draw(g);
		}else if(MenuFrame.live) {//绘制主菜单界面
			MenuFrame.draw(g);
		}else if(AchieveFrame.live) {//绘制成就界面
			AchieveFrame.draw(g);
		}else if(GardenFrame.live) {//绘制花园界面
			GardenFrame.draw(g);
		}else if(TuJianFrame.live) {//绘制图鉴界面
			TuJianFrame.draw(g);
		}else if(ShopFrame.live) {//绘制商店界面
			ShopFrame.draw(g);
		}else if(HelpFrame.live) {//绘制Help界面
			HelpFrame.draw(g);
		}else if(MiniFrame.live) {//绘制Mini界面
			MiniFrame.draw(g);
		}else if(PuzzleFrame.live) {//绘制Puzzle界面
			PuzzleFrame.draw(g);
		}else if(SurvivalFrame.live) {//绘制Survival界面
			SurvivalFrame.draw(g);
		}else if(GameFrame.live) {//绘制开始游戏界面
			GameFrame.draw(g);
		}
		
		
		
		
		
	}
	
	
	/*
	 * 双缓冲解决闪烁问题
	 */
	private Image offScreenImage=null;
	public void update(Graphics g)
	{
		if(offScreenImage==null)offScreenImage=this.createImage(804,640);
		Graphics gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage,0,0,null);
	}
}
