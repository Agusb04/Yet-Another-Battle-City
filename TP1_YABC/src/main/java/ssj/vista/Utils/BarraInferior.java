package ssj.vista.Utils;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ssj.vista.Juego.JuegoBase;
import ssj.vista.Menu.VistaInicio;
import ssj.vista.Menu.VistaMenu;
import javafx.geometry.Pos;

import java.util.Objects;

public class BarraInferior {

    public static void agregarBarra(Pane root, Stage stage, VistaInicio inicio, VistaMenu menu, JuegoBase juegoActivo) {
        HBox barra = new HBox(15);
        barra.setAlignment(Pos.CENTER);
        barra.setStyle("-fx-background-color: #333; -fx-padding: 10;");

        Image logoImg = new Image(Objects.requireNonNull(BarraInferior.class.getResourceAsStream(Grafico.IMAGEN_INICIO)));
        ImageView logoView = new ImageView(logoImg);
        logoView.setFitWidth(40);
        logoView.setFitHeight(40);
        logoView.setPreserveRatio(true);

        Button btnInicio = new Button("INICIO");
        Button btnMenu = new Button("MENU");
        Button btnSalir = new Button("SALIR");

        btnSalir.setOnAction(e -> stage.close());

        btnInicio.setOnAction(e -> {
            if (juegoActivo != null) juegoActivo.pausarJuego();
            stage.setScene(inicio.getScene());
        });

        btnMenu.setOnAction(e -> {
            if (juegoActivo != null) juegoActivo.pausarJuego();
            stage.setScene(menu.getScene());
        });

        barra.getChildren().addAll(logoView, btnInicio, btnMenu, btnSalir);

        double BarraInferiorHeight = 50;
        barra.setLayoutY(root.getPrefHeight() - BarraInferiorHeight);
        barra.setPrefWidth(root.getPrefWidth());

        root.getChildren().add(barra);
    }
}
