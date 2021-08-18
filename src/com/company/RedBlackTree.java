package com.company;

class Node {

    public int data;
    public Node parent;
    public Node left;
    public Node right;
    public int color; // 1 -> Red, 0 -> Black

    public Node(int data) {
        this.data = data;
        color = 1;
        left = null;
        right = null;
        parent = null;
    }

    public Node() {
        this.data = -1;
        color = 0;
        left = null;
        right = null;
        parent = null;
    }

}

public class RedBlackTree {

    Node root;
    Node lNode;

    public RedBlackTree() {
        lNode = new Node();
        this.root = lNode;
    }

    public void insert(int value) {
        Node node = new Node(value);
        node.left = lNode;
        node.right = lNode;
        node.parent = lNode;
        if (root == lNode) {
            root = node;
            node.color = 0;
        } else {
            Node temp = root, p = null;
            while (temp != lNode) {
                p = temp;
                if (temp.data > value) {
                    temp = temp.left;
                } else {
                    temp = temp.right;
                }
            }
            node.parent = p;
            if (p.data > value) {
                p.left = node;
            } else {
                p.right = node;
            }
            while (node != root && p.color == 1) {
                if (p.parent.left == p) {
                    if (p.parent.right.color == 1) {
                        p.parent.right.color = 0;
                        p.parent.color = 1;
                        p.color = 0;
                        node = p.parent;
                        p = node.parent;
                        if(node == root)
                            node.color = 0;
                    } else {
                        if (node == p.right) {
                            leftRotation(p);
                            rightRotation(node.parent);
                            node.color = 0;
                            node.right.color = 1;
                        } else {
                            rightRotation(p.parent);
                            p.color = 0;
                            p.right.color = 1;
                        }
                    }
                } else {
                    if (p.parent.left.color == 1) {
                        p.parent.left.color = 0;
                        p.color = 0;
                        p.parent.color = 1;
                        node = p.parent;
                        p = node.parent;
                        if(node == root)
                            node.color = 0;
                    } else {
                        if (node == p.left) {
                            rightRotation(p);
                            leftRotation(node.parent);
                            node.color = 0;
                            node.left.color = 1;
                        } else {
                            leftRotation(p.parent);
                            p.color = 0;
                            p.left.color = 1;

                        }
                    }
                }
            }
        }
    }

    public void delete(int value) {
        Node temp = root, deleteNode = null;
        while (temp != lNode) {
            deleteNode = temp;
            if (value == temp.data) {
                break;
            } else if (value < temp.data) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        if (temp == lNode || temp.data != value) {
            System.out.println("Tree doe not have this value " + value);
        } else {
            while (true) {
                temp = replacementNode(deleteNode);
                deleteNode.data = temp.data;
                if (temp.left == lNode && temp.right == lNode) {
                    if (temp.color == 1) {
                        if (temp == root) {
                            root = lNode;
                        } else if (temp.parent.left == temp) {
                            temp.parent.left = lNode;
                        } else {
                            temp.parent.right = lNode;
                        }
                        break;
                    }
                    while (true) {
                        if (temp == root) { //case 1
                            root = lNode;
                            break;
                        } else if (temp.parent.right == temp) {
                            if (temp.parent.left.color == 1) { //case 2
                                if (temp.parent == root) {
                                    root = temp.parent.left;
                                }
                                temp.parent.color = 1;
                                temp.parent.left.color = 0;
                                rightRotation(temp.parent);
                            } else if (temp.parent.left.left.color == 1) { // case
                                if (temp.parent == root) {
                                    root = temp.parent.left;
                                }
                                temp.parent.left.color = temp.parent.color;
                                temp.parent.left.left.color = 0;
                                temp.parent.color = 0;
                                rightRotation(temp.parent);
                                temp.parent.right = lNode;
                                break;
                            } else if (temp.parent.left.right.color == 1) { //case 5
                                temp.parent.left.right.color = 0;
                                temp.parent.left.color = 1;
                                leftRotation(temp.parent.left);
                            } else if (temp.parent.color == 1) { // case 4
                                temp.parent.color = 0;
                                temp.parent.left.color = 1;
                                temp.parent.right = lNode;
                                break;
                            } else { //case 3
                                temp.parent.left.color = 1;
                                temp.parent.right = lNode;
                                break;
                            }
                        } else {
                            if (temp.parent.right.color == 1) { //case 2
                                if (temp.parent == root) {
                                    root = temp.parent.right;
                                }
                                temp.parent.color = 1;
                                temp.parent.right.color = 0;
                                leftRotation(temp.parent);
                            } else if (temp.parent.right.right.color == 1) { // case 6
                                if (temp.parent == root) {
                                    root = temp.parent.right;
                                }
                                temp.parent.right.color = temp.parent.color;
                                temp.parent.right.right.color = 0;
                                temp.parent.color = 0;
                                leftRotation(temp.parent);
                                temp.parent.left = lNode;
                                break;
                            } else if (temp.parent.right.left.color == 1) { //case 5
                                temp.parent.right.left.color = 0;
                                temp.parent.right.color = 1;
                                rightRotation(temp.parent.right);
                            } else if (temp.parent.color == 1) { // case 4
                                temp.parent.color = 0;
                                temp.parent.right.color = 1;
                                temp.parent.left = lNode;
                                break;
                            } else { //case 3
                                temp.parent.right.color = 1;
                                temp.parent.left = lNode;
                                break;
                            }
                        }
                    }
                    break;
                } else {
                    deleteNode = temp;
                }
            }
        }
    }

    public void clear() {
        root = lNode;
    }

    public void printData() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node != lNode) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    private Node replacementNode(Node node) {
        if (node.left == lNode && node.right == lNode) {
            return node;
        } else {
            Node temp = node.left;
            if (temp != lNode) {
                while (temp.right != lNode) {
                    temp = temp.right;
                }
            }
            if ((temp == lNode || temp.color == 0) && temp.right != lNode) {
                temp = node.right;
                while (temp.left != lNode) {
                    temp = temp.left;
                }
            }
            return temp;
        }
    }

    private void leftRotation(Node node) {
        Node leftChild = node.right;
        node.right = leftChild.left;
        if (leftChild.left != lNode) {
            leftChild.left.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == lNode) {
            this.root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }
        leftChild.left = node;
        node.parent = leftChild;
    }

    private void rightRotation(Node node) {
        Node rightChild = node.left;
        node.left = rightChild.right;
        if (rightChild.right != lNode) {
            rightChild.right.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == lNode) {
            this.root = rightChild;
        } else if (node == node.parent.right) {
            node.parent.right = rightChild;
        } else {
            node.parent.left = rightChild;
        }
        rightChild.right = node;
        node.parent = rightChild;
    }
}

