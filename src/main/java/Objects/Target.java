package Objects;

import javafx.scene.image.Image;

/**
 * Class representing the target
 */
public class Target extends ObjectScheme
{
    /**
     * Constructor of the Target class
     *
     * @param positionX x coordinates of the target
     * @param positionY y coordinates of the target
     * @param image image of the target
     */
    public Target(float positionX, float positionY, Image image)
    {
        this.positionY = positionY;
        this.positionX = positionX;
        this.image = image;
    }

    /**
     * change target image
     *
     * @param image The new image to load
     */
    @Override
    public void changeImage(Image image, Image imageStop)
    {
        this.image = image;
    }

    /**
     * change target position
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
    public void changeRadiusCommunication(float radius)
    {
        this.radiusCommunication = radius;
    }

    /**
     * This method return the position of the target
     *
     * @return the target coordinates in an Array
     */
    @Override
    public float[] getPosition()
    {
        return new float[]{positionX,positionY};
    }

    /**
     * This method returns the communication radius (unused)
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
     *
     * @param object The other ObjectScheme
     *
     * @return
     */
    @Override
    public boolean isCommunication(ObjectScheme object) {
        return false;
    }
}
