```tex
闭包（closure）：有权访问另一个函数作用域中变量的函数。
shift:移除数组第一项|pop:移除数组最后一项|unshift:向数组开头添加元素|push:向数组结尾添加元素
[].slice.call(arguments)循环遍历arguments并复制到类数组的新数组
Object.assign(target:{},source1,source2)将可枚举属性的值的一个或多个源对象复制到目标对象
Object.hasOwnProperty(property)判断对象中是否有对象的属性
函数柯里化|函数反柯里化|函数防抖|函数节流
curry debounce防抖-电梯每次上人（重置等待时间） throttle节流-技能冷却-失效一段时间
防抖适用于多次触发只需执行一次的情况，而节流适用于限制连续触发事件的执行频率。
this:从自己的作用域链的上一层继承this
```

```javascript
//apply和call
Math.max.apply(null,arr)
Math.max.call(null,arg1,arg2...)
Array.property.push.apply(arr1,arr2)
array.splice(index,howmany,item1,...itemX)//index索引位置 howmany删除数量 item添加元素

//箭头函数嵌套，也称为函数柯里化
function add(a,b){//反柯里化
    return a+b;
}
function add(a){//柯里化
    return function(b){
        return a+b;    
    }
}
console.log(add(4)(5));//9
//箭头函数柯里化
const add = (a) => (b) => a + b;
console.log(add(4)(5));//9
```

```javascript
//对象解构
const note = {id:1,title:'My first note',date:'01/01/1970'}
const {id,title,date} = note;
//等价于以下代码
const id = note.id;const title = note.title;const date = note.date;
//数组解构
const date = ['1970','12','01']
const [year,month,day] = date
```

```javascript
//数组对象展开（spread）
const users = [{id:1,name:'Ben'},{id:2,name:'Leslie'}]
const newUser = [{id:3,name:'Ron'}]
const updateUsers = [...users,newUser]
//浅拷贝
const originalArray = ['one','two','three']
const secondArray = [...originalArray];
secondArray.pop();//remove the last item of the second Array
console.log(originalArray);//["one", "two", "three"]
//转换可迭代对象
const set = new set()
set.add('octopus')
set.add('starfish')
const seaCreatures = [...set]
console.log(seaCreatures) //['octopus','starfish']
//对象展开
const user = {id:3,name:'Ron',organization:{name:'Parks & Recreation',city:'Pawnee'}}
const updateUser = {...user,oganization:{position:'Director'}}
console.log(updateUser)
//organization被覆盖id: 3 name: "Ron" organization: {position: "Director"}
//正确的做法
const updateUser = {
    ...user,
    organization:{
        ...user.organization,
        position:'Director',            
    }
}
//id: 3	name: "Ron"	organization: {name: "Parks & Recreation", city: "Pawnee", position: "Director"}
consle.log(updateUser)
```

#### 模拟数据promise
```javascript
//模拟调用接口
function getData() {
    return new Promise<any[]>((resolve, reject) => {
        setTimeout(() => {
            // 模拟接口调用有概率出错
            if (Math.random() > 0.5) {
                resolve([
                    {
                        key: 1,
                        name: 'apple',
                        value: 1,
                    },
                    {
                        key: 2,
                        name: 'banana',
                        value: 2,
                    },
                    {
                        key: 3,
                        name: 'orange',
                        value: 3,
                    },
                ]);
            } else {
                reject(new Error('不小心出错了！'));
            }
        }, 3000);
    });
}
```

#### ES6新特性

??（空值合并操作符）

**当左侧值为 \*null\* 或 \*undefined\* 时，返回 ?? 符号右边的值**

```javascript
'hello world' ?? 'hi' 
// 'hello world'

'' ?? 'hi' 
// ''

false ?? 'hi' 
// false

null ?? 'hi'  
// 'hi'

undefined ?? 'hi'
// 'hi'
```

?.（可选链操作符）

**允许读取位于连接对象链深处的属性的值，而不必明确验证链中的每个引用是否有效。**

```javascript
let network = {
	chain: 1,
	name: 'ethereum'
}
let res = network?.name
// 返回 ethereum

let customer = {
  name: "Carl",
  details: {
    age: 82,
    location: "Paradise Falls" // details 的 address 属性未有定义
  }
};
let customerCity = customer.details?.address?.city;
// 由于 details 中 address 属性未有定义, 因此返回 undefined
```
#### 判断设备是PC端 or 移动端

