package run.nya.petbbs.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner
 *
 * 2021/02/18
 */
@Component
public class ServerRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ServerRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info(" _  _   _   ___ ____  _   _  ___   _ ___    _");
        logger.info("| || | /_\\ |_ _|_  / /_\\ | |/ / | | | _ \\  /_\\");
        logger.info("| __ |/ _ \\ | | / / / _ \\| ' <| |_| |   / / _ \\");
        logger.info("|_||_/_/ \\_\\___/___/_/ \\_\\_|\\_\\\\___/|_|_\\/_/ \\_\\");
        logger.info("Server: http://127.0.0.1:8080 ⚡️");
        logger.info("ApiDoc: http://127.0.0.1:8080/swagger-ui.html 📖");
    }

}

//     _  _   _   ___ ____  _   _  ___   _ ___    _
//    | || | /_\ |_ _|_  / /_\ | |/ / | | | _ \  /_\
//    | __ |/ _ \ | | / / / _ \| ' <| |_| |   / / _ \
//    |_||_/_/ \_\___/___/_/ \_\_|\_\\___/|_|_\/_/ \_\
