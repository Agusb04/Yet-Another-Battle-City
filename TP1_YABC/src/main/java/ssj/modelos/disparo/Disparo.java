package ssj.modelos.disparo;

import ssj.modelos.LogicaMovimiento.Posicion;
import ssj.modelos.LogicaMovimiento.Direccion;
import ssj.modelos.tanques.Tanque;

public class Disparo {

    private Posicion posicion;
    private Direccion direccion;
    private double velocidad;
    private boolean activo;
    private boolean potenciado;
    private Tanque tanqueOrigen;

    public Disparo(double x, double y, Direccion direccion, int velocidad, boolean potenciado, Tanque tanqueOrigen) {
        this.posicion = new Posicion(x, y);
        this.direccion = direccion;
        this.velocidad = velocidad;
        this.activo = true;
        this.potenciado = potenciado;
        this.tanqueOrigen = tanqueOrigen;
    }

    public void mover() {
        if (!activo) return;
        posicion.setX(posicion.getX() + direccion.getDx() * velocidad);
        posicion.setY(posicion.getY() + direccion.getDy() * velocidad);
    }

    public double getX() {
        return posicion.getX();
    }

    public double getY() {
        return posicion.getY();
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean esPotenciado() {
        return potenciado;
    }

    public Tanque getTanqueOrigen() { // <<< NUEVO
        return tanqueOrigen;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void desactivar() {
        this.activo = false;
    }
}
