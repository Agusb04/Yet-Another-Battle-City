package ssj.modelos.tanques;

public class EnemigoBasico extends Enemigo {
    public EnemigoBasico(int x, int y) {
        super(x, y, 1, 0.3, 2.2, TipoEnemigo.BASICO);
    }
}
