package new_sd3;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class AppWindow extends JFrame {

	private JFrame frmSkywars;
	private GUI gui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow window = new AppWindow();
					window.frmSkywars.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow() {
		getContentPane().setForeground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
		setBackground(Color.DARK_GRAY);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize sounds to avoid lag
		SoundEffects.init();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmSkywars = new JFrame();
		frmSkywars.setTitle("Skywars");
		frmSkywars.setResizable(false);
		frmSkywars.setBounds(0, 0, 700, 500);
		frmSkywars.setLocationRelativeTo(null);
		frmSkywars.setBackground(Color.BLACK);
		frmSkywars.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSkywars.getContentPane().setLayout(new CardLayout(0, 0));

		gui = new GUI();
		frmSkywars.getContentPane().add(gui);

		gui.startGame();

		frmSkywars.pack();

	}
}
