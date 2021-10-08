## 公众号(纯洁编程说：chunjie_tech)

## java中finally的执行时机

java中的finally一定会被执行吗？

答案：不是。

1、在执行try块之前直接return，finally块是不会执行的。(try块不执行，finally块也不执行)

2、在执行try块之前制造一个错误，直接爆红，finally块也是不会执行的。(try块不执行，finally块也不执行)

3、一般来说，执行了try块，finally块就会执行，但也有极端情况不会执行，如果执行了System.exit(0);，那么退出了jvm，finally块也不执行了。

4、finally执行时机：若return在finally块之前的try块或者catch块，那么finally块执行在的return之前。
                          
5、若在finally块之中进行return，那么return的值就是finally块中的return值；

   若在finally块之外进行return，那么return的值就是进行完上面的操作后的return值。