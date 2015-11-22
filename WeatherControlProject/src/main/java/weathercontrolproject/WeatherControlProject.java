/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weathercontrolproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import view.GUI;
import weathercontrolproject.JsonParser.Json;

/**
 *
 * @author mariane
 */
public class WeatherControlProject {

    public static void main(String[] args) throws MalformedURLException, IOException {
        /** TODO escolher se vou usar a API da google (openweather) ou a api previsaotempo
            google: http://openweathermap.org/
            previsaotempo: http://www.previsaodotempo.org/api 
        **/
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=campo+mourao&lang=pt&units=metric&appid=5613296465e6c36a58f8fd5fbcb2a258");
        
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 
//        BufferedReader b = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        System.out.println(b.readLine());
        
        ObjectMapper mapper = new ObjectMapper();
        Json json = mapper.readValue(connection.getInputStream(), Json.class);
        
        System.out.println(json.getName());
        
        new GUI();
    }
    
}
