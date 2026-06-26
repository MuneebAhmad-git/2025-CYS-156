class Point{    
    private double x;   
    private double y;    
    private double z;
    Point(double x1,double y1,double z1){       
        this. x = x1;        
        this.y = y1;        
        this.z = z1;
    }
    
    public void getInfo(){        
        System. out .println("("+this.x+","+this.y+","+this.z+")");
    }

    public double distance_calculator(Point p){        
        double dx = p.x - x;
        double dy = p.y - y;        
        double dz = p.z - z;        
        return Math. sqrt (dx*dx+dy+dy+dz*dz);
    }
}
public class Task1_3d_points {    
    static void main(String[] arg){        
        double distance;
        Point p1 = new Point(2.0,3.0,4.0);        
        Point p2 = new Point(5.0,6.0,7.0);        
        System. out .print("Point#1: ");        
        p1.getInfo();        
        System. out .print("Point#2: ");        
        p2.getInfo();
        distance = p1.distance_calculator(p2);        
        System. out .println("Distance between these points is: "+distance);    
    }
}