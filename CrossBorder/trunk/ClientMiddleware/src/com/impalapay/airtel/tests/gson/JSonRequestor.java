package com.impalapay.airtel.tests.gson;

import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

/**
 * com.edw.json.JSonRequestor
 *
 * @author edw
 *
 */
public class JSonRequestor {

	public JSonRequestor() {
	}

	private void doRequest() {
		URL serverURL = null;
		try {

			Invoice invoice = new Invoice();

			invoice.setPo_number("2000");
			invoice.setUsername("edw");

			Gson g = new Gson();
			String json = g.toJson(invoice);

			System.out.println(json);

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost("http://localhost/test2/");
			StringEntity stringEntity = new StringEntity(json);
			stringEntity.setContentType("application/json");
			httppost.setEntity(stringEntity);

			System.out
					.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println("Response content length: "
						+ resEntity.getContentLength());
				System.out.println("Chunked?: " + resEntity.isChunked());
			}
			EntityUtils.consume(resEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JSonRequestor jSonRequestor = new JSonRequestor();
		jSonRequestor.doRequest();
	}
}