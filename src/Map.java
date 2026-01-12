import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
	private int[][] _map;
	private boolean _cyclicFlag = true;

	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w,h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}

	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

	@Override
	public void init(int w, int h, int v) {
		/////// add your code below ///////
        this._map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                _map[i][j] = v;
            }
        }
		///////////////////////////////////
	}
	@Override
	public void init(int[][] arr) {
		/////// add your code below ///////
        if (arr == null || arr.length == 0 || arr[0] == null) {
            throw new RuntimeException("Invalid array initialization");
        }
		///////////////////////////////////
	}
	@Override
	public int[][] getMap() {
		/////// add your code below ///////
        int w = this._map.length;
        int h = this._map[0].length;
        int[][] ans = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ans[i][j] = this._map[i][j];
            }
        }
		///////////////////////////////////
		return ans;
	}

	@Override
	/////// add your code below ///////
	public int getWidth() {
        return this._map.length;
    }

	@Override
	/////// add your code below ///////
	public int getHeight() {
        return this._map[0].length;
    }

	@Override
	/////// add your code below ///////
	public int getPixel(int x, int y) {
        if (_cyclicFlag) {
            x = (x % getWidth() + getWidth()) % getWidth();
            y = (y % getHeight() + getHeight()) % getHeight();
        }
        if (isInside(x, y)) {
            return this._map[x][y];
        }
        return 0; //if not cycle
    }

	@Override
	/////// add your code below ///////
	public int getPixel(Pixel2D p) {
        return this.getPixel(p.getX(), p.getY());
	}

	@Override
	/////// add your code below ///////
	public void setPixel(int x, int y, int v) {
        if (_cyclicFlag) {
            x = (x % getWidth() + getWidth()) % getWidth();
            y = (y % getHeight() + getHeight()) % getHeight();
        }
        if (isInside(x, y)) {
            this._map[x][y] = v;
        }
    }

	@Override
	/////// add your code below ///////
	public void setPixel(Pixel2D p, int v) {
        this.setPixel(p.getX(), p.getY(), v);
	}

	@Override
	/**
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
     * Uses BFS Flood Fill logic same as in EX2.
	 */
	public int fill(Pixel2D xy, int new_v) {
		int ans=0;
		/////// add your code below ///////
        int old_v = getPixel(xy); //find out what color is there now
        if (old_v == new_v) return 0; //no need to color if it's already the new color

        Queue<Pixel2D> q = new LinkedList<>(); //make a queue to hold pixels we need to check for BFS method from EX2
        Set<String> visited = new HashSet<>(); //use a set to remember which pixels we already saw
        Pixel2D start = normalize(xy); //calls privet function that helps cycle world
        q.add(start);
        visited.add(start.toString());
        setPixel(start, new_v); //change the start to new color
        ans++;

        while(!q.isEmpty()) { //keep going while queue has pixels
            Pixel2D curr = q.poll();
            Pixel2D[] neighbors = getNeighbors(curr);
            for(Pixel2D n : neighbors) {
                Pixel2D normN = normalize(n); //handles if neighbor is outside map in cycle mode
                if (isInside(normN) && !visited.contains(normN.toString()) && getPixel(normN) == old_v) { //check if valid not visited and old color
                    setPixel(normN, new_v); //change to new color
                    visited.add(normN.toString()); //add to visited set
                    q.add(normN); //put in queue to check its neighbors later
                    ans++; //add 1 to count
                }
            }
        }
		///////////////////////////////////
		return ans; //give back how many we colored
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
        /////// add your code below ///////
        Map2D mapDistances = this.allDistance(p1, obsColor); //get all distances from p1 using BFS, walls are -1, unreachable -2
        int targetDist = mapDistances.getPixel(p2); //get the distance at the end point p2 from the distance map
        if (targetDist == -1 || targetDist == -2) { //check if p2 is a wall or no way to reach it
            return null; //no path, return nothing
        }
        if (targetDist == 0) { //if p1 == p2
            return new Pixel2D[]{p1}; //just return the start as the whole path
        }

        Pixel2D[] ans = new Pixel2D[targetDist + 1]; //make an array for the path, length is distance + 1 (includes start and end)
        ans[targetDist] = p2; //put the end point at the last spot in the array
        Pixel2D current = p2; //start from the end point
        int currentDist = targetDist; //start with the distance at the end

        while (currentDist > 0) { //keep going back until we hit the start (dist 0)
            Pixel2D[] neighbors = getNeighbors(current); //get the 4 possible neighbors around current
            boolean foundPrev = false; //flag to check if we found the previous step
            for (Pixel2D n : neighbors) { //go over each neighbor
                Pixel2D normN = normalize(n); //fix the neighbor if cyclic or edges

                if (isInside(normN) && mapDistances.getPixel(normN) == currentDist - 1) { //if neighbor is inside and has dist -1
                    current = normN; //move to this neighbor as the new current
                    currentDist--; //decrease the distance by 1
                    ans[currentDist] = current; //put it in the array at the right spot
                    foundPrev = true; //we found it, good
                    break; //stop looking, we only need one (any shortest path)
                }
            }
            if (!foundPrev) return null; //if no previous found, something wrong, return null
        }
		///////////////////////////////////
		return ans;
	}
	@Override
	/////// add your code below ///////
	public boolean isInside(Pixel2D p) {
        return isInside(p.getX(), p.getY());
	}

	@Override
	/////// add your code below ///////
	public boolean isCyclic() {
        return _cyclicFlag;
	}

	@Override
	/////// add your code below ///////
	public void setCyclic(boolean cy) {
        this._cyclicFlag = cy;
    }

	@Override
	public Map2D allDistance(Pixel2D start, int obsColor) {
		/////// add your code below ///////
        Map mapCopy = new Map(this._map); //make a new map with the same data as _map

        for (int i = 0; i < mapCopy.getWidth(); i++) { //go over every column in the copy
            for (int j = 0; j < mapCopy.getHeight(); j++) { //go over every row in the copy
                if (this._map[i][j] == obsColor) { //if this pixel is a wall in the original
                    mapCopy.setPixel(i, j, -1); //set it to -1 in the copy (wall)
                } else {
                    mapCopy.setPixel(i, j, -2); //set it to -2 (not visited yet)
                }
            }
        }
        Queue<Pixel2D> q = new LinkedList<>(); //create a queue for BFS to hold pixels to check
        Pixel2D normStart = normalize(start); //fix the starting point if needed (for edges or cyclic)

        if (mapCopy.getPixel(normStart) == -1) return mapCopy; //if start is a wall, just return the copy with -1 and -2
        q.add(normStart); //put the starting point into the queue
        mapCopy.setPixel(normStart, 0); //set distance 0 at the start in the copy

        while (!q.isEmpty()) { //keep going while there are pixels in the queue
            Pixel2D curr = q.remove(); //take the next pixel from the queue (FIFO)
            int currentDist = mapCopy.getPixel(curr); //get the distance of this current pixel
            Pixel2D[] neighbors = getNeighbors(curr); //get the 4 neighbors around it
            for (Pixel2D n : neighbors) { //go over each neighbor
                Pixel2D normN = normalize(n); //fix neighbor position if needed

                if (isInside(normN) && mapCopy.getPixel(normN) == -2) { //check if inside and still -2 (not visited)
                    mapCopy.setPixel(normN, currentDist + 1); //set its distance to current + 1
                    q.add(normN); //add it to the queue to check its neighbors later
                }
            }
        }
        return mapCopy; //return the copy with all distances filled in
    }

