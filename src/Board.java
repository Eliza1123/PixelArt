import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
	Color [][] pixels;
	double width;
	double height;


	public Board(Color[][] in){
		pixels = in;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		width = (double) this.getWidth() / pixels.length;
		height = (double) this.getHeight() / pixels[0].length;
		for (int column = 0; column < pixels[0].length; column++) {
			for (int row = 0; row < pixels.length; row++) {
				if (pixels[row][column].getAlpha() > 0) {
					g.setColor(pixels[row][column]);
					g.fillRect((int)Math.round(column * width),
							(int)Math.round(row * height),
							(int) width + 1,
							(int) height + 1);
				}

			}

		}

		g.setColor(Color.black);
		for (int x = 0; x < pixels.length; x++) {
			g.drawLine((int)Math.round(x*width),0,(int)Math.round(x*width),this.getHeight());
			g.drawLine(0, (int)Math.round(x*height), this.getWidth(), (int)Math.round(x*height));
		}
	}


}
