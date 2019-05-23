#ifndef OUR_RCC_H
#define OUR_RCC_H

#include <stdint.h>
#include <stm32f407xx.h>

#define RCC_AHB1Periph_GPIOA ((uint32_t)0x01)
#define RCC_AHB1Periph_GPIOC ((uint32_t)(0x01<<2))
#define RCC_AHB1Periph_GPIOD ((uint32_t)(0x01<<3))
#define RCC_AHB1Periph_GPIOF ((uint32_t)(0x01<<5))
#define RCC_AHB1Periph_GPIOG ((uint32_t)(0x01<<6))
#define RCC_APB1Periph_USART3 ((uint32_t)(0x01<<18))
#define RCC_APB2Periph_USART6 ((uint32_t)(0x01<<5))

#define LED_1_PIN 6
#define LED_2_PIN 7
#define LED_3_PIN 8
#define LED_4_PIN 9

#define BTN_1_PIN 0
#define BTN_2_PIN 13

#define MID_CHARACTER $
#define END_CHARACTER #

void RCC_AHB1PeriphClockCmd(uint32_t nPeriph, uint32_t on);
void RCC_APB1PeriphClockCmd(uint32_t nPeriph, uint32_t on);
void RCC_APB2PeriphClockCmd(uint32_t nPeriph, uint32_t on);

typedef enum
{ 
  GPIO_Mode_IN   = 0x00, /*!< GPIO Input Mode */
  GPIO_Mode_OUT  = 0x01, /*!< GPIO Output Mode */
  GPIO_Mode_AF   = 0x02, /*!< GPIO Alternate function Mode */
  GPIO_Mode_AN   = 0x03  /*!< GPIO Analog Mode */
}GPIOMode_Type;


void initGpioPinMode(GPIO_TypeDef *, uint32_t pin, GPIOMode_Type mode);
void togleGpioPinValue(GPIO_TypeDef *, uint32_t pin);
void setGpioPinValue(GPIO_TypeDef *, uint32_t pin, uint32_t value);
void setGpioPinAF(GPIO_TypeDef *, uint32_t pin, uint32_t AF);
uint32_t getGpioPinValue(GPIO_TypeDef *, uint32_t pin);
void generarRandom(void);
#endif
