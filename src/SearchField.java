import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.alee.laf.text.WebTextField;


public class SearchField extends WebTextField implements ActionListener {
	private IMatModel model;
	private MainWindow parent;
	private Timer timer = new Timer(500, this);
	
	private List<Integer> konami = new ArrayList<Integer>();
	private List<Integer> konamiProgress = new ArrayList<Integer>();
	
	public SearchField(IMatModel model, MainWindow parent) {
		super();
		
		this.model = model;
		this.parent = parent;
		
		konami.add(KeyEvent.VK_UP);
		konami.add(KeyEvent.VK_UP);
		konami.add(KeyEvent.VK_DOWN);
		konami.add(KeyEvent.VK_DOWN);
		konami.add(KeyEvent.VK_LEFT);
		konami.add(KeyEvent.VK_RIGHT);
		konami.add(KeyEvent.VK_LEFT);
		konami.add(KeyEvent.VK_RIGHT);
		konami.add(KeyEvent.VK_B);
		konami.add(KeyEvent.VK_A);
		
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
			if (((Integer)keyEvent.getKeyCode()).equals(konami.get(konamiProgress.size()))) {
				konamiProgress.add(keyEvent.getKeyCode());
				
				if (konamiProgress.size() == konami.size()) {
					parent.changeLogo();
					konamiProgress.clear();
				}
			} else {
				konamiProgress.clear();
			}
			
			timer.restart();
		}
	}
}
