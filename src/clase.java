import java.util.Map;

public class clase {

    private final String name = "Luciano";

    private String[] cities = { "nashville",
            "nashville",
            "los angeles",
            "nashville",
            "memphis",
            "barcelona",
            "los angeles",
            "sevilla",
            "madrid",
            "madrid",
            "canary islands",
            "barcelona",
            "madrid",
            "nashville",
            "barcelona",
            "london",
            "berlin",
            "madrid",
            "nashville",
            "london",
            "madrid" };

    private Map<String, Integer> tablaRomanos = new Map<String, Integer>();

    public void hello() {

        System.out.println("hello " + name);

    }

    public String getName() {
        return name;
    }

    public void ciudadesRepetidas() {

        Map<String, Integer> cuento = new HashMap<String, Integer>();

        for (String city : cities) {

            cuento.put(city, cuento.getOrDefault(city, 0) + 1);

        }
    }

    public int convertiNumeroRomano(String numeroRomano) {

        tablaRomanos.put('I', 1);
        tablaRomanos.put('V', 5);
        tablaRomanos.put('X', 10);
        tablaRomanos.put('L', 50);
        tablaRomanos.put('C', 100);
        tablaRomanos.put('D', 500);
        tablaRomanos.put('M', 1000);

        int entero = 0;

        for (i = 0; i < numeroRomano.length(); i++) {

            int romanoActual = tablanumero.get(numeroRomano.charAt(i));

            if (i < numeroRomano.length() - 1 && romanoActual < tablaRomanos.get(numeroRomano.charAt(i + 1))) {
                entero -= romanoActual;

            } else {
                entero += romanoActual;
            }

        }

        return entero;
    }

}
