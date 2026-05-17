package ssj.modelos.powerups;

import ssj.modelos.tanques.Jugador;

public class Estrella extends Powerup {

    public Estrella(double x, double y){
        super(x,y);
    }
    @Override
    public TipoPowerUp obtenerTipo() {
        return TipoPowerUp.ESTRELLA;
    }

    @Override
    protected void efecto(Jugador jugador) {
        jugador.setDisparoPotenciado(true);
    }
}
