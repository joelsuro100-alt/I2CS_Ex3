import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;
import java.awt.event.KeyEvent;

public class ManualAlgo implements PacManAlgo{
    public ManualAlgo() {;}
    @Override
    public String getInfo() {
        return "This is a manual algorithm for manual controlling the PacMan using w,a,x,d (up,left,down,right).";
    }

    @Override
    public int move(PacmanGame game) {
        int ans = PacmanGame.ERR;
        Character cmd = Ex3Main.getCMD();
            if (cmd != null) {
                if (cmd == 'w' || cmd == 'W' || cmd == KeyEvent.VK_UP || cmd == '8') {ans = PacmanGame.UP;}
                if (cmd == 's' || cmd == 'S' || cmd == KeyEvent.VK_DOWN || cmd == '5') {ans = PacmanGame.DOWN;}
                if (cmd == 'a' || cmd == 'A' || cmd == KeyEvent.VK_LEFT || cmd == '4') {ans = PacmanGame.LEFT;}
                if (cmd == 'd' || cmd == 'D' || cmd == KeyEvent.VK_RIGHT || cmd == '6') {ans = PacmanGame.RIGHT;}
            }
            return  ans;
    }
}
