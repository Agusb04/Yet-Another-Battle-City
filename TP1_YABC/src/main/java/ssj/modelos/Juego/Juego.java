package ssj.modelos.Juego;

import ssj.modelos.Nivel.Nivel;
import java.util.ArrayList;
import java.util.List;

public class Juego {
    private final List<Nivel> niveles;
    private int nivelActualIndex;
    private int cantidadJugadores;

    public Juego() {
        this.niveles = new ArrayList<>();
        this.nivelActualIndex = 0;
        this.cantidadJugadores = 1; // Por defecto 1 jugador
    }

    public void inicializarNiveles(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
        this.niveles.clear();
        this.nivelActualIndex = 0;


        this.niveles.add(new Nivel(1, 10, 4, 1.0));
        this.niveles.add(new Nivel(2, 14, 5, 1.2));
        this.niveles.add(new Nivel(3, 20, 6, 1.5));
    }

    public Nivel getNivelActual() {
        if (niveles.isEmpty() || nivelActualIndex >= niveles.size()) {
            return null;
        }
        return niveles.get(nivelActualIndex);
    }

    public void avanzarNivel() {
        if (nivelActualIndex < niveles.size() - 1) {
            nivelActualIndex++;
        }
    }

    public int obtenerNumeroNivelActual() {
        return nivelActualIndex + 1;
    }

    public boolean estaTerminado() {
        return nivelActualIndex >= niveles.size() - 1 && getNivelActual() != null && getNivelActual().estaCompletado();
    }

    public int getCantidadJugadores() {
        return cantidadJugadores;
    }
}