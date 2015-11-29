int estado = 0; // liga/desliga

int umidade1; // umidade do sensor 1
int umidade2; // umidade do sensor 2
int umidade3; // umidade do sensor 3
void setup()
{
  Serial.begin(9600);
  pinMode(13, OUTPUT);
}
void loop(){
    umidade1 = analogRead(A0);
    umidade2 = analogRead(A1);
    umidade3 = analogRead(A2);

    int porcento1 = map(umidade1, 1023, 0, 0, 100);
    int porcento2 = map(umidade2, 1023, 0, 0, 100);
    int porcento3 = map(umidade3, 1023, 0, 0, 100);
  

    Serial.print(porcento1);
    Serial.print(";");
    Serial.print(porcento2);
    Serial.print(";");
    Serial.print(porcento3);
    Serial.println();
    delay(1000);  
    
    if(Serial.available()){
      estado = Serial.read();
        if(estado == 49){
          digitalWrite(13, HIGH);
          delay(1000);
        }
        if(estado == 48){
          digitalWrite(13, LOW);
          delay(1000);
        }
  }
}
