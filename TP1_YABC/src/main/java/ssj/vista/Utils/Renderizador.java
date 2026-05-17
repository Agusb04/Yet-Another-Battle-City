package ssj.vista.Utils;

import javafx.geometry.Point2D;
import ssj.modelos.bloques.*;
import ssj.vista.View.BloqueView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Renderizador {

    public static final int TILE_SIZE = 20;

    public static class LevelInfo {
        public final List<Point2D> playerPositions = new ArrayList<>();
        public final List<Bloque> bloques = new ArrayList<>();
        public final List<BloqueView> bloquesView = new ArrayList<>();
    }

    // SE ELIMINÓ EL PARÁMETRO 'Pane root' QUE NO SE USABA
    public static LevelInfo renderizar(String nivelPath) {
        LevelInfo info = new LevelInfo();

        try (InputStream is = Renderizador.class.getResourceAsStream(nivelPath)) {
            if (is == null) throw new RuntimeException("No se encontró el nivel: " + nivelPath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();

            var jugadores = doc.getElementsByTagName("player");
            for (int i = 0; i < jugadores.getLength(); i++) {
                var player = (org.w3c.dom.Element) jugadores.item(i);
                double x = Double.parseDouble(player.getAttribute("x"));
                double y = Double.parseDouble(player.getAttribute("y"));
                info.playerPositions.add(new Point2D(x, y));
            }

            var estaticos = doc.getElementsByTagName("staticObject");
            for (int i = 0; i < estaticos.getLength(); i++) {
                var obj = (org.w3c.dom.Element) estaticos.item(i);
                int x = Integer.parseInt(obj.getAttribute("x"));
                int y = Integer.parseInt(obj.getAttribute("y"));
                String tipo = obj.getAttribute("type");

                Bloque bloque = switch (tipo) {
                    case "brickBlock" -> new Ladrillo(x, y);
                    case "steelBlock" -> new Acero(x, y);
                    case "waterBlock" -> new Agua(x, y);
                    case "forestBlock" -> new Bosque(x, y);
                    case "baseBlock" -> new Base(x, y);
                    default -> null;
                };

                if (bloque != null) {
                    info.bloques.add(bloque);

                    String spritePath = switch (tipo) {
                        case "brickBlock" -> Grafico.BLOQUE_DE_LADRILLOS;
                        case "steelBlock" -> Grafico.BLOQUE_DE_ACERO;
                        case "waterBlock" -> Grafico.BLOQUE_DE_AGUA;
                        case "forestBlock" -> Grafico.CELDA_BOSQUE;
                        case "baseBlock" -> Grafico.BASE_DE_JUGADORES;
                        default -> null;
                    };

                    // SE REMOVIÓ EL IF REDUNDANTE (Condición siempre true)
                    Image img = cargarImagen(spritePath);
                    BloqueView view = new BloqueView(bloque, img);
                    info.bloquesView.add(view);
                }
            }

        } catch (Exception e) {
            System.err.println("[Renderizador] Error crítico parseando el mapa XML: " + e.getMessage());
        }

        return info;
    }

    private static Image cargarImagen(String resourcePath) {
        try (InputStream is = Renderizador.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("[Renderizador] No se encontró sprite: " + resourcePath);
                return null;
            }
            return new Image(is, TILE_SIZE, TILE_SIZE, false, false);
        } catch (Exception ex) {
            System.err.println("[Renderizador] Excepción al cargar la imagen " + resourcePath + ": " + ex.getMessage());
            return null;
        }
    }
}