import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ClientHandlerTest {

    @Test
    public void testClientHandler() throws IOException {
        // Создаем моки для сокета и писателя
        Socket socket = mock(Socket.class);
        PrintWriter writer = mock(PrintWriter.class);

        // Создаем моки для входного и выходного потоков
        InputStream inputStream = new ByteArrayInputStream("John\nHello\n".getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();

        // Настройка поведения моков
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);

        // Создание и выполнение обработчика клиента
        ClientHandler clientHandler = new ClientHandler(socket, writer);
        clientHandler.run();

        // Проверяем, было ли вызвано нужное количество методов writer.println
        verify(writer, times(1)).println("Введите ваше имя:");
        // Также можно добавить другие проверки, в зависимости от вашей логики
    }
}
