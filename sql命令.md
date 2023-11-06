#### 多表查询

#### 内连接inner join

> 内连接：inner join on关键字  找出两表的【交集】
>
> 基本语法： SELECT * FROM [左表] innder join [右表] on [左表].[字段]=[右表].[字段]; 

"Persons" 表：

| Id_P | LastName | FirstName | Address        | City     |
| :--- | :------- | :-------- | :------------- | :------- |
| 1    | Adams    | John      | Oxford Street  | London   |
| 2    | Bush     | George    | Fifth Avenue   | New York |
| 3    | Carter   | Thomas    | Changan Street | Beijing  |

"Orders" 表：

| Id_O | OrderNo | Id_P |
| :--- | :------ | :--- |
| 1    | 77895   | 3    |
| 2    | 44678   | 3    |
| 3    | 22456   | 1    |
| 4    | 24562   | 1    |
| 5    | 34764   | 65   |

```sql
SELECT Persons.LastName, Persons.FirstName, Orders.OrderNo
FROM Persons
INNER JOIN Orders
ON Persons.Id_P=Orders.Id_P
ORDER BY Persons.LastName
```

| LastName | FirstName | OrderNo |
| :------- | :-------- | :------ |
| Adams    | John      | 22456   |
| Adams    | John      | 24562   |
| Carter   | Thomas    | 77895   |
| Carter   | Thomas    | 44678   |

#### 左连接left join

> 会显示左边表的全部数据，右边没有数据则以null显示

```sql
SELECT Persons.LastName, Persons.FirstName, Orders.OrderNo
FROM Persons
LEFT JOIN Orders
ON Persons.Id_P=Orders.Id_P
ORDER BY Persons.LastName
```

| LastName | FirstName | OrderNo |
| :------- | :-------- | :------ |
| Adams    | John      | 22456   |
| Adams    | John      | 24562   |
| Carter   | Thomas    | 77895   |
| Carter   | Thomas    | 44678   |
| Bush     | George    |         |

#### 右连接right join

> 会显示右边表的全部数据，左边没有数据则以null显示

```sql
SELECT Persons.LastName, Persons.FirstName, Orders.OrderNo
FROM Persons
RIGHT JOIN Orders
ON Persons.Id_P=Orders.Id_P
ORDER BY Persons.LastName
```

| LastName | FirstName | OrderNo |
| :------- | :-------- | :------ |
| Adams    | John      | 22456   |
| Adams    | John      | 24562   |
| Carter   | Thomas    | 77895   |
| Carter   | Thomas    | 44678   |
|          |           | 34764   |

#### 查询结果作为数据的查询条件

```sql
SELECT id value,item_text text from sys_dict_item 
where dict_id = (SELECT id from sys_dict dict WHERE dict_code='empty_flag')
```
#### not in替代方案

```sql
-- 使用NOT EXISTS子句。 NOT EXISTS子句将在查询中使用子查询，只返回不匹配的值。
SELECT * FROM table1 a WHERE NOT EXISTS (SELECT * FROM table2 b WHERE b.id = a.id);
-- 使用LEFT JOIN / IS NULL。LEFT JOIN操作会将左表的所有行与右表进行匹配，如果没有匹配的行，则为NULL。可以使用IS NULL子句来找到与左表不匹配的行。
SELECT * FROM table1 a LEFT JOIN table2 b ON a.id = b.id WHERE b.id IS NULL;
```

#### 递归查询所有子级包括自身

```sql
-- @ids、@l	自定义变量
-- (SELECT @ids := '8a81c2b6855cc58301855cff6845002c',@l := 0)	临时表
SELECT DATA.* 
FROM
	(
	SELECT
		@ids AS _ids,
		( 
          SELECT @ids := GROUP_CONCAT( PR_PROCESS_ID ) FROM gm_prp_flow 
          WHERE FIND_IN_SET( PARENT_ID, @ids ) 
        ) AS cids,@l := @l + 1 AS LEVEL 
	FROM gm_prp_flow,
		( SELECT @ids := '8a81c2b6851838ab018518505965003e', @l := 0 ) b 
	WHERE
		@ids IS NOT NULL 
	) ID,
	gm_prp_flow DATA 
WHERE
	FIND_IN_SET( DATA.PR_PROCESS_ID, ID._ids ) 
ORDER BY LEVEL
```
#### 递归查询所有父级包括自身
```sql
SELECT DATA .*, LEVEL
FROM
	(
        SELECT @ids AS _ids,
        (
            SELECT @ids := GROUP_CONCAT(PARENT_ID) FROM gm_prp_flow 
            WHERE FIND_IN_SET(PR_PROCESS_ID, @ids)
        ) AS cids ,@l := @l + 1 AS LEVEL
        FROM gm_prp_flow,
        	(SELECT @ids := '8a81c2b6855cc58301855cff6845002c',@l := 0) b
        WHERE @ids IS NOT NULL AND @ids <> 0
	) ID,
	gm_prp_flow DATA
WHERE
	FIND_IN_SET(DATA .PR_PROCESS_ID, ID._ids)
ORDER BY LEVEL
```
#### GROUP_CONCAT

```sql
-- GROUP_CONCAT	将分组中括号里对应的字符串进行连接
-- 将利用name(salary)进行连接，其中分隔符为';'
SELECT
	dept_id,
	GROUP_CONCAT(
		CONCAT(NAME, "(",salary,")") SEPARATOR ';'
	)
FROM
	employee2
GROUP BY
	dept_id
```

#### 自增设置

```mysql
-- 这将重置“id”列的序列，使其从1开始。请注意，这仅会影响插入表中的新行。现有行将保留其原始值。
ALTER TABLE gm_material AUTO_INCREMENT = 4067
-- 更新现有行以从1开始
SET @new_id = 0;
UPDATE your_table SET id = @new_id:=@new_id+1;
```
#### 子查询交集查询
```mysql
SELECT
-- 	gtp.task_batch_id
* 
FROM
	( SELECT g.task_batch_id FROM gm_tb_processing g WHERE g.id = '40288155878d96cf01879342eb572ece' ) gtp,
	( SELECT g2.product_inf_id, g2.task_batch_id FROM gm_workpiece g2 WHERE g2.TASK_PLAN_ID = '40288155876e2b2901876e96d91f0537' ) gw,
	gm_product_infor gpi 
WHERE
	gtp.task_batch_id = gw.task_batch_id 
	AND gpi.id = gw.product_inf_id 
	AND gpi.product_number = 'YD0061'
```

#### 关键词

```
ACID：原子性-atomicity、一致性-consistency、隔离性-isolation、持久性-durability
ETL：数据的抽取-Extract、转化-Transform、装载-Load
where子句=指定行所对应的条件	having子句=指定组所对应的条件
group by-分组	alter-改变	nested-嵌套
Join Buffer Size-连接缓存区大小
ASC升序-默认	DESC-降序
having子句的要素：3 个要素：常数、聚合函数 和 聚合键
HAVING 大多数情况下和结合 GROUP BY 来使用，但不是一定要结合 GROUP BY 来使用
limit i,n	i-默认为0,n-offset偏移量
SQL 的执行顺序
(8)SELECT
(9)DISTINCT 
(11)<TOP specification><select list>
(1)FROM <left table>
(3)<join_type>JOIN <right_table>
(2)ON <join_condition>
(4)WHERE <where_condition>
(5)GROUP BY <group_by_list>
(6)WITH {CUBE|ROLLUP}
(7)HAVING <having_condition>
(10)ORDER BY <order_by_list>
```

### 参考

- [SQL多表查询](https://blog.csdn.net/weixin_44682554/article/details/113454399)
