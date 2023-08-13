import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {

    @Test
    void testGetPort() throws IOException {
        // Метод должен считывать порт из файла `server_settings.txt`
        assertEquals(8080, Settings.getPort()); // предполагая, что порт в файле установлен в 8080
    }
}
