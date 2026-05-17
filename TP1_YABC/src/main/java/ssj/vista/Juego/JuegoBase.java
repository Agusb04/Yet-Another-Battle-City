package ssj.vista.Juego;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ssj.modelos.bloques.Bloque;
import ssj.modelos.disparo.Disparo;
import ssj.modelos.powerups.Powerup;
import ssj.modelos.tanques.Jugador;
import ssj.modelos.Nivel.Nivel;
import ssj.vista.Menu.VistaInicio;
import ssj.sonidos.ReproductorSonidos;
import ssj.vista.View.BloqueView;
import ssj.vista.View.EnemigoView;
import ssj.vista.View.PowerupView;

import java.util.ArrayList;
import java.util.List;

public abstract class JuegoBase {

    protected Pane root;
    protected Canvas canvas;
    protected GraphicsContext gc;
    protected Stage stage;
    protected VistaInicio inicio;

    protected final int WIDTH = 820;
    protected final int HEIGHT = 660;

    protected Jugador jugador;
    protected List<Jugador> jugadores = new ArrayList<>();
    protected List<Disparo> balas = new ArrayList<>();
    protected List<BloqueView> bloquesView = new ArrayList<>();
    protected List<EnemigoView> enemigosView = new ArrayList<>();

    protected List<Powerup> powerups = new ArrayList<>();
    protected List<PowerupView> powerupsView = new ArrayList<>();

    protected boolean gameOver = false;
    protected int nivelActual = 1;
    protected final int MAX_NIVELES = 3;

    protected Nivel nivel;
    protected Image imgDisparo;

    protected final ReproductorSonidos playSonido = new ReproductorSonidos();

    public List<Bloque> obtenerBloquesModelo() {
        List<Bloque> bloques = new ArrayList<>();
        for (BloqueView bv : bloquesView) bloques.add(bv.getModelo());
        return bloques;
    }

    public boolean isGameOver() { return gameOver; }
    public Nivel getNivel() { return nivel; }
    public List<Jugador> getJugadoresActivos() { return jugadores; }
    public List<Disparo> getBalasActivas() { return balas; }
    public List<BloqueView> getBloquesView() { return bloquesView; }
    public List<EnemigoView> getEnemigosView() { return enemigosView; }
    public List<Powerup> getPowerupsModelo() { return powerups; }
    public List<PowerupView> getPowerupsViewActivos() { return powerupsView; }

    public void pausarJuego() {
        this.gameOver = true;
        this.playSonido.pararMusica();
        if (getControlador() != null) {
            getControlador().detener();
        }
    }

    // Métodos abstractos de comunicación y ciclo de vida
    public abstract ssj.controlador.ControladorJuego getControlador();
    protected abstract void renderizar();
    protected abstract void mostrarDerrota();
    protected abstract void mostrarVictoriaFinal();
    protected abstract void mostrarVictoriaNivel();
}