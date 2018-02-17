/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy;
import java.util.*;
//import java.math.*;

/**
 *
 * @author manan Sharma
 */
class coordinatedbl
{
	double x,y;
	coordinatedbl(double x1,double y1)
	{
		x=x1;
		y=y1;
	}
	coordinatedbl(coordinate other)
	{
		x=other.x;
		y=other.y;
	}
	static double distance(coordinatedbl a,coordinatedbl b)
	{
		return Math.sqrt((b.y-a.y)*(b.y-a.y)+(b.x-a.x)*(b.x-a.x));           
	}                
	public boolean equals(coordinatedbl other)
	{
		return ((x==other.x)&&(y==other.y));
	}
	public boolean equals(coordinate other)
	{
		return ((x==other.x)&&(y==other.y));
	}
	@Override
	public String toString() 
	{
		return "coordinatedbl{" + "x=" + x + ", y=" + y + '}';
	}
}
public class Camera 
{
	int Range;
	int FieldView;//angle
	int Price;
	public Camera(int range,int price)
	{
		Range=range;
		FieldView=360;
		Price=price;
	}
	public Camera(int range,int fieldview,int price)
	{
		Range=range;
		FieldView=fieldview;
		Price=price;
	}

	//@Override
	static class CameraInstance
	{
		Camera cam;
		double angle;
		CameraInstance(Camera n)
		{
			cam=n;
		}

