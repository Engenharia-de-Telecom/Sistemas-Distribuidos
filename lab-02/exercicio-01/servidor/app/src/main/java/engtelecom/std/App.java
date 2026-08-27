// ./gradlew installDist -q --console=plain
// ./app/build/install/app/bin/app

package engtelecom.std;

import module java.base; // apenas para JAVA 25. Tem varias bibliotecas/classes

public class App {

    public static void main(String[] args) {

        int porta  = 1234;
        System.out.println("..:: Servidor ::..");

        // é obrigatório tratar exceção de a porta estar sendo utilizada por outro processo
        try(ServerSocket serverSocket = new ServerSocket(porta)){

            System.out.println("aguardando por conexões...");

            // Enquanto a thread atual não for interrompida
            while(!Thread.currentThread().isInterrupted()){
            Socket clientSocket = serverSocket.accept(); // socket do cliente
            Thread.ofVirtual().start(new AtenderClientes(clientSocket));
            }

        } catch(Exception e){
            System.err.println("Erro: " + e);
        }
    }
}
