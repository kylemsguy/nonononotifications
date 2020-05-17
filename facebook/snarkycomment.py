import random

templates = [
    "I'm sure {name} is just super busy right now... And also for the last {hours:d} hours.",
    "You have zero new messages 💖",
    "Sorry but {name} is busy right now, so please leave a message!",
    "It's been {hours:d} hours since {name} has responded to you, not sure if it's just you though.",
    "Spooky... Is it me or is {name} ghosting you? 👻",
    "I'm sure {name} misses you dearly... after all, they haven't responded to you in {hours:d} hours 😝",
    "What did you say to {name}? Clearly something awkward because {name} hasn't texted you back in {hours:d} hours!",
    "Ha Sike! Nobody messaged you! 🤪",
]

title_templates = [
    "[IMPORTANT MESSAGE]",
    "We're Here for you",
    "Facebook Messenger",
    "[NOTIFICATION ALERT]"
]

def get_title(message):
    comment = title_templates[random.randrange(len(title_templates))]
    return comment.format(name=message['first_name'], hours=int(message['hours_passed']))


def get_comment(message):
    comment = templates[random.randrange(len(templates))]
    return comment.format(name=message['first_name'], hours=int(message['hours_passed']))
