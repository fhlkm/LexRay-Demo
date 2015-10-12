package com.lextech.restutil;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.lextech.model.Result;
import com.lextech.quiz.json.com.lextech.quiz.LextechJSONQuizActivity;

/**
 * Created by hanlu Feng on 10/12/2015.
 */
public class FetchingDataTask extends AsyncTask<String, Void,Result> {

    private Context mContext;
    private ProgressBar mProgressBar;
    public FetchingDataTask(Context mContext){
        this.mContext = mContext;
        this.mProgressBar = ((LextechJSONQuizActivity)(mContext)).getmProgressBar();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Result doInBackground(String... strings) {
        String URL = strings[0];
        WebServiceClient mWebServiceClient = new WebServiceClient(URL);
        Result result = mWebServiceClient.callService(WebServiceClient.RequestMethod.GET);

        return result;
    }

    @Override
    protected void onPostExecute(Result returnProcess) {
        mProgressBar.setVisibility(View.GONE);
        LextechJSONQuizActivity mActivity = (LextechJSONQuizActivity)mContext;
        mActivity.callback(returnProcess);
    }
}
