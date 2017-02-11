package de.j4rvis.lowfat;

/**
 * Created by mischwar on 10.09.2015.
 */
public enum ColorEnum {
    RED("Rot"),
    GREEN("Grün"),
    YELLOW("Gelb");

    @Override
    public String toString() {
        return name;
    }

    String name;
    ColorEnum(String name) { this.name = name; }


}