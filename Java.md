```
静态初始器（静态块）Static Initializer	初始化操作顺序（加载类之后和首次使用之前）：静态代码-对象代码-构造函数
实例化方式：new语句 反射 clone()方法 反序列化readObject
JMM:Java Memory Model
无序列化：transient
共享不稳定可见性：volatile（禁止指令进行重排序优化）
原子性：Atomict	
独占：Exclusive
HQL:Hibernate Query Langauage	
getSession():createQuery、save、saveOrUpdate、delete、update、createCriteria、createSQLQuery
AbstractQueuedSynchronizer：抽象队列同步器-AQS
CAS：Compare And Swap
独占方式：tryAcquire-tryRelease
共享方式：tryAcquireShared-tryReleaseShared
组合方式：ReentrantReadWriteLock
内存溢出：out of memory
CPU密集型：N+1	I/O密集型：2N
happens-before规则（JSR 133）前一个操作的结果对后一个操作是可见的，无论这两个操作是否在同一个线程里	
并发编程三个重要特性：原子性（synchronized/lock）、可见性（synchronized/volatile/lock）、有序性
```

#### join用法

```java
Set set = new HashSet();
set.add("13-1");
set.add("14-1");
System.out.println(String.join(",", set));//14-1,13-1
List list = new ArrayList<String>();
list.add("1");
list.add("2");
System.out.println(String.join("-", list));//1-2-3
String[] str = new String[]{"a", "b", "c"};
System.out.println(String.join(";", str));//a;b;c
```

#### 数组最大值

```java
//依赖 Arrays.sort() 实现
import java.util.Arrays;
public class ArrayMax {
    public static void main(String[] args) {
        int[] arr = {3, 7, 2, 1, -4};
        int max = findMaxBySort(arr); // 根据 Arrays.sort 查找最大值
        System.out.println("最大值是：" + max);
    }
    /**
     * 根据 Arrays.sort 查找最大值
     * @param arr 待查询数组
     * @return 最大值
     */
    private static int findMaxBySort(int[] arr) {
        Arrays.sort(arr);
        return arr[arr.length - 1];
    }
}
//根据 Arrays.stream() 实现
import java.util.Arrays;
public class ArrayMax {
    public static void main(String[] args) {
        int[] arr = {3, 7, 2, 1, -4};
        int max = findMaxByStream(arr); // 根据 stream 查找最大值
        System.out.println("最大值是：" + max);
    }
    /**
     * 根据 stream 查找最大值
     * @param arr 待查询数组
     * @return 最大值
     */
    private static int findMaxByStream(int[] arr) {
        return Arrays.stream(arr).max().getAsInt();
    }
}
//依赖 Collections.max() 实现
import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;
import java.util.Collections;
public class ArrayMax {
    public static void main(String[] args) {
        int[] arr = {3, 7, 2, 1, -4};
        int max = findMaxByCollections(arr); // 根据 Collections 查找最大值
        System.out.println("最大值是：" + max);
    }
    /**
     * 根据 Collections 查找最大值
     * @param arr 待查询数组
     * @return 最大值
     */
    private static int findMaxByCollections(int[] arr) {
        List<Integer> list = Arrays.asList(
                org.apache.commons.lang3.ArrayUtils.toObject(arr));
        return Collections.max(list);
    }
}
```

