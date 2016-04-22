#include "mbed.h"
#include "EthernetInterface.h"

#define ECHO_SERVER_PORT  80


AnalogIn T1(PTB2); //  temperature  sensor T1 on A0
DigitalIn pb(SW2);
DigitalOut led(LED2);
float T; // average temperature
float K = 3.3*100;

TCPSocketServer server;
TCPSocketConnection socket;
EthernetInterface eth;
Serial pc(USBTX,USBRX); // print temperature on console (eg. Putty)

void checkThread(void const *args)
{
	while(true)
	{
		while(pb){}
		pc.printf("Led State is: %d\r\n",led.read());
		led = !led;
		wait(0.5);
	}
}
void connectionThread(void const *args)
{
	int ledState;
	char temp[10];
	char state[10];
    char message[10];
    while(socket.is_connected())
    {
    	memset(&message[0], 0, sizeof(message));
    	memset(&state[0], 0, sizeof(state));
    	memset(&temp[0], 0, sizeof(temp));
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
		strcat(message,":");
		strcat(message,state);
		strcat(message,"\n");
		pc.printf("Sending  message to Client : '%s' \r\n",message);
		int num = socket.send_all(message, sizeof(message) - 1);
		if(num != -1)
		{
			pc.printf("Bytes Sent: %d\r\n",num);
			char buf[256];
			int n = socket.receive(buf, 256);
			if(n == -1)
			{
				pc.printf("Message not received\r\n");
			}
			else
			{
				pc.printf("Bytes received = %d\r\n",n);
				buf[n] = '\0';
				pc.printf("Received message from Client: '%s'\r\n", buf);
				if(strcmp(buf,"on"))
					led.write(1);
				else
					led.write(0);
			}
		}
		Thread::wait(5000);
    }
    socket.close();
}
int main (void)
{
    eth.init(); //Use DHCP
    eth.connect();
	server.bind(ECHO_SERVER_PORT);
	server.listen();
	printf("\nWaiting for Client at %s\r\n", eth.getIPAddress());
	server.accept(socket);
	pc.printf("Client at %s connected\r\n",socket.get_address());
	Thread sockThread(connectionThread);
	Thread ledThread(checkThread);
	led.write(0);
    while(true){}

}