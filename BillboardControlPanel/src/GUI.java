import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The Class GUI that encapsulates user interface
 * for control panel. Starts from Login panel.
 */
public class GUI extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * All billboards, users, schedules are being updated every 3 seconds.
	 */
	public final static int UPDATE_DELAY = 3_000;

	/** The main panel. */
	private JPanel mainPanel = new JPanel();

	/** The login panel. */
	private LoginPanel loginPanel = new LoginPanel();

	/** The users panel. */
	private UsersPanel usersPanel = new UsersPanel();

	/** The billboard panel. */
	private BillboardsPanel billboardPanel = new BillboardsPanel();

	/** The schedules panel. */
	private SchedulesPanel schedulesPanel = new SchedulesPanel();

	/** The card layout. */
	private CardLayout cardLayout = new CardLayout();

	private boolean isLoginPanel;

	/**
	 * Instantiates a new gui.
	 *
	 * @param title the title
	 */
	public GUI(String title) {
		super(title);

		initGUIComponents();


		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!isLoginPanel) {
					if (JOptionPane.showConfirmDialog(null,
							"Do you want to exit your work will not be saved?") == 0) {
						System.exit(0);
					}
				} else {
					System.exit(0);
				}
			}
		});
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Inits the GUI components.
	 */
	private void initGUIComponents() {

		mainPanel.setLayout(cardLayout);

		mainPanel.add(loginPanel, "Login");
		mainPanel.add(usersPanel, "Users");
		mainPanel.add(billboardPanel, "Billboards");
		mainPanel.add(schedulesPanel, "Schedules");

		add(mainPanel);

		showLogin(); // Firstly login window should be shown.
	}

	/**
	 * Shows login panel.
	 */
	public void showLogin() {

		cardLayout.show(mainPanel, "Login");
		isLoginPanel = true;
	}

	/**
	 * Shows billboards panel.
	 */
	public void showBillboards() {
		isLoginPanel = false;
		cardLayout.show(mainPanel, "Billboards");
	}

	/**
	 * Shows schedules panel.
	 */
	public void showSchedules() {

		cardLayout.show(mainPanel, "Schedules");
		isLoginPanel = false;
	}

	/**
	 * Shows users panel.
	 */
	public void showUsers() {
		isLoginPanel = false;
		cardLayout.show(mainPanel, "Users");
	}

	/**
	 * Displays error.
	 *
	 * @param error the error
	 */
	public static void displayError(String error) {
		JOptionPane.showMessageDialog(null, error, "Failed", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Gets the login panel.
	 *
	 * @return the login panel
	 */
	public LoginPanel getLoginPanel() {
		return loginPanel;
	}

	/**
	 * Gets the users panel.
	 *
	 * @return the users panel
	 */
	public UsersPanel getUsersPanel() {
		return usersPanel;
	}

	/**
	 * Gets the schedules panel.
	 *
	 * @return the schedules panel
	 */
	public SchedulesPanel getSchedulesPanel() {
		return schedulesPanel;
	}

	/**
	 * Gets the billboard panel.
	 *
	 * @return the billboard panel
	 */
	public BillboardsPanel getBillboardPanel() {
		return billboardPanel;
	}

}
