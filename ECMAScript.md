```
闭包（closure）：有权访问另一个函数作用域中变量的函数。
shift:移除数组第一项|pop:移除数组最后一项|unshift:向数组开头添加元素|push:向数组结尾添加元素
[].slice.call(arguments)循环遍历arguments并复制到类数组的新数组
Object.assign(target:{},source1,source2)将可枚举属性的值的一个或多个源对象复制到目标对象
Object.hasOwnProperty(property)判断对象中是否有对象的属性
函数柯里化|函数反柯里化|函数防抖|函数节流
curry debounce防抖-电梯每次上人（重置等待时间） throttle节流-技能冷却-失效一段时间
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
