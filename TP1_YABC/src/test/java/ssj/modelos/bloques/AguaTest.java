package ssj.modelos.bloques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AguaTest {

    @Test
    public void testEsDestructible() {
        Agua agua = new Agua(0, 0);
        assertFalse(agua.esDestructible(), "El bloque de agua no debería ser destructible");
    }

    @Test
    public void testBloqueaDisparo() {
        Agua agua = new Agua(0, 0);
        assertFalse(agua.bloqueaDisparo(), "El bloque de agua no debería bloquear disparos");
    }

    @Test
    public void testEsTransitable() {
        Agua agua = new Agua(0, 0);
        assertFalse(agua.esTransitable(), "El bloque de agua no debería ser transitable");
    }

    @Test
    public void testRecibirImpacto() {
        Agua agua = new Agua(0, 0);
        agua.recibirImpacto();
        assertFalse(agua.estaDestruido(), "El bloque de agua nunca debería destruirse");
        assertEquals(0, agua.getVida(), "El bloque de agua tiene vida inicial 0");
    }
}
