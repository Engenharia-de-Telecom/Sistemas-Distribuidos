package engtelecom.std;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class App {


    public static void main(String[] args) throws Exception {
        IO.println("Iniciando Servidor...");

        Server servidor = ServerBuilder.forPort(50051).addService(new AgendaImpl()).build().start();

        // Para finalizar o servidor quando a JVM for finalizada
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("servidor gRPC sendo desligado pois a JVM está sendo desligada");
            servidor.shutdown();
            System.out.println("Servidor parado com sucesso");
        }));
        // Para Finalizar o servidor
        servidor.awaitTermination();


    }
}

