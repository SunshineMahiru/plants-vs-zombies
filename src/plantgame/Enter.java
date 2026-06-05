package plantgame;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
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
				gameCanvas.repaint();
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

	// 游戏画布，确保客户区精确900x600
	Canvas gameCanvas;
	// 双缓冲图像
	private Image offScreenImage = null;

	//构造方法
	public Enter() {
		Image ico=GameUtil.getImage(("LoadFrame/ico.png"));/*植物大战僵尸图标*/
		this.setTitle("植物VS僵尸");
		this.setIconImage(ico);   /*设置窗口的logo*/
		this.setLayout(new BorderLayout());

		// 创建固定大小的Canvas，确保客户区精确900x600
		gameCanvas = new Canvas() {
			@Override
			public void paint(Graphics g) {
				paintContent(g);
			}
			@Override
			public void update(Graphics g) {
				// 双缓冲
				if(offScreenImage==null) offScreenImage=this.createImage(900,600);
				Graphics gOff=offScreenImage.getGraphics();
				paintContent(gOff);
				g.drawImage(offScreenImage,0,0,null);
			}
		};
		gameCanvas.setPreferredSize(new Dimension(900, 600));
		this.add(gameCanvas, BorderLayout.CENTER);
		this.pack();
		this.setResizable(false);
		this.setLocation(300,100);
		this.setVisible(true);

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
		gameCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(LoadFrame.live) {//加载界面单击事件
					LoadFrame.MouseClick(e);
				}else if(MenuFrame.live) {//主菜单的点击事件
					MenuFrame.MouseClick(e);
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
		//右键取消选择植物
		gameCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3 && GameFrame.live) {
					GameFrame.cancelSelect();
				}
			}
		});
		//内部类监听鼠移动击事件
		gameCanvas.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(LoadFrame.live) {//加载界面的移动事件
					LoadFrame.MouseMove(e);
				}else if(MenuFrame.live) {//主菜单的移动事件
					MenuFrame.MouseMove(e);
				}else if(HelpFrame.live) {//Help的移动事件
					HelpFrame.MouseMove(e);
				}else if(MiniFrame.live) {//Mini的移动事件
					MiniFrame.MouseMove(e);
				}else if(PuzzleFrame.live) {//Puzzle的点击事件
					PuzzleFrame.MouseMove(e);
				}else if(SurvivalFrame.live) {//Survival的点击事件
					SurvivalFrame.MouseMove(e);
				}else if(GameFrame.live) {//GameFrame的移动事件
					GameFrame.MouseMove(e);
				}
			}
		});
	}

	// 统一绘制方法
	public void paintContent(Graphics g) {
		// 清屏
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, 900, 600);

		if(LoadFrame.live) {//绘制加载界面
			LoadFrame.draw(g);
		}else if(MenuFrame.live) {//绘制主菜单界面
			MenuFrame.draw(g);
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
}
