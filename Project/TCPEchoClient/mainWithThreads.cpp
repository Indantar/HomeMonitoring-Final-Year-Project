#include "mbed.h"
#include "EthernetInterface.h"
#include "DebounceIn/DebounceIn.h"
#include "rtos.h"

AnalogIn T1(PTB2); //  temperature  sensor T1 on A0
//DeboucnceIn pb(SW2);
DigitalIn pb(SW2);
DigitalOut led(LED_GREEN);
float T; // average temperature
float K = 3.3*100;
//const char* ECHO_SERVER_ADDRESS = "192.168.1.1";
const char* ECHO_SERVER_ADDRESS = "192.168.0.2";
const int ECHO_SERVER_PORT = 80;
void serverConnection();
EthernetInterface eth;
TCPSocketConnection socket;

Serial pc(USBTX,USBRX); // print temperature on console (eg. Putty)

void recieveThread(void const *args)
{
	while(true){
		// Receive message from server
		char buf[256];
		int n = socket.receive_all(buf, 256);
		if(n == -1){
			pc.printf("Message not received\r\n");
		}
		else
		{
			pc.printf("Bytes received = %d\r\n",n);
			buf[n] = '\0';
			pc.printf("Received message from server: '%s'\r\n", buf);
		}
		Thread::wait(1000);
	}
}
void sendThread(void const *args){
	int ledState;
	char temp[10];
	char state[10];
    char message[10];
    while(true){
    	memset(&message[0], 0, sizeof(message));
    	memset(&state[0], 0, sizeof(state));
    	memset(&temp[0], 0, sizeof(temp));
		led = !led;
		ledState = led;
		T = T1*K;
		pc.printf("Temperature (in Celsius) is %d \r\n",(int)T);
		sprintf(temp,"%d",(int)T);
		if(ledState == 1)
			strcat(state,"on");
		else
			strcat(state,"off");
		// Send message to server
		strcat(message,temp);
		strcat(message,"\n");
		strcat(message,state);
		strcat(message,"\n");
		pc.printf("Sending  message to Server : '%s' \r\n",message);
		int num = socket.send_all(message, sizeof(message) - 1);
		if(num != -1)
			pc.printf("Bytes Sent: %d\r\n",num);
		Thread::wait(1000);
    }
}
int main()
{
	eth.init();
    eth.connect();
    pc.printf("\nClient IP Address is %s\r\n", eth.getIPAddress());
    // Connect to Server
    while (socket.connect(ECHO_SERVER_ADDRESS, ECHO_SERVER_PORT) < 0) {
        pc.printf("Unable to connect to (%s) on port (%d)\r\n", ECHO_SERVER_ADDRESS, ECHO_SERVER_PORT);
        wait(1);
    }
    pc.printf("Connected to Server at %s\r\n",ECHO_SERVER_ADDRESS);
	Thread recThread(recieveThread);
	Thread senThread(sendThread);
	while(true){

	}
}
