package plantgame;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
/*
 * GameFrame????
 */
import java.util.ArrayList;
import java.util.Date;
public class GameFrame  {
	static ArrayList<Glass> glass=new ArrayList<>();//???????????
	 static ArrayList<Car> car=new ArrayList<>();//??????
	 static ArrayList<Card> card=new ArrayList<>();//?????????
	 static ArrayList<Flower> flower=new ArrayList<>();//????
	 static ArrayList<Wandou> wandou=new ArrayList<>();//?????????
	 static ArrayList<Hanbing> hanbing=new ArrayList<>();//??????????
	 static ArrayList<Jianguo> jianguo=new ArrayList<>();//?????
	 static ArrayList<Sun> sunings=new ArrayList<>();//???????
	 static ArrayList<Bullet> bullet=new ArrayList<>();//??????
	 static ArrayList<QiZhi> qizhi=new ArrayList<>();//?????????
	 static ArrayList<TieTong> tietong=new ArrayList<>();//?????????
	 static ArrayList<BaoZhi> baozhi=new ArrayList<>();//????????
	 static ArrayList<GanLan> ganlan=new ArrayList<>();//?????????
	 static Shove shove;
	 static boolean backgame,backmenu,restart;
	 static int x;
	 static int m=0;//??????????
	 static int y;
	 static int loadtime;
	 static int op;
	 static int grade;//???????????????
	 static boolean live;
	 static GameUtil util;
	 static Image img[];
	 static Image suns[];//????????s??????arraylist???
	 static Image flowers[];
	 static Image wandous[];
	 static Image hanbings[];
	 static Image jianguos[];
	 static Image bullets[];//???
	 static Image qizhis[];//??????
	 static Image tietongs[];//??????
	 static Image ganlans[];//??????
	 static Image baozhis[];//??????
	 static int sun;
	 static int num;//???????????
	 static Date startTime;//?????????? 
	 static Date stopTime;
	 static Date start;//?????????
	 static Date stop;
	 static Sun suning;
	 
