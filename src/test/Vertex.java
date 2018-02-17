package test;

import java.io.IOException;
import java.util.*;



class coordinate
    {
        public int x,y;
        public coordinate()
        {
            x=y=0;
        }
        public coordinate(int x1,int y1)
        {
            this.x=x1;
            this.y=y1;
        }
        public boolean equals(coordinate other)
        {
            return ((x==other.x)&&(y==other.y));
        }
        @Override public String toString()
        {
            return "("+x+","+y+")";
        }
    }
class edge
    {
        int u,v;   
        double info; // edge length or weight
        int priority;
        int no;
        public edge(edge e )
        {
            info=e.info;
                        no=e.no;
                        u=e.u;
                        v=e.v;
                         }
        public edge(int u1,int v1)
        {
            u=u1;
            v=v1;
            info=0;
	    priority=0;
        }
        public edge(int u1,int v1,int info1)
        {
            u=u1;
            v=v1;
            info=info1;
	    priority=0;
        }
        public boolean equals(edge e)
        {
            return ((u==e.u)&&(v==e.v));
        }
    }
 class map {
    
    
    public ArrayList<Vertex> vertices;
    ArrayList<ArrayList<halfedge> > adjacency;
    
   /* public map(map m1)
    {
        this.initialise(m1.vertices,)
    }*/
    int range=5;
        class halfedge
        {
            double info;
            int v;
            public halfedge(int target,double info1)
            {
                v=target;
                info=info1;
            }

        }
    public map()
    {
        adjacency=new ArrayList<>();
        vertices=new ArrayList<>();
    }
    public void initialize(ArrayList<Vertex> vertices1,ArrayList<edge> edges)
    {
        if(vertices==null)
        {
            vertices=new ArrayList<>();
            adjacency=new ArrayList< >();
        }
        vertices.clear();
        vertices.addAll(vertices1);
        adjacency.clear();
        //adjacency.setSize(vertices.size());
        
        for(int i=0;i<vertices.size();++i)
        {
            adjacency.add(new ArrayList<halfedge>());
        }
        for(int i=0;edges!=null&&i<edges.size();++i)
        {
            edge temp=edges.get(i);
            adjacency.get(temp.u).add(new halfedge(temp.v,temp.info));
            adjacency.get(temp.v).add(new halfedge(temp.u,temp.info));
        }
    }
    public void Add(Vertex v)
    {
        if(vertices==null)
        {
            vertices=new ArrayList<Vertex>();
        }
        vertices.add(v);
    }
    public void Add(edge e)
    {
        if(adjacency==null)
        {
            adjacency=new ArrayList<ArrayList<halfedge> >();
            for(int i=0;i<vertices.size();++i)
            {
                adjacency.add(new ArrayList<halfedge>());
            }
        }
        edge temp=e;
        adjacency.get(temp.u).add(new halfedge(temp.v,temp.info));
        adjacency.get(temp.v).add(new halfedge(temp.u,temp.info));
    }
    public void output()
    {
       for(int i=0;i<vertices.size();++i)
       {
           System.out.println(i+" "+vertices.get(i).pos);
       }
    }
    ArrayList<Vertex> VertexCover()
    {
        ArrayList<Vertex> vertexcover;
        vertexcover=new ArrayList<>();
        ArrayList<ArrayList<halfedge> > adjcopy;
        adjcopy=new ArrayList<>();
        adjcopy.addAll(adjacency);
        int cover=0;
        while(cover<vertices.size())
        {
            int max=0;
            for(int i=0;i<adjcopy.size();++i)
            {
                if(adjcopy.get(max).size()<adjcopy.get(i).size())
                    max=i;
            }
            vertexcover.add(vertices.get(max));
            cover+=adjcopy.get(max).size()+1;
            for(int i=0;i<adjcopy.get(max).size();++i)
            {
                int target=adjcopy.get(max).get(i).v;
                int j;
                for(j=0;j<adjcopy.get(target).size();++j)
                {
                    if(adjcopy.get(target).get(j).v==max)
                        break;
                }
                if(j<adjcopy.get(target).size())
                    adjcopy.get(target).remove(j);
            }
            adjcopy.get(max).clear();
        }
        return vertexcover;
    }
        ArrayList<Vertex> RangeCover()
	{ 
                int flag=0;
		ArrayList<Vertex> vercopy=new ArrayList<>();
		ArrayList<ArrayList<map.halfedge> > adjacencycopy=new ArrayList<>();
		for(int i=0;i< vertices.size();++i)
		{
			vercopy.add(vertices.get(i));
		}
		for(int i=0;i<adjacency.size();++i)
			adjacencycopy.get(i);
		class comparator implements Comparator<Integer>
		{
		@Override public int compare(Integer a,Integer b)
			{
                            int flag =0;
				if(vercopy.get(a).priority>vercopy.get(b).priority)
					flag= -1;
				else if(vercopy.get(a).priority<vercopy.get(b).priority)
					flag=1;
				else if(vercopy.get(a).priority == vercopy.get(b).priority)
                                { 
                                    if(vercopy.get(a).degree >= vercopy.get(b).degree)
                                        flag=-1;
                                    else 
                                        flag=1;
                                }
                            return flag;
			}
		}
		ArrayList<Vertex> cover=new ArrayList<>();
		PriorityQueue<Vertex> queue=new PriorityQueue<>();
                for(int i=0; i<vercopy.size();i++)
                {
                    queue.add(vercopy.get(i));                    
                }
                for(int i=0;i<queue.size();i++)
                {
                    System.out.print(queue.remove()+" ");
                }
    		return cover;
	}
	
    }
public class Vertex
    {
        coordinate pos;
	int priority;
        int degree;
	int label;
        public boolean equals(Vertex other)
        {
            return pos.equals(other.pos);
        }
        public Vertex(coordinate pos1)
        {
            pos=pos1;
	    priority=0;
	    degree=0;
        }
        public Vertex()
        {
        }
        
        public static void main(String[] args) throws IOException
        {
            ArrayList<Vertex> temp=new ArrayList<>();
            ArrayList<Vertex> inputvertex =new ArrayList<>();
                Scanner input=new Scanner(System.in);
        System.out.println("enter the number of vertices");
        int n;
        n=input.nextInt();
        System.out.println("enter all the coordinates");
       // ArrayList<Vertex> inputvertex=new ArrayList<>();
        for(int i=0;i<n;++i)
        {
            inputvertex.add(new Vertex(new coordinate(input.nextInt(),input.nextInt())));
        }
        System.out.println("enter the number of adges in the map");
        int k=input.nextInt();
        ArrayList<edge> inputedges=new ArrayList<>();
        for(int i=0;i<k;++i)
        {
            inputedges.add(new edge(input.nextInt(),input.nextInt()));
        }
        map mymap=new map();
        mymap.initialize(inputvertex, inputedges);
        mymap.RangeCover();
        }
    }

