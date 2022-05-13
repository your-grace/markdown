### 单选下拉和单选框
> *JDictSelectTag 组件用法  
> type可选select和radio，默认为select*
```js
//下拉
<j-dict-select-tag v-model='queryParam.sex' placeholder='请输入性别'  dictCode='sex'/>
```
```js
//radio
<j-dict-select-tag v-model='queryParam.sex' :type='radio'  dictCode='sex'/>
```
> `JDictSelectTag.vue`注释掉~~`<a-select-option :value="undefined">请选择</a-select-option>`~~代码，无请选择项

### 多选下拉和复选框

> *JMultiSelectTag 多选组件  
type可选select和checkbox，默认为select*
```js
//下拉
<j-multi-select-tag v-model='selectValue' :options='dicOptions' placeholder='请做出你的选择'>{{selectValue}}</j-multi-select-tag>
```
```js
//checkbox
<j-multi-select-tag v-model='checkboxValue' :options='dicOptions' type="checkbox">{{checkboxValue}}</j-multi-select-tag>
```
```js
selectValue:'',
checkboxValue:'',
dictOptions:[{lable:'选项一',value='1'},{lable:'选项二',value='2'}]
```
> *组件位置：src/components/dict目录下  
字典优先在缓存中取，参考`src/components/dict/JDictSelectTag.vue`*
### 组件和混入

> - JeecgListMixin：列表共通功能提取（常用）
> - JModal：全屏模态框组件
> - JFormContainer：表单禁用专用组件
> - JInput：特殊查询组件（模糊查询)系统原有模块引入生效，新增模块引入不生效，应该是后端处理的问题
> - JPopup：弹窗选择组件，搭配online表单配置中的列表使用
> - JSuperQuery：高级查询组件，后端需要使用通用QueryGenerator生效，分页使用自定义的不生效

### 查看实时日志
```bash
tail -f /var/tmp/springboot/vhost/logs/jeecg-boot-module-system-3.log
tail -f /home/GYIMOM/logs/jeecgboot-2022-03-04.0.log    #当前日期
```

### 用户代码片段

> - vlist：用户管理  
> - vmodal：用户管理模态框
> - vlist2：租户管理  
> - vmodal2：租户管理模态框
> - vform：租户表单
> - vlist3：计量单位
> - vmodal3：计量单位模态框

### 字典用法

> 字典值转换字典文本

```javascript
//引入js方法
import {filterDictTextByCache} from '@/components/dict/JDictSelectUtil' 
//字段翻译
{
  title: '性别',
  align: "center",
  dataIndex: 'sex',
  customRender: (text,record,index) => {//参数要写全
    //字典值翻译通用方法
    return filterDictTextByCache('sex', text);
  }
}
```

### 表单字段校验和重复校验

> - 后端逻辑：根据查询结果的数量来判断是否可用
>
> - 校验分为两种
>
>   - 添加：数据表名+字段名+校验值
>
>   - 编辑：数据表名+字典名+校验值+id
>
> - 查询结果为count，num接收，`num==0`可用，否则不可用

```javascript
//标签使用
<a-form-model ref="form" :model="model" slot="detail" :rules="validatorRules">
//引入重复校验
import { duplicateCheck } from '@/api/api'
//校验规则
validatorRules:{
    name: [//表单字段名称
        { required: true, message: '请输入单位名称!' },
        { validator: this.validateName }
    ],
    unit: [
        { required: true, message: '请输入单位!' }
    ],
    phone: [
        { required: true, pattern: /^1[3|4|5|6|7|8|9]\d{9}$/, message: '请输入正确格式的联系电话!' }
    ],
}
//重复校验参数
{tableName:表名,fieldName:字段名,fieldVal:字段值,dataId:表的主键}
//具体使用
validateName(rule,value,callback) {
    // 重复校验
    var params = {
        tableName: 'gm_unit_measurement',
        fieldName: 'name',
        fieldVal: value,
        dataId: this.model.id
    }
    console.log(params)
    duplicateCheck(params).then((res) => {
        if (res.success) {
            callback()
        } else {
            callback(res.message)
        }
    })
}
//触发表单验证
this.$refs.form.validate(valid => {
    if(valid){
        //请求逻辑
    }
    else{
        return false
    }
})
```

### 字段和标签说明

> - `<j-form-container :disabled="formDisabled">`：表单禁用专用组件，不支持表单中iframe的内容禁用
>
> - disableSubmit：表单组件禁用标识字段
>
> - 头部左侧文字全称：收缩后变简称（在移动端简称应尽量简短，否则头部右侧会换行显示）
>
> - 表格最好设置列的宽度，这样可以防止列表在操作时的拉伸
>
> - ```js
>  //表格强制不换行
>   import '@/assets/less/TableExpand.less'
>   class="j-table-force-nowrap"
>   ```
>
> - [列表自定义列实现](http://doc.jeecg.com/2043979)
>
> - [自定义组件](http://doc.jeecg.com/2043986)
>
> - *角色管理右侧列表根据` this.rightcolval == 1`来显示与否*
>
> - *tabs—contextmenu右键操作（可配置）*
>
> - *项目一级目录下定义全局环境变量，使用`process.env.xxx`调用*
>
> - 菜单配置
>
>    - 非叶子菜单（即没有下级的菜单）配置固定，前端组件`layouts/RouteView`
> 	- 通的叶子菜单（即具体的页面） 配置相对于src/views目录的路径
>    - 需要跳转到第三页面的菜单，前端组件固定为`layouts/IframePageView`，比如跳转百度，菜单路径设置为`https://www.baidu.com`
>    - java后台请求的菜单需要以`{{window._CONFIG['domianURL']}}`开头，菜单路径设置`{{window._CONFIG['domianURL']}}/druid/`
>    - 配置外网打开的链接请求地址需要以`http`或者`{{window._CONFIG['domianURL']}}`开头，前端固定组件为`layouts/IframePageView`，打开方式为外部
>    
> - pointer-events：属性定义元素是否对指针事件做出反应。
>
> - `pointer-events: auto|none;`

### 按钮权限用法

> 1.前端页面通过使用指令 v-has
>
> `<a-button @click="handleAdd" v-has="'user:add'" type="primary" icon="plus">添加用户</a-button>`
>
> 2.后台进入菜单管理页面配置按钮权限菜单
>
> ![img](https://img.kancloud.cn/a7/ee/a7ee8685e36317a2399c1b8f3a380bbb_837x653.png)
>
> 3.进入角色管理授权按钮（授权后即可看见按钮）
>
> ![输入图片说明](https://static.oschina.net/uploads/img/201904/12141144_Ft4J.png)
>
> 备注： 授权标识支持多个，多个逗号分隔
