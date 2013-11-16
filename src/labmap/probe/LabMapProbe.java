/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package labmap.probe;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Pratyush
 */
public class LabMapProbe {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {
        UserData user = new UserData(getUsername(), getHostname());
        Gson gson = new Gson();
        String json = gson.toJson(user);
        sendData(json);
    }
    
    public static String getUsername() {
        return System.getProperty("user.name");
    }
    public static String getHostname() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
    public static void sendData(String json) {
        HttpURLConnection conn;
        try{
            String param = URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(json, "UTF-8");

            URL myurl = new URL("http://localhost/LabMap/test.php");
            conn=(HttpURLConnection)myurl.openConnection();
            //set the output to true, indicating you are outputting(uploading) POST data
            //conn.setDoOutput(true);
            //once you set the output to true, you don't really need to set the request method to post, but I'm doing it anyway
            conn.setRequestMethod("POST");

            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.write(param);
            out.close();

            }catch(MalformedURLException ex){
            }catch(IOException ex){
        }
    }
    
}
