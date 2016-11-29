package Game;

import Game.Enums.ColorMode;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by giogio on 9/17/16.
 */
public class NodeCell {

    private NodeCell upperL, upperR, lowerL, lowerR, left, right;

    private byte status;

    private boolean checked;

    private int x, y;
    public NodeCell(int coordX, int coordY) {
        status=0;
        checked=false;
        x = coordX;
        y = coordY;
    }

    public void uncheck(){
        checked = false;
    }

    public boolean isConnected(NodeCell second){
        if(this.getStatus()!=0 && this.getStatus()==second.getStatus())
            return isConnectedTo(second);
        else
           return false;

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

    public ArrayList<NodeCell> getListFreeNeighbours(){
        ArrayList<NodeCell> list = new ArrayList<NodeCell>();
        if(upperL!=null && (upperL.status==0 || upperL.status==status))
            list.add(upperL);
        if(upperR!=null && (upperR.status==0 || upperR.status==status))
            list.add(upperR);
        if(right!=null && (right.status==0 || right.status==status))
            list.add(right);
        if(lowerR!=null && (lowerR.status==0 || lowerR.status==status))
            list.add(lowerR);
        if(lowerL!=null && (lowerL.status==0 || lowerL.status==status))
            list.add(lowerL);
        if(left!=null && (left.status==0 || left.status==status))
            list.add(left);
        return list;
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
    public void setStatus(byte status) {
            this.status = status;
    }

    public ColorMode getColor(){
        if(status==1){
            return ColorMode.Blue;
        }else if(status==2){
            return ColorMode.Red;
        }
        return null;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
