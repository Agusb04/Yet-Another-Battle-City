package ssj.modelos.tanques;

import ssj.modelos.LogicaMovimiento.Direccion;
import ssj.modelos.powerups.Powerup;
import ssj.modelos.disparo.Disparo;
import ssj.modelos.LogicaMovimiento.Posicion;

public abstract class Tanque {

    protected Posicion posicion;
    protected int vida;
    protected double velocidad;
    protected Disparo disparoActivo;
    protected boolean puedeAgarrarPowerUps;
    protected Powerup powerUpActivo;
    protected Direccion direccionActual;
    private boolean invulnerable;
    protected boolean disparoPotenciado;

    public Tanque(double x, double y, int vida, double velocidad) {
        this.posicion = new Posicion(x, y);
        this.vida = vida;
        this.velocidad = velocidad;
        this.disparoActivo = null;
        this.puedeAgarrarPowerUps = false;
        this.powerUpActivo = null;
        this.direccionActual = Direccion.ARRIBA;
        this.invulnerable = false;
        this.disparoPotenciado = false;
    }

    public abstract void moverse();

    public abstract TipoTanque getTipoTanque();

    public abstract void activarPowerUp();

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirImpacto(boolean esDisparoPotenciado) {
        if (!invulnerable && vida > 0) {
            if (esDisparoPotenciado) {
                vida = 0;
            } else {
                vida--;
            }
        }
    }

    public boolean puedeAgarrarPowerUps() {
        return puedeAgarrarPowerUps;
    }

    public Disparo disparar(int velocidadDisparo) {
        if (disparoActivo == null || !disparoActivo.isActivo()) {
            disparoActivo = new Disparo(
                    posicion.getX(),
                    posicion.getY(),
                    direccionActual,
                    velocidadDisparo,
                    disparoPotenciado,
                    this
            );
        }
        return disparoActivo;
    }

    public boolean isPowerUpActivo() {
        return powerUpActivo != null;
    }

    public double getX() {
        return posicion.getX();
    }

    public double getY() {
        return posicion.getY();
    }

    public int getVida() {
        return vida;
    }

    public Direccion getDireccionActual() {
        return direccionActual;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public void setX(double x) {
        this.posicion.setX(x);
    }

    public void setY(double y) {
        this.posicion.setY(y);
    }

    public void setPosicion(double x, double y) {
        this.posicion.setX(x);
        this.posicion.setY(y);
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setDisparoPotenciado(boolean disparoPotenciado) {
        this.disparoPotenciado = disparoPotenciado;
    }

}
