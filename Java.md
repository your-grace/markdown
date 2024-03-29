```tex
静态初始器（静态块）Static Initializer	初始化操作顺序（加载类之后和首次使用之前）：静态代码-对象代码-构造函数
实例化方式：new语句 反射 clone()方法 反序列化readObject
JMM:Java Memory Model-java内容模型
无序列化：transient	即时编译器：JIt（Just-In-Time）	预先编译：AOT(Ahead-Of-Time)
共享不稳定可见性：volatile（禁止指令进行重排序优化），修饰变量对所有线程可见，保证修饰变量可见性和顺序性
重量级锁：synchronized	BlockingQueue-阻塞队列：生产者消费者存放元素容器
原子性：Atomic+包装类型		Pair-键值对容器
独占：Exclusive	获得-acquired	park-阻塞线程	unpark-唤醒线程
HQL:Hibernate Query Langauage	cas-compare and swap
getSession():createQuery、save、saveOrUpdate、delete、update、createCriteria、createSQLQuery
AbstractQueuedSynchronizer：抽象队列同步器-AQS
CAS：Compare And Swap
独占方式：tryAcquire-tryRelease
共享方式：tryAcquireShared-tryReleaseShared
组合方式：ReentrantReadWriteLock
内存溢出-OOM：out of memory
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
-Dserver.port=8082 启动端口在Edit-Configurations中修改
服务消费者	服务提供者
指定FeignClient字节码
@EnableFeignClients(clients={UserClient.class})
RestTemplate远程调用	Eureka注册中心	Ribbon负载均衡	Nacos(Nacos服务分级存储)注册中心、配置管理中心
Feign远程调用（http客户端）	Gateway(SpringCloudGateway)统一网关-基于Spring5提供的WebFlux，响应式编程实现	
Zuul基于Servlet实现，阻塞式编程 	Load-Balance负载均衡lb
Apache-Lucene：以 Lucene 为基础建立的开源可用全文搜索引擎主要是 Solr 和 Elasticsearch
Docker Compose：Docker容器编排 
CI/CD：一个CI(Continuous Integration,表示持续集成)，两个CD(Continuous Delivery和 Continuous Deployment,分别是持续交付和持续部署。)
基本数据类型和对象的引用-栈stack
new关键字创建的对象-堆heap
jmap -histo:live pid | head -n 10
查看到堆内对象示例的统计信息、查看 ClassLoader 的信息以及 finalizer 队列
JDK的源码所在位置在JDK安装路径下的src.zip文件，需手动导入到Project Structrue SDKs-Classpath下
javap -c Demo.class	反编译class文件为字节码指令
常用工具类：Scanner Arrays StringUtils Objects Collections Hutool Guava commons-lang3	joda-time
java8引入语法：类名::方法名-方法名后没有()
StandardCharsets.UTF_8 枚举字符编码
MinorGC-新生代GC	MajorGC-老年代GC
MCS-并发标记清除（Concurrent Mark Sweep）
书：Effective Java
```

