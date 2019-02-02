package io.reactiveprogramming.webhook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.reactiveprogramming.webhook.entity.Listener;
import io.reactiveprogramming.webhook.enums.EventType;

@Repository
public interface IListenerDAO extends JpaRepository<Listener, Long>{
	public List<Listener> findByEventType(EventType eventType);
}
