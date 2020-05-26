import utilities.Message;
import utilities.MessageBuilder;
import utilities.Schedule;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import static utilities.Message.SCHEDULES;

public class Main {
    /**
     * Main method to run the client program. Creates instance of {@link GUI}, makes
     * it visible and runs it on the AWT event dispatching thread.
     *
     * @param args the command line arguments, not used in this program
     */
    public static void main(String[] args) {

        // Read network.props file to obtain host and port to connect to.
        try (InputStream fileStream = new FileInputStream(GUI.NETWORK_PROPERTIES_FILENAME)) {

            Properties props = new Properties();

            props.load(fileStream);

            String host = props.getProperty("host");

            if (host == null) {
                throw new UnknownHostException();
            }

            int port = Integer.parseInt(props.getProperty("port"));

            try {

                Socket serverSocket = new Socket(host, port);

                try (ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream())) {

                    // Special token "viewer" allows to get all schedules.
                    oos.writeObject(MessageBuilder.build(null, null, SCHEDULES, null, null, "viewer", null, null, null,
                            null, null, null, null));

                    try (ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream())) {
                        Message msg = (Message) ois.readObject();

                        List<Schedule> schedules = msg.schedules();
                        for (Schedule sh : schedules) {
                            Client.runViewer(sh);
                        }
                    } catch (ClassNotFoundException e) {
                        Client.displayError(e.getMessage());
                    }
                } finally {
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                }
            } catch (UnknownHostException e) {
                Client.displayError("Host found in " + GUI.NETWORK_PROPERTIES_FILENAME + " (" + host + ") is not valid.");
            } catch (IOException e) {
                Client.displayError(e.getMessage());
            } catch (NumberFormatException e) {
                Client.displayError("Port found in " + GUI.NETWORK_PROPERTIES_FILENAME + " (" + props.getProperty("port")
                        + ") is not valid.");
            }

        } catch (FileNotFoundException e1) {
            Client.displayError(GUI.NETWORK_PROPERTIES_FILENAME + " file with host and port to connect to "
                    + "the server was not found.");
        } catch (IOException e1) {
            Client.displayError(e1.getMessage());
        }
    }
}
