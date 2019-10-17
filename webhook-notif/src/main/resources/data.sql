delete from listeners;
insert into listeners(id, endpoint, event_type) values(1, 'http://localhost:9092', 'NEW_SALES');