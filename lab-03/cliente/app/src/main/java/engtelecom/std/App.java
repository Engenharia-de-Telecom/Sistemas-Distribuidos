package engtelecom.std;

import java.util.concurrent.TimeUnit;
import engtelecom.std.agenda.AgendaGrpc;
import engtelecom.std.agenda.Pessoa;
import engtelecom.std.agenda.Pessoa.NumeroTelefone;
import engtelecom.std.agenda.Pessoa.TipoTelefone;
import io.grpc.ManagedChannelBuilder;

public class App {
    public static void main(String[] args) throws Exception {

        // Por padrão o gRPC sempre será sobre TLS, como não criamos um certificado digital, forçamos aqui não usar TLS
        var channel = ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext().build();

        // Criando uma pessoa usando o padrão de projeto Builder
        var juca = Pessoa.newBuilder()
                .setNome("Juca")
                .setEmail("juca@email.com")
                .setId(1)
                .addTelefones(
                        NumeroTelefone.newBuilder()
                                .setNumero("48 3381-2800")
                                .setTipo(TipoTelefone.TRABALHO).build())
                .build();

        System.out.println("Adicionando uma pessoa na agenda de contatos no servidor");
        var agendaBlockingStub = AgendaGrpc.newBlockingStub(channel);

        var respostaAdd = agendaBlockingStub.adicionar(juca);
        System.out.print(respostaAdd);

        System.out.println("Buscando por uma pessoa na agenda de contatos");

        var resultado = agendaBlockingStub.buscar(juca);



        System.out.println("Dados da pessoa retornada pelo servidor: " + resultado);
        System.out.println("Finalizando...");

        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
}