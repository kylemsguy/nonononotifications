import random

templates = [
    "I'm sure {name} is just super busy right now... And also for the last {hours:d} hour(s).",
    "You have zero new messages 😊",  # U+1F60A
    "Sorry but {name} is busy right now. Please leave a message!"
]


def get_comment(message):
    comment = templates[random.randrange(len(templates))]
    return comment.format(name = message['name'], hours = int(message['hours_passed']))
