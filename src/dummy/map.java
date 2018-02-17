/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy;

/**
 *
 * @author Manasvi
 */

/*import java.io.IOException;*/
import static java.lang.Math.*;
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
	static double distance(coordinate a,coordinate b)
	{
		return Math.sqrt((b.y-a.y)*(b.y-a.y)+(b.x-a.x)*(b.x-a.x));           
	}                
	public boolean equals(coordinate other)
	{
		return ((x==other.x)&&(y==other.y));
	}
	@Override
	public String toString() 
	{
		return "coordinate{" + "x=" + x + ", y=" + y + '}';
	}
}
class edge
{
	int u,v;   
	int width; // edge length or weight
	int priority;
	int no;
	public edge(edge e )
	{
		width=e.width;
		no=e.no;
		u=e.u;
		v=e.v;
	}
	public edge(int u1,int v1)
	{
		u=u1;
		v=v1;
		width=0;
		priority=0;
	}
	public edge(int u1,int v1,int p1,int w)
	{
		u=u1;
		v=v1;
		width=w;
		priority=p1;
	}
	public boolean equals(edge e)
	{
		return ((u==e.u)&&(v==e.v));
	}
}
public class map 
{
	int max_dist;
	ArrayList<Vertex> vertices;
	ArrayList<ArrayList<halfedge> > adjacency;	
	public ArrayList <Camera> cc= new ArrayList<>();
	int range;
	public int budget;
	class halfedge
	{
		int width;
		int v;
		int priority;
		public halfedge(int target,int p,int w)
		{
			v=target;
			priority=p;
			width=w;
		}
		public halfedge()
		{
			// info=0;
			v=0;
			priority=0;
		}		
	}
	public map()
	{
		adjacency=new ArrayList<>();
		vertices=new ArrayList<>();
	}
	void initialize(ArrayList<Vertex> vertices1,ArrayList<edge> edges)
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
			adjacency.add(new ArrayList <>());
		}
		for(int i=0;edges!=null&&i<edges.size();++i)
		{
			edge temp=edges.get(i);
			vertices.get(temp.u).priority=Math.max(vertices.get(temp.u).priority, temp.priority);
			vertices.get(temp.v).priority=Math.max(vertices.get(temp.v).priority, temp.priority);
			adjacency.get(temp.u).add(new halfedge(temp.v,temp.priority,temp.width));
			adjacency.get(temp.v).add(new halfedge(temp.u,temp.priority,temp.width));
		}
	}
	void Add(Vertex v)
	{
		if(vertices==null)
		{
			vertices=new ArrayList <>();
		}
		vertices.add(v);
	}
	void Add(edge e)
	{
		if(adjacency==null)
		{
			adjacency=new ArrayList <>();
			for(int i=0;i<vertices.size();++i)
			{
				adjacency.add(new ArrayList <>());
			}
		}
		edge temp=e;
		adjacency.get(temp.u).add(new halfedge(temp.v,temp.priority,temp.width));
		adjacency.get(temp.v).add(new halfedge(temp.u,temp.priority,temp.width));
	}
	public void output()
	{
		for(int i=0;i<vertices.size();++i)
		{
			System.out.println(i+" "+vertices.get(i).pos);
		}
	}       
	ArrayList<Vertex> BudgetedRangeCover(int Budget)
	{
		int rembudget=Budget;
		int auxbud=0;
		ArrayList<Vertex> vertexcover;
		vertexcover=new ArrayList<>();    
		final ArrayList<Vertex> vercopy=new ArrayList<>();
		ArrayList<ArrayList<map.halfedge> > adjcopy=new ArrayList<>();
		for(int i=0;i< vertices.size();++i)
		{
			vercopy.add(vertices.get(i));
		}
		for(int i=0;i<adjacency.size();++i)
		{
			adjcopy.add(new ArrayList<>()); 
			for(int j=0;j<adjacency.get(i).size();++j)
				adjcopy.get(i).add(new halfedge(adjacency.get(i).get(j).v,adjacency.get(i).get(j).priority,adjacency.get(i).get(j).width));
		}
		class comparator implements Comparator<Integer>
		{
			@Override
			public int compare( Integer a, Integer b)
			{
				int flag =0;
				if(vercopy.get(a).priority >vercopy.get(b).priority)
					flag= -1;
				else if(vercopy.get(a).priority<vercopy.get(b).priority)
					flag=1;
				else if(vercopy.get(a).priority == vercopy.get(b).priority)
				{ 
					if(adjcopy.get(a).size() > adjcopy.get(b).size())
						flag=-1;
					else if(adjcopy.get(a).size() < adjcopy.get(b).size())
						flag=1;
					else if(adjcopy.get(a).size()==1)
					{
						if(adjcopy.get(a).get(0).width>adjcopy.get(b).get(0).width)
							flag=-1;
						else if(adjcopy.get(a).get(0).width<adjcopy.get(b).get(0).width)
							flag=1;
					}
				}
				return flag;
			}
		}		
		PriorityQueue<Integer> queue;
		queue=new PriorityQueue(new comparator());
		for(int i=0; i<vercopy.size();i++)
		{
			queue.add(i);
		}
		ArrayList<Camera.Line> lines=new ArrayList<>();
		int curpriority=vercopy.get(queue.peek()).priority;
		while(queue.size()>0)
		{
			int q;
			q=queue.remove();
			if(adjcopy.get(q).isEmpty())
				continue;
			if(vercopy.get(q).priority!=curpriority)
			{
				if(!lines.isEmpty())
				{
					
					if(rembudget>0)
						vertexcover.addAll(Camera.Line.All(lines));
					else
					{
						vertexcover.addAll(Camera.Line.DP1(lines, rembudget+auxbud));
						lines.clear();
						break;
					}
					lines.clear();
				}
				
			}
			int tar=adjcopy.get(q).get(0).v;
			if(adjcopy.get(q).size()==1&&adjcopy.get(tar).size()==1&&vercopy.get(q).priority==vercopy.get(tar).priority)
			{
				coordinate a=vercopy.get(q).pos;
				coordinate b=vercopy.get(tar).pos;
				
				lines.add(new Camera.Line(a, b, adjcopy.get(q).get(0).width));
				rembudget-=Camera.LengthCost.get((int) coordinate.distance(a, b))[adjcopy.get(q).get(0).width/5-1];
				auxbud+=Camera.LengthCost.get((int) coordinate.distance(a, b))[adjcopy.get(q).get(0).width/5-1];
				System.out.println(rembudget+" "+auxbud);//vertexcover.addAll(Camera.linearcover(a, b,adjcopy.get(q).get(0).width));
				queue.remove(tar);
			}
			else
			{
				
				int min=(int) coordinate.distance(vercopy.get(q).pos, vercopy.get(tar).pos);
				for(int i=0;i<adjcopy.get(q).size();++i)
				{
					int target=adjcopy.get(q).get(i).v;
					int dis=(int) coordinate.distance(vercopy.get(q).pos, vercopy.get(target).pos);
					min=Math.min(min, dis);
				}
				
				range=Math.min(min, Camera.maxrange);
				rembudget-=Camera.AngularFunction.get(Camera.CameraList.size()-1).get(range).get(360);
				if(rembudget<0)
					break;
				vertexcover.addAll(Camera.AngularCover(360, range, vercopy.get(q).pos, 0));
				System.out.println(vercopy.get(q).toString()+adjcopy.get(q).size());
				for(int i=0;i<adjcopy.get(q).size();i++)
				{      				
					int target=adjcopy.get(q).get(i).v;
					coordinate a=new coordinate(vercopy.get(q).pos.x,vercopy.get(q).pos.y);
					coordinate b=new coordinate(vercopy.get(target).pos.x,vercopy.get(target).pos.y);
					int d=(int)coordinate.distance(a, b);
					if(d > range)
					{
						double t;
						t=atan2(b.y-a.y,b.x-a.x);
						//  Vertex v=new Vertex(new coordinate(a.x+(d*cos(t)),a.y+(d*sin(t))),new edge(adjcopy.size()+count,i,adjcopy.get(q).get(i).priority));
						Vertex v=new Vertex(new coordinate(a.x+(int)(range*cos(t)),a.y+(int)(range*sin(t))),adjcopy.get(q).get(i).priority);
						v.degree=1;
						vercopy.add(v);                                         
						adjcopy.add(new ArrayList <>());
						int ver=adjcopy.get(q).get(i).v;
						int uer=vercopy.size()-1;
						edge temp=new edge(uer,ver,adjcopy.get(q).get(i).priority,adjcopy.get(q).get(i).width);
						adjcopy.get(temp.u).add(new halfedge(temp.v,temp.priority,temp.width));
						//adjcopy.get(temp.v).add(new halfedge(temp.u,temp.priority/*,temp.info*/));
						for(int s=0; s<adjcopy.get(ver).size();s++)
						{
							if(adjcopy.get(ver).get(s).v==q)
							{
								adjcopy.get(ver).get(s).priority=temp.priority;
								adjcopy.get(ver).get(s).v=vercopy.size()-1;
							}
						}                              
						queue.add(vercopy.size()-1); 					
					}
					else
					{
						int j;
						for(j=0;j<=adjcopy.get(target).size();++j)
						{
							if(adjcopy.get(target).get(j).v==q)
								break;
						}
						adjcopy.get(target).remove(j);
					}
				}
				
			}
		}
		if(!lines.isEmpty())
		{
			System.out.println(rembudget);
			if(rembudget>=0)
				vertexcover.addAll(Camera.Line.All(lines));
			else
			{
				vertexcover.addAll(Camera.Line.DP1(lines, rembudget+auxbud));
				lines.clear();
			}
			lines.clear();
		}
		System.out.println("the vertex list is");
		for(int i=0;i<vertexcover.size();++i)
			System.out.println(vertexcover.get(i));
		System.out.println("end of list");
		return vertexcover;
		//System.out.print(v+" ");		
	}
	ArrayList<Vertex> FullRangeCover()
	{
		ArrayList<Vertex> vertexcover;
		vertexcover=new ArrayList<>();    
		final ArrayList<Vertex> vercopy=new ArrayList<>();
		ArrayList<ArrayList<map.halfedge> > adjcopy=new ArrayList<>();
		for(int i=0;i< vertices.size();++i)
		{
			vercopy.add(vertices.get(i));
		}
		for(int i=0;i<adjacency.size();++i)
		{
			adjcopy.add(new ArrayList<>()); 
			for(int j=0;j<adjacency.get(i).size();++j)
				adjcopy.get(i).add(new halfedge(adjacency.get(i).get(j).v,adjacency.get(i).get(j).priority,adjacency.get(i).get(j).width));
		}
		class comparator implements Comparator<Integer>
		{
			@Override
			public int compare( Integer a, Integer b)
			{
				int flag =0;
				if(vercopy.get(a).priority >vercopy.get(b).priority)
					flag= -1;
				else if(vercopy.get(a).priority<vercopy.get(b).priority)
					flag=1;
				else if(vercopy.get(a).priority == vercopy.get(b).priority)
				{ 
					if(adjcopy.get(a).size() > adjcopy.get(b).size())
						flag=-1;
					else if(adjcopy.get(a).size() < adjcopy.get(b).size())
						flag=1;
					else if(adjcopy.get(a).size()==1)
					{
						if(adjcopy.get(a).get(0).width>adjcopy.get(b).get(0).width)
							flag=-1;
						else if(adjcopy.get(a).get(0).width<adjcopy.get(b).get(0).width)
							flag=1;
					}
				}
				return flag;
			}
		}		
		PriorityQueue<Integer> queue;
		queue=new PriorityQueue(new comparator());
		for(int i=0; i<vercopy.size();i++)
		{
			queue.add(i);
		}
		ArrayList<Camera.Line> lines=new ArrayList<>();
		while(queue.size()>0)
		{
			int q;
			q=queue.remove();
			if(adjcopy.get(q).isEmpty())
				continue;
			int tar=adjcopy.get(q).get(0).v;
			if(adjcopy.get(q).size()==1&&adjcopy.get(tar).size()==1&&vercopy.get(q).priority==vercopy.get(tar).priority)
			{
				coordinate a=vercopy.get(q).pos;
				coordinate b=vercopy.get(tar).pos;
				
				vertexcover.addAll(Camera.linearcover(new coordinatedbl(a), new coordinatedbl(b),adjcopy.get(q).get(0).width));
				queue.remove(tar);
			}
			else
			{
				
				int min=(int) coordinate.distance(vercopy.get(q).pos, vercopy.get(tar).pos);
				for(int i=0;i<adjcopy.get(q).size();++i)
				{
					int target=adjcopy.get(q).get(i).v;
					int dis=(int) coordinate.distance(vercopy.get(q).pos, vercopy.get(target).pos);
					min=Math.min(min, dis);
				}
				
				range=Math.min(min, Camera.maxrange);
				vertexcover.addAll(Camera.AngularCover(360, range, vercopy.get(q).pos, 0));
				System.out.println(vercopy.get(q).toString()+adjcopy.get(q).size());
				for(int i=0;i<adjcopy.get(q).size();i++)
				{      				
					int target=adjcopy.get(q).get(i).v;
					coordinate a=new coordinate(vercopy.get(q).pos.x,vercopy.get(q).pos.y);
					coordinate b=new coordinate(vercopy.get(target).pos.x,vercopy.get(target).pos.y);
					int d=(int)coordinate.distance(a, b);
					if(d > range)
					{
						double t;
						t=atan2(b.y-a.y,b.x-a.x);
						//  Vertex v=new Vertex(new coordinate(a.x+(d*cos(t)),a.y+(d*sin(t))),new edge(adjcopy.size()+count,i,adjcopy.get(q).get(i).priority));
						Vertex v=new Vertex(new coordinate(a.x+(int)(range*cos(t)),a.y+(int)(range*sin(t))),adjcopy.get(q).get(i).priority);
						v.degree=1;
						vercopy.add(v);                                         
						adjcopy.add(new ArrayList <>());
						int ver=adjcopy.get(q).get(i).v;
						int uer=vercopy.size()-1;
						edge temp=new edge(uer,ver,adjcopy.get(q).get(i).priority,adjcopy.get(q).get(i).width);
						adjcopy.get(temp.u).add(new halfedge(temp.v,temp.priority,temp.width));
						//adjcopy.get(temp.v).add(new halfedge(temp.u,temp.priority/*,temp.info*/));
						for(int s=0; s<adjcopy.get(ver).size();s++)
						{
							if(adjcopy.get(ver).get(s).v==q)
							{
								adjcopy.get(ver).get(s).priority=temp.priority;
								adjcopy.get(ver).get(s).v=vercopy.size()-1;
							}
						}                              
						queue.add(vercopy.size()-1); 					
					}
					else
					{
						int j;
						for(j=0;j<=adjcopy.get(target).size();++j)
						{
							if(adjcopy.get(target).get(j).v==q)
								break;
						}
						adjcopy.get(target).remove(j);
					}
				}
				
			}
		}
		System.out.println("the vertex list is");
		for(int i=0;i<vertexcover.size();++i)
			System.out.println(vertexcover.get(i));
		System.out.println("end of list");
		return vertexcover;
		//System.out.print(v+" ");		
	}
}
class Vertex
{
	coordinate pos;
	int priority;
	int degree;
	int label;        
	Camera.CameraInstance cam;
	public boolean equals(Vertex other)
	{
		return pos.equals(other.pos);
	}
	public Vertex(coordinate pos1,int p)
	{
		pos=pos1;
		priority=p;
		degree=0;
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
	@Override
	public String toString() {
		return "Vertex{" + "pos=" + pos + ", priority=" + priority + ", degree=" + degree + ", label=" + label + ", cam=" + cam + '}';
	}
}