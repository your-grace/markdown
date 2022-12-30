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

