package Objects;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.scene.image.Image;

import static com.example.simulationresearch.InterfaceController.*;

/**
 * Classe representing the agent
 */
public class Agent extends ObjectScheme
{
    public boolean isGoingToTarget;
    public double agentsDetectionRange;
    public int step;
    public boolean targetFound;
    public float newAngle;
    public float oldAngle;
    public boolean isRotating;
    public int deplacementBackward;
    public boolean isGoingBackward;

    /**
     * Constructor of the Agent class
     *
     * @param positionX x coordinates of the agent
     * @param positionY y coordinates of the agent
     * @param Step position of the agent in the list of position
     * @param image default image of the agent
     * @param stopImg stopped image of the agent
     */
    public Agent(float positionX,float positionY,int Step,Image image,Image stopImg)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radiusCommunication = ConfigReading.agentsCommunicationRange;
        this.image = image;
        this.agentsDetectionRange = ConfigReading.agentsDetectionRange;
        this.velocityMagnitude = ConfigReading.agentSpeed;
        this.step = Step;
        this.direction = new float[]{0,0};
        this.targetFound=false;
        this.isGoingToTarget=false;
        this.isRotating=false;
        this.deplacementBackward = 0;
        this.isGoingBackward = false;
//        this.stopImg =stopImg;
    }

    /**
     * change agent image
     *
     * @param image The new image to load
     */
    @Override
    public void changeImage(Image image, Image imageStop) {
        this.image=image;
        activeTheme[1] = imageStop;
    }

    /**
     * change agent position
     *
     * @param positionX The new position in the x-axis
     * @param positionY The new position in the y-axis
     */
    @Override
    public void changePosition(float positionX, float positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * change communication radius (unused)
     *
     * @param radius The new radius value
     */
    @Override
    public void changeRadiusCommunication(float radius) {
        this.radiusCommunication=radius;
    }

    /**
     * This method return the position of the agent
     *
     * @return the agent coordinates in an Array
     */
    @Override
    public float[] getPosition()
    {
        return new float[]{positionX,positionY};
    }

    /**
     * This method returns the communication radius
     *
     * @return the agent communication radius
     */
    @Override
    public float radiusCommunication() {
        return this.radiusCommunication;
    }

    /**
     * This Method returns the image of the target
     *
     * @return the agent image
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * This method checks if the communication between the object and another Object Scheme object is possible (unused)
     * depending of the distance with this object
     *
     * @param object The other ObjectScheme
     *
     * @return true if the other object is close enough
     */
    @Override
    public boolean isCommunication(ObjectScheme object) {
        return Math.hypot(this.positionX - object.getPosition()[0], this.positionY - object.getPosition()[1]) <= this.radiusCommunication;
    }

    /**
     * This method calculate and return the agent angle
     *
     * @return the agent angle
     */
    public float getAngle()
    {
        float angle;
        angle = (float) Math.toDegrees(Math.atan2(direction[1],direction[0]));
        return angle + 90;
    }

    /**
     * This method calculate the agent movement depending on his state
     * (research, target found, go to target)
     */
    public void Deplacement() {
        if(!this.Collision() && !this.isGoingBackward) //si l'agent ne detecte pas de colision et qu'il ne va pas en arrière
        {
            //Si l'agent n'a pas trouvé la cible, qu'il ne va pas en direction de la cible et qu'il ne tourne pas
            if(!this.targetFound && !this.isGoingToTarget && !this.isRotating)
            {
                //récup de la step
                int step = this.step;
                //récup de l'objectif
                int NP = step + 1;
                //check si fin de tableau
                if (NP == GestionObjects.N) NP = 0;
                //changer la position
                this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
                //si position atteinte
                if (Math.hypot(this.getPosition()[0] - GestionObjects.posTab[step][0], this.getPosition()[1] - GestionObjects.posTab[step][1]) > Math.hypot(GestionObjects.posTab[NP][0] - GestionObjects.posTab[step][0], GestionObjects.posTab[NP][1] - GestionObjects.posTab[step][1])) {
                    //incrémenter la step et check si fin de tableau
                    if (step == GestionObjects.N - 1) {
                        this.setStep(0);
                    } else {
                        this.setStep(step + 1);
                    }
                    //engagement de la rotation
                    this.isRotating=true;
                    //stock de l'angle de départ
                    this.oldAngle=this.getAngle();
                    //récup de la nouvelle step
                    step = this.step;
                    //calcul prochain objectif
                    NP = step + 1;
                    //check si fin de tableau
                    if (NP == GestionObjects.N) NP = 0;
                    //ajustement la position au pixel près
                    this.changePosition(GestionObjects.posTab[step][0], GestionObjects.posTab[step][1]);
                    //calcul du nouveau vecteur de direction
                    this.setDirection((float) ((GestionObjects.posTab[NP][0] - GestionObjects.posTab[step][0]) / (Math.hypot((GestionObjects.posTab[step][0] - GestionObjects.posTab[NP][0]), (GestionObjects.posTab[NP][1] - GestionObjects.posTab[step][1])))), (float) ((GestionObjects.posTab[NP][1] - GestionObjects.posTab[step][1]) / (Math.hypot((GestionObjects.posTab[step][0] - GestionObjects.posTab[NP][0]), (GestionObjects.posTab[step][1] - GestionObjects.posTab[NP][1])))));
                    //stock du nouvel angle
                    this.newAngle=this.getAngle();
                }
            }
            //Si l'agent a trouvé la cible ne va pas en direction de la cible et qu'il ne tourne pas
            else if(this.targetFound && !this.isGoingToTarget && !this.isRotating)
            {
                //Si l'agent a atteint le centre du canvas
                if (Math.hypot(ConfigReading.dimensionCaneva[0] / 2 - this.positionX, ConfigReading.dimensionCaneva[1] / 2 - this.positionY) < ConfigReading.agentSpeed)
                {
                    //Set la position de l'agent au centre du canvas
                    this.changePosition(ConfigReading.dimensionCaneva[0]/2, ConfigReading.dimensionCaneva[1]/2);
                }
                else
                {
                    //Déplace l'agent en direction du centre du canvas
                    this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
                }
            }
            //Si l'agent n'a pas trouvé la cible et qu'il ne tourne pas
            else if(!this.targetFound && !this.isRotating)
            {
                //Change la position de l'agent en fonction de sa vitesse et de sa direction
                this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
            }
        }
        else //Si l'agent a détecté une colision et ou qu'il ne va pas en arrière
        {
            //Test si l'agent va en arrière
            if(isGoingBackward)
            {
                //Changment de la position de l'agent
                this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
                //Décrémente le nombre de coup que l'agent doit encore aller en arrière
                this.deplacementBackward--;
                //Si l'agent ne doit plus aller en arrière
                if(deplacementBackward == 0 || this.targetFound)
                {
                    //Remet la direction pour que l'agent aille en avant
                    this.isGoingBackward = false;
                    this.direction[0] = -this.direction[0];
                    this.direction[1] = -this.direction[1];
                }
            }
        }
    }

    /**
     * this method returns the agent direction vector
     *
     * @return the agent direction in an array
     */
    public float[] getDirection(){
        return new float[]{this.direction[0], this.direction[1]};
    }

    /**
     * change the agent direction
     *
     * @param dirX the new direction component in x
     * @param dirY the new direction component in y
     */
    public void setDirection(float dirX, float dirY)
    {
        this.direction[0] = dirX;
        this.direction[1] = dirY;
    }

    /**
     * change the agent step in the position list
     *
     * @param step the new position step
     */
    public void setStep(int step){
        this.step=step;
    }

    /**
     * this method detect the target if it is close enough
     */
    public void targetDetection()
    {
        Target target = GestionObjects.target;
        //Test si la distance entre la cible et l'agent est plus petite que la detection range
        if(Math.hypot(this.positionX-target.getPosition()[0],this.positionY-target.getPosition()[1])<=this.agentsDetectionRange){
            this.targetFound=true;
            if(!this.isGoingToTarget)
            {
                //Changement de direction pour aller au centre du canvas et mis en rotation de l'agent
                this.isRotating=true;
                this.oldAngle=this.getAngle();
                this.setDirection((float) ((ConfigReading.dimensionCaneva[0] / 2 - this.positionX) / Math.hypot(ConfigReading.dimensionCaneva[0] / 2 - this.positionX, ConfigReading.dimensionCaneva[1] / 2 - this.positionY)), (float) ((ConfigReading.dimensionCaneva[1] / 2 - this.positionY) / Math.hypot(ConfigReading.dimensionCaneva[0] / 2 - this.positionX, ConfigReading.dimensionCaneva[1] / 2 - this.positionY)));
                this.newAngle=this.getAngle();
            }
            else
            {
                //Changement d'image de l'agent
                this.image = activeTheme[1];
                //Calculs pour faire tourner l'image autour de la cible
                double w = ConfigReading.agentSpeed / 100;
                float X = this.getPosition()[0] - ConfigReading.posCible[0];
                float Y = this.getPosition()[1] - ConfigReading.posCible[1];
                float fi = (float) Math.atan2(Y,X);
                float module = (float) Math.hypot(X,Y);
                this.setDirection((float) (-module*Math.cos(fi+w)), (float) (-module*Math.sin(fi+w)));
                this.changePosition((float) (module*Math.cos(fi+w))+ConfigReading.posCible[0], (float) (module*Math.sin(fi+w))+ConfigReading.posCible[1]);
            }
        }
    }

    /**
     * this method return the state of the agent
     *
     * @return the two state variable of the agent in an array
     * +
     */
    public boolean[] getState(){
        return new boolean[]{this.targetFound,this.isGoingToTarget};
    }

    /**
     * this method checks if it's colliding if another agent.
     *
     * @return if the agent is colliding with another agent
     */
    public  boolean Collision()
    {
        //Boucle pour passer dans tout le tableau agent
        for(int idx = 0;idx < GestionObjects.NbrAgent;idx++)
        {
            if(this != GestionObjects.agents[idx]) //Test pour pas que l'agent test les collision avec lui même
            {
                //Calcul de la nouvelle position de l'agent
                float tempX =(this.positionX + this.direction[0]*ConfigReading.agentSpeed);
                float tempY =(this.positionY + this.direction[1]*ConfigReading.agentSpeed);

                //Calcul de la distance entre les deux agents
                float deltaX = GestionObjects.agents[idx].positionX - tempX;
                float deltaY = GestionObjects.agents[idx].positionY - tempY;
                double distance = Math.hypot(deltaX,deltaY);

                if(distance < this.image.getHeight())//Test si la distance est plus petite que la grandeur de l'image
                {
                    if(!this.isGoingBackward && !this.targetFound)//Test si l'agent va en arrière
                    {
                        //Inversion de la direction et set l'état isGoingBackward a true
                        this.isGoingBackward = true;
                        this.deplacementBackward = 5;
                        this.direction[0] = -this.direction[0];
                        this.direction[1] = -this.direction[1];
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

