package org.epics.arduinoSerialComms;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.TooManyListenersException;


public class ArduinoSerialComms implements SerialPortEventListener

{
	public SerialPort serialPort;
	public CommPortIdentifier com;
	
	public static BufferedReader input;
	public static OutputStream os;
	
    public void setup(String ComPort, int baudRate) throws NoSuchPortException, 
    PortInUseException, UnsupportedCommOperationException, 
    IOException, TooManyListenersException								//set up serial connection
    //TODO: overload for more/less defaults
    {
    	com = CommPortIdentifier.getPortIdentifier(ComPort);            
    	serialPort = (SerialPort) com.open("LED control sample", 5000); //ask the OS to give us the Serial Port (name and timeout in millis)
    	   	
    	serialPort.setSerialPortParams(
    			    baudRate,                    						//baud rate 
    			    SerialPort.DATABITS_8,       						//data bits
    			    SerialPort.STOPBITS_1,       						//stop bits
    			    SerialPort.PARITY_NONE);     						//parity
    
    	os = serialPort.getOutputStream();   						 	//open a channel to send to device 
        input = new BufferedReader
        		(new InputStreamReader(serialPort.getInputStream()));	//read what is being sent back    	
    	serialPort.addEventListener(this);   						 	//detect when device is sending us something
    	serialPort.notifyOnDataAvailable(true); 					 	//allow notification when something is being sent
    }
   
    public synchronized void serialEvent(SerialPortEvent event) {   	//read what the device has to tell us
		if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE){
    	try{
		String inline = input.readLine();  								//read String from stream
		System.out.println(inline);        								//print the string
		}
		catch(IOException e){}	           								//doesn't matter at the moment if we get an empty stream back
		}
	}  
    public synchronized void writeText(String s){ 						//write a message to the device
    	try{		
    		os.write(s.getBytes());
    	}
    	catch(IOException e){}
    }
    
    public synchronized void close() throws IOException{				//close when we're done
    	serialPort.removeEventListener();
    	os.close();
    	input.close();
    }   
}
