import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
  static Queue<Ceil> openList = new LinkedList<Ceil>();
  static Ceil[][] ceiledMaze = new Ceil[1][1];
  static char[][] sourceMaze = new char[1][1];
  static boolean[][] visitedMaze = new boolean[1][1]; 
  static char controlRoom = 'C';
  static char point = '.';
  static char wall = '#';
  static int R, C, KY, KX;;
  
  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    boolean isCFound = false;
        R = in.nextInt(); // number of rows.
        C = in.nextInt(); // number of columns.
        int A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
        int yC=-1;//coordinates of C
        int xC=-1;
        int d=0;   
        // game loop
        while (true) {
            KY = in.nextInt(); // row where Kirk is located.
            KX = in.nextInt(); // column where Kirk is located.
            System.err.println("MAIN KY= "+KY+" KX - " + KX);
            if (d==0) {
              visitedMaze = new boolean[R][C];
              sourceMaze = new char[R][C];
              ceiledMaze = new Ceil[R][C];
              for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                  visitedMaze[i][j] = false;                          
                }                    
              }               
              d=1;
            }
            for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) {
                  sourceMaze[i][j] = ROW.charAt(j);
                }
              }
              visitedMaze[KY][KX] = true;
              discoverWorld(sourceMaze,visitedMaze,KY,KX, in);
              for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {                
                  System.err.print(sourceMaze[i][j]);
                  visitedMaze[i][j] = false;  
                  if(sourceMaze[i][j] == controlRoom){
                    yC = i;xC = j;
                  }
                }
                System.err.println("");
              }
              
              System.err.println("yC= "+yC+" xC - " + xC+ " KY KX"+KY+" "+KX);
              
              aStar(yC, xC,KY,KX, sourceMaze, openList);            
            }
          }
          
          public static void aStar(int destY, int destX, int y, int x,  char[][] sourceMaze, Queue<Ceil> openList){
            int[][] dist = new int[R][C];
            for (int i = 0; i < R; i++) {
              for (int j = 0; j < C; j++) {
                dist[i][j] = -1;
              }
            }
            openList.add(new Ceil(0,destY,destX));
            visitedMaze[destY][destX]= true;
            Ceil tmp;
            
            boolean switcher = true;        
            
            int curY, curX;
            int f = 0;
            dist[destY][destX] = 0;
            while(!openList.isEmpty()){            
              tmp = openList.poll();            
              f=tmp.getF()+1; 
              curY = tmp.getY();
              curX = tmp.getX();
              if (sourceMaze[curY][curX] == 'T') {
                break;
              }
            //left
              if (sourceMaze[curY][curX-1] != wall && visitedMaze[curY][curX-1] == false){
                dist[curY][curX-1] = f;
                visitedMaze[curY][curX-1] = true;
                openList.add(new Ceil(f,curY,curX-1));
            }//right
            if (sourceMaze[curY][curX+1] != wall && visitedMaze[curY][curX+1] == false){
              dist[curY][curX+1] = f;
              visitedMaze[curY][curX+1] = true;
              openList.add(new Ceil(f,curY,curX+1));
            }//up
            if (sourceMaze[curY-1][curX] != wall && visitedMaze[curY-1][curX] == false){
              dist[curY-1][curX] = f;
              visitedMaze[curY-1][curX] = true;
              openList.add(new Ceil(f,curY-1,curX));
            }//down
            if (sourceMaze[curY+1][curX] != wall && visitedMaze[curY+1][curX] == false){
              dist[curY+1][curX] = f;
              visitedMaze[curY+1][curX] = true;
              openList.add(new Ceil(f,curY+1,curX));
            }           
            System.err.println("F="+f);
          }
          int min = dist[y][x];
          String move = "";
          Stack wayBack = new Stack();
          int ty = y; 
          int tx = x;
          System.err.print("T = "+dist[destY][destX]+ " ceil "+ sourceMaze[destY][destX]+"  ");
          for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
              if (dist[i][j]==-1){
                System.err.print("0  "); 
              }else if(dist[i][j]>=100){
               System.err.print(dist[i][j]);
             }else if(dist[i][j]<100 && dist[i][j]>=10){
               System.err.print(" " + dist[i][j]);
             }else{
              System.err.print("  " + dist[i][j]);
            }
          }
          System.err.println("");
        }
        int i =0;
        while(sourceMaze[y][x] != controlRoom){
          System.err.println("i = " + (++i));
          System.err.println("y = " +y + " x = "+x+ " dist[][] = "+ dist[y][x]);
            //LEFT
          if(dist[y][x - 1] < min && dist[y][x - 1] > -1){
            min = dist[y][x - 1];
            tx = x - 1;
            move = "LEFT";
            wayBack.push("RIGHT");
            } //RIGHT
            if(dist[y][x + 1] < min && dist[y][x + 1] > -1){
              min = dist[y][x + 1];
              tx = x + 1;
              move = "RIGHT";
              wayBack.push("LEFT");
            }//UP
            if(dist[y - 1][x] < min && dist[y - 1][x] > -1){
              min = dist[y - 1][x];
              ty = y - 1;
              move = "UP";
              wayBack.push("DOWN");
            }//DOWN
            if(dist[y + 1][x] < min && dist[y + 1][x] > -1){
              min = dist[y + 1][x];
              ty = y + 1;
              move = "DOWN";
              wayBack.push("UP");
            }
            x = tx;
            y = ty;
            System.err.println("move is  - "+move);
            System.out.println(move);
          }
          while (!wayBack.isEmpty()){
            System.err.println("IN STACK");
            System.out.println(wayBack.pop()); 
          }
          
        }
        
        public static void discoverWorld(char[][] sourceMaze,boolean[][] visitedMaze,int curY, int curX, Scanner in){
        ///UP
          if (sourceMaze[curY-1][curX] != wall && sourceMaze[curY-1][curX] != controlRoom) {
            if (visitedMaze[curY-1][curX] != true) {
              System.out.println("UP");
              visitedMaze[curY-1][curX] = true;
            KY = in.nextInt(); // row where Kirk is located.
            KX = in.nextInt();
            System.err.println("IN UP FUNCTION KY= "+KY+" KX - " + KX+" cury - " + curY + " cur X - "+curX);
            for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                 sourceMaze[i][j] = ROW.charAt(j);                    
               }
             }
             discoverWorld(sourceMaze,visitedMaze,KY,KX,in);
             System.err.print("COME BACK DOWN");
             System.out.println("DOWN");
             KY = in.nextInt(); // row where Kirk is located.
             KX = in.nextInt();
             for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                 sourceMaze[i][j] = ROW.charAt(j);                   
               }
             }
             curY = KY;
             curX = KX;
           }          
         }
        //RIGHT
         if (sourceMaze[curY][curX+1] != wall && sourceMaze[curY][curX+1] != controlRoom) {
          if (visitedMaze[curY][curX+1] != true) {
            System.out.println("RIGHT");
            visitedMaze[curY][curX+1] = true;
            KY = in.nextInt(); // row where Kirk is located.
            KX = in.nextInt();
            System.err.println("In RGHT FUNCTION KY= "+KY+" KX - " + KX+" cury - " + curY + " cur X - "+curX);
            for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                 sourceMaze[i][j] = ROW.charAt(j);                   
               }
             }
             discoverWorld(sourceMaze,visitedMaze,KY,KX,in);
             System.err.print("COME BACK LEFT");
             System.out.println("LEFT");
             KY = in.nextInt(); // row where Kirk is located.
             KX = in.nextInt();
             for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                 sourceMaze[i][j] = ROW.charAt(j);                   
               }
             }
             curY = KY;
             curX = KX;
           }          
         }
        ////LEFT
         if (sourceMaze[curY][curX-1] != wall && sourceMaze[curY][curX-1] != controlRoom) {
          if (visitedMaze[curY][curX-1] != true) {
            System.out.println("LEFT");
            visitedMaze[curY][curX-1] = true;
             KY = in.nextInt(); // row where Kirk is located.
             KX = in.nextInt();
             System.err.println("In LEFT FUNCTION KY= "+KY+" KX - " + KX+" cury - " + curY + " cur X - "+curX);
             for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                  sourceMaze[i][j] = ROW.charAt(j);
                    // if(i == KY && j == KX){
                    //     System.err.print(1);
                    // }else{
                    // System.err.print(sourceMaze[i][j]);  
                    // }
                }
                    // System.err.println("");
              }
              discoverWorld(sourceMaze,visitedMaze,KY,KX,in);
              System.err.print("COME BACK RIGHT"); 
              System.out.println("RIGHT");
             KY = in.nextInt(); // row where Kirk is located.
             KX = in.nextInt();
             for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                 sourceMaze[i][j] = ROW.charAt(j);                   
               }
             }
             curY = KY;
             curX = KX;
           }          
         }
        ///DOWN
         if (sourceMaze[curY+1][curX] != wall && sourceMaze[curY+1][curX] != controlRoom) {
          if (visitedMaze[curY+1][curX] != true) {
            System.out.println("DOWN");
            visitedMaze[curY+1][curX] = true;
            KY = in.nextInt(); // row where Kirk is located.
            KX = in.nextInt();
            System.err.println("IN DOWN FUNCTION KY= "+KY+" KX - " + KX+" cury - " + curY + " cur X - "+curX);
            for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                  sourceMaze[i][j] = ROW.charAt(j);                   
                }                   
              }
              discoverWorld(sourceMaze,visitedMaze,KY,KX,in);
              System.err.print("COME BACK UP");
              System.out.println("UP");
             KY = in.nextInt(); // row where Kirk is located.
             KX = in.nextInt();
             for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) { 
                 sourceMaze[i][j] = ROW.charAt(j);                   
               }
             }
             curY = KY;
             curX = KX;
           }          
         }        
       }
     }

     class CeilComparator implements Comparator<Ceil>{
      @Override
      public int compare(Ceil x, Ceil y){
        if (x.getF() < y.getF()){
          return -1;
        }else{
          return 1;
        }
      }
    }
    class Ceil{

      int F, G, H, Y, X;
      String parent, move;
      
      public int getF() {return F;}
      public int getG() {return G;}
      public int getH() {return H;}    
      public int getY() {return Y;}    
      public int getX() {return X;}    
      public String getParent() {return parent;}
      public String getMove() {return move;}

      public void setF(int f) {F = f;}
      public void setG(int g) {G = g;}
      public void setH(int h) {H = h;}
      public void setParent(String parent) {this.parent = parent;}
      public void setMove(String m) {move = m;}       

      Ceil (int f,int g, int h, String m, String p, int y, int x) {        
        parent = p;
        move = m;
        F = f;
        G = g;
        H = h;               
        Y = y;               
        X = x;               
      }
      Ceil(int f, int y, int x){
        F = f;              
        Y = y;               
        X = x; 
      }
      
    };

