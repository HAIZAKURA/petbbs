package run.nya.petbbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("run.nya.petbbs.mapper")
public class PetbbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetbbsApplication.class, args);
    }

}
