package duriu.deton.duriuopen.duriuopen.config;

import duriu.deton.duriuopen.duriuopen.model.Person;
import org.springframework.context.event.EventListener;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Component
public class DatabaseInitializer {

    private final DatabaseClient databaseClient;

    public DatabaseInitializer(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // Build DDL from Person.class via simple mapping
        String tableName = "person"; // from @Table on Person
        String ddl = buildCreateTableDDL(Person.class, tableName);

        databaseClient.sql(ddl)
                .fetch()
                .rowsUpdated()
                .doOnSuccess(cnt -> System.out.println("DatabaseInitializer: executed DDL for table '" + tableName + "', updated rows: " + cnt))
                .doOnError(err -> System.err.println("DatabaseInitializer: failed to execute DDL: " + err.getMessage()))
                .subscribe();
    }

    private String buildCreateTableDDL(Class<?> entityClass, String tableName) {
        Field[] fields = entityClass.getDeclaredFields();
        StringJoiner cols = new StringJoiner(",\n  ", "CREATE TABLE IF NOT EXISTS " + tableName + " (\n  ", "\n);\n");

        for (Field f : fields) {
            String colName = toSnakeCase(f.getName());
            Class<?> t = f.getType();
            if (f.isAnnotationPresent(Id.class) && (t == Long.class || t == long.class)) {
                cols.add(colName + " BIGSERIAL PRIMARY KEY");
                continue;
            }

            if (t == String.class) {
                cols.add(colName + " VARCHAR(255)");
            } else if (t == Integer.class || t == int.class) {
                cols.add(colName + " INTEGER");
            } else if (t == Long.class || t == long.class) {
                cols.add(colName + " BIGINT");
            } else if (t == LocalDateTime.class) {
                cols.add(colName + " TIMESTAMP");
            } else {
                // fallback to text
                cols.add(colName + " TEXT");
            }
        }

        return cols.toString();
    }

    private String toSnakeCase(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

