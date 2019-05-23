#ifndef OUR_COM_H
#define OUR_COM_H

#include <stdint.h>
#include <stm32f407xx.h>

#define FIFO_BUFFER_SIZE 128 //Tama�o del Buffer (en bytes)
#define USED_COM_PORT COM2

typedef enum e_Com {COM1,COM2} COM;

typedef struct {
  uint8_t bufferData[FIFO_BUFFER_SIZE];//Buffer FIFO
  uint16_t oldestData;                 //�ndice del dato m�s antiguo introducido
  uint16_t newestData;                 //�ndice del dato m�s reciente introducido
  uint16_t byteNum;										 //N�mero de bytes actualmente en el buffer
}BUFFER;

uint32_t initCom(COM com, uint32_t baudRate);
void blockingWriteToUart(COM com, uint8_t *pMsg, uint32_t len);
uint32_t escribirUsart(COM com, uint8_t *pMsg, uint32_t len);
uint32_t leerUsart(COM com);

volatile extern uint8_t rxBufferFull;
volatile extern uint8_t rxBufferNotEmpty;

volatile extern uint8_t txBufferFull;
volatile extern uint8_t txBufferNotEmpty;


#endif
