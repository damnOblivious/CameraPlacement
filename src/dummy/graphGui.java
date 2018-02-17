package dummy;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;
import javax.swing.*;
import java.awt.*;


public class graphGui extends JPanel {

    map mymap;
    ArrayList<Vertex> v_cover;
    String title;

    String[] type = { "Serif","SansSerif"};
    int[] styles = { Font.PLAIN, Font.ITALIC, Font.BOLD,
        Font.ITALIC + Font.BOLD };
        String[] stylenames =
        { "Plain", "Italic", "Bold", "Bold & Italic" };

        public graphGui()
        {}

            public graphGui(map map1  ,ArrayList<Vertex> cover ,String s)
            {
                mymap=new map();
                mymap=map1;

                title=s;
                v_cover=new ArrayList<>();

                v_cover=cover;
            }


            public void draw_graph()
            {
                JFrame f = new JFrame();
                f.setSize(800, 600);
                f.add(new graphGui(mymap,v_cover,title));
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
            @Override
            public void paint(Graphics g) {
                int price=0;
                double x1,y1,x2,y2; int i,j;
                int xcor=this.getWidth();
                int ycor=this.getHeight();
                double xmin=0,ymin=0,xmax=0,ymax=0;
                xmin = xmax = mymap.vertices.get(0).pos.x;
                ymin = ymax = mymap.vertices.get(0).pos.y;
                for(i = 0; i < mymap.vertices.size(); ++i) {
                    x1 = mymap.vertices.get(i).pos.x;
                    y1 = mymap.vertices.get(i).pos.y;
                    if(x1>xmax) xmax=x1;
                    if(x1<xmin) xmin=x1;
                    if(y1>ymax) ymax=y1;
                    if(y1<ymin) ymin=y1;
                }
                for(int pi=0;pi<v_cover.size();++pi)
                {
                    price+=v_cover.get(pi).cam.cam.Price;
                }
                int margin=50;
                String S="TotalCost:"+price;

                g.drawString(title,(int)(xcor/2) , (int)(margin/2) );
                g.drawString(S,(int)(xcor/2) , ycor-(int)(margin/2) );

                double xfac=(800-2*margin)/(xmax-xmin);
                double yfac=(600-2*margin)/(ymax-ymin);
                int r=20;
                Font font = new Font(type[0], styles[2], 18);
                g.setFont(font);
                for( i=0;i<mymap.vertices.size();i++)
                {   x1=mymap.vertices.get(i).pos.x;
                    y1=mymap.vertices.get(i).pos.y;

                    g.drawOval((int)((x1-xmin)*xfac)+margin - (r/2) ,(int)((y1-ymin)*yfac) +margin - (r/2),  r, r);
                    String n="";
                    n=n+(i+1);
                    g.drawString(n ,(int)((x1-xmin)*xfac)+margin-5,(int)((y1-ymin)*yfac) +margin +5);
                }
                for(i=0;i<mymap.vertices.size();i++)
                {
                    x1=mymap.vertices.get(i).pos.x;
                    y1=mymap.vertices.get(i).pos.y;
                    for(j=0;j<mymap.adjacency.get(i).size();j++)
                    {   x2=mymap.vertices.get(mymap.adjacency.get(i).get(j).v).pos.x;
                        y2=mymap.vertices.get(mymap.adjacency.get(i).get(j).v).pos.y;

                        g.drawLine((int)((x1-xmin)*xfac)+margin,(int)((y1-ymin)*yfac) +margin,(int)((x2-xmin)*xfac)+margin,(int)((y2-ymin)*yfac) +margin);
                    }
                }

                for(i=0;i<v_cover.size();i++)
                {
                    double x3=v_cover.get(i).pos.x;
                    double y3=v_cover.get(i).pos.y;
                    g.drawOval((int)((x3-xmin)*xfac)+margin - r,(int)((y3-ymin)*yfac) +margin - r, 2*r , 2*r );
                    Color c;
                    c = new Color(255,0,0);
                    g.setColor(c);
                    g.drawLine((int)((x3-xmin)*xfac)+margin ,(int)((y3-ymin)*yfac) +margin ,(int)((x3-xmin)*xfac)+margin +(int)(r*Math.cos(v_cover.get(i).cam.angle*Math.PI/180)),(int)((y3-ymin)*yfac) +margin+(int)(r*Math.sin(v_cover.get(i).cam.angle*Math.PI/180)));

                }

            }
        }
