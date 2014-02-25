import com.alee.laf.button.WebToggleButton;


public class CategoryToggleButton extends WebToggleButton{

	private String name;
	private int number;
	public CategoryToggleButton(String name, int number) {
		super("<html><table cellpadding=0 cellspacing=0 style='width: 134px'><tr><td>" + name + "</td><td style='text-align: right; color: rgb(150, 150, 150)'>" + number + "</td></tr></table></html>");
		this.name = name;
		this.number = number;
	}
	
	public String getName(){
		return name;
	}
	
	public void SetNumber(int number){
		setText("<html><table cellpadding=0 cellspacing=0 style='width: 134px'><tr><td>" + name + "</td><td style='text-align: right; color: rgb(150, 150, 150)'>" + number + "</td></tr></table></html>");
	}
	
	public void setName(String name){
		setText("<html><table cellpadding=0 cellspacing=0 style='width: 134px'><tr><td>" + name + "</td><td style='text-align: right; color: rgb(150, 150, 150)'>" + number + "</td></tr></table></html>");
	}
	

}
