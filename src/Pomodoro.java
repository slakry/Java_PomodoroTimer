import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Pomodoro extends JFrame implements ActionListener, ChangeListener{
	
	private Principles principles;
	private JMenuBar menuBar;
	private JMenu menuPomodoro, menuAuthor;
	private JMenuItem miAboutPomodoro, miAboutAuthor;
	private JTextField textfieldPomodoroTime;
	private JLabel labelWorkTime, labelShortBreak, labelLongBreak, labelSessions;
	private JSlider sliderWorkTime, sliderShortBreak, sliderLongBreak, sliderSessions;
	private JButton buttonStart, buttonStop, buttonReset;
	private int shortBreak=5, longBreak=15, sessions=0, minutes=25, seconds;
	private Timer timer;
	private int [] arrayMinutes, arraySeconds;
	private int i = 0;

	
	public Pomodoro() {
		
		timer = new Timer(1000, this);
		
		setSize(390,375);
		setTitle("Pomodoro Timer ver 1.0");
		setLayout(null); //pozycjonowanie bezwglêdne
		setResizable(false);
		
		//MENU
		menuBar = new JMenuBar();
		
		menuPomodoro = new JMenu("Pomodoro");
		miAboutPomodoro = new JMenuItem("About Pomodoro");
		miAboutPomodoro.addActionListener(this);
		menuPomodoro.add(miAboutPomodoro);
		
		menuAuthor = new JMenu("Author");
		miAboutAuthor = new JMenuItem("About Author");
		miAboutAuthor.addActionListener(this);
		menuAuthor.add(miAboutAuthor);
		
		setJMenuBar(menuBar);
		menuBar.add(menuPomodoro);
		menuBar.add(menuAuthor);
		
		//TEXTFIELD
		textfieldPomodoroTime = new JTextField();
		textfieldPomodoroTime.setEditable(false);
		textfieldPomodoroTime.setHorizontalAlignment(SwingConstants.CENTER);
		textfieldPomodoroTime.setFont(new Font("Sanserif", Font.BOLD, 80));
		textfieldPomodoroTime.setBounds(30, 10, 320, 90);
		add(textfieldPomodoroTime);
		setTime();
		
		//LABELS
		labelWorkTime = new JLabel("Worktime [min]: " + minutes, JLabel.RIGHT);
		labelWorkTime.setBounds(30, 185, 148, 30);
		labelWorkTime.setFont(new Font("Sanserif", Font.BOLD, 15));
		add(labelWorkTime);
		labelShortBreak = new JLabel("Short break [min]: " + shortBreak, JLabel.RIGHT);
		labelShortBreak.setBounds(30, 215, 148, 30);
		labelShortBreak.setFont(new Font("Sanserif", Font.BOLD, 15));
		add(labelShortBreak);
		labelLongBreak = new JLabel("Long break [min]: " + longBreak, JLabel.RIGHT);
		labelLongBreak.setBounds(30, 245, 148, 30);
		labelLongBreak.setFont(new Font("Sanserif", Font.BOLD, 15));
		add(labelLongBreak);
		labelSessions = new JLabel("Session: " + 0 +"/"+ 4, JLabel.RIGHT);
		labelSessions.setBounds(30, 275, 148, 30);
		labelSessions.setFont(new Font("Sanserif", Font.BOLD, 15));
		add(labelSessions);
		
		setValueLabel(minutes, shortBreak, longBreak);
		
		//SLIDERS
		sliderWorkTime = new JSlider(1,60,minutes);
		sliderWorkTime.setBounds(190, 185, 165, 30);
		sliderWorkTime.setMinorTickSpacing(1);		//podzia³ka krótka co 1
		sliderWorkTime.addChangeListener(this);
		add(sliderWorkTime);
		sliderShortBreak = new JSlider(1,10,shortBreak);
		sliderShortBreak.setBounds(190, 215, 165, 30);
		sliderShortBreak.setMinorTickSpacing(1);		
		sliderShortBreak.addChangeListener(this);
		add(sliderShortBreak);
		sliderLongBreak = new JSlider(1,30,longBreak);
		sliderLongBreak.setBounds(190, 245, 165, 30);
		sliderLongBreak.setMinorTickSpacing(1);		
		sliderLongBreak.addChangeListener(this);
		add(sliderLongBreak);
		sliderSessions = new JSlider(0,4,sessions);
		sliderSessions.setEnabled(false);
		sliderSessions.setBounds(190, 275, 165, 30);
		sliderSessions.setMinorTickSpacing(1);		
		sliderSessions.addChangeListener(this);
		add(sliderSessions);
		
		//BUTTONS
		buttonStart = new JButton("START");
		buttonStart.setBackground(Color.GREEN);
		buttonStart.setForeground(Color.WHITE);
		//buttonStart.setOpaque(false);
		buttonStart.setEnabled(true);
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				timer.start();				
			}
		});
		buttonStart.setBounds(30, 115, 100, 40);
		add(buttonStart);
		
		buttonStop = new JButton("STOP");
		buttonStop.setBackground(Color.RED);
		buttonStop.setForeground(Color.WHITE);
		buttonStop.setOpaque(true);
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.stop();
			}
		});
		buttonStop.setBounds(140, 115, 100, 40);
		add(buttonStop);
		
		buttonReset = new JButton("RESET");
		buttonReset.setBackground(Color.BLUE);
		buttonReset.setForeground(Color.WHITE);
		buttonReset.setOpaque(true);
		buttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				i = 0;
				minutes = 25;
				seconds = 0;
				shortBreak = 5;
				longBreak = 15;
				sessions = 0;
				
				sliderWorkTime.setValue(minutes);
				sliderShortBreak.setValue(shortBreak);
				sliderLongBreak.setValue(longBreak);
				sliderSessions.setValue(sessions);
						
				setValueLabel(minutes, shortBreak, longBreak);
				setTime();
				
				sliderWorkTime.setEnabled(true);
				sliderShortBreak.setEnabled(true);
				sliderLongBreak.setEnabled(true);
			}
		});
		buttonReset.setBounds(250, 115, 100, 40);
		add(buttonReset);
		
		//ARRAYS
		arrayMinutes = new int[9];
		arraySeconds = new int[9];
			
		for (int i = 0; i < arraySeconds.length; i++) {
			arraySeconds[i] = 0;
		}		
		
}
	
	public void setValueLabel(int mins, int bShort, int bLong) {
		String minutes = String.valueOf(mins);
		String shortBreak = String.valueOf(bShort);
		String longBreak = String.valueOf(bLong);
		
		if (minutes.length() == 1) {
			minutes = "0" + minutes;
		}
		if (shortBreak.length() == 1) {
			shortBreak = "0" + shortBreak;
		}
		if (longBreak.length() == 1) {
			longBreak = "0" + longBreak;
		}

		labelWorkTime.setText("Worktime [min]: " + minutes);
		labelShortBreak.setText("Short break [min]: " + shortBreak);
		labelLongBreak.setText("Long break [min]: " + longBreak);
	}
	
	public void setTime() {
		String m = String.valueOf(minutes);
		String s = String.valueOf(seconds);

		if (m.length() == 1) {
			m = "0" + m;
		}
		if (s.length() == 1) {
			s = "0" + s;
		}
		textfieldPomodoroTime.setText(m + ":" + s);
	}
	
	
	public static void main(String[] args) {
		try {
			Pomodoro app = new Pomodoro();
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			app.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source == timer) {
				
			if (seconds == 0) {
				minutes--;
				seconds = 60;
			}
			seconds--;
			if (minutes == 0) {
				minutes = 0;
			}
			if (minutes == 0 && seconds <= 5) {
				Toolkit.getDefaultToolkit().beep();
			}
			if (seconds == 0 && minutes == 0) {
				++i;
				minutes = arrayMinutes[i];
				seconds = arraySeconds[i];
				if (i == arrayMinutes.length-1) {
					minutes=seconds=0;
					sessions = 0;
					timer.stop();
					sliderSessions.setValue(sessions);
					labelSessions.setText("Session: " + sessions +"/"+ 4);
					buttonStart.setEnabled(false);
					buttonStart.setOpaque(false);
					
					setTime();
					i=0;
					sessions = 0;
				}
			} 
			
			if (i == 0) {
				sessions = 1;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Session: " + sessions +"/"+ 4);
			} else if (i == 1) {
				sessions = 1;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Short break: " + sessions +"/"+ 4);
			} else if (i == 2) {
				sessions = 2;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Session: " + sessions +"/"+ 4);
			} else if (i == 3) {
				sessions = 2;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Short break: " + sessions +"/"+ 4);
			} else if (i == 4) {
				sessions = 3;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Session: " + sessions +"/"+ 4);
			} else if (i == 5) {
				sessions = 3;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Short break: " + sessions +"/"+ 4);
			} else if (i == 6) {
				sessions = 4;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Session: " + sessions +"/"+ 4);
			} else if (i == 7) {
				sessions = 4;
				sliderSessions.setValue(sessions);
				labelSessions.setText("Long break: " + sessions +"/"+ 4);
			}  			
			setTime(); 	
		}
		
		if (source == miAboutPomodoro) {
			principles = new Principles(this);
			principles.setVisible(true);
		}
		
		if (source == miAboutAuthor) {
			JOptionPane.showMessageDialog(this, ">>> S³awomir Kryniewski <<<", "About Author", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		minutes = sliderWorkTime.getValue();
		shortBreak = sliderShortBreak.getValue();
		longBreak = sliderLongBreak.getValue();
		sessions = sliderSessions.getValue();
		labelSessions.setText("Session: " + sessions +"/"+ 4);
		
		setTime();
		setValueLabel(minutes, shortBreak, longBreak);
		
		arrayMinutes[0] = minutes;
		arrayMinutes[1] = shortBreak;
		arrayMinutes[2] = minutes;
		arrayMinutes[3] = shortBreak;
		arrayMinutes[4] = minutes;
		arrayMinutes[5] = shortBreak;
		arrayMinutes[6] = minutes;
		arrayMinutes[7] = longBreak;
		arrayMinutes[8] = 0;

		if (labelWorkTime.isFontSet()==true) {
			if (labelShortBreak.isFontSet()==true) {
				if (labelLongBreak.isFontSet()==true){
		buttonStart.setEnabled(true);
		buttonStart.setOpaque(true);
				}
			}
		}
		
		minutes = arrayMinutes[i];
		seconds = arraySeconds[i];
		
		if (buttonStart.isDisplayable()==true && timer.isRunning()) {
			sliderWorkTime.setEnabled(false);
			sliderShortBreak.setEnabled(false);
			sliderLongBreak.setEnabled(false);
		} else {
			sliderWorkTime.setEnabled(true);
			sliderShortBreak.setEnabled(true);
			sliderLongBreak.setEnabled(true);
		}
}
}