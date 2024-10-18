package entrevista.tecnica.practicameli.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import entrevista.tecnica.practicameli.model.*;
import java.util.List;;

@Repository
public interface MutantRepository extends MongoRepository<Mutant, String> {

    List<Mutant> findByDna(String[] dna);

}
