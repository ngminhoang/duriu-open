package duriu.deton.duriuopen.duriuopen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class DuriuOpenApplication {

    public static void main(String[] args) {
        // Ensure the JVM timezone is a Postgres-recognized zone name.
        // Some systems report deprecated IDs like "Asia/Saigon" which Postgres rejects.
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        System.setProperty("user.timezone", "Asia/Ho_Chi_Minh");

        SpringApplication.run(DuriuOpenApplication.class, args);
    }

}
