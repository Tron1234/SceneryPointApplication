package com.example.a5069.scenerypointapplication;

/**
 * Created by a5069 on 2017/6/20.
 */
public class scenerypicture {
    private String url1,url2,url3;

    public scenerypicture(String url1, String url2, String url3) {
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
    }

    public scenerypicture() {
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }
}
