from fbchat import Client
from fbchat.models import *

userpath = "ignoreme/email.txt"
passpath = "ignoreme/password.txt"
username = password = ""

with open(userpath) as f:
    username = f.read()
with open(passpath) as f:
    password = f.read()

content = "Hi"


# TODO: Look for a thread which you are the last person the send a message
# Then check if the last message you sent has been read

client = Client(username, password)

# threadids = client.searchForThreads()

# message = Message(text=content)
# message = Message(text="👍", emoji_size=EmojiSize.LARGE)
#
# def sendmessage():
#     client.send(message, thread_id = userid, thread_type = ThreadType.USER)
#
# client.logout()
