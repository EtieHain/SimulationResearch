package GestionObjects;

import Objects.Agent;
import Objects.Cible;
import com.example.simulationresearch.HelloController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GestionObjects
{
    static Agent[] agents;
    static Cible cible;

    static public void creationObjects(int NbrAgents)
    {
        Image earth = new Image( "earth.png" );
        cible = new Cible(0.0f,0.0f,4f,earth);
    }
    static public void Affichage()
    {
        GraphicsContext gc = HelloController.ResearchZone.getGraphicsContext2D();
        //gc.drawImage(agents[0].getImage(),agents[0].getPosition()[0],agents[0].getPosition()[1]);
        gc.drawImage(cible.getImage(),cible.getPosition()[0],cible.getPosition()[1]);
    }
}
