package ssj.vista.Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ssj.vista.Utils.Grafico;

import java.util.Objects;

public class VistaInicio {

    private final Scene inicioScene;
    private final Button btnStart;

    public VistaInicio(double width, double height) {
        btnStart = new Button("START GAME");
        btnStart.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-size: 16px;");

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Grafico.IMAGEN_INICIO)));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(400);
        logoView.setPreserveRatio(true);

        VBox inicioRoot = new VBox(20, logoView, btnStart);
        inicioRoot.setAlignment(Pos.CENTER);
        inicioRoot.setStyle("-fx-background-color: #2F2F2F;");

        inicioScene = new Scene(inicioRoot, width, height);
    }

    public Scene getScene() {
        return inicioScene;
    }

    public Button getStartButton() {
        return btnStart;
    }
}
