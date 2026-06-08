package ssj.modelos.bloques;

public class TanqueDestruido extends Bloque {

    public TanqueDestruido(int x, int y) {
        super(x, y, 1);
    }

    @Override
    public TipoBloque obtenerTipo() {
        return TipoBloque.TANQUE_DESTRUIDO;
    }

    @Override
    public boolean esDestructible() {
        return false;
    }

    @Override
    public boolean bloqueaDisparo() {
        return false;
    }

    @Override
    public boolean esTransitable() {
        return true;
    }
}
