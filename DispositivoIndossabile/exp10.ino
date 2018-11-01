
#include <MySignals.h>
#include "Wire.h"
#include "SPI.h"

#define temperatura A0 //the thermistor attach to
#define beta 4090 //the beta of the thermistor
#define resist 10 //the value of the pull-down resistor

unsigned long currentMillis, start, start1, start2, start3;
const unsigned long period = 5000; // periodo per misurare il bpm
const unsigned long periodoNotifiche =30000;
const unsigned long periodoCaduta =  30000;
const unsigned long periodoGrande = 3600000;
const int pw = 8;
const int wf = 9;
const int dj = 10;
const int sd = 2;
const int bz = 3;

void setup()
{
  pinMode(pw,OUTPUT);
  pinMode(wf,OUTPUT);
  pinMode(dj,OUTPUT);
  pinMode(sd,OUTPUT);
  pinMode(bz,OUTPUT);
  digitalWrite(pw,HIGH);
  delay(250);
  digitalWrite(wf,HIGH);
  delay(250);
  digitalWrite(dj,HIGH);
  delay(250);
  digitalWrite(sd,HIGH);
  delay(250);
  digitalWrite(bz,HIGH);
  delay(100);
   digitalWrite(wf,LOW);
   digitalWrite(dj,LOW);
   digitalWrite(sd,LOW);
   digitalWrite(bz,LOW);  
  delay(1000);
  Serial.begin(115200);

  MySignals.begin();

  //Enable WiFi ESP8266 Power -> bit1:1
  bitSet(MySignals.expanderState, EXP_ESP8266_POWER);
  MySignals.expanderWrite(MySignals.expanderState);

  MySignals.initSensorUART();

  MySignals.enableSensorUART(WIFI_ESP8266);
  delay(1000);

  // Checks if the WiFi module is started
  int8_t answer = sendATcommand("AT", "OK", 6000);
  if (answer == 0)
  {
    MySignals.println("Error");
    // waits for an answer from the module
    while (answer == 0)
    {
      // Send AT every two seconds and wait for the answer
      answer = sendATcommand("AT", "OK", 6000);
    }
  }
  else if (answer == 1)
  {

    MySignals.println("WiFi succesfully working!");


    if (sendATcommand("AT+CWMODE=1", "OK", 6000))
    {
      MySignals.println("CWMODE OK");
    }
    else
    {
      MySignals.println("CWMODE Error");

    }


    //Change here your WIFI_SSID and WIFI_PASSWORD
    if (sendATcommand("AT+CWJAP=\"NETGEAR00\",\"helpfulsea116\"", "OK", 20000))
    {
      MySignals.println("Connected!");
      digitalWrite(wf,HIGH);
    }
    else
    {
      MySignals.println("Error");
      digitalWrite(wf,LOW);
    }
  }
  start = 0;
  start1 = 0;
  start2 = 0;
  start3 = 0;
  MySignals.initBodyPosition();
  delay(1000);
  MySignals.println("********inizio******");
}

