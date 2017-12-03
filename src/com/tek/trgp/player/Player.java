package com.tek.trgp.player;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

public class Player {
	
	Robot robot;
	String url;
	BufferedImage dino;
	Match m;
	Location loc;
	Color[][] view;
	int tileSize;
	int tileLength, tileHeight;
	Display display;
	
	public Player() {
		try {
			this.robot = new Robot();
			this.url = "http://apps.thecodepost.org/trex/trex.html";
			this.dino = ImageIO.read(getClass().getResource("/dino.png"));
			this.tileLength = 24;
			this.tileHeight = 6;
			this.tileSize = 25;
			this.view = new Color[tileLength][tileHeight];
		} catch (AWTException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean init() {
		Region reg = new Screen();
		Pattern p = new Pattern(dino);
		try {
			m = reg.find(p);
			return true;
		} catch (FindFailed e) {
			return false;
		}
	}
	
	public void start() {
		display = new Display();
		Graphics2D graphics = display.getGraphics();
		
		try {
			while(true) {
				BufferedImage screen = robot.createScreenCapture(m.getRect());
				
				for(int x = 0; x < tileLength; x++) {
					for(int y = 0; y < tileHeight; y++) {
						Color average = getAverage(screen, x * tileSize, y * tileSize, tileSize, tileSize);
						float luminance = (float) (0.2126*average.getRed() + 0.7152*average.getGreen() + 0.0722*average.getBlue());
						if(luminance < 225) {
							view[x][y] = Color.BLACK;
						}else if(luminance < 237){
							view[x][y] = Color.GRAY;
						}else {
							view[x][y] = Color.WHITE;
						}
					}
				}
				
				graphics.setColor(display.getGraphics().getBackground());
				graphics.clearRect(0, 0, display.getWidth(), display.getHeight());
				for(int x = 0; x < tileLength; x++) {
					for(int y = 0; y < tileHeight; y++) {
						if(view[x][y] == null) continue;
						graphics.setColor(view[x][y]);
						graphics.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
					}
				}
				
				if(view[6][5] == Color.GRAY || view[6][5] == Color.BLACK || view[5][5] == Color.BLACK || view[5][5] == Color.GRAY) {
					robot.keyPress(KeyEvent.VK_UP);
				}else{
					robot.keyRelease(KeyEvent.VK_UP);
				}
				
				if(view[12][3] == Color.GRAY) {
					robot.keyPress(KeyEvent.VK_UP);
					Thread.sleep(5);
					robot.keyRelease(KeyEvent.VK_UP);
				}
				
				Thread.sleep(50);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Color getAverage(BufferedImage screen, int x, int y, int width, int height) {
		long r = 0, g = 0, b = 0;
		
		for(int x1 = 0; x1 < width; x1++) {
			for(int y1 = 0; y1 < width; y1++) {
				int ic = screen.getRGB(x + x1, y + y1);
				int red = (ic >> 16) & 0xFF;
				int green = (ic >>  8) & 0xFF;
				int blue = (ic      ) & 0xFF;
				r += red;
				g += green;
				b += blue;
			}
		}
		
		return new Color((int)(r / (width * height)),(int)(g / (width * height)),(int)(b / (width * height)));
	}
	
}
