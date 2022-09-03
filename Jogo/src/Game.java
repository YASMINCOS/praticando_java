import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {
    
	public static final int WIDTH= 640, HEIGTH=480;
	
	public static int contador=100;
	
	public Spawner spawner;
	
	public Game() {
	
    	 Dimension dimesion= new Dimension(WIDTH,HEIGHT);
    	 this.setPreferredSize(dimesion);
    	 
    	 spawner = new Spawner();
     }
	public void update() {
	  contador--;
	  spawner.update();
	  if(contador <=0) {
		  contador=100;
	  }
	}
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs== null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g= bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGTH);
		/*
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD,23));
		g.drawString("Pontos :"+ contador, WIDTH/2 -60,30);
		*/
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH/2 - 100-70, 20, contador *3, 20);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH/2 - 100-70, 20,300, 20);
		bs.show();
		
		spawner.render(g);
		
	}
	
	public static void main(String[] args) {
		Game game= new Game();
		
		JFrame jframe= new JFrame("meu jogo");
		jframe.add(game);
		jframe.setLocationRelativeTo(null);
		jframe.pack();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		
		new Thread(game).start();
		
	}
	@Override
	public void run() {
		
		while (true) {
			
			update();
			render();
			try {
				Thread.sleep(100/60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
}
