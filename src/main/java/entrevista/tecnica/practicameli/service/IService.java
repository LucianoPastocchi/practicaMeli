package entrevista.tecnica.practicameli.service;

import org.springframework.boot.json.JsonParseException;

public interface IService {

    Boolean isMutant(String[] dna);

    String[] getDNAFromRequestBody(String bodyContent) throws JsonParseException;

}
