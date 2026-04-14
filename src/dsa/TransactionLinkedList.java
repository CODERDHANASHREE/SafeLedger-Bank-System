package dsa;
import model.Transaction;

class Node{
    Transaction data;
    Node next;

    Node(Transaction data){
        this.data=data;
        this.next=null;
    }
}
public class TransactionLinkedList {
    Node head;

    public void add(Transaction t){
        Node newNode = new Node(t);

        if(head==null){
            head=newNode;
            return;
        }

        Node temp=head;

        while(temp.next!=null){
          temp=temp.next;
        }
        temp.next=newNode;
    }

    public void display(){
        Node temp=head;

        while(temp!=null) {
            System.out.println(temp.data.transactionId + " " + temp.data.type + " " + temp.data.amount);
            temp = temp.next;
        }
    }
}
