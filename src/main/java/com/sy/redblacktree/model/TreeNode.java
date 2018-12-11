package com.sy.redblacktree.model;

import lombok.Data;

import java.awt.*;
import java.util.Comparator;

@Data
public class TreeNode<T> {

//    public TreeNode() {
//
//    }

    public TreeNode(T value) {
        this.value = value;
        this.color = color;
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

    /**
     * 比较规则
     */
    private Comparator<T> comparator;

    public Integer compareTo(T target) {
        return comparator.compare(this.value, target);
    }


    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value +
                '}';
    }
}
