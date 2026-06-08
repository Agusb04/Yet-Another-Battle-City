package ssj.vista.View;

import javafx.scene.image.Image;
import ssj.modelos.powerups.Powerup;

public class PowerupView {
    private Powerup modelo;
    private Image sprite;

    public PowerupView(Powerup modelo, Image sprite) {
        this.modelo = modelo;
        this.sprite = sprite;
    }

    public Image getSprite() {
        return sprite;
    }

    public Powerup getModelo() {
        return modelo;
    }

    public double getX() {
        return modelo.getX();
    }

    public double getY() {
        return modelo.getY();
    }
}
