package exception;

public class TimerException extends Exception {
    public TimerException(long tempo, String unidade){
        super(String.format("Falha ao aguardar %d %s", tempo, unidade));
    }
}
