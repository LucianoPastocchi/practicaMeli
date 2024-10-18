package entrevista.tecnica.practicameli.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import entrevista.tecnica.practicameli.model.*;
import java.util.List;;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, String> {

    List<Mutant> findByDna(String[] dna);

}
