package ssj.vista.Juego;

import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ssj.modelos.Juego.Juego;
import ssj.modelos.bloques.Bloque;
import ssj.modelos.bloques.TipoBloque;
import ssj.modelos.tanques.Enemigo;
import ssj.modelos.tanques.Jugador;
import ssj.modelos.disparo.Disparo;
import ssj.modelos.Nivel.Nivel;
import ssj.modelos.powerups.Powerup;
import ssj.controlador.ControladorInput;
import ssj.controlador.ControladorJuego;
import ssj.vista.Menu.VistaInicio;
import ssj.vista.Menu.VistaMenu;
import ssj.vista.View.BloqueView;
import ssj.vista.View.EnemigoView;
import ssj.vista.View.PowerupView;
import ssj.vista.Utils.*;

import java.util.*;

public class JuegoVista extends JuegoBase {

    private final Scene scene;
    private final VistaMenu menu;
    private final int cantidadJugadores;
    private final Juego modeloJuego;

    private final ControladorJuego controlador;

    private final Map<Integer, Image[]> spritesJugadores = new HashMap<>();
    private final Map<Integer, Boolean> toggleSprites = new HashMap<>();
    private final Map<Integer, Integer> frameCounters = new HashMap<>();

    private final Image imgEscudo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.ESCUDO_INVULNERABILIDAD)));

    public JuegoVista(Stage stage, VistaInicio inicio, VistaMenu menu, int cantidadJugadores) {
        this.stage = stage;
        this.inicio = inicio;
        this.menu = menu;
        this.cantidadJugadores = cantidadJugadores;

        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        scene = new Scene(root, WIDTH, HEIGHT);

        modeloJuego = new Juego();
        modeloJuego.inicializarNiveles(cantidadJugadores);

        ControladorInput input = new ControladorInput(scene, cantidadJugadores);
        this.controlador = new ControladorJuego(this, modeloJuego, input);

        // Ahora sí cargamos el nivel inicial haciendo uso de la configuración
        cargarNivel(modeloJuego.obtenerNumeroNivelActual());
        this.controlador.comenzar();
    }

    @Override
    public ControladorJuego getControlador() {
        return this.controlador;
    }

    public int getCantidadJugadores() { return cantidadJugadores; }
    public Scene getScene() { return scene; }

    public void avanzarSiguienteNivel() {
        modeloJuego.avanzarNivel();
        if (!modeloJuego.haySiguienteNivel()) {
            mostrarVictoriaFinal();
        } else {
            mostrarVictoriaNivel();
        }
    }

    public void agregarPowerupView(Powerup p) {
        Image sprite = switch (p.obtenerTipo()) {
            case CASCO -> new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.POWERUP_CASCO)));
            case ESTRELLA -> new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.POWERUP_ESTRELLA)));
            case GRANADA -> new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.POWERUP_GRANADA)));
        };
        powerups.add(p);
        powerupsView.add(new PowerupView(p, sprite));
    }

    private void cargarNivel(int numero) {
        root.getChildren().clear();
        root.getChildren().add(canvas);
        BarraInferior.agregarBarra(root, stage, inicio, menu, this);

        String pathNivel = Niveles.getPath(numero, cantidadJugadores == 2);
        Renderizador.LevelInfo info = Renderizador.renderizar(pathNivel);
        this.bloquesView = new ArrayList<>(info.bloquesView);

        jugadores.clear();
        spritesJugadores.clear();
        toggleSprites.clear();
        frameCounters.clear();

        for (int i = 0; i < cantidadJugadores; i++) {
            double startX = 100 + (i * 100);
            double startY = 500;
            if (!info.playerPositions.isEmpty() && info.playerPositions.size() > i) {
                Point2D p = info.playerPositions.get(i);
                startX = p.getX();
                startY = p.getY();
            }

            Jugador j = new Jugador(i + 1, (int) startX, (int) startY, 3);
            jugadores.add(j);

            String sprite1Path = (i == 0) ? Grafico.JUGADOR_1_SPRITE_1 : Grafico.JUGADOR_2_SPRITE_1;
            String sprite2Path = (i == 0) ? Grafico.JUGADOR_1_SPRITE_2 : Grafico.JUGADOR_2_SPRITE_2;

            spritesJugadores.put(j.getNumeroJugador(), new Image[]{
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(sprite1Path))),
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(sprite2Path)))
            });
            toggleSprites.put(j.getNumeroJugador(), false);
            frameCounters.put(j.getNumeroJugador(), 0);
        }

        if (!jugadores.isEmpty()) {
            this.jugador = jugadores.getFirst();
        }
        imgDisparo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.DISPARO)));

        balas.clear();
        enemigosView.clear();
        powerups.clear();
        powerupsView.clear();

        nivel = modeloJuego.getNivelActual();
        int spawnInicial = Math.min(nivel.getSpawn().getMaxEnemigosPantalla(), nivel.getSpawn().getEnemigosTotales());
        nivel.getSpawn().spawnInitial(spawnInicial, obtenerBloquesModelo(), WIDTH, HEIGHT, jugadores);

        for (Enemigo e : nivel.getEnemigos()) {
            enemigosView.add(new EnemigoView(e));
        }
    }

    @Override
    public void renderizar() {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        for (BloqueView bv : bloquesView) {
            Bloque b = bv.getModelo();
            if (b.obtenerTipo() != TipoBloque.BOSQUE) {
                gc.drawImage(bv.getSpriteImage(), b.getX(), b.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
            }
        }

        for (PowerupView pv : powerupsView) {
            gc.drawImage(pv.getSprite(), pv.getX(), pv.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
        }

        for (Jugador j : jugadores) {
            if (!j.estaVivo()) continue;

            int count = frameCounters.get(j.getNumeroJugador()) + 1;
            frameCounters.put(j.getNumeroJugador(), count);
            if (count % 6 == 0) {
                toggleSprites.put(j.getNumeroJugador(), !toggleSprites.get(j.getNumeroJugador()));
            }

            Image[] sprites = spritesJugadores.get(j.getNumeroJugador());
            Image spriteActual = toggleSprites.get(j.getNumeroJugador()) ? sprites[1] : sprites[0];

            gc.save();
            gc.translate(j.getX() + Renderizador.TILE_SIZE / 2.0, j.getY() + Renderizador.TILE_SIZE / 2.0);

            if (j.getDireccionActual() != null) {
                switch (j.getDireccionActual()) {
                    case ARRIBA -> gc.rotate(0);
                    case DERECHA -> gc.rotate(90);
                    case ABAJO -> gc.rotate(180);
                    case IZQUIERDA -> gc.rotate(270);
                }
            }

            gc.drawImage(spriteActual, -Renderizador.TILE_SIZE / 2.0, -Renderizador.TILE_SIZE / 2.0, Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);

            if (j.isInvulnerable()) {
                double cascoSize = Renderizador.TILE_SIZE * 1.2;
                gc.drawImage(imgEscudo, -cascoSize / 2.0, -cascoSize / 2.0, cascoSize, cascoSize);
            }
            gc.restore();
        }

        for (EnemigoView ev : enemigosView) {
            Enemigo e = ev.getModelo();
            gc.save();
            gc.translate(e.getX() + Renderizador.TILE_SIZE / 2.0, e.getY() + Renderizador.TILE_SIZE / 2.0);
            if (e.getDireccionActual() != null) {
                switch (e.getDireccionActual()) {
                    case ARRIBA -> gc.rotate(0);
                    case DERECHA -> gc.rotate(90);
                    case ABAJO -> gc.rotate(180);
                    case IZQUIERDA -> gc.rotate(270);
                }
            }
            gc.drawImage(ev.getSprite().getImage(), -Renderizador.TILE_SIZE / 2.0, -Renderizador.TILE_SIZE / 2.0, Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
            gc.restore();
        }

        for (Disparo d : balas) {
            gc.drawImage(imgDisparo, d.getX(), d.getY(), 6, 6);
        }

        for (BloqueView bv : bloquesView) {
            if (bv.getModelo().obtenerTipo() == TipoBloque.BOSQUE) {
                gc.drawImage(bv.getSpriteImage(), bv.getModelo().getX(), bv.getModelo().getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
            }
        }
    }

    @Override
    public void mostrarDerrota() {
        ejecutarPantallaFinal("¡GAME OVER!", javafx.scene.paint.Color.RED);
    }

    @Override
    public void mostrarVictoriaFinal() {
        ejecutarPantallaFinal("¡GANASTE EL JUEGO!", javafx.scene.paint.Color.LIMEGREEN);
    }

    @Override
    public void mostrarVictoriaNivel() {
        gameOver = true;
        playSonido.pararMusica();

        javafx.scene.layout.StackPane overlay = new javafx.scene.layout.StackPane();
        overlay.setPrefSize(WIDTH, HEIGHT);
        overlay.setStyle("-fx-background-color: black;");

        javafx.scene.text.Text texto = new javafx.scene.text.Text("¡NIVEL SUPERADO!");
        texto.setFill(javafx.scene.paint.Color.YELLOW);
        texto.setStyle("-fx-font-size: 60px; -fx-font-weight: bold;");

        overlay.getChildren().add(texto);
        root.getChildren().add(overlay);

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            root.getChildren().remove(overlay);
            gameOver = false;
            cargarNivel(modeloJuego.obtenerNumeroNivelActual());
            controlador.comenzar();
        });
        delay.play();
    }

    private void ejecutarPantallaFinal(String mensaje, javafx.scene.paint.Color color) {
    gameOver = true;
    playSonido.pararMusica();

    javafx.scene.layout.StackPane overlay = new javafx.scene.layout.StackPane();
    overlay.setPrefSize(WIDTH, HEIGHT);
    overlay.setStyle("-fx-background-color: black;");

    javafx.scene.text.Text texto = new javafx.scene.text.Text(mensaje);
    texto.setFill(color);
    texto.setStyle("-fx-font-size: 72px; -fx-font-weight: bold;");

    overlay.getChildren().add(texto);
    root.getChildren().add(overlay);

    PauseTransition delay = new PauseTransition(Duration.seconds(3));

    delay.setOnFinished(event -> {
        stage.setScene(menu.getScene());
    });

    delay.play();
}
}