package Objects;

import javafx.scene.image.Image;

public class Agent extends ObjectScheme
{
    public Agent(float positionX,float positionY,float radiusCommunication,Image image)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radiusCommunication = radiusCommunication;
        this.image = image;
    }

    @Override
    public void changeImage(Image image) {

    }

    @Override
    public void changePosition(float positionX, float positionY) {

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
}
