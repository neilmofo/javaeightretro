package com.mobileforming.javaeightretro.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mobileforming.javaeightretro.model.Joke;

import java.io.UnsupportedEncodingException;

/**
 * @author neil
 *         5/12/16
 *         ©2016 Hilton Worldwide Inc. All rights reserved.
 */
public class JokeRequest extends Request<Joke> implements JokeResolver {

    private final Response.Listener<Joke> mSuccessListener;

    public JokeRequest(JokeResolver jokeResolver, Response.Listener<Joke> successListener, Response.ErrorListener errorListener) {
        super(Method.GET, jokeResolver.getJokeURL(), errorListener);
        mSuccessListener = successListener;
    }

    /**
     * Subclasses must implement this to parse the raw network response
     * and return an appropriate response type. This method will be
     * called from a worker thread.  The response will not be delivered
     * if you return null.
     *
     * @param response Response from the network
     *
     * @return The parsed response, or null in the case of an error
     */
    @Override
    protected Response<Joke> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "UTF-8"));

            Joke joke = new Gson().fromJson(jsonString, Joke.class);

            return Response.success(joke, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Subclasses must implement this to perform delivery of the parsed
     * response to their listeners.  The given response is guaranteed to
     * be non-null; responses that fail to parse are not delivered.
     *
     * @param response The parsed response returned by
     *                 {@link #parseNetworkResponse(NetworkResponse)}
     */
    @Override
    protected void deliverResponse(Joke response) {
        mSuccessListener.onResponse(response);
    }

    @Override
    public String getJokeURL() {
        return getPath();
    }
}
