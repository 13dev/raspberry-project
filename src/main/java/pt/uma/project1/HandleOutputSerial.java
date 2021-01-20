package pt.uma.project1;

import pt.uma.project1.serial.OutputSerialHandler;

public class HandleOutputSerial extends OutputSerialHandler {
    @Override
    protected void handle(String line) {
        System.out.println(line);
    }
}
