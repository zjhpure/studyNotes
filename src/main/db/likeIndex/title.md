## 公众号(纯洁编程说：chunjie_tech)

## like百分号查询的索引问题

sql的like查询%在前不走索引。

100w测试数据的一个表：

1、假设c_user_id有索引，like中的%在前不走索引，%不在前走索引，两者的运行时间有明显的差别，%在前的慢很多，%不在前的快很多；

select * from t_user where c_user_id like '291a';

select * from t_user where c_user_id like '%291a';

select * from t_user where c_user_id like '37a5b636%';

select * from t_user where c_user_id like '%291a%';

2、假设c_name没有索引，%在哪里运行时间都差不多。

select * from t_user where c_name like '546';

select * from t_user where c_name like '%546';

select * from t_user where c_name like '546%';

select * from t_user where c_name like '%546%';

分析：
为什么like查询%在前为什么不走索引？

因为和B+Tree有关系，索引树从左到右都是有顺序的，对于索引中的关键字进行对比的时候，一定是从左往右以此对比，且不可跳过。

为什么是最左匹配原则？因为我们要比较一个字符串xttblog与xmtblog，我们肯定是先从第一个字符开始比较，第一个相同后，再比较第二个字符，以此类推。

所以要从左边开始，并且是不能跳过的，SQL索引也是这样的，%在前，就代表前面的内容不确定，不确定，怎么比较？只能一个一个的比较，那就相当于全匹配了，全匹配就不需要索引，还不如直接全表扫描。

tip：
mysql性能优化有个神器，叫做explain，它可以对select语句进行分析并且输出详细的select执行过程的详细信息，让开发者从这些信息中获得优化的思路。

例如：
explain select * from user where id = 1

执行完毕之后，它的输出有以下字段：
id
select_type
table
partitions
type
possible_keys
key
key_len
ref
rows
Extra

如果使用到了索引，key这一项会显示，否则key这一项会显示为null。
通过这种方法，可以验证like查询时各种%的情况是否使用了索引。

like是否使用索引的规律：
like语句要使索引生效，like后不能以%开始，即：
(like %字段名%)、(like %字段名)这类语句会使索引失效。
(like 字段名)、(like 字段名%)这类语句索引是可以正常使用的。