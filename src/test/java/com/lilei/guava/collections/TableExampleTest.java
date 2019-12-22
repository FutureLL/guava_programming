package com.lilei.guava.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: Collections Table
 * @author: Mr.Li
 * @date: Created in 2019/12/21 19:11
 * @version: 1.0
 * @modified By:
 * <p>
 * Table:
 * A collection that associates an ordered pair of keys, called a row key and a column key, with a single value.
 * A table may be sparse, with only a small fraction of row key / column key pairs possessing a corresponding value.
 */
public class TableExampleTest {

    //ArrayTable
    //TreeBaseTable
    //HashBaseTable
    //ImmutableTable

    @Test
    public void test() {
        Table<String, String, String> table = HashBasedTable.create();
        table.put("Language", "Java", "1.8");
        table.put("Language", "Scala", "2.3");
        table.put("Database", "Oracle", "12C");
        table.put("Database", "Mysql", "7.0");
        System.out.println("create: " + table);

        System.out.println("-----------------------------");

        // row(R rowKey): Returns a view of all mappings that have the given row key.
        Map<String, String> language = table.row("Language");
        assertThat(language.containsKey("Java"), is(true));
        System.out.println("row" + language);

        System.out.println("-----------------------------");

        // Map<String,Map<String,String>>
        assertThat(table.row("Language").get("Java"), equalTo("1.8"));
        // column(C columnKey): Returns a view of all mappings that have the given column key.
        Map<String, String> result = table.column("Java");
        System.out.println("column: " + result);

        System.out.println("-----------------------------");

        // cellSet(): Returns a set of all row key / column key / value triplets.
        Set<Table.Cell<String, String, String>> cells = table.cellSet();
        System.out.println("cellSet: " + cells);

        System.out.println("-----------------------------");

        // columnKeySet(): Returns a set of column keys that have one or more values in the table.
        Set<String> columnKeySet = table.columnKeySet();
        System.out.println("columnKeySet: " + columnKeySet);

        System.out.println("-----------------------------");

        // clear(): Removes all mappings from the table.
        table.clear();
        assertThat(table.isEmpty(), equalTo(true));

        /**
         * create: {Language={Java=1.8, Scala=2.3}, Database={Oracle=12C, Mysql=7.0}}
         * -----------------------------
         * row{Java=1.8, Scala=2.3}
         * -----------------------------
         * column: {Language=1.8}
         * -----------------------------
         * cellSet: [(Language,Java)=1.8, (Language,Scala)=2.3, (Database,Oracle)=12C, (Database,Mysql)=7.0]
         * -----------------------------
         * columnKeySet: [Java, Scala, Oracle, Mysql]
         * -----------------------------
         */
    }

    /**
     * 双键的 Map --> Table --> rowKey+columnKye+value
     */
    @Test
    public void test1() {
        Table<Object, Object, Object> table = HashBasedTable.create();
        table.put("jack", "java", 98);
        table.put("jack", "php", 65);
        table.put("jack", "ui", 80);
        table.put("jack", "mysql", 86);

        // 遍历
        Set<Table.Cell<Object, Object, Object>> cells = table.cellSet();
        cells.forEach(c -> System.out.println(c.getRowKey() + " - " + c.getColumnKey() + " : " + c.getValue()));

        /**
         * jack - java : 98
         * jack - php : 65
         * jack - ui : 80
         * jack - mysql : 86
         */
    }

    @Test
    public void test2() {
        //Table<R,C,V> == Map<R,Map<C,V>>
        Table<String, String, String> table = HashBasedTable.create();

        // 使用员工详细信息初始化表
        table.put("IBM", "101", "Mahesh");
        table.put("IBM", "102", "Ramesh");
        table.put("IBM", "103", "Suresh");

        table.put("Microsoft", "111", "Sohan");
        table.put("Microsoft", "112", "Mohan");
        table.put("Microsoft", "113", "Rohan");

        table.put("TCS", "121", "Ram");
        table.put("TCS", "122", "Shyam");
        table.put("TCS", "123", "Sunil");

        // 获取与IBM对应的Map
        Map<String, String> ibmMap = table.row("IBM");

        System.out.println("IBM员工名单");
        for (Map.Entry<String, String> entry : ibmMap.entrySet()) {
            System.out.println("Emp Id: " + entry.getKey() + ", Name: " + entry.getValue());
        }
        System.out.println();

        // 获取表格的所有唯一键
        Set<String> employers = table.rowKeySet();
        System.out.print("Employers: ");
        employers.forEach(e -> System.out.print(e + " "));
        System.out.println();

        // 得到一个对应102的Map
        Map<String, String> EmployerMap = table.column("102");
        for (Map.Entry<String, String> entry : EmployerMap.entrySet()) {
            System.out.println("Employer: " + entry.getKey() + ", Name: " + entry.getValue());
        }
        /**
         * IBM员工名单
         * Emp Id: 101, Name: Mahesh
         * Emp Id: 102, Name: Ramesh
         * Emp Id: 103, Name: Suresh
         *
         * Employers: IBM Microsoft TCS
         *
         * Employer: IBM, Name: Ramesh
         */
    }

    @Test
    public void test3() {
        Table<String, Integer, Integer> table = HashBasedTable.create();
        table.put("A", 1, 100);
        table.put("A", 2, 101);
        table.put("B", 1, 200);
        table.put("B", 2, 201);

        /**
         * contains(Object rowKey, Object columnKey)：
         * Table中是否存在指定rowKey和columnKey的映射关系
         */
        boolean containsA3 = table.contains("A", 3); // false
        boolean containColumn2 = table.containsColumn(2);    // true
        boolean containsRowA = table.containsRow("A");          // true
        boolean contains201 = table.containsValue(201);                 // true

        /**
         * remove(Object rowKey, Object columnKey)：
         * 删除Table中指定行列值的映射关系
         */
        table.remove("A", 2);   // 101

        /**
         * get(Object rowKey, Object columnKey)：
         * 获取Table中指定行列值的映射关系
         */
        table.get("B", 2);      // 201

        /**
         * column(C columnKey)：返回指定columnKey下的所有rowKey与value映射
         */
        Map<String, Integer> columnMap = table.column(2);

        /**
         * row(R rowKey)：返回指定rowKey下的所有columnKey与value映射
         */
        Map<Integer, Integer> rowMap = table.row("B");

        /**
         * 返回以Table.Cell<R, C, V>为元素的Set集合
         * 类似于Map.entrySet
         */
        Set<Table.Cell<String, Integer, Integer>> cells = table.cellSet();
        for (Table.Cell<String, Integer, Integer> cell : cells) {
            // 获取cell的行值rowKey
            cell.getRowKey();
            // 获取cell的列值columnKey
            cell.getColumnKey();
            // 获取cell的值value
            cell.getValue();
        }
    }
}