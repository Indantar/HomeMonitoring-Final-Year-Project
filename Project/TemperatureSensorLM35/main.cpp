/*
The program reads the temperature from the TMP35 Sensor using 
the PTB2 pin on the bored.
*/
 
#include "mbed.h"
AnalogIn T1(PTB2); //  temperature  sensor T1 on A0
DigitalIn sw2(SW2);

float T; // average temperature
float K = 3.3*100;
float sam = 3.3/(2^16);
 // analog value to temperature
 
 Serial pc(USBTX,USBRX); // print temperature on console (eg. Putty)
 
int main() {
 pc.printf("TMP35 temperature sensor\n\r");
 
    while(1) {
        if(sw2 == 0){
             T=T1*K;  
             wait(0.2f); //wait a little
             //Temp in °C = [(Vout in mV) - 500] / 10
             //pc.printf("Voltage in mV is %.2f \r\n",V);
             pc.printf("Temperature (in Celsius) is %4.2f \r\n",T);
        }
    }
}