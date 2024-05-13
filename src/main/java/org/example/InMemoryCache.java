/*
  Использую в комбинации такие структуры данных, как HashMap и TreeMap. По той причине, что в HashMap доступ к элементам
  O(1) в среднем случае, а TreeMap - O(log n) для операций вставки, удаления и поиска - что требуется в задаче.

  Обоснование выбора структур данных и алгоритмов
  `HashMap` использую для хранения записей по уникальному идентификатору (account), что обеспечивает
  быстрый доступ для операций изменения и удаления.
  `TreeMap` используется для индексации записей по полям (account, name, value), что позволяет
  выполнять поиск за O(log n).
*/
package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
class Record {
    long account;
    String name;
    double value;
}

public class InMemoryCache {
    private final Map<Long, Record> recordsById = new HashMap<>();
    private final TreeMap<Long, Set<Long>> recordsByAccount = new TreeMap<>();
    private final TreeMap<String, Set<Long>> recordsByName = new TreeMap<>();
    private final TreeMap<Double, Set<Long>> recordsByValue = new TreeMap<>();

    public void addRecord(Record record) {
        recordsById.put(record.getAccount(), record);
        recordsByAccount.computeIfAbsent(record.getAccount(), k -> new HashSet<>()).add(record.account);
        recordsByName.computeIfAbsent(record.name, k -> new HashSet<>()).add(record.account);
        recordsByValue.computeIfAbsent(record.value, k -> new HashSet<>()).add(record.account);
    }

    public void removeRecord(long accountId) {
        Record record = recordsById.remove(accountId);
        if (record != null) {
            recordsByAccount.getOrDefault(record.account, Collections.emptySet()).remove(accountId);
            recordsByName.getOrDefault(record.name, Collections.emptySet()).remove(accountId);
            recordsByValue.getOrDefault(record.value, Collections.emptySet()).remove(accountId);
        }
    }

    public void updateRecord(Record newRecord) {
        removeRecord(newRecord.account);
        addRecord(newRecord);
    }

    public Set<Record> getRecordsByAccount(long account) {
        return getRecords(recordsByAccount.getOrDefault(account, Collections.emptySet()));
    }

    public Set<Record> getRecordsByName(String name) {
        return getRecords(recordsByName.getOrDefault(name, Collections.emptySet()));
    }

    public Set<Record> getRecordsByValue(double value) {
        return getRecords(recordsByValue.getOrDefault(value, Collections.emptySet()));
    }


    private Set<Record> getRecords(Set<Long> accountIds) {
        Set<Record> result = new HashSet<>();
        for (Long accountId : accountIds) {
            result.add(recordsById.get(accountId));
        }
        return result;
    }
}
