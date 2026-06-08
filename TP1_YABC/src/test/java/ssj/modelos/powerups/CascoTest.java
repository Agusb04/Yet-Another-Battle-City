package ssj.modelos.powerups;

import org.junit.jupiter.api.Test;
import ssj.modelos.tanques.Jugador;
import ssj.modelos.tanques.Tanque;

import static org.junit.jupiter.api.Assertions.*;

public class CascoTest {

    @Test
    public void testTipo() {
        Casco casco = new Casco(0,0);
        assertEquals(TipoPowerUp.CASCO, casco.obtenerTipo());
    }
    @Test
    public void testActivarEfecto() {
        Casco casco = new Casco(0,0);
        Jugador jugador = new Jugador(1,0, 0, 3);
        assertFalse(jugador.isInvulnerable());
        casco.activar(jugador);
        assertTrue(jugador.isInvulnerable());
    }

    @Test
    public void testSinActivarNoCambiaTanque() {
        Casco casco = new Casco(0,0);
        Tanque tanque = new Jugador(1,0, 0, 3);
        assertFalse(tanque.isInvulnerable());
    }

}

