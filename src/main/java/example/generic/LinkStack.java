package example.generic;

public class LinkStack<T> {

    private static class Node<T> {
        T item;
        Node<T> next;
        public Node (T item,Node<T> next) {
            this.item = item;
            this.next = next;
        }
        public Node () {}
        public boolean end () {
            return item == null && next == null; //使用end sentinel(结束哨兵)
        }

    }
    private Node<T> top =  new Node<>();

    void push (T item) {
        top = new Node<>(item,top);
    }

    T pop () {
        if (!top.end()) {
            Node<T> temp = top;
            top = top.next;
            return temp.item;
        }
        return null;
    }

    public static void main(String[] args) {
        LinkStack<Student> stack = new LinkStack<>();
        stack.push(new Student());
        stack.push(new Student());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }


}