```javascript
function isMobile() {
    let userAgentInfo = navigator.userAgent;
    let Agents = ['Android', 'iPhone', 'SymbianOS', 'Windows Phone', 'iPad', 'iPod'];
    let getArr = Agents.filter(i => userAgentInfo.includes(i));
    return getArr.length ? true : false;
}
```

```javascript
var ua = navigator.userAgent;
var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
    isAndroid = ua.match(/(Android)\s+([\d.]+)/),
    isMobile = isIphone || isAndroid;
    if(isMobile) {
        location.href = 'http://m.baidu.com';
    }else{
        location.href = 'http://www.baidu.com';
    ｝
    return;
    //或者单独判断iphone或android
    if(isIphone){
        console.log("iphone访问");
    }else if(isAndroid){
        console.log("Android访问");
    }else{
        console.log("非iphone或Android访问");
    }
}
```

#### 浏览器静态资源更新

**时间戳**

```html
<link rel="stylesheet" href="~/XXX.css?time='+new Date().getTime()+'"> <script src="~/XXX.js?time='+new Date().getTime()+'"></script>
```

**webpack hash**

```js
import { defineConfig } from 'vite';
 export default defineConfig({
  build: {
    rollupOptions: {
      output: {
        entryFileNames: '[name].[hash].js',
        chunkFileNames: '[name].[chunkhash].js',
        assetFileNames: '[name].[contenthash].[ext]',
      },
    },
  },
});
```

**vite热模块替换（HMR）**

当静态文件发生变化时，vite会根据模块的依赖关系进行增量更新。它通过使用ES模块的特性，利用浏览器的原生模块加载器来实现热模块替换。具体原理如下：  
1. 当文件发生变化时，vite会通知浏览器重新加载发生变化的模块。 
2. 浏览器会使用HTTP请求重新获取变化的模块文件。 
3. 变化的模块文件会被浏览器加载和执行。 
4. 通过HMR接口，更新的模块会被注入到应用程序中，替换旧的模块。 

### apply 源码解析 es3 es6

```javascript
function add(c, d) {
  return this.a + this.b + c + d;
}
const obj = { a: 1, b: 2 };
// ES3 apply 实现
Function.prototype.es3apply = function (context, arr) {
  var context = context || window;
  context.fn = this;
  var result;
  if (!arr) {
    result = context.fn();
  } else {
    // 获取参数
    var args = [];
    for (var i = 0, len = arr.length; i < len; i++) {
      args.push('arr[' + i + ']');
    }
    // 执行函数
    result = eval('context.fn(' + args + ')')
  }
  delete context.fn;
  return result
}
console.log(add.apply(obj, [1, 2])); // 6
// ES6 apply 实现
Function.prototype.es6apply = function (context, arr) {
    var context = context || window;
    context.fn = this;
    var result;
    if (!arr) {
        result = context.fn();
    } else {
        if (!(arr instanceof Array)) throw new Error('params must be array');
        result = context.fn(...arr);
    }
    delete context.fn;
    return result;
}
console.error(add.es6apply(obj, [1, 2])); // 6
```

#### 元编程

```javascript
//代理 Proxy
let handler = {
  get: function (target, name) {
    return name in target ? target[name] : 42;
  },
};
let p = new Proxy({}, handler);
p.a = 1;
console.log(p.a, p.b); // 1, 42
//apply函数
Function.prototype.apply.call(Math.floor, undefined, [1.75]);
//反射Reflect
Reflect.apply(Math.floor, undefined, [1.75]);
// 1;
Reflect.apply(String.fromCharCode, undefined, [104, 101, 108, 108, 111]);
// "hello"
Reflect.apply(RegExp.prototype.exec, /ab/, ["confabulation"]).index;
// 4
Reflect.apply("".charAt, "ponies", [3]);
// "i"
//检查属性是否定义成功
if (Reflect.defineProperty(target, property, attributes)) {
  // success
} else {
  // failure
}
let obj = {}
Reflect.defineProperty(obj, 'x', {value: 7})  // true
obj.x                                         // 7
```

#### 防抖(debounce)

```javascript
/*****************************简化后的分割线 ******************************/
function debounce(fn,delay){
    let timer = null //借助闭包
    return function() {
        if(timer){
            clearTimeout(timer) 
        }
        timer = setTimeout(fn,delay) // 简化写法
    }
}
// 然后是旧代码
function showTop  () {
    var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
　　console.log('滚动条位置：' + scrollTop);
}
window.onscroll = debounce(showTop,1000) // 为了方便观察效果我们取个大点的间断值，实际使用根据需要来配置
```

#### 节流(throttle)

