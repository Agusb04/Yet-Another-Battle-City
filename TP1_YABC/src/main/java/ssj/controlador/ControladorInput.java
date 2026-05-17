package ssj.controlador;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class ControladorInput {

    private final Set<KeyCode> teclasPresionadas = new HashSet<>();
    private final int cantidadJugadores;

    public ControladorInput(Scene scene, int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;

        scene.setOnKeyPressed(e -> teclasPresionadas.add(e.getCode()));
        scene.setOnKeyReleased(e -> teclasPresionadas.remove(e.getCode()));
    }

    public boolean p1Arriba() { return cantidadJugadores == 1 ? teclasPresionadas.contains(KeyCode.UP) : teclasPresionadas.contains(KeyCode.W); }
    public boolean p1Abajo()  { return cantidadJugadores == 1 ? teclasPresionadas.contains(KeyCode.DOWN) : teclasPresionadas.contains(KeyCode.S); }
    public boolean p1Izquierda() { return cantidadJugadores == 1 ? teclasPresionadas.contains(KeyCode.LEFT) : teclasPresionadas.contains(KeyCode.A); }
    public boolean p1Derecha()   { return cantidadJugadores == 1 ? teclasPresionadas.contains(KeyCode.RIGHT) : teclasPresionadas.contains(KeyCode.D); }
    public boolean p1Disparar()  { return cantidadJugadores == 1 ? teclasPresionadas.contains(KeyCode.SPACE) : teclasPresionadas.contains(KeyCode.F); }

    public boolean p2Arriba() { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.UP); }
    public boolean p2Abajo()  { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.DOWN); }
    public boolean p2Izquierda() { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.LEFT); }
    public boolean p2Derecha()   { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.RIGHT); }
    public boolean p2Disparar()  { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.ENTER); }
}