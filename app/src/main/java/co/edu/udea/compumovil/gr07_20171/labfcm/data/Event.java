package co.edu.udea.compumovil.gr07_20171.labfcm.data;

/**
 * Created by Jeniffer Acosta on 25/04/2017.
 */

public class Event {
    private String name;
    private String firstDescription;
    private String information;
    private String place;
    private String date;
    private String user;
    private String picture;

    public String getName() {
        return name;
    }

    public Event(){

    }

    public Event(String name, String firstDescription, String information, String place, String date, String user, String picture) {
        this.name = name;
        this.firstDescription = firstDescription;
        this.information = information;
        this.place = place;
        this.date = date;
        this.user = user;
        this.picture = picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstDescription() {
        return firstDescription;
    }

    public void setFirstDescription(String firstDescription) {
        this.firstDescription = firstDescription;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", firstDescription='" + firstDescription + '\'' +
                ", information='" + information + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
