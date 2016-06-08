package example.loloangela.loadingscreen;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lo on 6/7/2016.
 */
public class ResolveTask extends AsyncTask<String, Void, String> {
    Context context;
    TextView errorTv;
    Button exitBtn;
    ProgressBar progressBar;
    String error1 = "Username exists already.", error2 = "Unable to create user.";
    public ResolveTask(LoadingActivity la, TextView tv, Button btn, ProgressBar pb) {
        context = la;
        errorTv = tv;
        exitBtn = btn;
        progressBar = pb;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String link = "http://lorioliver.net/tester.php";
        // Check database to see if user is in database
        try {
            // Create Connection
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // Write data to output stream
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            bw.write(data);
            // Close output stream
            bw.flush();
            bw.close();
            outputStream.close();

            // Read data from input stream
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "";
            String response = "";
            while ((line = br.readLine()) != null){
                response += line;
            }
            // Close input stream and disconnect
            br.close();
            inputStream.close();
            httpURLConnection.disconnect();
            // Send server response back to user
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
        if(result.equalsIgnoreCase("-2")){
            errorTv.setText(error1);
            progressBar.setVisibility(View.GONE);
            exitBtn.setVisibility(View.VISIBLE);
            ((LoadingActivity)context).setTitle("Error");
        }
    }
}
