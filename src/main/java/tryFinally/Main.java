package tryFinally;

/**
 * @author zhangjianhua
 * @date 2021-10-08 14:59
 * @description java中finally的执行时机
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("test1 执行返回：" + test1());
//        System.out.println("test2 执行返回：" + test2()); // 这里需要注释掉才能测试后面，因为test2()无法编译通过
//        System.out.println("test3 执行返回：" + test3()); // 这里需要注释掉才能测试后面，因为如果不注释掉，那么执行了System.exit(0);，退出了jvm，后面的也不会执行了
        System.out.println("test4 执行返回：" + test4());
        System.out.println("test5 执行返回：" + test5());
        System.out.println("test6 执行返回：" + test6());
        System.out.println("test7 执行返回：" + test7());
        System.out.println("test8 执行返回：" + test8());
        System.out.println("test9 执行返回：" + test9());
    }

    // 在执行try块之前直接return，finally块是不会执行的
    private static int test1() {
        int i = 11;
        if (i == 11) {
            return i;
        }
        try {
            System.out.println("test1 执行try");
        } finally {
            System.out.println("test1 执行finally");
        }
        return 0;
    }

//    // 在执行try块之前制造一个错误，直接爆红，finally块也是不会执行的
//    private static int test2() {
//        return 1 / 0;
//        try {
//            System.out.println("test2 执行try");
//        } finally {
//            System.out.println("test2 执行finally");
//        }
//        return 0;
//    }

//    // 在执行了try块执行了System.exit(0);，那么退出了jvm，finally块也不执行了
//    private static int test3() {
//        try {
//            System.out.println("test3 执行try");
//            System.exit(0);
//        } catch (Exception e) {
//            System.out.println("test3 执行catch");
//        } finally {
//            System.out.println("test3 执行finally");
//        }
//        return 0;
//    }

    // 常规情况，finally块执行在try块的return之前
    private static int test4() {
        try {
            System.out.println("test4 执行try");
            return 11;
        } finally {
            System.out.println("test4 执行finally");
        }
    }

    // finally执行在catch块的return之前
    private static int test5() {
        try {
            System.out.println("test5 执行try");
            return 1 / 0;
        } catch (Exception e) {
            System.out.println("test5 执行catch");
            return 11;
        } finally {
            System.out.println("test5 执行finally");
        }
    }

    // 重点注意的情况，这里容易搞错，finally块不含return值，但是做改变变量值的操作，是不会改变return值的
    private static int test6() {
        int i = 0;
        try {
            System.out.println("test6 执行try：" + i);
            return i;
        } finally {
            ++i;
            System.out.println("test6 执行finally：" + i);
        }
    }

    // finally中含有return值，return的值就是finally块中的return值
    private static int test7() {
        try {
            System.out.println("test7 执行try");
            return 1;
        } finally {
            System.out.println("test7 执行finally");
            return 2;
        }
    }

    // finally中含有return值，return的值就是finally块中的return值
    private static int test8() {
        int i = 1;
        try {
            System.out.println("test8 执行try：" + i);
            return i;
        } finally {
            ++i;
            System.out.println("test8 执行finally：" + i);
            return i;
        }
    }

    // finally中含有return值，return的值就是finally块中的return值
    private static int test9() {
        int i = 1;
        try {
            System.out.println("test9 执行try：" + i);
        } finally {
            ++i;
            System.out.println("test9 执行finally：" + i);
        }
        return i;
    }

}
