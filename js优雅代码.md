#### URL编码解码

```javascript
//编码
encodeURIComponent("url")
//解码
decodeURIComponent("url")
```

#### 数组去重

```javascript
const uniqueArr = (arr) => [...new Set(arr)];

console.log(uniqueArr(["前端","js","html","js","css","html"]));
// ['前端', 'js', 'html', 'css']
```

#### 从url获取参数并转为对象

```javascript
const getParameters = URL => JSON.parse(`{"${decodeURI(URL.split("?")[1]).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"')}"}`
  )

getParameters("https://www.google.com.hk/search?q=js+md&newwindow=1");
// {q: 'js+md', newwindow: '1'}
```

#### 检查对象是否为空

```javascript
const isEmpty = obj => Reflect.ownKeys(obj).length === 0 && obj.constructor === Object;
isEmpty({}) // true
isEmpty({a:"not empty"}) //false
```

#### 反转字符串

```javascript
const reverse = str => str.split('').reverse().join('');
reverse('this is reverse');
// esrever si siht
```

#### 生成随机十六进制

```javascript
const randomHexColor = () => `#${Math.floor(Math.random() * 0xffffff).toString(16).padEnd(6, "0")}`
console.log(randomHexColor());
// #a2ce5b
```

#### 检查当前选项卡是否在后台

```javascript
const isTabActive = () => !document.hidden; 

isTabActive()
// true|false
```

#### 检测元素是否处于焦点

```javascript
const elementIsInFocus = (el) => (el === document.activeElement);

elementIsInFocus(anyElement)
// 元素处于焦点返回true，反之返回false
```

#### 检查设备类型

```javascript
const judgeDeviceType =
      () => /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|OperaMini/i.test(navigator.userAgent) ? 'Mobile' : 'PC';

judgeDeviceType()  // PC | Mobile
```

#### 文字复制到剪贴板

```javascript
const copyText = async (text) => await navigator.clipboard.writeText(text)
copyText('单行代码 前端世界')
```

#### 获取选定的文本

```javascript
const getSelectedText = () => window.getSelection().toString();

getSelectedText();
// 返回选中的内容
```

#### 查询某天是否为工作日

```javascript
const isWeekday = (date) => date.getDay() % 6 !== 0;

isWeekday(new Date(2022, 03, 11))
// true
```

#### 转换华氏/摄氏

 ```javascript
 //将华氏温度转换为摄氏温度
 const fahrenheitToCelsius = (fahrenheit) => (fahrenheit - 32) * 5/9;
 fahrenheitToCelsius(50);
 // 10
 
 //将摄氏温度转华氏温度
 const celsiusToFahrenheit = (celsius) => celsius * 9/5 + 32;
 celsiusToFahrenheit(100)
 // 212
 ```

#### 两日期之间相差的天数

```javascript
const dayDiff = (date1, date2) => Math.ceil(Math.abs(date1.getTime() - date2.getTime()) / 86400000);

dayDiff(new Date("2021-10-21"), new Date("2022-02-12"))
// Result: 114
```

#### 将 RGB 转换为十六进制

```javascript
const rgbToHex = (r, g, b) =>   "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);

rgbToHex(255, 255, 255); 
//  #ffffff
```

#### 计算数组平均值

```javascript
const average = (arr) => arr.reduce((a, b) => a + b) / arr.length;
average([1,9,18,36]) //16
```
#### 出处
> [掘金单行代码](https://juejin.cn/post/7145623660680708104#heading-0)

