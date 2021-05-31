class btreeTester:
    if name == __main__:
        t1 = BinaryTree(0)
        t1.add_leftchild(BinaryTree(5))
        t2 = BinaryTree(10)
        t2.add_rightchild(BinaryTree(22))
        t1.add_rightchild(t2)
        print(list(iter(t1)))