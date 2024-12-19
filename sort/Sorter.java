package sort;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

public class Sorter {
	JFrame frame;
	JLabel background;
	BufferedImage pic;
	int[] data;
	JButton rand;
	JButton rand2;
	JButton reverse;
	JButton sort;
	JButton help;
	JComboBox sortingnamessel;
	JSlider hues;
	int store = 0;
	int[] pidgihol;
	int cf = 0;
	Timer t2;
	int ty = Toolkit.getDefaultToolkit().getScreenSize().height;
	int tx = Toolkit.getDefaultToolkit().getScreenSize().width;

	public void init() {
		frame = new JFrame("Sorter");
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds((tx - 800) / 2, (ty - 650) / 2, 800, 650);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(50, 50, 50));
		background = new JLabel();
		background.setBounds(0, 0, 800, 400);
		background.setFocusable(false);
		rand = new JButton("Randomise (N)");
		rand.setBounds(60, 425, 125, 25);
		rand2 = new JButton("Randomise (P)");
		rand2.setBounds(245, 425, 125, 25);
		reverse = new JButton("Reverse");
		reverse.setBounds(430, 425, 125, 25);
		sort = new JButton("Sort");
		sort.setBounds(615, 425, 125, 25);
		hues = new JSlider(-64, 64);
		hues.setSnapToTicks(true);
		hues.setMajorTickSpacing(16);
		hues.setMinorTickSpacing(4);
		hues.setPaintLabels(true);
		hues.setPaintTicks(true);
		hues.setBounds(50, 500, 700, 50);
		hues.setBackground(new Color(50, 50, 50));
		hues.setForeground(new Color(200, 200, 200));
		help = new JButton("Help");
		help.setBounds(150, 575, 200, 25);
		String[] sortingnames = { "Bubble Sort O(n^2)", "Cocktail Shaker Sort O(n^2)", "Odd Even Sort O(n^2)",
				"Selection Sort O(n^2)", "Insertion Sort O(n^2)", "Pigeon Hole Sort O(n+r)", "Pancake Sort O(n)",
				"Stalin Sort O(n)" };
		sortingnamessel = new JComboBox(sortingnames);
		sortingnamessel.setBounds(450, 575, 200, 25);
		frame.add(background);
		frame.add(rand);
		frame.add(rand2);
		frame.add(reverse);
		frame.add(sort);
		frame.add(sortingnamessel);
		frame.add(hues);
		frame.add(help);
		background.repaint();
		rand.repaint();
		rand2.repaint();
		reverse.repaint();
		sort.repaint();
		sortingnamessel.repaint();
		hues.repaint();
		help.repaint();

