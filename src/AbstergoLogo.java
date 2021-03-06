import processing.core.PApplet;

public class AbstergoLogo extends UIElement implements Loading
{
	private int counter;

	AbstergoLogo(UI ui, float x, float y)
	{
		this.ui = ui;
		this.coordinateX = x;
		this.coordinateY = y;
	}

	private void drawTrapezium(int trans)
	{
		ui.pushMatrix();
		ui.translate(coordinateX, coordinateY);
		ui.rotate(PApplet.radians(trans));
		ui.translate(-ui.width / 8, ui.height / 36);
		float length = ui.width / 7;
		float side = ui.height / 9;
		double height = Math.sqrt(3) / 2 * side;
		ui.fill(255);
		ui.noStroke();
		ui.quad(0, 0, length, 0, length + side / 2, (float) height, -side / 2, (float) height);
		ui.popMatrix();
	}

	private void drawLogo()
	{
		drawTrapezium(0);
		drawTrapezium(120);
		drawTrapezium(240);
		ui.fill(255, ui.millis() / 10 % 255);
		ui.textSize(ui.width / 20);
		ui.textAlign(ui.CENTER, ui.CENTER);
		ui.text("LOADING", coordinateX, ui.height - ui.height / 5);
	}

	public void render()
	{
		drawLogo();
	}

	public void update()
	{
		counter = ui.millis() / 1000;
	}

	public int returnTimer()
	{
		return counter;
	}

	public Boolean checkFinish(int timer)
	{
		return counter == timer + 5;
	}
}
