package ssj.modelos.tanques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ssj.modelos.LogicaMovimiento.Direccion;

public class JugadorTest {

    @Test
    public void testInicializacion() {
        Jugador jugador = new Jugador(1,5, 10, 3);
        assertEquals(3, jugador.getVidas());
        assertEquals(TipoTanque.JUGADOR, jugador.getTipoTanque());
    }

    @Test
    public void testImpactos() {
        Jugador jugador = new Jugador(1,5, 10, 3);
        assertEquals(3, jugador.getVidas());
        jugador.recibirImpacto(false);
        assertEquals(2, jugador.getVidas());
        jugador.recibirImpacto(false);
        assertEquals(1, jugador.getVidas());
        jugador.recibirImpacto(false);
        assertEquals(0, jugador.getVidas());
    }

    @Test
    public void testPoweup() {
        Jugador jugador = new Jugador(1,0, 0, 3);
        assertFalse(jugador.isPowerUpActivo());
        jugador.activarPowerUp();
        assertFalse(jugador.isPowerUpActivo());
    }
}
