/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermonitoring.JsonParser;

/**
 *
 * @author mariane
 */
public class Rain {
 private String x;

    public String getx ()
    {
        return x;
    }

    public void setx (String x)
    {
        this.x = x;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [x = "+x+"]";
    }    
}
