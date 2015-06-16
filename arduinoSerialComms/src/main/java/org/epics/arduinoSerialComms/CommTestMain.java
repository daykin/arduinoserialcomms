package org.epics.arduinoSerialComms;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.TooManyListenersException;

import org.epics.arduinoSerialComms.ArduinoSerialComms;

public class CommTestMain {	
	public static void main(String[] args) throws NoSuchPortException, PortInUseException, 
	UnsupportedCommOperationException, IOException, TooManyListenersException, InterruptedException{
		ArduinoSerialComms uno = new ArduinoSerialComms();
		uno.setup("COM4",9600);
		while(true){
			Thread.sleep(1500);
			uno.writeText("1");
			Thread.sleep(1500);
			uno.writeText("0");
		}
	}	
}
