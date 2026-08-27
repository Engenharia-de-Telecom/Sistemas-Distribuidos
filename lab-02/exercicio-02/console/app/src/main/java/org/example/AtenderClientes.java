package engtelecom.std;

import module java.base;

public record AtenderClientes(Socket clientSocket) implements Runnable{

    @Override
    public void run() {
        
        var enderecoCliente = clientSocket.getInetAddress().getHostAddress();
        var portaCliente = clientSocket.getPort();
        System.out.printf("Cliente conectado: %s:%d%n", enderecoCliente, portaCliente);

        try (var socket = clientSocket;
            var dis = new DataInputStream(clientSocket.getInputStream());
            var dos = new DataOutputStream(clientSocket.getOutputStream())
            ){
            
            String nomeArquivo = dis.readUTF();
            System.out.println("Arquivo solicitado: " + nomeArquivo);

            var arquivo = Path.of(nomeArquivo);
            
            if (Files.exists(arquivo)) {

                // Se o arquivo existe, envie seu tamanho e conteúdo
                long tamanho = Files.size(arquivo);

                dos.writeLong(tamanho);
                dos.flush();

                long bytesEnviados = Files.copy(arquivo, dos);

                dos.flush();

                System.out.printf("Enviado: %s (%d bytes)%n", nomeArquivo, bytesEnviados);

            } else {
                // Se o arquivo não existe, envie -1 para indicar erro
                dos.writeLong(-1);
                dos.flush();
                System.out.println("Arquivo não encontrado: " + nomeArquivo);
            }
        }catch(Exception e){
            System.err.println("Erro: " + e);
        }
    }
}
