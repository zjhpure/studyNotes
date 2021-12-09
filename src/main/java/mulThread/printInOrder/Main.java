package mulThread.printInOrder;

import java.util.Scanner;

/**
 * @author zhangjianhua
 * @date 12/9/21 8:50 PM
 * @description 按序打印的运行
 */
public class Main {

    public static void main(String[] args) {
        // 获取输入结果
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        scanner.close();

        // 处理输入结果
        String[] strs = str.split("\\[")[1].split("]")[0].split(",");
        int size = strs.length;
        int[] nums = new int[size];
        for (int i = 0; i < size; ++i) {
            nums[i] = Integer.parseInt(strs[i]);
        }

        // 执行
        run(nums);
    }

    // 根据输入的顺序执行方法，并且在多线程的环境下执行
    private static void run(int[] nums) {
        Foo foo = new Foo();

        Runnable runnable1 = () -> runMethod(nums[0], foo);
        Thread thread1 = new Thread(runnable1);
        thread1.start();

        Runnable runnable2 = () -> runMethod(nums[1], foo);
        Thread thread2 = new Thread(runnable2);
        thread2.start();

        Runnable runnable3 = () -> runMethod(nums[2], foo);
        Thread thread3 = new Thread(runnable3);
        thread3.start();
    }

    private static void runMethod(int num, Foo foo) {
        switch (num) {
            case 1:
                foo.first();
                break;
            case 2:
                foo.second();
                break;
            case 3:
                foo.third();
                break;
        }
    }

}