void loop()
{
 
  digitalWrite(bz,LOW);
  digitalWrite(dj,LOW);
  long a = analogRead(temperatura);
  int tempC = beta /(log((1025.0 * 10 / a - 10) / 10) + beta / 298.0) - 273.0; 
  float conductance = MySignals.getGSR(CONDUCTANCE);
  
  int ids = 39; // id dell'assistito da inserire a mano
  char mess = 'S'; //
  int bpm = 0;
//*******Algor Caduta*****
  int position2 = MySignals.getBodyPosition();

  MySignals.getAcceleration();
  
  float x = MySignals.x_data ;
  float y = MySignals.x_data ; 
  float z = MySignals.x_data ;   
  float res = sqrt(pow(x,2)+pow(y,2)+pow(z,2));
  char buv [50];
  dtostrf (res, 2, 2, buv);
  MySignals.println(buv);
  
  if(res<0.9 && position2 == 1 && periodoCaduta + start1 <= millis()){
    digitalWrite(dj,HIGH);
    digitalWrite(bz,HIGH);
    mess = 'A';
    bpm = getBpm();
    httpPush(bpm,ids,mess,conductance);
    start1=millis();
    }    
 //Algor stress
  if(conductance>=4.7 && periodoNotifiche + start2 <= millis() ){ //4,7 
    digitalWrite(dj,HIGH);
    bpm = getBpm();
    mess = 'B';
    httpPush(bpm,ids,mess,conductance);
    start2=millis();
    }

  
 //Algor Cardio
  if(periodoNotifiche + start3 <= millis()){
  bpm = getBpm(); 
  if(bpm>=86){
    digitalWrite(dj,HIGH);
    mess = 'C';
    httpPush(bpm,ids,mess,conductance);
    start3 = millis();
    }
  } 

   if(tempC>=34){
    bpm = getBpm(); 
    char mess = 'D';
    httpPush(bpm,ids,mess,conductance);
    }
  if(tempC<=18){
    bpm = getBpm(); 
    char mess = 'E';
    httpPush(bpm,ids,mess,conductance);
    }

  if(periodoGrande + start <= millis()){
    httpPush(bpm,ids,mess,conductance);
    start = millis();
    digitalWrite(bz,HIGH);
    delay(100);
    digitalWrite(bz,LOW);
    }

  delay(500);
}

void httpPush(int bpm, int id, char mes, float gsr) { // per i 5 tipi di chiamate post
  digitalWrite(sd, HIGH);
  char aiuto [25]; //= "m=Salut&t=FromArdu";
  char duck [5];
  dtostrf(gsr , 2, 1, duck);
  
  snprintf(aiuto,sizeof(aiuto),"b=%i&g=%s&i=%d&m=%c",bpm,duck,id,mes);
  
  char prost[200];
  snprintf(prost, sizeof(prost), "POST /server/pdr.php HTTP/1.1\r\nHost: 192.168.0.4\r\nContent-Type: application/x-www-form-urlencoded\r\nContent-Length: %d\r\n\r\n%s\r\n",strlen(aiuto), aiuto);

  char infox [20];
  snprintf(infox, sizeof(infox), "AT+CIPSEND=%d", strlen(prost));

  delay(100);
  sendATcommand("AT+CIPMUX=0", "OK", 500);
  sendATcommand("AT+CIPSTART=\"TCP\",\"192.168.0.4\",80", "OK", 1000);

  sendATcommand(infox, "OK", 500);
  sendATcommand(prost, "OK", 500);
  sendATcommand("AT+CIPCLOSE", "OK", 500);

  digitalWrite(sd, LOW);
  delete[]infox;
  delete[]aiuto;
  delete[]prost;
  delay(200);
}

int8_t sendATcommand(char* ATcommand, char* expected_answer1, unsigned int timeout)
{

  uint8_t x = 0,  answer = 0;
  char response[500];
  unsigned long previous;

  memset(response, '\0', sizeof(response));    // Initialize the string

  delay(100);

  while ( Serial.available() > 0) Serial.read();   // Clean the input buffer

  delay(1000);
  Serial.println(ATcommand);    // Send the AT command

  x = 0;
  previous = millis();

  // this loop waits for the answer
  do
  {

    if (Serial.available() != 0)
    {
      response[x] = Serial.read();
      x++;
      // check if the desired answer is in the response of the module
      if (strstr(response, expected_answer1) != NULL)
      {
        answer = 1;
        MySignals.println(response);

      }
    }
    // Waits for the asnwer with time out
  }
  while ((answer == 0) && ((millis() - previous) < timeout));

  return answer;
}
int getBpm() {

  currentMillis = millis();
  int flag = 0; int contatore = 0;
  while (currentMillis + period >= millis()) {
    uint16_t ecg = (uint16_t)MySignals.getECG(DATA);
    ecg = map(ecg, 0, 1023, 1023, 0);
    if (ecg >= 700 && flag == 0) {
      flag = 1;
    } else if (flag == 1 && ecg <= 700) {
      contatore += 1;
      flag = 0;
    }
    delay(10); // oppure 6
  }

  return contatore * 12;
}		
