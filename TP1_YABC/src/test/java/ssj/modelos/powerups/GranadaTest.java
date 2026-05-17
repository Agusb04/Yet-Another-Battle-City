package ssj.modelos.powerups;

import org.junit.jupiter.api.Test;
import ssj.modelos.tanques.Enemigo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GranadaTest {

    @Test
    public void testTipo() {
        List<Enemigo> enemigos = List.of();
        Granada granada = new Granada(0, 0, enemigos);
        assertEquals(TipoPowerUp.GRANADA, granada.obtenerTipo());
    }
}
