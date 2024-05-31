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
    static public int nbrAgentAvertis = 0;
    static public int NbrAgent;
    static public Agent[] agents;
    static public Cible cible;
    //Algo de calcul des position en fonctione de la fenetre et des parametres de l'agent
    static int winWidth;
    static int winHeight;
    static public int N;
    //déclaration du tableau de position
    //N cellule avec 2 coordonnée (x,y)
    public static float[][] posTab;

    public static Image bg = new Image("bg.png",Math.max(LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1]),Math.max(LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1]),true,false);

    static public void creationObjects(int NbrAgents)
    {
        Image ship = new Image( "ship.png",40,40,false,false);
        Image target = new Image("target.png",40,40,false,false);
        winWidth = (int) (LectureConfig.dimensionCaneva[0]-(ship.getWidth()));
        winHeight = (int) (LectureConfig.dimensionCaneva[1]-(ship.getHeight()));
        //x : nombre de position minimale sur la moitié d'une arrete
        //arrondie au dessus en cas de division pas entière
        float x = (float) Math.ceil((double) LectureConfig.dimensionCaneva[0] /2/(2*Math.sqrt(Math.pow(LectureConfig.agentsDetectionRange,2)-Math.pow(ship.getWidth()/2,2))));
        float y = (float) Math.ceil((double) LectureConfig.dimensionCaneva[1] /2/(2*Math.sqrt(Math.pow(LectureConfig.agentsDetectionRange,2)-Math.pow(ship.getHeight()/2,2))));
        //l : distance minimale entre les positions afin de tout couvrir
        float w = ((float) winWidth /2)/x;
        float h = ((float) winHeight /2)/y;
        //N : nombre totale de positions
        // 2 moitié sur 4 arrete et *2 pour les positions intérieures -> 2*4*2=16
        N = (int) (2*(4*x+4*y));
        posTab = new float[N][2];
        float r = (float) (Math.min(LectureConfig.agentsDetectionRange,LectureConfig.agentsCommunicationRange)*0.95);
        int ox = (int) (ship.getWidth()/2);
        int oy = (int) (ship.getHeight()/2);
        //boucle de calcul des coordonnée des position
        for(int i =0;i<N;i+=2){
            //algo différent en fonction de l'arrête
            if (i>=0&&i<4*x) {
                //calcul des coordonnée des position extérieure
                posTab[i+1][0] = winWidth-(i/2)*w+ox;
                posTab[i+1][1] = winHeight+ox;
                //Calcul des positions intérieures en fonction des position intérieure
                //position allignée à la position extérieures par rapport au centre
                posTab[i][0]=ox+winWidth/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][0]-ox-winWidth/2);
                posTab[i][1]=oy+winHeight/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][1]-oy-winHeight/2);
            } else if (i>=4*x&&i<4*(x+y)) {
                posTab[i+1][0] = ox;
                posTab[i+1][1] = winHeight-((i-4*x)/2)*h+oy;
                posTab[i][0]=ox+winWidth/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][0]-ox-winWidth/2);
                posTab[i][1]=oy+winHeight/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][1]-oy-winHeight/2);
            } else if (i>=4*(x+y)&&i<4*(2*x+y)) {
                posTab[i+1][0] = ((i-N/2)/2)*w+ox;
                posTab[i+1][1] = oy;
                posTab[i][0]=ox+winWidth/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][0]-ox-winWidth/2);
                posTab[i][1]=oy+winHeight/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][1]-oy-winHeight/2);
            } else if (i>=4*(2*x+y)&&i<N) {
                posTab[i+1][0] = winWidth+ox;
                posTab[i+1][1] = (float)((i-(N/2)-4*x)/2)*h+oy;
                posTab[i][0]=ox+winWidth/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][0]-ox-winWidth/2);
                posTab[i][1]=oy+winHeight/2 + (r/((float) Math.hypot((GestionObjects.posTab[i+1][0]-ox-winWidth/2),(GestionObjects.posTab[i+1][1]-ox-winHeight/2))))*(GestionObjects.posTab[i+1][1]-oy-winHeight/2);
            }
        }
        //Stock dans la classe le nombre d'agents
        NbrAgent = NbrAgents;

        //Création d'un tableau temporaire d'agents
        Agent[] temp = new Agent[NbrAgents];

        //calcul de l'intervalle de position dans le tableau entre les agent
        //ex : 4 agent 16 position -> 1 agent toute les 4 positions
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
        gc.drawImage(bg,0,0);

        //Affichage de la cible
        gc.drawImage(cible.getImage(),cible.getPosition()[0]-cible.getImage().getWidth()/2,cible.getPosition()[1]-cible.getImage().getHeight()/2);

        //Affichage de agents
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            if(agents[idx].isRotating)
            {
                int rotationSpeed = 25;
                if(agents[idx].oldAngle > agents[idx].newAngle)
                {
                    agents[idx].oldAngle = agents[idx].oldAngle -rotationSpeed;
                    if(agents[idx].oldAngle < agents[idx].newAngle)
                    {
                        agents[idx].isRotating = false;
                    }
                }
                else
                {
                    agents[idx].oldAngle = agents[idx].oldAngle +rotationSpeed;
                    if(agents[idx].oldAngle > agents[idx].newAngle)
                    {
                        agents[idx].isRotating = false;
                    }
                }
                drawImage(gc,agents[idx].getImage(),agents[idx].oldAngle,agents[idx].getPosition()[0],agents[idx].getPosition()[1]);
            }
            else
            {
                drawImage(gc,agents[idx].getImage(),agents[idx].getAngle(),agents[idx].getPosition()[0],agents[idx].getPosition()[1]);
            }
        }
    }
    static private void drawImage(GraphicsContext gc, Image image, double angle,float positionX,float positionY) {

        //Acquisition de la position de l'image
        float posX = (float) (positionX);
        float posY = (float) (positionY);
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

    public static void testCommunication(int founderIndex)
    {
        for(int idx = 0;idx < NbrAgent;idx++)
        {
            if(agents[founderIndex].isCommunication(GestionObjects.agents[idx])&&!GestionObjects.agents[idx].getState()[0]&&!GestionObjects.agents[idx].getState()[1]){
                agents[idx].isGoingToTarget=true;

                agents[idx].isRotating=true;
                agents[idx].oldAngle=agents[idx].getAngle();
                agents[idx].setDirection((float) ((LectureConfig.posCible[0] - agents[idx].getPosition()[0]) / Math.hypot(LectureConfig.posCible[0] - agents[idx].getPosition()[0], LectureConfig.posCible[1] - agents[idx].getPosition()[1])), (float) ((LectureConfig.posCible[1] - agents[idx].getPosition()[1]) / Math.hypot(LectureConfig.posCible[0] - agents[idx].getPosition()[0], LectureConfig.posCible[1] - agents[idx].getPosition()[1])));
                agents[idx].newAngle=agents[idx].getAngle();

                nbrAgentAvertis++;
                if(nbrAgentAvertis>=NbrAgent/2){
                    agents[founderIndex].isRotating=true;
                    agents[founderIndex].oldAngle=agents[founderIndex].getAngle();
                    agents[founderIndex].setDirection((float) ((LectureConfig.posCible[0] - agents[founderIndex].getPosition()[0]) / Math.hypot(LectureConfig.posCible[0] - agents[founderIndex].getPosition()[0], LectureConfig.posCible[1] - agents[founderIndex].getPosition()[1])), (float) ((LectureConfig.posCible[1] - agents[founderIndex].getPosition()[1]) / Math.hypot(LectureConfig.posCible[0] - agents[founderIndex].getPosition()[0], LectureConfig.posCible[1] - agents[founderIndex].getPosition()[1])));
                    agents[founderIndex].newAngle=agents[founderIndex].getAngle();

                    agents[founderIndex].isGoingToTarget=true;
                    agents[founderIndex].targetFound=false;
                }
            }
        }
    }
}
