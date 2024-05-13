/*
  Использую в комбинации HashMap и TreeMap. По той причине, что в HashMap доступ к элементам
  O(1) в среднем случае, а TreeMap - O(log n) для операций вставки, удаления и поиска - что требуется в задаче.

  Обоснование выбора структур данных и алгоритмов.
  `HashMap` использую для хранения записей по уникальному идентификатору (account), что обеспечивает
  быстрый доступ для операций изменения и удаления.
  `TreeMap` используется для индексации записей по полям (account, name, value), что позволяет
  выполнять поиск за O(log n).
*/
package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Record[] records = new Record[]{
                new Record(1, "roma", 123.45),
                new Record(2, "tema", 123.45),
                new Record(3, "gosha", 123.45),
        };

        InMemoryCache inMemoryCache = new InMemoryCache();

        Arrays.stream(records).forEach(inMemoryCache::addRecord);

        inMemoryCache.getRecordsByValue(123.45).forEach(x -> System.out.println(x.toString()));
    }
}