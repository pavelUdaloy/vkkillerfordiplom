# vkkillerfordiplom

соц сеть))

можно создать пользователя, в нем можно редактировать first/last name или birth date

у пользователя есть чаты и он может отравлять там сообщения
в чате должно быть >=2 участников
чтобы написать пользователю сообщение, он должен создать с ним чат
чтобы создать чат, он должен указать id второго пользователя
так же(по id) происходит добавление в чат

сообщения можно редактировать, удалять

есть группы
группу может создать любой пользователь, он может редактировать ее имя и удалять

у групп есть посты(записи группы)
посты добавлять может админ только группы, он может менять название поста и его содержание(текст)
на группу может подписаться любой пользователь

у постов есть комментарии
комментировать запись может любой пользователь, так же, может редактировать/удалять свой комментарий

так же есть так называемые тэги(метки)
тэги должен вешать на свою группу(при создании) и посты(при создании) администратор группы

благодаря этим тэгам пользователь может фильтровать контент, который попадет ему в ленту рекомендаций,
то есть, пользователь может выбрать у себя в настройках, записи и группы с какими тегами он видеть не хочет

у любого пользователя есть две ленты: лента рекомендаций и лента по подпискам

лента по подпискам выдает последние посты в группах, на которые подписан пользователь

лента рекомендаций будет рандомно(случайно) доставать из базы данных группы, а в каждой будет выбирать по одному последнему посту и отображать пользователю
но если группа или пост этой группы не будут соответствовать тегам, которые хочет увидеть пользователь, она будет отсеиваться
то есть, условно, у группы может быть 100 тегов и если хотя бы один будет подходить под "нежелательные" для пользователя, она будет отсеиваться
так же с постами
