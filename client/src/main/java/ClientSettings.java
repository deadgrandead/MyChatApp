import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientSettings {
    public static int getPort() throws IOException {
        Properties prop = new Properties();

        InputStream in = ClientSettings.class.getClassLoader().getResourceAsStream("client_settings.txt");
        if (in == null) {
            throw new IOException("File client_settings.txt not found in resources.");
        }

        prop.load(in);
        in.close();

        return Integer.parseInt(prop.getProperty("port"));
    }
}