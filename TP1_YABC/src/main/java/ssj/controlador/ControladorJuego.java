package ssj.controlador;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import ssj.modelos.Juego.Juego;
import ssj.modelos.Nivel.Nivel;
import ssj.modelos.LogicaMovimiento.Direccion;
import ssj.modelos.bloques.Bloque;
import ssj.modelos.bloques.TanqueDestruido;
import ssj.modelos.bloques.TipoBloque;
import ssj.modelos.disparo.Disparo;
import ssj.modelos.powerups.Casco;
import ssj.modelos.powerups.Estrella;
import ssj.modelos.powerups.Granada;
import ssj.modelos.powerups.Powerup;
import ssj.modelos.powerups.TipoPowerUp;
import ssj.modelos.tanques.Enemigo;
import ssj.modelos.tanques.Jugador;
import ssj.modelos.tanques.Tanque;
import ssj.modelos.tanques.TipoEnemigo;
import ssj.modelos.tanques.TipoTanque;
import ssj.vista.Juego.JuegoBase;
import ssj.vista.Utils.Renderizador;
import ssj.vista.View.BloqueView;
import ssj.vista.View.EnemigoView;
import ssj.vista.View.TanqueDestruidoView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControladorJuego {

    private static final int TAM_BALA = 6;

    private final JuegoBase vista;
    private final Juego modeloJuego;
    private final ControladorInput input;
    private final ControladorSonidos sonidos;

    private AnimationTimer gameLoop;
    private long lastTime;
    private long lastShot1 = 0;
    private long lastShot2 = 0;

    private final int WIDTH = 820;
    private final int HEIGHT = 660;
    private final double BARRA_INF = 50;

    public ControladorJuego(JuegoBase vista, Juego modeloJuego, ControladorInput input) {
        this.vista = vista;
        this.modeloJuego = modeloJuego; // Corregido: Parámetro asignado correctamente
        this.input = input;
        this.sonidos = new ControladorSonidos();

        inicializarBucle();
    }

    private void inicializarBucle() {
        this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (vista.isGameOver()) return;

                double deltaTiempo = (now - lastTime) / 1_000_000_000.0;
                if (deltaTiempo > 0.1) deltaTiempo = 0.016;
                lastTime = now;

                actualizarMundo(deltaTiempo, now);
            }
        };
    }

    public void comenzar() {
        this.lastTime = System.nanoTime();
        this.sonidos.iniciarMusicaJuego();
        this.gameLoop.start();
    }

    public void detener() {
        this.gameLoop.stop();
        this.sonidos.detenerMusica();
    }

    public Juego getModeloJuego() {
        return this.modeloJuego;
    }

    private void actualizarMundo(double deltaTiempo, long now) {
        Nivel nivelActual = vista.getNivel();
        if (nivelActual == null) return;

        for (Jugador j : vista.getJugadoresActivos()) {
            if (j.isInvulnerable()) {
                j.actualizarInvulnerabilidad(deltaTiempo);
            }
        }

        procesarInputJugadores(now);

        nivelActual.actualizar(deltaTiempo, vista.obtenerBloquesModelo(), WIDTH, HEIGHT, vista.getJugadoresActivos());

        List<Tanque> tanques = new ArrayList<>();
        tanques.addAll(vista.getJugadoresActivos());
        tanques.addAll(nivelActual.getEnemigos());

        actualizarEnemigosLogica(nivelActual, deltaTiempo, tanques);
        actualizarBalasLogica();
        actualizarPowerupsLogica();
        procesarEnemigosMuertos(nivelActual);

        for (Enemigo e : nivelActual.getEnemigos()) {
            boolean yaExiste = vista.getEnemigosView().stream().anyMatch(v -> v.getModelo() == e);
            if (!yaExiste) vista.getEnemigosView().add(new EnemigoView(e));
        }

        Iterator<EnemigoView> itEv = vista.getEnemigosView().iterator();
        while (itEv.hasNext()) {
            EnemigoView ev = itEv.next();
            Enemigo mod = ev.getModelo();
            if (!nivelActual.getEnemigos().contains(mod) || !mod.estaVivo()) {
                itEv.remove();
                continue;
            }
            ev.actualizar();
        }

        if (nivelActual.estaCompletado()) {
            detener();
            vista.avanzarSiguienteNivel();
            return;
        }

        vista.renderizar();
    }

    private void procesarInputJugadores(long now) {
        List<Jugador> jugadores = vista.getJugadoresActivos();
        if (jugadores.isEmpty()) return;

        Jugador j1 = jugadores.get(0);
        if (j1.estaVivo()) {
            if (input.p1Arriba()) moverJugador(j1, Direccion.ARRIBA);
            else if (input.p1Abajo()) moverJugador(j1, Direccion.ABAJO);
            else if (input.p1Izquierda()) moverJugador(j1, Direccion.IZQUIERDA);
            else if (input.p1Derecha()) moverJugador(j1, Direccion.DERECHA);

            if (input.p1Disparar() && (now - lastShot1 > 700_000_000)) {
                ejecutarDisparo(j1);
                lastShot1 = now;
            }
        }

        if (vista.getCantidadJugadores() == 2 && jugadores.size() > 1) {
            Jugador j2 = jugadores.get(1);
            if (j2.estaVivo()) {
                if (input.p2Arriba()) moverJugador(j2, Direccion.ARRIBA);
                else if (input.p2Abajo()) moverJugador(j2, Direccion.ABAJO);
                else if (input.p2Izquierda()) moverJugador(j2, Direccion.IZQUIERDA);
                else if (input.p2Derecha()) moverJugador(j2, Direccion.DERECHA);

                if (input.p2Disparar() && (now - lastShot2 > 700_000_000)) {
                    ejecutarDisparo(j2);
                    lastShot2 = now;
                }
            }
        }
    }

    private void moverJugador(Jugador j, Direccion dir) {
        double oldX = j.getX();
        double oldY = j.getY();

        j.mover(dir);

        if (j.getX() < 0) j.setX(0);
        if (j.getY() < 0) j.setY(0);
        if (j.getX() > WIDTH - Renderizador.TILE_SIZE) j.setX(WIDTH - Renderizador.TILE_SIZE);
        if (j.getY() > HEIGHT - Renderizador.TILE_SIZE - BARRA_INF)
            j.setY(HEIGHT - Renderizador.TILE_SIZE - BARRA_INF);

        if (colisionJugadorConBloques(j) || colisionJugadorConEnemigos(j)) {
            j.setPosicion(oldX, oldY);
        }
    }

    private void ejecutarDisparo(Jugador j) {
        Disparo d = j.disparo();
        if (d != null) {
            vista.getBalasActivas().add(d);
            sonidos.reproducirDisparo();
        }
    }

    private void actualizarEnemigosLogica(Nivel nivel, double deltaTiempo, List<Tanque> tanques) {
        Iterator<Enemigo> it = nivel.getEnemigos().iterator();
        while (it.hasNext()) {
            Enemigo e = it.next();
            double oldX = e.getX();
            double oldY = e.getY();

            Disparo disparo = e.actualizar(deltaTiempo, vista.obtenerBloquesModelo(), tanques, WIDTH, HEIGHT);
            if (disparo != null) vista.getBalasActivas().add(disparo);

            if (e.getX() < 0) e.setX(0);
            if (e.getY() < 0) e.setY(0);
            if (e.getX() > WIDTH - Renderizador.TILE_SIZE) e.setX(WIDTH - Renderizador.TILE_SIZE);
            if (e.getY() > HEIGHT - Renderizador.TILE_SIZE - BARRA_INF) e.setY(HEIGHT - Renderizador.TILE_SIZE - BARRA_INF);

            if (colisionEnemigoConBloques(e) || colisionEntreEnemigos(e)) {
                e.setPosicion(oldX, oldY);
            }
        }
    }

    private void procesarEnemigosMuertos(Nivel nivel) {
        Iterator<Enemigo> it = nivel.getEnemigos().iterator();
        while (it.hasNext()) {
            Enemigo e = it.next();
            if (!e.estaVivo()) {
                it.remove();
                sonidos.reproducirMuerteTanque();
                evaluarSpawnPowerup(e.getX(), e.getY());
            }
        }
    }

    private void actualizarBalasLogica() {
        List<Disparo> eliminar = new ArrayList<>();
        List<Disparo> balas = vista.getBalasActivas();

        for (Disparo d : new ArrayList<>(balas)) {
            d.mover();

            if (d.getX() < 0 || d.getX() > WIDTH || d.getY() < 0 || d.getY() > HEIGHT) {
                eliminar.add(d);
                continue;
            }

            if (colisionBalaBloques(d, eliminar)) continue;
            if (colisionBalaEnemigos(d, eliminar)) continue;
            colisionBalaJugadores(d, eliminar);
        }

        for (int i = 0; i < balas.size(); i++) {
            for (int j = i + 1; j < balas.size(); j++) {
                Disparo b1 = balas.get(i);
                Disparo b2 = balas.get(j);
                if (new Rectangle2D(b1.getX(), b1.getY(), TAM_BALA, TAM_BALA).intersects(new Rectangle2D(b2.getX(), b2.getY(), TAM_BALA, TAM_BALA))) {
                    eliminar.add(b1);
                    eliminar.add(b2);
                }
            }
        }
        balas.removeAll(eliminar);
        eliminar.forEach(Disparo::desactivar);
    }

    private boolean colisionBalaEnemigos(Disparo d, List<Disparo> eliminar) {
        if (d.getTanqueOrigen().getTipoTanque() == TipoTanque.JUGADOR) {
            Rectangle2D balaBounds = new Rectangle2D(d.getX(), d.getY(), TAM_BALA, TAM_BALA);
            Nivel nivel = vista.getNivel();
            if (nivel == null) return false;

            for (Iterator<Enemigo> it = nivel.getEnemigos().iterator(); it.hasNext(); ) {
                Enemigo e = it.next();
                if (balaBounds.intersects(new Rectangle2D(e.getX(), e.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE))) {
                    e.recibirImpacto(d.esPotenciado());
                    if (e.estaVivo()) {
                        if (e.getTipoEnemigo() == TipoEnemigo.BLINDADO) sonidos.reproducirImpactoBlindado();
                    } else {
                        sonidos.reproducirMuerteTanque();
                        it.remove();
                        evaluarSpawnPowerup(e.getX(), e.getY());
                    }
                    eliminar.add(d);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean colisionBalaBloques(Disparo d, List<Disparo> eliminar) {
        Rectangle2D balaBounds = new Rectangle2D(d.getX(), d.getY(), TAM_BALA, TAM_BALA);
        for (Iterator<BloqueView> it = vista.getBloquesView().iterator(); it.hasNext(); ) {
            BloqueView bv = it.next();
            Bloque b = bv.getModelo();
            if (b.bloqueaDisparo() && balaBounds.intersects(new Rectangle2D(b.getX(), b.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE))) {
                b.recibirImpacto();
                eliminar.add(d);

                TipoBloque tipo = b.obtenerTipo();

                if (tipo == TipoBloque.LADRILLO && b.estaDestruido()) sonidos.reproducirDestruccionLadrillo();
                if (tipo == TipoBloque.ACERO) sonidos.reproducirImpactoAcero();
                if (tipo == TipoBloque.BASE && b.estaDestruido()) {
                    sonidos.reproducirDestruccionBase();
                    detener();
                    vista.mostrarDerrota();
                }
                return true;
            }
        }
        return false;
    }

    private void colisionBalaJugadores(Disparo d, List<Disparo> eliminar) {
        Rectangle2D balaBounds = new Rectangle2D(d.getX(), d.getY(), TAM_BALA, TAM_BALA);
        Tanque origen = d.getTanqueOrigen();
        List<Jugador> jugadores = vista.getJugadoresActivos();

        for (Jugador j : jugadores) {
            if (balaBounds.intersects(new Rectangle2D(j.getX(), j.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE)) && j.estaVivo()) {
                if (origen == j) continue;

                boolean origenEsJugador = origen instanceof Jugador;

                if (origenEsJugador) {
                    j.freeze(1000);
                } else {
                    j.recibirImpacto(d.esPotenciado());
                    if (!j.estaVivo()) {
                        sonidos.reproducirMuerteTanque();
                        vista.getBloquesView().add(new TanqueDestruidoView(new TanqueDestruido((int) j.getX(), (int) j.getY())));

                        if (jugadores.stream().noneMatch(Jugador::estaVivo)) {
                            detener();
                            vista.mostrarDerrota();
                        }
                    }
                }
                eliminar.add(d);
                return;
            }
        }
    }

    private void actualizarPowerupsLogica() {
        var it = vista.getPowerupsViewActivos().iterator();
        while (it.hasNext()) {
            var pv = it.next();
            Powerup p = pv.getModelo();

            boolean recolectado = false;
            for (Jugador j : vista.getJugadoresActivos()) {
                if (new Rectangle2D(j.getX(), j.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE)
                        .intersects(new Rectangle2D(pv.getX(), pv.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE))) {
                    p.activar(j);
                    recolectado = true;
                    break;
                }
            }
            if (recolectado) {
                it.remove();
                vista.getPowerupsModelo().remove(p);
            }
        }
    }

    private Powerup crearPowerup(double x, double y) {
        TipoPowerUp[] tipos = TipoPowerUp.values();
        TipoPowerUp tipo = tipos[(int)(Math.random() * tipos.length)];
        return switch (tipo) {
            case CASCO -> new Casco(x, y);
            case ESTRELLA -> new Estrella(x, y);
            case GRANADA -> new Granada(x, y, vista.getNivel().getEnemigos());
        };
    }

    private void evaluarSpawnPowerup(double x, double y) {
        if (Math.random() < 0.2) {
            Rectangle2D spawnBounds = new Rectangle2D(x, y, Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
            for (BloqueView bv : vista.getBloquesView()) {
                if (!bv.getModelo().esTransitable() && spawnBounds.intersects(new Rectangle2D(bv.getModelo().getX(), bv.getModelo().getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE))) {
                    return;
                }
            }
            vista.agregarPowerupView(crearPowerup(x, y));
        }
    }

    private boolean colisionJugadorConBloques(Jugador j) {
        Rectangle2D bounds = new Rectangle2D(j.getX(), j.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
        return vista.getBloquesView().stream()
                .filter(bv -> !bv.getModelo().esTransitable())
                .anyMatch(bv -> bounds.intersects(new Rectangle2D(bv.getModelo().getX(), bv.getModelo().getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE)));
    }

    private boolean colisionJugadorConEnemigos(Jugador j) {
        Rectangle2D bounds = new Rectangle2D(j.getX(), j.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
        Nivel nivel = vista.getNivel();
        return nivel != null && nivel.getEnemigos().stream()
                .anyMatch(e -> bounds.intersects(new Rectangle2D(e.getX(), e.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE)));
    }

    private boolean colisionEnemigoConBloques(Enemigo e) {
        Rectangle2D bounds = new Rectangle2D(e.getX(), e.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
        return vista.getBloquesView().stream()
                .filter(bv -> !bv.getModelo().esTransitable())
                .anyMatch(bv -> bounds.intersects(new Rectangle2D(bv.getModelo().getX(), bv.getModelo().getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE)));
    }

    private boolean colisionEntreEnemigos(Enemigo e) {
        Rectangle2D bounds = new Rectangle2D(e.getX(), e.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE);
        Nivel nivel = vista.getNivel();
        return nivel != null && nivel.getEnemigos().stream()
                .filter(otro -> otro != e)
                .anyMatch(otro -> bounds.intersects(new Rectangle2D(otro.getX(), otro.getY(), Renderizador.TILE_SIZE, Renderizador.TILE_SIZE)));
    }
}