package GestionObjects;

import Objects.Agent;
import Objects.Cible;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.lang.Math;

public class GestionObjects
{
    static private int NbrAgent;
    static public Agent[] agents;
    static Cible cible;

    static public void creationObjects(int NbrAgents)
    {
        Agent[] temp = new Agent[5];
        NbrAgent = NbrAgents;
        Image earth = new Image( "earth.png" );
        cible = new Cible(552f,552f,4f,earth);
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            temp[idx] = new Agent(110.0f*idx,110.0f*idx,4f,earth);
        }
        agents = temp;

    }
    static public void Affichage(GraphicsContext gc)
    {
        gc.setFill(Color.WHITE); // Couleur de fond
        gc.fillRect(0, 0, 600,600);
        //gc.drawImage(cible.getImage(),cible.getPosition()[0],cible.getPosition()[1]);
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            drawImage(gc,agents[idx].getImage(),Agent.angle,agents[idx].getPosition()[0],agents[idx].getPosition()[1]);
        }
        drawImage(gc,cible.getImage(),0d,cible.getPosition()[0],cible.getPosition()[1]);
    }
    static private void drawImage(GraphicsContext gc, Image image, double angle,float positionX,float positionY) {

        float posX = (float) (positionX + image.getWidth()/2);
        float posY = (float) (positionY + image.getHeight()/2);
        // Sauvegarder l'état actuel de la transformation
        gc.save();

        // Appliquer la rotation
        gc.rotate(angle);

        //Calculs marabouteux pour replacer l'image au bon endroit
        double alpha = Math.atan(posX/-posY);
        double module = Math.hypot(posX,posY);
        double alpha2 = alpha + (angle/180*Math.PI);
        double posX2 = module * Math.cos(alpha2);
        double posY2 = -module * Math.sin(alpha2);

        // Dessiner l'image centrée par rapport à son point de rotation
        gc.drawImage(image,posX2-(image.getWidth()/2), posY2- (image.getHeight()/2));

        // Restaurer l'état précédent de la transformation
        gc.restore();

    }
}
