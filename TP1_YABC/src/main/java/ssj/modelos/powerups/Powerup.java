package ssj.modelos.powerups;

import ssj.modelos.tanques.Jugador;

public abstract class Powerup {
    protected boolean activo = true;
    protected double x, y;

    public Powerup(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public abstract TipoPowerUp obtenerTipo();
    protected abstract void efecto(Jugador jugador);


    public void activar(Jugador jugador) {
        if (activo) {
            efecto(jugador);
            activo = false;
        }
    }


    public boolean estaActivo() { return activo; }
}
