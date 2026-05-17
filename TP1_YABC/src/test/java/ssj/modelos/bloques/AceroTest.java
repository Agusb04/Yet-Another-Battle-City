package ssj.modelos.bloques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AceroTest {

    @Test
    public void testEsDestructible() {
        Acero acero = new Acero(0, 0);
        assertFalse(acero.esDestructible(), "El bloque de acero no debería ser destructible");
    }

    @Test
    public void testBloqueaDisparo() {
        Acero acero = new Acero(0, 0);
        assertTrue(acero.bloqueaDisparo(), "El bloque de acero debería bloquear disparos");
    }

    @Test
    public void testEsTransitable() {
        Acero acero = new Acero(0, 0);
        assertFalse(acero.esTransitable(), "El bloque de acero no debería ser transitable");
    }

    @Test
    public void testRecibirImpacto() {
        Acero acero = new Acero(0, 0);
        acero.recibirImpacto();
        assertFalse(acero.estaDestruido(), "El bloque de acero nunca debería destruirse");
        assertEquals(0, acero.getVida(), "El bloque de acero tiene vida inicial 0");
    }
}

