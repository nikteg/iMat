import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import com.alee.laf.text.WebTextField;


public class SearchField extends WebTextField implements ActionListener {
	private IMatModel model;
	private Timer timer = new Timer(500, this);
	
	public SearchField(IMatModel model) {
		super();
		
		this.model = model;
		
		initialize();
	}
	
	public void initialize() {
		timer.setRepeats(false);
		
		setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		setInputPrompt("Sök...");
		addActionListener(this);
		addKeyListener(new KeyListener());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == timer) {
			if (getText().length() < 2) return;
			
			model.getSearchResults(getText());
		}
	}
	
	private class KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			timer.restart();
		}
	}
}
