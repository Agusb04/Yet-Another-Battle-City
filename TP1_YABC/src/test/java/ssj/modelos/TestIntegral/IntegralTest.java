package ssj.modelos.TestIntegral;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import ssj.modelos.Nivel.Nivel;
import ssj.modelos.tanques.Enemigo;
import ssj.modelos.tanques.Jugador;
import ssj.modelos.bloques.*;

public class IntegralTest {

    @Test
    public void testPasarDeNivel() {
        Jugador jugador = new Jugador(1, 5, 5, 3);

        Nivel nivel1 = new Nivel(1, 1, 1, 0.1);
        Nivel nivel2 = new Nivel(2, 1, 1, 0.1);

        List<Nivel> niveles = new ArrayList<>();
        niveles.add(nivel1);
        niveles.add(nivel2);

        List<Bloque> bloques = new ArrayList<>();
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugador);

        nivel1.actualizar(0.1, bloques, 200, 200, listaJugadores);

        for (Enemigo e : nivel1.getEnemigos()) {
            e.recibirImpacto(true);
        }
        assertTrue(nivel1.estaCompletado(), "Nivel 1 debería estar completado");

        nivel2.actualizar(0.1, bloques, 200, 200, listaJugadores);

        for (Enemigo e : nivel2.getEnemigos()) {
            e.recibirImpacto(true);
        }
        assertTrue(nivel2.estaCompletado(), "Nivel 2 debería estar completado");
    }

    @Test
    public void testJugadorEliminaTodosLosEnemigos() {
        Jugador jugador = new Jugador(1, 5, 5, 3);

        Nivel nivel = new Nivel(1, 1, 1, 0.1);

        List<Bloque> bloques = new ArrayList<>();
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugador);

        nivel.actualizar(0.1, bloques, 200, 200, listaJugadores);

        for (Enemigo e : nivel.getEnemigos()) {
            e.recibirImpacto(true);
        }

        assertTrue(nivel.estaCompletado(), "El nivel debería estar completado después de eliminar todos los enemigos");
    }

    @Test
    public void testJuegoCompletoDosJugadores() {
        Jugador jugador1 = new Jugador(1, 5, 5, 3);
        Jugador jugador2 = new Jugador(2, 15, 5, 3);

        Nivel nivel1 = new Nivel(1, 1, 1, 0.1);
        Nivel nivel2 = new Nivel(2, 1, 1, 0.1);
        Nivel nivel3 = new Nivel(3, 1, 1, 0.1);

        List<Nivel> niveles = new ArrayList<>();
        niveles.add(nivel1);
        niveles.add(nivel2);
        niveles.add(nivel3);

        List<Bloque> bloques = new ArrayList<>();
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugador1);
        listaJugadores.add(jugador2);

        for (Nivel nivel : niveles) {
            nivel.actualizar(0.1, bloques, 200, 200, listaJugadores);

            for (Enemigo e : nivel.getEnemigos()) {
                e.recibirImpacto(true);
            }

            assertTrue(nivel.estaCompletado(), "El nivel " + nivel.getNumero() + " debería estar completado");
        }

        assertEquals(3, niveles.size(), "Debería haber exactamente 3 niveles");
        for (Nivel nivel : niveles) {
            assertTrue(nivel.estaCompletado(), "Nivel " + nivel.getNumero() + " debería estar completado");
        }
    }
}