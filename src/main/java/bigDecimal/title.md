## 公众号(纯洁编程说：chunjie_tech)

## java中BigDecimal使用

我们在使用BigDecimal时，使用它的BigDecimal(String)构造器创建对象才有意义，才不会丢失精度。

例如：
System.out.println(0.05 + 0.01);  
System.out.println(1.0 - 0.42);  
System.out.println(4.015 * 100);  
System.out.println(123.3 / 100);  
     
以上都会丢失精度，可见直接传入数字到BigDecimal中会导致丢失精度。

例如：
BigDecimal a = new BigDecimal(1.01);
BigDecimal b = new BigDecimal(1.02);
BigDecimal c = new BigDecimal("1.01");
BigDecimal d = new BigDecimal("1.02");
System.out.println(a.add(b));
System.out.println(c.add(d));

输出：
2.0300000000000000266453525910037569701671600341796875
2.03

但是把数字写成字符串再传入到BigDecimal中，就不会丢失精度。

另外，BigDecimal所创建的对象不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，而是必须调用其相对应的方法，方法中的参数也必须是BigDecimal的对象。


