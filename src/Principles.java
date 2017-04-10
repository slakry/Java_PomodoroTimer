import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Principles extends JDialog {
	JLabel labelPrinciples, labelHyperlinks;
	JTextArea taPrinciples;
	
	
	public Principles(JFrame pomodoroInformation) {
		super(pomodoroInformation, "Pomodoro - Information", true);
		setSize(450,350);
		setLayout(null);
		
		labelPrinciples = new JLabel("Underlying principles");
		labelPrinciples.setBounds(20, 30, 200, 30);
		labelPrinciples.setFont(new Font("Sanserif", Font.BOLD, 20));
		add(labelPrinciples);
		
		taPrinciples = new JTextArea("There are six steps in the technique: \n\n" + 
									"1. Decide on the task to be done.\n" +
									"2. Set the pomodoro timer (traditionally to 25 minutes).\n" +
									"3. Work on the task until the timer rings. If a distraction pops into your head, write it down, but immediately get back on task.\n" + 
									"4. After the timer rings, put a checkmark on a piece of paper.\n" +
									"5. If you have fewer than four checkmarks, take a short break (3–5 minutes), then go to step 2.\n" +
									"6. After four pomodoros, take a longer break (15–30 minutes), reset your checkmark count to zero, then go to step 1.\n\n" + 
									"https://en.wikipedia.org/wiki/Pomodoro_Technique");
		JScrollPane scrollPane = new JScrollPane(taPrinciples);
		taPrinciples.setEditable(false);
		scrollPane.setBounds(20, 80, 390, 180);
		add(scrollPane);
	}

}
