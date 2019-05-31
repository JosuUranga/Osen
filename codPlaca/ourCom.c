#include "ourCom.h"
#include "ourRccGpio.h"

#define ISER0_OFFSET 0x00
#define ISER1_OFFSET 0x04
#define ISER2_OFFSET 0x08
#define ISER3_OFFSET 0x0C

#define TXE_BIT 7
#define TXEIE_BIT 7
#define RXNE_BIT 5	
#define RXNEIE_BIT 5

#define USART3_INTERRUPTION 39
#define USART6_INTERRUPTION 71

#define USART3_TX_PIN 8
#define USART3_RX_PIN 9
#define USART6_TX_PIN 6
#define USART6_RX_PIN 9

#define NVIC_ISER 0xE000E100

#define USART6_GPIOC_BASE ((GPIO_TypeDef *) GPIOC_BASE)
#define USART6_GPIOG_BASE ((GPIO_TypeDef *) GPIOG_BASE)
#define USART3_GPIOD_BASE ((GPIO_TypeDef *) GPIOD_BASE)

BUFFER rxBuffer = { {0}, 0, 0, 0 }; //Declaramos un Buffer para recibir datos
BUFFER txBuffer = { {0}, 0, 0, 0 }; //Declaramos un Buffer para enviar datos 
char byte;
uint32_t initCom(COM com, uint32_t baudRate){
				
	/* Periférico USART */
	switch(com) {
		
		//USART6 RS232_1 GPIOC6: TX ; GPIOG9: RX
	   case COM1:			
					//Habilitar clock a USART6, GPIOC y GPIOG
					RCC->APB2ENR |= RCC_APB2ENR_USART6EN;
					RCC->AHB1ENR |= RCC_AHB1ENR_GPIOCEN;
					RCC->AHB1ENR |= RCC_AHB1ENR_GPIOGEN;
					//Activar los bits UE, TE y RE del USART6
					USART6->CR1 |= USART_CR1_UE;
					USART6->CR1 |= USART_CR1_TE;
					USART6->CR1 |= USART_CR1_RE;
					//Configurar los baudios a USART6
					switch(baudRate) {
						case 9600:
							USART6->BRR = 0;
							USART6->BRR = 0x68<<4;
							USART6->BRR = 0X3;
							break;
					 }					 
					 //Configurar los pins TX y RX en modo alternativo
					 initGpioPinMode(GPIOC,USART6_TX_PIN,GPIO_Mode_AF);
					 initGpioPinMode(GPIOG,USART6_RX_PIN,GPIO_Mode_AF);
					 
					//("Alternate function mapping" Table)					
					USART6_GPIOC_BASE->AFR[USART6_TX_PIN >> 3] |= (8 << (4 * (USART6_TX_PIN & 15))); // 1000 en el AFR que se necesite
					USART6_GPIOG_BASE->AFR[USART6_RX_PIN >> 3] |= (8 << (4 * ((USART6_RX_PIN & 15)-8)));	
				
					 //Configurar la interrupción de USART6 en NVIC
					*((uint32_t*) (NVIC_ISER + ISER2_OFFSET)) |= (1 << (USART6_INTERRUPTION%32)); //Numero interrupt/cuantos iser(%32) para saber el restante
				
					//Habilitar las interrupciones de USART6
				  //USART6->CR1 |= (1 << TXEIE_BIT);
					USART6->CR1 |= (1 << RXNEIE_BIT);
					
	      break; 
				
		// USART3 RS232_2 GPIOD8: TX ; GPIOD9: RX
	   case COM2  :
					//Habilitar clock a USART3 y GPIOD
					RCC->APB1ENR |= RCC_APB1ENR_USART3EN;
					RCC->AHB1ENR |= RCC_AHB1ENR_GPIODEN;
					//Activar los bits UE, TE y RE del USART3 //UE usart enable  TE transmiter enable RE receiver enable
					USART3->CR1 |= USART_CR1_UE;
					USART3->CR1 |= USART_CR1_TE;
					USART3->CR1 |= USART_CR1_RE;
					//Configurar los baudios a USART3
					switch(baudRate) {
						case 9600:
							USART3->BRR = 0;
							USART3->BRR = 0x3;
							USART3->BRR = 0x68<<4;
							break;
					 }
					//Configurar los pins TX y RX en modo alternativo
					 initGpioPinMode(GPIOD,USART3_TX_PIN,GPIO_Mode_AF);
					 initGpioPinMode(GPIOD,USART3_RX_PIN,GPIO_Mode_AF);
					//("Alternate function mapping" Table)
					USART3_GPIOD_BASE->AFR[USART3_TX_PIN >> 3] |= (7 << (4 * (USART3_TX_PIN & 7))); // 0111 en el AFR que se necesite
					USART3_GPIOD_BASE->AFR[USART3_RX_PIN >> 3] |= (7 << (4 * (USART3_RX_PIN & 7)));
					 
					 //Configurar la interrupción de USART3 en NVIC
					*((uint32_t*) (NVIC_ISER + ISER1_OFFSET)) |= (1 << (USART3_INTERRUPTION%32));//Numero interrupt/cuantos iser(%32) para saber el restante
					//Habilitar las interrupciones de USART3
					//USART3->CR1 |= (1 << TXEIE_BIT);
					USART3->CR1 |= (1 << RXNEIE_BIT);
	      break;  
	}
	return 0;
}