```javascript
function throttle(fn,delay){
    let valid = true
    return function() {
       if(!valid){
           //休息时间 暂不接客
           return false 
       }
       // 工作时间，执行函数并且在间隔期内把状态位设为无效
        valid = false
        setTimeout(() => {
            fn()
            valid = true;
        }, delay)
    }
}
/* 请注意，节流函数并不止上面这种实现方案,
   例如可以完全不借助setTimeout，可以把状态位换成时间戳，然后利用时间戳差值是否大于指定间隔时间来做判定。
   也可以直接将setTimeout的返回的标记当做判断条件-判断当前定时器是否存在，如果存在表示还在冷却，并且在执行fn之后消除定时器表示激活，原理都一样
    */

// 以下照旧
function showTop  () {
    var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
　　console.log('滚动条位置：' + scrollTop);
}
window.onscroll = throttle(showTop,1000) 
```

#### [迭代异步生成器](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Statements/for-await...of#迭代异步生成器)

```javascript
//for await... of	获得API 的响应值的大小
async function* streamAsyncIterator(stream) {
  const reader = stream.getReader();
  try {
    while (true) {
      const { done, value } = await reader.read();
      if (done) {
        return;
      }
      yield value;
    }
  } finally {
    reader.releaseLock();
  }
}
// 从 url 获取数据并使用异步 generator 来计算响应值的大小
async function getResponseSize(url) {
  const response = await fetch(url);
  // Will hold the size of the response, in bytes.
  let responseSize = 0;
  // 使用 for-await-of 循环。异步 generator 会遍历响应值的每一部分
  for await (const chunk of streamAsyncIterator(response.body)) {
    // Incrementing the total response length.
    responseSize += chunk.length;
  }

  console.log(`Response Size: ${responseSize} bytes`);
  // expected output: "Response Size: 1071472"
  return responseSize;
}
getResponseSize("https://jsonplaceholder.typicode.com/photos");
```

```javascript
//for ... in	hasOwnProperty-继承的属性不显示:prototype
var triangle = { a: 1, b: 2, c: 3 };
function ColoredTriangle() {
  this.color = "red";
}
ColoredTriangle.prototype = triangle;
var obj = new ColoredTriangle();
for (var prop in obj) {
  if (obj.hasOwnProperty(prop)) {
    console.log(`obj.${prop} = ${obj[prop]}`);
  }
}
// Output:
// "obj.color = red"
```

```javascript
//动态导入
const main = document.querySelector("main");
for (const link of document.querySelectorAll("nav > a")) {
  link.addEventListener("click", (e) => {
    e.preventDefault();
    import("/modules/my-module.js")
      .then((module) => {
        module.loadPageInto(main);
      })
      .catch((err) => {
        main.textContent = err.message;
      });
  });
}
//支持await关键字
(async () => {
  if (somethingIsTrue) {
    const {
      default: myDefault,
      foo,
      bar,
    } = await import("/modules/my-module.js");
  }
})();
```

#### 扩展构造函数

```javascript
function extend(sup, base) {
  var descriptor = Object.getOwnPropertyDescriptor(
    base.prototype,
    "constructor",
  );
  base.prototype = Object.create(sup.prototype);
  var handler = {
    construct: function (target, args) {
      var obj = Object.create(base.prototype);
      this.apply(target, obj, args);
      return obj;
    },
    apply: function (target, that, args) {
      sup.apply(that, args);
      base.apply(that, args);
    },
  };
  var proxy = new Proxy(base, handler);
  descriptor.value = proxy;
  Object.defineProperty(base.prototype, "constructor", descriptor);
  return proxy;
}
var Person = function (name) {
  this.name = name;
};
var Boy = extend(Person, function (name, age) {
  this.age = age;
});
Boy.prototype.sex = "M";
var Peter = new Boy("Peter", 13);
console.log(Peter.sex); // "M"
console.log(Peter.name); // "Peter"
console.log(Peter.age); // 13
```

#### IIFE模块模式

