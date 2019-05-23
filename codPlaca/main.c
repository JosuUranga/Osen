#include <stdint.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>
#include "ourRccGpio.h"
#include "ourCom.h"


void initGPIO(void);
void initGPIOInterrupts(void);

char msg[128] = "400";

/***********************INICIO FLAGS DEL BUFFER*********************/
volatile uint8_t rxBufferFull=0;
volatile uint8_t rxBufferNotEmpty=0;

volatile uint8_t txBufferFull=0;
volatile uint8_t txBufferNotEmpty=0;
/************************FIN FLAGS DEL BUFFER***********************/
	
int main(void)
{
  initGPIO();	
	initCom(USED_COM_PORT,9600);
	while(1)
  {	
	}
}

void initGPIO(void)
{
  RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOF, 1);
  initGpioPinMode(GPIOF, LED_1_PIN, GPIO_Mode_OUT);
  initGpioPinMode(GPIOF, LED_2_PIN, GPIO_Mode_OUT);
  initGpioPinMode(GPIOF, LED_3_PIN, GPIO_Mode_OUT);
  initGpioPinMode(GPIOF, LED_4_PIN, GPIO_Mode_OUT);
	
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOA, 1);
	initGpioPinMode(GPIOA, BTN_1_PIN, GPIO_Mode_IN);
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOC, 1);
	initGpioPinMode(GPIOC, BTN_2_PIN, GPIO_Mode_IN);
}

void generarRandom(){
	char co2[3],temp[2],hum[2],voc[4];
	int r = rand()%800;
	r+=200;
	sprintf(co2,"%d",r);
	r = rand()%21;
	r+=15;
	sprintf(temp,"%d",r);
	r = rand()%65;
	r+=20;
	sprintf(hum,"%d",r);
	r = rand()%800;
	r+=200;
	sprintf(voc,"%d",r);
	sprintf(msg,"%s$%s$%s$%s#",co2,temp,hum,voc);
	escribirUsart(USED_COM_PORT,(uint8_t*)msg,strlen(msg));
}
