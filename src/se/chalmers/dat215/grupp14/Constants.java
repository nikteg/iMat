package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.text.DecimalFormat;

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

    public static final DecimalFormat currencyFormat = new DecimalFormat("#.##");
    public static final String currencySuffix = "kr";
}
