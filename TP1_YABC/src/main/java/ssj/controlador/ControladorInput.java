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

    public boolean p1Arriba() { return teclasPresionadas.contains(KeyCode.UP); }
    public boolean p1Abajo()  { return teclasPresionadas.contains(KeyCode.DOWN); }
    public boolean p1Izquierda() { return teclasPresionadas.contains(KeyCode.LEFT); }
    public boolean p1Derecha()   { return teclasPresionadas.contains(KeyCode.RIGHT); }
    public boolean p1Disparar()  { return teclasPresionadas.contains(KeyCode.SPACE); }

    public boolean p2Arriba() { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.W); }
    public boolean p2Abajo()  { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.S); }
    public boolean p2Izquierda() { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.A); }
    public boolean p2Derecha()   { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.D); }
    public boolean p2Disparar()  { return cantidadJugadores == 2 && teclasPresionadas.contains(KeyCode.F); }
}