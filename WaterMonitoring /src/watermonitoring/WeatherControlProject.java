/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermonitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import view.GUI;
import watermonitoring.JsonParser.Json;

/**
 *
 * @author mariane
 */
public class WeatherControlProject {
    
    
    public static void main(String[] args) throws MalformedURLException, IOException, SQLException {
       URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=campo+mourao&lang=pt&units=metric&appid=5613296465e6c36a58f8fd5fbcb2a258");
        
       HttpURLConnection connection = (HttpURLConnection) url.openConnection();

       BufferedReader b = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       String j = b.readLine().replace("3h", "x");
        
      ObjectMapper mapper = new ObjectMapper();
      Json json = mapper.readValue(j, Json.class);
  
      new GUI(json);
    }
    
}