```bash
mvn clean install package '-Dmaven.test.skip=true'
javac -g Test.java	#编译为class文件
javap -verbose Test.class	#查看class文件格式
xxd hello.class	#给定文件转换成十六进制形式，在bash终端中使用
javap -v -p Main.class	#反编译命令
jps	#当前用户的Java进程
#默认**显示 pid **以及 main 方法对应的 class 名称
#-v：输出传递给 JVM 的参数
#-l： 输出 main 方法对应的 class 的完整 package 名
#HSDB（Hotspot Debugger)，是一款内置于 SA 中的 GUI 调试工具，可用于调试 JVM 运行时数据，从而进行故障排除
#windows
$ java -classpath "%JAVA_HOME%/lib/sa-jdi.jar" sun.jvm.hotspot.HSDB
java -Dspring.config.location=..\config\application.properties -jar kkFileView-4.0.0.jar -> ..\log\kkFileView.log
#使用指定的配置文件，启动jar文件，标准输出重定向到日志文件中。
#该命令允许以自定义的配置启动该应用程序，并将日志输出到指定的日志文件中。
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

#### 如何重构-SOLID原则

> 单一职责原则(SRP)：一个类只负责完成一个职责或者功能，不要存在多于一种导致类变更的原因。
> 开放-关闭原则（OCP)：添加一个新的功能，应该是通过在已有代码基础上扩展代码（新增模块、类、方法、属性等），而非修改已有代码（修改模块、类、方法、属性等）的方式来完成。
> 里氏替换原则(LSP)：子类对象（object of subtype/derived class）能够替换程序（program）中父类对象（object of base/parent class）出现的任何地方，并且保证原来程序的逻辑行为（behavior）不变及正确性不被破坏。
> 接口隔离原则(ISP)：调用方不应该依赖它不需要的接口；一个类对另一个类的依赖应该建立在最小的接口上。 接口隔离原则提供了一种判断接口的职责是否单一的标准：通过调用者如何使用接口来间接地判定。如果调用者只使用部分接口或接口的部分功能，那接口的设计就不够职责单一。
> 依赖反转原则(DIP)：高层模块不应该依赖低层模块，二者都应该依赖其抽象；抽象不应该依赖细节，细节应该依赖抽象。
> 迪米特法则：一个对象应该对其他对象保持最少的了解
> 合成复用原则：尽量使用合成/聚合的方式，而不是使用继承

#### ClassUtils
spring 的`org.springframework.util`包下的`ClassUtils`类，它里面有很多让我们惊喜的功能。
它里面包含了类和对象相关的很多非常实用的方法。
##### 获取对象的所有接口
如果你想获取某个对象的所有接口，可以使用 ClassUtils 的`getAllInterfaces`方法。例如：
```java
Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(new User());
```
##### 获取某个类的包名
如果你想获取某个类的包名，可以使用 ClassUtils 的`getPackageName`方法。例如：
```java
String packageName = ClassUtils.getPackageName(User.class);
System.out.println(packageName);
```
#####  判断某个类是否内部类
如果你想判断某个类是否内部类，可以使用 ClassUtils 的`isInnerClass`方法。例如：
```java
System.out.println(ClassUtils.isInnerClass(User.class));
```
##### 判断对象是否代理对象
如果你想判断对象是否代理对象，可以使用 ClassUtils 的`isCglibProxy`方法。例如：
```java
System.out.println(ClassUtils.isCglibProxy(new User()));
```
#### BeanUtils
Spring 给我们提供了一个`JavaBean`的工具类，它在`org.springframework.beans`包下面，它的名字叫做：`BeanUtils`。
让我们一起看看这个工具可以带给我们哪些惊喜。

##### 拷贝对象的属性
曾几何时，你有没有这样的需求：把某个对象中的所有属性，都拷贝到另外一个对象中。这时就能使用 BeanUtils 的`copyProperties`方法。例如：
```java
User user1 = new User();
user1.setId(1L);
user1.setName("沉默王二");
user1.setAddress("中国");
User user2 = new User();
BeanUtils.copyProperties(user1, user2);
System.out.println(user2);
```
##### 实例化某个类
如果你想通过反射实例化一个类的对象，可以使用 BeanUtils 的`instantiateClass`方法。例如：
```java
User user = BeanUtils.instantiateClass(User.class);
System.out.println(user);
```
##### 获取指定类的指定方法
如果你想获取某个类的指定方法，可以使用 BeanUtils 的`findDeclaredMethod`方法。例如：
```java
Method declaredMethod = BeanUtils.findDeclaredMethod(User.class, "getId");
System.out.println(declaredMethod.getName());
```
##### 获取指定方法的参数
如果你想获取某个方法的参数，可以使用 BeanUtils 的`findPropertyForMethod`方法。例如：
```java
Method declaredMethod = BeanUtils.findDeclaredMethod(User.class, "getId");
PropertyDescriptor propertyForMethod = BeanUtils.findPropertyForMethod(declaredMethod);
System.out.println(propertyForMethod.getName());
```
#### ReflectionUtils
有时候，我们需要在项目中使用`反射`功能，如果使用最原始的方法来开发，代码量会非常多，而且很麻烦，它需要处理一大堆异常以及访问权限等问题。
好消息是 Spring 给我们提供了一个`ReflectionUtils`工具，它在`org.springframework.util`包下面。

##### 获取方法
如果你想获取某个类的某个方法，可以使用 ReflectionUtils 类的`findMethod`方法。例如：
```java
Method method = ReflectionUtils.findMethod(User.class, "getId");
```
##### 获取字段
如果你想获取某个类的某个字段，可以使用 ReflectionUtils 类的`findField`方法。例如：
```java
Field field = ReflectionUtils.findField(User.class, "id");
```
##### 执行方法
如果你想通过反射调用某个方法，传递参数，可以使用 ReflectionUtils 类的`invokeMethod`方法。例如：
```java
 ReflectionUtils.invokeMethod(method, springContextsUtil.getBean(beanName), param);
