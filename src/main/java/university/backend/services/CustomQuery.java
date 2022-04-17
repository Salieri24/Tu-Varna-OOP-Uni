package university.backend.services;

import java.util.List;

public interface CustomQuery<T> {
    List<T> query(Service<T> service);
}
