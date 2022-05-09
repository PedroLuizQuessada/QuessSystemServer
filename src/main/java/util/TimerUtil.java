package util;

import exception.TimerException;

public class TimerUtil {
    public void aguardarMinutos(long minutos) throws TimerException {
        try{
            Thread.sleep(minutos * 1000 * 60);
        }
        catch (InterruptedException ex){
            throw new TimerException(minutos, "minutos");
        }
    }
}