		pic = new BufferedImage(800, 400, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = pic.createGraphics();
		g.setColor(new Color(15, 15, 15));
		g.fill3DRect(0, 0, 800, 400, false);
		g.dispose();
		data = new int[128];
		for (int i = 0; i < 128; i++) {
			data[i] = i + 1;
		}
		rand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				randomisearr(128, 128);
			}
		});
		rand2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				randomisearr2(128);
			}
		});
		reverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reverse(128);
			}
		});
		sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				store = 0;
				t2 = new Timer();
				t2.schedule(new TimerTask() {
					public void run() {
						if (issorted()) {
							t2.cancel();
						} else {
							sortarr();
							update();
						}
					}
				}, 75, 75);

			}
		});
		hues.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {

			}

			public void mouseDragged(MouseEvent e) {
				update();
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						"Help\nRandomise (N) -> generates a random series of bars \nRandomise (P) -> generates a shuffles series of consecutive and increasing bars \nReverse -> generates a series of consecutive and decreasing bars \nSort -> Sorts the bars \n\nBubble Sort -> Swaps adjacent bars if they are not in order from left to right  \nCocktail Shaker Sort -> Bubble sort but from both sides \nOdd Even Sort -> Bubble sort but alternating between odd and even positions \nSelection Sort -> Finds the next smallest bar and arranges it \nInsertion Sort -> Inserts the bars into the sorted part of the array \nPigeon hole Sort -> Sorts the bars by putting them into another array like pigeons in pigeon holes and then loads them back \nPancake sort -> Flips a collection of bars from the highest to the end \nStalin Sort -> All who are out of order must be sent to the gulag",
						"help", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				update();
			}
		}, 500, 500);
	}

	public void update() {
		Graphics g = pic.createGraphics();
		g.setColor(new Color(15, 15, 15));
		g.fill3DRect(0, 0, 800, 400, false);
		if (sortingnamessel.getSelectedIndex() == 7) {
			if (data.length < 128) {
				if (issorted()) {
					g.setColor(new Color(0.9f, 0.0f, 0.0f, 0.9f));
					g.fill3DRect(0, 0, 800, 400, false);
				}
			}
			hues.setValue(16);
			for (int i = 0; i < data.length; i++) {
				g.setColor(new Color(Color.HSBtoRGB((float) ((hues.getValue()) * 2) / 255, 0.9f, 0.9f)));
				g.fill3DRect(10 + (i * 6), 400 - (data[i] * 3), 5, data[i] * 3, true);
			}
		} else {
			for (int i = 0; i < data.length; i++) {
				g.setColor(new Color(Color.HSBtoRGB((float) ((data[i] + hues.getValue()) * 2) / 255, 0.9f, 0.9f)));
				g.fill3DRect(10 + (i * 6), 400 - (data[i] * 3), 5, data[i] * 3, true);
			}
		}
		background.setIcon(new ImageIcon(pic));
		background.repaint();
	}

	public void randomisearr(int max, int size) {
		int arr[] = new int[size];
		for (int i = 0; i < data.length; i++) {
			arr[i] = (int) (Math.random() * max) + 1;
		}
		data = arr;
	}

	public void randomisearr2(int max) {
		int arr[] = new int[max];
		int temp;
		for (int i = 0; i < max; i++) {
			arr[i] = i + 1;
		}
		for (int i = 0; i < 1000; i++) {
			int x = (int) (Math.random() * max);
			int y = (int) (Math.random() * max);
			temp = arr[x];
			arr[x] = arr[y];
			arr[y] = temp;
		}
		data = arr;
	}

	public void reverse(int max) {
		int arr[] = new int[max];
		for (int i = 0; i < max; i++) {
			arr[max - i - 1] = i + 1;
		}
		data = arr;
	}

	public void sortarr() {
		if (sortingnamessel.getSelectedIndex() == 0) {
			for (int i = 0; i < data.length - 1; i++) {
				if (data[i] > data[i + 1]) {
					int temp;
					temp = data[i];
					data[i] = data[i + 1];
					data[i + 1] = temp;
				}
			}
		} else if (sortingnamessel.getSelectedIndex() == 1) {
			if (store == 0) {
				for (int i = 0; i < data.length - 1; i++) {
					if (data[i] > data[i + 1]) {
						int temp;
						temp = data[i];
						data[i] = data[i + 1];
						data[i + 1] = temp;
					}
				}
				store = 1;
			} else {
				for (int i = data.length - 1; i > 0; i--) {
					if (data[i] < data[i - 1]) {
						int temp;
						temp = data[i];
						data[i] = data[i - 1];
						data[i - 1] = temp;
					}
				}
				store = 0;
			}

		} else if (sortingnamessel.getSelectedIndex() == 2) {

			for (int i = store; i < data.length - 1; i += 2) {
				if (data[i] > data[i + 1]) {
					int temp;
					temp = data[i];
					data[i] = data[i + 1];
					data[i + 1] = temp;
				}
			}
			if (store == 0) {
				store = 1;
			} else {
				store = 0;
			}

		} else if (sortingnamessel.getSelectedIndex() == 3) {
			int smallest = store;
			for (int i = store + 1; i < data.length; i++) {
				if (data[smallest] > data[i]) {
					smallest = i;
				}
			}
			int temp;
			temp = data[smallest];
			data[smallest] = data[store];
			data[store] = temp;
			store++;
		} else if (sortingnamessel.getSelectedIndex() == 4) {
			for (int j = 0; j < store; j++) {
				if (data[j] > data[store]) {
					int temp;
					temp = data[j];
					data[j] = data[store];
					data[store] = temp;
				}
			}
			store++;
		} else if (sortingnamessel.getSelectedIndex() == 5) {
			if (store == 0) {
				pidgihol = new int[data.length + 1];
				for (int i = 0; i < pidgihol.length; i++) {
					pidgihol[i] = 0;
				}
				for (int i = 0; i < data.length; i++) {
					pidgihol[data[i]]++;
				}
			} else {
				for (int i = 0; i < pidgihol.length; i++) {
					if (pidgihol[i] > 0) {
						data[store-1] = i;
						pidgihol[i]--;
						break;	
					}
				}
			}
			store++;
		} else if (sortingnamessel.getSelectedIndex() == 6) {
			int end = data.length - store;
			int maxpos = 0;
			for (int i = 0; i < end; i++) {
				if (data[i] > data[maxpos]) {
					maxpos = i;
				}
			}
			int arr[] = data.clone();
			for (int i = maxpos; i < end; i++) {
				arr[i] = data[end - i + maxpos - 1];
				System.out.println(data[i] + " " + data[end - i + maxpos - 1]);
			}
			data = arr;
			store++;
		} else if (sortingnamessel.getSelectedIndex() == 7) {
			if (data.length > 1) {
				int[] arr = new int[data.length - 1];
				arr[0] = data[0];
				int i;
				for (i = 0; i < data.length - 2; i++) {
					if (data[i] > data[i + 1]) {
						break;
					} else {
						arr[i + 1] = data[i + 1];
					}
				}
				i++;
				System.arraycopy(data, i + 1, arr, i, data.length - i - 1);
				data = arr;
			}
		}
	}

	public boolean issorted() {
		boolean flag = true;
		for (int i = 0; i < data.length - 1; i++) {
			if (data[i] > data[i + 1]) {
				flag = false;
			}
		}
		return (flag);
	}

	public static void main(String[] args) {
		Sorter s = new Sorter();
		s.init();
	}
}
