package ssj.modelos.tanques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ssj.modelos.LogicaMovimiento.Direccion;

public class EnemigoPotenteTest {

    @Test
    public void testInicializacion() {
        EnemigoPotente e = new EnemigoPotente(5, 10);
        assertEquals(5, e.posicion.getX(), 0.001);
        assertEquals(10, e.posicion.getY(), 0.001);
        assertEquals(TipoTanque.ENEMIGO, e.getTipoTanque());
        assertEquals(1, e.getVida());
        assertFalse(e.puedeAgarrarPowerUps());
    }

    @Test
    public void testRecibirImpacto() {
        EnemigoPotente e = new EnemigoPotente(0,0);
        assertEquals(1, e.getVida());
        e.recibirImpacto(false);
        assertEquals(0, e.getVida());
        e.recibirImpacto(true);
        assertEquals(0, e.getVida());
    }

    @Test
    public void testMoverse() {
        EnemigoPotente e = new EnemigoPotente(0,0);
        e.direccionActual = Direccion.DERECHA;
        double oldX = e.posicion.getX();
        double oldY = e.posicion.getY();
        e.moverse();
        assertEquals(oldX + e.velocidad, e.posicion.getX(), 0.001);
        assertEquals(oldY, e.posicion.getY(), 0.001);
    }

}
