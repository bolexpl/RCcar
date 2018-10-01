#include <SPI.h>
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

#define left 4           //D2
#define right 15         //D8
#define forward 2        //D4
#define backward 12      //D6
#define eep 16           //D0

//IP 192.168.4.1
#define localPort 3000

#define ssid "ESPcar"
#define pass "nie wiem"

WiFiUDP Udp;
char command[255];

void setup() {
  Serial.begin(9600);
  Serial.print("Setting soft-AP ... ");

  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
  pinMode(forward, OUTPUT);
  pinMode(backward, OUTPUT);
  pinMode(eep, OUTPUT);

  digitalWrite(eep, HIGH);
  digitalWrite(left, LOW);
  digitalWrite(right, LOW);
  digitalWrite(forward, LOW);
  digitalWrite(backward, LOW);

  if (WiFi.softAP(ssid, pass)) {
    Serial.println("Ready");
  } else {
    Serial.println("Failed");
  }

  Serial.println("Access Point IP: " + WiFi.softAPIP());
  Serial.println("UDP started");
  Udp.begin(localPort);
}

//----------------------------------------------------------------------------------------

void loop() {
  int packetSize = Udp.parsePacket();
  if (packetSize) {
    Serial.printf("Received %d bytes from %s, port %d\n", packetSize, Udp.remoteIP().toString().c_str(), Udp.remotePort());
    int len = Udp.read(command, 255);
    if (len > 0) {
      command[len] = 0;
    }
    Serial.printf("UDP packet: %s\n", command);

    //start
    if (!strcmp(command, "forward")) {
      digitalWrite(forward, HIGH);
      digitalWrite(backward, LOW);
      
    } else if (!strcmp(command, "backward")) {
      digitalWrite(forward, LOW);
      digitalWrite(backward, HIGH);
      
    } else if (!strcmp(command, "left")) {
      digitalWrite(left, HIGH);
      digitalWrite(right, LOW);
      
    } else if (!strcmp(command, "right")) {
      digitalWrite(left, LOW);
      digitalWrite(right, HIGH);
      
    } else

      //stop
      if (!strcmp(command, "stop")) {
        digitalWrite(forward, LOW);
        digitalWrite(backward, LOW);
      } else if (!strcmp(command, "straight")) {
        digitalWrite(left, LOW);
        digitalWrite(right, LOW);
      }
  }
}

