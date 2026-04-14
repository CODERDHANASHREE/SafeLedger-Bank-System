package logic;
import model.Transaction;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BinarySearchTransaction {
    public static Transaction search(Transaction arr[],int id){
        Arrays.sort(arr, (t1, t2) -> t1.transactionId - t2.transactionId);

        int left=0;
        int right = arr.length-1;

        while(left<=right){
            int mid=left + (left-right)/2;

            if(arr[mid].transactionId==id){
                return arr[mid];
            }else if(arr[mid].transactionId < id){
                left=mid+1;
            }else{
                right=mid-1;
            }
        }
        return null;
    }
}
