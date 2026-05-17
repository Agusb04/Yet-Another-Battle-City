package ssj.modelos.bloques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BaseTest {

    @Test
    public void testEsDestructible() {
        Base base = new Base(0, 0);
        assertTrue(base.esDestructible(), "La base debería ser destructible");
    }

    @Test
    public void testBloqueaDisparo() {
        Base base = new Base(0, 0);
        assertTrue(base.bloqueaDisparo(), "La base debería bloquear disparos");
    }

    @Test
    public void testEsTransitable() {
        Base base = new Base(0, 0);
        assertFalse(base.esTransitable(), "La base no debería ser transitable");
    }

    @Test
    public void testRecibirImpacto() {
        Base base = new Base(0, 0);
        assertFalse(base.estaDestruido(), "Inicialmente la base no está destruida");
        base.recibirImpacto();
        assertTrue(base.estaDestruido(), "La base debería estar destruida después de recibir un impacto");
    }
}
