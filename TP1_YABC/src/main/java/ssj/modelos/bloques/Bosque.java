package ssj.modelos.bloques;

public class Bosque extends Bloque {

    public Bosque(int x, int y) {
        super(x,y,0);
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

    @Override
    public TipoBloque obtenerTipo() {
        return TipoBloque.BOSQUE;
    }
}
