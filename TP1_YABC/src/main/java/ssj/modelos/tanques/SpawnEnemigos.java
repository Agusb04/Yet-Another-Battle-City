package ssj.modelos.tanques;

import ssj.modelos.LogicaMovimiento.Rect;
import ssj.modelos.bloques.Bloque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnEnemigos {

    private static final int ENEMIGO_SIZE = 20;

    private final List<Enemigo> enemigos;
    private final int maxEnemigosPantalla;
    private int enemigosTotales;
    private final Random rand = new Random();

    private double tiempoSpawn;
    private final double delaySpawn;

    private final int maxSpawnsPorMinuto;
    private double timerVentanaMinuto = 0.0;
    private int spawnsEnVentana = 0;

    public SpawnEnemigos(int maxEnemigosPantalla, int enemigosTotales, double delaySpawn) {
        this(maxEnemigosPantalla, enemigosTotales, delaySpawn, 10);
    }

    public SpawnEnemigos(int maxEnemigosPantalla, int enemigosTotales, double delaySpawn, int maxSpawnsPorMinuto) {
        this.enemigos = new ArrayList<>();
        this.maxEnemigosPantalla = maxEnemigosPantalla;
        this.enemigosTotales = enemigosTotales;
        this.delaySpawn = delaySpawn;
        this.tiempoSpawn = 0;
        this.maxSpawnsPorMinuto = Math.max(1, maxSpawnsPorMinuto);
    }

    public void actualizar(double deltaTiempo, List<Bloque> bloques, int anchoMapa, int altoMapa, List<Jugador> jugadores) {

        timerVentanaMinuto += deltaTiempo;
        if (timerVentanaMinuto >= 60.0) {
            timerVentanaMinuto = 0.0;
            spawnsEnVentana = 0;
        }

        tiempoSpawn += deltaTiempo;

        boolean puedeSpawnearPorMinuto = spawnsEnVentana < maxSpawnsPorMinuto;

        if (tiempoSpawn >= delaySpawn
                && enemigos.size() < maxEnemigosPantalla
                && enemigosTotales > 0
                && puedeSpawnearPorMinuto) {
            boolean spawned = spawn(bloques, anchoMapa, altoMapa, jugadores);
            if (spawned) {
                tiempoSpawn = 0;
                spawnsEnVentana++;
            } else {
                tiempoSpawn = Math.min(tiempoSpawn, delaySpawn / 2.0);
            }
        }

        enemigos.removeIf(e -> !e.estaVivo());
    }

    // Refactorizado: Evita spawnear encima de CUALQUIER jugador vivo de la lista
    private boolean spawn(List<Bloque> bloques, int anchoMapa, int altoMapa, List<Jugador> jugadores) {
        int intentos = 0;
        int x, y;
        boolean posicionValida;

        int alturaMax = altoMapa - ENEMIGO_SIZE - 50;
        int anchoMax = anchoMapa - ENEMIGO_SIZE;

        do {
            int zona = rand.nextInt(3);
            int yMin, yMax;
            switch (zona) {
                case 0 -> { yMin = 0; yMax = alturaMax / 3; }
                case 1 -> { yMin = alturaMax / 3; yMax = 2 * alturaMax / 3; }
                default -> { yMin = 2 * alturaMax / 3; yMax = alturaMax; }
            }

            x = rand.nextInt(Math.max(1, anchoMax));
            y = yMin + rand.nextInt(Math.max(1, yMax - yMin));

            Rect nuevoRect = new Rect(x, y, ENEMIGO_SIZE, ENEMIGO_SIZE);
            posicionValida = true;

            for (Bloque b : bloques) {
                if (!b.esTransitable()) {
                    Rect rB = new Rect(b.getX(), b.getY(), ENEMIGO_SIZE, ENEMIGO_SIZE);
                    if (nuevoRect.intersects(rB)) {
                        posicionValida = false;
                        break;
                    }
                }
            }

            if (posicionValida) {
                for (Enemigo e : enemigos) {
                    Rect rE = new Rect(e.getX(), e.getY(), ENEMIGO_SIZE, ENEMIGO_SIZE);
                    if (nuevoRect.intersects(rE)) {
                        posicionValida = false;
                        break;
                    }
                }
            }

            // Validar dinámicamente contra todos los jugadores
            if (posicionValida && jugadores != null) {
                for (Jugador j : jugadores) {
                    if (j.estaVivo()) {
                        Rect rJugador = new Rect(j.getX(), j.getY(), ENEMIGO_SIZE, ENEMIGO_SIZE);
                        if (nuevoRect.intersects(rJugador)) {
                            posicionValida = false;
                            break;
                        }
                    }
                }
            }

            intentos++;
            if (intentos > 100) return false;
        } while (!posicionValida);

        TipoEnemigo tipo = switch (rand.nextInt(4)) {
            case 0 -> TipoEnemigo.BASICO;
            case 1 -> TipoEnemigo.RAPIDO;
            case 2 -> TipoEnemigo.POTENTE;
            default -> TipoEnemigo.BLINDADO;
        };

        Enemigo nuevo = switch (tipo) {
            case BASICO -> new EnemigoBasico(x, y);
            case RAPIDO -> new EnemigoRapido(x, y);
            case POTENTE -> new EnemigoPotente(x, y);
            case BLINDADO -> new EnemigoBlindado(x, y);
        };

        enemigos.add(nuevo);
        enemigosTotales--;
        return true;
    }

    public void spawnInitial(int count, List<Bloque> bloques, int anchoMapa, int altoMapa, List<Jugador> jugadores) {
        for (int i = 0; i < count && enemigosTotales > 0; i++) {
            boolean ok = spawn(bloques, anchoMapa, altoMapa / 2, jugadores);
            if (ok) {
                spawnsEnVentana = Math.min(maxSpawnsPorMinuto, spawnsEnVentana + 1);
            } else {
                break;
            }
        }
    }

    public int getEnemigosTotales() {
        return enemigosTotales;
    }

    public int getMaxEnemigosPantalla() {
        return maxEnemigosPantalla;
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }
}