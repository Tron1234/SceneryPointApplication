package com.example.a5069.scenerypointapplication;

/**
 * Created by a5069 on 2017/6/19.
 */
public class scenery {
    private String name,contact,address,graph,summary,lat,lng,belongprovince;

    public scenery(String name, String contact, String address, String lat, String lng, String summary) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.summary = summary;
    }

    public scenery(String name, String graph) {
        this.name = name;
        this.graph = graph;
    }

    public scenery() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getBelongprovince() {
        return belongprovince;
    }

    public void setBelongprovince(String belongprovince) {
        this.belongprovince = belongprovince;
    }
}
