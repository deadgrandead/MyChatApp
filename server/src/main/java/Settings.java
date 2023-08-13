import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    public static int getPort() throws IOException {
        Properties prop = new Properties();

        // Загрузка файла из каталога ресурсов
        InputStream in = Settings.class.getClassLoader().getResourceAsStream("server_settings.txt");
        if (in == null) {
            throw new IOException("File server_settings.txt not found in resources.");
        }

        prop.load(in);
        in.close();

        return Integer.parseInt(prop.getProperty("port"));
    }
}
