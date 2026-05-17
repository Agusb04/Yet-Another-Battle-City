package ssj.modelos.powerups;

import ssj.modelos.tanques.Jugador;

public class Casco extends Powerup {

    public Casco(double x, double y) {
        super(x,y);
    }

    @Override
    public TipoPowerUp obtenerTipo() {
        return TipoPowerUp.CASCO;
    }

    protected void efecto(Jugador jugador) {
        jugador.activarInvulnerabilidad(10_000);
    }
}
