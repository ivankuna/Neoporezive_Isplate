import java.io.IOException;

import static Servis.FileUtils.readTxt;

public class provjera {
    public static void main(String[] args) {
        String[] testTemp;
        {
            try {
                testTemp = readTxt("dat/connection_type.txt", 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < testTemp.length; i++) {
            System.out.println(testTemp[i]);
        }
    }
}
