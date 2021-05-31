class BinaryTree:

    @property
    def leftChild(self):
        return self._leftChild

    @property
    def rightChild(self):
        return self._rightChild

    def __init__(self, *data):
        if len(data) == 1:
            self._data = data[0]
        else:
            self._data = None
        self._leftChild = None
        self._rightChild = None

    def add_leftchild(self, p):
        if type(p) != type(self) and type(p) is not None:
            raise TypeError
        elif not isinstance(p.data, type(self.data)) and (type(p) is not None and p.data is not None):
            raise TypeError("Type mismatch between " + type(self.data).__name__ + " and " + type(p.data).__name__)
        else:
            self._leftChild = p

    def add_rightchild(self, p):
        if type(p) != type(self) and type(p) is not None:
            raise TypeError
        elif not isinstance(p.data, type(self.data)) and (type(p) is not None and p.data is not None):
            raise TypeError("Type mismatch between " + type(self.data).__name__ + " and " + type(p.data).__name__)
        else:
            self._rightChild = p

    @property
    def data(self):
        return self._data

    @data.setter
    def data(self, d):
        if not isinstance(self._data, type(d)):
            if (type(d) is not None) and (self._data is not None):
                raise TypeError("Type mismatch between " + type(self._data).__name__ + " and " + type(d).__name__)
            else:
                self._data = d
        else:
            self._data = d

    @leftChild.setter
    def leftChild(self, l):
        if not isinstance(type(self._leftChild), type(l)):
            raise TypeError("Type mismatch between " + type(self._leftChild).__name__ + " and " + type(l).__name__)
        elif not isinstance(l.data, type(self._leftChild.data)) and (type(l) is not None and l.data is not None):
            raise TypeError("Type mismatch between " + type(self._leftChild.data).__name__ + " and " + type(l.data).__name__)
        else:
            self._leftChild = l

    @rightChild.setter
    def rightChild(self, l):
        if not isinstance(type(self._rightChild), type(l)):
            raise TypeError("Type mismatch between " + type(self._rightChild).__name__ + " and " + type(l).__name__)
        elif not isinstance(l.data, type(self._rightChild.data)) and (type(l) is not None and l.data is not None):
            raise TypeError("Type mismatch between " + type(self._rightChild.data).__name__ + " and " + type(l.data).__name__)
        else:
            self._rightChild = l

    def __iter__(self):
        if self._data is None and (self._leftChild is not None or self._rightChild is not None):
            yield self._data
        if self._data is not None:
            yield self._data
        if self._leftChild is not None:
            for p in self._leftChild.__iter__():
                yield p
        if self._rightChild is not None:
            for p in self._rightChild.__iter__():
                yield p

if __name__ == "__main__":
    t1 = BinaryTree(0)
    t1.add_rightchild(BinaryTree(5))
    t2 = BinaryTree(4)
    t2.add_leftchild(BinaryTree(2))
    t2.add_rightchild(BinaryTree(None))
    t1.add_leftchild(t2)
    print(list(iter(t1)))








