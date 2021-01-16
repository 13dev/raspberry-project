package pt.uma.project1;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    private SerialReader serialReader;


    void connect() throws Exception {
        System.setProperty("gnu.io.rxtx.SerialPorts", SerialReader.DEFAULT_PORT_ID);

        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(SerialReader.DEFAULT_PORT_ID);

        SerialPort serialPort = (SerialPort) portId.open(
                this.getClass().getName(),
                SerialReader.SERIAL_TIMEOUT
        );

        serialPort.setSerialPortParams(SerialPort.SERIAL_BPS, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.FLOWCONTROL_NONE);

        serialReader = new SerialReader(serialPort.getInputStream());

        new Thread(serialReader).start();

    }

    public static void main(String[] args) {
        try {
            new Main().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}