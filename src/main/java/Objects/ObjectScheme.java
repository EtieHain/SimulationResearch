import javafx.scene.image.Image;

/**
 * Abstract class representing the different object that you should implement (agent and target).
 */
public abstract class ObjectScheme {

    // Variables
    protected float positionX, positionY;
    protected float radiusCommunication;
    protected float[] direction;
    protected float velocityMagnitude;
    protected Image image;

    // Set methods
    /**
     * Change the object image.
     * @param image The new image to load
     */
    public abstract void changeImage(Image image);

    /**
     * Change the object position (x,y).
     * @param positionX The new position in the x-axis
     * @param positionY The new position in the y-axis
     */
    public abstract void changePosition(float positionX, float positionY);

    /**
     * Change the communication radius value.
     * @param radius The new radius value
     */
    public abstract void changeRadiusCommunication(float radius);

    // Get methods
    /**
     * This method returns the object position.
     * @return A float array containing the position {x,y}
     */
    public abstract float[] getPosition();

    /**
     * This method returns the object communication radius.
     * @return A float containing the radius
     */
    public abstract float radiusCommunication();

    /**
     * This method returns the variable containing the image of the object.
     * @return The variable containing the image of the object
     */
    public abstract Image getImage();

    // Other method
    /**
     * This method checks the distance between the current object (this) and another object.
     * If the distance is less than the communication radius, then the method returns true, indicating
     * that the objects can communicate. Otherwise, it returns false, meaning no communication is possible.
     * Your must use this method before attempting to exchange information between objects.
     *
     * @param object The other ObjectScheme
     *
     * @return The possibility to communicate with the object (true or false)
     */
    public abstract boolean isCommunication(ObjectScheme object);
}
