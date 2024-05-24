package Objects;

import LectureConfig.LectureConfig;
import javafx.scene.image.Image;

public class Agent extends ObjectScheme
{
    public double agentsDetectionRange;
    private float angle;
    public Agent(float positionX,float positionY,Image image)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radiusCommunication = LectureConfig.agentsCommunicationRange;
        this.image = image;
        this.agentsDetectionRange = LectureConfig.agentsDetectionRange;
        this.velocityMagnitude = LectureConfig.agentSpeed;
        this.direction = new float[]{1,-1};
        this.angle = 90;
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

    public void Deplacement()
    {
        float newPosX = positionX + velocityMagnitude*direction[0];
        if(newPosX > LectureConfig.dimensionCaneva[1] - image.getWidth() || newPosX < 0)
        {
            direction[0] = -direction[0];
        }
        float newPosY = positionY + velocityMagnitude*direction[1];
        if(newPosY > LectureConfig.dimensionCaneva[0] - image.getHeight()|| newPosY < 0)
        {
            direction[1] = -direction[1];
        }
        changePosition(newPosX,newPosY);
        System.out.println(getAngle());
    }
}

