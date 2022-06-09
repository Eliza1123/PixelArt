import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class colorValueSliderControl extends JFrame {

	public colorValueSliderControl() {
		new TColor(this);
	}

	public static void main(String arg[]) {
		new colorValueSliderControl();
	}


	class TColor implements ActionListener, ChangeListener, MouseListener, MouseMotionListener {
		final static int PENCIL = 0;
		final static int ERASER = 1;
		final static int DROPPER = 2;

		DrawingCanvas canvas = new DrawingCanvas();
		JLabel rgbValue = new JLabel("");
		JSlider sliderR, sliderG, sliderB, sliderAlpha;
		int tool = PENCIL;
		Color[][] pixels = new Color[40][40];
		JFrame frame;
		Board panel = new Board(pixels);
		Container south = new Container();
		Container east = new Container();
		JButton pencil = new JButton("pencil");
		JButton eraser = new JButton("eraser");
		JButton dropper = new JButton("dropper");

		public TColor(JFrame frame) {
			this.frame = frame;
			for (int row = 0; row <pixels.length; row++) {
				for (int column = 0; column <pixels[0].length; column++) {
					pixels[row][column] = Color.WHITE;
				}

			}
			sliderR = getSlider(0, 255, 0, 50, 5);
			sliderG = getSlider(0, 255, 0, 50, 5);
			sliderB = getSlider(0, 255, 0, 50, 5);
			sliderAlpha = getSlider(0, 255, 255, 50, 5);


			south.setLayout(new GridLayout(4, 1, 0, 0));

			south.add(new JLabel("R-G-B-A Sliders (0 - 255)"));
			south.add(new JLabel(" ", JLabel.RIGHT));

			south.add(sliderR);
			sliderR.addChangeListener(this);
			south.add(sliderG);
			sliderG.addChangeListener(this);
			south.add(sliderB);
			sliderB.addChangeListener(this);
			south.add(sliderAlpha);
			sliderAlpha.addChangeListener(this);
			south.add(canvas);


			frame.setSize(600, 600);
			frame.setLayout(new BorderLayout());
			frame.add(panel, BorderLayout.CENTER);
			south.setLayout(new GridLayout(5, 2));
			east.setLayout(new GridLayout(3, 1));
			frame.add(south, BorderLayout.SOUTH);

			east.add(pencil);
			pencil.addActionListener(this);
			east.add(eraser);
			eraser.addActionListener(this);
			east.add(dropper);
			dropper.addActionListener(this);
			frame.add(east, BorderLayout.EAST);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

			panel.addMouseListener(this);
			panel.addMouseMotionListener(this);
		}

		public JSlider getSlider(int min, int max, int init, int mjrTkSp, int mnrTkSp) {
			JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
			slider.setPaintTicks(true);
			slider.setMajorTickSpacing(mjrTkSp);
			slider.setMinorTickSpacing(mnrTkSp);
			slider.setPaintLabels(true);
			slider.addChangeListener(new SliderListener());
			return slider;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == pencil){
				tool = PENCIL;
			}
			if(e.getSource() == eraser){
				tool = ERASER;
			}
			if(e.getSource() == dropper){
				tool = DROPPER;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent event) {
			painter(event);
		}

		@Override
		public void mouseReleased(MouseEvent event) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void stateChanged(ChangeEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent event) {
			painter(event);
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}


		public void painter(MouseEvent event){
			double width = (double) panel.getWidth() / (double) pixels[0].length;
			double height = (double) panel.getHeight() / (double) pixels.length;
			int column = Math.min(pixels[0].length-1, (int)(event.getX() / width));
			int row = Math.min(pixels.length-1,(int)(event.getY() / height));
				if(tool == PENCIL) {
					pixels[row][column] = canvas.color;
				}
				if(tool == ERASER){
					pixels[row][column] = Color.WHITE;
				}
				if(tool == DROPPER){
					canvas.color = pixels[row][column];
					canvas.displayColor();
				}
			frame.repaint();
		}

		class DrawingCanvas extends Canvas {
			Color color;
			int redValue, greenValue, blueValue;
			int alphaValue = 255;

			public DrawingCanvas() {
				setSize(50, 50);
				color = new Color(0, 0, 0);
				setBackgroundColor();
			}

			public void setBackgroundColor() {
				color = new Color(redValue, greenValue, blueValue, alphaValue);
				setBackground(color);
			}

			public void displayRGBColor() {
				setBackgroundColor();
				rgbValue.setText(Integer.toString(canvas.color.getRGB() & 0xffffff, 16));
			}

			public void displayColor(){
				setBackground(color);
				rgbValue.setText(Integer.toString(canvas.color.getRGB() & 0xffffff, 16));
			}
		}

		class SliderListener implements ChangeListener {
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();

				if (slider == sliderAlpha) {
					canvas.alphaValue = slider.getValue();
					canvas.displayRGBColor();
				} else if (slider == sliderR) {
					canvas.redValue = slider.getValue();
					canvas.displayRGBColor();
				} else if (slider == sliderG) {
					canvas.greenValue = slider.getValue();
					canvas.displayRGBColor();
				} else if (slider == sliderB) {
					canvas.blueValue = slider.getValue();
					canvas.displayRGBColor();
				}
				canvas.repaint();
			}
		}
	}
}