		@Override
		public String toString() {
			return "CameraInstance{" + "cam=" + cam + ", angle=" + angle + '}';
		}
		
	}
	@Override
	public String toString() {
		return "Camera{" + "Range=" + Range + ", FieldView=" + FieldView + ", Price=" + Price + '}';
	}
	static ArrayList<ArrayList<ArrayList<Integer>>> AngularFunction;
	static int maxrange;
	static ArrayList<Camera> CameraList;
	static void initializeAngular(ArrayList<Camera> input) throws CloneNotSupportedException
	{
		CameraList=new ArrayList<>();
		for(int i=0;i<input.size();++i)
		{
			CameraList.add(input.get(i));
		}
		int maxrad=0;
		for(int i=0;i<input.size();++i)
		{
			maxrad=(maxrad<input.get(i).Range)?input.get(i).Range:maxrad;
		}
		//1->camera num
		//2->radius
		//3->angle
		maxrange=maxrad;
		AngularFunction=new ArrayList<>();
		for(int i=0;i<input.size();++i)
		{
			AngularFunction.add(new ArrayList<>());
			for(int j=0;j<=maxrad;++j)
				AngularFunction.get(i).add(new ArrayList<>());
			for(int ran=maxrad;ran>=0;--ran)
			{
				for(int angle=0;angle<=360;++angle)
				{
					int price=-1;
					if(i>0)
					{
						if(price==-1)
							price=AngularFunction.get(i-1).get(ran).get(angle);
						else
							price=Math.min(price, AngularFunction.get(i-1).get(ran).get(angle));
					}
					if(ran==0)
					{
						price=0;
					}
					else if(ran<CameraList.get(i).Range)
					{
						if(price==-1)
							price=AngularFunction.get(i).get(ran+1).get(angle);
						else
							price=Math.min(price, AngularFunction.get(i).get(ran+1).get(angle));
					}
					if(angle==0)
					{
						price=0;
					}
					else if(angle<CameraList.get(i).FieldView && CameraList.get(i).Range>=ran)
					{
						if(price==-1)
							price=CameraList.get(i).Price;
						else
							price=Math.min(price, CameraList.get(i).Price);
					}
					else if(CameraList.get(i).Range>=ran)
					{
						if(price==-1)
							price=AngularFunction.get(i).get(ran).get(angle-CameraList.get(i).FieldView)+CameraList.get(i).Price;
						else
							price=Math.min(price, AngularFunction.get(i).get(ran).get(angle-CameraList.get(i).FieldView)+CameraList.get(i).Price);

					}
					AngularFunction.get(i).get(ran).add(price);
				}
			}
		}
	}
	static ArrayList<Vertex> AngularCover(int angle,int range,coordinate pivot,int baseangle)
	{
		ArrayList<Vertex> Cover=new ArrayList<>();
		int n=AngularFunction.size()-1;
		while(true)
		{
			if(angle==0)
				break;
			boolean place=false;
			if(range<maxrange)
			{
				if(Objects.equals(AngularFunction.get(n).get(range+1).get(angle), AngularFunction.get(n).get(range).get(angle)))
					range++;
			}
			if(n>0)
			{
				if(Objects.equals(AngularFunction.get(n-1).get(range).get(angle), AngularFunction.get(n).get(range).get(angle)))
					n--;
			}
			if(angle>CameraList.get(n).FieldView)
			{
				if(AngularFunction.get(n).get(range).get(angle)==AngularFunction.get(n).get(range).get(angle-CameraList.get(n).FieldView)+CameraList.get(n).Price)
				{
					angle-=CameraList.get(n).FieldView;
					place =true;
				}
			}
			else
			{
				if(AngularFunction.get(n).get(range).get(angle)==CameraList.get(n).Price)
				{
					angle=0;
					place=true;
				}
			}
			if(place)
			{
				Vertex v=new Vertex(pivot);
				v.cam=new CameraInstance(CameraList.get(n));
				v.cam.angle=baseangle+CameraList.get(n).FieldView/2;
				baseangle+=CameraList.get(n).FieldView;
				Cover.add(v);
			}
			
		}
		return Cover;
	}
	static ArrayList<int[]> LengthCost;
	static ArrayList<int[]> partition;
	static void divide_path(int max_dist)
	{
		partition=new ArrayList<>();
		LengthCost=new ArrayList<>();
		for(int i=0;i<=max_dist;++i)
		{
			int [] temp=new int[3];
			temp[0]=temp[1]=temp[2]=-1;
			LengthCost.add(temp);
			temp=new int[3];
			temp[0]=temp[1]=temp[2]=-1;
			partition.add(temp);
		}
		for(int i=0;i<3;++i)
		{
			int h=(i+1)*5;
			LengthCost.get(0)[i]=0;
			for(int dis=1;dis<LengthCost.size();++dis)
			{
				int min=0;
				int rad=(int)Math.sqrt(((double)dis/2)*((double)dis/2)+h*h);
				int cost;
				if(rad<=maxrange)
					cost=AngularFunction.get(AngularFunction.size()-1).get(rad).get(180);
				else
					cost =-1;
				for(int sep=1;sep<dis/2;++sep)
				{
					if(cost==-1)
					{
						cost=LengthCost.get(sep)[i]+LengthCost.get(dis-sep)[i];
						continue;
					}
					if(LengthCost.get(sep)[i]+LengthCost.get(dis-sep)[i]<cost)
					{
						cost=LengthCost.get(sep)[i]+LengthCost.get(dis-sep)[i];
						min=sep;
					}
				}
				LengthCost.get(dis)[i]=cost;
				partition.get(dis)[i]=min;
			}
		}
	}
	static ArrayList<Vertex> linearcover(coordinatedbl a,coordinatedbl b,int width)
	{
		ArrayList<Vertex> Cover=new ArrayList<>();
		double d=coordinatedbl.distance(a, b);
		int i=width/5;
		double angle=Math.atan2(b.y-a.y, b.x-a.x);
		i--;
		if(partition.get((int)d)[i]==0)
		{
			Cover.addAll(AngularCover(180,(int)d/2,new coordinate((int)(a.x+b.x)/2,(int)(a.y+b.y)/2),(int)(angle*180/Math.PI)));
		}
		else
		{
			int k=partition.get((int)d)[i];
			coordinatedbl part=new coordinatedbl(a.x+k*Math.cos(angle),(a.y+k*Math.sin(angle)));
			
			Cover.addAll(linearcover(a,part,width));
			Cover.addAll(linearcover(part,b,width));
		}
		return Cover;
	}
	static class Line
	{
		coordinate u,v;
		int d;
		int width;
		int price;
		Line(coordinate u1,coordinate v1,int width1)
		{
			u=u1;
			v=v1;
			d=(int) coordinate.distance(u, v);
			width=width1;
			price=Camera.LengthCost.get(d)[width/5-1];
		}
		static ArrayList<Vertex> All(ArrayList<Line> lines)
		{
			ArrayList<Vertex> cover=new ArrayList<>();
			for(int i=0;i<lines.size();++i)
			{
				Line l=lines.get(i);
				cover.addAll(Camera.linearcover(new coordinatedbl(l.u),new coordinatedbl( l.v), l.width));
			}
			return cover;
		}
		static ArrayList<Vertex> DP1(ArrayList<Line> lines,int budget)
		{
			int DP[][];
			DP=new int[lines.size()][budget/100+1];
			
			for(int i=0;i<DP.length;++i)
			{
				for(int price=0;price<DP[i].length;++price)
				{
					int length=0;
					if(i>0)
						length=DP[i-1][price];
					if(price-lines.get(i).price/100>=0)
					{
						if(i>0)
							length=Math.max(length, DP[i-1][price-lines.get(i).price/100]+lines.get(i).d);
						else
							length=Math.max(length, lines.get(i).d);
					}
					DP[i][price]=length;
				}
			}
			ArrayList<Vertex> cover=new ArrayList<>();
			int n=DP.length-1;
			Line notplaced=null;
			int price=DP[n].length-1;System.out.println(budget);
			while(true)
			{
				boolean place=false;
				if(n<0)
					break;
				if(n>0)
				{
					if(DP[n][price]==DP[n-1][price])
					{
						notplaced=lines.get(n);
						n--;
					}
				}
				if(price-lines.get(n).price/100>=0)
				{
					if(n>0&&DP[n][price]==DP[n-1][price-lines.get(n).price/100]+lines.get(n).d)
					{
						place=true;
						price-=lines.get(n).price/100;
						--n;
					}
					else if(n==0&&DP[0][price]==lines.get(0).d)
					{
						place=true;
						price-=lines.get(n).price/100;
						--n;
					}
				}
				if(place)
				{
					cover.addAll(Camera.linearcover(new coordinatedbl(lines.get(n).u), new coordinatedbl(lines.get(n).v), lines.get(n).width));
					budget-=lines.get(n).price;
				}
				if(DP[n][price]==0)
				{
					notplaced=lines.get(n);
					n--;
				}
			}
			int dis=0;
			System.out.println(budget);
			if(budget>0&&notplaced!=null)
			{
				
				for(int i=0;i<LengthCost.size()&&budget>0;++i)
				{
					if(LengthCost.get(i)[notplaced.width/5-1]<=budget)
						dis=i;
				}
				double angle=Math.atan2(notplaced.v.y-notplaced.u.y,notplaced.v.x-notplaced.u.x);
				coordinatedbl part=new coordinatedbl((notplaced.u.x+dis*Math.cos(angle)),(notplaced.u.y+dis*Math.sin(angle)));
				
				ArrayList<Vertex>auxcover=linearcover(new coordinatedbl(notplaced.u),part,notplaced.width);
				double factor=coordinate.distance(notplaced.u, notplaced.v)/dis;
				System.out.println(part.toString()+factor);
				for(int i=0;i<auxcover.size();++i)
				{
					if(i<auxcover.size()-1&&auxcover.get(i).pos.equals(auxcover.get(i+1).pos))
					{
					
					}
					else
					{
					coordinate pos=auxcover.get(i).pos;
					System.out.println(pos);
					pos.x=(int)(notplaced.u.x+((pos.x-notplaced.u.x)*factor));
					pos.y=(int)(notplaced.u.y+((pos.y-notplaced.u.y)*factor));
					System.out.println(pos);
					}
					cover.add(auxcover.get(i));
				}
			}
			return cover;
		}
	}
	static class cameracomparator implements Comparator<Camera>
	{
		@Override
		public int compare(Camera a,Camera b)
		{
			if(a.Range/(double)a.Price>b.Range/(double)b.Price)
				return -1;
			if(a.Range/(double)a.Price<b.Range/(double)b.Price)
				return 1;
			else if(a.Range>b.Range)
				return -1;
			else if(a.Range<b.Range)
				return 1;
			else
				return 0;
		}
	}
}