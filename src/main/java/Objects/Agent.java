package Objects;

import LectureConfig.LectureConfig;
import javafx.scene.image.Image;

public class Agent extends ObjectScheme
{
    public double agentsDetectionRange;
    public Agent(float positionX,float positionY,Image image)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radiusCommunication = LectureConfig.agentsCommunicationRange;
        this.image = image;
        this.agentsDetectionRange = LectureConfig.agentsDetectionRange;
        this.velocityMagnitude = LectureConfig.agentSpeed;
        this.direction = new float[]{1,1};
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
        return (float) Math.toDegrees(Math.atan(this.direction[1]/this.direction[0]));
    }
}
