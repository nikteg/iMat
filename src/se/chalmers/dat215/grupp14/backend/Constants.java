package se.chalmers.dat215.grupp14.backend;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Constants {
    public static enum Category {
        BREAD("Bröd"), SNACKS("Snacks"), DRINKS("Drycker"), DAIRIES("Mejeri"), MEAT_FISH("Kött & fisk"), VEGETABLES(
                "Grönsaker"), FRUIT_BERRIES("Frukt & bär"), PASTA_POTATO_RICE("Pasta, potatis & ris"), SPICES("Kryddor"), ROOT_VEGETABLES(
                "Rotfrukter"), PANTRY("Skafferi");

        private String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    };

    public static final Color ERROR_COLOR = new Color(250, 150, 150);
    public static final Color ALT_COLOR = new Color(248, 248, 248);

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
    public static final DecimalFormat currencyFormat = new DecimalFormat("#.##");
    public static final String currencySuffix = "kr";
    
    public static final int GRID_WIDTH = 180;
    public static final int GRID_HEIGHT = 240;
    public static final int LIST_HEIGHT = 64;
    
    public static final int MARGIN = 10;
}
