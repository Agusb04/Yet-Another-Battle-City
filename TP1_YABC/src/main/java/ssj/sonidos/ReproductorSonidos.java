package ssj.sonidos;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;

public class ReproductorSonidos {

    private MediaPlayer musica;

    public ReproductorSonidos(){

    }
    public void reproducirMusica(String ruta){
        pararMusica();

        Media media = new Media(Objects.requireNonNull(getClass().getResource(ruta)).toExternalForm());
        musica = new MediaPlayer(media);
        musica.setCycleCount(MediaPlayer.INDEFINITE);
        musica.setVolume(0.3);
        musica.play();
    }

    public void pararMusica(){
        if (musica != null){
            musica.stop();
            musica.dispose();
            musica = null;
        }
    }

    public void reproducirSonido(String ruta){
        AudioClip clip = new AudioClip(Objects.requireNonNull(getClass().getResource(ruta)).toExternalForm());
        clip.setVolume(0.7);
        clip.play();
    }
}
