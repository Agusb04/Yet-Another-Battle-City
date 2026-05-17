package ssj.vista.View;

import javafx.scene.image.Image;
import ssj.modelos.bloques.Bloque;

public class BloqueView {
    private final Bloque modelo;
    private final Image sprite;
    private boolean visible = true;

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

    public boolean isVisible() {
        return visible && !modelo.estaDestruido();
    }

    public void actualizar() {
        if (modelo.estaDestruido()) {
            visible = false;
        }
    }
}