```
##### 判断字段是否常量
如果你想判断某个字段是否常量，可以使用 ReflectionUtils 类的`isPublicStaticFinal`方法。例如：
```java
Field field = ReflectionUtils.findField(User.class, "id");
System.out.println(ReflectionUtils.isPublicStaticFinal(field));
```
##### 判断是否 equals 方法
如果你想判断某个方法是否 equals 方法，可以使用 ReflectionUtils 类的`isEqualsMethod`方法。例如：
```java
Method method = ReflectionUtils.findMethod(User.class, "getId");
System.out.println(ReflectionUtils.isEqualsMethod(method));
```
#### Optional
```java
//过滤值
public class FilterOptionalDemo {
    public static void main(String[] args) {
        String password = "12345";
        Optional<String> opt = Optional.ofNullable(password);
        System.out.println(opt.filter(pwd -> pwd.length() > 6).isPresent());
    }
}
```
```java
//过滤值
Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;
password = "1234567";
opt = Optional.ofNullable(password);
boolean result = opt.filter(len6.and(len10)).isPresent();
System.out.println(result);
```
```java
//过滤值和转化值结合
public class OptionalMapFilterDemo {
    public static void main(String[] args) {
        String password = "password";
        Optional<String>  opt = Optional.ofNullable(password);

        Predicate<String> len6 = pwd -> pwd.length() > 6;
        Predicate<String> len10 = pwd -> pwd.length() < 10;
        Predicate<String> eq = pwd -> pwd.equals("password");

        boolean result = opt.map(String::toLowerCase).filter(len6.and(len10 ).and(eq)).isPresent();
        System.out.println(result);
    }
}
```
#### Lombok
1. `@Getter`：生成属性的getter方法。

2. `@Setter`：生成属性的setter方法。

3. `@ToString`：生成toString方法，方便输出对象的字符串表示。

4. `@EqualsAndHashCode`：生成equals和hashCode方法，用于对象比较和哈希计算。

5. `@NoArgsConstructor`：生成无参构造函数。

6. `@AllArgsConstructor`：生成全参构造函数。

7. `@Data`：生成getter、setter、toString、equals和hashCode等方法。

8. `@Builder`：生成Builder模式相关的代码，方便创建复杂对象。

9. `@Slf4j`：为类自动生成一个名为"log"的Slf4j Logger对象，简化日志记录。

10. `@SneakyThrows`：在方法中自动处理受检异常，使其不再需要显式地抛出。

#### reflect

1、获取反射类的Class对象

```java
Class c1 = Class.forName("com.itwanger.s39.ReflectionDemo3");
System.out.println(c1.getCanonicalName());

Class c2 = Class.forName("[D");
System.out.println(c2.getCanonicalName());

Class c3 = Class.forName("[[Ljava.lang.String;");
System.out.println(c3.getCanonicalName());
```

输出结果：

```text
com.itwanger.s39.ReflectionDemo3
double[]
java.lang.String[][]
```

类名 + `.class`，只适合在编译前就知道操作的 Class。。

```java
Class c1 = ReflectionDemo3.class;
System.out.println(c1.getCanonicalName());

Class c2 = String.class;
System.out.println(c2.getCanonicalName());

Class c3 = int[][][].class;
System.out.println(c3.getCanonicalName());
```

来看一下输出结果：

```java
com.itwanger.s39.ReflectionDemo3
java.lang.String
int[][][]
```

2、创建反射类的对象

通过反射来创建对象的方式有两种：

- 用 Class 对象的 `newInstance()` 方法。
- 用 Constructor 对象的 `newInstance()` 方法。

```java
Class c1 = Writer.class;
Writer writer = (Writer) c1.newInstance();

Class c2 = Class.forName("com.itwanger.s39.Writer");
Constructor constructor = c2.getConstructor();
Object object = constructor.newInstance();
```

3、获取构造方法

Class 对象提供了以下方法来获取构造方法 Constructor 对象：

- `getConstructor()`：返回反射类的特定 public 构造方法，可以传递参数，参数为构造方法参数对应 Class 对象；缺省的时候返回默认构造方法。
- `getDeclaredConstructor()`：返回反射类的特定构造方法，不限定于 public 的。
- `getConstructors()`：返回类的所有 public 构造方法。
- `getDeclaredConstructors()`：返回类的所有构造方法，不限定于 public 的。

```java
Class c2 = Class.forName("com.itwanger.s39.Writer");
Constructor constructor = c2.getConstructor();

