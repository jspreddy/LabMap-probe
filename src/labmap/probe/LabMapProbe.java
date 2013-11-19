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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pratyush
 */
public class LabMapProbe {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run()
            {
                UserData user = null;
                try {
                    user = new UserData(getUsername(), getHostname(), getSystemType(), false);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(LabMapProbe.class.getName()).log(Level.SEVERE, null, ex);
                }
                Gson gson = new Gson();
                String json = gson.toJson(user);
                try {
                    sendData(json);
                } catch (IOException ex) {
                    Logger.getLogger(LabMapProbe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        while(Thread.State.RUNNABLE.toString().equalsIgnoreCase("RUNNABLE")) {
            
            UserData user = new UserData(getUsername(), getHostname(), getSystemType(), getLogInStatus());
            Gson gson = new Gson();
            String json = gson.toJson(user);
            sendData(json);
            
            try {
                Thread.sleep(10*60*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LabMapProbe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public static String getUsername() {
        return System.getProperty("user.name");
    }
    public static String getSystemType() {
        return System.getProperty("os.name");
    }
    public static String getHostname() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
    public static Boolean getLogInStatus() throws UnknownHostException {
        return true;
    }
    
    public static void sendData(String json) throws IOException {
       
        URL siteUrl = new URL("http://localhost/LabMap/test.php");
        HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        String content = "data=" + URLEncoder.encode(json, "UTF-8");
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
