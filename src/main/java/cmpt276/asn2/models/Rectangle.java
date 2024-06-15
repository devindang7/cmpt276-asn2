package cmpt276.asn2.models;

import jakarta.persistence.*;

@Entity
@Table(name="Rectangle")
public class Rectangle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int uid;
    private String name;
    private int width;
    private int height;
    private String color;
    private String image;
    
    public Rectangle() {
    }
    public Rectangle(String name,int width,int height,String color, String image) {
        this.name=name;
        this.width=width;
        this.height=height;
        this.color=color;
        this.image=image;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid=uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width=width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height=height;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color=color;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image=image;
    }
}
