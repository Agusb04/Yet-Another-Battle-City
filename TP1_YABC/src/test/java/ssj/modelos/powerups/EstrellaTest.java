package ssj.modelos.powerups;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EstrellaTest {

    @Test
    public void testTipo() {
        Estrella estrella = new Estrella(0,0);
        assertEquals(TipoPowerUp.ESTRELLA, estrella.obtenerTipo());
    }
    

}