/////// additional privet functions ///////
private boolean isInside(int x, int y) { //privet function to help check if inside map
    return _cyclicFlag || (x >= 0 && x < getWidth() && y >= 0 && y < getHeight());
}

    /**
     * privet function to fix the position of a point Returns a normalized point based on if the map is cyclic or not
     * @param p
     * @return
     */
    private Pixel2D normalize(Pixel2D p) { //helper function to fix the position of a point
    int x = p.getX(); //get the x coordinate from the point
    int y = p.getY(); //get the y coordinate from the point
    if (_cyclicFlag) { //if the map is cyclic (like a round world)
        x = (x % getWidth() + getWidth()) % getWidth(); //wrap x around the width, handle negative too
        y = (y % getHeight() + getHeight()) % getHeight(); //wrap y around the height, handle negative too
    }
    return new Index2D(x, y); //return the fixed point as new Index2D
}

    /**
     * //helper to get the up, down, left, right points
     * @param p
     * @return
     */
    private Pixel2D[] getNeighbors(Pixel2D p) {
        int x = p.getX(); //get current x
        int y = p.getY(); //get current y
        return new Pixel2D[]{ //make an array of 4 new points
                new Index2D(x + 1, y), // Right: add 1 to x
                new Index2D(x - 1, y), // Left: subtract 1 from x
                new Index2D(x, y + 1), // Down: add 1 to y (or up, depending on how the map is set up, in matrix down is next row)
                new Index2D(x, y - 1) // Up: subtract 1 from y
        }; //end of the array
    }


}
