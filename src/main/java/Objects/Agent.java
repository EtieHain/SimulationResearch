package Objects;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import com.example.simulationresearch.HelloApplication;
import javafx.scene.image.Image;

import java.nio.charset.MalformedInputException;

public class Agent extends ObjectScheme
{
    public boolean isGoingToTarget;
    public double agentsDetectionRange;
    public int step;
    public boolean targetFound;
    public float newAngle;
    public float oldAngle;
    public boolean isRotating;

    public Agent(float positionX,float positionY,int Step,Image image)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radiusCommunication = LectureConfig.agentsCommunicationRange;
        this.image = image;
        this.agentsDetectionRange = LectureConfig.agentsDetectionRange;
        this.velocityMagnitude = LectureConfig.agentSpeed;
        this.step = Step;
        this.direction = new float[]{0,0};
        this.targetFound=false;
        this.isGoingToTarget=false;
        this.isRotating=false;
    }

    @Override
    public void changeImage(Image image) {
        this.image=image;
    }

    @Override
    public void changePosition(float positionX, float positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void changeRadiusCommunication(float radius) {
        this.radiusCommunication=radius;
    }

    @Override
    public float[] getPosition()
    {
        return new float[]{positionX,positionY};
    }

    @Override
    public float radiusCommunication() {
        return this.radiusCommunication;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        return Math.hypot(this.positionX - object.getPosition()[0], this.positionY - object.getPosition()[1]) <= this.radiusCommunication;
    }

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

    public void Deplacement() {
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
            if (Math.hypot(LectureConfig.dimensionCaneva[0] / 2 - this.positionX, LectureConfig.dimensionCaneva[1] / 2 - this.positionY) < LectureConfig.agentSpeed) {
                this.changePosition(LectureConfig.dimensionCaneva[0]/2,LectureConfig.dimensionCaneva[1]/2);
            } else {
                this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
            }
        }
        else if(!this.targetFound && !this.isRotating){
            this.changePosition(this.getPosition()[0] + this.velocityMagnitude * this.getDirection()[0], this.getPosition()[1] + this.velocityMagnitude * this.getDirection()[1]);
        }
    }
    public float[] getDirection(){
        return new float[]{this.direction[0], this.direction[1]};
    }

    public void setDirection(float dirX, float dirY){
        this.direction[0] = dirX;
        this.direction[1] = dirY;
    }

    public void setStep(int step){
        this.step=step;
    }

    public void targetDetection(){
        Cible target = GestionObjects.cible;
        if(Math.hypot(this.positionX-target.getPosition()[0],this.positionY-target.getPosition()[1])<=this.agentsDetectionRange){
            this.targetFound=true;
            if(!this.isGoingToTarget){
                this.isRotating=true;
                this.oldAngle=this.getAngle();
                this.setDirection((float) ((LectureConfig.dimensionCaneva[0] / 2 - this.positionX) / Math.hypot(LectureConfig.dimensionCaneva[0] / 2 - this.positionX, LectureConfig.dimensionCaneva[1] / 2 - this.positionY)), (float) ((LectureConfig.dimensionCaneva[1] / 2 - this.positionY) / Math.hypot(LectureConfig.dimensionCaneva[0] / 2 - this.positionX, LectureConfig.dimensionCaneva[1] / 2 - this.positionY)));
                this.newAngle=this.getAngle();
            }
        }
    }
    public boolean[] getState(){
        return new boolean[]{this.targetFound,this.isGoingToTarget};
    }
}

