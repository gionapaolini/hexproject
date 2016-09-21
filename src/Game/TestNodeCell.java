package Game;

/**
 * Created by giogio on 9/17/16.
 */
public class TestNodeCell {
    public static void main(String[] args){
        int n=11;
        Board board = new Board(n);

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                board.getGrid()[i][j].setStatus((byte)1);
            }
        }

        board.getGrid()[n-2][n-1].setStatus((byte)0);
        board.getGrid()[n-1][n-2].setStatus((byte)0);
        System.out.println(board.getGrid()[0][0].isConnectedTo(board.getGrid()[n-1][n-1]));
        board.getGrid()[n-1][n-2].setStatus((byte)1);
        System.out.println(board.isConnected(true));


    }
}
