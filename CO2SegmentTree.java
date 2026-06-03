import java.util.*;

class Product {
    String category;
    int price;
    String name;

    Product(String category, int price, String name) {
        this.category = category;
        this.price = price;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + category + ", ₹" + price + ")";
    }
}

class BPlusTree {

    private static final int ORDER = 4;

    abstract class Node {
        List<Integer> keys = new ArrayList<>();
        InternalNode parent;
        abstract boolean isLeaf();
    }

    class InternalNode extends Node {
        List<Node> children = new ArrayList<>();

        boolean isLeaf() {
            return false;
        }
    }

    class LeafNode extends Node {
        List<Product> products = new ArrayList<>();
        LeafNode next;

        boolean isLeaf() {
            return true;
        }
    }

    private Node root = new LeafNode();

    public void insert(Product product) {

        int key = product.price;

        LeafNode leaf = findLeaf(key);

        int pos = 0;
        while (pos < leaf.keys.size() &&
               leaf.keys.get(pos) < key)
            pos++;

        leaf.keys.add(pos, key);
        leaf.products.add(pos, product);

        if (leaf.keys.size() >= ORDER)
            splitLeaf(leaf);
    }

    private LeafNode findLeaf(int key) {

        Node current = root;

        while (!current.isLeaf()) {

            InternalNode internal =
                (InternalNode) current;

            int i = 0;

            while (i < internal.keys.size() &&
                   key >= internal.keys.get(i))
                i++;

            current = internal.children.get(i);
        }

        return (LeafNode) current;
    }

    private void splitLeaf(LeafNode leaf) {

        int mid = leaf.keys.size() / 2;

        LeafNode newLeaf = new LeafNode();

        newLeaf.keys.addAll(
            leaf.keys.subList(mid, leaf.keys.size()));

        newLeaf.products.addAll(
            leaf.products.subList(mid,
                                  leaf.products.size()));

        leaf.keys.subList(mid, leaf.keys.size()).clear();
        leaf.products.subList(mid,
                              leaf.products.size()).clear();

        newLeaf.next = leaf.next;
        leaf.next = newLeaf;

        insertIntoParent(
            leaf,
            newLeaf.keys.get(0),
            newLeaf
        );
    }

    private void insertIntoParent(
        Node left,
        int key,
        Node right) {

        if (left == root) {

            InternalNode newRoot =
                new InternalNode();

            newRoot.keys.add(key);
            newRoot.children.add(left);
            newRoot.children.add(right);

            root = newRoot;

            left.parent = newRoot;
            right.parent = newRoot;

            return;
        }

        InternalNode parent = left.parent;

        int index =
            parent.children.indexOf(left) + 1;

        parent.keys.add(index - 1, key);
        parent.children.add(index, right);

        right.parent = parent;

        if (parent.keys.size() >= ORDER)
            splitInternal(parent);
    }

    private void splitInternal(
        InternalNode node) {

        int mid = node.keys.size() / 2;

        int promoteKey = node.keys.get(mid);

        InternalNode newNode =
            new InternalNode();

        newNode.keys.addAll(
            node.keys.subList(mid + 1,
                              node.keys.size()));

        newNode.children.addAll(
            node.children.subList(mid + 1,
                                  node.children.size()));

        for (Node child : newNode.children)
            child.parent = newNode;

        node.keys.subList(mid,
                          node.keys.size()).clear();

        node.children.subList(mid + 1,
                              node.children.size()).clear();

        insertIntoParent(
            node,
            promoteKey,
            newNode
        );
    }

    public void rangeQuery(
        int minPrice,
        int maxPrice) {

        LeafNode leaf = findLeaf(minPrice);

        System.out.println(
            "\nProducts between ₹" +
            minPrice +
            " and ₹" +
            maxPrice +
            ":");

        while (leaf != null) {

            for (int i = 0;
                 i < leaf.keys.size();
                 i++) {

                int price = leaf.keys.get(i);

                if (price >= minPrice &&
                    price <= maxPrice) {

                    System.out.println(
                        leaf.products.get(i));
                }

                if (price > maxPrice)
                    return;
            }

            leaf = leaf.next;
        }
    }
}

public class FlipkartCatalogBPlusTree {

    public static void main(String[] args) {

        BPlusTree tree = new BPlusTree();

        tree.insert(new Product(
            "Smartphone",
            12000,
            "Redmi Note"));

        tree.insert(new Product(
            "Smartphone",
            13500,
            "Realme Narzo"));

        tree.insert(new Product(
            "Smartphone",
            14800,
            "Samsung M14"));

        tree.insert(new Product(
            "Smartphone",
            16000,
            "Moto G"));

        tree.insert(new Product(
            "Smartphone",
            12500,
            "POCO X"));

        tree.insert(new Product(
            "Smartphone",
            14000,
            "iQOO Z"));

        tree.insert(new Product(
            "Smartphone",
            18000,
            "Nothing Phone"));

        // Diwali price-range scan
        tree.rangeQuery(12000, 14800);
    }
}
