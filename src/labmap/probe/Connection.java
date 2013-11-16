/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package labmap.probe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 *
 * @author Pratyush
 */
public class Connection {
    HttpURLConnection conn;
    
    public void connect() {
    
        try{
            String param="param1=" + URLEncoder.encode("value1","UTF-8")+"&param2="+URLEncoder.encode("value2","UTF-8")+"&param3="+URLEncoder.encode("value3","UTF-8");

            URL myurl = new URL("http://localhost/LabMap/test.php");
            conn=(HttpURLConnection)myurl.openConnection();
            //set the output to true, indicating you are outputting(uploading) POST data
            conn.setDoOutput(true);
            //once you set the output to true, you don't really need to set the request method to post, but I'm doing it anyway
            conn.setRequestMethod("POST");

            //Android documentation suggested that you set the length of the data you are sending to the server, BUT
            // do NOT specify this length in the header by using conn.setRequestProperty("Content-Length", length);
            //use this instead.
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();

            //build the string to store the response text from the server
            String response= "";

            //start listening to the stream
            Scanner inStream = new Scanner(conn.getInputStream());

            //process the stream and store it in StringBuilder
            while(inStream.hasNextLine())
            response+=(inStream.nextLine());

            }catch(MalformedURLException ex){
            }catch(IOException ex){
        }
    }
}
