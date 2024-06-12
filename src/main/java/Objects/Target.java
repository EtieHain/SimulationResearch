package Objects;

import javafx.scene.image.Image;

/**
 * Classe représentant la cible
 */
public class Target extends ObjectScheme
{
    /**
     * Constructeur de la classe Target
     *
     * @param positionX position en x de la cible
     * @param positionY position en y de la cible
     * @param image image de la cible
     */
    public Target(float positionX, float positionY, Image image)
    {
        this.positionY = positionY;
        this.positionX = positionX;
        this.image = image;
    }

    /**
     * Methode de changement de l'image de la cible
     *
     * @param image The new image to load
     */
    @Override
    public void changeImage(Image image, Image imageStop)
    {
        this.image = image;
    }

    /**
     * Methode de changement de la position de la cible
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
    public void changeRadiusCommunication(float radius)
    {
        this.radiusCommunication = radius;
    }

    /**
     * Methode de récupération de la position de la cible
     *
     * @return the target coordinates in an Array
     */
    @Override
    public float[] getPosition()
    {
        return new float[]{positionX,positionY};
    }

    /**
     * Methode de récupération du rayon de communication (pas utilisée)
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
     * Methode de vérification de la possible communication avec un autre Objet de la classe Object Scheme (pas utilisée)
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
