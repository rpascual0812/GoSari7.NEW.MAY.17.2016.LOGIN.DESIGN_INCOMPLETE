package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/5/16.
 * */

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.DataInput;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

public class JSONParser {

    static InputStream is=null;
    static JSONObject jobj=null;
    static String json="";

    public JSONParser(){

    }

    public JSONObject getJSONFromUrl(String urlstring){


        try{
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Check the connection status
            if(urlConnection.getResponseCode() == 200)
            {
                // if response code = 200 ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                // Read the BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }

                json=sb.toString();

                jobj=new JSONObject(json);

                urlConnection.disconnect();
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        finally {

        }
        return jobj;
    }
}