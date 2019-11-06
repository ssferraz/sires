package com.github.ssferraz.sires.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.github.ssferraz.sires.entity.Reserva;

public class ReservaDAO {

	private static ReservaDAO instance;
	protected EntityManager entityManager;
	private EntityManagerFactory emf = null;

	public static ReservaDAO getInstance() {
		if (instance == null) {
			instance = new ReservaDAO();
		}
		return instance;
	}

	public ReservaDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		emf = Persistence.createEntityManagerFactory("siresPU");
		if (entityManager == null) {
			entityManager = emf.createEntityManager();
		}
		return entityManager;
	}

	public Reserva getById(int id) {
		return entityManager.find(Reserva.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Reserva> findAll() {
		List<Reserva> listaRetorno = this.entityManager.createQuery("from " + Reserva.class.getName()).getResultList();
		this.entityManager.close();
		return listaRetorno;
	}

	public void persist(Reserva reserva) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(reserva);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void update(Reserva reserva) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(reserva);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Reserva reserva) {
		try {
			entityManager.getTransaction().begin();
			reserva = entityManager.find(Reserva.class, reserva.getId());
			entityManager.remove(reserva);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(int id) {
		try {
			Reserva reserva = getById(id);
			remove(reserva);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
