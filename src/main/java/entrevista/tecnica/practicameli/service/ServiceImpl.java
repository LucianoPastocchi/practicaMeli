package entrevista.tecnica.practicameli.service;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ServiceImpl implements IService {

    private static final int SECUENCIA_MINIMA = 4;

    private int secuenciasEncontradas;

    private int tamanioMatriz;

    private char[][] matriz;

    @Override
    public Boolean isMutant(String[] dna) {

        secuenciasEncontradas = 0;
        tamanioMatriz = dna.length;

        matriz = new char[tamanioMatriz][tamanioMatriz];

        // Convierto a matriz de chars
        for (int i = 0; i < tamanioMatriz; i++) {
            matriz[i] = dna[i].toCharArray();
        }

        // Reviso filas y columnas
        for (int i = 0; i < tamanioMatriz; i++) {
            secuenciasEncontradas += checkFila(matriz, i);
            secuenciasEncontradas += checkColumna(matriz, i);

            if (secuenciasEncontradas >= 2) {
                return true;
            }

        }

        // Reviso diagonales
        for (int i = 0; i <= tamanioMatriz - SECUENCIA_MINIMA; i++) {
            secuenciasEncontradas += checkDiagonales(matriz, i, 0, 1, 1); // Diagonal principal (↘)
            secuenciasEncontradas += checkDiagonales(matriz, 0, i, 1, 1); // Diagonal principal desde columnas (↘)
            secuenciasEncontradas += checkDiagonales(matriz, i, tamanioMatriz - 1, 1, -1); // Diagonal inversa (↙)
            secuenciasEncontradas += checkDiagonales(matriz, 0, tamanioMatriz - 1 - i, 1, -1); // Diagonal inversa desde
                                                                                               // columnas (↙)

            if (secuenciasEncontradas >= 2) {
                return true;
            }
        }

        return false;
    }

    private int checkFila(char[][] matriz, int row) {
        int contador = 0;
        int consecutivos = 1;
        for (int i = 1; i < matriz[row].length; i++) {
            if (matriz[row][i] == matriz[row][i - 1]) {
                consecutivos++;
                if (consecutivos == SECUENCIA_MINIMA) {
                    contador++;
                    break;
                }
            } else {
                consecutivos = 1;
            }
        }
        return contador;
    }

    private int checkColumna(char[][] matrix, int column) {
        int contador = 0;
        int consecutivos = 1;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][column] == matrix[i - 1][column]) {
                consecutivos++;
                if (consecutivos == SECUENCIA_MINIMA) {
                    contador++;
                    break;
                }
            } else {
                consecutivos = 1;
            }
        }
        return contador;
    }

    private int checkDiagonales(char[][] matrix, int startX, int startY, int dx, int dy) {
        int contador = 0;
        int consecutivos = 1;
        int x = startX + dx;
        int y = startY + dy;

        while (x < matrix.length && y >= 0 && y < matrix.length) {
            if (matrix[x][y] == matrix[x - dx][y - dy]) {
                consecutivos++;
                if (consecutivos == SECUENCIA_MINIMA) {
                    contador++;
                    break;
                }
            } else {
                consecutivos = 1;
            }
            x += dx;
            y += dy;
        }

        return contador;
    }

    @Override
    public String[] getDNAFromRequestBody(String bodyContent) throws JsonParseException {

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> parsed = jsonParser.parseMap(bodyContent);

        if (!parsed.containsKey("dna")) {
            throw new JsonParseException();
        }

        List<String> dnaList = (List<String>) parsed.get("dna");

        // Convertir la lista en un array
        String[] dnaArray = dnaList.toArray(new String[] {});

        return dnaArray;
    }

}
