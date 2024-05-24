package Objects;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import javafx.scene.image.Image;

public class Cible extends ObjectScheme
{
    public Cible(float positionX,float positionY,Image image)
    {
        this.positionY = positionY;
        this.positionX = positionX;
        this.image = image;
    }

    @Override
    public void changeImage(Image image)
    {
        this.image = image;
    }

    @Override
    public void changePosition(float positionX, float positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void changeRadiusCommunication(float radius)
    {
        this.radiusCommunication = radius;
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
