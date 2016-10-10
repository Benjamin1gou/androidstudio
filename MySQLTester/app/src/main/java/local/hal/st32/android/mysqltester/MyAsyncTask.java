package local.hal.st32.android.mysqltester;

/**
 * Created by Tester on 2016/10/10.
 */
import android.os.AsyncTask;
import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.util.Log;

import java.io.ByteArrayOutputStream;


public class MyAsyncTask extends AsyncTask<String, Integer, Integer> implements OnCancelListener{
    final String TAG = "MyAsyncTask";

    private Activity m_Activity;

    private static final String url = "http://nvtrlab.jp/test.php";

    //クライアント設定
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(url);

    List<NameValuePair> nameValuePair;
    HttpResponse response;
    ByteArrayOutputStream byteArrayOutputStream;
    HttpResponse res = null;

    public MyAsyncTask(Activity activity) {
        this.m_Activity = activity;
    }


    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute");
    }

    @Override
    protected Integer doInBackground(String... contents) {
        Log.d(TAG, "doInBackground - " + contents[0]);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
        params.add( new BasicNameValuePair("moji", contents[0]));

        try {
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            res = httpClient.execute(post);

            byteArrayOutputStream = new ByteArrayOutputStream();
            res.getEntity().writeTo(byteArrayOutputStream);

        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.valueOf(res.getStatusLine().getStatusCode());

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d(TAG, "onProgressUpdate - " + values[0]);

    }

    @Override
    protected void onCancelled() {
        Log.d(TAG, "onCancelled");

    }

    @Override
    protected void onPostExecute(Integer result) {
        Log.d(TAG, "onPostExecute - " + result);


        //サーバーからの応答を取得
        if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        {
//デバッグ用にリザルトコードをTextViewに表示させておく
            TextView tv = (TextView) this.m_Activity.findViewById(R.id.textView1);
            tv.setText(""+result);

            tv.setText(result+"\n"+byteArrayOutputStream.toString());

//サーバーから受けとった文字列の処理
            if(byteArrayOutputStream.toString().equals("1")){
                Toast.makeText(this.m_Activity, "[ここには処理１] ", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this.m_Activity, "[ここには処理２] ", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this.m_Activity, "[error]: "+response.getStatusLine(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.d(TAG, "Dialog onCancell... calling cancel(true)");
        this.cancel(true);
    }
}
