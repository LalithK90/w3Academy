package lk.w3Academy.w3.util.interfaces;

import java.util.List;

public interface AbstractService<E, I> {

    List<E> findAll();

    E findById(I id);

    E persist(E e);

    boolean delete(I id);

    List<E> search(E e);

}
