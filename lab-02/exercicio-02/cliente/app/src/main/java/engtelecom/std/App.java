package engtelecom.std;

import module java.base;

public class App {
    public static void main(String[] args) {
        String nomeArquivo = "/home/aluno/Downloads/slides.pdf";
    try (var socket = new Socket("localhost", 1234);
        var dos = new DataOutputStream(socket.getOutputStream());
        var dis = new DataInputStream(socket.getInputStream())) {
        // Envia o nome do arquivo desejado
        dos.writeUTF(nomeArquivo);
        dos.flush();
        // Indica o caminho de destino para salvar o arquivo recebido
        Path destino = Path.of(nomeArquivo);
        // Lê a resposta do servidor (tamanho do arquivo ou -1 para indicação de erro)
        long tamanho = dis.readLong();
        if (tamanho > 0) {
            // Irá salvar o arquivo recebido no caminho especificado, substituindo se já existir
            long bytesRecebidos = Files.copy(dis, destino, StandardCopyOption.REPLACE_EXISTING);
            System.out.printf("Arquivo salvo: %s (%d / %d bytes)%n",
            nomeArquivo, bytesRecebidos, tamanho);
        } else {
            System.out.println("O servidor informou que o arquivo não existe: " + nomeArquivo);
            }

        }catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}