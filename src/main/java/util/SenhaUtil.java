package util;

import java.util.Base64;

public class SenhaUtil {
    public String criptografar(String senhaDescriptografada){
        return Base64.getMimeEncoder().encodeToString(senhaDescriptografada.getBytes());
    }
}
