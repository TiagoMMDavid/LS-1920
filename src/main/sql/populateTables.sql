begin;
    INSERT INTO public.room("name",description,"location",capacity) VALUES 
        ('room1',NULL,'location1',20)
        ,('room2',NULL,'location2',NULL)
        ,('room3','A nice room','location3',20);

    INSERT INTO public."label"("name") VALUES 
        ('projector')
        ,('ac')
        ,('fridge');

    INSERT INTO public.roomlabel(lid,rid) VALUES 
        (1,1)
        ,(2,1)
        ,(2,2)
        ,(1,3);

    INSERT INTO public.users("name",email) VALUES 
        ('Joao','joao@email.com')
        ,('Miguel','miguel@email.com');

    INSERT INTO public.booking(uid,rid,begin_inst,end_inst) VALUES 
        (1,1,'2020-05-11 14:00:00.000','2020-05-12 14:30:00.000')
        ,(1,1,'2020-05-12 14:00:00.000','2020-05-13 14:30:00.000');
commit;
end;