package com.sy.redblacktree.util;

import com.sun.javafx.css.Rule;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sy.redblacktree.constant.RedBlackConst;
import com.sy.redblacktree.exception.UnSupportException;
import com.sy.redblacktree.model.TreeNode;
import sun.plugin.com.event.COMEventHandler;
import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import java.util.*;

public class RedBlackTree<T> {

    // 或许这并不是一颗红黑树...
    private static final int UNMATCH = -1;

    // 如果是根节点, 直接涂黑
    private static final int RULE0 = 0;

    // 如果父节点为黑色, 无论在那一支添加节点都不会违反红黑树的五个性质
    private static final int RULE1 = 1;

    // 如果父亲节点为红色, 叔叔节点为红色, 且插入点为右支是, 先对父节点进行左旋
    private static final int RULE2 = 2;

    // 如果父亲节点为红色, 叔叔节点为红色, 且插入点为左支是, 应该交换祖父和父节点的颜色然后以父节点右旋
    // 如果我们把原先的父节点看做是新的要插入的节点，把原先要插入的节点看做是新的父节点，那就变成了RULE2的情况
    private static final int RULE3 = 3;

    // 如果父亲节点为红色, 叔叔节点为红色, 吧祖父节点和父. 叔叔节点交换颜色,
    // 再把祖父节点看作插入的点, 调整树重新符合红黑树性质
    private static final int RULE4 = 4;

    private TreeNode<T> root;

