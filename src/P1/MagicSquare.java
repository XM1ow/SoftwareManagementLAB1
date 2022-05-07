package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MagicSquare {
	public static int Max = 200;
	public static boolean[] visited = new boolean[Max * Max + 1];
	
	public static void main(String[] args) throws IOException{
		String ans = "";
		for(char i = '1' ; i <= '5'; i++) {
			ans = String.valueOf(isLegalMagicSquare("src/P1/txt/" + i + ".txt"));
			System.out.println("testing " + i + ".txt  " + ans);
		}
		
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		scan.close();
		if(n <= 0 || n % 2 == 0) {
			System.out.println("False");
			return;
		}
		generateMagicSquare(n);
		ans = String.valueOf(isLegalMagicSquare("src/P1/txt/6.txt"));
		System.out.println("testing 6.txt  " + ans);
		
		return;
	}
	//Generate函数
	public static boolean generateMagicSquare(int n) throws FileNotFoundException,IOException{
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
			if (row == 0)
				row=n-1;
			else 
				row--;
			if (col == (n-1))
				col=0;
			else 
				col++;
			}
		}
		File file = new File("src/P1/txt/6.txt");
		BufferedWriter putline = new BufferedWriter(new FileWriter(file));
		for(i = 0; i < n; i++) {
			for(j = 0; j < n; j++) {
				System.out.print(magic[i][j] + "\t");
				putline.write(magic[i][j] + "\t");
			}
			System.out.println();
			putline.write("\n");
			}
		putline.close();
	return true;
	}
	
	//编写一个识别MagicSquare的函数
	public static int [][] square = new int[Max][Max];
	public static boolean isLegalMagicSquare(String fileName) throws IOException{
		File f = new File(fileName);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String line = "";
		Arrays.fill(visited, false);
		int col = 0,row = 0;
		while ((line = br.readLine()) != null) {
			String[] l = line.split("\t");
			col = l.length;
			for(int i = 0; i < col; i++) {
				char ch = l[i].charAt(l[i].length()/2);
				if(ch == ' ')
					return false;
				square[row][i] = Integer.valueOf(l[i].trim());
				if(square[row][i] <= 0 || visited[square[row][i]])
					return false;
				else
					visited[square[row][i]] = true;
			}
			row++;
		}
		br.close();
		
		//矩阵检测
		if (col != row)
			return false;
		
		int sum = 0;
		int s1 = 0,s2 = 0;
		int i = 0;
		//对角检测
		for (; i < row; i++) {
			s1 += square[i][i];
			s2 += square[i][col-i-1];
		}
		if(s1 != s2)
			return false;
		else 
			sum = s1;
		
		s1 = s2 = 0;
		i = 0;
		int j = 0;
		for(;i < row; i++) {
			for(;j < col; j++) {
				s1 += square[i][j];
			}
			if (s1 != sum)
				return false;
		}//行检测
		i = j =0;
		for(;i < col; i++) {
			for(;j < row; j++) {
				s2 += square[j][i];
			}
			if (s2 != sum)
				return false;
		}//列检测
		return true;
	}
}

