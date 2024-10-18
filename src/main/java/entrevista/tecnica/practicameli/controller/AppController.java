package entrevista.tecnica.practicameli.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import entrevista.tecnica.practicameli.model.Mutant;
import entrevista.tecnica.practicameli.model.dto.ResponseDTO;
import entrevista.tecnica.practicameli.repository.MutantRepository;
import entrevista.tecnica.practicameli.service.ServiceImpl;
import org.springframework.boot.json.JsonParseException;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@RestController
@RequestMapping("/")
public class AppController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MutantRepository mutantRepository;

    @Autowired
    private ServiceImpl service;

    @Autowired
    private RedisTemplate<String, Boolean> redisTemplate;

    @PostMapping(value = "mutant", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseDTO esMutante(@RequestBody String dna, HttpServletResponse response)
            throws IOException {

        log.info("Consultando si ya existe el mutante");

        try {

            String[] dnaArray = service.getDNAFromRequestBody(dna);

            ValueOperations<String, Boolean> valueOperations = redisTemplate.opsForValue();
            // Clave para Redis
            String dnaKey = String.join("", dnaArray);

            // Verificar si ya está en caché
            Boolean cachedResult = (Boolean) redisTemplate.opsForValue().get(dnaKey);

            if (cachedResult != null) {
                if (cachedResult) {
                    response.setStatus(HttpStatus.OK.value());
                    return new ResponseDTO("Mutante (desde cache)");
                } else {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return new ResponseDTO("No es mutante (desde cache)");
                }
            }

            // Verificar en la base de datos
            List<Mutant> enMemoria = mutantRepository.findByDna(dnaArray);

            if (!enMemoria.isEmpty()) {
                valueOperations.set(dnaKey, true, 1, TimeUnit.HOURS);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseDTO("Mutante encontrado en base de datos");
            } else {
                Boolean esMutante = service.isMutant(dnaArray);
                if (esMutante) {
                    valueOperations.set(dnaKey, true, 1, TimeUnit.HOURS);
                    Mutant mutante = new Mutant();
                    mutante.setDna(dnaArray);
                    mutantRepository.save(mutante);
                    response.setStatus(HttpStatus.OK.value()); // Mutante confirmado
                    return new ResponseDTO("Mutante confirmado");
                } else {
                    valueOperations.set(dnaKey, false, 1, TimeUnit.HOURS);
                    response.setStatus(HttpStatus.FORBIDDEN.value()); // No es mutante
                    return new ResponseDTO("No es mutante");
                }
            }

        } catch (JsonParseException e) {
            log.error("No se puede parsear el cuerpo: " + e.getLocalizedMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value()); // Error en el cuerpo
            return new ResponseDTO("Error en el formato del cuerpo");
        }
    }
}