void blockingWriteToUart(COM com, uint8_t *pMsg, uint32_t len){
			
	switch(com) {
		case COM1 :
			for(int i = 0;i<len;i++){
			while (!(USART6->SR & USART_SR_TXE)){};
			USART6->DR = pMsg[i];
			txBuffer.oldestData++;
			txBuffer.byteNum--;
			}
			break;
		
		case COM2 :
			for(int i = 0;i<len;i++){
			while (!(USART3->SR & USART_SR_TXE)){};
			USART3->DR = pMsg[i];
			txBuffer.oldestData++;
			txBuffer.byteNum--;
			}
			break;				
		}
}
uint32_t mirarTransmitir(COM com){
	
	switch(com){
		case COM1:
			if(USART6->CR1 & (1<<TXEIE_BIT)) return 1;
			else return 0;
		case COM2:
			if(USART3->CR1 & (1<<TXEIE_BIT)) return 1;
			else return 0;
	}
	return 0;
}
 
uint32_t mirarRecibir(COM com){
	
	switch(com){
		case COM1:
			if(USART6->SR & (1<<RXNE_BIT)) return 1;
			else return 0;
		case COM2:
			if(USART3->SR & (1<<RXNE_BIT)) return 1;
			else return 0;
	}
	return 0;
}
	//logica buffers
void ourUSART3_IRQHandler(void){ //RS232_2

	if(mirarTransmitir(USED_COM_PORT)){//Mirar si se esta transmitiendo
		if(txBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer esta lleno
			txBufferFull = 0;//ponemos el flag del buffer lleno
		}
		if(txBuffer.byteNum > 0) {//Si hay algo en el Buffer
			blockingWriteToUart(USED_COM_PORT,txBuffer.bufferData,txBuffer.byteNum);
		}
		if(txBuffer.oldestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
			txBuffer.oldestData = 0;
		}
		if(txBuffer.byteNum == 0) {//Si no hay nada en el Buffer
			txBufferNotEmpty = 0;    //el flag del bufer no vacio a 0
		}
		USART3->CR1 &= ~(1 << TXEIE_BIT);//Desactivamos las interrupciones mientras trabajamos con el buffer para no tener problemas
		
	}
	if(mirarRecibir(USED_COM_PORT)){//Si se quiere recibir

		if(rxBuffer.byteNum < FIFO_BUFFER_SIZE) {//Si el Buffer no está lleno
			rxBuffer.bufferData[rxBuffer.oldestData] = USART3->DR;//Poner el dato recibido en el Buffer	 
			rxBuffer.oldestData++;		//aumentar la posicion del dato mas viejo
			rxBuffer.byteNum++;				//aumentar el numero de bytes del buffer
		}
		if(rxBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer se llena en el proceso
			rxBufferFull = 1;					//el flag de buffer lleno a 1
		}
		if(rxBuffer.oldestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
			rxBuffer.oldestData = 0;
		}
		rxBufferNotEmpty = 1;		//flag de buffer no vacio a 1
		leerUsart(USED_COM_PORT);	//leer lo que ha llegado
		if(byte=='A') {
			togleGpioPinValue(GPIOF,LED_3_PIN);
			generarRandom();			//generar valores aleatorios, provisional antes de hacer i2c
		}
	}
}

void ourUSART6_IRQHandler(void) { //RS232_1

	if(mirarTransmitir(USED_COM_PORT)){//Mirar si se esta transmitiendo
		if(txBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer esta lleno ya
			txBufferFull = 0;//ponemos el flag del buffer lleno
		}
		if(txBuffer.byteNum > 0) {//Si hay algo en el Buffer     
			blockingWriteToUart(USED_COM_PORT,txBuffer.bufferData,txBuffer.byteNum); 
		}
		if(txBuffer.oldestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
			txBuffer.oldestData = 0;	
		}
		if(txBuffer.byteNum == 0) {//Si no hay nada en el Buffer
			txBufferNotEmpty = 0;    //el flag del bufer no vacio a 0
		}
		USART6->CR1 &= ~(1 << TXEIE_BIT);//Desactivamos las interrupciones mientras trabajamos con el buffer para no tener problemas
	}
	
	if(mirarRecibir(USED_COM_PORT)){//Mirar si se esta recibibiendo
		if(rxBuffer.byteNum < FIFO_BUFFER_SIZE) {//Si el Buffer no está lleno			 
			rxBuffer.bufferData[rxBuffer.oldestData] = USART6->DR;//Poner el dato recibido en el Buffer	 
			rxBuffer.oldestData++;	//aumentar la posicion del dato mas viejo
			rxBuffer.byteNum++;			//aumentar el numero de bytes del buffer
		}
		if(rxBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer se llena en el proceso
			rxBufferFull = 1;		//el flag de buffer lleno a 1
		}
		if(rxBuffer.oldestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
			rxBuffer.oldestData = 0;
		}
		rxBufferNotEmpty = 1;			//flag de buffer no vacio a 1
		leerUsart(USED_COM_PORT);	//leer lo que ha llegado
		if(byte=='A') {
			togleGpioPinValue(GPIOF,LED_3_PIN);
			generarRandom();				//generar valores aleatorios, provisional antes de hacer i2c
		}
	}
}

