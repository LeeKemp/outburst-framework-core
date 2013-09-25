/*
 * Copyright 2009 - 2010 Lee Kemp
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.outburstframework.test.util.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HTTPUtils {

	/**
	 * Makes a HTTP Get request.
	 * 
	 * @param url
     *          The URL fo the page you would like to return
	 * @return The body of the HTTP request
	 * @throws HTTPException
     *              The was a problem making the request
	 * @throws InvalidResponseException
	 *             The Response HTTP Status was not 200 OK
	 */
	public static String HTTPGet(String url) throws InvalidResponseException, HTTPException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
		} catch (Exception e) {
			throw new HTTPException(e.getMessage(), e);
		}

		if (response.getStatusLine().toString().indexOf("200 OK") == -1) {
			throw new InvalidResponseException("Invalid status: "
					+ response.getStatusLine());
		}

		try {
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new HTTPException(e.getMessage(), e);
		}
	}

}
