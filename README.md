# CustomStringBuilder

Реализация `StringBuilder` на `char[]` с дополнительным методом `undo()`
на основе snapshot-паттерна.

- Динамический буфер `char[]` с автоматическим расширением
- Методы: `append`, `insert`, `delete`, `indexOf`, `charAt`, `toString`, `length`, `capacity`
- **`undo()`** — откат последнего изменения (один уровень истории)
- Полное покрытие JUnit 5 тестами