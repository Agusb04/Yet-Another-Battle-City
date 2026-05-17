package ssj.modelos.bloques;

public class Ladrillo extends Bloque {

    public Ladrillo(int x, int y) {
        super(x,y,3);
    }
    @Override
    public boolean esDestructible() {
        return true;
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
        return TipoBloque.LADRILLO;
    }
}
