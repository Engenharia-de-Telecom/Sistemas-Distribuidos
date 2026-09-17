package engtelecom.std;

import engtelecom.std.agenda.AgendaGrpc;
import engtelecom.std.agenda.Pessoa;
import engtelecom.std.agenda.Resposta;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;

public class AgendaImpl extends AgendaGrpc.AgendaImplBase {

    private HashMap<Integer, Pessoa> agenda = new HashMap<>();

    @Override
    public void adicionar(Pessoa request, StreamObserver<Resposta> responseObserver) {

        String mensagem = "adicionado com sucesso";

        if(!this.agenda.containsKey(request.getId())){
            this.agenda.put(request.getId(), request);
        } else{
            mensagem = String.format("%d já existe", request.getId());
        }
        Resposta resposta = Resposta.newBuilder().setResultado("adicionado com sucesso").build();

        responseObserver.onNext(resposta);
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(Pessoa request, StreamObserver<Pessoa> responseObserver) {
        Pessoa p = this.agenda.get(request.getId());
        responseObserver.onNext(p);
        responseObserver.onCompleted();


    }
}