```javascript
const makeWithdraw = (balance) => {
  return ((copyBalance) => {
    let balance = copyBalance; // 这个变量是私有的
    const doBadThings = () => {
      console.log("I will do bad things with your money");
    };
    doBadThings();
    return ({
      withdraw(amount) {
        if (balance >= amount) {
          balance -= amount;
          return balance;
        }
        return "Insufficient money"
      },
    });
  })(balance);
}
const firstAccount = makeWithdraw(100);// "I will do bad things with your money"
console.log(firstAccount.balance); // undefined
console.log(firstAccount.withdraw(20)); // 80
console.log(firstAccount.withdraw(30)); // 50
console.log(firstAccount.doBadThings); // undefined; this method is private
const secondAccount = makeWithdraw(20); // "I will do bad things with your money"
console.log(secondAccount.withdraw(30)); // "Insufficient money"
console.log(secondAccount.withdraw(20)); // 0
```
#### 复制
##### 浅拷贝
```javascript
//1.使用 Array.from() 或扩展运算符（spread operator）
const originalArray = [1, 2, 3];
const copiedArray1 = Array.from(originalArray);
const copiedArray2 = [...originalArray];
console.log(copiedArray1); // [1, 2, 3]
console.log(copiedArray2); // [1, 2, 3]
//2.使用 slice() 方法
const originalArray = [1, 2, 3];
const copiedArray = originalArray.slice();
console.log(copiedArray); // [1, 2, 3]
```
##### 深拷贝
```javascript
//1.递归实现深度复制
function deepCopy(obj) {
  if (typeof obj !== 'object' || obj === null) {
    return obj;
  }
  let copy;
  if (Array.isArray(obj)) {
    copy = [];
    for (let i = 0; i < obj.length; i++) {
      copy[i] = deepCopy(obj[i]);
    }
  } else {
    copy = {};
    for (let key in obj) {
      if (obj.hasOwnProperty(key)) {
        copy[key] = deepCopy(obj[key]);
      }
    }
  }
  return copy;
}
// 示例用法
const originalObj = { 
  name: 'John', 
  age: 30, 
  hobbies: ['reading', 'music']
};
const copiedObj = deepCopy(originalObj);
copiedObj.name = 'Jane';
copiedObj.hobbies.push('sports');
console.log(originalObj); 
// { name: 'John', age: 30, hobbies: [ 'reading', 'music' ] }
console.log(copiedObj);   
// { name: 'Jane', age: 30, hobbies: [ 'reading', 'music', 'sports' ] }
//2.使用第三方库（如lodash）的cloneDeep()方法
const _ = require('lodash');
const originalObj = { 
  name: 'John', 
  age: 30, 
  hobbies: ['reading', 'music']
};
const copiedObj = _.cloneDeep(originalObj);
copiedObj.name = 'Jane';
copiedObj.hobbies.push('sports');
console.log(originalObj); 
// { name: 'John', age: 30, hobbies: [ 'reading', 'music' ] }
console.log(copiedObj);   
// { name: 'Jane', age: 30, hobbies: [ 'reading', 'music', 'sports' ] }
```
#### 通用的柯里化函数
```javascript
var currying = function (fn) {
    var args = [];
    return function () {
        if (arguments.length === 0) {
            return fn.apply(this, args);
        } else {
            [].push.apply(args, arguments);//callee属性指代当前正在执行的函数
            return arguments.callee;
        }
    }
};
var count= (function () {
    var sum= 0;
    return function () {
        for (var i = 0; i < arguments.length; i++) {
            sum+= arguments[i];
        }
        return sum;
    }
})();
var count= currying(count); // 转化成 currying 函数
count(100);count(200); count(300);  
console.log(count());  // 求值并输出：600
```
#### 可传参的柯里化函数
```javascript
var currying = function (fn) {
    var args = [];
    /*将除了第一个参数fn之外的参数，存储到args中arguments本身是没有slice方法的，因此想取到arguments中除了第一项以外的参数
      就要通过[].slice.call(arguments,1) 相当于把数组slice的方法，搬到arguments上*/
    args = args.concat([].slice.call(arguments,1)); 
    return function () {
        if (arguments.length === 0) {
            return fn.apply(this, args);
        } else {
            [].push.apply(args, arguments);//callee属性指代当前正在执行的函数
            return arguments.callee;
        }
    }
};
```
#### 通用的反柯里化函数
```javascript
var uncurrying= function (fn) {
    return function () {
        var args=[].slice.call(arguments,1);
        return fn.apply(arguments[0],args);        
    }    
};
```
#### Object.create()
Object.create() 静态方法以一个现有对象作为原型，创建一个新对象。
```javascript
//Object.create(proto)	 Object.create(proto,propertiesObject)
const person = {
  isHuman: false,
  printIntroduction: function () {
    console.log(`My name is ${this.name}. Am I human? ${this.isHuman}`);
  },
};
const me = Object.create(person);
me.name = 'Matthew'; // "name" is a property set on "me", but not on "person"
me.isHuman = true; // Inherited properties can be overwritten
me.printIntroduction();
// Expected output: "My name is Matthew. Am I human? true".
```
![img](https://github.com/your-grace/markdown/blob/main/image/es/1.png?raw=true)