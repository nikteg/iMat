package se.chalmers.dat215.grupp14;

import java.awt.Cursor;

import com.alee.laf.button.WebToggleButton;

@SuppressWarnings("serial")
public class CategoryButton extends WebToggleButton {
    private String name;
    private int number;

    private Constants.Category cat;

    public CategoryButton() {
        super();
    }

    public CategoryButton(String name, int number) {
        this();
        this.name = name;
        this.number = number;

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setText(name, number);
    }

    public void setNumber(int number) {
        this.number = number;
        setText(name, number);
    }

    @Override
    public void setName(String name) {
        this.name = name;
        setText(name, number);
    }

    private void setText(String name, int number) {
        super.setText("<html><table cellpadding=0 cellspacing=0 style='width: 122px'><tr><td>" + name
                + "</td><td style='text-align: right; color: rgb(150, 150, 150)'>" + number
                + "</td></tr></table></html>");
    }

    public void setCategory(Constants.Category cat) {
        this.cat = cat;
    }

    public Constants.Category getCategory() {
        return cat;
    }

}
