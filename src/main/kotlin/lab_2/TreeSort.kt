package lab_2

class Tree
{
    class Node(var value: Int) {
        var left: Node?
        var right: Node? = null

        init {
            left = right
        }
    }

    var root: Node? = null

    // This method mainly
    // calls insertRec()
    private fun insert(value: Int) {
        root = insertRec(root, value)
    }

    /* A recursive function to
    insert a new key in BST */
    private fun insertRec(root: Node?, value: Int): Node {

        /* If the tree is empty,
        return a new node */
        if (root == null) {
            return Node(value)
        }

        /* Otherwise, recur down the tree */
        if (value < root.value) {
            root.left = insertRec(root.left, value)
        } else if (value > root.value) {
            root.right = insertRec(root.right, value)
        }

        return root
    }

    // A function to do
    // inorder traversal of BST
    fun inorderRec(root: Node?) {
        if (root != null) {
            inorderRec(root.left)
            print(root.value.toString() + " ")
            inorderRec(root.right)
        }
    }

    fun treeIns(arr: IntArray) {
        for (i in arr.indices) {
            insert(arr[i])
        }
    }
}

fun main() {
    val tree = Tree()
    val arr = intArrayOf(5, 4, 7, 2, 11)

    println("Given arr")
    printArr(arr)

    tree.treeIns(arr)

    println("Sorted arr")
    tree.inorderRec(tree.root)
}