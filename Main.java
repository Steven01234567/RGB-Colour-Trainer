package draft;
/*
 *  RBG Colour Trainer:
 *  Tests the user's accuracy in identifying RGB colour values
 *  Steven Chen | 2023-04-08
 */

// Imports
import javax.swing.JFrame;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	float accuracy = 0;
	final float maxDiff = 255 * 3;
	int rounds = 0;
	final int length = 3;
	int[] rgb = new int[length], guess = new int[length];
	final char[] syms = {'R', 'G', 'B'};
	final int width = 500, height = 500;
	
	public Main() {
		super();
		setLayout(null);
		setVisible(true);
		setSize(width, height);
		setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(rgb[0], rgb[1], rgb[2]));
		g2.fillRect(0, 0, width, height);
	}
	
	/*
	 *  generateColour() sets the elements of rgb to random integers
	 *  	between 0 and 255 inclusive
	 *  effects: modifies rgb
	 */
	public void generateColour() {
		for (int i = 0; i < length; i++) {
			rgb[i] = (int)(Math.random()*256);
		}
	}
	
	/*
	 *  prompt(in) takes user input and stores it in guess
	 *  effects: reads input
	 *  		 produces output
	 *  		 modifies guess
	 */
	public void prompt(Scanner in) {
		System.out.printf("Round %d:\n", rounds);
		for (int i = 0; i < length; i++) {
			System.out.printf("%c: ", syms[i]);
			//System.out.print(rgb[i]);
			guess[i] = in.nextInt();
		}
		in.nextLine();
	}
	
	/*
	 *  results() outputs the results of a test
	 *  effects: produces output
	 *  		 modifies rounds and accuracy
	 */
	public void results() {
		float diff = 0;
		for (int i = 0; i < length; i++) {
			diff += Math.abs(rgb[i] - guess[i]);
		}
		System.out.printf("Answers:  R: %3d  G: %3d  B: %3d\nRound Accuracy: %.2f%%\n",
				rgb[0], rgb[1], rgb[2], (maxDiff - diff) / maxDiff * 100);
		accuracy *= rounds - 1;
		accuracy += (maxDiff - diff) / maxDiff;
		accuracy /= rounds;
		System.out.printf("Cummulative Accuracy: %.2f%%\n\n", accuracy * 100);
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean play = true;
		String cmd = null;
		Main m = new Main();
		while (play) {
			m.generateColour();
			m.repaint();
			m.rounds++;
			m.prompt(in);
			m.results();
			do {
				System.out.print("Play Again? (y/n) ");
				cmd = in.nextLine();
				if (!cmd.equalsIgnoreCase("y") && !cmd.equalsIgnoreCase("n")) {
					System.out.println("Invalid command. Try again.");
				}
			} while (!cmd.equalsIgnoreCase("y") && !cmd.equalsIgnoreCase("n"));	
			if (cmd.equalsIgnoreCase("n")) {
				play = false;
			} else {
				System.out.println();
			}
		}
		in.close();
	}
}
