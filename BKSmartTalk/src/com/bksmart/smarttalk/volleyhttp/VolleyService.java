package com.bksmart.smarttalk.volleyhttp;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.client.utils.URIUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bksmart.smarttalk.BKSmartApplication;

public class VolleyService {

	public static JSONObject jsonObjectResult = new JSONObject();

	public VolleyService() {

	}

	private static JSONObject postInfo(String url, JSONObject paramsOb) {
		VolleySingleton
				.getInstance()
				.getRequestQueue()
				.add(new JsonObjectRequest(url, paramsOb,
						new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								jsonObjectResult = response;
							}

						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								VolleyLog.e("Volley ErrorResponse: ",
										error.getMessage());
								jsonObjectResult = new JSONObject();
							}

						}));
		return jsonObjectResult;
	}

	public static JSONObject sendMassage(String mass) throws JSONException {
		return postInfo(
				"http://tech.fpt.com.vn/AIML/api/bots/"
						+ BKSmartApplication.smart_en + "/chat?request="
						+ URLEncoder.encode(mass) + "&token="
						+ BKSmartApplication.token, null);

	}
}
