package ssj.modelos.bloques;

public class Acero extends Bloque {

    public Acero(int x, int y) {
        super(x,y,0);
    }

    @Override
    public boolean esDestructible() {
        return false;
    }

    @Override
    public boolean bloqueaDisparo() {
        return true;
    }

    @Override
    public boolean esTransitable() {
        return false;
    }

    @Override
    public TipoBloque obtenerTipo() {
        return TipoBloque.ACERO;
    }
}
