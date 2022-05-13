### 字段和标签说明

 - `<a-divider type="vertical" />`：垂直分割线
 - validate：任一表单项被校验后触发
 - resetFields()：对整个表单进行重置，将所有字段值重置为初始值并移除校验结果
 - hasFeedback：配合validateStatus属性使用，展示校验状态图标，建议只配合Input组件使用
 - allowClear：Select props支持清除（下拉框属性）
 - 下拉placeholder：清除后显示placeholder，需要设置`value:undefined`
 - `model: {}`：表单对象
- `queryParam: {}`：查询对象
 - `:scroll="{x:true}"`：水平滚动
 - title：模态框标题
- width：模态框宽度
- visible：控制模态框的显示
- `@ok="handleOk"`：模态框提交绑定方法
- `@cancel="handleCancel"`：模态框关闭绑定方法
- `:okButtonProps="{ class:{'jee-hidden': disableSubmit} }"`：控制模态框提交按钮的显示
- `cancelText="关闭"`：模态框关闭文字描述
- `okText='保存'`：模态框提交文字描述
- `<a-spin :spinning="confirmLoading">`：控制Spin加载中的显示
- `:maxLength='10'`：input最大长度（number）

###  弹窗选择组件
- 列表url
- 列表column
- 查询queryparam
  - field：查询参数
  - label：查询文本
  - type：查询类型，默认1(input)，2(字典下拉)、3(下拉搜索)
- multi：默认false（radio），可选true（checkbox）


### 子组件修改父组件的值

> 1、通过`this.$emit('fatherMethod',param)`调用父级组件的方法来修改
>
> ```js
> //父组件
> <template>
> 	<parent @change-type="changeType"></parent>
> </template>
> <script>
> 	export default{
> 	  data () {
>        	return {
>         	types: 0,
>       	},
>       },
>       methods: {
>        changeType (type) { // type是子组件$emit传递的参数
>         this.types = type
>        }
>       }
> 	}  
>  </script>
> //子组件
> <template>
>   <div>
> 	<span @click="test(0)"></span>
> </div>
> </template>
> <script>
>      export(){
>          data () {
>              return {
>                  types: 0,
>              },
>          }
>          methods: {
>              test (type) {
>                  this.types = type
>                  this.$emit('change-type', type) // 用来触发父组件定义的@change-type
>              }
>          }
>      }
>  </script>
> //$emit调用父组件方法，接收两个参数[父组件方法名，传递的参数]
> //父组件中$on绑定的方法名应该同步于$emit调用的
> //缺点是父组件必须存在此方法，否则会报错
> ```
>
> 
>
> 2、父组件在子组件上添加`:name.sync:'value'`，子组件通过`this.$emit('update:name',newValue)`来修改父组件的值
>
> ```js
> //父组件
> <test :test.sync= 'visible'></test>
> <script>
> export default {
> 	data() {
>         return {
>             visible:true
>         }
>     }
> }    
> </script>
> //子组件
> <a-button @click="$emit('update:test',false)">确定</a-button>
> <script>
> export default {
> 	props:{
>         test:{
>             type:Boolean,
>             required:false,
>             default:false
>         }
>     }
> } 
> </script>
> ```
>
> 
>
> 3、在子组件上使用`v-model='value'`双向数据绑定，在子组件中`{{value}}`,通过调用method或其它来修改 value的值即可
>
> ```js
> //父组件
> <template>
>     <parent v-model="changeType"></parent>
> </template>
> //子组件
>  <template>
>     <div>
>         <span @click="test(0)"></span>
>     </div>
> </template>
> <script>
>     export default{
>        data () {
>        return { 
>        }
>       },
>       methods: {
>         test (type) {
>           this.$emit('input', type) // 用来触发父组件定义的@input
>        }
>       } 
>     }
> </script>
> //v-model原则上也是利用$emit以及$on
> //v-model会默认绑定input事件
> ```

### table

#### *table单元格自定义样式*

```js
customCell:function(record,rowIndex){
    if(record.realname == 'admin'){
        return {
            style:{
                'background-color':'yellow'
            }
        }
    }
}
```

#### *单元格自定义数据*

```js
customRender: (text,record,index) => {
    return filterDictTextByCache('u_type',record.utype)
}
```

#### *scopedSlots*

使用columns时，可以通过该属性配置支持`slot-scope`的属性，如
  ```js
  //columns
  columns:[
      {
          title: '头像',
          align: "center",
          width: 120,
          dataIndex: 'avatar',
          scopedSlots: {customRender: "avatarslot"}
      }
  ]
  ```

```vue
<!-- 作用于插槽 -->
<template slot="avatarslot" slot-scope="text, record">
 <div class="anty-img-wrap">
     <a-avatar shape="square" :src="getAvatarView(record.avatar)" icon="user"/>
  </div>
</template>
```

想控制插槽的属性来实现效果，则属性要在接口数据中存在，通过record来接收；否认临时赋予的属性不会生效。

```vue
<span slot="operation"  slot-scope="text, record">
    <a @click="handleWarehousing(record)" v-has="'ewm:warehousing'" :disabled="record.status == '1' ? true:false">入库</a>
</span>
```



