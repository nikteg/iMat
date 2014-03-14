package se.chalmers.dat215.grupp14;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

import se.chalmers.dat215.grupp14.backend.IMatModel;

import com.alee.laf.text.WebTextField;

@SuppressWarnings("serial")
public class SearchField extends WebTextField implements ActionListener {
    private IMatModel model;
    private MainWindow parent;
    private Timer timer = new Timer(500, this);

    /* KONAMI STUFF */
    private List<Integer> konami = new ArrayList<Integer>();
    private int konamiProgress = 0;
    AudioInputStream inputStream;
    Clip clip;

    /* END KONAMI STUFF */

    public SearchField() {
        super();
        initializeGUI();
    }
    
    public SearchField(IMatModel model, MainWindow parent) {
        this();

        this.model = model;
        this.parent = parent;

        /* KONAMI STUFF */
        try {
            inputStream = AudioSystem.getAudioInputStream(SearchField.class
                    .getResourceAsStream("resources/powerup.wav"));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (Exception e) {
        }

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
        /* END KONAMI STUFF */
    }

    public void initializeGUI() {
        timer.setRepeats(false);

        setFont(new Font("Lucida Grande", Font.PLAIN, 24));
        setInputPrompt("Sök...");
        addActionListener(this);
        addKeyListener(new KeyListener());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == timer) {
            if (getText().length() == 0 || getText().length() > 1) {
                model.search(getText().trim());
            }
        }
    }

    private class KeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            /* KONAMI STUFF */
            if (((Integer) keyEvent.getKeyCode()).equals(konami.get(konamiProgress))) {
                if (++konamiProgress == konami.size()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            clip.setFramePosition(0);
                            clip.start();
                        }
                    }).start();
                    parent.changeLogo();
                    konamiProgress = 0;
                    setText(getText().substring(0, Math.max(getText().length() - 2, 0)));
                }
            } else {
                konamiProgress = 0;
            }
            /* END KONAMI STUFF */

            timer.restart();
        }
    }
}
