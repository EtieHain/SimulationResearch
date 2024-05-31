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
    static public int NbrAgent;
    static public Agent[] agents;
    static public Cible cible;
    //Algo de calcul des position en fonctione de la fenetre et des parametres de l'agent
    //x : nombre de position minimale sur la moitié d'une arrete
    //arrondie au dessus en cas de division pas entière
    private static float x = (float)Math.ceil((float)(LectureConfig.dimensionCaneva[0]/2)/(LectureConfig.agentsDetectionRange*2));
    static int winSize;
    //l : distance minimale entre les positions afin de tout couvrir
     private static float l;
    //N : nombre totale de positions
    // 2 moitié sur 4 arrete et *2 pour les positions intérieures -> 2*4*2=16
     public static int N = (int) (16*x);
    //déclaration du tableau de position
    //N cellule avec 2 coordonnée (x,y)
    public static float[][] posTab = new float[N][2];
    //définition du r minimale pour le calcul des positions intérieures.
    private static float r = Math.min(LectureConfig.agentsDetectionRange,LectureConfig.agentsCommunicationRange);

    static public void creationObjects(int NbrAgents)
    {
        Image ship = new Image( "ship.png" );
        Image target = new Image("target.png");
        winSize = (int) (LectureConfig.dimensionCaneva[0]-(ship.getHeight()));
        l = (winSize /2)/x;
        int o = (int) (ship.getWidth()/2);
        //boucle de calcul des coordonnée des position
        for(int i =0;i<N;i+=2){
            //algo différent en fonction de l'arrête
            if (i>=0&&i<N/4) {
                //calcul des coordonnée des position extérieure
                posTab[i+1][0] = winSize-(i/2)*l+o;
                posTab[i+1][1] = winSize+o;
                //Calcul des positions intérieures en fonction des position intérieure
                //position allignée à la position extérieures par rapport au centre
                posTab[i][0]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][0]-o-winSize/2);
                posTab[i][1]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][1]-o-winSize/2);
            } else if (i>=N/4&&i<N/2) {
                posTab[i+1][0] = o;
                posTab[i+1][1] = winSize-((i/2)-N/8)*l+o;
                posTab[i][0]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][0]-o-winSize/2);
                posTab[i][1]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][1]-o-winSize/2);
            } else if (i>=N/2&&i<N*0.75) {
                posTab[i+1][0] = ((i/2)-N/4)*l+o;
                posTab[i+1][1] = o;
                posTab[i][0]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][0]-o-winSize/2);
                posTab[i][1]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][1]-o-winSize/2);
            } else if (i>=N*0.75&&i<N) {
                posTab[i+1][0] = winSize+o;
                posTab[i+1][1] = (float)((i/2)-(N/2)*0.75)*l+o;
                posTab[i][0]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][0]-o-winSize/2);
                posTab[i][1]=o+winSize/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-o-winSize/2),(GestionObjects.posTab[i+1][1]-o-winSize/2))))*(GestionObjects.posTab[i+1][1]-o-winSize/2);
            }
        }
        //Stock dans la classe le nombre d'agents
        NbrAgent = NbrAgents;

        //Création d'un tableau temporaire d'agents
        Agent[] temp = new Agent[NbrAgents];

        //calcul de l'intervalle de position dans le tableau entre les agent
        //ex : 4 agent 16 position -> 1 agent toute les 4 positions
        System.out.println(N);
        float intervalle = (float) Math.ceil(N /NbrAgents);
        //boucle de création des agents
        for(int jj = 0;jj<NbrAgent;jj++){
            //position de l'agent en fct de l'offset
            int S = (int) (jj*intervalle);
            //cration de l'objet et calcul de sa direction en fct de sa prochaine position
            temp[jj] = new Agent(posTab[S][0],posTab[S][1],S,ship);
            temp[jj].setDirection((float) ((posTab[S+1][0]-posTab[S][0])/(Math.hypot((posTab[S][0]-posTab[S+1][0]),(posTab[S+1][1]-posTab[S][1])))), (float) ((posTab[S+1][1]-posTab[S][1])/(Math.hypot((posTab[S][0]-posTab[S+1][0]),(posTab[S][1]-posTab[S+1][1])))));
        }

        //Attribution du tableau temporaire au tableau d'agents de la classe
        agents = temp;

        cible = new Cible(LectureConfig.posCible[0],LectureConfig.posCible[1],target);
    }
    static public void Affichage(GraphicsContext gc)
    {
        //Actualisation de l'arriere plan
//        gc.setFill(Color.WHITE);
//        gc.fillRect(0, 0, LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1]);

        Image bg = new Image("background2.jpg",LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1],false,false);
        gc.drawImage(bg,0,0);

        //Affichage de la cible
        gc.drawImage(cible.getImage(),cible.getPosition()[0]-cible.getImage().getWidth()/2,cible.getPosition()[1]-cible.getImage().getHeight()/2);

        //Affichage de agents
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            drawImage(gc,agents[idx].getImage(),agents[idx].getAngle(),agents[idx].getPosition()[0],agents[idx].getPosition()[1]);
        }
    }
    static private void drawImage(GraphicsContext gc, Image image, double angle,float positionX,float positionY) {

        //Acquisition de la position de l'image
        float posX = (float) (positionX/* + image.getWidth()/2*/);
        float posY = (float) (positionY/* + image.getHeight()/2*/);
        // Sauvegarder l'état actuel de la transformation
        gc.save();

        // Appliquer la rotation
        gc.rotate(angle);

        //Calcul de la position de l'image en polaire
        double alpha = Math.atan(posY/-posX);
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

    public void testCommunication()
    {
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            for(int idx2 = idx+1;idx2<NbrAgent;idx2++)
            {
                if(Math.hypot(agents[idx].getPosition()[0]-agents[idx2].getPosition()[0],agents[idx].getPosition()[1]-agents[idx2].getPosition()[1]) <= LectureConfig.agentsCommunicationRange)
                {
                    if(agents[idx].targetFound)
                    {
                        agents[idx2].isGoingToTarget = true;
                    } else if (agents[idx2].targetFound)
                    {
                        agents[idx].isGoingToTarget = true;
                    }
                }
            }
        }
    }
}
