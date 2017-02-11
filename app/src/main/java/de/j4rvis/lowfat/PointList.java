package de.j4rvis.lowfat;


import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by mischwar on 10.09.2015.
 */
public class PointList extends SugarRecord<PointList> {

    private String name;

    @Ignore
    private List<Point> mPoints;

    public PointList(){}

    public PointList(String name){
        this.name = name;
    }

    public List<Point> getPoints(){
        return Point.find(Point.class, "m_point_list = ?", String.valueOf(getId()));
    }

    public double getSize(){
        List<Point> list = Point.find(Point.class, "m_point_list = ?", String.valueOf(getId()));
        double size = 0;
        for(Point point : list){
            size+=point.getSize();
        }
        return size;
    }

    public double getSizeByColor(ColorEnum color){
        List<Point> list = Point.find(Point.class, "m_point_list = ? AND m_color = ?", String.valueOf(getId()), String.valueOf(color));
        double size = 0;
        for(Point point : list){
            size+=point.getSize();
        }
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
