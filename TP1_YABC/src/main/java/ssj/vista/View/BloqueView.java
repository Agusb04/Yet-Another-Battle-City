package ssj.vista.View;

import javafx.scene.image.Image;
import ssj.modelos.bloques.Bloque;

public class BloqueView {
    private final Bloque modelo;
    private final Image sprite;

    public BloqueView(Bloque modelo, Image sprite) {
        this.modelo = modelo;
        this.sprite = sprite;
    }

    public Bloque getModelo() {
        return modelo;
    }

    public Image getSpriteImage() {
        return sprite;
    }
}
