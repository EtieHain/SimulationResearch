package Objects;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.scene.image.Image;

import static com.example.simulationresearch.InterfaceController.*;

/**
 * Classe représentant les agents
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
    //public Image stopImg;

    /**
     * Constructeur de la classe Agent
     *
     * @param positionX position en x de l'agent
     * @param positionY position en y de l'agent
     * @param Step étape de l'agent dans la liste de position
     * @param image image par défaut de l'agent
     * @param stopImg image de l'agent lorsque la clibe est trouvée
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
        //this.stopImg =stopImg;
    }

    /**
     * Methode de changement de l'image de l'agent
     *
     * @param image The new image to load
     */
    @Override
    public void changeImage(Image image, Image imageStop) {
        this.image=image;
        stopImg = imageStop;
    }

    /**
     * Methode de changement de la position de l'agent
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
     * Methode de changement du rayon de communication (pas utilisée)
     *
     * @param radius The new radius value
     */
    @Override
    public void changeRadiusCommunication(float radius) {
        this.radiusCommunication=radius;
    }

    /**
     * Methode de récupération de la position de l'agent
     *
     * @return the agent coordinates in an Array
     */
    @Override
    public float[] getPosition()
    {
        return new float[]{positionX,positionY};
    }

    /**
     * Methode de récupération du rayon de communication
     *
     * @return the agent communication radius
     */
    @Override
    public float radiusCommunication() {
        return this.radiusCommunication;
    }

    /**
     * Methode de récupération de l'image de la cible
     *
     * @return the agent image
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * Methode de vérification de la possible communication avec un autre Objet de la classe Object Scheme
     * en fonction de la distance avec cet objet
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
     * Methode de calcul de l'angle en fonction de sa direction
     *
     * @return the agent angle
     */
    public float getAngle()
    {
        float temp;
        temp = (float) Math.toDegrees(Math.atan(direction[1]/direction[0]));
        if(direction[0] < 0)
        {
            temp -= 180;
        }
        return temp + 90;
    }

    /**
     * Methode de calcul du déplacement de l'agent en fonction de son état
     * (recherche, cible trouvée, aller à la cible)
     */
    public void Deplacement() {
        if(!this.Collision() && !this.isGoingBackward)
        {

            if(!this.targetFound && !this.isGoingToTarget && !this.isRotating) {
                int step = this.step;
                int NP = step + 1;
                if (NP == GestionObjects.N) NP = 0;
                this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
                if (Math.hypot(this.getPosition()[0] - GestionObjects.posTab[step][0], this.getPosition()[1] - GestionObjects.posTab[step][1]) > Math.hypot(GestionObjects.posTab[NP][0] - GestionObjects.posTab[step][0], GestionObjects.posTab[NP][1] - GestionObjects.posTab[step][1])) {
                    if (step == GestionObjects.N - 1) {
                        this.setStep(0);
                    } else {
                        this.setStep(step + 1);
                    }
                    this.isRotating=true;
                    this.oldAngle=this.getAngle();
                    step = this.step;
                    NP = step + 1;
                    if (NP == GestionObjects.N) NP = 0;
                    this.changePosition(GestionObjects.posTab[step][0], GestionObjects.posTab[step][1]);
                    this.setDirection((float) ((GestionObjects.posTab[NP][0] - GestionObjects.posTab[step][0]) / (Math.hypot((GestionObjects.posTab[step][0] - GestionObjects.posTab[NP][0]), (GestionObjects.posTab[NP][1] - GestionObjects.posTab[step][1])))), (float) ((GestionObjects.posTab[NP][1] - GestionObjects.posTab[step][1]) / (Math.hypot((GestionObjects.posTab[step][0] - GestionObjects.posTab[NP][0]), (GestionObjects.posTab[step][1] - GestionObjects.posTab[NP][1])))));
                    this.newAngle=this.getAngle();
                }
            }else if(this.targetFound && !this.isGoingToTarget && !this.isRotating){
                if (Math.hypot(ConfigReading.dimensionCaneva[0] / 2 - this.positionX, ConfigReading.dimensionCaneva[1] / 2 - this.positionY) < ConfigReading.agentSpeed) {
                    this.changePosition(ConfigReading.dimensionCaneva[0]/2, ConfigReading.dimensionCaneva[1]/2);
                } else {
                    this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
                }
            }
            else if(!this.targetFound && !this.isRotating){
                this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
            }
        }
        else
        {
            if(isGoingBackward)
            {
                this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
                this.deplacementBackward--;
                if(deplacementBackward == 0)
                {
                    this.isGoingBackward = false;
                    this.direction[0] = -this.direction[0];
                    this.direction[1] = -this.direction[1];
                }
            }
        }
    }

    /**
     * Methode de récupération de la direction de l'agent
     *
     * @return the agent direction in an array
     */
    public float[] getDirection(){
        return new float[]{this.direction[0], this.direction[1]};
    }

    /**
     * Methode de changement de la direction de l'agent
     *
     * @param dirX the new direction component in x
     * @param dirY the new direction component in y
     */
    public void setDirection(float dirX, float dirY){
        this.direction[0] = dirX;
        this.direction[1] = dirY;
    }

    /**
     * Methode de changement de l'étape de l'agent dans le tableau de position
     *
     * @param step the new position step
     */
    public void setStep(int step){
        this.step=step;
    }

    /**
     * Methode de détéction de la cible en fonction de sa distance avec l'agent
     */
    public void targetDetection(){
        Target target = GestionObjects.target;
        if(Math.hypot(this.positionX-target.getPosition()[0],this.positionY-target.getPosition()[1])<=this.agentsDetectionRange){
            this.targetFound=true;
            if(!this.isGoingToTarget){
                this.isRotating=true;
                this.oldAngle=this.getAngle();
                this.setDirection((float) ((ConfigReading.dimensionCaneva[0] / 2 - this.positionX) / Math.hypot(ConfigReading.dimensionCaneva[0] / 2 - this.positionX, ConfigReading.dimensionCaneva[1] / 2 - this.positionY)), (float) ((ConfigReading.dimensionCaneva[1] / 2 - this.positionY) / Math.hypot(ConfigReading.dimensionCaneva[0] / 2 - this.positionX, ConfigReading.dimensionCaneva[1] / 2 - this.positionY)));
                this.newAngle=this.getAngle();
            }else
            {
                this.image = stopImg;
                double w = ConfigReading.agentSpeed / 100;
                float X = this.getPosition()[0] - ConfigReading.posCible[0];
                float Y = this.getPosition()[1] - ConfigReading.posCible[1];
                float fi = (float) Math.atan(Y/X);
                float module = (float) Math.hypot(X,Y);
                if(X < 0)
                {
                    fi -= Math.PI;
                }
                this.setDirection((float) (-module*Math.cos(fi+w)), (float) (-module*Math.sin(fi+w)));
                this.changePosition((float) (module*Math.cos(fi+w))+ConfigReading.posCible[0], (float) (module*Math.sin(fi+w))+ConfigReading.posCible[1]);
            }
        }
    }

    /**
     * Methode de récupération de l'étape de l'agent dans le tableau de position
     *
     * @return the two state variable of the agent in an array
     * +
     */
    public boolean[] getState(){
        return new boolean[]{this.targetFound,this.isGoingToTarget};
    }

    public  boolean Collision()
    {
        for(int idx = 0;idx < GestionObjects.NbrAgent;idx++)
        {
            float tempX =(this.positionX + this.direction[0]*ConfigReading.agentSpeed);
            float tempY =(this.positionY + this.direction[1]*ConfigReading.agentSpeed);
            if(this != GestionObjects.agents[idx])
            {
                float deltaX = GestionObjects.agents[idx].positionX - tempX;
                float deltaY = GestionObjects.agents[idx].positionY - tempY;
                double distance = Math.hypot(deltaX,deltaY);
                if(distance < this.image.getHeight())
                {
                    if(!this.isGoingBackward)
                    {
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

