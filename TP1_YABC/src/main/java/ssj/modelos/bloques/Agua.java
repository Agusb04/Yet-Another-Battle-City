package ssj.modelos.bloques;

public class Agua extends Bloque {

    public Agua(int x, int y) {
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
        return false;
    }

    @Override
    public  TipoBloque obtenerTipo() {
        return TipoBloque.AGUA;
    }
}
