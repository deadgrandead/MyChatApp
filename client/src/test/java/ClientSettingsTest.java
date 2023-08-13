import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientSettingsTest {

    @Test
    void testGetPort() throws IOException {
        // Метод должен считывать порт из файла `client_settings.txt`
        assertEquals(8080, ClientSettings.getPort()); // предполагая, что порт в файле установлен в 8080
    }
}
