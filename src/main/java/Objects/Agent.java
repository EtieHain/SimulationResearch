package Objects;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import com.example.simulationresearch.HelloApplication;
import javafx.scene.image.Image;

import java.nio.charset.MalformedInputException;

public class Agent extends ObjectScheme
{
    public double agentsDetectionRange;
    private float angle;
    public int step;
    public Agent(float positionX,float positionY,int Step,Image image)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radiusCommunication = LectureConfig.agentsCommunicationRange;
        this.image = image;
        this.agentsDetectionRange = LectureConfig.agentsDetectionRange;
        this.velocityMagnitude = LectureConfig.agentSpeed;
        this.angle = 90;
        this.step = Step;
        this.direction = new float[]{0,0};
    }

    @Override
    public void changeImage(Image image) {

    }

    @Override
    public void changePosition(float positionX, float positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void changeRadiusCommunication(float radius) {

    }

    @Override
    public float[] getPosition()
    {
        return new float[]{positionX,positionY};
    }

    @Override
    public float radiusCommunication() {
        return 0;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        return false;
    }

    public float getAngle()
    {
        float temp;
        temp = (float) Math.toDegrees(Math.atan(-direction[1]/direction[0]));
        if(direction[0] < 0)
        {
            temp -= 180;
        }
        return temp+180;
    }

    public void Deplacement() {
        for (int idx = 0;idx<GestionObjects.NbrAgent;idx++){
            int step = GestionObjects.agents[idx].step;
            int NP = step+1;
            if(NP==GestionObjects.N) NP=0;
            GestionObjects.agents[idx].changePosition(GestionObjects.agents[idx].getPosition()[0]+LectureConfig.agentSpeed*GestionObjects.agents[idx].getDirection()[0],GestionObjects.agents[idx].getPosition()[1]+LectureConfig.agentSpeed*GestionObjects.agents[idx].getDirection()[1]);
            if(Math.hypot(GestionObjects.agents[idx].getPosition()[0]-GestionObjects.posTab[step][0],GestionObjects.agents[idx].getPosition()[1]-GestionObjects.posTab[step][1])>Math.hypot(GestionObjects.posTab[NP][0]-GestionObjects.posTab[step][0],GestionObjects.posTab[NP][1]-GestionObjects.posTab[step][1])){
                if(step==GestionObjects.N-1){
                    GestionObjects.agents[idx].setStep(0);
                }else{
                    GestionObjects.agents[idx].setStep(step+1);
                }
                step = GestionObjects.agents[idx].step;
                NP = step+1;
                if(NP==GestionObjects.N) NP=0;
                GestionObjects.agents[idx].changePosition(GestionObjects.posTab[step][0], GestionObjects.posTab[step][1]);
                GestionObjects.agents[idx].setDirection((float) ((GestionObjects.posTab[NP][0]-GestionObjects.posTab[step][0])/(Math.hypot((GestionObjects.posTab[step][0]-GestionObjects.posTab[NP][0]),(GestionObjects.posTab[NP][1]-GestionObjects.posTab[step][1])))), (float) ((GestionObjects.posTab[NP][1]-GestionObjects.posTab[step][1])/(Math.hypot((GestionObjects.posTab[step][0]-GestionObjects.posTab[NP][0]),(GestionObjects.posTab[step][1]-GestionObjects.posTab[NP][1])))));
            }
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
}

