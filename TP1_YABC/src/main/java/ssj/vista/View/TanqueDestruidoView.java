package ssj.vista.View;

import javafx.scene.image.Image;
import ssj.modelos.bloques.TanqueDestruido;
import ssj.vista.Utils.Grafico;
import java.util.Objects;

public class TanqueDestruidoView extends BloqueView {

    public TanqueDestruidoView(TanqueDestruido modelo) {

        super(modelo, new Image(Objects.requireNonNull(TanqueDestruidoView.class.getResourceAsStream(Grafico.TANQUE_DESTRUIDO))));
    }

    @Override
    public TanqueDestruido getModelo() {
        return (TanqueDestruido) super.getModelo();
    }
}