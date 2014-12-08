package assign4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class Kmeans {


	public static int smallest(double x[])
	{
		double a = x[0];
		int b = 0;
		for (int i = 0; i < x.length; i++)
			if (x[i]<a)
			{
				a = x[i];
				b = i;
			}
		return b;
	}


	public static double[][] add_to_clusters(String data[][],double data1[][],double centroid_data[][])
	{
		String [][]clusters = new String [4][128];
		double clusters_data[][][] = new double [4][128][41];
		int c0=0,c1=0,c2=0,c3=0;
		int results=0;
		double [] result = new double[4];
		double [] compare = new double[4];
		for(int i=0; i<128;i++)
		{

			for(int j=0;j<4;j++)
			{
				result[j]=0;
				for(int k=0;k<40;k++)
				{
					if(k<1)
						result[j] += Math.pow((centroid_data[j][k] - data1[i][k]),2);
				}
				result[j]=Math.sqrt(result[j]);	
				//System.out.println(result[j]);
			}
			int smallest = smallest(result);
			//System.out.println(smallest);
			switch(smallest)
			{
			case 0: clusters[smallest][c0] = data[i][0];
			for(int k=0;k<40;k++)
				clusters_data[smallest][c0][k] = data1 [i][k];
			c0++;
			break;

			case 1: clusters[smallest][c1] = data[i][0];
			for(int k=0;k<40;k++)
				clusters_data[smallest][c0][k] = data1 [i][k];
			c1++;
			break;

			case 2: clusters[smallest][c2] = data[i][0];
			for(int k=0;k<40;k++)
				clusters_data[smallest][c0][k] = data1 [i][k];
			c2++;
			break;

			case 3: clusters[smallest][c3] = data[i][0];
			for(int k=0;k<40;k++)
				clusters_data[smallest][c0][k] = data1 [i][k];
			c3++;
			break;
			}
		}
		display(clusters);

		within_ss(clusters_data,centroid_data,clusters);
		int[] a= new int[4];
		double centroids[][] = new double[4][40];
		double addition[][] = new double[4][40];

			a[0] = c0;
			a[1] = c1;
			a[2] = c2;
			a[3] = c3;
			
		//new centroids
		for(int i=0;i<4;i++)
			{
				for(int k=0;k<40;k++)
				{
					for(int j=0;j<128;j++)
					{
						addition[i][k] += clusters_data[i][j][k];
					}
					centroids[i][k]=addition[i][k]/a[i];
				}
			}
		
		/*for(int i=0;i<4;i++)
		{
			for(int j=0;j<40;j++)
			{
				System.out.print(centroids[i][j]+ " ");
			}
			System.out.println();
		}*/
		
		return centroids;
	}

	public static void within_ss(double x[][][],double centroid_data[][],String clusters[][])
	{
		double total=0;
		double a[]=new double[4];
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<128;j++)
			{
				if(clusters[i][j]!=null)
					for(int k=0;k<40;k++)
					{
						a[i]+=Math.pow((centroid_data[i][k] - x[i][j][k]),2);
					}
			}
			total += a[i];
			System.out.println("Cluster "+(i+1)+" SS_Score = "+a[i]);
		}
		System.out.println("Total SS score = "+ total);
	}

	public static void display(String clusters[][])
	{
		for(int i=0; i<4;i++)
		{
			System.out.print("Cluster "+ (i+1) + " = ");
			for(int j=0;j<128;j++)
			{
				if(clusters[i][j]!=null)
				{
					System.out.print(clusters[i][j]+ " ");
				}
			}
			System.out.println();
		}
	}
	public static void main(String args[]) throws Exception
	{
		String a;
		int count = 0;
		String[][] data = new String[128][41];
		double [][]data1 = new double[128][40];
		BufferedReader br = new BufferedReader(new FileReader("E:\\extra_data.txt"));

		while ((a = br.readLine())!=null)
		{
			a=a.replaceAll("\\s+"," ");
			data[count] = a.split(" ");
			count++;
		}

		for(int i=0; i<count;i++)
		{
			for(int j=0;j<40;j++)
			{
				data1[i][j]=Double.parseDouble(data[i][j+1]);
				//System.out.print(data[i][j]);
			}
		}

		double [][]centroid_data = new double [4][40];
		int []rand = new int [4];
		for(int i=0;i<4;i++)
		{
			Random r = new Random();
			rand[i] =r.nextInt(41);
			//System.out.print(rand[i] + " - ");
			//System.out.println("Centroid "+ i + "="+ );

			for(int j=1;j<41;j++)
			{

				//System.out.print(data[rand[i]][j] + " ");
				String temp = data[rand[i]][j].toString();
				Double temp1 = Double.parseDouble(temp);
				centroid_data[i][j-1]= temp1;
			}
			//System.out.println("\n");
		}

		for(int i=0;i<5;i++)
		{
			System.out.println("Run " + (i+1));
			centroid_data = add_to_clusters(data,data1,centroid_data);
			System.out.println();
		}
	}
}
