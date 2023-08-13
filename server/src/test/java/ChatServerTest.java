import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ChatServerTest {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter clientWriter;

    @BeforeEach
    public void setUp() throws IOException {
        serverSocket = mock(ServerSocket.class);
        clientSocket = mock(Socket.class);
        clientWriter = mock(PrintWriter.class);

        when(serverSocket.accept()).thenReturn(clientSocket);
        when(clientSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
    }

    @AfterEach
    public void tearDown() {
        ChatServer.clients.clear();
    }

    @Test
    public void testBroadcastMessage() throws IOException {
        // Добавляем мок клиента в список клиентов сервера
        ChatServer.clients.add(clientWriter);

        // Тестируем метод broadcastMessage
        String message = "test message";
        ChatServer.broadcastMessage(message);

        // Проверяем, что сообщение было отправлено всем клиентам
        verify(clientWriter, times(1)).println(message);
    }

    @Test
    public void testLogMessage() throws IOException {
        // Тестируем метод logMessage
        String message = "log message";
        ChatServer.logMessage(message);

    }
}
