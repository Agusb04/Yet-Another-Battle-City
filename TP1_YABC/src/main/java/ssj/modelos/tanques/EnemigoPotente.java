package ssj.modelos.tanques;

public class EnemigoPotente extends Enemigo {
    public EnemigoPotente(int x, int y) {
        super(x, y, 1, 0.25, 1.8, TipoEnemigo.POTENTE);
    }
}
