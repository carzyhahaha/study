package com.sy.redblacktree.model;

import com.sy.redblacktree.constant.RedBlackTreeConst;
import lombok.Data;

import java.awt.*;
import java.util.Comparator;

@Data
public class TreeNode<T> {

    public TreeNode(T value) {
        this.value = value;
        this.color = RedBlackTreeConst.Color.RED;
    }

    /**
     * 权值
     */
    private T value;

    /**
     * 颜色, 0.红色 1.黑色
     */
    private Integer color;

    /**
     * 左子节点
     */
    private TreeNode leftChildren;

    /**
     * 右子节点
     */
    private TreeNode rightChildren;

    /**
     * 父亲节点
     */
    private TreeNode fartherNode;


    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value +
                ", color=" + color +
                ", leftChildren=" + (leftChildren == null ? "null" : leftChildren.getValue()) +
                ", rightChildren=" + (rightChildren == null ? "null" : rightChildren.getValue()) +
                ", fartherNode=" + (fartherNode == null ? "null" : fartherNode.getValue()) +
                '}';
    }
}
