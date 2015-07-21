Wiiam's Personal Bot
======

_**A bot to do anything I please.**_

---

## Where to find this bot

This bot can be found on **#pasta** on [**irc.rizon.net**](irc://irc.rizon.net). It is usually under the name *taylorswift*, however if it is not there then ask **wiiaam**. The bot can be invited to a channel with the `/INVITE` command. Make sure to **check with an admin first** before inviting bots to a channel as they may prefer to keep them out.

## Features

This bot has many features, including, but not limited to,

- [IBIP](https://github.com/Teknikode/IBIP) compliant
- URL title reporting, with a few APIs
- CTCP version, time, ping checks
- Basic administration features
- Currency system
- Cowsay
- Taylor Swift related features
- Ignores
- Automatic nick ghosting
- Web searches
- bro detection
- Steam user info
- Triggers
- Weather info
- Sad frogs
- User quotes

**Note:** These modules and this bot were all made and used primarily for the Rizon IRC network.

An issue with some other networks is that it cannot check for registered users, as there is no `+r` usermode. Although it is unsafe, this can be fixed by changing [this method](https://github.com/wiiam/Personal-Bot/blob/master/bot/Message.java#L237) (bot/Message.java, line 237) to 
```
return Config.getAdmins().contains(sender);
```
This would mean that anyone that uses a nick in the admins list can use admin commands, regardless of wether or not they are registered.

## Using this bot

Any libraries used can be found in the [libs](libs/) folder

If you wish to use this bot

1. Clone the repo with `$ git clone https://github.com/wiiam/Personal-Bot.git`
2. `$ cd Personal-Bot`
3. Rename the config file `bot/config/config.json.example` to `config.json` then edit with your configuration 
4. `$ chmod +x compile.sh` then `$ ./compile.sh`
5. Then run the bot with `$ chmod +x run.sh` `$ ./run.sh`

## Modules

I update this git with modules when I create them

**Commands for the current modules can be found [here](COMMANDS.md). This list may be out of date**

### Adding more modules to the bot

Any modules to add to the bot need to implement the [bot.Module](bot/Module.java) interface, and be placed in the [modules](modules/) folder. The bot is setup to read this folder for modules then create an object of each module to be put into an ArrayList.

### How modules work

When the bot has logged in correctly it will iterate over the Module list and parse each of them a [Message](bot/Message.java) 

Then each module does whatever is in the `parse(Message m)` method. Outputs can be sent with the `Server` class
```
public static void send(String message) - Sends an irc format message
public static void pm(String target, String message) - Sends a PRIVMSG to the target
public static void notice(String targer, String message) - Sends a NOTICE to the target
public static void notice(String target, String message) - Sends a NOTICE to the target
public static void say(String target, String message) - Sends a PRIVMSG to a channel/NOTICE to a user
```

Some useful methods for getting info from the [Message](bot/Message.java) class,
```
public String botCommand() - Returns the bot command, or null if there is none
public String botParams() - Returns the bot params (anything following the bot command)
public boolean senderIsAdmin() - Returns true if the sender is a bot admin
public String sender() - Returns the senders nickname
public String command() - Returns the command, ie PRIVMSG
public String param() - Returns the param, this is usually a room or a user.
public String trailing() - Returns the trailing, the part that they wanted you to see
```


## Known issues

- [quotes.json](modules/json/quotes.json) takes forever to load.
