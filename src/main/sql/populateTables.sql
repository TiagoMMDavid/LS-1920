begin;
    INSERT INTO ROOM(name, description, location, capacity) VALUES
        ('room1', NULL, 'location1', 20),
        ('room2', NULL, 'location2', NULL),
        ('room3', 'A nice room', 'location3', 20);

    INSERT INTO LABEL(name) VALUES
        ('projector'),
        ('ac'),
        ('fridge');

    INSERT INTO ROOMLABEL(lid, rid) VALUES
        (1, 1),
        (2, 1),
        (2, 2),
        (1, 3);

    INSERT INTO USERS(name, email) VALUES
        ('Joao', 'joao@email.com'),
        ('Miguel', 'miguel@email.com');

    INSERT INTO BOOKING(uid, rid, begin_inst, end_inst) VALUES
        (1, 1, '2020-05-11 14:00', '2020-05-12 14:30'),
        (1, 1, '2020-05-12 14:00', '2020-05-13 14:30');
commit;
end;