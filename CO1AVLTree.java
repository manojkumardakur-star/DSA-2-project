class AVLNode {
    int patientID;
    int height;
    AVLNode left, right;

    AVLNode(int patientID) {
        this.patientID = patientID;
        this.height = 1;
    }
}

class AVLTree {

    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int patientID) {

        if (node == null)
            return new AVLNode(patientID);

        if (patientID < node.patientID)
            node.left = insert(node.left, patientID);
        else if (patientID > node.patientID)
            node.right = insert(node.right, patientID);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Rotation
        if (balance > 1 && patientID < node.left.patientID)
            return rightRotate(node);

        // RR Rotation
        if (balance < -1 && patientID > node.right.patientID)
            return leftRotate(node);

        // LR Rotation
        if (balance > 1 && patientID > node.left.patientID) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Rotation
        if (balance < -1 && patientID < node.right.patientID) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    AVLNode delete(AVLNode root, int patientID) {

        if (root == null)
            return root;

        if (patientID < root.patientID)
            root.left = delete(root.left, patientID);
        else if (patientID > root.patientID)
            root.right = delete(root.right, patientID);
        else {

            if ((root.left == null) || (root.right == null)) {
                AVLNode temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                AVLNode temp = minValueNode(root.right);

                root.patientID = temp.patientID;

                root.right = delete(root.right, temp.patientID);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    boolean search(AVLNode root, int patientID) {

        if (root == null)
            return false;

        if (root.patientID == patientID)
            return true;

        if (patientID < root.patientID)
            return search(root.left, patientID);

        return search(root.right, patientID);
    }

    void inorder(AVLNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.patientID + " ");
            inorder(root.right);
        }
    }
}

public class MediFlowHospitalAVL {

    public static void main(String[] args) {

        AVLTree tree = new AVLTree();
        AVLNode root = null;

        int patientIDs[] = {
            50, 30, 70, 20, 40, 60, 80,
            10, 25, 35, 45, 55, 65
        };

        System.out.println("Inserting Patient IDs:");

        for (int id : patientIDs) {
            root = tree.insert(root, id);
            System.out.print(id + " ");
        }

        System.out.println("\n\nAVL Tree (Inorder Traversal):");
        tree.inorder(root);

        System.out.println("\n\nSearching Patient ID 60:");
        if (tree.search(root, 60))
            System.out.println("Patient Found");
        else
            System.out.println("Patient Not Found");

        System.out.println("\nDeleting Patient IDs 30, 70, and 50...");

        root = tree.delete(root, 30);
        root = tree.delete(root, 70);
        root = tree.delete(root, 50);

        System.out.println("\nAVL Tree After Deletion:");
        tree.inorder(root);
    }
}
