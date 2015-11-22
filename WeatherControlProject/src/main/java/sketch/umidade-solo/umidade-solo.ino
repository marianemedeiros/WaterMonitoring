/*********************************
 ***Autor: Leonardo Dalla Porta****
 ***********25/05/2014*************
 *************Atenção**************
 ***O Codigo esta livre para uso,**
 *desde que seja mantida sua fonte* 
 ********e seu autor.**************
 ********Faça um bom uso***********
 ******Att. Equipe UsinaInfo*******
 *********************************/
 
int umidade1; // umidade do sensor 1
int umidade2; // umidade do sensor 2
int umidade3; // umidade do sensor 3
void setup()
{
  Serial.begin(9600);
  Serial.println("www.usinainfo.com.br");
  pinMode(13, OUTPUT);
}
void loop()
{
  umidade1 = analogRead(A0);
  umidade2 = analogRead(A1);
  umidade3 = analogRead(A2);

  int porcento1 = map(umidade1, 1023, 0, 0, 100);
  int porcento2 = map(umidade2, 1023, 0, 0, 100);
  int porcento3 = map(umidade3, 1023, 0, 0, 100);
 
  double media = (porcento1 + porcento2 + porcento3) / 3;
  
  Serial.print(media);
  Serial.println("%");

  if(media <=70)
  {
    Serial.println("Irrigando...");
    digitalWrite(13, HIGH);
  }
 
  else
  {
    digitalWrite(13, LOW);
  }
  delay(1000);
}
