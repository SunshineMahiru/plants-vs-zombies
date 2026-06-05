package plantgame;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.w3c.dom.NodeList;
 
public class GameUtil {
     GameUtil() {
    }
    
     static boolean ifRect(int x,int y,int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4) {
		if((y>=(((y2-y1)*(x-x1))/(x2-x1)+y1))&&(y<=(((y3-y2)*(x-x2))/(x3-x2)+y2))&&(y<=(((y4-y3)*(x-x3))/(x4-x3)+y3))&&(y>=(((y4-y1)*(x-x1))/(x4-x1)+y1))) {
			return true;
		}else {
			return false;
		}
	}
	 static boolean ifRect(int x,int y,int x1,int y1,int x2,int y2) {
		if(x>=x1&&x<=x2&&y>=y1&&y<=y2){
			return true;
		}else {
			return false;
		}
	}
    
    static Image getImage(String path) {
        BufferedImage bi = null;
        try {
            URL u = GameUtil.class.getClassLoader().getResource(path);
            bi = ImageIO.read(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }

    static Image getGifImage(String path) {
        URL u = GameUtil.class.getClassLoader().getResource(path);
        if(u==null) {
            System.err.println("Resource not found: "+path);
            return null;
        }
        return new javax.swing.ImageIcon(u).getImage();
    }

    static Image[] getGifFrames(String path) {
        try {
            URL u = GameUtil.class.getClassLoader().getResource(path);
            if(u==null) {
                System.err.println("Resource not found: "+path);
                return new Image[1];
            }
            ImageInputStream iis = ImageIO.createImageInputStream(u.openStream());
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(iis);
            
            int numFrames = reader.getNumImages(true);
            if(numFrames<=0) return new Image[1];
            
            int width = reader.getWidth(0);
            int height = reader.getHeight(0);
            
            BufferedImage[] frames = new BufferedImage[numFrames];
            BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            BufferedImage savedCanvas = null;
            
            for(int i=0; i<numFrames; i++) {
                IIOMetadataNode root = (IIOMetadataNode)reader.getImageMetadata(i)
                    .getAsTree("javax_imageio_gif_image_1.0");
                
                int offsetX=0, offsetY=0;
                int disposal=1;
                
                NodeList imgDescs = root.getElementsByTagName("ImageDescriptor");
                if(imgDescs.getLength()>0) {
                    IIOMetadataNode imgDesc = (IIOMetadataNode)imgDescs.item(0);
                    offsetX = Integer.parseInt(imgDesc.getAttribute("imageLeftPosition"));
                    offsetY = Integer.parseInt(imgDesc.getAttribute("imageTopPosition"));
                }
                
                NodeList gces = root.getElementsByTagName("GraphicControlExtension");
                if(gces.getLength()>0) {
                    IIOMetadataNode gce = (IIOMetadataNode)gces.item(0);
                    String dispStr = gce.getAttribute("disposalMethod");
                    if("restoreToBackgroundColor".equals(dispStr)) {
                        disposal = 2;
                    } else if("restoreToPrevious".equals(dispStr)) {
                        disposal = 3;
                    } else {
                        disposal = 1;
                    }
                }
                
                if(disposal==3) {
                    savedCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D sg = savedCanvas.createGraphics();
                    sg.drawImage(canvas, 0, 0, null);
                    sg.dispose();
                }
                
                BufferedImage frame = reader.read(i);
                Graphics2D cg = canvas.createGraphics();
                cg.drawImage(frame, offsetX, offsetY, null);
                cg.dispose();
                
                frames[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D fg = frames[i].createGraphics();
                fg.drawImage(canvas, 0, 0, null);
                fg.dispose();
                
                if(disposal==2) {
                    Graphics2D dg = canvas.createGraphics();
                    dg.setComposite(AlphaComposite.Clear);
                    dg.fillRect(offsetX, offsetY, frame.getWidth(), frame.getHeight());
                    dg.dispose();
                } else if(disposal==3 && savedCanvas!=null) {
                    canvas = savedCanvas;
                    savedCanvas = null;
                }
            }
            
            reader.dispose();
            iis.close();
            return frames;
        } catch(Exception e) {
            e.printStackTrace();
            return new Image[1];
        }
    }

     Clip bgm;
     FloatControl volumeControl;
    public  void loadBGM(String path) {
    	try {
			bgm=AudioSystem.getClip();
			InputStream bg=this.getClass().getClassLoader().getResourceAsStream(path);
			AudioInputStream bj=AudioSystem.getAudioInputStream(bg);
				bgm.open(bj);
			volumeControl = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public  void playBGM() {
		bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public  void stopBGM() {
    	bgm.stop();
    }
    // 设置背景音乐音量 (0.0-1.0)
    public void setVolume(float volume) {
    	if(volumeControl != null) {
    		float min = volumeControl.getMinimum();
    		float max = volumeControl.getMaximum();
    		float range = max - min;
    		float gain = min + range * volume;
    		volumeControl.setValue(gain);
    	}
    }
    // 播放音效并设置音量 (volume: 0.0-1.0, 1.0为最大)
    public  void playBGM(String path,float volume) {
    		try {
    			Clip clip=AudioSystem.getClip();
    			InputStream bg=this.getClass().getClassLoader().getResourceAsStream(path);
    			AudioInputStream bj=AudioSystem.getAudioInputStream(bg);
    				clip.open(bj);
    			FloatControl vc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    			float min = vc.getMinimum();
    			float max = vc.getMaximum();
    			vc.setValue(min + (max - min) * volume);
    			clip.start();
    		} catch (LineUnavailableException e) {
    			e.printStackTrace();
    		} catch (UnsupportedAudioFileException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
    public  void playBGM(String path,int x) {
    	if(x==1) {
    		playBGM(path, 1.0f);
    	}
    }
}
