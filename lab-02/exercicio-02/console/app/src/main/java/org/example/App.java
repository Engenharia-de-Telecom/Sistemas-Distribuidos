package engtelecom.std;

import module java.base;
public class App {

    public static void main(String[] args) {

        int porta = 1234;

        System.out.println("..::Servidor::..");
        
        try (ServerSocket serverSocket = new ServerSocket(porta)){
            
            System.out.println("aguardando por conexões...");
            
            var enderecoCliente = serverSocket.getInetAddress().getHostAddress();
            var portaCliente = serverSocket.getLocalPort();
            System.out.printf("Cliente conectado: %s:%d%n", enderecoCliente, portaCliente);

            while(!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                Thread.ofVirtual().start(new AtenderClientes(clientSocket));
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}
