import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import utilities.Schedule;

/**
 * The Class Client to connect to the server, retrieve schedules and run viewers
 * according to the schedule times.
 */
public class Client {

	/**
	 * Runs viewer.
	 *
	 * @param sched the schedule
	 */
	public static void runViewer(Schedule sched) {

		new Thread(() -> {
			final Schedule sh = sched;
			int minutes = 0;
			if (sh.getRepeat().equals("Hour")) {
				minutes = 60;
			} else if (sh.getRepeat().equals("Day")) {
				minutes = 60 * 24;
			} else if (sh.getRepeat().startsWith("Minutes")) {
				minutes = Integer.parseInt(sh.getRepeat().split(":")[1]);
			}
			long millis = minutes * 60 * 1000;
			LocalDateTime limit = sh.getDateTime().plusMinutes(sh.getDuration());
			java.util.Timer tmr = new java.util.Timer();
			tmr.schedule(new TimerTask() {
				GUI viewer = null;

				@Override
				public void run() {

					if (LocalDateTime.now().isBefore(limit) || LocalDateTime.now().isEqual(limit)) {

						SwingUtilities.invokeLater(() -> {
							if (viewer == null)
							viewer = new GUI("Billboard Viewer", sh.getIdBillboard());
							viewer.setVisible(true);
						});
					} else {
						if (viewer != null)
							viewer.dispose(); // Close viewer after limit.
						tmr.cancel(); // Cancel timer.
					}
				}

			}, Date.from(sched.getDateTime().atZone(ZoneId.systemDefault()).toInstant()), millis);
		}).start();
	}

	/**
	 * Displays error.
	 *
	 * @param error the error
	 */
	public static void displayError(String error) {
		JOptionPane.showMessageDialog(null, error, "Failed", JOptionPane.ERROR_MESSAGE);
	}

}