	 //???????????????,?????????live?false
	static {
		for(int i=0;i<5;i++) {
			for(int j=0;j<9;j++) {
				glass.add(new Glass(46+82*j,115+100*i));	
			}
		}
		grade=1;
		sun=50;
		 x=0;
		 y=30;
		 loadtime=0;
		 op=2;
		 live=false; 
		 util=new GameUtil();
		 util.loadBGM("sounds/bgm8.wav");
		 img=new Image[21];
		 // flowers loaded from GIF
		 wandous=new Image[43];
		 hanbings=new Image[39];
		 jianguos=new Image[115];
		 bullets=new Image[2];
		 qizhis=new Image[134];//???????????
		 tietongs=new Image[239];
		 ganlans=new Image[239];
		 baozhis=new Image[235];
		 for(int i=0;i<img.length;i++) {
			 img[i]=GameUtil.getImage("GameFrame/back"+i+".png");
			 }
		 suns=GameUtil.getGifFrames("Sun/Sun.gif");
		 flowers=GameUtil.getGifFrames("flower/SunFlower.gif");
		 for(int i=0;i<wandous.length;i++) {
			 wandous[i]=GameUtil.getImage("wandou/wandou ("+(i+1)+").png");
			 }
		 for(int i=0;i<hanbings.length;i++) {
			 hanbings[i]=GameUtil.getImage("hanbing/hanbing ("+(i+1)+").png");
			 }
		 for(int i=0;i<jianguos.length;i++) {
			 jianguos[i]=GameUtil.getImage("jianguo/jianguo ("+(i+1)+").png");
			 }
		 for(int i=0;i<bullets.length;i++) {
			 bullets[i]=GameUtil.getImage("Bullets/bullet"+(i+1)+".png");
			 }
		 for(int i=0;i<qizhis.length;i++) {
			 qizhis[i]=GameUtil.getImage("Zombies/qizhi/qizhi ("+(i+1)+").png");
			 }
		 for(int i=0;i<tietongs.length;i++) {
			 tietongs[i]=GameUtil.getImage("Zombies/tietong/tietong ("+(i+1)+").png");
			 }
		 for(int i=0;i<ganlans.length;i++) {
			 ganlans[i]=GameUtil.getImage("Zombies/ganlan/ganlan ("+(i+1)+").png");
			 }
		 for(int i=0;i<baozhis.length;i++) {
			 baozhis[i]=GameUtil.getImage("Zombies/baozhi/baozhi ("+(i+1)+").png");
			 }
		 
		 
		 
		 //????
		 shove=new Shove(487,29,60,65,0,img[5]);
		 //??????????car??arrylist
		 for(int i=1;i<6;i++) {
			 car.add(new Car(1-(i-1)*3,30+i*100,46,64,8,img[4]));
			 }
		 //????????????card??arrylist
			 card.add(new Card(127+0*52,49,38,45,25,img[12],img[13]));//?????
			 card.add(new Card(127+1*52,49,38,45,100,img[14],img[15]));//??????
			 card.add(new Card(127+2*52,49,38,45,150,img[16],img[17]));//????????
			 card.add(new Card(127+3*52,49,38,45,50,img[18],img[19]));//???	
		 }
	//???????????
	public static void draw(Graphics g) {
		Font font =g.getFont();//???滭???????????
		g.setFont(new Font("??????",Font.BOLD+Font.ITALIC,25));
		stopTime=new Date();
		if(loadtime==0) {//???????????
			suning=new Sun((int)(Math.random()*500)+200,50,50,(int)(Math.random()*350)+200,suns);
			g.drawImage(img[0],-212,y,null);
			 startTime=new Date();//?????????
			 start=new Date();
			loadtime++;	
		}else if(loadtime==1) {//??????
			if((int)((stopTime.getTime()-startTime.getTime())*0.001)%7==0) {//???????????????
				suning=new Sun((int)(Math.random()*500)+200,50,50,(int)(Math.random()*350)+200,suns);
				suning.live=true;suning.move=false;
			}
			
			g.drawImage(img[1],x,y-1,null);//????
			g.drawString(sun+"",70, 112);//???????
			
			g.setFont(font);//???????
			
			g.drawImage(img[op],x,y,null);//?????????
			stop =new  Date();
				if((int)((stopTime.getTime()-startTime.getTime())*0.001)>=20) {//40??????????????	
					if(num>=0&&num<=5) {
						if((int)((stop.getTime()-start.getTime())*0.001)>=((int)(Math.random()*5)+20)) {
								qizhi.add(new QiZhi(qizhis,(int)(Math.random()*5)*100+50,0.6,10,100));	
								start=new Date();
						}
					}else if(num>5&&num<=25) {
						if((int)((stop.getTime()-start.getTime())*0.001)>=((int)(Math.random()*5)+15)) {
							int x=(int)(Math.random()*16);
							if(x<5&&x>=0) {
								qizhi.add(new QiZhi(qizhis,(int)(Math.random()*5)*100+50,1,20,100));
							}else if(x>=5&&x<11) {
								tietong.add(new TieTong(tietongs,(int)(Math.random()*5)*100+50,0.8,15,200));
							}else if(x>=11&&x<14) {
								baozhi.add(new BaoZhi(baozhis,(int)(Math.random()*5)*100+50,0.8,15,200));
							}else {
								ganlan.add(new GanLan(ganlans,(int)(Math.random()*5)*100+50,0.8,15,200));
							}	
							start=new Date();
						}
					}else if(num>25&&num<=50) {
						if((int)((stop.getTime()-start.getTime())*0.001)>=((int)(Math.random()*5)+10)) {
							for(int i=0;i<2;i++) {//????????????
								int x=(int)(Math.random()*16);
								if(x<4&&x>=0) {
									qizhi.add(new QiZhi(qizhis,(int)(Math.random()*5)*100+50,1,20,100));
								}else if(x>=4&&x<11) {
									tietong.add(new TieTong(tietongs,(int)(Math.random()*5)*100+50,0.8,15,200));
								}else if(x>=11&&x<14) {
									baozhi.add(new BaoZhi(baozhis,(int)(Math.random()*5)*100+50,0.8,15,200));
								}else {
									ganlan.add(new GanLan(ganlans,(int)(Math.random()*5)*100+50,0.8,15,200));
								}
							}
							start=new Date();
						}
					}else if(num>50){
						if((int)((stop.getTime()-start.getTime())*0.001)>=10+((int)(Math.random()*5))) {
							for(int i=0;i<2;i++) {
								int x=(int)(Math.random()*16);
								if(x<3&&x>=0) {
									qizhi.add(new QiZhi(qizhis,(int)(Math.random()*5)*100+50,1,20,100));
								}else if(x>=3&&x<9) {
									tietong.add(new TieTong(tietongs,(int)(Math.random()*5)*100+50,0.8,15,200));
								}else if(x>=9&&x<14) {
									baozhi.add(new BaoZhi(baozhis,(int)(Math.random()*5)*100+50,0.8,15,200));
								}else {
									ganlan.add(new GanLan(ganlans,(int)(Math.random()*5)*100+50,0.8,15,200));
								}	
							}
							start=new Date();
						}
					}
				}
			
				for(int i=0;i<qizhi.size();i++) {//??????
					if(qizhi.get(i).live) {
						qizhi.get(i).drawSelf(g);
					}else {
						qizhi.remove(i);
					}
				}
				for(int i=0;i<tietong.size();i++) {//??????
					if(tietong.get(i).live) {
						tietong.get(i).drawSelf(g);
					}else {
						tietong.remove(i);
					}
				}
				for(int i=0;i<baozhi.size();i++) {//??????
					if(baozhi.get(i).live) {
						baozhi.get(i).drawSelf(g);
					}else {
						baozhi.remove(i);
					}
				}
				for(int i=0;i<ganlan.size();i++) {//??魽??
					if(ganlan.get(i).live) {
						ganlan.get(i).drawSelf(g);
					}else {
						ganlan.remove(i);
					}
				}
			for(int i=0;i<flower.size();i++) {//?????
				if(flower.get(i).live) {
					flower.get(i).drawSelf(g);
				}else {
					flower.remove(i);
				}
			}
			for(int i=0;i<wandou.size();i++) {//??????
				if(wandou.get(i).live) {
					wandou.get(i).drawSelf(g);
				}
			}
			for(int i=0;i<hanbing.size();i++) {//????????
				if(hanbing.get(i).live) {
					hanbing.get(i).drawSelf(g);
				}
			}
			for(int i=0;i<jianguo.size();i++) {//???
				if(jianguo.get(i).live) {
					jianguo.get(i).drawSelf(g);
				}
			}
			for(int i=0;i<car.size();i++) {//????
				car.get(i).drawSelf(g);
			}
			for(int i=0;i<card.size();i++) {//??????
				card.get(i).drawSelf(g);
			}
			shove.drawSelf(g);//????
			suning.drawSelf(g);//???????
			if(!sunings.isEmpty()) {
				for(int i=0;i<sunings.size();i++) {
					sunings.get(i).drawSelf(g);
					if(!sunings.get(i).live) {
						sunings.remove(i);
					}
				}
			}
			if(!bullet.isEmpty()) {//???????+???????
				for(int i=0;i<bullet.size();i++) {
					bullet.get(i).drawSelf(g);
					if(!bullet.get(i).live) {
						bullet.remove(i);
					}
					
				}
			}
			
			
			
			
			//?????????????
			for(int i=0;i<card.size();i++) {//?????????????????????7.5???????cool=false;
				card.get(i).stop_time=new Date();
				if((card.get(i).stop_time.getTime()-card.get(i).start_time.getTime())*0.001>7.5) {
					card.get(i).cool=false;
				}
			}
			//?????????????????cd
			if(!flower.isEmpty()) {
				for(int i=0;i<flower.size();i++) {
					flower.get(i).end=new Date();
					if((flower.get(i).end.getTime()-flower.get(i).start.getTime())*0.001>15) {//??????15s
						flower.get(i).start=new Date();//?????????????????start???????*/
						sunings.add(new Sun(flower.get(i).x,flower.get(i).y,suns));
						System.out.println("????????25");
					}
				}
			}
			//????????????????????????????????
			if(!sunings.isEmpty()) {
				for(int i=0;i<sunings.size();i++) {
					if(!sunings.get(i).live) {//??????false????????????
						sunings.remove(i);
					}else {
						sunings.get(i).end=new Date();
						if((sunings.get(i).end.getTime()-sunings.get(i).start.getTime())*0.001>10) {//????10????????????????????false
							sunings.get(i).live=false;
						}
						
					}
				}
			}	
		}else if(loadtime==2) {//??????????
			util.stopBGM();
			g.drawImage(img[8],x,y,null);
			if(backmenu) {
				g.drawImage(img[9],x,y,null);
			}else if(restart) {
				g.drawImage(img[10],x,y,null);
			}else if(backgame) {
				g.drawImage(img[11],x,y,null);
			}
		}else if(loadtime==3) {
			util.playBGM("sounds/shibai.wav",1);
			loadtime++;
		}else if(loadtime==4) {
			g.drawImage(img[20],x,y,null);
		}
	}
	//右键取消选择植物
	public static void cancelSelect() {
		for(int i=0;i<card.size();i++) {
			if(card.get(i).move) {
				card.get(i).move=false;
				card.get(i).x=127+i*52;
				card.get(i).y=49;
			}
		}
		if(shove.move) {
			shove.move=false;
			shove.x=495;shove.y=29;
		}
	}
	//鼠标移动事件处理
	public static void MouseMove(MouseEvent e){
		if(loadtime==1) {

			if(GameUtil.ifRect(e.getX(),e.getY(),681,38,791,75)) {
				op=3;
			}else {op=2;}
			//?????????????
			 if(shove.move) {
				 shove.x=e.getX()-33;
				 shove.y=e.getY()-44;
				 }
			 //?????????????
			 for(int i=0;i<card.size();i++) {
				 if(card.get(i).move) {
					 card.get(i).x=e.getX()-9;
					 card.get(i).y=e.getY()-24;
				 }
			 }
		}else if(loadtime==2) {
			if(GameUtil.ifRect(e.getX(),e.getY(),301,338,502,377)) {//?????????
				backmenu=true;
			}else if(GameUtil.ifRect(e.getX(),e.getY(),301,389,502,425)) {//?????????
				restart=true;
			}else if(GameUtil.ifRect(e.getX(),e.getY(),241,460,571,528)) {//???????
				backgame=true;
			}else {
				backmenu=false;restart=false;backgame=false;
			}
		}
	}		
	//????????????????
	public static void MouseClick(MouseEvent e){
		if(loadtime==1) {//?????????????
			if(GameUtil.ifRect(e.getX(),e.getY(),0,0,50,70)) {

			}
			//??????????,??μ?25
			if(GameUtil.ifRect(e.getX(),e.getY(),67,45,112,83)) {
				sun+=25;
			}
			//???????β?????????????????????????25
			if(GameUtil.ifRect(e.getX(),e.getY(),681,38,791,75)) {
				loadtime=2;//???????????????????????棬???????????
			}
			//???????
			if(shove.move){//???ж??????????????????????????????
				for(int i=0;i<flower.size();i++) {//???????????????????????
					if(GameUtil.ifRect(e.getX(),e.getY(),flower.get(i).x, flower.get(i).y, flower.get(i).x +flower.get(i).wide,flower.get(i).y+flower.get(i).high)) {
						for(int j=0;j<qizhi.size();j++) {//????????????????????????
							if(qizhi.get(j).getRect().intersects(flower.get(i).getRect())) {
								qizhi.get(j).attacking=false;qizhi.get(j).count=0;qizhi.get(j).moving=true;qizhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<tietong.size();j++) {//????????????????????????
							if(tietong.get(j).getRect().intersects(flower.get(i).getRect())) {
								if(tietong.get(j).hp>100) {
									tietong.get(j).count=0;
								}else {
									tietong.get(j).count=96;
								}
								tietong.get(j).attacking=false;tietong.get(j).moving=true;tietong.get(j).pengtime=1;
							}
						}
						for(int j=0;j<baozhi.size();j++) {//????????????????????????
							if(baozhi.get(j).getRect().intersects(flower.get(i).getRect())) {
								if(baozhi.get(j).hp>100) {
									baozhi.get(j).count=0;
								}else {
									baozhi.get(j).count=94;
								}
								baozhi.get(j).attacking=false;baozhi.get(j).moving=true;baozhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<ganlan.size();j++) {//????????????????????????
							if(ganlan.get(j).getRect().intersects(flower.get(i).getRect())) {
								if(ganlan.get(j).hp>100) {
									ganlan.get(j).count=0;
								}else {
									ganlan.get(j).count=96;
								}
								ganlan.get(j).attacking=false;ganlan.get(j).moving=true;ganlan.get(j).pengtime=1;
							}
						}
						GameFrame.util.playBGM("sounds/plant.wav",1);flower.get(i).live=false;glass.get(flower.get(i).num).live =false;flower.remove(i);
					}
					
				}
				for(int i=0;i<wandou.size();i++) {//????????????????????????
					if(GameUtil.ifRect(e.getX(),e.getY(),wandou.get(i).x, wandou.get(i).y, wandou.get(i).x +wandou.get(i).wide,wandou.get(i).y+wandou.get(i).high)) {
						for(int j=0;j<qizhi.size();j++) {//????????????????????????
							if(qizhi.get(j).getRect().intersects(wandou.get(i).getRect())) {
								qizhi.get(j).attacking=false;qizhi.get(j).count=0;qizhi.get(j).moving=true;qizhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<tietong.size();j++) {//????????????????????????
							if(tietong.get(j).getRect().intersects(wandou.get(i).getRect())) {
								if(tietong.get(j).hp>100) {
									tietong.get(j).count=0;
								}else {
									tietong.get(j).count=96;
								}
								tietong.get(j).attacking=false;tietong.get(j).moving=true;tietong.get(j).pengtime=1;
							}
						}
						for(int j=0;j<baozhi.size();j++) {//????????????????????????
							if(baozhi.get(j).getRect().intersects(wandou.get(i).getRect())) {
								if(baozhi.get(j).hp>100) {
									baozhi.get(j).count=0;
								}else {
									baozhi.get(j).count=94;
								}
								baozhi.get(j).attacking=false;baozhi.get(j).moving=true;baozhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<ganlan.size();j++) {//????????????????????????
							if(ganlan.get(j).getRect().intersects(wandou.get(i).getRect())) {
								if(ganlan.get(j).hp>100) {
									ganlan.get(j).count=0;
								}else {
									ganlan.get(j).count=96;
								}
								ganlan.get(j).attacking=false;ganlan.get(j).moving=true;ganlan.get(j).pengtime=1;
							}
						}
						GameFrame.util.playBGM("sounds/plant.wav",1);wandou.get(i).live=false;glass.get(wandou.get(i).num).live =false;wandou.remove(i);
					}
					
				}
				for(int i=0;i<hanbing.size();i++) {//????????????????????????????
					if(GameUtil.ifRect(e.getX(),e.getY(),hanbing.get(i).x, hanbing.get(i).y, hanbing.get(i).x +hanbing.get(i).wide,hanbing.get(i).y+hanbing.get(i).high)) {
						for(int j=0;j<qizhi.size();j++) {//????????????????????????
							if(qizhi.get(j).getRect().intersects(hanbing.get(i).getRect())) {
								qizhi.get(j).attacking=false;qizhi.get(j).count=0;qizhi.get(j).moving=true;qizhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<tietong.size();j++) {//????????????????????????
							if(tietong.get(j).getRect().intersects(hanbing.get(i).getRect())) {
								if(tietong.get(j).hp>100) {
									tietong.get(j).count=0;
								}else {
									tietong.get(j).count=96;
								}
								tietong.get(j).attacking=false;tietong.get(j).moving=true;tietong.get(j).pengtime=1;
							}
						}
						for(int j=0;j<baozhi.size();j++) {//????????????????????????
							if(baozhi.get(j).getRect().intersects(hanbing.get(i).getRect())) {
								if(baozhi.get(j).hp>100) {
									baozhi.get(j).count=0;
								}else {
									baozhi.get(j).count=94;
								}
								baozhi.get(j).attacking=false;baozhi.get(j).moving=true;baozhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<ganlan.size();j++) {//????????????????????????
							if(ganlan.get(j).getRect().intersects(hanbing.get(i).getRect())) {
								if(ganlan.get(j).hp>100) {
									ganlan.get(j).count=0;
								}else {
									ganlan.get(j).count=96;
								}
								ganlan.get(j).attacking=false;ganlan.get(j).moving=true;ganlan.get(j).pengtime=1;
							}
						}
						GameFrame.util.playBGM("sounds/plant.wav",1);hanbing.get(i).live=false;glass.get(hanbing.get(i).num).live =false;hanbing.remove(i);
					}
					
				}
				for(int i=0;i<jianguo.size();i++) {//????????????????????????
					if(GameUtil.ifRect(e.getX(),e.getY(),jianguo.get(i).x, jianguo.get(i).y, jianguo.get(i).x +jianguo.get(i).wide,jianguo.get(i).y+jianguo.get(i).high)) {
						for(int j=0;j<qizhi.size();j++) {//????????????????????????
							if(qizhi.get(j).getRect().intersects(jianguo.get(i).getRect())) {
								qizhi.get(j).attacking=false;qizhi.get(j).count=0;qizhi.get(j).moving=true;qizhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<tietong.size();j++) {//????????????????????????
							if(tietong.get(j).getRect().intersects(jianguo.get(i).getRect())) {
								if(tietong.get(j).hp>100) {
									tietong.get(j).count=0;
								}else {
									tietong.get(j).count=96;
								}
								tietong.get(j).attacking=false;tietong.get(j).moving=true;tietong.get(j).pengtime=1;
							}
						}
						for(int j=0;j<baozhi.size();j++) {//????????????????????????
							if(baozhi.get(j).getRect().intersects(jianguo.get(i).getRect())) {
								if(baozhi.get(j).hp>100) {
									baozhi.get(j).count=0;
								}else {
									baozhi.get(j).count=94;
								}
								baozhi.get(j).attacking=false;baozhi.get(j).moving=true;baozhi.get(j).pengtime=1;
							}
						}
						for(int j=0;j<ganlan.size();j++) {//????????????????????????
							if(ganlan.get(j).getRect().intersects(jianguo.get(i).getRect())) {
								if(ganlan.get(j).hp>100) {
									ganlan.get(j).count=0;
								}else {
									ganlan.get(j).count=96;
								}
								ganlan.get(j).attacking=false;ganlan.get(j).moving=true;ganlan.get(j).pengtime=1;
							}
						}
						GameFrame.util.playBGM("sounds/plant.wav",1);jianguo.get(i).live=false;glass.get(jianguo.get(i).num).live =false;jianguo.remove(i);
					}
					
				}
				 shove.move=false;
				 shove.x=495;shove.y=29;
			 }
			//????????????????????
			if(GameUtil.ifRect(e.getX(),e.getY(),508,32,567,96)) {
				 shove.move=true;
				 GameFrame.util.playBGM("sounds/moveshove.wav",1);
			 }
			
			//?????????????
			for(int i=0;i<card.size();i++) {//???????ж????????????????????,????????????
				if(card.get(i).move) {
					card.get(i).move=false;
					switch(i) {
					case 0:{
						for(int j=0;j<glass.size();j++) {
							if(glass.get(j).live==false&&GameUtil.ifRect(e.getX(),e.getY(),glass.get(j).x,glass.get(j).y,glass.get(j).x+glass.get(j).wide,glass.get(j).y+glass.get(j).high)) {
								flower.add(new Flower(glass.get(j).x,glass.get(j).y,60,70,j));
								glass.get(j).live=true;sun-=card.get(i).price; //?????????true??????????
								GameFrame.util.playBGM("sounds/plant.wav",1);//??Ч
								card.get(i).cool=true;card.get(i).start_time=new Date();//?????????
								break;
							}
						}
						card.get(i).x=127+i*52;card.get(i).y=49;break;//?????λ
					}
					case 1:{
						for(int j=0;j<glass.size();j++) {
							if(glass.get(j).live==false&&GameUtil.ifRect(e.getX(),e.getY(),glass.get(j).x,glass.get(j).y,glass.get(j).x+glass.get(j).wide,glass.get(j).y+glass.get(j).high)) {
								wandou.add(new Wandou(glass.get(j).x,glass.get(j).y,60,70,wandous,j));
								glass.get(j).live=true;sun-=card.get(i).price;//?????????true??????????
								GameFrame.util.playBGM("sounds/plant.wav",1);//??Ч
								card.get(i).cool=true;card.get(i).start_time=new Date();//?????????
								break;
							}
						}
						card.get(i).x=127+i*52;card.get(i).y=49;break;//?????λ
					}
					case 2:{
						for(int j=0;j<glass.size();j++) {
							if(glass.get(j).live==false&&GameUtil.ifRect(e.getX(),e.getY(),glass.get(j).x,glass.get(j).y,glass.get(j).x+glass.get(j).wide,glass.get(j).y+glass.get(j).high)) {
								hanbing.add(new Hanbing(glass.get(j).x,glass.get(j).y,60,70,hanbings,j));
								glass.get(j).live=true;sun-=card.get(i).price;//?????????true??????????
								GameFrame.util.playBGM("sounds/plant.wav",1);//??Ч
								card.get(i).cool=true;card.get(i).start_time=new Date();//?????????
								break;
							}
						}
						card.get(i).x=127+i*52;card.get(i).y=49;break;//?????λ
					}
					case 3:{
						for(int j=0;j<glass.size();j++) {
							if(glass.get(j).live==false&&GameUtil.ifRect(e.getX(),e.getY(),glass.get(j).x,glass.get(j).y,glass.get(j).x+glass.get(j).wide,glass.get(j).y+glass.get(j).high)) {
								jianguo.add(new Jianguo(glass.get(j).x,glass.get(j).y,60,70,jianguos,j));
								glass.get(j).live=true;sun-=card.get(i).price;//?????????true??????????
								GameFrame.util.playBGM("sounds/plant.wav",1);//??Ч
								card.get(i).cool=true;card.get(i).start_time=new Date();//?????????
								break;
							}
						}
						card.get(i).x=127+i*52;card.get(i).y=49;break;//?????λ
					}
					}
				}
			}
			//????ε?????		
			if(GameUtil.ifRect(e.getX(),e.getY(),131,40,176,106)) {
				 if(sun>=card.get(0).price&&card.get(0).cool==false) {
					 card.get(0).move=true;
					 GameFrame.util.playBGM("sounds/plant.wav",1);
				 }
			 }else if(GameUtil.ifRect(e.getX(),e.getY(),184,39,227,105)){
				 if(sun>=card.get(1).price&&card.get(1).cool==false) {
					 card.get(1).move=true;
				    GameFrame.util.playBGM("sounds/plant.wav",1);
				 }
			 }else if(GameUtil.ifRect(e.getX(),e.getY(),235,39,280,107)){
				 if(sun>=card.get(2).price&&card.get(2).cool==false) {
					 card.get(2).move=true;
					 GameFrame.util.playBGM("sounds/plant.wav",1);
				 }
			 }else if(GameUtil.ifRect(e.getX(),e.getY(),287,39,334,108)){
				 if(sun>=card.get(3).price&&card.get(3).cool==false) {
					 card.get(3).move=true;
					 GameFrame.util.playBGM("sounds/plant.wav",1);
				 }
			 }
			//??????????????????
			if(GameUtil.ifRect(e.getX(),e.getY(),suning.x-10,suning.y-10,suning.x-10+suning.wide,suning.y-10+suning.high)) {
				suning.move=true;
				GameFrame.util.playBGM("sounds/yangguang.wav",1);
				if((suning.y-45)<30) {
					suning.speedx=10;
					suning.speedy=5;
				}else {
				suning.speedx=(int)((suning.x-70)/30);
				suning.speedy=(int)((suning.y-45)/30);
				}	
			}
			//?????????????????????,?????????鲻???????????ж???????
			        if(!sunings.isEmpty()) {
			        	for(int i=0;i<sunings.size();i++) {
			    			if(GameUtil.ifRect(e.getX(),e.getY(),sunings.get(i).x-10,sunings.get(i).y-10,sunings.get(i).x-10+70,sunings.get(i).y-10+70)) {
			    				sunings.get(i).move=true;
			    				GameFrame.util.playBGM("sounds/yangguang.wav",1);
			    				
			    			}
			    		}
			        }
		}else if(loadtime==2) {
			if(GameUtil.ifRect(e.getX(),e.getY(),301,338,502,377)) {//?????????
				loadtime=1;
				GameFrame.live=false;
				MenuFrame.live =true;
				LoadFrame.util.playBGM();
			}else if(GameUtil.ifRect(e.getX(),e.getY(),301,389,502,425)) {//?????????,??????????
				loadtime=0;sun=100;
				flower.clear();wandou.clear();hanbing.clear();jianguo.clear();
				qizhi.clear();tietong.clear();baozhi.clear();ganlan.clear();bullet.clear();car.clear();sunings.clear();
				for(int i=0;i<card.size();i++) {
					card.get(i).cool=false;
				}
				for(int i=0;i<glass.size();i++) {
					glass.get(i).live=false;
				}
				for(int i=1;i<6;i++) {
					car.add(new Car(1-(i-1)*3,30+i*100,46,64,8,img[4]));//???????
					}
				startTime=new  Date();
				start=new Date();
				util.playBGM();
			}else if(GameUtil.ifRect(e.getX(),e.getY(),241,460,571,528)) {//???????
				util.playBGM();
				loadtime=1;
			}
		}else if(loadtime==4) {			
			loadtime=0;sun=50;
			flower.clear();wandou.clear();hanbing.clear();jianguo.clear();
			qizhi.clear();tietong.clear();baozhi.clear();ganlan.clear();bullet.clear();car.clear();sunings.clear();
			for(int i=0;i<card.size();i++) {
				card.get(i).cool=false;
			}
			for(int i=0;i<glass.size();i++) {
				glass.get(i).live=false;
			}
			for(int i=1;i<6;i++) {
				car.add(new Car(1-(i-1)*3,30+i*100,46,64,8,img[4]));//???????
				}
			util.stopBGM();
			GameFrame.live=false;
			MenuFrame.live =true;
			LoadFrame.util.playBGM();
		}
	}
}
