package run.nya.petbbs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.nya.petbbs.mapper.SysSensitiveWordMapper;

import java.util.List;

@SpringBootTest
public class WordTest {

    @Autowired
    private SysSensitiveWordMapper sysSensitiveWordMapper;

    @Test
    public void test() {
        List<String> words = sysSensitiveWordMapper.getWords();
        System.out.println(words);
    }

}
