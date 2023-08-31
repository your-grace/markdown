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