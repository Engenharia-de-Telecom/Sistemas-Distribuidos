package engtelecom.std;

import module java.base;

public record AtenderClientes(Socket clientSocket) implements Runnable{

    @Override
    public void run() {
        try {
            var enderecoCliente = clientSocket.getInetAddress().getHostAddress();
            var portaCliente = clientSocket.getPort();

            // vai executar as linhas abaixo quando um cliente conectar
            System.out.printf("Cliente conectado: %s:%d%n", enderecoCliente, portaCliente);

            // Cliente já conectou. Agora vamos estabelecer fluxos de entrada e saida;
            // Atrelar a saida de um lado com a entrada do outro;
            // Isso também gera uma exceção => tratar;
            // 'var' infere o tipo da variavel pelo tipo de dado atribuído à variavel.
            var reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8)
            ); // input

            var writer = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)
            ); // output

            // Protocolo de comunicação
            // 1- cliente envia a primeira mensagem => servidor deve lê-la

            String mensagem = " ";
            do {

                mensagem = reader.readLine();
                System.out.printf("[%s:%d] -> %s%n", enderecoCliente, portaCliente, mensagem);

                // 2- Servidor escreve para o cliente
                writer.write(mensagem.toUpperCase());
                writer.newLine();
                writer.flush(); // descarregar buffer

            } while (!(mensagem.equalsIgnoreCase("sair")));

            System.out.println("Servidor encerrado!");
            reader.close();
            writer.close();
        }catch(Exception e){
            System.err.println("Erro: " + e);
        }
    }
}
