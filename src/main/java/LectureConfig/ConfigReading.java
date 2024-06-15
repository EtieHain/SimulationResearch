package LectureConfig;

import javafx.scene.control.Alert;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ConfigReading
{
    public static int[] dimensionCaneva = new int[]{800,800};
    public static float[] posCible = new float[]{400f,400f};
    public static float agentSpeed = 5f;
    public static float agentsDetectionRange = 50f;
    public static float agentsCommunicationRange = 50f;

    /**
     * Methode de lecture du fichier de configuration
     *
     * @param file the config file that you want to read
     */
    static public void ConfigReading(File file)
    {
        //Déclaration du fichier a lire
//        File file = new File("src/main/resources/configuration.txt");

        //Création d'une array liste qui va stocker chaque ligne
        ArrayList<String> values = new ArrayList<String>();
        try {
            //Création d'un nouveau filereader
            Scanner filereader = new Scanner(file);

            //Initialisation des séparateur des lignes
            filereader.useDelimiter("\r\n");

            //Lecture du fichier tant qu'il a du contenu
            while (filereader.hasNextLine())
            {
                values.add(filereader.next());
            }
            filereader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if(values.size() == 0)
        {
            // Afficher une alerte ou mettre à jour l'interface utilisateur après une erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Invalid or empty File ! \n\nDefault parameters applied");
            alert.showAndWait();
        }
        //Pour chaque ligne de l'array list
        for(int idx = 0;idx < values.size();idx++)
        {
            //Décomposition de la ligne en mots
            String[] data = values.get(idx).split(" ");

            //Attibution de la valeur à la bonne variable
            switch (data[0])
            {
                case "environment_size_W_H":
                    int x = Integer.parseInt(data[1]);
                    int y = Integer.parseInt(data[2]);
//                    Random rand = new Random();
//                    x = rand.nextInt(500+1)+200;
//                    y = rand.nextInt(500+1)+200;
                    dimensionCaneva = new int[]{x,y};
                    break;
                case "target_position_x_y":
                    float x1 = Float.parseFloat(data[1]);
                    float y1 = Float.parseFloat(data[2]);
//                    Random rand2 = new Random();
                    //x1 = rand2.nextInt(dimensionCaneva[0]+1);
                    //y1 = rand2.nextInt(dimensionCaneva[1]+1);
                    posCible= new float[]{x1,y1};
                    break;
                case "agents_speed":
                    agentSpeed = Float.parseFloat(data[1]);
                    break;
                case "agents_detection_range":
                    agentsDetectionRange = Float.parseFloat(data[1]);
                    break;
                case "agents_communication_range":
                    agentsCommunicationRange = Float.parseFloat(data[1]);
                    break;
            }
        }
    }
}
