```tex
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
druid连接池默认：项目访问地址+/druid，若配置中定义了druidWebStatFilter，则使用相应的定义配置。
IOC：控制反转-别名（依赖注入DI）	AOP：切面编程	ORM-对象关系映射（Object Relation Mapping）
jdk5注解新特性：通过注解来配置信息就是为了简化IOC容器的配置，注解可以把对象添加到IOC容器中、处理对象依赖关系
POJO（Plain Ordinary Java Object）：简单的Java对象，普通JavaBeans
EJB（Enterprise Java Beans）：是JavaEE中的商业应用组件技术，是JavaEE三大组件（Servlet，JSP，EJB）之一。EJB提供了让客户端使用远程分布式对象的框架，极大地简化了具有良好的可扩充性的企业级应用的开发。EJB组件结构是基于组件的分布式计算结构，是分布式应用系统中的组件。
JPA是Java Persistence API的简称，中文名Java持久层API，是JDK5.0注解或XML描述对象－关系表的映射关系，并将运行期的实体对象持久化到数据库中。
BBP的全称：BeanPostProcessor，一般我们俗称对象后处理器
```

#### Spring

**无论是创建对象、处理对象之间的依赖关系、对象创建的时间还是对象的数量，我们都是在Spring为我们提供的IOC容器上配置对象的信息就好了**

> ==**6大模块**==
>
>  - Spring Core  spring的核心功能： IOC容器, 解决对象创建及依赖关系
>
>  - Spring Web  Spring对web模块的支持。
>
>  - - 可以与struts整合,让struts的action创建交给spring
>    - spring mvc模式
>
>  - Spring DAO  Spring 对jdbc操作的支持  【JdbcTemplate模板工具类】
>
>  - Spring ORM  spring对orm的支持：
>
>  - - 既可以与hibernate整合，【session】
>    - 也可以使用spring的对hibernate操作的封装
>
>  - Spring AOP  切面编程
>
>  - SpringEE  spring 对javaEE其他模块的支持
>
>  ==创建对象以及处理对象依赖关系，相关的注解：==
>
>  - **@ComponentScan扫描器**
>
>  - **@Configuration表明该类是配置类**
>
>  - **@Component  指定把一个对象加入IOC容器--->@Name也可以实现相同的效果【一般少用】**
>
>  - **@Repository  作用同@Component； 在持久层使用**
>
>  - **@Service    作用同@Component； 在业务逻辑层使用**
>
>  - **@Controller   作用同@Component； 在控制层使用**
>
>  - **@Resource  依赖关系**
>  - - **如果@Resource不指定值，那么就根据类型来找，相同的类型在IOC容器中不能有两个**
>  - -  **如果@Resource指定了值，那么就根据名字来找**
>
>  ==**Bean创建细节总结**==  
>  1)对象创建： 单例/多例
>   `scope="singleton"`, 默认值， 即 默认是单例 【service/dao/工具类】
>   `scope="prototype"`, 多例；        【Action对象】
>
>  2)什么时候创建?
>    `scope="prototype"`  在用到对象的时候，才创建对象。
>    `scope="singleton"`  在启动(容器初始化之前)， 就已经创建了bean，且整个应用只有一个。
>  3)是否延迟创建
>    `lazy-init="false`"  默认为false,  不延迟创建，即在启动时候就创建对象
>    `lazy-init="true"`  延迟初始化， 在用到对象的时候才创建对象
>    （只对单例有效）
>  4)创建对象之后，初始化/销毁
>    `init-method="init_user"`    【对应对象的init_user方法，在对象创建之后执行 】注解：`@PostConstruct`
>    `destroy-method="destroy_user"`  【在调用容器对象的destroy方法时候执行，(容器用实现类)】注解：`@PreDestroy`
>
> ==依赖注入和控制反转==：所谓的依赖注入，则是甲方开放接口，在它需要的时候，能够讲乙方传递进来(注入)；所谓的控制反转，甲乙双方不相互依赖，交易活动的进行不依赖于甲乙任何一方，整个活动的进行由第三方负责管理。

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

#### lambda表达式

##### 内置函数式接口

