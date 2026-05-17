package ssj.modelos.disparo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ssj.modelos.LogicaMovimiento.Direccion;
import ssj.modelos.tanques.Jugador;

public class DisparoTest {

    @Test
    public void testInicializacion() {
        Jugador jugador = new Jugador(0, 0, 1, 1);
        Disparo d = new Disparo(0, 0, Direccion.ARRIBA, 3, true, jugador);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
        assertEquals(Direccion.ARRIBA, d.getDireccion());
        assertEquals(3, d.getVelocidad());
        assertTrue(d.isActivo());
        assertTrue(d.esPotenciado());
        assertEquals(jugador, d.getTanqueOrigen());
    }

    @Test
    public void testMovimiento() {
        Jugador jugador = new Jugador(0, 0, 1, 1);
        Disparo d = new Disparo(0, 0, Direccion.DERECHA, 2, false, jugador);
        d.mover();
        assertEquals(2, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    public void testDesactivar() {
        Jugador jugador = new Jugador(0, 0, 1, 1);
        Disparo d = new Disparo(0, 0, Direccion.ABAJO, 1, false, jugador);
        d.desactivar();
        assertFalse(d.isActivo());
        d.mover();
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    public void testCambioDireccion() {
        Jugador jugador = new Jugador(0, 0, 1, 1);
        Disparo d = new Disparo(0, 0, Direccion.ARRIBA, 1, false, jugador);
        d.setDireccion(Direccion.IZQUIERDA);
        d.mover();
        assertEquals(-1, d.getX());
        assertEquals(0, d.getY());
    }
}
