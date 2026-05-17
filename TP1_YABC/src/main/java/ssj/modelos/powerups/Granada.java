package ssj.modelos.powerups;

import ssj.modelos.tanques.Enemigo;
import ssj.modelos.tanques.Jugador;

import java.util.List;

public class Granada extends Powerup {

    private final List<Enemigo> enemigos;

    public Granada(double x, double y, List<Enemigo> enemigos) {
        super(x, y);
        this.enemigos = enemigos;
    }

    @Override
    public TipoPowerUp obtenerTipo() {
        return TipoPowerUp.GRANADA;
    }

    @Override
    protected void efecto(Jugador jugador) {
        for (Enemigo e : enemigos) {
            e.recibirImpacto(true);
        }
    }
}
