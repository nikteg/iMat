import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;


public class CartButton extends JButton {
	private int number;
	private Font font = new Font("Sans Serif", Font.BOLD, 10);
	
	public CartButton() {
		super();
	}
	
	public CartButton(String text) {
		setText(text);
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (number != 0) {
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.RED);
			g.fillRoundRect(30, 10, 4 + (8 * (number + "").length()), 12, 10, 10);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString(this.number + "", 33, 20);
		}
	}
}
