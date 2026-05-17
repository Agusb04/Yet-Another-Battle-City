package ssj.modelos.Nivel;

import ssj.modelos.bloques.*;
import ssj.modelos.tanques.Enemigo;
import ssj.modelos.tanques.SpawnEnemigos;
import ssj.modelos.tanques.Jugador;
import ssj.modelos.tanques.Tanque;

import java.util.ArrayList;
import java.util.List;

public class Nivel {
    private final int numero;
    private final SpawnEnemigos spawn;
    private List<Bloque> bloques;

    public Nivel(int numero, int totalEnemigos, int maxEnPantalla, double intervaloRespawn) {
        this.numero = numero;
        this.spawn = new SpawnEnemigos(maxEnPantalla, totalEnemigos, intervaloRespawn);
        this.bloques = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    /**
     * Actualiza la lógica del nivel.
     * Ahora recibe correctamente la lista de todos los jugadores activos en la partida.
     */
    public void actualizar(double deltaTiempo, List<Bloque> bloques, List<Tanque> tanques, int width, int height, List<Jugador> jugadores) {
        this.bloques = new ArrayList<>(bloques);

        // Se le pasa la lista completa al spawn para calcular colisiones correctamente
        spawn.actualizar(deltaTiempo, bloques, tanques, width, height, jugadores);
    }

    /**
     * El nivel se completa si no quedan enemigos en la pantalla AND tampoco quedan enemigos por spawnear.
     */
    public boolean estaCompletado() {
        boolean noHayEnemigosVivos = spawn.getEnemigos().stream().noneMatch(Enemigo::estaVivo);
        boolean noQuedanEnemigosEnReserva = spawn.getEnemigosTotales() <= 0;

        return noHayEnemigosVivos && noQuedanEnemigosEnReserva;
    }

    public List<Enemigo> getEnemigos() {
        return spawn.getEnemigos();
    }

    public SpawnEnemigos getSpawn() {
        return spawn;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }
}