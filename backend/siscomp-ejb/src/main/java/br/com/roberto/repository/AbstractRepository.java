package br.com.roberto.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractRepository<T, ID> {
    private Class<T> clazz;

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> getClazz() {
        if (clazz == null) {
            clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return clazz;
    }

    public List<T> findAll() {
        return em.createQuery("Select t from " + getClazz().getSimpleName() + " t").getResultList();
    }

    public <T> Paginacao findAllWithPagination(int totalRegistrosPorPagina, int paginaAtual) {
        Paginacao<T> myPaginacao = new Paginacao<T>();
        myPaginacao.setTotaRegistros(Integer.valueOf(em.createQuery("Select count(t) from " + getClazz().getSimpleName() + " t").getSingleResult().toString()));
        myPaginacao.setTotalRegistrosPorPagina(totalRegistrosPorPagina);
        myPaginacao.setPaginaAtual(paginaAtual);
        return myPaginacao;
    }

    public T findById(ID id) {
        return em.find(getClazz(), id);
    }

    public void merge(T entity) {
        em.merge(entity);
    }

    public void persist(T entity) {
        em.persist(entity);
    }

    public void remove(T entity) {em.remove(entity);}

}
