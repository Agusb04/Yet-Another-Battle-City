package ssj.vista.View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ssj.modelos.tanques.Enemigo;
import ssj.vista.Utils.Grafico;
import ssj.modelos.LogicaMovimiento.Direccion;

import java.util.Objects;

public class EnemigoView {

    private final Enemigo enemigo;
    private final ImageView sprite;

    private final Image spriteQuieto;
    private final Image spriteMoviendo;

    private double xAnterior, yAnterior;
    private boolean toggleSprite;

    public EnemigoView(Enemigo enemigo) {
        this.enemigo = enemigo;

        switch (enemigo.getTipoEnemigo()) {
            case BASICO -> {
                spriteQuieto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_BASICO_1)));
                spriteMoviendo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_BASICO_2)));
            }
            case RAPIDO -> {
                spriteQuieto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_RAPIDO_1)));
                spriteMoviendo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_RAPIDO_2)));
            }
            case POTENTE -> {
                spriteQuieto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_POTENTE_1)));
                spriteMoviendo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_POTENTE_2)));
            }
            case BLINDADO -> {
                spriteQuieto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_BLINDADO_1)));
                spriteMoviendo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.TANQUE_ENEMIGO_BLINDADO_2)));
            }
            default -> throw new IllegalArgumentException("Tipo de enemigo desconocido");
        }

        sprite = new ImageView(spriteQuieto);
        sprite.setFitWidth(20);
        sprite.setFitHeight(20);

        xAnterior = enemigo.getX();
        yAnterior = enemigo.getY();
    }

    public void actualizar() {
        boolean seMovio = (enemigo.getX() != xAnterior) || (enemigo.getY() != yAnterior);

        if (seMovio) {
            toggleSprite = !toggleSprite;
            sprite.setImage(toggleSprite ? spriteMoviendo : spriteQuieto);
        }

        Direccion dir = enemigo.getDireccionActual();
        if (dir != null) {
            sprite.setRotate(switch (dir) {
                case ARRIBA -> 270;
                case ABAJO -> 90;
                case IZQUIERDA -> 180;
                case DERECHA -> 0;
            });
        }

        xAnterior = enemigo.getX();
        yAnterior = enemigo.getY();
    }

    public Enemigo getModelo() {
        return enemigo;
    }

    public ImageView getSprite() {
        return sprite;
    }
}