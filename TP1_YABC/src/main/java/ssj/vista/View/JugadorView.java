package ssj.vista.View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import ssj.modelos.tanques.Jugador;
import ssj.modelos.LogicaMovimiento.Direccion;
import ssj.vista.Utils.Grafico;

import java.util.Objects;

public class JugadorView extends StackPane {

    private final Jugador jugador;
    private final ImageView sprite;
    private final ImageView overlayInvulnerable;

    private final Image spriteQuieto;
    private final Image spriteMoviendo;

    private double xAnterior, yAnterior;
    private boolean toggleSprite;
    private int frameCounter = 0;

    public JugadorView(Jugador jugador) {
        this.jugador = jugador;

        if (jugador.getNumeroJugador() == 1) {
            spriteQuieto   = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.JUGADOR_1_SPRITE_1)));

            spriteMoviendo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.JUGADOR_1_SPRITE_2)));
        } else {
            spriteQuieto   = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.JUGADOR_2_SPRITE_1)));

            spriteMoviendo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.JUGADOR_2_SPRITE_2)));
        }

        sprite = new ImageView(spriteQuieto);
        sprite.setFitWidth(20);
        sprite.setFitHeight(20);

        overlayInvulnerable = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.ESCUDO_INVULNERABILIDAD))));
        overlayInvulnerable.setFitWidth(20);
        overlayInvulnerable.setFitHeight(20);
        overlayInvulnerable.setOpacity(0.6);
        overlayInvulnerable.setVisible(false);

        getChildren().addAll(sprite, overlayInvulnerable);

        setLayoutX(jugador.getX());
        setLayoutY(jugador.getY());

        xAnterior = jugador.getX();
        yAnterior = jugador.getY();
    }

    public void actualizar() {
        boolean seMovio = (jugador.getX() != xAnterior) || (jugador.getY() != yAnterior);

        if (seMovio) {
            frameCounter++;
            if (frameCounter % 6 == 0) {
                toggleSprite = !toggleSprite;
                sprite.setImage(toggleSprite ? spriteMoviendo : spriteQuieto);
            }
        }

        setLayoutX(jugador.getX());
        setLayoutY(jugador.getY());

        Direccion dir = jugador.getDireccionActual();
        if (dir != null) {
            sprite.setRotate(dir.getAngulo());
        }

        overlayInvulnerable.setVisible(jugador.estaInvulnerable());

        xAnterior = jugador.getX();
        yAnterior = jugador.getY();
    }

    public Jugador getModelo() {
        return jugador;
    }
}