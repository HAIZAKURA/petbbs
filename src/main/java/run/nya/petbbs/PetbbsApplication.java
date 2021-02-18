package run.nya.petbbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan("run.nya.petbbs.mapper")
@SpringBootApplication
public class PetbbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetbbsApplication.class, args);
    }

}
