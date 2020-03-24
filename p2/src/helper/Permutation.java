package helper;

import java.util.List;

public class Permutation {
    public static void permutation(int[] arr, int pos, List<int[]> list){
        if(arr.length - pos == 1)
            list.add(arr.clone());
        else
            for(int i = pos; i < arr.length; i++){
                swap(arr, pos, i);
                permutation(arr, pos+1, list);
                swap(arr, pos, i);
            }
    }

    public static void swap(int[] arr, int pos1, int pos2){
        int h = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = h;
    }
}

