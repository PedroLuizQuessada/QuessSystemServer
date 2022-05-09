package exception;

public class DiretorioException extends Exception {
    public DiretorioException(String acao, String path) {
        super(String.format("Falha ao %s o diretório %s", acao, path));
    }
}
