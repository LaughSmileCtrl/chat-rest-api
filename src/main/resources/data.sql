INSERT INTO users (name) VALUES
    ('firman'),
    ('mang');

INSERT INTO conversations (user_one_id, user_two_id) VALUES
    (1, 2);

INSERT INTO conversation_messages (conversation_id, sender_id, message) VALUES
    (1, 1, 'hai mang, whatsup bro?'),
    (1, 2, 'hello firman, im fine'),
    (1, 1, 'lol?'),
    (1, 2, 'yes, of course'),
    (1, 1, ':)'),
    (1, 2, 'hahaha');