uint32_t escribirUsart(COM com, uint8_t *pMsg, uint32_t len) {
   
	
	switch(com) {
		case(COM1) :			
			USART6->CR1 &= ~(1 << TXEIE_BIT);//Desactivamos las interrupciones mientras trabajamos con el buffer
			for(int i=0;i<len;i++){		
				if(txBuffer.byteNum < FIFO_BUFFER_SIZE){//Si hay sitio en el Buffer
					txBuffer.bufferData[txBuffer.newestData] = pMsg[i];//Mover dato al Buffer
					txBuffer.newestData++;
					txBuffer.byteNum++;	
				}
				if(txBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer esta lleno
					txBufferFull = 1;
				}
				if(txBuffer.newestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
					txBuffer.newestData = 0; 
				}
			}
		 
			USART6->CR1 |= (1 << TXEIE_BIT);//Reactivamos las interrupciones al finalizar de trabajar con el buffer
		 
			if(txBuffer.byteNum > 0) {//Si hay algo en el Buffer		 
				txBufferNotEmpty = 1; 
			}
				break;
			
		case(COM2) :
			
			USART3->CR1 &= ~(1 << TXEIE_BIT);//Desactivamos las interrupciones mientras trabajamos con el buffer
			
			for(int i=0;i<len;i++){		
				if(txBuffer.byteNum < FIFO_BUFFER_SIZE){//Si hay sitio en el Buffer
					txBuffer.bufferData[txBuffer.newestData] = pMsg[i];//Mover dato al Buffer
					txBuffer.newestData++; 
					txBuffer.byteNum++;	
				}
				if(txBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer esta lleno
					txBufferFull = 1; 
				}
				if(txBuffer.newestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
					txBuffer.newestData = 0;
				}
			}
		 
			USART3->CR1 |= (1 << TXEIE_BIT);//Reactivamos las interrupciones al finalizar de trabajar con el buffer
		 
			if(txBuffer.byteNum > 0) {//Si hay algo en el Buffer			 
				txBufferNotEmpty = 1;
			}
				break;
	}
	return 0;
}

uint32_t leerUsart(COM com){


	switch(com){
			case(COM1) :
				USART6->CR1 &= ~(1 << RXNEIE_BIT);//Desactivamos las interrupciones mientras trabajamos con el buffer
						if(rxBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer está lleno
							rxBufferFull = 0;//Lo ponemos a 0 porque lo vamos a vaciar
						}
						if(rxBuffer.byteNum > 0) {//Si hay algo en el Buffer
							byte = rxBuffer.bufferData[rxBuffer.oldestData];//Poner el primer dato introducido en "byte"
							rxBuffer.oldestData++;
							rxBuffer.byteNum--;
						}else{//Si el Buffer está vacio
							rxBufferNotEmpty = 0;
						}
						if(rxBuffer.oldestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
							rxBuffer.oldestData = 0;
						}
				USART6->CR1 |= (1 << RXNEIE_BIT);//Reactivamos las interrupciones al finalizar de trabajar con el buffer
				break;
			case(COM2) :
				USART3->CR1 &= ~(1 << RXNEIE_BIT);//Desactivamos las interrupciones mientras trabajamos con el buffer
						if(rxBuffer.byteNum == FIFO_BUFFER_SIZE) {//Si el Buffer está lleno
							rxBufferFull = 0;//Lo ponemos a 0 porque lo vamos a vaciar
						}
						if(rxBuffer.byteNum > 0) {//Si hay algo en el Buffer
							byte = rxBuffer.bufferData[rxBuffer.oldestData-1];//Poner el primer dato introducido en "byte"
							rxBuffer.oldestData++;
							rxBuffer.byteNum--;
						}else{//Si el Buffer está vacio
							rxBufferNotEmpty = 0;
						}
						if(rxBuffer.oldestData == FIFO_BUFFER_SIZE) {//Si se llega al final del Buffer, volver al principio
							rxBuffer.oldestData = 0;
						}
				USART3->CR1 |= (1 << RXNEIE_BIT);//Reactivamos las interrupciones al finalizar de trabajar con el buffer
				break;
	}
	 
  return 0;
}
