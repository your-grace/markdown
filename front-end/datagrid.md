#### EasyUI DataGrid 通过index获取行的内容
##### 第一种方法

```javascript
var rowsData = $("#dg").datagrid('getSelected'); //获得被选择的行console.info(rowsData);
```

##### 第二种方法

```javascript
var rows = $("#dg").datagrid('getRows');//获得所有行var row = rows[index];//根据index获得其中一行。console.info(row);
```

#### 获取所有选中行

```javascript
var rowsData = $('#taskPlanListTree').datagrid('getSelections');
```