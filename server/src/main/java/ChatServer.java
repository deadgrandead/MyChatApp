import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int MAX_CLIENTS = 10;
    static final List<PrintWriter> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        int port;
        try {
            port = Settings.getPort(); // Допустим, что Settings.getPort() читает порт из файла "resources/server_settings.txt"
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);
            System.out.println("Chat server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.add(writer);

                ClientHandler clientHandler = new ClientHandler(clientSocket, writer);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcastMessage(String message) {
        synchronized (clients) {
            for (PrintWriter client : clients) {
                client.println(message); // Отправляем "сырое" сообщение клиенту
            }
        }
    }

    static void logMessage(String message) {
        try (FileWriter fileWriter = new FileWriter("file.log", true)) {
            fileWriter.write(message + "\n"); // Логируем форматированное сообщение на стороне сервера
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}