```java
//1、Consumer 消费性接口：void accept(T t)；
//有一个参数，并且无返回值
public static void test3() {
    //这个e就代表所实现的接口的方法的参数，
    Consumer<String> consumer = e->System.out.println("Lambda 表达式方式，"+e);
    consumer.accept("传入参数");
}
//2、Supplier供给型接口： T get();
public class Test2 {
    public static void main(String[] args) {
        ArrayList<Integer> res = getNumList(10,()->(int)(Math.random()*100));
        System.out.println(res);
    }
    public static ArrayList<Integer> getNumList(int num, Supplier<Integer> sup){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer e = sup.get();
            list.add(e);
        }
        return list;
    }
}
//3、Function 函数式接口：R apply(T t);
public class Test2 {
    public static void main(String[] args) {
        String newStr = strHandler("abc",(str)->str.toUpperCase());
        System.out.println(newStr);
        newStr = strHandler("  abc  ",(str)->str.trim());
        System.out.println(newStr);
    }
    public static String strHandler(String str, Function<String,String>fun){
        return fun.apply(str);
    }
}
//4、Predicate 断言式接口：boolean test(T t);
public class Test2 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello","jiangshuying","lambda","www","ok","q");
        List<String> ret = filterStr(list,(str)->str.length()>2);
        System.out.println(ret);
    }
    public static List<String> filterStr(List<String> list, Predicate<String> pre){
        ArrayList<String> arrayList = new ArrayList<>();
        for(String str:list){
            if(pre.test(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }
}
```

#####  方法引用与构造器引用

```
对象::实例方法名
类::静态方法名
类::实例方法名
```

```java
public static void test9(){
    Comparator<Integer> comparator = (x,y)->Integer.compare(x,y);
    Comparator<Integer> comparator1 = Integer::compare;
    int compare = comparator.compare(1,2);
    int compare1 = comparator1.compare(1,2);
    System.out.println("compare:"+compare);
    System.out.println("compare1:"+compare1);
}
//::-把方法当做参数传到stream内部，使stream的每个元素都传入到该方法里面执行一下
public class MyTest {
    public static void  printValur(String str){
        System.out.println("print value : "+str);
    }
 
    public static void main(String[] args) {
        List<String> al = Arrays.asList("a", "b", "c", "d");
        al.forEach(AcceptMethod::printValur);
        //下面的方法和上面等价的
        Consumer<String> methodParam = AcceptMethod::printValur; //方法参数
        al.forEach(x -> methodParam.accept(x));//方法执行accept
    }
}
//OPC UA通信协议 物联网-IOT
```

##### lambda表达式的一些常见用法

```java
//1、使用lambda表达式对集合进行迭代
public class Test3 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("java","c#","javascript");
        //before java8
        for (String str:list){
            System.out.println("before java8,"+str);
        }
        //after java8
        list.forEach(x-> System.out.println("after java8,"+x));
    }
}
//2、用lambda表达式实现map
public class Test3 {
    public static void main(String[] args) {
        List<Double> list = Arrays.asList(10.0,20.0,30.0);
        list.stream().map(x->x+x*0.05).forEach(x-> System.out.println(x));
    }
}
//3、用lambda表达式实现map与reduce
public class Test3 {
    public static void main(String[] args) {
        //before java8
        List<Double> cost = Arrays.asList(10.0, 20.0,30.0);
        double sum = 0;
        for(double each:cost) {
            each += each * 0.05;
            sum += each;
        }
        System.out.println("before java8,"+sum);
        //after java8
        List<Double> list = Arrays.asList(10.0,20.0,30.0);
        double sum2 = list.stream().map(x->x+x*0.05).reduce((sum1,x)->sum1+x).get();
        System.out.println("after java8,"+sum2);
    }
}
//4、filter操作
public class Test3 {
    public static void main(String[] args) {
        List<Double> cost = Arrays.asList(10.0, 20.0,30.0,40.0);
        List<Double> filteredCost = cost.stream().filter(x -> x > 25.0).collect(Collectors.toList());
        filteredCost.forEach(x -> System.out.println(x));
    }
}
//5、与函数式接口Predicate配合
public class Test4 {
    public static void filterTest(List<String> languages, Predicate<String> condition) {
        languages.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x + " "));
    }
    public static void main(String[] args) {
        List<String> languages = Arrays.asList("Java","Python","scala","Shell","R");
        filterTest(languages,x->x.startsWith("J"));//Java 
        filterTest(languages,x -> x.endsWith("a"));//Java,scala 
        filterTest(languages,x -> true);//Java,Python,scala,Shell,R
        filterTest(languages,x -> false);//
        filterTest(languages,x -> x.length() > 4);//Python,scala,Shell,
    }
}
```

