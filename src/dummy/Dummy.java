/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy;
import java.io.*;
import java.util.*;

class hold
    {
        int h;
        int v_no;
        int more_e;
        int chb[];
        int budget;
        
        public hold()
        {
            h=1;
            v_no=0;
            more_e=1;
            chb=new int[3];
            budget=0;
            for(int i=0;i<3;i++)
                chb[i]=0;
        }
    }
public class Dummy {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
   
    public static void main(String[] args) throws IOException 
	{
        Scanner input=new Scanner(System.in);
        
        ArrayList <Vertex> inputvertex=new ArrayList<>();
        ArrayList <edge> inputedge = new ArrayList<>();
        
        map mymap=new map();
        hold h1=new hold();
        NewJFrame f0=new NewJFrame(inputvertex,inputedge,mymap,h1);
        f0.setVisible(true);
        while(h1.h!=0){
           System.out.print("");
        }
        
        
        h1.h=1;
        
        ContactEditorUI f1 =new ContactEditorUI(inputvertex,inputedge,mymap,h1);
        f1.setVisible(true);
        
        while(h1.h!=0)
        {System.out.print("");
        }
        
        /*for(int i=0;i<inputvertex.size();i++)
            System.out.println(inputvertex.get(i).toString());*/
        
        
        h1.h=1;
        ContactEditorUI2 f2 =new ContactEditorUI2(inputvertex,inputedge,mymap,h1);
        while(h1.more_e!=0)
        {    
         
         f2.setVisible(true);
         while(h1.h!=0){
         System.out.print("");}
        }
        
        for(int i=0;i<inputedge.size();i++)
            System.out.println(inputedge.get(i).u+","+inputedge.get(i).v+","+inputedge.get(i).width+","+inputedge.get(i).priority);
        
        h1.h=1;
        
        ContactEditorUI3 f3 =new ContactEditorUI3(h1);
        f3.setVisible(true);
        
        while(h1.h!=0){
            System.out.print("");
        }
        
        /*for(int i=0;i<3;i++)
            System.out.print(".......\n"+h1.chb[i]+",");
                 
            System.out.println("\n"+h1.budget);*/
        
              int max_dist=0;
       double d;
        for(int i=0;i<inputedge.size();i++)
        {  
           int  t1=inputedge.get(i).u;
             int t2=inputedge.get(i).v;
            
             d=Math.sqrt(Math.pow(inputvertex.get(t2).pos.x-inputvertex.get(t1).pos.x,2)+Math.pow(inputvertex.get(t2).pos.y-inputvertex.get(t1).pos.y,2));
             if(max_dist<d)
                 max_dist=(int)d;
              inputvertex.get(t1).degree++;
              inputvertex.get(t2).degree++;           
        }
        
        
        
        mymap.max_dist=max_dist;
        
        ArrayList <Camera> cc=new ArrayList<>();
     try{  
      
    BufferedReader fp=new BufferedReader(new FileReader("abc.txt"));  
    String s;  
    String[] words ;
    
       while((s=fp.readLine())!=null){  
        words = s.split("\\s+");
            for (int i = 0; i < words.length; i++) 
              {
             words[i] = words[i].replaceAll("[^\\w]", "");
              }
           cc.add(new Camera(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[0])));
       
       
       
           }  
      
           fp.close();
           
           Collections.sort(cc,new Camera.cameracomparator());
           
           for(int i=0;i<cc.size();i++)
           {
               mymap.cc.add(cc.get(i));
           }
           
           
           for(int i=0;i<cc.size();i++)
           {
               System.out.println(cc.get(i).Price+" "+cc.get(i).Range+" "+cc.get(i).FieldView);
           }   
           Camera.initializeAngular(cc);
           Camera.divide_path(max_dist);
        }catch(Exception e){System.out.println(e);}  
        
        mymap.initialize(inputvertex, inputedge);
        //ArrayList<Vertex>cover= mymap.VertexCover();
        //ArrayList<Vertex>cover= mymap.BudgetedRangeCover(h1.budget);
		if(h1.chb[1]==1)
		{
			ArrayList<Vertex>cover= mymap.BudgetedRangeCover(h1.budget);
			graphGui g=new graphGui(mymap,cover,"Budgeted");
			g.draw_graph();
		}
		if(h1.chb[2]==1)
		{
			ArrayList<Vertex>cover= mymap.FullRangeCover();
			graphGui g=new graphGui(mymap,cover,"Full Fledged");
			g.draw_graph();
		}
     }
    
}
