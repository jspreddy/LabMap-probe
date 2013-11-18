/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package labmap.probe;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 *
 * @author Pratyush
 */
public class LabMapProbe {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
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
    public static void sendData(String json) throws IOException {
       
        URL siteUrl = new URL("http://localhost/LabMap/test.php");
        HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        String content = "json=" + URLEncoder.encode(json, "UTF-8");
        System.out.println(content);
        out.writeBytes(content);
        out.flush();
        out.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        while((line=in.readLine())!=null) {
                System.out.println(line);
        }
        in.close();
    }
    
}
