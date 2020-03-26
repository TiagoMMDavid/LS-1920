begin;
	delete from BOOKING;
	delete from ROOMLABEL;
	delete from LABEL;
	delete from ROOM;
	delete from USERS;
	
	ALTER SEQUENCE users_uid_seq RESTART;
	ALTER SEQUENCE booking_bid_seq RESTART;
	ALTER SEQUENCE label_lid_seq RESTART;
	ALTER SEQUENCE room_rid_seq restart;
commit;
end;
