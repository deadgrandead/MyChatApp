import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final PrintWriter writer;

    public ClientHandler(Socket clientSocket, PrintWriter writer) {
        this.clientSocket = clientSocket;
        this.writer = writer;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            writer.println("Введите ваше имя:");
            String name = reader.readLine();

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String fullMessage = name + " (" + timestamp + "): " + clientMessage;

                ChatServer.broadcastMessage(clientMessage); // Передаем "сырое" сообщение
                ChatServer.logMessage(fullMessage); // Логируем форматированное сообщение
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

