# metacode-json-logic

## 是什么？

Metacode-json-logic 用来构建复杂的规则，将它们序列化为JSON，在前端和后端之间共享

## 能干什么？

- 通过JSON去表示条件组规则、函数、表达式功能
- 进行计算得出布尔值、数值、日期时间等
- 将语法转换为其他DSL语法(例如SQL)等

## 语法文档

[Operations.md](doc%2FOperations.md)

## 快速开始

### 添加依赖

```xml

<dependency>
  <groupId>tech.wetech.metacode</groupId>
  <artifactId>meatacode-json-logic</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

要求Java 17以上版本，低版本不受支持

### Examples

返回一个布尔值：

```java
// 创建一个线程安全的新实例
JsonLogic jsonLogic = new JsonLogic();
// 进行逻辑运算
String expression = """
  { "or": [{ ">": [1, 2] }, { "<": [1, 2] }] }
""";
assert jsonLogic.evaluateBoolean(expression, null) == true;
```

返回一个数字：

```java
// 4+2=6
String expression = """
  {"+":[4,2]}
""";
// 进行四则运算
assert jsonLogic.evaluateNumber(expression, null) == 6.0;
```

使用函数：

```java
// max(1,2)*min(2,3,4)*2*2*2
String expression = """
  { "*": [{ "max": [1, 2] }, { "min": [2, 3, 4] }, 2, 2, 2] }
""";
// 进行四则运算
assert jsonLogic.evaluateNumber(expression, null) == 6.0;
```

使用变量：

```java
// a=5, b=3
Map<String, String> data = Map.of("a", "5", "b", "3");
// 2+a+b
String expression = """
   {"+":[2,{"var":"a"},{"var":"b"}]}
""";
// 进行四则运算
assert jsonLogic.evaluateNumber(expression, data) == 10.0;
```

返回日期时间：

```java
// TODO
```

转换为 SQL语法：

```java
String expression = """
  {
    "and": [
    { ">": [{ "table_field": ["user", "id"] }, 2] },
    { "==": ["jack", { "table_field": ["user", "name"] }] },
    { "<": [{ "table_field": ["user", "age"] }, 21] }
    ]
  }
"""
IndexSqlRenderResult result = jsonLogic.evaluateIndexSql(expression);
assert " ( user.id > ? and  ? = user.name and  user.age < ? )".equals(result.whereClause());;
assert List.of(2.0, "jack", 21.0).equals(result.args());
```

转换为 SQL语法（Named Params）:

```java
String expression = """
    {
      "contains": [
        {
          "table_field": ["xuesheng", "created_by"]
        },
        [2, 3, 4, 5, 6]
      ]
    }
""";
NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(expression);
assert "xuesheng.created_by in (:xuesheng_created_by_0, :xuesheng_created_by_1, :xuesheng_created_by_2, :xuesheng_created_by_3, :xuesheng_created_by_4) "
    .equals(result.whereClause());
assert Map.of("xuesheng_created_by_0",2.0,
              "xuesheng_created_by_1",3.0,
              "xuesheng_created_by_2",4.0,
              "xuesheng_created_by_3",5.0,
              "xuesheng_created_by_4",6.0).equals(result.args());
```

转换为MongoDB语法:

```java
// TODO
```

更多示例请查看代码测试用例。