Constructor[] constructors1 = String.class.getDeclaredConstructors();
for (Constructor c : constructors1) {
    System.out.println(c);
}
```

4、获取字段

大体上和获取构造方法类似，把关键字 Constructor 换成 Field 即可。

```java
Method setNameMethod = clazz.getMethod("setName", String.class);
Method getNameMethod = clazz.getMethod("getName");
```

5、获取方法

大体上和获取构造方法类似，把关键字 Constructor 换成 Method 即可。

```java
Method[] methods1 = System.class.getDeclaredMethods();
Method[] methods2 = System.class.getMethods();
```

“注意，如果你想反射访问私有字段和（构造）方法的话，需要使用 `Constructor/Field/Method.setAccessible(true)` 来绕开 Java 语言的访问限制。”

#### 视图-view
**ModelAndView**	

```java
@RequestMapping(params = "graph")
public ModelAndView graph(HttpServletRequest req, String processId,String processRouteid) {
    // 关系图
    String page = "com/wise/gemmes/processroute/drawPage";
    if(processRouteid!=null&&!processRouteid.equals("")){
        ProcessRouteEntity processRouteEntity= systemService.getEntity(ProcessRouteEntity.class,processRouteid );
        req.setAttribute("state", processRouteEntity.getUpdateState());
    }
    return new ModelAndView(page).addObject("processId", processRouteid);
}
//ModelAndView上方法addObject添加的属性，通过ViewResolver等处理最后添加到request中，与request.setAttribute添加属性效果一致
```

**String**

```java
@RequestMapping(params = "goSelectBom")
public String goSelectBom(HttpServletRequest req, String id,String updateState) {
    req.setAttribute("id",id);
    req.setAttribute("updateState",updateState);
    return "com/wise/gemmes/processroute/selectBom";
}
```

#### CriteriaQuery

```java
@RequestMapping(params = "routeFilesdatagrid")
public void routeFilesdatagrid(RouteFilesEntity routeFilesEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,String routeId) {
    CriteriaQuery cq = new CriteriaQuery(RouteFilesEntity.class, dataGrid);
    try{
        cq.add(Restrictions.eq("sourceId",routeId));
        if(StringUtils.isNotEmpty(routeFilesEntity.getType())){
            cq.add(Restrictions.eq("type", routeFilesEntity.getType()));
        }
        if(StringUtils.isNotEmpty(routeFilesEntity.getFileName())){
            cq.add(Restrictions.like("fileName", routeFilesEntity.getFileName(), MatchMode.ANYWHERE));
        }
    }catch (Exception e) {
        throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.processRouteService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
}
```
#### hql
```java
@RequestMapping(params = "processdatagrid")
public void processdatagrid(ProcessRouteProcessRelation processroute, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,String processRouteid){
    String hql="from ProcessRouteProcessRelation where 1=1";
    if(processRouteid.equals("")&&processRouteid==""){
    	hql=hql+" and processRoute.id='0'";
    }else{
    	hql=hql+" and processRoute.id='"+processRouteid+"'";
    }
    if(processroute.getErpProcess() != null){
        if(StringUtils.isNotEmpty(processroute.getErpProcess().getProcessNumber())){
            hql=hql+" and erpProcess.processNumber like '%"+processroute.getErpProcess().getProcessNumber()+"%'";
        }
        if(StringUtils.isNotEmpty(processroute.getErpProcess().getProcessName())){
            hql=hql+" and erpProcess.processName like '%"+processroute.getErpProcess().getProcessName()+"%'";
        }
        if(StringUtils.isNotEmpty(processroute.getErpProcess().getProcessDescribe())){
            hql=hql+" and erpProcess.processDescribe like '%"+processroute.getErpProcess().getProcessDescribe()+"%'";
        }
        if(StringUtils.isNotEmpty(processroute.getErpProcess().getwFocus())){
            hql=hql+" and erpProcess.wFocus='"+processroute.getErpProcess().getwFocus()+"'";
        }
        if(StringUtils.isNotEmpty(processroute.getErpProcess().getCheckProcess())){
            hql=hql+" and erpProcess.checkProcess='"+processroute.getErpProcess().getCheckProcess()+"'";
        }
        if(processroute.getErpProcess().getDepart() != null){
            if(StringUtils.isNotEmpty(processroute.getErpProcess().getDepart().getDepartname())){
                hql=hql+" and erpProcess.depart.departname like '%"+processroute.getErpProcess().getDepart().getDepartname()+"%'";
            }
        }
    }
    hql=hql+" order by processOrder,erpProcess.processNumber";
    List<ProcessRouteProcessRelation> list = systemService.findByQueryString(hql,dataGrid);
    dataGrid.setResults(list);
    Map<String, Map<String, Object>> ExtMap = new HashMap<String, Map<String, Object>>();
    for (ProcessRouteProcessRelation process : list){
        String hqkprp=" from PrpFlowEntity where processroute.id='"+process.getId()+"'";
        List<PrpFlowEntity>PrpFlowEntityList=systemService.findByQueryString(hqkprp);
        String returnmsg="";
        for(int i=0;i<PrpFlowEntityList.size();i++){
            if(PrpFlowEntityList.get(i).getParentId()!="0"){
                ProcessRouteProcessRelation processRouteProcessRelation=  systemService.getEntity(ProcessRouteProcessRelation.class, PrpFlowEntityList.get(i).getParentId());
                if(processRouteProcessRelation!=null){
                    if(returnmsg==""){
                        returnmsg=processRouteProcessRelation.getErpProcess().getProcessName();
                    }else{
                        returnmsg=returnmsg+","+processRouteProcessRelation.getErpProcess().getProcessName();
                    }
                }
            }
        }
        Map m = new HashMap();
        if(process.getErpProcess()!=null){
            m.put("processNumber", process.getErpProcess().getProcessNumber());
            m.put("processName", process.getErpProcess().getProcessName());
            m.put("processDescribe", process.getErpProcess().getProcessDescribe());
            m.put("departname", process.getErpProcess().getDepart().getDepartname());
        }
        m.put("parentid", returnmsg);

        ExtMap.put(process.getId(), m);
    }
    TagUtil.datagrid(response, dataGrid,ExtMap);
}
```

#### HQL查询

##### 1.分页查询数据

```java
int startRow = (dataGrid.getPage() > 0 ? dataGrid.getPage() - 1 : 0) * dataGrid.getRows();
Query query = systemService.getSession().createQuery(hql);
query.setMaxResults(dataGrid.getRows());
query.setFirstResult(startRow);
List<GmTbProcessingEntity> processingEntities = query.list();
```

##### 2.使用占位参数

```java
String hql="from Users where uid=? or uname=?";
Query query = session.createQuery(hql);
//索引从0开始
query.setInteger(0, 3);//query.setParameter(0, 3);
query.setString(1, "张三");//query.setParameter(1, "张三");
List<Users> list = query.list();
for (Users user : list) {
    System.out.println(user);
}
```

##### 3.使用参数名称

```java
String hql = "from Users where uid=:no1 or uid=:no2";
Query query = session.createQuery(hql);
query.setInteger("no1", 1);
query.setInteger("no2", 3);
//....
```

##### 4.可以使用点位参数和名称参数混合使用

```java
String hql = "from User where uid=? or uid=:no2";
Query query = session.createQuery(hql);
query.setInteger(0, 7788);
query.setInteger("no2", 7566);
//....
//使用点位参数和名称参数混合使用，所有点位参数必须放在前面，一旦有名称参数出现，其后将不能再出现占位参数
```

##### 5.连接查询

```java
String hql="SELECT e.ename, e.sal, e.dept.dname FROM Emp e";
//HQL连接查询
String hql="SELECT e.ename, e.sal, d.dname FROM Emp e JOIN e.dept d";
String hql = "SELECT e FROM Emp e JOIN e.dept"; //JOIN将没有意义
String hql = "FROM Emp e JOIN e.dept";
Query query = session.createQuery(hql);
List<Object[]> list = query.list();
//List集合中的数组中会保存两个元素：
//0：主数据(Emp)
//1：从数据(Dept)
//查询编号为7788的员工信息，同时将对应的dept信息和manager信息查询并保存在对应的子属性中
String hql = "FROM Emp e JOIN FETCH e.dept d JOIN FETCH e.manager m WHERE e.empno=7788";
Query query = session.createQuery(hql);
Emp emp = (Emp) query.uniqueResult();
System.out.println(emp);
System.out.println(emp.getManager());
System.out.println(emp.getDept());
```

##### 6.分页

```java
String hql = "from Users";
Query query = session.createQuery(hql);
query.setFirstResult(0);
query.setMaxResults(2);
```

#### QBC（Query By Criteria）

1. Restrictions 条件限制
2. Projections 列设射
3. Order 排序
##### 1.查询实现

```java
Criteria criteria = session.createCriteria(Users.class);
//session.createCriteria("entity.Users");
//session.createCriteria(Users.class, "别名");
List<Dept> list = criteria.list();
//查询单行结果（如果结果有两行或更多，会报错）
Object uniqueResult = criteria.uniqueResult();
```

##### 2. Projections 列投射
```java
//查询uname属性
Criteria criteria = session.createCriteria(Users.class);
PropertyProjection property = Projections.property("name");
criteria.setProjection(property);
List<Object> result = criteria.list();
//查询uname, upwd属性
Criteria criteria = session.createCriteria(Users.class);
//1.创建投射列表
ProjectionList projectionList = Projections.projectionList();
//2.向投射列表中添加列投射
PropertyProjection property1 = Projections.property("uname");
PropertyProjection property2 = Projections.property("upwd");
projectionList.add(property1).add(property2);
//3.将投射列表设置到准则中
criteria.setProjection(projectionList);
List<Object> result = criteria.list();
```
##### 3.Restrictions 条件限制
```java
Criteria criteria = session.createCriteria(Users.class);
Criterion notNull = Restrictions.isNotNull("comm");
criteria.add(notNull); //添加一个条件（如果添加了多个条件，默认条件之间使用and连接）
List<Users> list = criteria.list();
//Restrictions-eq、allEq、gt、ge、lt、le、between、like、in、and、or、isNull、sqlRestriction（限定查询）、not
```
##### 4.排序
```java
Criteria criteria = session.createCriteria(Dept.class);
criteria.addOrder(Order.asc("name")).addOrder(Order.desc("loc"));
//SELECT * FROM DEPT ORDER BY name ASC, loc DESC 默认升序ASC
```
##### 5.分页查询
```java
Criteria criteria = session.createCriteria(Dept.class);
int pageNum = 2, pageSize = 5;
criteria.setFirstResult((pageNum-1)*pageSize); //查询起始行下标
criteria.setMaxResults(pageSize); //查询的最大行数
List list = criteria.list();
//setFirstResult方法和setMaxResults方法同样可以在SQLQuery及Query类型上使用
```
##### 6.创建外键表关联对象
```java
CriteriaQuery cq = new CriteriaQuery(PrpPindicItemsEntity.class, dataGrid);
cq.createAlias("pindicId", "ppc");
cq.add(Restrictions.eq("ppc.useState","1"));
```

#### 原生SQL查询
##### 1.查询
```java
String sql = "select uid,uname,upwd from _users";
List list = session.createSQLQuery(sql).list();
for(Object obj : list){
    System.out.println(obj);
}
```
##### 2.addEntity()
```java
String sql = "select uid,uname,upwd from _users";
// addEntity()可以告诉Hibernate你想要封装成对象的类型，然后自动为你封装
SQLQuery query = session.createSQLQuery(sql).addEntity(Users.class);
List<User> list = query.list();
for(Users user : list){
	System.out.println(user.getUname());
}
````

##### 3.uniqueResult
```java
String sql = "select uid,uname,upwd from _users where uid = 2";
SQLQuery query = session.createSQLQuery(sql).addEntity(Users.class);
Users user = (Users) query.uniqueResult();//返回单一对象
System.out.println(user.getUname());
```

#### 基于栈的指令集与基于寄存器的指令集

Java 编译器输出的指令流，基本上是一种基于栈的指令集架构。基于栈的指令集主要的优点就是可移植，寄存器由硬件直接提供，程序直接依赖这些硬件寄存器则不可避免的要受到硬件约束。栈架构的指令集还有一些其他优点，比如相对更加紧凑（字节码中每个字节就对应一条指令，而多地址指令集中还需要存放参数）、编译实现更加简单（不需要考虑空间分配的问题，所有空间都是在栈上操作）等。

栈架构指令集的主要缺点是执行速度相对来说会稍慢一些。所有主流物理机的指令集都是寄存器架构也从侧面印证了这一点。

虽然栈架构指令集的代码非常紧凑，但是完成相同功能需要的指令集数量一般会比寄存器架构多，因为出栈、入栈操作本身就产生了相当多的指令数量。更重要的是，栈实现在内存中，频繁的栈访问也意味着频繁的内存访问，相对于处理器来说，内存始终是执行速度的瓶颈。由于指令数量和内存访问的原因，所以导致了栈架构指令集的执行速度会相对较慢。

正是基于上述原因，Android 虚拟机中采用了基于寄存器的指令集架构。不过有一点不同的是，前面说的是物理机上的寄存器，而 Android 上指的是虚拟机上的寄存器。