package pt.uma.project1;
import pt.uma.project1.serial.SerialConnection;

public class Main {

    public static void main(String[] args) {
        try {
            SerialConnection serialConnection = new SerialConnection();
            // Connect to Serial port
            serialConnection.initialize(
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}