package ssj.modelos.tanques;

import ssj.modelos.LogicaMovimiento.Direccion;
import ssj.modelos.LogicaMovimiento.Rect;
import ssj.modelos.disparo.Disparo;
import ssj.modelos.bloques.Bloque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemigo extends Tanque {

    private static final int TAM_TANQUE = 20;
    private static final int ALTURA_BARRA = 50;
    private static final int TAM_BALA = 6;

    private double tiempoRestante;
    private double tiempoSinMoverse;
    private final Random rand = new Random();

    private double tiempoDisparo;
    private final double cadenciaDisparo;

    private double ultimoX;
    private double ultimoY;

    private TipoEnemigo tipo;

    public Enemigo(int x, int y, int vida, double velocidad, double cadenciaDisparo, TipoEnemigo tipo) {
        super(x, y, vida, velocidad);
        this.cadenciaDisparo = cadenciaDisparo;
        // FIX: Empiezan con un poquito de carga en el disparo para que no tarden tanto en tirar la primera bala al spawnear
        this.tiempoDisparo = rand.nextDouble() * (cadenciaDisparo * 0.5);
        this.puedeAgarrarPowerUps = false;
        reiniciarTiempo();
        tiempoSinMoverse = 0;
        this.tipo = tipo;

        this.ultimoX = x;
        this.ultimoY = y;
    }

    private void reiniciarTiempo() {
        if (this.tipo == TipoEnemigo.RAPIDO) {
            tiempoRestante = 0.5 + rand.nextDouble();
        } else {
            tiempoRestante = 1 + rand.nextDouble() * 3;
        }
    }

    public Disparo actualizar(double deltaTiempo, List<Bloque> bloques, List<Tanque> tanques, int anchoMapa, int altoMapa) {
        tiempoRestante -= deltaTiempo;
        tiempoDisparo += deltaTiempo;

        moverseConColision(bloques, tanques, anchoMapa, altoMapa);

        if (Math.abs(posicion.getX() - ultimoX) < 0.01 && Math.abs(posicion.getY() - ultimoY) < 0.01) {
            tiempoSinMoverse += deltaTiempo;
        } else {
            tiempoSinMoverse = 0;
        }

        ultimoX = posicion.getX();
        ultimoY = posicion.getY();

        if (tiempoRestante <= 0 || tiempoSinMoverse >= 0.4) {
            List<Direccion> candidatas = new ArrayList<>();

            for (Direccion d : Direccion.values()) {
                if (d == direccionActual) continue;

                double newX = posicion.getX() + d.getDx() * velocidad;
                double newY = posicion.getY() + d.getDy() * velocidad;

                Rect test = new Rect(newX, newY, TAM_TANQUE, TAM_TANQUE);
                boolean bloqueado = false;

                double maxY = altoMapa - TAM_TANQUE - ALTURA_BARRA;
                if (newX < 0 || newY < 0 || newX + TAM_TANQUE > anchoMapa || newY > maxY) {
                    bloqueado = true;
                } else {
                    for (Bloque b : bloques) {
                        if (!b.esTransitable()) {
                            Rect rB = new Rect(b.getX(), b.getY(), TAM_TANQUE, TAM_TANQUE);
                            if (test.intersects(rB)) {
                                bloqueado = true;
                                break;
                            }
                        }
                    }
                }

                if (!bloqueado) {
                    candidatas.add(d);
                }
            }

            if (!candidatas.isEmpty()) {
                direccionActual = candidatas.get(rand.nextInt(candidatas.size()));
            } else {
                direccionActual = Direccion.values()[rand.nextInt(Direccion.values().length)];
            }

            reiniciarTiempo();
            tiempoSinMoverse = 0;

        }

        if (tiempoDisparo >= cadenciaDisparo) {
            Disparo d = disparar(1);
            tiempoDisparo = 0;
            return d;
        }

        return null;
    }

    public void moverseConColision(List<Bloque> bloques, List<Tanque> tanques, int anchoMapa, int altoMapa) {
        double oldX = posicion.getX();
        double oldY = posicion.getY();

        double newX = oldX + direccionActual.getDx() * velocidad;
        double newY = oldY + direccionActual.getDy() * velocidad;

        double maxY = altoMapa - TAM_TANQUE - ALTURA_BARRA;

        if (newX < 0 || newY < 0 || newX + TAM_TANQUE > anchoMapa || newY > maxY) return;

        Rect nuevoBounds = new Rect(newX, newY, TAM_TANQUE, TAM_TANQUE);

        for (Bloque b : bloques) {
            if (!b.esTransitable()) {
                Rect rB = new Rect(b.getX(), b.getY(), TAM_TANQUE, TAM_TANQUE);
                if (nuevoBounds.intersects(rB)) return;
            }
        }

        for (Tanque t : tanques) {
            if (t == this) continue;
            Rect rT = new Rect(t.getX(), t.getY(), TAM_TANQUE, TAM_TANQUE);
            if (nuevoBounds.intersects(rT)) return;
        }

        posicion.setX(newX);
        posicion.setY(newY);
    }

    public Disparo disparar(int velocidadBala) {
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

        return new Disparo(spawnX, spawnY, direccionActual, velocidadBala, false, this);
    }

    public TipoEnemigo getTipoEnemigo() {
        return tipo;
    }

    @Override
    public TipoTanque getTipoTanque() {
        return TipoTanque.ENEMIGO;
    }

    @Override
    public void activarPowerUp() { }

    @Override
    public void moverse() {
        posicion.setX(posicion.getX() + direccionActual.getDx() * velocidad);
        posicion.setY(posicion.getY() + direccionActual.getDy() * velocidad);
    }
}