package CODSOFT;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter Base Currency: ");
        String base=scanner.nextLine().toUpperCase();
        System.out.print("Enter Target Currency: ");
        String target=scanner.nextLine().toUpperCase();
        System.out.print("What is the amount: ");
        int amount=scanner.nextInt();
        String api="http://api.exchangerate.host/convert?access_key=f80341d1a66126d8840a625257da12e1&from="+base+"&to="+target+
                "&amount="+amount+"&format=1";
        URL url=new URL(api);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode=httpURLConnection.getResponseCode();
        //if succeed
        if (responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader in=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response=new StringBuffer();
            while ((inputLine=in.readLine())!=null){
                response.append(inputLine);
            }in.close();

            JSONObject obj=new JSONObject(response.toString());
            BigDecimal result=obj.getBigDecimal("result");
            System.out.println("*******");
            System.out.println(result+" "+target);
        }
        else System.out.println("FAİLED");



    }
}
