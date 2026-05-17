package ssj.modelos.bloques;

public class Base extends Bloque {


    public Base(int x, int y) {
        super(x,y,1);
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
        return TipoBloque.BASE;
    }

}
