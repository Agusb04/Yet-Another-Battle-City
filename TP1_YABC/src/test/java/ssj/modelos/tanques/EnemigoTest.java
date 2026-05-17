package ssj.modelos.tanques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ssj.modelos.LogicaMovimiento.Direccion;

public class EnemigoTest {

    @Test
    public void testInicializacion() {
        Enemigo enemigo = new Enemigo(10, 15, 3, 2, 1.0, TipoEnemigo.BASICO);
        assertEquals(10, enemigo.getX());
        assertEquals(15, enemigo.getY());
        assertEquals(3, enemigo.getVida());
        assertEquals(TipoTanque.ENEMIGO, enemigo.getTipoTanque());
        assertFalse(enemigo.puedeAgarrarPowerUps());
        assertEquals(TipoEnemigo.BASICO, enemigo.getTipoEnemigo());
    }

    @Test
    public void testMovimientoManual() {
        Enemigo enemigo = new Enemigo(0, 0, 1, 2, 1.0, TipoEnemigo.BASICO);
        enemigo.direccionActual = Direccion.DERECHA;
        enemigo.moverse();
        assertEquals(2, enemigo.getX());
        assertEquals(0, enemigo.getY());

        enemigo.direccionActual = Direccion.ABAJO;
        enemigo.moverse();
        assertEquals(2, enemigo.getX());
        assertEquals(2, enemigo.getY());
    }
}
