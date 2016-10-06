package Game;

import Game.Enums.ColorMode;

/**
 * Created by giogio on 9/17/16.
 */
public class NodeCell {

    private NodeCell upperL, upperR, lowerL, lowerR, left, right;

    private byte status;

    private boolean checked;

    public NodeCell() {
        status=0;
        checked=false;
    }

    public void uncheck(){
        checked = false;
    }

    public boolean isConnected(NodeCell second){
        if(this.getStatus()==0)
            return false;
        else
            return isConnectedTo(second);

    }

    public boolean isConnectedTo(NodeCell second){
        this.setChecked(true);
        if(checkNode(upperL, second) || checkNode(upperR, second) || checkNode(right, second)
                || checkNode(lowerR, second) || checkNode(lowerL, second) || checkNode(left, second))
            return true;
        else
            return false;


    }

    private boolean checkNode(NodeCell first, NodeCell second){
        if(first!=null && first.getStatus()==this.getStatus() && !first.isChecked()) {
            if (first == second) {
                return true;
            } else if (first.isConnectedTo(second)) {
                return true;
            }
        }
        return false;
    }

    public NodeCell getUpperL() {
        return upperL;
    }

    public void setUpperL(NodeCell upperL) {
        this.upperL = upperL;
    }

    public NodeCell getUpperR() {
        return upperR;
    }

    public void setUpperR(NodeCell upperR) {
        this.upperR = upperR;
    }

    public NodeCell getLowerL() {
        return lowerL;
    }

    public void setLowerL(NodeCell lowerL) {
        this.lowerL = lowerL;
    }

    public NodeCell getLowerR() {
        return lowerR;
    }

    public void setLowerR(NodeCell lowerR) {
        this.lowerR = lowerR;
    }

    public NodeCell getLeft() {
        return left;
    }

    public void setLeft(NodeCell left) {
        this.left = left;
    }

    public NodeCell getRight() {
        return right;
    }

    public void setRight(NodeCell right) {
        this.right = right;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(ColorMode colorMode) {
        if(colorMode==ColorMode.Blue){
            this.status = (byte)1;
        }else{
            this.status = (byte)2;
        }

    }

    public boolean isChecked() {
        return checked;
    }

    private void setChecked(boolean checked) {
        this.checked = checked;
    }
}