### *列表自定义实现*

> 功能说明：
>
> ```tex
> 页面自定义设置列表需要选择的列，设置组件集成的两种方法，一个是在列表外增加设置组件，一个是在列表表头增加设置组件
> 具体代码案例参照【常用示例-单表模型示例】功能
> ```

#### *功能预览*：

![输入图片说明](https://static.oschina.net/uploads/img/201906/14115727_mdM3.png)

![输入图片说明](https://static.oschina.net/uploads/img/201906/14115739_9e8f.png)

![输入图片说明](https://static.oschina.net/uploads/img/201906/14115753_6Sbd.png)

#### *实现方法*

> 实现方法：
>
> 1.data()方法中配置
>
> ```js
> //表头
> columns: [],
> //列设置
> settingColumns: [],
> //列定义
> defColumns: [
>       {
>             title: '#',
>             dataIndex: '',
>             key: 'rowIndex',
>             width: 60,
>             align: "center",
>             customRender: function (t, r, index) {
>               return parseInt(index) + 1;
>             }
>       },
>       {
>             title: '姓名',
>             align: "center",
>             dataIndex: 'name'
>       },
> ]
> ```
>
> 说明：
> columns：列表展示的列，初始为空。
> settingColumns：保存勾选的列设置
> defColumns：定义列表可以展示的列信息
>
> 2.增加设置按钮，两种实现方法任选其一即可
>
> （1）第一种在列表外增加设置按钮
>
> ```vue
> <a-popover
>   title="自定义列"
>   trigger="click"
>   placement="leftBottom">
> 	<template slot="content">
>            <a-checkbox-group
>               @change="onColSettingsChange"
>               v-model="settingColumns"
>               :defaultValue="settingColumns"
>             >
>               <a-row>
>                 <template v-for="(item,index) in defColumns">
>                   <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
>                     <a-col :span="12">
>                       <a-checkbox :value="item.dataIndex">{{ item.title }}</a-checkbox>
>                     </a-col>
>                   </template>
>                 </template>
>               </a-row>
>             </a-checkbox-group>
>    	</template>
>       <a>
>           <a-icon type="setting" />自定义列
>        </a>
>   </a-popover>
> ```
> 
>（2）第二种在表头列中扩展按钮
> 
>在操作列定义中增加插槽设置
> 
>```js
> {
>      	title: '操作',
>        dataIndex: 'action',
>        align: "center",
>        scopedSlots: {
>            filterDropdown: 'filterDropdown',
>            filterIcon: 'filterIcon',
>            customRender: 'action'
>        },
>    }
> ```
> 
>增加插槽代码
> 
>```vue
> <div slot="filterDropdown">
>        <a-card>
>          <a-checkbox-group
>            @change="onColSettingsChange"
>            v-model="settingColumns"
>            :defaultValue="settingColumns"
>          >
>            <a-row>
>              <template v-for="(item,index) in defColumns">
>                <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
>                  <a-col :span="12">
>                    <a-checkbox :value="item.dataIndex">{{ item.title }}</a-checkbox>
>                  </a-col>
>                </template>
>              </template>
>            </a-row>
>          </a-checkbox-group>
>        </a-card>
>    </div>
> <a-icon
>     slot="filterIcon"
>     type='setting'
>     :style="{ fontSize:'16px',color:  '#108ee9' }"
> />
> ```
> 
>3.实现checkbox @change
> 
>```js
> //列设置更改事件
> onColSettingsChange (checkedValues) {
>       var key = this.$route.name+":colsettings";
>       Vue.ls.set(key, checkedValues, 7 * 24 * 60 * 60 * 1000)
>       this.settingColumns = checkedValues;
>       const cols = this.defColumns.filter(item => {
>          if(item.key =='rowIndex'|| item.dataIndex=='action'){
>               return true
>           }
>           if (this.settingColumns.includes(item.dataIndex)) {
>               return true
>           }
>           return false
>        })
>       this.columns =  cols;
>   },
> ```
> 
>4.页面加载时实现列的初始化方法
> 
>```js
> initColumns(){
>     //权限过滤（列权限控制时打开，修改第二个参数为授权码前缀）
>       //this.defColumns = colAuthFilter(this.defColumns,'testdemo:');
>      var key = this.$route.name+":colsettings";
>       let colSettings= Vue.ls.get(key);
>       if(colSettings==null||colSettings==undefined){
>          let allSettingColumns = [];
>           this.defColumns.forEach(function (item,i,array ) {
>               allSettingColumns.push(item.dataIndex);
>           })
>           this.settingColumns = allSettingColumns;
>           this.columns = this.defColumns;
>        }else{
>          this.settingColumns = colSettings;
>           const cols = this.defColumns.filter(item => {
>               if(item.key =='rowIndex'|| item.dataIndex=='action'){
>                   return true;
>               }
>               if (colSettings.includes(item.dataIndex)) {
>                   return true;
>               }
>               return false;
>           })
>           this.columns =  cols;
>        }
>    }
>   ```
> 
> created中调用
>
> ```js
>created() {
> 	this.initColumns();
> }
> ```