/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import uyennlp.account.AccountDTO;

/**
 *
 * @author HP
 */
public class APIWrapper {

    private final String appId = "3335401283177987";
    private final String appSecret = "a009ee5cda46639287cdd7e2f764f9e2";
    private final String redirectUrl = "http://localhost:8084/J3LP0008/loginFacebook";
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken(String code) throws IOException {
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=%s"
                + "&client_secret=%s"
                + "&redirect_uri=%s"
                + "&code=%s";
        accessTokenLink = String.format(accessTokenLink, appId, appSecret, redirectUrl, code);
        String result = NetHelper.getResult(accessTokenLink);
        //String token = result.substring(result.indexOf("=") + 1, result.indexOf("&"));
        JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
        String token = jobj.get("access_token").toString().replaceAll("\"", "");

        return token;
    }

    public AccountDTO getAccountInfo() throws IOException {
        String infoUrl = "https://graph.facebook.com/me?access_token=%s";
        infoUrl = String.format(infoUrl, this.accessToken);

        String result = NetHelper.getResult(infoUrl);

        AccountDTO account = new Gson().fromJson(result, AccountDTO.class);
        if (account == null) {
            return null;
        }
        return account;
    }
}
