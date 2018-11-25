import org.mpo.newstracker.security.SecurityConstants;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Test {
    public static  void  main(String [] args){
        Long EXPIRATION_TIME = 60L;
        LocalDateTime expirationTime = LocalDateTime.now().plus(EXPIRATION_TIME, ChronoUnit.SECONDS);
        System.out.println(expirationTime);

    }
}
