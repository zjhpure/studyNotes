## 公众号(纯洁编程说：chunjie_tech)

## 跨库分页方案

mysql分库分表后，跨库分页成了业界难题

#### 方案1：全局视野法

1、将order by time offset X limit Y，改写成order by time offset 0 limit X+Y

2、服务层对得到的N*(X+Y)条数据进行内存排序，内存排序后再取偏移量X后的Y条记录

这种方法随着翻页的进行，性能越来越低。

即：每次从各个库中取前n页的数据(因为极限情况下可能所有数据都落在同一个库里)，取出后再在内存中进行排序，取出第n页的数据。

优点：通过服务层修改SQL语句，扩大数据召回量，能够得到全局视野，业务无损，精准返回所需数据。

缺点(显而易见)：

(1)每个分库需要返回更多的数据，增大了网络传输量(耗网络)；

(2)除了数据库按照time进行排序，服务层还需要进行二次排序，增大了服务层的计算量(耗CPU)；

(3)最致命的缺点是，这个算法随着页码的增大，性能会急剧下降，这是因为SQL改写后每个分库要返回X+Y行数据：返回第3页，offset中的X=200；假如要返回第100页，offset中的X=9900，即每个分库要返回100页数据，数据量和排序量都将大增，性能平方级下降。
即：假如你要获取第10页的数据，那么你要从每一个库获取前10页的数据，再把这些数据进行内排序，再从这些数据中取第10页，随着页码的增大，就会越耗时。

#### 方案2：业务折衷法-禁止跳页查询

1、用正常的方法取得第一页数据，并得到第一页记录的time_max(取第一页时和全局视野法一样)

2、每次翻页，将order by time offset X limit Y，改写成order by time where time>$time_max limit Y

以保证每次只返回一页数据，性能为常量。

因为每次获得当前页面数据后，都能获取到当前页面数据的最大值，

那么在获取下一页数据时，可以把大于上一页最大值的条件加上，

那么每次获取一页数据，只会从各个库中获取一页数据出来，

再把这些数据进行内排序，获取这些数据的第一页数据，就是当前页的数据。

优点：不会随着页码的增大，性能急剧下降。

缺点：不能跳页查询，但是在很多场景适用，很多产品并不提供“直接跳到指定页面”的功能，而只提供“下一页”的功能，这一个小小的业务折衷，就能极大的降低技术方案的复杂度。
例如在app上下拉数据，就不会有"跳转到指定页面"的功能，只会不断向下一页查询，但是对于管理后台，一般都需要提供"跳转到指定页面"的功能，那么禁止跳页查询就有很大的缺陷。

