package entrevista.tecnica.practicameli.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Mutant")
public class Mutant {

    @Id
    String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    @Override
    public String toString() {
        return "Mutant [dna=" + dna + "]";
    }

}
