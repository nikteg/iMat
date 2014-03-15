package se.chalmers.dat215.grupp14;

import java.awt.Cursor;

import se.chalmers.dat215.grupp14.backend.Constants;

import com.alee.laf.button.WebToggleButton;

/**
 * Category button
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class CategoryButton extends WebToggleButton {
    private String name;
    private int amount;

    private Constants.Category cat;

    /**
     * Constructor
     */
    public CategoryButton() {
        super();
    }

    /**
     * Constructor with name and amount
     * @param name
     * @param amount
     */
    public CategoryButton(String name, int amount) {
        this();
        this.name = name;
        this.amount = amount;

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setText(name, amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        setText(name, amount);
    }

    @Override
    public void setName(String name) {
        this.name = name;
        setText(name, amount);
    }

    private void setText(String name, int amount) {
        super.setText("<html><table cellpadding=0 cellspacing=0 style='width: 122px'><tr><td>" + name
                + "</td><td style='text-align: right; color: rgb(150, 150, 150)'>" + amount
                + "</td></tr></table></html>");
    }

    public void setCategory(Constants.Category cat) {
        this.cat = cat;
    }

    public Constants.Category getCategory() {
        return cat;
    }

}
