import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

import java.awt.*;

/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 *
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo{
    private int _count;
    public Ex3Algo() {_count=0;}
    @Override
    /**
     *  Add a short description for the algorithm as a String.
     */
    //TODO
    public String getInfo() {
        return "this algorithem uses the function of shortespath and alldistance with the BFS " +
                " algo to eat all of the pink-dots";
    }
    @Override
    /**
     * This ia the main method - that you should design, implement and test.
     */
    public int move(PacmanGame game) {
        int code = 0;
        int[][] board = game.getGame(code); //get the board from the game
        String posString = game.getPos(code); //pacman pos
        Pixel2D pacmanPos = stringToPixel(posString); //convert string to pixel position
        Map map = new Map(board); //make map from the board to use map functions
        map.setCyclic(true); //set to cyclic mode true if accidentally turned off

        Map2D distanceMap = map.allDistance(pacmanPos, 1); //call alldistance(BFS) to calc dis 1= wall
        Pixel2D nearest = null; //nearest food start null
        int minDistance = Integer.MAX_VALUE; //start with very large distance, Uri uncle suggestion
        for (int x = 0; x < map.getWidth(); x++) { //search map for food
            for (int y = 0; y < map.getHeight(); y++) {
                int pixelValue = map.getPixel(x, y); //get the value at this position
                if (pixelValue == 3 || pixelValue == 5) {  //check if the pixel is food (3 or 5)
                    Pixel2D foodPos = new Index2D(x, y); //define this as a pixel for food position
                    int distance = distanceMap.getPixel(foodPos); //get distance to this food from distance map
                    if (distance > 0 && distance < minDistance) { //check if reachable and closest
                        minDistance = distance; //update the minimum distance
                        nearest = foodPos; //update the nearest food position
                    }
                }
            }
        }
//        if(_count==0 || _count==300) {
//
//
//            printBoard(board);
//            int blue = Game.getIntColor(Color.BLUE, code);
//            int pink = Game.getIntColor(Color.PINK, code);
//            int black = Game.getIntColor(Color.BLACK, code);
//            int green = Game.getIntColor(Color.GREEN, code);
//            System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
//
//            String[] parts = pos.split(","); //split the string to int
//            int x = Integer.parseInt(parts[0].trim());
//            int y = Integer.parseInt(parts[1].trim());
//            System.out.println("Pacman coordinate: "+pos);
//            GhostCL[] ghosts = game.getGhosts(code);
//            printGhosts(ghosts);
//            int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
//        }
//        _count++;
//        int dir = randomDir();
//        return dir;
    }
    private static void printBoard(int[][] b) {
        for(int y =0;y<b[0].length;y++){
            for(int x =0;x<b.length;x++){
                int v = b[x][y];
                System.out.print(v+"\t");
            }
            System.out.println();
        }
    }
    private static void printGhosts(GhostCL[] gs) {
        for(int i=0;i<gs.length;i++){
            GhostCL g = gs[i];
            System.out.println(i+") status: "+g.getStatus()+",  type: "+g.getType()+",  pos: "+g.getPos(0)+",  time: "+g.remainTimeAsEatable(0));
        }
    }
    private static int randomDir() {
        int[] dirs = {Game.UP, Game.LEFT, Game.DOWN, Game.RIGHT};
        int ind = (int)(Math.random()*dirs.length);
        return dirs[ind];
    }
    /**
     * helper function to convert position of Pack-Man(pos=string) string "x,y" to Pixel2D
     * @param str the string with x,y coordinates
     * @return Pixel2D with those coordinates
     */
    private static Pixel2D stringToPixel(String str) {
        /////// add your code below ///////
        String[] arr = str.split(","); //split the string by comma to get x and y separately
        int x = Integer.parseInt(arr[0]); //get x coordinate from first part
        int y = Integer.parseInt(arr[1]); //get y coordinate from second part
        return new Index2D(x, y); //return new pixel with those coordinates
        ///////////////////////////////////
    }
}