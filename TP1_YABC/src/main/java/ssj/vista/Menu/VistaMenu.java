package ssj.vista.Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class VistaMenu {

    private final Scene menuScene;
    private final Button btn1Player;
    private final Button btn2Player;
    private final Button btnExit;

    public VistaMenu(double width, double height) {
        btn1Player = new Button("1 Player");
        btn2Player = new Button("2 Players");
        btnExit = new Button("Exit");

        btn1Player.setPrefWidth(150);
        btn2Player.setPrefWidth(150);
        btnExit.setPrefWidth(150);

        VBox menuRoot = new VBox(10, btn1Player, btn2Player, btnExit);
        menuRoot.setAlignment(Pos.CENTER);
        menuRoot.setStyle("-fx-background-color: #2F2F2F;");

        btn1Player.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-size: 16px;");
        btn2Player.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-size: 16px;");
        btnExit.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-size: 16px;");

        menuScene = new Scene(menuRoot, width, height);
    }

    public Scene getScene() {
        return menuScene;
    }

    public Button getBtn1Player() { return btn1Player; }
    public Button getBtn2Player() { return btn2Player; }
    public Button getBtnExit() { return btnExit; }
}
