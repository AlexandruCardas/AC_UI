import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.data.Table;
import processing.data.TableRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UI extends PApplet
{
	private static final int FADE = 250;
	private AudioPlayer player;
	private Minim minim;
	private ArrayList<DNA> dna1 = new ArrayList<>();
	private ArrayList<DNA> dna2 = new ArrayList<>();
	private ArrayList<MenuOptions> menu = new ArrayList<>();
	private ArrayList<MenuOptions> options = new ArrayList<>();
	private LoadingText loadingMessage;
	private AbstergoLogo abstergoLogo;
	private MemoryLegend memoryLegend1;
	private MemoryLegend memoryLegend2;
	private ArrayList<Button> menuButtonList = new ArrayList<>();
	private ArrayList<Button> optionButtonList = new ArrayList<>();
	private int timer;
	private int which;
	private int volume;

	public void settings()
	{
//		size(1280, 720, P3D);
//		size(1600, 900, P3D);
		smooth(8);
		fullScreen(P3D);
//		pixelDensity(displayDensity());
	}

	public void setup()
	{
		PFont myFont = createFont("Arial", width / 20, true);
		textFont(myFont);
		loadMenuOptions("menu.csv", this.menu);
		loadMenuOptions("options.csv", this.options);
		setMenu();
		setOptions();

		loadMusic();

		for (int i = 0; i < 10; i++)
		{
			DNA s1 = new DNA(this, width / 12f + i * 50, height / 2.25f, 20, 255);
			dna1.add(s1);

			DNA s2 = new DNA(this, width - width / 12f - i * 50, height / 2.25f, 20, 0);
			dna2.add(s2);
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(new Date());

		String message = "Dublin // Ireland " + strDate + " ";
		loadingMessage = new LoadingText(this, width / 8, height / 2, message);
		abstergoLogo = new AbstergoLogo(this, width / 2, height / 2);

		PVector al = new PVector(width - width / 4, height / 9);
		memoryLegend1 = new MemoryLegend(this, al, 255, true);
		memoryLegend2 = new MemoryLegend(this, al, 0, false);
	}

	private void loadMenuOptions(String filename, ArrayList<MenuOptions> name)
	{
		Table table = loadTable(filename, "header");
		for (TableRow tr : table.rows())
		{
			MenuOptions p = new MenuOptions(tr);
			name.add(p);
		}
	}

	private void setMenu()
	{
		float ratio = width / 15f;
		float rectWidth = ratio * (11 / 3f);
		float rectHeight = height / 6;
		for (int i = 0; i < menu.size(); i++)
		{
			float ratioFormulaX = ratio + (ratio + rectWidth) * i;
			float ratioFormulaY = height - height / 3;
			MenuOptions p = menu.get(i);
			Button but = new Button(this, ratioFormulaX, ratioFormulaY, rectWidth, rectHeight, p.getName(), p.getDescription(), 0, 255);
			menuButtonList.add(but);
		}
	}

	private void setOptions()
	{
		float ratio = width / 9f;
		float rectWidth = ratio * 3f;
		float rectHeight = height / 10;
		for (int i = 0; i < options.size(); i++)
		{
			float ratioFormulaX = ratio + (ratio + rectWidth) * i;
			float ratioFormulaY = height / 2.25f;
			MenuOptions p = options.get(i);
			Button but = new Button(this, ratioFormulaX, ratioFormulaY, rectWidth, rectHeight, p.getName(), p.getDescription(), 255, 0);
			optionButtonList.add(but);
		}
	}

	private void drawMenu()
	{
		for (Button button : menuButtonList)
		{
			button.render();
		}
	}

	private void drawOptions()
	{
		for (Button button : optionButtonList)
		{
			button.render();
		}
	}

	private void drawLines(int y)
	{
		float ratio = width / 10;
		float lineSize = ratio * (8 / 3f);

		for (int i = 0; i < 3; i++)
		{
			stroke(255);
			line((lineSize + ratio) * i, y, lineSize * (i + 1) + ratio * (i), y);
		}
	}

	private void drawSequence()
	{
		int lineSize = height / 144;
		float placement1 = height / 2.8f;
		float placement2 = height - height / 2.8f;
		for (int i = 0; i < width; i += width / 100)
		{
			stroke(255);
			line(i, placement1, i, placement1 + lineSize);
			stroke(255);
			line(i, placement2, i, placement2 - lineSize);
		}
	}

	public void mouseClicked()
	{
		if (abstergoLogo.checkFinish(timer))
		{
			for (Button b : menuButtonList)
			{
				if (mouseX >= b.getX() && mouseX <= b.getX() + b.getRectWidth() && mouseY >= b.getY() && mouseY <= b.getY() + b.getRectHeight())
				{
					which = menuButtonList.indexOf(b);
				}
			}

			for (Button b : optionButtonList)
			{
				if (mouseX >= b.getX() && mouseX <= b.getX() + b.getRectWidth() && mouseY >= b.getY() && mouseY <= b.getY() + b.getRectHeight())
				{
					volume = optionButtonList.indexOf(b) + 1;
				}
			}
		}
	}

	private void overButton()
	{
		if (abstergoLogo.checkFinish(timer))
		{
			for (Button b : menuButtonList)
			{
				if (mouseX >= b.getX() && mouseX <= b.getX() + b.getRectWidth() && mouseY >= b.getY() && mouseY <= b.getY() + b.getRectHeight())
				{
					stroke(255, millis() / 3 % 255);
					line(b.getX() - width / 30f, b.getY(), b.getX() - width / 30f, b.getY() + b.getRectHeight());
					stroke(255, millis() / 3 % 255);
					line(b.getX() + width / 30f + b.getRectWidth(), b.getY(), b.getX() + width / 30f + b.getRectWidth(), b.getY() + b.getRectHeight());
				}
			}
		}
	}

	private void mainMenu()
	{
		for (DNA value : dna1)
		{
			value.render();
			value.update();
		}

		for (DNA value : dna2)
		{
			value.render();
			value.update();
		}
	}

	private void volumeButtons()
	{
		float rectWidth = width / 10;
		float rectHeight = height / 15;

		float map1 = map(player.getGain(), 0, -45, rectWidth, 0);

		stroke(0);
		noFill();
		rect(width / 2f - rectWidth / 2f, height / 9, rectWidth, rectHeight, height / 64);

		stroke(0);
		fill(255);
		if (map1 != 0)
		{
			rect(width / 2f - rectWidth / 2f, height / 9, map1, rectHeight, height / 64);
		}

		switch (volume)
		{
			case 0:
				volume = 0;
				break;
			case 1:
				if (player.getGain() > -45)
				{
					player.shiftGain(player.getGain(), player.getGain() - 5, FADE);
				}

				volume = 0;
				break;
			case 2:
				if (player.getGain() < 0)
				{
					player.shiftGain(player.getGain(), player.getGain() + 5, FADE);
				}

				volume = 0;
				break;
			default:
				break;
		}
	}

	public void draw()
	{
		background(37, 84, 199);

		drawLines(height - height / 9);
		drawLines(height / 9);

		textSize(width / 40);
		fill(255);
		textAlign(LEFT, BOTTOM);
		text("Animus version 1.3", width / 30, height / 9);

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String currentTime = dateFormat.format(new Date());

		textSize(width / 40);
		fill(255);
		textAlign(CENTER, BOTTOM);
		text(currentTime, width / 2, height / 9);


		stroke(255, 20);
		line(width / 10, 0, width - width / 10, height);
		stroke(255, 20);
		line(width - width / 5, 0, width / 5, height);

		timer = loadingMessage.returnTimer();

		if (!loadingMessage.checkFinish(timer))
		{
			loadingMessage.render();
			loadingMessage.update();
		}
		else if (!abstergoLogo.checkFinish(timer))
		{
			abstergoLogo.render();
			abstergoLogo.update();
		}
		else
		{
			overButton();
			drawSequence();
			memoryLegend1.render();
			memoryLegend2.render();
			drawMenu();

			switch (which)
			{
				case 0:
					mainMenu();
					break;
				case 1:
					volumeButtons();
					drawOptions();
					break;
				case 2:
					exit();
				default:
					break;
			}
		}
	}

	// finished method
	private void loadMusic()
	{
		minim = new Minim(this);
		player = minim.loadFile("vr.mp3");
		player.setGain(0);

		player.play();
		player.loop();
	}

	public void stop()
	{
		// always close Minim audio classes when you are done with them
		player.close();
		minim.stop();
		super.stop();
	}
}