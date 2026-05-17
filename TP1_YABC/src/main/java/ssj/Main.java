package ssj;

import javafx.application.Application;
import javafx.stage.Stage;
import ssj.vista.Juego.JuegoVista; // Importamos la nueva vista unificada
import ssj.vista.Menu.VistaInicio;
import ssj.vista.Menu.VistaMenu;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        int WIDTH = 820;
        int HEIGHT = 660;
        VistaInicio inicio = new VistaInicio(WIDTH, HEIGHT);
        VistaMenu menu = new VistaMenu(WIDTH, HEIGHT);

        // Transición de la pantalla de bienvenida al menú principal
        inicio.getStartButton().setOnAction(e -> stage.setScene(menu.getScene()));

        // Botón salir
        menu.getBtnExit().setOnAction(e -> stage.close());

        // Configuración para 1 Jugador
        menu.getBtn1Player().setOnAction(e -> {
            // Instanciamos JuegoVista pasando '1' en el parámetro de cantidad de jugadores
            JuegoVista juego1 = new JuegoVista(stage, inicio, menu, 1);
            stage.setScene(juego1.getScene());
            juego1.getScene().getRoot().requestFocus(); // Le damos el foco para que capte las teclas inmediatamente
        });


        menu.getBtn2Player().setOnAction(e -> {

            JuegoVista juego2 = new JuegoVista(stage, inicio, menu, 2);
            stage.setScene(juego2.getScene());
            juego2.getScene().getRoot().requestFocus();
        });


        stage.setTitle("YABC - TP");
        stage.setScene(inicio.getScene());
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}