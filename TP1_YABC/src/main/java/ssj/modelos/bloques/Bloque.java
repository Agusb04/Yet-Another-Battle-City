package ssj.modelos.bloques;

import ssj.modelos.LogicaMovimiento.Posicion;

public abstract class Bloque {

    protected int vida;
    protected Posicion posicion;

    public Bloque(int x, int y, int vida) {
        this.posicion = new Posicion(x,y);
        this.vida = vida;
    }

    public void recibirImpacto() {
        if (esDestructible() && vida > 0) {
            vida--;
        }
    }

    public boolean estaDestruido() {
        return esDestructible() && vida <= 0;
    }

    public abstract TipoBloque obtenerTipo();

    public abstract boolean esDestructible();

    public abstract boolean bloqueaDisparo();

    public abstract boolean esTransitable();

    public int getVida() {
        return vida;
    }

    public double getX() {
        return posicion.getX();
    }

    public double getY() {
        return posicion.getY();
    }
}
