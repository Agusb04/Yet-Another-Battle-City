package ssj.modelos.bloques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BosqueTest {

    @Test
    public void testEsDestructible() {
        Bosque bosque = new Bosque(0, 0);
        assertFalse(bosque.esDestructible(), "El bosque no debería ser destructible");
    }

    @Test
    public void testBloqueaDisparo() {
        Bosque bosque = new Bosque(0, 0);
        assertFalse(bosque.bloqueaDisparo(), "El bosque no debería bloquear disparos");
    }

    @Test
    public void testEsTransitable() {
        Bosque bosque = new Bosque(0, 0);
        assertTrue(bosque.esTransitable(), "El bosque debería ser transitable");
    }

    @Test
    public void testRecibirImpacto() {
        Bosque bosque = new Bosque(0, 0);
        bosque.recibirImpacto();
        assertFalse(bosque.estaDestruido(), "El bosque no debería destruirse al recibir un impacto");
    }
}
