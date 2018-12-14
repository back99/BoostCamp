package org.techtown.boostcamp;

public class MovieItem {

    public String title;
    public String link;
    public String image;
    public int pubDate;
    public String director;
    public String actor;
    public float userRating;

    public MovieItem(String title, String link, String image, int pubDate, String director, String actor, float userRating) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPubDate() {
        return Integer.toString(pubDate);
    }

    public void setPubDate(int pubDate) {
        this.pubDate = pubDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public float getUserRating() {
        return userRating/2.0f;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
}
