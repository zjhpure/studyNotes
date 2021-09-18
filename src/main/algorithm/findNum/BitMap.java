package findNum;

import java.util.BitSet;

/**
 * @author zhangjianhua
 * @date 2021-09-18 19:30
 * @description Bitmap在Java中的应用
 */
public class BitMap {

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 22, 0, 3};

        BitSet bitSet = new BitSet(6);

        // 将数组内容组bitmap
        for (int value : array) {
            bitSet.set(value, true);
        }

        System.out.println(bitSet.size());
        System.out.println(bitSet.get(3));
        System.out.println(bitSet.get(2));
        System.out.println(bitSet.get(10));
    }

}
