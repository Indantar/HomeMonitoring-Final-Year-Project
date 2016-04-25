/*
 * motorTest.cpp
 *
 *  Created on: 25 Apr 2016
 *      Author: g00284823
 */


#include "motordriver.h"
#include "mbed.h"

Motor A(D7,D6,D5,1); // pwm, fwd, rev, can break
int main()
{
    for (float s=-1.0; s < 1.0 ; s += 0.01) {
       A.speed(s);
       wait(0.02);
    }
    A.stop(0);
    wait(1);
    A.coast();

}


