package pt.uma.project1.serial;

import gnu.io.*;
import sun.misc.Signal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialConnection implements SerialPortEventListener {

    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;

    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    /**
     * The port we're normally going to use.
     */
    private static final String[] PORT_NAMES = {
            "/dev/ttyACM0", // Raspberry Pi
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
    };

    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    private BufferedReader input;

    /**
     * The output stream to the port
     */
    private OutputStream output;

    SerialPort serialPort;

    public void initialize() {
        System.setProperty("gnu.io.rxtx.SerialPorts", PORT_NAMES[0]);

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }

        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }
        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(
                    this.getClass().getName(),
                    TIME_OUT
            );

            // set port parameters
            serialPort.setSerialPortParams(
                    DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE
            );

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Signal.handle(new Signal("INT"), signal -> close());
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();

                System.out.println(inputLine);
            } catch (Exception e) {
                close();
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

}
