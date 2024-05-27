package GestionObjects;

import LectureConfig.LectureConfig;
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
        //Stock dans la classe le nombre d'agents
        NbrAgent = NbrAgents;

        //Création d'un tableau temporaire d'agents
        Agent[] temp = new Agent[NbrAgents];

        //Création de l'image
        Image ship = new Image( "ship.png" );

        //Création de la cible
        cible = new Cible(LectureConfig.posCible[0], LectureConfig.posCible[1], ship);

        //Création des agents
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            temp[idx] = new Agent(110.0f*idx,110.0f*idx,ship);
        }

        //Attribution du tableau temporaire au tableau d'agents de la classe
        agents = temp;
    }
    static public void Affichage(GraphicsContext gc)
    {
        //Actualisation de l'arriere plan
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1]);

        //Affichage de la cible
        drawImage(gc,cible.getImage(),0d,cible.getPosition()[0],cible.getPosition()[1]);

        //Affichage de agents
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            drawImage(gc,agents[idx].getImage(),agents[idx].getAngle(),agents[idx].getPosition()[0],agents[idx].getPosition()[1]);
        }
    }
    static private void drawImage(GraphicsContext gc, Image image, double angle,float positionX,float positionY) {

        //Acquisition de la position de l'image
        float posX = (float) (positionX + image.getWidth()/2);
        float posY = (float) (positionY + image.getHeight()/2);
        // Sauvegarder l'état actuel de la transformation
        gc.save();

        // Appliquer la rotation
        gc.rotate(angle);

        //Calcul de la position de l'image en polaire
        double alpha = Math.atan(posX/-posY);
        double module = Math.hypot(posX,posY);

        //Calcul du nouvel angle de l'image apres rotation
        double alpha2 = alpha + (angle/180*Math.PI);

        //Calcul de la nouvelle position en cartésien
        double posX2 = module * Math.cos(alpha2);
        double posY2 = -module * Math.sin(alpha2);

        // Dessiner l'image centrée par rapport à son point de rotation
        gc.drawImage(image,posX2-(image.getWidth()/2), posY2- (image.getHeight()/2));

        // Restaurer l'état précédent de la transformation
        gc.restore();

    }
}
