package com.github.wannesvr.core.request;

import com.github.wannesvr.core.config.Dota2ApiConfig;
import com.github.wannesvr.core.exception.Dota2ApiException;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class containing the request path and url parameters.
 */
public abstract class AbstractSteamApiRequest implements Dota2ApiRequest {
    private final String path;
    private HttpGet request;
    private List<NameValuePair> urlParameters;

    public AbstractSteamApiRequest(String path) {
        this.request = new HttpGet();
        this.path = path;
        this.urlParameters = new ArrayList<>();
    }

    public void urlParam(String key, String value) {
        this.urlParameters.add(new BasicNameValuePair(key, value));
    }

    public void urlParam(String key, int value) {
        this.urlParameters.add(new BasicNameValuePair(key, String.valueOf(value)));
    }

    public void urlParam(String key, long value) {
        this.urlParameters.add(new BasicNameValuePair(key, String.valueOf(value)));
    }

    public void urlParam(String key, boolean value) {
        this.urlParameters.add(new BasicNameValuePair(key, value ? "1" : "0"));
    }

    /**
     * Adds every {@see Parameter} and url to the {@link HttpGet}.
     *
     * @return A {@link HttpGet} object
     */
    @Override
    public HttpRequest getRequest() {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https");
        uriBuilder.setHost("api.steampowered.com");
        uriBuilder.setPath(this.path);

        try {
            uriBuilder.addParameter("key", Objects.requireNonNull(Dota2ApiConfig.API_KEY, "API Key is null"));
            uriBuilder.addParameter("format", Dota2ApiConfig.FORMAT);
            uriBuilder.addParameter("language", Dota2ApiConfig.LANGUAGE);
        } catch (NullPointerException e) {
            throw new Dota2ApiException(e.getMessage());
        }

        this.urlParameters.forEach(kv -> uriBuilder.addParameter(kv.getName(), kv.getValue()));

        try {
            this.request.setURI(uriBuilder.build());
        } catch (URISyntaxException e) {
            throw new Dota2ApiException("Invalid url format: " + e.getMessage());
        }

        return request;
    }
}
