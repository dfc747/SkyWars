package new_sd3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GUI extends JPanel implements Runnable {

	private static MasterSpaceShip masterShip;
	private SkyTile[][] grid = new SkyTile[4][4];
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private BufferedImage background;
	private CommandManager cm;
	private JLabel lblScore;
	private JRadioButton rdbtnDefensive;
	private JButton btnMove;
	private static boolean isRunning = false;
	private static Thread thread;

	public GUI() {
		setOpaque(false);
		setLayout(null);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(0, 0, 700, 600);
		setPreferredSize(new Dimension(700, 600));
		revalidate();

		Initialise();

	}

	private void Initialise() {

		// Load background image
		try {
			URL url = ClassLoader.getSystemResource("sw.jpg");
			background = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		masterShip = new MasterSpaceShip();
		cm = CommandManager.getInstance();
		setLayout(null);
		JPanel gridPanel = new JPanel();
		gridPanel.setOpaque(false);
		gridPanel.setBackground(Color.BLACK);
		gridPanel.setLayout(new GridLayout(4, 4, 0, 0));
		gridPanel.setBounds(new Rectangle(0, 0, 700, 500));
		add(gridPanel);

		// Add Skytiles to GridPanel
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				grid[i][j] = new SkyTile();
				grid[i][j].setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
				gridPanel.add(grid[i][j]);
			}
		}

		// Create the game control panel
		JPanel ctrlPanel = new JPanel();
		ctrlPanel.setToolTipText("");
		ctrlPanel.setBounds(0, 500, 700, 100);
		ctrlPanel.setBackground(new Color(0, 0, 0, 0));
		ctrlPanel.setLayout(null);
		add(ctrlPanel);

		btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("MOVE CLICKED");
				cm.executeCommand(new CommandMove(masterShip));
				lblScore.setText(masterShip.getScore());
				repaint();

				if (!masterShip.isAlive()) {
					btnMove.setEnabled(false);
					int selected = JOptionPane.showConfirmDialog(null, "Game Over!\nPlay again?", null,
							JOptionPane.YES_NO_OPTION);
					if (selected == JOptionPane.YES_OPTION) {
						restartGame();
					}
				}

			}
		});
		btnMove.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnMove.setBounds(130, 29, 104, 60);
		ctrlPanel.add(btnMove);

		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("UNDO CLICKED");

				if (!masterShip.isAlive()) {
					masterShip.setAlive(true);
					btnMove.setEnabled(true);
				}

				int obsnum = masterShip.getObservers().size();
				// +1 is for the mastership which is not an observer
				for (int i = 0; i < obsnum + 1; i++) {
					cm.undo();
				}

				lblScore.setText(masterShip.getScore());
				repaint();
			}
		});
		btnUndo.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnUndo.setBounds(244, 29, 104, 60);
		ctrlPanel.add(btnUndo);

		// Create the Operational mode radio buttons
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Operational Mode", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 120, 80);
		ctrlPanel.add(panel);

		rdbtnDefensive = new JRadioButton("Defensive");
		rdbtnDefensive.setSelected(true);
		rdbtnDefensive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				masterShip.setShipMode(new DefensiveMode());
			}
		});
		JRadioButton rdbtnOffensive = new JRadioButton("Offensive");
		rdbtnOffensive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				masterShip.setShipMode(new OffensiveMode());
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(rdbtnDefensive)
								.addComponent(rdbtnOffensive))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(rdbtnDefensive)
						.addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE).addComponent(rdbtnOffensive)));
		panel.setLayout(gl_panel);

		buttonGroup.add(rdbtnOffensive);
		buttonGroup.add(rdbtnDefensive);

		JLabel lblScoreLabel = new JLabel("Score:");
		lblScoreLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblScoreLabel.setBounds(488, 51, 67, 24);
		ctrlPanel.add(lblScoreLabel);

		lblScore = new JLabel("0");
		lblScore.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblScore.setBounds(557, 52, 50, 22);
		ctrlPanel.add(lblScore);

		JCheckBox chkBoxVolume = new JCheckBox("Sound Effects");
		chkBoxVolume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundEffects.toggleVolume();
			}
		});
		chkBoxVolume.setSelected(true);
		chkBoxVolume.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
		chkBoxVolume.setBounds(600, 51, 97, 23);
		ctrlPanel.add(chkBoxVolume);

		JButton btnRedo = new JButton("Redo");
		btnRedo.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("REDO CLICKED");
				
				if (!masterShip.isAlive()) {
					btnMove.setEnabled(false);
				}
				
				

				int obsnum = masterShip.getObservers().size();
				// +1 is for the mastership which is not an observer
				for (int i = 0; i < obsnum + 1; i++) {
					cm.redo();
				}

				lblScore.setText(masterShip.getScore());
				repaint();
			}
		});
		btnRedo.setBounds(358, 29, 104, 60);
		ctrlPanel.add(btnRedo);

	}

	// reset game and start again
	protected void restartGame() {
		// clear score
		masterShip.score = 0;
		lblScore.setText("0");
		// reselect radio button Defensive
		rdbtnDefensive.setSelected(true);
		// clear observers
		masterShip.clearObservers();
		// clear command manager
		cm.clearCommands();
		// start
		startGame();
		masterShip = new MasterSpaceShip();
		// enable Move button
		btnMove.setEnabled(true);
		// repaint
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(background, 0, 0, null);
		grid[masterShip.getXpos()][masterShip.getYpos()].addShip(masterShip);

		for (Ship s : masterShip.getObservers()) {
			grid[s.getXpos()][s.getYpos()].addShip(s);
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				grid[i][j].fillShipLabels();
			}
		}

	}

	@Override
	public void run() {

	}

	public boolean isRunning() {
		return isRunning;
	}

	public void startGame() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public static MasterSpaceShip getMasterShip() {
		return masterShip;
	}
}
