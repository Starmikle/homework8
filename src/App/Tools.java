package App;

import java.util.Random;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date: 05.08.2021
 */

public class Tools {

    public static Random random = new Random();

    public static int randomIntRange(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public static boolean randomAction() {
        return random.nextInt(100) < 10;
    }


}