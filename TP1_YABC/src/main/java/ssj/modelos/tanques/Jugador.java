package ssj.modelos.tanques;

import ssj.modelos.LogicaMovimiento.Posicion;
import ssj.modelos.LogicaMovimiento.Direccion;
import ssj.modelos.disparo.Disparo;

public class Jugador extends Tanque {

    private int vidas;
    private final Posicion posicionInicial;
    private boolean muerto = false;
    private final int numeroJugador;
    private boolean congelado = false;
    private long finCongelamiento = 0;


    private static final int TAM_TANQUE = 20;
    private static final int TAM_BALA = 6;

    public Jugador(int numeroJugador, int x, int y, int vidasIniciales) {
        super(x, y, 1, 0.5);
        this.vidas = vidasIniciales;
        this.posicionInicial = new Posicion(x, y);
        this.puedeAgarrarPowerUps = true;
        this.powerUpActivo = null;
        this.numeroJugador = numeroJugador;
    }

    public int getNumeroJugador() {
        return numeroJugador;
    }

    @Override
    public void moverse() {
        if (estaCongelado()) return;
        posicion.setX(posicion.getX() + direccionActual.getDx() * velocidad);
        posicion.setY(posicion.getY() + direccionActual.getDy() * velocidad);
    }

    public void mover(Direccion dir) {
        if (!estaVivo()) return;
        if (estaCongelado()) return;
        this.direccionActual = dir;
        moverse();
    }

    public Disparo disparo() {
        if (disparoActivo == null || !disparoActivo.isActivo()) {
            int balaVel = 1;

            double centerX = posicion.getX() + TAM_TANQUE / 2.0 - TAM_BALA / 2.0;
            double centerY = posicion.getY() + TAM_TANQUE / 2.0 - TAM_BALA / 2.0;

            double spawnX = centerX;
            double spawnY = centerY;

            switch (direccionActual) {
                case ARRIBA -> spawnY = posicion.getY() - TAM_BALA;
                case ABAJO -> spawnY = posicion.getY() + TAM_TANQUE;
                case IZQUIERDA -> spawnX = posicion.getX() - TAM_BALA;
                case DERECHA -> spawnX = posicion.getX() + TAM_TANQUE;
            }

            disparoActivo = new Disparo(
                    spawnX,
                    spawnY,
                    direccionActual,
                    balaVel,
                    disparoPotenciado,
                    this
            );
            return disparoActivo;
        }
        return null;
    }

    @Override
    public boolean estaVivo() { return vida > 0 && !muerto; }
    @Override
    public TipoTanque getTipoTanque() { return TipoTanque.JUGADOR; }

    @Override
    public void activarPowerUp() {
        if (powerUpActivo != null && powerUpActivo.estaActivo()) {
            powerUpActivo.activar(this);
            powerUpActivo = null;
        }
    }

    @Override
    public void recibirImpacto(boolean esDisparoPotenciado) {
        if (!isInvulnerable() && estaVivo()) {
            vida = 0;
            vidas--;
            if (vidas > 0) respawnear();
            else muerto = true;
        }
    }

    private void respawnear() {
        vida = 1;
        posicion.setX(posicionInicial.getX());
        posicion.setY(posicionInicial.getY());
        disparoActivo = null;
        setInvulnerable(false);
        disparoPotenciado = false;
        congelado = false;
        powerUpActivo = null;
        invulnerabilidadRestante = 0;
    }

    public void freeze(long duracionMs) {
        congelado = true;
        finCongelamiento = System.currentTimeMillis() + duracionMs;
    }

    public boolean estaCongelado() {
        if (congelado && System.currentTimeMillis() > finCongelamiento) {
            congelado = false;
        }
        return congelado;
    }

    private double invulnerabilidadRestante = 0;

    public void activarInvulnerabilidad(long duracionMs) {
        invulnerabilidadRestante = duracionMs / 1000.0;
        super.setInvulnerable(true);
    }

    public void actualizarInvulnerabilidad(double deltaTiempo) {
        if (invulnerabilidadRestante > 0) {
            invulnerabilidadRestante -= deltaTiempo;
            if (invulnerabilidadRestante <= 0) {
                super.setInvulnerable(false);
                invulnerabilidadRestante = 0;
            }
        }
    }

    public int getVidas() { return vidas; }
}