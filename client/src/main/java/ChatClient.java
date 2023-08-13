import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClient {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private String userName;

    public ChatClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private volatile boolean running = true;  // флаг для управления потоком чтения
    public void start() throws IOException {
        try {
            // Прием имени пользователя
            System.out.println(in.readLine());  // "Введите ваше имя:"
            userName = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.println(userName);

            // Запускаем поток для чтения сообщений
            new Thread(() -> {
                try {
                    String serverMessage;
                    while (running && (serverMessage = in.readLine()) != null) {  // учитываем значение флага
                        logMessage(serverMessage);
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    if (running) {  // только если исключение не было вызвано нами же при завершении
                        e.printStackTrace();
                    }
                }
            }).start();

            // Главный поток отправляет сообщения
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (running && !(message = userInput.readLine()).equals("/exit")) {
                out.println(message);
            }

            stop();
            System.out.println("Вы вышли из чата.");

        } finally {
            socket.close();
            out.close();
            in.close();
        }
    }

    private void stop() throws IOException {
        running = false;  // установка флага в false, чтобы поток чтения завершился
        out.close();
        in.close();
        socket.close();
    }

    private void logMessage(String message) throws IOException {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String fullMessage = userName + " (" + timestamp + "): " + message;
        try (FileWriter fileWriter = new FileWriter("client_file.log", true)) {
            fileWriter.write(fullMessage + "\n");
        }
    }

    public static void main(String[] args) {
        try {
            int port = ClientSettings.getPort();
            ChatClient client = new ChatClient("localhost", port);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
