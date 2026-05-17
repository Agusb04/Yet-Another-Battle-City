package ssj.vista.Utils;

import java.util.Map;

public class Niveles {

    public static final String NIVEL_UNO_JUGADOR = "/assets/levels/GeneratedLevels/Level1.1.xml";
    public static final String NIVEL_DOS_JUGADOR = "/assets/levels/GeneratedLevels/level2.1.xml";
    public static final String NIVEL_TRES_JUGADOR = "/assets/levels/GeneratedLevels/level3.1.xml";

    public static final String NIVEL_UNO_JUGADORES = "/assets/levels/GeneratedLevels/Level1.2.xml";
    public static final String NIVEL_DOS_JUGADORES = "/assets/levels/GeneratedLevels/level2.2.xml";
    public static final String NIVEL_TRES_JUGADORES = "/assets/levels/GeneratedLevels/level3.2.xml";

    private static final Map<Integer, String> MAPAS_1P = Map.of(
            1, NIVEL_UNO_JUGADOR,
            2, NIVEL_DOS_JUGADOR,
            3, NIVEL_TRES_JUGADOR
    );

    private static final Map<Integer, String> MAPAS_2P = Map.of(
            1, NIVEL_UNO_JUGADORES,
            2, NIVEL_DOS_JUGADORES,
            3, NIVEL_TRES_JUGADORES
    );

    /**
     * Devuelve la ruta del XML del nivel según el número y el modo de juego.
     * * Evita que las vistas tengan que mutar o romper su lógica si el día de
     * mañana se decide añadir un Nivel 4, 5 o 20.
     */
    public static String getPath(int numeroNivel, boolean esModoDosJugadores) {
        Map<Integer, String> mapas = esModoDosJugadores ? MAPAS_2P : MAPAS_1P;
        // Retorna la ruta mapeada o la del nivel 1 como fallback seguro
        return mapas.getOrDefault(numeroNivel, mapas.get(1));
    }
}