package ssj.modelos.bloques;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LadrilloTest {

    @Test
    public void testEsDestructible() {
        Ladrillo ladrillo = new Ladrillo(0, 0);
        assertTrue(ladrillo.esDestructible(), "El ladrillo debería ser destructible");
    }

    @Test
    public void testBloqueaDisparo() {
        Ladrillo ladrillo = new Ladrillo(0, 0);
        assertTrue(ladrillo.bloqueaDisparo(), "El ladrillo debería bloquear disparos");
    }

    @Test
    public void testEsTransitable() {
        Ladrillo ladrillo = new Ladrillo(0, 0);
        assertFalse(ladrillo.esTransitable(), "El ladrillo no debería ser transitable");
    }

    @Test
    public void testRecibirImpacto() {
        Ladrillo ladrillo = new Ladrillo(0, 0);
        ladrillo.recibirImpacto();
        assertEquals(2, ladrillo.getVida(), "La vida del ladrillo debería decrementar tras un impacto");
        ladrillo.recibirImpacto();
        ladrillo.recibirImpacto();
        assertTrue(ladrillo.estaDestruido(), "El ladrillo debería destruirse tras 3 impactos");
    }
}
