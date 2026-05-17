package ssj.controlador;

import ssj.sonidos.ReproductorSonidos;
import ssj.sonidos.Sonido;

public class ControladorSonidos {

    private final ReproductorSonidos reproductor;

    public ControladorSonidos() {
        this.reproductor = new ReproductorSonidos();
    }

    public void iniciarMusicaJuego() {
        reproductor.reproducirMusica(Sonido.MUSICA_JUEGO);
    }

    public void detenerMusica() {
        reproductor.pararMusica();
    }

    public void reproducirDisparo() {
        reproductor.reproducirSonido(Sonido.DISPARO);
    }

    public void reproducirMuerteTanque() {
        reproductor.reproducirSonido(Sonido.MUERTE_TANQUE);
    }

    public void reproducirDestruccionBase() {
        reproductor.reproducirSonido(Sonido.DESTRUCCION_BASE);
    }

    public void reproducirImpactoBlindado() {
        reproductor.reproducirSonido(Sonido.IMPACTO_TANQUE_BLINDADO);
    }

    public void reproducirImpactoAcero() {
        reproductor.reproducirSonido(Sonido.IMPACTO_BLOQUE_ACERO);
    }

    public void reproducirDestruccionLadrillo() {
        reproductor.reproducirSonido(Sonido.DESTRUCCION_BLOQUE_LADRILLO);
    }
}