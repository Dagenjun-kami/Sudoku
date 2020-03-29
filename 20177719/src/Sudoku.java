import java.io.*;
public class Sudoku {
    static String inputFileName = null;
    static String outputFileName = null;
    static int m , n,flag=0;
    static int sudoku[][];
    static int emptyNum ;
    public static void load(String args[])
    {
        m = Integer.valueOf(args[1]);
        n = Integer.valueOf(args[3]);
        inputFileName = args[5];
        outputFileName = args[7];
    }

    static void backTrace(int row , int col)
    {
        for(int i = 1 ; i <= m ;i++)
        {
            //填上行列宫不重复数字
            if(judge(sudoku,row,col,i,m))
            {
                sudoku[row][col] = i ;
                emptyNum--;
                //填完所有空缺
                if(emptyNum==0)
                {
                    print();flag=1;
                    break;
                }
                int tempArray[] ;
                tempArray = nextEmpty(row,col);
                backTrace(tempArray[0] ,tempArray[1]);
                //回溯
                sudoku[row][col] = 0;
                emptyNum++;
            }
        }

    }
    public static Boolean judge(int a[][],int row, int col, int num,int m) {
        for (int i = 0; i < m; i++) {
            if (i != row && a[i][col] == num) {
                return false;
            }
            if (i != col && a[row][i] == num) {
                return false;
            }
        }
        int x, y, px = 0 , py = 0;
        //宫的大小
        if (m == 3 || m == 5 || m == 7) {
            return true;
        }
        if (m == 4) {
            px = 2;
            py = 2;
        } else if (m == 6) {
            px = 2;
            py = 3;
        } else if (m == 8) {
            px = 4;
            py = 2;
        } else if (m == 9) {
            px = 3;
            py = 3;
        }
        //(x,y)是(row,col)所属小宫格第一格的坐标
        x = row / px * px;
        y = col / py * py;
        for (int i = x; i < x + px; i++) {
            for (int j = y; j < y + py; j++) {
                if (i != row && j != col && a[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    static int [] nextEmpty(int row,int col)//寻找未填空缺坐标
    {
        int c[] = new int[2];
        for(int i = row ; i < m ; i++)
        {
            for(int j = 0;j < m ; j++)
            {
                if(sudoku[i][j]==0)
                {
                    c[0] = i;
                    c[1] = j;
                    return c;
                }
            }
        }
        return c;
    }
    static void fileIO()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(inputFileName));
            PrintStream ps  = new PrintStream(new FileOutputStream(outputFileName,true));
            System.setOut(ps);
            for(int p=0 ; p<n ; p++)
            {
                int i=0;String s;
                boolean first = true;
                int emptyRow = 0,emptyCol = 0;
                //读入文件写入数组循环
                for(int k=0 ; k < m ; k++) {
                    if ((s = br.readLine()) != null);
                    {
                        String[] temp = s.split(" ");
                        for (int j = 0; j < m; j++) {
                            sudoku[i][j] = Integer.parseInt(temp[j]);
                            if (sudoku[i][j] == 0)
                            {
                                if (first) {
                                    emptyRow = i;
                                    emptyCol = j;
                                    first = false;
                                }
                                emptyNum++;
                            }
                        }
                        i++;
                    }
                }
                backTrace(emptyRow,emptyCol);
                if(flag==0){
                    System.out.println(("此数独无解"));
                }
                if(p<n-1) {
                    System.out.println();
                }
                br.readLine();
            }
            br.close();
            ps.close();
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
            System.exit(-1);
        }
        catch (IOException e2)
        {
            e2.printStackTrace();
            System.exit(-1);
        }
    }

    static void print()
    {
        int i,j;
        flag=1;
            for(i = 0 ; i < m ; i++)
            {
                for(j = 0;j < m ; j++)
                {
                    System.out.print(sudoku[i][j]+ " ");
                }
                System.out.println();
            }

    }
    public static void main(String args[]) {
        load(args);
        sudoku = new int[m][m];
        fileIO();
    }
}