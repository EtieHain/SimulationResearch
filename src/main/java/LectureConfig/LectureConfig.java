package LectureConfig;

import com.example.simulationresearch.HelloApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LectureConfig
{
    public static int[] dimensionCaneva;
    public static float[] posCible;
    public static float agentSpeed;
    public static float agentsDetectionRange;
    public static float agentsCommunicationRange;
    static public void LectureFichier()
    {
        File file = new File("src/main/resources/configuration.txt"); // e.g., "src/winequality-red.csv";
        ArrayList<String> values = new ArrayList<String>();
        try {
            // Create an object of filereader
            // class with CSV file as a parameter.
            Scanner filereader = new Scanner(file);
            filereader.useDelimiter("\r\n");
            // change the delimiter according to your file
            while (filereader.hasNextLine())
            {
                values.add(filereader.next());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        for(int idx = 0;idx < values.size();idx++)
        {
            String[] data = values.get(idx).split(" ");
            switch (data[0])
            {
                case "environment_size_W_H":
                    int x = Integer.parseInt(data[1]);
                    int y = Integer.parseInt(data[2]);
                    dimensionCaneva = new int[]{x,y};
                    break;
                case "target_position_x_y":
                    float x1 = Float.parseFloat(data[1]);
                    float y1 = Float.parseFloat(data[2]);
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
