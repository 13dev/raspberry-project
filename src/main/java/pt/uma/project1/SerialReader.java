package pt.uma.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SerialReader implements Runnable {
    public static final String DEFAULT_PORT_ID = "/dev/ttyACM0";
    public static final int SERIAL_TIMEOUT = 5000;
    public static final int SERIAL_BPS = 9600; // Bits per second on port serial

    InputStream inputStream;
    BufferedReader bufferedReader;

    public SerialReader(InputStream inputStream) {
        this.inputStream = inputStream;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void run() {
        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Read line with " + line.length() + " characters: \"" + line + "\"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
