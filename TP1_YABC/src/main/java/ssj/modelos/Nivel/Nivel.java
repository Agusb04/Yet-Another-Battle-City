package ssj.modelos.Nivel;

import ssj.modelos.bloques.*;
import ssj.modelos.tanques.Enemigo;
import ssj.modelos.tanques.SpawnEnemigos;
import ssj.modelos.tanques.Jugador;

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

    public void actualizar(double deltaTiempo, List<Bloque> bloques, int width, int height, List<Jugador> jugadores) {
        this.bloques = new ArrayList<>(bloques);

        spawn.actualizar(deltaTiempo, bloques, width, height, jugadores);
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