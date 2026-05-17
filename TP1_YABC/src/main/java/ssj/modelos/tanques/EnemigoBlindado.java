package ssj.modelos.tanques;

public class EnemigoBlindado extends Enemigo {
    public EnemigoBlindado(int x, int y) {
        super(x, y, 3, 0.20, 3.0, TipoEnemigo.BLINDADO);
    }
}
