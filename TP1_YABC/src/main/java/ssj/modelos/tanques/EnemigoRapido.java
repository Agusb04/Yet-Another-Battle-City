package ssj.modelos.tanques;

public class EnemigoRapido extends Enemigo {
    public EnemigoRapido(int x, int y) {
        super(x, y, 1, 0.35, 2.0, TipoEnemigo.RAPIDO);
    }
}
