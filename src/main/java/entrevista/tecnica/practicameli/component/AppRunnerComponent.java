package entrevista.tecnica.practicameli.component;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.transaction.annotation.Transactional;

public class AppRunnerComponent implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunnerComponent.class);

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        logger.info("Updating data, running");

        /*
         * Satelite kenobi = new Satelite("kenobi");
         * Satelite sato = new Satelite("sato");
         * Satelite skywalker = new Satelite("skywalker");
         * 
         * this.sateliteRepository.save(kenobi);
         * this.sateliteRepository.save(sato);
         * this.sateliteRepository.save(skywalker);
         */

    }

}
