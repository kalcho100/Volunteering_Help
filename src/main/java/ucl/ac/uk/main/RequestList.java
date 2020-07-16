package ucl.ac.uk.main;

import java.util.Iterator;

public class RequestList{
    private Element head;
    private static class Element{
        private Request value;
        double score;
        private Element next;

        public Element(Request value, double score, Element next){
            this.value = value;
            this.score = score;
            this.next = next;
        }

        public Request getValue() {
            return value;
        }

        public Element getNext () {
            return next;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public void setValue (Request a){
            this.value = a;
        }

        public void setNext (Element a){
            this.next = a;
        }

        public Element copy() {
            return new Element(value, score, next == null ? null : next.copy());
        }
    }

    public RequestList()
    {
        head = new Element(null, -1, null);
    }

    public Element getHead(){
        return head;
    }

    public Element getTail() {return head.getNext().getNext(); }

    public void add(double score, Request a) {
        Element dummy = new Element(a, score, null);
        Element current = head;
        while(true) {
            if(current.getNext() == null){
                current.setNext(dummy);
                break;
            }
            else if (score <= current.getNext().getScore()) {
                dummy.setNext(current.getNext());
                current.setNext(dummy);
                break;
            }
            else{
                current = current.getNext();
            }
        }
    }

    public boolean contains(Request value){
        if(head == null){
            return false;
        }
        Element current = head.getNext();
        for(int i = 0; i < size(); i++){
            if(current.getValue().equals(value)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty(){
        return head.getNext() == null;
    }

    public int size(){
        int sum = 0;
        if(head == null){
            return sum;
        }
        Element current = head;
        while(current.getNext() != null){
            sum += 1;
            current = current.getNext();
        }
        return sum;
    }

    private class RequestListIterator implements Iterator<Request>
    {
        private Element current = head;
        public boolean hasNext()
        {
            return current.getNext() != null;
        }

        public Request next()
        {
            if (current != null) {
                current = current.getNext();
                return current.getValue();
            }
            return null;
        }
    }

    public Iterator<Request> iterator(){
        return new RequestListIterator();
    }
}
