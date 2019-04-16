import java.util.ArrayList;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.Table;
import processing.data.TableRow;

public class UI extends PApplet
{
	private AudioPlayer player;
	private Minim minim;

	private double bx;
	private double by;
	private int box_size = 75;
	boolean overBox = false;
	boolean locked = false;
	float xOffset = 0;
	float yOffset = 0;

	// button variables
	private float button_height = 50;
	private float border = 20;
	private float button_width = 200;
	private float gap = 40;

	private ArrayList<Menu_Options> options = new ArrayList<>();

	private Button b;
	private DNA mc;

	public void settings()
	{
		fullScreen(P3D);
		smooth(8);
	}

	public void draw_rhombus(int rx, int ry, int trans)
	{
		pushMatrix();
		translate(rx, ry);
		rotate(radians(trans));
		float length = 180;
		float side = 80;
		double height = Math.sqrt(3) / 2 * side;
		fill(190);
		noStroke();
		quad(0, 0, length, 0, length + side / 2, (float) height, 0 - side / 2, (float) height);
		popMatrix();
	}

	public void setup()
	{
		PFont my_font = createFont("Arial", 32, true);
		textFont(my_font);
		load_menu_options();

		// temp variables
		bx = width / 2.0;
		by = height / 2.0;
		rectMode(RADIUS);

//		b = new Button(this, 50, 50, 100, 50, "I am a button");
//		mc = new DNA(this, (float) width / 2, (float) height / 2, 50);

		loadMusic();
	}

	// finished method
	private void loadMusic()
	{
		minim = new Minim(this);
		player = minim.loadFile("vr.mp3");

		player.play();
		player.loop();
	}


	private void draw_menu_options()
	{
		pushMatrix();
		translate(400, 400);
		for (int i = 0; i < options.size(); i++)
		{
			Menu_Options p = options.get(i);

			float y = border + (i * (button_height + gap));
			float x = border;
			noFill();
			stroke(0);
			rect(x, y, button_width, button_height);
			textAlign(CENTER, CENTER);
			fill(30);
			textSize(32);
			text(p.getName(), x + button_width * 0.5f, y + button_height * 0.5f);
		}
		popMatrix();
	}

	private void load_menu_options()
	{
		Table table = loadTable("options.csv", "header");
		for (TableRow tr : table.rows())
		{
			Menu_Options p = new Menu_Options(tr);
			options.add(p);
		}
	}

	private void draw_lines(int y)
	{
		int line_size = width / 3;
		for (int i = 0; i < 6; i++)
		{
			if (i % 2 == 1)
			{
				continue;
			}
			line(i * width / 5, y, i * width / 5 + line_size, y);
		}
	}

	public void draw()
	{
		background(17, 66, 214);
//		triangle(width / 2 - side/2, height / 2, width / 2, height / 2f - (sqrt(3) * (side / 2f)), width / 2 + side/2, height / 2);

		int m = millis();

		draw_lines(height - 100);
		draw_lines(100);

		fill(255);

//		camera(mouseX, mouseY, 1000, width / 2, height / 2, 0,
//				0, 1, 0);

		if (m < 5000)
		{
			pushMatrix();
			translate(width / 3.5f, height / 5);
			draw_rhombus(250, 300, 0);
			draw_rhombus(460, 135, 120);
			draw_rhombus(500, 400, 240);
			fill(240, m / 6 % 255);
			textSize(64);
			textAlign(CENTER, CENTER);
			text("LOADING", 400, 500);
			popMatrix();
		}
		else
		{
			draw_menu_options();
		}
	}

	public void stop()
	{
		// always close Minim audio classes when you are done with them
		player.close();
		minim.stop();
		super.stop();
	}
}