    /** 比较规则 **/
    private Comparator<T> comparator;

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }
//    RedBlackTree(T value) {
//        root = new TreeNode<>(value);
//    }

    public Boolean add(TreeNode children) {

        if (root == null) {
            root = children;
            children.setColor(RedBlackConst.Color.BLACK);
            return true;
        } else {
            try {
                dfsTreeAndGetFartherNode(root, children);
            } catch (UnSupportException e) {
                e.printStackTrace();
            }
            Integer rule = matchRule(children.getFartherNode(), children);

            if (Objects.equals(rule, RULE0)) {
                System.out.println(rule);
            } else if (Objects.equals(rule, RULE1)) {
                System.out.println(rule);
            } else if (Objects.equals(rule, RULE2)) {
                TreeNode fartherNode = children.getFartherNode();
                changeColor(fartherNode, children);
                Integer leftOrRight = Objects.equals(fartherNode.getLeftChildren(), children) ? 0 : 1;

                if (leftOrRight == 0) {
                    rightRotate(fartherNode, children);
                } else {
                    leftRotate(fartherNode, children);
                }

            }
            return true;
        }



    }

    private int matchRule(TreeNode fartherNode, TreeNode children) {

        if (fartherNode == null) {
            return RULE0;
        }

        TreeNode grandFartherNode = fartherNode.getFartherNode();

        TreeNode uncleNode = null;

        if (grandFartherNode != null) {
            uncleNode =  Objects.equals(fartherNode, grandFartherNode.getLeftChildren())
                    ? grandFartherNode.getRightChildren() : grandFartherNode.getLeftChildren();
        }



        if (Objects.equals(fartherNode.getColor(), RedBlackConst.Color.BLACK)) {
            return RULE1;
        }else if (Objects.equals(fartherNode.getColor(), RedBlackConst.Color.RED)
                && (uncleNode != null || Objects.equals(uncleNode.getColor(), RedBlackConst.Color.BLACK))) {
            return RULE2;
        }else if (Objects.equals(fartherNode.getColor(), RedBlackConst.Color.RED)
                && (uncleNode != null || Objects.equals(uncleNode.getColor(), RedBlackConst.Color.RED))) {
            return RULE3;
        }
        return UNMATCH;
    }

    // 换色
    private void changeColor(TreeNode n1, TreeNode n2) {
        Integer n1Color = n1.getColor();
        Integer n2Color = n2.getColor();

        n1.setColor(n2Color);
        n2.setColor(n1Color);
    }

    // 左旋
    private static void leftRotate(TreeNode fartherNode, TreeNode rightChildrenNode) {

        // 祖父节点
        TreeNode grandFartherNode = fartherNode.getFartherNode();

        // 右子树的左节点
        TreeNode rightChilLeftOff = rightChildrenNode.getLeftChildren();

        if (grandFartherNode != null) {

            rightChildrenNode.setFartherNode(grandFartherNode);

            if (Objects.equals(grandFartherNode.getLeftChildren(), fartherNode)) {
                grandFartherNode.setLeftChildren(rightChildrenNode);
            } else {
                grandFartherNode.setRightChildren(rightChildrenNode);
            }

        } else {
            rightChildrenNode.setFartherNode(null);
        }

        fartherNode.setRightChildren(rightChilLeftOff);
        fartherNode.setFartherNode(rightChildrenNode);
        rightChildrenNode.setLeftChildren(fartherNode);

    }

    // 右旋
    private static void rightRotate(TreeNode fartherNode, TreeNode leftChildrenNode) {

        // 祖父节点
        TreeNode grandFartherNode = fartherNode.getFartherNode();

        // 左子树的右节点
        TreeNode leftChilRightOff = leftChildrenNode.getRightChildren();

        if (grandFartherNode != null) {

            leftChildrenNode.setFartherNode(grandFartherNode);

            if (Objects.equals(grandFartherNode.getLeftChildren(), fartherNode)) {
                grandFartherNode.setLeftChildren(leftChildrenNode);
            } else {
                grandFartherNode.setRightChildren(leftChildrenNode);
            }

        } else {
            leftChildrenNode.setFartherNode(null);
        }

        fartherNode.setLeftChildren(leftChilRightOff);
        fartherNode.setFartherNode(leftChildrenNode);
        leftChildrenNode.setLeftChildren(fartherNode);
    }


    private void dfsTreeAndGetFartherNode(TreeNode<T> checkNode, TreeNode<T> children) throws UnSupportException {

        if (comparator.compare(checkNode.getValue(), children.getValue()) == 1) {
            // 找到合适点, 插入
            if (checkNode.getLeftChildren() == null) {
                checkNode.setLeftChildren(children);
                children.setFartherNode(checkNode);
            }
            dfsTreeAndGetFartherNode(checkNode.getLeftChildren(), children);
        } else if (comparator.compare(checkNode.getValue(), children.getValue()) == -1) {
            if (checkNode.getRightChildren() == null) {
                checkNode.setRightChildren(children);
                children.setFartherNode(checkNode);
            }
            dfsTreeAndGetFartherNode(checkNode.getRightChildren(), children);
        }

    }


    public static void main(String[] args) throws UnSupportException {

        RedBlackTree<Integer> root = new RedBlackTree<>();

        TreeNode<Integer> node1 = new TreeNode<>(5);
        TreeNode<Integer> node2 = new TreeNode<>(3);
        TreeNode<Integer> node3 = new TreeNode<>(2);
        TreeNode<Integer> node4 = new TreeNode<>(4);
        TreeNode<Integer> node5 = new TreeNode<>(1);
        TreeNode<Integer> node6 = new TreeNode<>(7);
        TreeNode<Integer> node7 = new TreeNode<>(8);

        node1.setLeftChildren(node2);
        node2.setFartherNode(node1);

        node2.setLeftChildren(node3);
        node3.setFartherNode(node2);

        node2.setRightChildren(node4);
        node4.setFartherNode(node2);

        int j = 2;

//        lef
// tRotate(node2, node4);

        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();

        redBlackTree.setComparator((Integer t1, Integer t2) -> {
            if (t1 > t2) {
                return 1;
            }else if (t1 < t2) {
                return -1;
            }else {
                return 0;
            }
        });

        redBlackTree.add(node1);
        redBlackTree.add(node2);
        redBlackTree.add(node3);
        redBlackTree.add(node4);
        redBlackTree.add(node5);
        redBlackTree.add(node6);
        redBlackTree.add(node7);

        int i = 1;
    }


}
