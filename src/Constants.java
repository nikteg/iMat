public class Constants {
	public static enum Category {
		BREAD("Bröd"), SNACKS("Snacks"), DRINKS("Drycker"), DAIRIES("Mejeri"),
		MEAT_FISH("Kött & fisk"), VEGETABLES("Grönsaker"), FRUIT_BERRIES("Frukt & bär"),
		PASTA_POTATO_RICE("Pasta, potatis & ris"), SPICES("Kryddor"),
		ROOT_VEGETABLES("Rotfrukter"), PANTRY("Skafferi");

		private String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	};
}
