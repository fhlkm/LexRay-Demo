package com.lextech.restutil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.lextech.model.Message;
import com.lextech.model.Result;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

/**
 *
 * @author Hanlu Feng
 *
 */
@SuppressWarnings( "deprecation" )
public class WebServiceClient {
	public enum RequestMethod{GET,POST,DELETE};
	public static final String URL = "https://stagefundstransfer.cashedge.com/mobilewebservice";
    private ArrayList <NameValuePair> params;
    private static ArrayList <NameValuePair> headers=new ArrayList<NameValuePair>();
	private static String CLASS_NAME=WebServiceClient.class.getSimpleName();
    private String url;
    private int responseCode;
    private String message;
    private String response;

    public String getResponse() {
        return response;
    }

    public String getErrorMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public WebServiceClient(String callUrl){
        url= callUrl;
    }




    public void execute(RequestMethod method) throws Exception{
    	switch (method) {
    	case GET: {
    		String combinedParams = "";
    		HttpGet request = new HttpGet(url + combinedParams);
    		for (NameValuePair h : headers) {
    			request.addHeader(h.getName(), h.getValue());
    		}
    		executeRequest(request, url);
    		break;
    	}
    	case POST: {
    		HttpPost request = new HttpPost(url);
    		for (NameValuePair h : headers) {
    			request.addHeader(h.getName(), h.getValue());
    		}
    		if (!params.isEmpty()) {
    			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
    		}
    		executeRequest(request, url);
    		break;
    	}
    	case DELETE:{
    		HttpDelete request = new HttpDelete(url);
    		for (NameValuePair h : headers) {
    			request.addHeader(h.getName(), h.getValue());
    		}
    		executeRequest(request, url);
    		break;
    	}
    	}
    }

    private void executeRequest(HttpUriRequest request, String url){
		HttpClient client = new DefaultHttpClient();
		URI ur = request.getURI();

		HttpResponse httpResponse;
		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);
				instream.close();
			}
		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }




	public Result callService(RequestMethod reqmethod ) {
		Result result = new Result();
		try {
			execute(reqmethod);
			String response = getResponse();
			Log.d("WebServiceClient",response);
			String error = getErrorMessage();
			int code = getResponseCode();
			Log.d("WebServiceClient::code",code+"");
			if(code==200){
				result.setSuccess(true);
				result.setSuccessMessage(response);
			}else if(code == 500){
				result.setSuccess(false);
				Log.d("WebServiceClient", error);
				result.setFailureMessage(response);
			}else{
				result.setSuccess(false);
				Log.d("WebServiceClient",error);
				result.setFailureMessage(Message.UNABLE_TO_CONNECT+(error));
			}
		} catch (Exception e) {
			e.printStackTrace();
				Log.d("ERROR", e.getLocalizedMessage());
				result.setSuccess(false);
				result.setFailureMessage(Message.UNABLE_TO_CONNECT);
			}

		Log.d("WebServiceClient",result.isSuccess()+"");
		Log.d("WebServiceClient",result.toString());
		return result;
	}

	/**
	 *
	 * @param con
	 * @return
	 */
	public boolean isOnline(Context con) {
	    ConnectivityManager cm =
	        (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}


}
