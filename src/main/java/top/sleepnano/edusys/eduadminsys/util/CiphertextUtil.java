package top.sleepnano.edusys.eduadminsys.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CiphertextUtil {

    public static String getBCryptPasswordEncoder(String plaintext){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(plaintext);
    }

}
