/*
 * main2.cpp
 *
 *  Created on: 6 Apr 2016
 *      Author: g00284823
 */

#include "mbed.h"
#include "EthernetInterface.h"

//RawSerial pc(USBTX, USBRX);  //<<added line

int main() {
   // pc.baud(230400);        //<<added line
    EthernetInterface eth;
    eth.init(); //Use DHCP
    eth.connect();
    printf("IP Address-a is %s\n", eth.getIPAddress());
    //printf("IP Address-b is %s\n", eth.getIPAddress());     //<<added line
    //printf("IP Address-c is %s\n", eth.getIPAddress());     //<<added line

    /*TCPSocketConnection sock;
    sock.connect("mbed.org", 80);

    char http_cmd[] = "GET /media/uploads/mbed_official/hello.txt HTTP/1.0\n\n";
    sock.send_all(http_cmd, sizeof(http_cmd)-1);

    char buffer[300];
    int ret;
    while (true) {
        ret = sock.receive(buffer, sizeof(buffer)-1);
        if (ret <= 0)
            break;
        buffer[ret] = '\0';
        printf("Received %d chars from server:\n%s\n", ret, buffer);
    }

    sock.close();*/
    eth.disconnect();

    while(1) {}
}


