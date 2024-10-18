package entrevista.tecnica.practicameli.controller;

import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.json.JsonParseException;
import entrevista.tecnica.practicameli.repository.*;
import entrevista.tecnica.practicameli.service.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import entrevista.tecnica.practicameli.model.Mutant;;

@RestController
@RequestMapping("/")
public class AppController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MutantRepository mutantRepository;

    @Autowired
    private ServiceImpl service;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> init() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Practica MELI Pastocchi");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Determina si es mutante o no", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Es mutante"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "No es mutante"),
            @ApiResponse(code = 500, message = "Internal error server")
    })
    @PostMapping(value = "mutant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> esMutante(@RequestBody String dna) {

        log.info("Consultando si ya existe el mutante");

        try {
            String[] dnaArray = service.getDNAFromRequestBody(dna);

            List<Mutant> enMemoria = mutantRepository.findByDna(dnaArray);

            Mutant mutante = new Mutant();

            if (!enMemoria.isEmpty()) {
                return ResponseEntity.ok("Mutante");
            } else {
                Boolean esMutante;
                esMutante = service.isMutant(dnaArray);
                if (esMutante) {
                    mutante.setDna(dnaArray);
                    mutantRepository.save(mutante);
                    return ResponseEntity.ok("Mutante");
                }
            }

        } catch (JsonParseException e) {
            log.error("Can't parse the body: " + e.getLocalizedMessage());
        }

        log.error("No es mutante");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es mutante");

    }

}
